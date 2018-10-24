package ru.defo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.defo.filters.CriterionFilter;
import ru.defo.managers.BinManager;
import ru.defo.managers.UnitManager;
import ru.defo.model.WmBin;
import ru.defo.model.WmUnit;
import ru.defo.model.WmUser;
import ru.defo.model.views.VQuantInfoShort;
import ru.defo.servlets.InvOrdBinBlock;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;
import ru.defo.util.HibernateUtil;

public class BinController {

	private BinManager binMgr;


	public WmBin getBin(String areaCode, Long rackNo, Long binRackNo, Long levelNo)
	{
		return new BinManager().getBin(areaCode, rackNo, levelNo, binRackNo);

	}

	public Long getMirroredRackNo(WmBin bin)
	{
		if(bin.getRackNo().intValue()%2 ==0){
			   return bin.getRackNo()-1;
			} else {
			   return bin.getRackNo()+1;
			}
	}

	public WmBin calcMirrorBinForBin(WmBin bin)
	{
		Long mirroredRackNo = getMirroredRackNo(bin);
		return getBin(bin.getAreaCode(), mirroredRackNo, bin.getBinRackNo(), bin.getLevelNo());
	}

	public WmBin getMirrorBin(Object binCode)
	{
		WmBin bin = getBin(binCode.toString());
		return getMirrorBin(bin);
	}

	public String checkBinForReserv(HttpSession session){

		WmUnit unit = new UnitController().getUnitByUnitCode(session.getAttribute("unitcode"));
		WmBin unitBin = new BinController().getBin(session.getAttribute("bincode"));
		WmBin reservBin = new BinController().getBin(session.getAttribute("bincodeblock"));

		return checkBinForReserv(unit, unitBin, reservBin);
	}

	public String checkBinForReserv(WmUnit unit, WmBin unitBin, WmBin reservBin ){
		String err = checkBinForReserv(reservBin);

		/* Разные этажи, через стеллаж */
		if(unitBin.getLevelNo() != reservBin.getLevelNo()
				|| (Math.abs(unitBin.getRackNo().intValue()-reservBin.getRackNo().intValue()))>1
				|| (Math.abs(unitBin.getBinRackNo().intValue()-reservBin.getBinRackNo().intValue()))>2
		   ){
			  err = "Ячейка <b>"+reservBin.getBinCode()+"</b> не может быть занята поддоном <b>"+unit.getUnitCode()+"</b> прописанным в ячейке <b>"+unitBin.getBinCode()+"</b>";
		     }

		if(reservBin.getReservForUnitId() != null && !reservBin.getReservForUnitId().equals(unit.getUnitId())){

			WmUnit unitRes = new UnitManager().getUnitById(reservBin.getReservForUnitId());

			err = DefaultValue.BIN_RESERVED+" -> "+unitRes.getUnitCode();
		}

		return err;
	}

	public String checkBinForReserv(WmBin bin)
	{
		/* Ячейка занята другим поддоном */
		if(!isEmpty(bin)){
			return DefaultValue.INVENT_BIN_RESERV_UNIT;
		}
		return "";
	}

	public boolean isEmpty(WmBin bin){
		return new UnitManager().getUnitListByBin(bin).size()==0;
	}

	public WmBin getMirrorBin(WmBin bin)
	{
		return new BinManager().getMirroredBin(bin);
	}

	public BinController() {
		binMgr = new BinManager();
	}

	public WmBin getBin(HttpSession httpSession){
		return getBin(httpSession.getAttribute("bincode").toString());
	}

	public boolean clearReservForUnit(HttpSession session)
	{
		WmBin bin = new BinController().getBin(session.getAttribute("bincode"));
		WmUser user = new UserController().getUserById(session.getAttribute("userid"));

		return clearReservForUnit(user, bin);
	}

	public boolean clearReservForUnit(WmUser user, WmBin bin)
	{
		Session session = HibernateUtil.getSession();
		WmUnit  unit = new UnitManager().getUnitById(bin.getReservForUnitId());

		String comment = "Инвентаризация. Отмена блокирования ячейки "+(bin==null?"null":bin.getBinCode())+" поддоном "+(unit==null?"null":unit.getUnitCode());

		bin.setReservForUnitId(null);
		Transaction tx = session.getTransaction();
		try{
			tx.begin();
			session.merge(bin);
			new HistoryController().addEntry(session, user, unit, bin, comment);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}finally{
			session.clear();
			session.close();
		}

		return true;
	}


	public boolean setReserv(HttpSession session)
	{
		WmUnit unit = new UnitController().getUnitByUnitCode(session.getAttribute("unitcode"));
		WmBin reservBin = new BinController().getBin(session.getAttribute("bincodeblock"));
		WmUser user = new UserController().getUserById(session.getAttribute("userid"));

		return setReserv(reservBin, unit, user);
	}

	public boolean setReserv(WmBin binForReserv, WmUnit unit, WmUser user)
	{
		binForReserv.setReservForUnitId(unit.getUnitId());

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		try{
			tx.begin();
			session.merge(binForReserv);
			new HistoryController().addEntry(session, user, unit, binForReserv, DefaultValue.INVENT_BIN_RESERV_UNIT+" "+unit.getUnitCode()+"->"+binForReserv.getBinCode());

			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
			return false;
		}finally{
			session.close();
		}

		return true;
	}

	public WmBin getBin(WmUnit unit)
	{
		if(unit ==null || unit.getBinId()==null) return new WmBin();
		return new BinManager().getBinById(unit.getBinId());
	}


	public Long getBinNo(WmBin bin)
	{
		return getBinNo(bin.getBinCode());
	}

	public Long getBinNo(String binCode)
	{
		Long binNo;
		try{
			binNo = Long.valueOf(binCode.substring(8, 11));
		 }catch(NumberFormatException e){
			 System.out.println("ERROR: wrong binNo for ["+binCode+"] !");
			 binNo = null;
		 }

		return binNo;
	}

	public List<WmBin> getBinList(Object startBinCodeObj, Object finishBinCodeObj){

		WmBin binStart = getBin(startBinCodeObj);
		WmBin binFinish = getBin(finishBinCodeObj);

		return getBinList(binStart, binFinish);
	}

	public String getBinListStringByBinFlt(String binFilter)
	{
		StringBuilder sb = new StringBuilder("");
		List<WmBin> binList = getBinListByBinFlt(binFilter);
		for(WmBin bin : binList){
			sb.append(","+bin.getBinId().toString());
		}

		return sb.toString().substring(1, sb.length());
	}

	public List<WmBin> getBinListByBinFlt(String binFilter)
	{
		return new BinManager().getBinListByBinFlt(binFilter);
	}

	public List<WmBin> getBinList(WmBin startBin, WmBin finishBin)
	{
		return new BinManager().getBinListBetweenBins(startBin, finishBin);
	}

	public boolean checkBin(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession();
		if(session.getAttribute("bincode")==null){
			session.setAttribute("message", "Потеряно значение ячейки! Операция отменена. Необходимо повторная инвентаризация поддона ["+session.getAttribute("unitcode")+"]");
			session.setAttribute("action", "InvStart");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return false;
		}
		return true;
	}

	public Map<WmBin, Long> getBinMapByOrderPosArticle(String orderPosIdTxt, String articleCode)
	{
		Map<WmBin, Long> binMap = new HashMap<WmBin, Long>();

		List<VQuantInfoShort> vquantinfoList = new QuantController().getVQuantInfoListByArticle(articleCode);
		for(VQuantInfoShort quantInfo : vquantinfoList)
		{
			WmBin bin = new BinController().getBin(quantInfo.getBinCode());
			if(bin!=null && bin.getBinTypeId() <=2L && bin.getBlockOut()==false && bin.getNeedCheck()==false)
			{
				Long binNo = new BinController().getBinNoByBinCode(bin.getBinCode());
				long needQty = new OrderController().getPickRemainsOrderPos(orderPosIdTxt).longValue();

				Long weight = bin.getLevelNo()*50+bin.getRackNo()*20+binNo+ Math.abs((quantInfo.getQuantity().longValue()-needQty)*100);
				//System.out.println("BinRequest:doGet:findbin "+bin.getBinCode()+" weight: "+weight+" qty_bin: "+ quantInfo.getQuantity()+" qty_ord: "+needQty);

				binMap.put(bin, weight);
			}
		}

		return binMap;
	}


	public Map<WmBin, Long> getSortedBinMap(Map<WmBin, Long> unsortedBinMap)
	{
		return unsortedBinMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue)->oldValue, LinkedHashMap::new));
	}


	public void markBinUnitNeedCheck(HttpSession sessionHTTP, WmBin bin, WmUnit unit){

		Session sessin = HibernateUtil.getSession();
		try{
			sessin.getTransaction().begin();

			if(bin != null){
				bin.setNeedCheck(true);
				HibernateUtil.update(sessin, bin);
			}
			if(unit != null){
				unit.setNeedCheck(true);
				HibernateUtil.update(sessin, unit);
			}

			new HistoryController().addEntryMarkNeedCheck(sessin, sessionHTTP, bin, unit, 5L, "Подбор: требуется локальная инвентаризация");
			sessin.getTransaction().commit();

		}catch(Exception e){
			e.printStackTrace();
			sessin.getTransaction().rollback();
			//sessin.close();
		} finally{
			sessin.close();
		}

	}

	public boolean equalBins(String masterBinCode, String checkBinCode)
	{
		return masterBinCode.substring(0, masterBinCode.length()-1)
		.equalsIgnoreCase(checkBinCode.substring(0, checkBinCode.length()-1));
	}

	public String getBinCode(Long binId) {
		WmBin bin = binMgr.getBinById(binId);

		if (bin instanceof WmBin) {
			return bin.getBinCode();
		} else
			return "";
	}

	public WmBin getBinById(Long binId){
		return binMgr.getBinById(binId);
	}

	/**@deprecated
	 * */
	public WmBin getBin(String binBarcode) {
		return binMgr.getBinByBarcode(binBarcode);
	}

	public WmBin getBin(Object binBarcode)
	{
		String binCode = new String();
		if(binBarcode != null) {
			binCode = binBarcode.toString().trim();
			binCode = ru.defo.util.Bc.symbols(binCode);
		}
		//System.out.println("ERROR: BinController.getBin BinBarcode ["+binBarcode+"] -> binCode ["+binCode+"]");
		WmBin bin = binMgr.getBinByBarcode(binCode);
		return bin;
	}

	public boolean isLevelExist(Object binbarcode, Object levelText){
		BinManager mgr = new BinManager();
		WmBin bin = mgr.getBinByBarcode(String.valueOf(binbarcode));

		if(levelText.toString().length() != 1) return false;

		int level;
		try{
			level = Integer.valueOf(String.valueOf(levelText));
		}catch(NumberFormatException e){
			return false;
		}

		return (mgr.getMaxSectionLevel(bin.getSectionId()) >= level //&& level > 0
				);
	}

	/**
	 * @deprecated
	 * */
	public boolean isLevelExist(Long binId, int level) {
		return (binMgr.getMaxSectionLevel(binMgr.getBinById(binId).getSectionId()) >= level //&& level > 0
				);
	}


	public String getBinWithLevel(Object binbarcode, Object leveltext){
		String bincode = String.valueOf(binbarcode);
		int level = Integer.valueOf(String.valueOf(leveltext));

		return getBinWithLevel(bincode, level);
	}

	public String getBinWithLevel(String binCode, int level) {

		return binCode.substring(0, binCode.length() - 2) + String.valueOf(100 + (level > 0 ? level : 0)).substring(1);
	}

	/**
	 * @deprecated
	 */
	public long getFreeSpaceBin(Long binId) {
		WmBin bin = new BinManager().getBinById(binId);
		return (bin.getDepthCount() - new UnitManager().getUnitListByBinId(binId).size());
	}

	public int getFreeSpaceBin(Object binbarcode) {
		WmBin bin = new BinManager().getBinByBarcode(String.valueOf(binbarcode));
		return (int) (bin.getDepthCount() - new UnitManager().getUnitListByBinId(bin.getBinId()).size());
	}

	public String getErrorTextWrongBin(String masterBinCode, String scanedBinCode){
		return ErrorMessage.WRONG_BIN_SCANED.message(new ArrayList<String>(Arrays.asList(masterBinCode, scanedBinCode)));
	}

	public String getErrorTextBinCode(String binCode){
		return ErrorMessage.WRONG_BIN.message(new ArrayList<String>(Arrays.asList(binCode)));
	}

	public String getErrorTextWrongLevel(String masterBinCode, String level){
		int masterLevel = Integer.valueOf(masterBinCode.substring(masterBinCode.length()-2, masterBinCode.length()));
		return ErrorMessage.WRONG_BIN_LEVEL.message(new ArrayList<String>(Arrays.asList(masterBinCode, String.valueOf(masterLevel), level)));
	}

	public String getErrorText(String binBarcode) {
		return ErrorMessage.BC_NOT_BIN.message(new ArrayList<String>(Arrays.asList(binBarcode)));
	}

	public String getAdviceBinErrorText(String binCode){
		WmBin bin = getBin(binCode);

		/* is not bin BarCode */
		if(bin == null){
			return getErrorText(binCode);
		}

		/* bin able to receive */
		if(!bin.isAdviceBin()){
			return ErrorMessage.WRONG_ADVICE_BIN_TYPE_BIN.message(new ArrayList<String>(Arrays.asList(binCode)));
		}

		return null;
	}

	public String getError(String binBarcode){
		WmBin bin = getBin(binBarcode);
		if(!(bin instanceof WmBin)) return getErrorText(binBarcode);

		return getError(bin);
	}

	public String getError(WmBin bin)
	{
		if(bin.getReservForUnitId() != null){
			WmUnit unit = new UnitController().getUnitById(bin.getReservForUnitId());
			return ErrorMessage.BIN_RESERVED_UNIT.message(new ArrayList<String>(Arrays.asList(bin.getBinCode(),(unit==null?bin.getReservForUnitId().toString():unit.getUnitCode()) )));

		}

		if(new InventoryController().hasOpenedInventByBin(bin)){
			return "Запрещается перемещение при открытых заданиях на инвентаризацию!";
		}

		return null;
	}

	public String check(String binBarcode)
	{
		WmBin bin = getBin(binBarcode);
		if (bin == null) {
			return getErrorText(binBarcode);
		}

		return null;
	}

	public String getLeveledBin(Object binBarcode, String levelNo) {
		if(levelNo == null || levelNo.isEmpty()) return null;
		String LeveledBinCode = null;
		int level = 0;

		level = Integer.valueOf(levelNo).intValue();

		String bincode = String.valueOf(binBarcode);
		WmBin bin = new BinManager().getBinByBarcode(bincode);

		if (bin == null)
			return null;
        /* --Нет у нас больше нормальных набивных ячеек. Они у нас теперь этажные
		if (bin.getDepthCount() > 1)
			return bincode;
        */

		if (isLevelExist(bin.getBinId(), level))
			LeveledBinCode = getBinWithLevel(bincode, Integer.valueOf(levelNo));

		return LeveledBinCode;
	}

	public String getErrorTextBinFull(Object binBarcode, Object unitBarcode) {

		String bincode = String.valueOf(binBarcode);
		String unitcode = String.valueOf(unitBarcode);

		String errMessage = null;
		WmBin bin = getBin(bincode);

		if(!(bin instanceof WmBin)) return null;

		System.out.println("bin.getBinId() : "+bin.getBinId());

		if (getFreeSpaceBin(bin.getBinId()) < 1) {
			errMessage = ErrorMessage.BIN_IS_FULL.message(new ArrayList<String>(Arrays.asList(bincode, unitcode)));
		}
		return errMessage;
	}

	public int getBinDepthCount(Object binbarcode) {
		String bincode = String.valueOf(binbarcode);
		return new BinManager().getDepthCount(bincode);
	}

	public int getLevelFromBinCode(String binBarcode){
		WmBin bin = new BinManager().getBinByBarcode(binBarcode);
		return bin.getLevelNo().intValue();
	}

	public List<WmBin> getDokList(){
		return binMgr.getDokList();
	}

	public Long getBinNoByBinCode(String binCode)
	{
		String binNoTxt = binCode.substring(binCode.length()-6, binCode.length()-3);
		return Long.valueOf(binNoTxt);
	}

}

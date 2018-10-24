package ru.defo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.defo.managers.ArticleManager;
import ru.defo.managers.BinManager;
import ru.defo.managers.QuantManager;
import ru.defo.managers.UnitManager;
import ru.defo.managers.UnitTypeManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmBin;
import ru.defo.model.WmQuant;
import ru.defo.model.WmUnit;
import ru.defo.model.WmUnitType;
import ru.defo.model.WmUser;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;
import ru.defo.util.HibernateUtil;

public class UnitController {

	public WmBin getBinByUnit(WmUnit unit){

		if(unit.getBinId()!=null){
			WmBin bin = new BinManager().getBinById(unit.getBinId());
			return bin;
		}

		return null;
	}

    public WmUnit getUnit(HttpSession httpSession){
    	return getUnitByUnitCode(httpSession.getAttribute("unitcode"));
    }


	public WmUnit getUnitByUnitCode(Object unitIdObj)
	{
		String unitCode = ru.defo.util.Bc.symbols(String.valueOf(unitIdObj).trim());
		return getWmUnit(unitCode);
	}

	public WmUnit getWmUnit(String unitCode) {
		WmUnit unit = null;
		try{
			unit = new UnitManager().getUnitByCode(unitCode);
		}catch(Exception e){
			e.printStackTrace();
		}

		return unit;
	}

	/**
	 * @param - simple convert String unitIdText to unitId Long
	 * */
	public static Long getId(String unitIdText){
		Long unitId = null;
		try{
			unitId = Long.valueOf(unitIdText);
		} catch(NumberFormatException e){
			e.printStackTrace();
		}
		return unitId;
	}

	public static Long getId(Object unitIdObj)
	{
		String unitIdTxt = String.valueOf(unitIdObj);
		return getId(unitIdTxt);
	}

	public Long getUnitId(String unitCode){

		WmUnit unit = getWmUnit(unitCode);
		if(!(unit instanceof WmUnit)) return null;

		return unit.getUnitId();
	}


	public boolean isUnitBarCode(String barCode)
	{
		if (barCode==null ||  barCode.length() != DefaultValue.UNIT_NUMBER_SIZE) return false;

		WmUnitType unitType = new UnitTypeManager().getUnitTypeByCode(barCode.substring(0, 2));
		return (unitType instanceof WmUnitType);

	}

	public List<WmUnit> getUnitListByBinId(Long binId) {
		return new UnitManager().getUnitListByBinId(binId);
	}

	public List<WmUnit> getUnitListByBinIdArticleId(Long binId, Long articleId) {
		return new UnitManager().getUnitListByBinIdArticleId(binId, articleId);
	}

	public int getUnitListSize(List<WmUnit> unitList) {
		return unitList.size();
	}

	public WmUnit getUnitListFirst(List<WmUnit> unitList) {
		return (getUnitListSize(unitList) > 0 ? unitList.get(0) : null);
	}

	/**@deprecated
	 * @see +articleId
	 * */
	public WmUnit getUnitFromBincode(Object binBarcode) {
		WmBin bin = new BinManager().getBinByBarcode(String.valueOf(binBarcode));
		return getUnitListFirst(getUnitListByBinId(bin.getBinId() ));
	}

	public WmUnit getUnitFromBincodeArticleCode(Object binBarcode, Object articleCode) {
		WmBin bin = new BinManager().getBinByBarcode(String.valueOf(binBarcode));
		WmArticle article = new ArticleManager().getArticleByCode(articleCode.toString());
		return getUnitListFirst(getUnitListByBinIdArticleId(bin.getBinId(), article.getArticleId()));
	}


	@SuppressWarnings("deprecation")
	public void del_UnitFromBin(Long unitId) {
		new UnitManager().clearBinId(unitId);
	}

	public void delUnitFromBin(Session session, WmUnit unit, WmUser user)
	{
		new HistoryController().addEntry(session, Long.valueOf(String.valueOf(user.getUserId())), unit.getUnitId(), DefaultValue.INVENT_DEL_UNIT_TEXT);
		new UnitManager().clearUnitReserv(session, unit);
		new UnitManager().clearBinId(session, unit);

	}


	/**
	 *	@deprecated
	 */
	public void delUnitFromBin(Object unitBarcode, Object userIdo) {
		UnitManager mgr = new UnitManager();

		Long unitId = getUnitId(unitBarcode.toString());

		new HistoryController().addEntry(Long.valueOf(String.valueOf(userIdo)), unitId, DefaultValue.INVENT_DEL_UNIT_TEXT);
		mgr.clearBinId(unitId);
	}

	/**
	 *	@deprecated
	 */
	public void delUnitFromBin(Object unitBarcode, Object userIdo, String commentText) {
		UnitManager mgr = new UnitManager();

		Long unitId = getUnitId(unitBarcode.toString());

		new HistoryController().addEntry(Long.valueOf(String.valueOf(userIdo)), unitId, commentText);
		mgr.clearBinId(unitId);
	}

	public void delUnitFromBin(Session session, Object unitBarcode, Object userIdo, String commentText) {
		UnitManager mgr = new UnitManager();

		Long unitId = getUnitId(unitBarcode.toString());

		new HistoryController().addEntry(session, Long.valueOf(String.valueOf(userIdo)), unitId, commentText);
		mgr.clearBinId(session, unitId);
	}

	public WmUnit getOrCreateUnit(Session session, Object unitBarcode, WmBin bin, Object userId, String moduleText)
	{
		String unitCode = String.valueOf(unitBarcode);
		Long userIdl = Long.valueOf(String.valueOf(userId));

		WmUnit unit = getWmUnit(unitCode);
		if (!(unit instanceof WmUnit))
		{
			unit = createUnit(session, unitCode, bin, moduleText+"."+DefaultValue.CREATE_UNIT_TEXT, userIdl);
		} else {
			if(bin!=null)unit.setBinId(bin.getBinId());
			unit.setNeedCheck(false);
			session.persist(unit);
		}

		return unit;
	}

	public WmUnit createUnit(Session session, String unitCode, WmBin bin, String comment, Long userId)
	{

		WmUnit unit = initUnit(unitCode, bin==null?null:bin.getBinId());
		session.merge(unit);
		new HistoryController().addEntry(session, userId, unit, bin, comment);
		return unit;
	}

	public WmUnit initUnit(String unitCode, Long binId){
		UnitManager unitMgr = new UnitManager();
		WmUnit unit = new WmUnit();
		unit.setUnitId(Long.valueOf(unitMgr.getNextUnitId()));
		unit.setUnitCode(unitCode);
		unit.setWaste(false);
		unit.setBlocked(false);
		if(binId != null)unit.setBinId(binId);
		unit.setNeedCheck(false);
		unit.setUnitTypeCode(unitCode.substring(0, 2));
		return unit;
	}


	@SuppressWarnings("deprecation")
	public Long createUnit(String unitCode, Long binId, String comment, Long userId) {

		UnitManager unitMgr = new UnitManager();
		WmUnit unit = new WmUnit();
		unit.setUnitId(Long.valueOf(unitMgr.getNextUnitId()));
		unit.setUnitCode(unitCode);
		unit.setWaste(false);
		unit.setBlocked(false);
		unit.setBinId(binId);
		unit.setNeedCheck(false);
		unit.setUnitTypeCode(unitCode.substring(0, 2));



		//unitMgr.createWmUnit(unit);
		HibernateUtil.getSession().merge(unit);
		new HistoryController().addEntry(userId, unit.getUnitId(), binId, comment);

		return unit.getUnitId();
	}

	public void setBinUnit(Session session, WmBin bin, WmUnit unit, String comment, Long userId) {
		unit.setBinId(bin.getBinId());
		unit.setNeedCheck(false);
		session.persist(unit);

		List<WmQuant> quantList = new QuantManager().getQuantListByUnitId(unit.getUnitId());
		for(WmQuant quant : quantList){
			quant.setNeedCheck(false);
			session.persist(quant);
		}

		bin.setNeedCheck(false);
		session.persist(bin);

		new HistoryController().addEntry(session, userId, unit, bin, comment);
	}
	/**@deprecated
	 * */
	public void setBinUnit(Session session, Long binId, Long unitId, String comment, Long userId) {
		UnitManager unitMgr = new UnitManager();
		WmUnit unit = unitMgr.getUnitById(unitId);
		unit.setBinId(binId);
		//unitMgr.updateUnit(session, unit);
		session.persist(unit);

		new HistoryController().addEntry(session, userId, unitId, comment);
	}

	public void setBinUnit(Object binBarcode, Object unitBarcode, String comment, Object userId) {

		Session session = HibernateUtil.getSession();
		Transaction t = session.getTransaction();
		try{
			t.begin();
			Long userIdL = Long.valueOf(String.valueOf(userId));

			WmBin bin = new BinManager().getBinByBarcode(String.valueOf(binBarcode));
			WmUnit unit = getOrCreateUnit(session, unitBarcode, bin, userId, DefaultValue.INVENTORY);
			t.commit();
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			if(session.isOpen()) session.close();
		}
/*
		t.begin();
		setBinUnit(session, bin, unit, comment, userIdL);
		t.commit();
*/
	}

	public WmUnit getUnitById(Long unitId) {
		UnitManager unitMgr = new UnitManager();
		return (unitMgr.getUnitById(unitId));
	}

	public boolean checkUnitHasArticle(WmUnit unit, WmArticle article, String backPageJsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		QuantController quantCtrl = new QuantController();
		WmQuant quant = quantCtrl.getQuantByUnitArticle(unit, article);

		if(quant instanceof WmQuant == false)
		{
			request.getSession().setAttribute("message", quantCtrl.getErrorText(article.getArticleCode(), unit.getUnitCode()));
			request.getSession().setAttribute("action", backPageJsp);
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return false;
		}

		return true;
	}

	public boolean checkUnitBcExists(String unitBc, String backPageJsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if(isUnitBc(unitBc) == false){
			request.getSession().setAttribute("message", ErrorMessage.BC_NOT_UNIT.message(new ArrayList<String>(Arrays.asList(unitBc))));
			request.getSession().setAttribute("action", backPageJsp);
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return false;
		}
		return true;
	}

	public boolean checkUnitNotEmpty(String unitBc, String backPageJsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if(isEmpty(getUnitId(unitBc))){
			request.getSession().setAttribute("message", getErrorText_EmptyUnit(unitBc));
			request.getSession().setAttribute("action", backPageJsp);
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return false;
		}

		return true;
	}

	public boolean isUnitBc(String unitBc){
		return (getWmUnit(unitBc) instanceof WmUnit);
	}


	public String getErrorText(String unitBarcode) {
		if (unitBarcode != null) {
			UnitController unitCtrl = new UnitController();

			if (!unitCtrl.isUnitBarCode(unitBarcode))
				return ErrorMessage.BC_NOT_UNIT.message(new ArrayList<String>(Arrays.asList(unitBarcode)));
			/*
			WmUnit unit = unitCtrl.getWmUnit(unitBarcode);
			if (unit != null) {
				WmBin bin = new BinManager().getBinById(unit.getBinId());
				if (bin != null && !bin.isAdviceBin())
					return ErrorMessage.WRONG_BINTYPE_LOCATION_ADVICE
							.message(new ArrayList<String>(Arrays.asList(unitBarcode, bin.getBinCode())));
			}
			*/
		}
		return null;
	}

	public String getErrorText_EmptyUnit(String unitCode) {
		return ErrorMessage.UNIT_IS_EMPTY.message(new ArrayList<String>(Arrays.asList(unitCode)));
	}

	public int getBinUnitCount(Object binBarcode) {
		WmBin bin = new BinManager().getBinByBarcode(String.valueOf(binBarcode));
		List<WmUnit> units = getUnitListByBinId(bin.getBinId());

		return units.size();
	}

	public String getUnitCode_BinArticle(String binCode, String articleCode) {

		WmBin bin = new BinManager().getBinByBarcode(binCode);
		if(!(bin instanceof WmBin)) return null;

		Long binId = bin.getBinId();
		Long articleId = new ArticleManager().getArticleByCode(articleCode).getArticleId();

		return new UnitManager().getUnitBinArticle(binId, articleId);
	}

	public boolean isEmpty(Long unitId) {
		return new QuantController().getQuantListByUnit(unitId).isEmpty();
	}

	@SuppressWarnings("deprecation")
	public boolean saveSplitedEntry(String unitSrcCode, String unitDestCode, String articleCode, String splitedQty,
			Long userId) {
		UnitManager unitMgr = new UnitManager();
		QuantManager qtyMgr = new QuantManager();
		HistoryController histCtrl = new HistoryController();
		Long qty = Long.valueOf(splitedQty);

		Session sessionH = HibernateUtil.getSession();
		Transaction trn = sessionH.getTransaction();
		try {
			trn.begin();

			WmArticle article = new ArticleManager().getArticleByCode(articleCode);

			WmUnit unitSrc = unitMgr.getUnitByCode(unitSrcCode);
			System.out.println("1.unitSrc: "+unitSrc);
			WmQuant quantSrc = qtyMgr.getQuantByUnitArticle(unitSrc, article);
			System.out.println("2.quantSrc: "+quantSrc);
			quantSrc.setQuantity(quantSrc.getQuantity() - qty);

			WmUnit unitDest = unitMgr.getUnitByCode(unitDestCode);

			WmQuant newQuant = new QuantController().addOrCreateQuant(HibernateUtil.getSession(),quantSrc.getArticleId(), quantSrc.getSkuId(),
					quantSrc.getLotId(), quantSrc.getQualityStateId(), quantSrc.getBoxBarcode(), qty,
					quantSrc.getClientId(), unitDest.getUnitId(), quantSrc.getAdviceId(), quantSrc.getAdvicePosId(),
					userId);
			System.out.println("3.newQuantId: "+newQuant.getQuantId());
			histCtrl.addEntry(sessionH, userId, unitDest.getUnitId(), DefaultValue.SPLIT + "." + DefaultValue.PLUS_QUANT,
					qtyMgr.getQuantById(newQuant.getQuantId()), qty);

			if (quantSrc.getQuantity() == 0) {
				histCtrl.addEntry(sessionH, userId, unitSrc.getUnitId(), DefaultValue.SPLIT + "." + DefaultValue.DEL_QUANT,
						quantSrc, -qty);
				//qtyMgr.delQuantByQuantId(quantSrc.getQuantId());
				sessionH.delete(quantSrc);
			} else {
				//qtyMgr.updateQuant(quantSrc);
				sessionH.saveOrUpdate(quantSrc);
				histCtrl.addEntry(sessionH, userId, unitSrc.getUnitId(), DefaultValue.SPLIT + "." + DefaultValue.MINUS_QUANT,
						quantSrc, -qty);
			}
			System.out.println("END: ");

			trn.commit();

			return true;
		} catch (Exception e) {
			trn.rollback();
			e.printStackTrace();
			return false;
		}

	}


	public int getArticleCount(Long unitId){
		return new QuantManager().getCountArticleInUnit(unitId);
	}

}

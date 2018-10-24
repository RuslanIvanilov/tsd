package ru.defo.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.defo.managers.ArticleManager;
import ru.defo.managers.BinManager;
import ru.defo.managers.QuantManager;
import ru.defo.managers.UnitManager;
import ru.defo.managers.VQuantInfoShortManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmBin;
import ru.defo.model.WmInventory;
import ru.defo.model.WmInventoryPos;
import ru.defo.model.WmQualityState;
import ru.defo.model.WmQuant;
import ru.defo.model.WmSku;
import ru.defo.model.WmUnit;
import ru.defo.model.WmUser;
import ru.defo.model.views.VQuantInfoShort;
import ru.defo.util.DefaultValue;
import ru.defo.util.EntryType;
import ru.defo.util.ErrorMessage;
import ru.defo.util.HibernateUtil;
import ru.defo.util.HistoryComment;

public class QuantController {

	public Long getQuantId(Object quantId) {
		return Long.valueOf(String.valueOf(quantId));
	}

	public List<WmQuant> getQuantListByUnit(Object unitCodeObj){
		WmUnit unit = new UnitController().getUnitByUnitCode(unitCodeObj);
		return new QuantManager().getQuantListByUnit(unit);
	}

	public HashMap<Integer, String> getArticleQtyMap(List<WmQuant> quantList)
	{
		HashMap<Integer, String> quantArticleMap = new HashMap<Integer, String>();
		for(WmQuant quant : quantList)
		{
			WmArticle article = new ArticleManager().getArticleById(quant.getArticleId());
			int qty = quantArticleMap.get(article.getArticleCode())==null?0:Integer.decode(quantArticleMap.get(article.getArticleCode()));
			if(qty !=0)
				quantArticleMap.remove(article.getArticleCode());

			quantArticleMap.put(article.getArticleId().intValue(),article.getArticleCode()+" : "+String.valueOf((quant.getQuantity().intValue()+qty)));
		}
		return quantArticleMap;
	}

	public HashMap<Integer, String> getArticleQtyMapByUnitObj(Object unitCodeObj){
		List<WmQuant> quantList =  getQuantListByUnit(unitCodeObj);
		return getArticleQtyMap(quantList);

	}

	public Long getSumQtyByBinArticle(WmBin bin, WmArticle article)
	{
	    String unitCode = new UnitManager().getUnitBinArticle(bin.getBinId(), article.getArticleId());
	    WmUnit unit = new UnitController().getUnitByUnitCode(unitCode);

	    Long sumQty = new QuantManager().getQuantityByUnitArticle(unit.getUnitId(), article.getArticleId());
		if(sumQty==null){
			return 0L;
		}

		return sumQty;
	}

	public WmQuant copyQuant(WmQuant srcQuant, Long userId, Long destUnitId){

		WmQuant q = new WmQuant();
		q.setQuantId(new QuantManager().getNextQuantId());
		q.setCreateUser(userId);
		q.setCreateDate(new Timestamp(System.currentTimeMillis()));
		q.setQualityStateId(srcQuant.getQualityStateId());
		q.setClientId(srcQuant.getClientId());
		q.setArticleId(srcQuant.getArticleId());
		q.setSkuId(srcQuant.getSkuId());
		q.setUnitId(destUnitId);
		q.setLotId(srcQuant.getLotId());
		q.setQuantity(0L);
		return q;
	}

	/**@deprecated
	 * */
	public WmQuant getWmQuant(Long unitId) {
		return new QuantManager().getQuantByUnitId(unitId);
	}

	public WmQuant getQuantByQuantId(Long quantId){
		return new QuantManager().getQuantById(quantId);
	}


	public boolean isEmptyPickPlace(String articleCode, String srcUnitCode ){

		WmUnit unit = null;
		try {
			unit = new UnitManager().getUnitByCode(srcUnitCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		WmArticle article = new ArticleManager().getArticleByCode(articleCode);

		WmQuant quant = new QuantManager().getQuantByUnitArticle(unit, article);
		return (!(quant instanceof WmQuant));

	}


	public WmBin getBinByQuantId(Long quantId){
		WmQuant quant = getQuantByQuantId(quantId);
		if(!(quant instanceof WmQuant)) return null;

		WmBin bin = new UnitManager().getBinByUnitId(quant.getUnitId());
		if(!(bin instanceof WmBin)) return null;

		return bin;
	}

	public WmUnit getUnitByQuantId(Long quantId){
		WmQuant quant = getQuantByQuantId(quantId);
		return new UnitManager().getUnitById(quant.getUnitId());
	}

	/**@deprecated
	 * */
	public WmQuant getQuantByUnitArticle(String unitCode, String articleCode){
		WmQuant quant = null;
		QuantManager quantMgr = new QuantManager();

		//Long unitId = new UnitController().getUnitId(unitCode);
		//Long articleId = new ArticleManager().getArticleByCode(articleCode).getArticleId();

		//WmUnit unit = new UnitManager().getUnitByCode(unitCode);
		WmUnit unit = new UnitController().getUnitByUnitCode(unitCode);
		//WmArticle article = new ArticleManager().getArticleByCode(articleCode);
		WmArticle article = new ArticleController().getArticleByCode(articleCode);

		quant = quantMgr.getQuantByUnitArticle(unit, article);

		if(quant != null)
			quant.setQuantity(quantMgr.getQuantityByUnitArticle(unit.getUnitId(), article.getArticleId()));

		return quant;
	}

	public WmQuant getQuantByUnitArticle(WmUnit unit, WmArticle article)
	{
		WmQuant quant = new QuantManager().getQuantByUnitArticle(unit, article);
		if(!(quant instanceof WmQuant)) return null;

		return quant;
	}


	public VQuantInfoShort getVQuantInfoShort(Long quantId) {
		return new VQuantInfoShortManager().getQuantByUnitId(quantId);
	}

	public List<VQuantInfoShort> getVQuantInfoListByArticle(String articleCode) {

		return new VQuantInfoShortManager().getListQuantInfo(articleCode);
	}

	public List<WmQuant> getQuantListByUnit(Long unitId){

		return new QuantManager().getQuantListByUnitId(unitId);
	}

	public List<VQuantInfoShort> getVQuantInfoListByUnit(String unitCode) {

		return new VQuantInfoShortManager().getListQuantInfoUnit(unitCode);
	}

	public boolean hasQuantInfoList(List<VQuantInfoShort> quantInfoList) {
		return (quantInfoList.size() > 0);
	}

	public int getPlacesCount(List<VQuantInfoShort> quantInfoList) {
		return quantInfoList.size();
	}

	public void delQuantByUnitCode(Session session, WmUser user, WmUnit unit)
	{
		new QuantManager().delQuantListByUnitId(session, unit, user);
		new UnitController().delUnitFromBin(session, unit, user);
	}

	public void delQuantByUnitCode(Session session, WmUser user, WmUnit unit, WmInventory invent)
	{
		List<WmInventoryPos> posList = new InventoryController().getPosList(user, unit, invent);

		for(WmInventoryPos pos : posList){
			if(pos.getQuantityAfter() == null || pos.getQuantityAfter().intValue() == 0) {
				WmArticle article  = new ArticleManager().getArticleById(pos.getArticleId());
				if(article instanceof WmArticle){
					new QuantManager().delQuantListByUnitId(session, unit, article, user);
					if(new UnitController().isEmpty(unit.getUnitId()))
						new UnitController().delUnitFromBin(session, unit, user);
					//А поддоны или ячейки на которых небыло товара нужно отметить как пустые !!!
				} else {
					new InventoryController().closePos(session, pos);
				}
			}
		}

		new InventoryController().canCloseInvent(session, invent);

		if(new QuantManager().getQuantListByUnit(unit).size()==0   /* Нет Quant by unitId*/){
			new UnitController().delUnitFromBin(session, unit, user);
		}


	}


	/**@deprecated
	 * */
	public void delQuantByUnitCode(Object userIdo, Object unitbarcode) {
		Long userId = Long.valueOf(String.valueOf(userIdo));
		Long unitId = new UnitController().getUnitId(String.valueOf(unitbarcode));

		new QuantManager().delQuantListByUnitId(unitId, userId);
		new UnitController().delUnitFromBin(unitbarcode, userIdo);
	}

	public void delQuantByUnitCode(Session session,Object userIdo, Object unitbarcode, EntryType entryTypeId) {
		/* Удаление всех квантов с поддона */
		Long userId = Long.valueOf(String.valueOf(userIdo));
		Long unitId = new UnitController().getUnitId(String.valueOf(unitbarcode));

		HistoryComment comment = new HistoryComment(entryTypeId);

		new QuantManager().delQuantListByUnitId(session, unitId, userId, comment.getDelQuantComment());
		new UnitController().delUnitFromBin(session, unitbarcode, userIdo, comment.getDelUnitBinComment());
	}

	public void delQuantByQuantId(Object quantId, Object userid) {

		QuantManager quantMgr = new QuantManager();
		Long quantIdL = Long.valueOf(String.valueOf(quantId));
		Long userIdL = Long.valueOf(String.valueOf(userid));

		WmQuant quant = quantMgr.getQuantById(quantIdL);

		Session session = HibernateUtil.getSession();
		Transaction trn = session.getTransaction();
		try{
			trn.begin();
			new HistoryController().addEntry(session, userIdL, quant.getUnitId(), DefaultValue.INVENTORY+"."+DefaultValue.DEL_QUANT, quant);
			quantMgr.delQuant(session, quant);
	 	trn.commit();
		}catch(Exception e){
			trn.rollback();
			session.close();
			e.printStackTrace();
		}
	}

	/**
	 * @deprecated
	 */
	public void delQuantByUnitId(Long userId, Long unitId, String comment) {
		// new HistoryController().addEntry(userId, unitId, comment);
		new QuantManager().delQuantListByUnitId(unitId, userId);

	}

	public WmQuant addOrCreateQuant(Session session, Long articleId, Long skuId, Long lotId, Long qualityStateId, String boxBarcode,
			Long quantity, Long clientId, Long unitId, Long adviceId, Long advicePosId, Long userId) {

		QuantManager mgr = new QuantManager();
		WmQuant quant = new WmQuant();
		quant.setQuantId(Long.valueOf(mgr.getNextQuantId()));
		quant.setArticleId(articleId);
		quant.setSkuId(skuId);
		quant.setLotId(lotId);
		quant.setQualityStateId(qualityStateId);
		quant.setBoxBarcode(boxBarcode);
		quant.setQuantity(quantity);
		quant.setClientId(clientId);
		quant.setUnitId(unitId);
		quant.setAdviceId(adviceId);
		quant.setAdvicePosId(advicePosId);
		quant.setNeedCheck(false);
		quant.setCreateDate(new Timestamp(System.currentTimeMillis()));
		quant.setCreateUser(userId);

		List<WmQuant> quantList = mgr.getQuantListByUnitId(unitId);
		for(WmQuant qnt : quantList){
			if(mgr.equals(quant, qnt)){
				//mgr.addQuantityToQuant(qnt, quant.getQuantity());
				qnt.setQuantity(qnt.getQuantity()+quant.getQuantity());
				session.persist(qnt);
				return qnt;
			}
		}

		//mgr.createQuant(quant);
		session.persist(quant);
		return quant;

	}


	public String createQuant(HttpSession httpSession, String historyComment)
	{
		System.out.println("Использована последняя версия метода!");

		WmUser user = new UserController().getUser(httpSession);
		if(!(user instanceof WmUser)){
			return ErrorMessage.WRONG_USER.message(new ArrayList<String>(Arrays.asList("")));
		}

		WmUnit unit = new UnitController().getUnit(httpSession);
		if(!(unit instanceof WmUnit)){
			return ErrorMessage.BC_NOT_UNIT.message(new ArrayList<String>(Arrays.asList(httpSession.getAttribute("unitcode").toString())));
		}

		WmBin bin = new BinController().getBin(httpSession);
		if(!(bin instanceof WmBin)){
			return ErrorMessage.WRONG_BIN.message(new ArrayList<String>(Arrays.asList(httpSession.getAttribute("bincode").toString())));
		}

		WmArticle article = new ArticleController().getArticle(httpSession);
		if(!(article instanceof WmArticle)){
			return ErrorMessage.WRONG_ART.message(new ArrayList<String>(Arrays.asList(httpSession.getAttribute("articleid").toString())));
		}

		WmSku sku = new SkuController().getSku(httpSession);
		if(!(sku instanceof WmSku)){
			return ErrorMessage.WRONG_SKU.message(new ArrayList<String>(Arrays.asList(httpSession.getAttribute("skuid").toString())));
		}

		WmQualityState state = new QualityStateController().getState(httpSession);
		if(!(state instanceof WmQualityState)){
			return ErrorMessage.WRONG_STATE.message(new ArrayList<String>(Arrays.asList(httpSession.getAttribute("state").toString())));
		}

		Long quantity = Long.valueOf(httpSession.getAttribute("quantity").toString());


		Session session  = HibernateUtil.getSession();
		Transaction t = session.getTransaction();
		t.begin();
		WmQuant quant = addOrCreateQuant(session, article.getArticleId() , sku.getSkuId(), null,
				state.getQualityStateId(), null, quantity,
				DefaultValue.DEFAULT_CLIENT_ID, unit.getUnitId(), null, null, user.getUserId());


		unit.setBinId(bin.getBinId());
		unit.setNeedCheck(false);
		session.persist(unit);

		new BinManager().setMakeCheck(session, bin);

		new HistoryController().addEntry(session, user.getUserId(), unit.getUnitId(), historyComment, quant);
		t.commit();
		session.close();

		return "";
	}

	/**
	 * @deprecated
	 * */
	public void createQuant(Object articleId, Object skuId, Object qualityStateId, Object quantity, Object unitIdObj,
			Object userId, String historyComment) {

		Long unitId = Long.valueOf(String.valueOf(unitIdObj));
		Long user = Long.valueOf(String.valueOf(userId));
		Session session  = HibernateUtil.getSession();
		Transaction t = session.getTransaction();
		t.begin();
		WmQuant quant = addOrCreateQuant(session, Long.valueOf(String.valueOf(articleId)), Long.valueOf(String.valueOf(skuId)), null,
				Long.valueOf(String.valueOf(qualityStateId)), null, Long.valueOf(String.valueOf(quantity)),
				DefaultValue.DEFAULT_CLIENT_ID, unitId, null, null, user);

		WmUnit unit = new UnitManager().getUnitById(unitId);
		new UnitManager().setMakeCheck(session, unit);

		if(unit.getBinId() != null){
			WmBin bin = new BinManager().getBinById(unit.getBinId());
			new BinManager().setMakeCheck(session, bin);
		}

		new HistoryController().addEntry(session, user, unit.getUnitId(), historyComment, quant);
		t.commit();
		session.close();
	}

	public boolean unitIsEmpty(String unitCode){
		List<WmQuant> quantList = new QuantManager().getQuantListByUnitId(new UnitController().getUnitId(unitCode));

		return ((quantList == null ? 0 : quantList.size()) < 1);
	}

	public List<WmQuant> getQuantList(Object unitIdo) {
		Long unitId = Long.valueOf(String.valueOf(unitIdo));
		return new QuantManager().getListWmQuant(unitId);
	}

	public void initQuantAttributes(HttpSession session, Long unitId) {
		VQuantInfoShort quantInfoShort = null;
		QuantController quantCtrl = new QuantController();

		// articlecount
		//List<WmQuant> quantList = quantCtrl.getQuantList(unitId);
		List<WmQuant> quantList = quantCtrl.getQuantListByUnit(unitId);
		session.setAttribute("articlecount", quantList.size());

		if (quantList.size() == 1) {
			WmQuant quant = quantList.get(0);
			if (quant != null) {
				quantInfoShort = quantCtrl.getVQuantInfoShort(quant.getQuantId());
				session.setAttribute("quantid", quant.getQuantId());
			}
			if (quantInfoShort != null) {
				session.setAttribute("articlecode", quantInfoShort.getArticleCode());
				session.setAttribute("articlename", quantInfoShort.getArtName());
				session.setAttribute("quantity", quantInfoShort.getQuantity());
				session.setAttribute("skuname", quantInfoShort.getSkuName());
				session.setAttribute("qualityname", quantInfoShort.getQualityName());
			}
		}

	}

	public Long getUnitQualityState(String unitbarcode, String userIdtext, String comment){
		WmUnit unit = new UnitController().getOrCreateUnit(HibernateUtil.getSession(), unitbarcode, null, userIdtext, comment);
		Long unitId = unit.getUnitId();
		List<WmQuant> quantList = new QuantManager().getListWmQuant(unitId);
		if(quantList == null) return null;
		return (quantList.isEmpty() ? null : quantList.get(0).getQualityStateId());
	}

	public int getCountArticleInUnit(String unitCode){

		Long unitId = new UnitController().getUnitId(unitCode);

		QuantManager qtyMgr = new QuantManager();
		int countArticleInPaller = qtyMgr.getCountArticleInUnit(unitId);

		//qtyMgr.closeSession();
		//unitMgr.closeSession();

		return countArticleInPaller;
	}

	public String getErrorText(String articleCode, String unitCode){

		return ErrorMessage.ARTICLE_NOT_FROM_UNIT.message(new ArrayList<String>(Arrays.asList(articleCode, unitCode)));
	}

}

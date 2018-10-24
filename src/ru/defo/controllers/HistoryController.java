package ru.defo.controllers;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.defo.managers.BinManager;
import ru.defo.managers.HistoryManager;
import ru.defo.managers.UnitManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmBin;
import ru.defo.model.WmCheck;
import ru.defo.model.WmHistory;
import ru.defo.model.WmOrder;
import ru.defo.model.WmOrderPos;
import ru.defo.model.WmPick;
import ru.defo.model.WmPreAdvice;
import ru.defo.model.WmQuant;
import ru.defo.model.WmSku;
import ru.defo.model.WmUnit;
import ru.defo.model.WmUser;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

public class HistoryController {
	private HistoryManager historyMgr;

	public HistoryController(){
		historyMgr = new HistoryManager();
	}

	private WmHistory initHistory(WmUser user){
		WmHistory h = new WmHistory();
		//h.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		h.setCreateUser(user.getUserId());
		h.setCreateDate(new Timestamp(System.currentTimeMillis()));
		return h;
	}

	public void addArticleChangeEntry(Session sesson, WmArticle article, WmUser user)
	{
		WmHistory h = new WmHistory();
		//h.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		h.setCreateDate(new Timestamp(System.currentTimeMillis()));
		h.setCreateUser(user.getUserId());
		h.setComment("Товар. Редактирование товара "+article.getArticleCode());
		h.setArticleId(article.getArticleId());
		sesson.persist(h);
	}

	public void addSkuChangeEntry(Session sesson, WmSku sku, WmUser user)
	{
		WmHistory h = new WmHistory();
		//h.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		h.setCreateDate(new Timestamp(System.currentTimeMillis()));
		h.setCreateUser(user.getUserId());
		h.setComment("Упаковка. Редактирование упаковки:: вес: "+sku.getWeight()+" длина: "+sku.getLength()+" ширина: "+sku.getWidth()+" высота: "+sku.getHeigh());
		h.setArticleId(sku.getArticleId());
		h.setSkuId(sku.getSkuId());
		sesson.persist(h);
	}

	public void addEntry_DelAdviceLink(Session sesson, WmUser user, WmPreAdvice preAdvice){
		WmHistory h = initHistory(user);
		h.setClientId(preAdvice.getClientId());
		h.setDocumentId(preAdvice.getAdviceId());
		h.setDocumentTypeId(preAdvice.getAdviceTypeId());
		h.setComment("Уделение складской приемки "+preAdvice.getWmAdviceLink()+" для ордера приход: " +preAdvice.getAdviceCode());
		sesson.persist(h);
	}

	public void createPickHistory(Session sesson, WmPick pick, WmOrder order, Long qty){
		WmHistory h = new WmHistory();
		//h.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		h.setCreateUser(pick.getCreateUser());
		h.setCreateDate(pick.getCreateDate());
		h.setClientId(order.getClientId());
		h.setArticleId(pick.getArticleId());
		h.setBinId(pick.getSourceBinId());
		h.setDocumentId(pick.getPickId());
		h.setDocumentTypeId(5L);
		h.setLotId(pick.getLotId());
		h.setQualityStateId(pick.getQualityStateId());
		h.setQuantity(-qty);
		h.setSkuId(pick.getSkuId());
		h.setUnitId(pick.getSourceUnitId());
		h.setComment("Подбор. из источника");
		sesson.persist(h);

		WmHistory d = new WmHistory();
		//d.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		d.setCreateUser(pick.getCreateUser());
		d.setCreateDate(pick.getCreateDate());
		d.setClientId(order.getClientId());
		d.setArticleId(pick.getArticleId());
		d.setDocumentId(pick.getPickId());
		d.setDocumentTypeId(5L);
		d.setLotId(pick.getLotId());
		d.setQualityStateId(pick.getQualityStateId());
		d.setQuantity(qty);
		d.setSkuId(pick.getSkuId());

		WmUnit unit = new UnitController().getUnitById(pick.getDestUnitId());

		d.setBinId(unit.getBinId()==null?null:unit.getBinId());
		d.setUnitId(pick.getDestUnitId());
		d.setComment("Подбор. на поддон");
		sesson.persist(d);

	}

	public void addEntryMarkNeedCheck(Session session, HttpSession sessionHTTP, WmBin bin, WmUnit unit, Long docTypeId, String comment){
		WmHistory h = new WmHistory();
		//h.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		h.setCreateUser(Long.valueOf(sessionHTTP.getAttribute("userid").toString()));
		h.setCreateDate(new Timestamp(System.currentTimeMillis()));
		h.setBinId(bin.getBinId());
		h.setUnitId(unit.getUnitId());
		h.setDocumentTypeId(docTypeId);
		if(sessionHTTP.getAttribute("articleid") != null) h.setArticleId(Long.valueOf(sessionHTTP.getAttribute("articleid").toString()));
		h.setComment(comment);
		session.persist(h);
	}

	public void addAuthorizationEntry(Long userId){
		 WmHistory wmHistory = new WmHistory();
		 //wmHistory.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		 wmHistory.setComment(DefaultValue.AUTHORIZATION_TEXT);
		 wmHistory.setCreateDate(new Timestamp(System.currentTimeMillis()));
		 wmHistory.setCreateUser(userId);
		 //historyMgr.insertHistory(wmHistory);
		 Session session = HibernateUtil.getSession();
		 Transaction trn = session.getTransaction();
		 try{
			 trn.begin();
			 session.persist(wmHistory);
			 trn.commit();
		 }catch(Exception e){
			 trn.rollback();
			 e.printStackTrace();
		 }finally{
			 if(session.isOpen()) session.close();
		 }
	}

	public void addLoadDataEntry(Session session, Long userId, String objectName, String keyValue)
	{
		WmHistory h = new WmHistory();
		//h.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		h.setCreateDate(new Timestamp(System.currentTimeMillis()));
		h.setCreateUser(userId);
		h.setComment(DefaultValue.LOAD_DATA_HISTORY+" : "+objectName+" = "+keyValue);
		session.persist(h);
	}


	public void addEntry(Object userIdObj, Object unitCodeObj, Object binCodeSrcObj, String comment){

		Long userId = Long.valueOf(userIdObj.toString());
		WmUnit unit = new UnitController().getUnitByUnitCode(unitCodeObj);
		WmBin bin = new BinController().getBin(binCodeSrcObj==null?null:binCodeSrcObj.toString());

		WmHistory wmHistory = new WmHistory();
		//wmHistory.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		wmHistory.setComment(comment);
		wmHistory.setCreateDate(new Timestamp(System.currentTimeMillis()));
		wmHistory.setCreateUser(userId);
		wmHistory.setBinId(bin==null?null:bin.getBinId());
		wmHistory.setUnitId(unit.getUnitId());
		historyMgr.insertHistory(wmHistory);
	}

	/**@deprecated
	 * */
	public void addEntry(Long userId, Long unitId, String comment){

		WmUnit unit = new UnitManager().getUnitById(unitId);

		 WmHistory wmHistory = new WmHistory();
		 //wmHistory.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		 wmHistory.setComment(comment);
		 wmHistory.setCreateDate(new Timestamp(System.currentTimeMillis()));
		 wmHistory.setCreateUser(userId);
		 wmHistory.setUnitId(unit.getUnitId());
		 wmHistory.setBinId(unit.getBinId());
		 //historyMgr.insertHistory(wmHistory);
		 HibernateUtil.getSession().persist(wmHistory);
	}

	public void addEntry(Session session, Long userId, Long unitId, String comment){
		WmUnit unit = new UnitManager().getUnitById(unitId);

		 WmHistory wmHistory = new WmHistory();
		 //wmHistory.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		 wmHistory.setComment(comment);
		 wmHistory.setCreateDate(new Timestamp(System.currentTimeMillis()));
		 wmHistory.setCreateUser(userId);
		 wmHistory.setUnitId(unit.getUnitId());
		 wmHistory.setBinId(unit.getBinId());
		 session.persist(wmHistory);
	}

	public void addEntry(Session session, WmUser user, WmUnit unit, WmBin bin, String comment){
		WmHistory wmHistory = new WmHistory();
		//wmHistory.setHistoryId(Long.valueOf(new HistoryManager().getNextHistoryId()));
		wmHistory.setComment(comment);
		wmHistory.setCreateDate(new Timestamp(System.currentTimeMillis()));
		wmHistory.setCreateUser(user.getUserId());
		wmHistory.setUnitId(unit.getUnitId());
		if(bin != null) wmHistory.setBinId(bin.getBinId());
		session.persist(wmHistory);
	}

	/**@deprecated
	 * @see addEntry(Session session, WmUser user, WmUnit unit, WmBin bin, String comment)
	 * */
	public void addEntry(Session session, Long userId, WmUnit unit, WmBin bin, String comment)
	{
		WmHistory wmHistory = new WmHistory();
		//wmHistory.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		wmHistory.setComment(comment);
		wmHistory.setCreateDate(new Timestamp(System.currentTimeMillis()));
		wmHistory.setCreateUser(userId);
		wmHistory.setUnitId(unit.getUnitId());
		if(bin != null) wmHistory.setBinId(bin.getBinId());
		//historyMgr.insertHistory(wmHistory);
		session.persist(wmHistory);

	}


	public WmHistory fillHistoryByPick(WmPick pick, String comment, Long userId){

		WmHistory history = new WmHistory();
		//history.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		history.setComment(comment);
		history.setCreateDate(new Timestamp(System.currentTimeMillis()));
		history.setCreateUser(userId);

		history.setArticleId(pick.getArticleId());
		history.setSkuId(pick.getSkuId());
		history.setBinId(pick.getSourceBinId());
		history.setUnitId(pick.getDestUnitId());
		history.setQualityStateId(pick.getQualityStateId());
		history.setQuantity(pick.getQuantity());
		history.setDocumentId(pick.getOrderId());

		return history;
	}

	public WmHistory fillHistoryByCheck(WmCheck check, String comment, Long userId){

		WmHistory history = new WmHistory();
		//history.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		history.setComment(comment);
		history.setCreateDate(new Timestamp(System.currentTimeMillis()));
		history.setCreateUser(userId);

		history.setArticleId(check.getArticleId());
		history.setSkuId(check.getSkuId());
		history.setUnitId(check.getUnitId());
		history.setQuantity(check.getScanQuantity());
		history.setDocumentId(check.getOrderId());

		return history;
	}

	public WmHistory fillHistoryByShpt(WmOrderPos pos, WmUnit unit, String comment, Long userId){

		WmHistory history = new WmHistory();
		history.setComment(comment);
		history.setCreateDate(new Timestamp(System.currentTimeMillis()));
		history.setCreateUser(userId);

		history.setArticleId(pos.getArticleId());
		history.setSkuId(pos.getSkuId());
		history.setUnitId(unit.getUnitId());
		history.setQuantity(pos.getFactQuantity());
		history.setDocumentId(pos.getOrderId());

		return history;
	}


	public void addEntryByPick(Session session, WmPick pick, String comment, Long userId){
		WmHistory history = fillHistoryByPick(pick, comment, userId);
		session.persist(history);
	}

	public void addEntryByCheck(Session session, WmCheck check, String comment, Long userId)
	{
		WmHistory history = fillHistoryByCheck(check, comment, userId);
		session.persist(history);
	}

	public void addEntryByShpt(Session session, WmOrderPos pos, WmUnit unit, String comment, Long userId)
	{
		WmHistory history = fillHistoryByShpt(pos, unit, comment, userId);
		session.persist(history);
	}

	/**@deprecated
	 * */
	public void addEntry(long userId, long unitId, String comment, WmQuant quant){
		addEntry(userId, unitId, comment, quant, quant.getQuantity());
	}

	public void addEntry(Session session, long userId, long unitId, String comment, WmQuant quant){
		addEntry(session, userId, unitId, comment, quant, quant.getQuantity());
	}

	/**@deprecated
	 * */
	public void addEntry(long userId, long unitId, String comment, WmQuant quant, Long quantity){
		 WmHistory wmHistory = new WmHistory();
		 //wmHistory.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		 wmHistory.setComment(comment);
		 wmHistory.setCreateDate(new Timestamp(System.currentTimeMillis()));
		 wmHistory.setCreateUser(Long.valueOf(userId));

		 WmUnit unit = new UnitManager().getUnitById(unitId);
		 if(unit != null)
		   wmHistory.setBinId(unit.getBinId());

		 wmHistory.setUnitId(Long.valueOf(unitId));

		 if(quant != null){
		   wmHistory.setQuantId(quant.getQuantId());
		   wmHistory.setClientId(quant.getClientId());
		   wmHistory.setArticleId(quant.getArticleId());
		   wmHistory.setSkuId(quant.getSkuId());
		   wmHistory.setQualityStateId(quant.getQualityStateId());
		   wmHistory.setLotId(quant.getLotId());
		   wmHistory.setQuantity(quantity);
		 }

		 historyMgr.insertHistory(wmHistory);
	}

	public void addEntry(Session session, long userId, long unitId, String comment, WmQuant quant, Long quantity){
		 WmHistory wmHistory = new WmHistory();
		 //wmHistory.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		 wmHistory.setComment(comment);
		 wmHistory.setCreateDate(new Timestamp(System.currentTimeMillis()));
		 wmHistory.setCreateUser(Long.valueOf(userId));

		 WmUnit unit = new UnitManager().getUnitById(unitId);
		 if(unit != null)
		   wmHistory.setBinId(unit.getBinId());

		 wmHistory.setUnitId(Long.valueOf(unitId));

		 if(quant != null){
		   wmHistory.setQuantId(quant.getQuantId());
		   wmHistory.setClientId(quant.getClientId());
		   wmHistory.setArticleId(quant.getArticleId());
		   wmHistory.setSkuId(quant.getSkuId());
		   wmHistory.setQualityStateId(quant.getQualityStateId());
		   wmHistory.setLotId(quant.getLotId());
		   wmHistory.setQuantity(quantity);
		 }

		 session.persist(wmHistory);
		 //historyMgr.insertHistory(wmHistory);
	}

	public void addAssignEntry(Long userId, Long articleId, Long skuId, String comment){
		WmHistory wmHistory = new WmHistory();
		//wmHistory.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		wmHistory.setComment(comment);
		wmHistory.setCreateDate(new Timestamp(System.currentTimeMillis()));
		wmHistory.setCreateUser(userId);
		wmHistory.setArticleId(articleId);
		wmHistory.setSkuId(skuId);
		historyMgr.insertHistory(wmHistory);

		//historyMgr.closeSession();
	}

	/*
	public void addBcPrintEntry(long userId, String comment){
		 WmHistory wmHistory = new WmHistory();
		 wmHistory.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		 wmHistory.setComment(comment);
		 wmHistory.setCreateDate(new Timestamp(System.currentTimeMillis()));
		 wmHistory.setCreateUser(Long.valueOf(userId));
		 historyMgr.insertHistory(wmHistory);
	}
	*/

	public void addBcPrintEntry(Session session, long userId, String comment){
		 WmHistory wmHistory = new WmHistory();
		 //wmHistory.setHistoryId(Long.valueOf(historyMgr.getNextHistoryId()));
		 wmHistory.setComment(comment);
		 wmHistory.setCreateDate(new Timestamp(System.currentTimeMillis()));
		 wmHistory.setCreateUser(Long.valueOf(userId));
		 session.persist(wmHistory);
	}

	public String getLastBinByUnit(String unitCode){
		Long unitId = null;
		try{
			unitId = new UnitManager().getUnitByCode(unitCode).getUnitId();
		} catch(Exception e){
			e.printStackTrace();
		}

		Long binId = historyMgr.getLastBinIdByUnitId(unitId);
		if(binId instanceof Long == false) return null;
		return new BinManager().getBinById(binId).getBinCode();
	}

	public void addAdviceStartEntry(Long userId, Long docId, Long docTypeId, Long binId,  String comment)
	{
		WmHistory history = new WmHistory();
		//history.setHistoryId(historyMgr.getNextHistoryId());
		history.setDocumentId(docId);
		history.setDocumentTypeId(docTypeId);
		history.setBinId(binId);
		history.setComment(comment);
		history.setCreateUser(userId);
		history.setCreateDate(new Timestamp(System.currentTimeMillis()));
		historyMgr.insertHistory(history);

	}

	public void addAdvicePosEntry(Long userId, Long docTypeId, Long binId, WmQuant quant, String comment)
	{
		WmHistory history = new WmHistory();
		//history.setHistoryId(historyMgr.getNextHistoryId());
		//history.setHistoryId(historyMgr.getNextHistoryId());
		history.setDocumentId(quant.getAdviceId());
		history.setDocumentTypeId(docTypeId);
		history.setBinId(binId);
		history.setUnitId(quant.getUnitId());
		history.setQuantId(quant.getQuantId());
		history.setArticleId(quant.getArticleId());
		history.setSkuId(quant.getSkuId());
		history.setQualityStateId(quant.getQualityStateId());
		history.setLotId(quant.getLotId());
		history.setQuantity(quant.getQuantity());
		history.setComment(comment);
		history.setCreateUser(userId);
		history.setCreateDate(new Timestamp(System.currentTimeMillis()));
		historyMgr.insertHistory(history);

	}


}

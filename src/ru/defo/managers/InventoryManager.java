package ru.defo.managers;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.BinController;
import ru.defo.controllers.InventoryController;
import ru.defo.controllers.SkuController;
import ru.defo.controllers.StatusController;
import ru.defo.controllers.UnitController;
import ru.defo.controllers.UserController;
import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmArticle;
import ru.defo.model.WmBin;
import ru.defo.model.WmInventory;
import ru.defo.model.WmInventoryInitiator;
import ru.defo.model.WmInventoryPos;
import ru.defo.model.WmSku;
import ru.defo.model.WmUnit;
import ru.defo.model.WmUser;
import ru.defo.model.local.HostSku;
import ru.defo.servlets.Inventory;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

public class InventoryManager extends ManagerTemplate {
	public InventoryManager(){ super(Inventory.class); }

	public List<WmInventoryPos> getOpenPosList(WmInventory invent)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("inventoryId", invent.getInventoryId().toString(), "eq");
		flt.addFilter("statusId", "3", "le");

		return HibernateUtil.getObjectList(WmInventoryPos.class, flt.getFilterList(), 0, false, "inventoryId,inventoryPosId");
	}

	public List<WmInventoryPos> getOpenPosListByUnit(WmUnit unit)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("unitId", unit.getUnitId().toString(), "eq");
		flt.addFilter("statusId", "3", "le");
		return HibernateUtil.getObjectList(WmInventoryPos.class, flt.getFilterList(), 0, false, "inventoryId,inventoryPosId");
	}


	public List<WmInventoryPos> getUnconfirmedPosList(WmInventory invent, WmUser user, WmBin bin, WmUnit unit, int rowCount)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("inventoryId", invent.getInventoryId().toString(), "eq");
		flt.addFilter("executorId", user.getUserId().toString(), "eq");
		if(bin instanceof WmBin)flt.addFilter("binId", bin.getBinId().toString(), "eq");
		flt.addFilter("unitId", unit.getUnitId().toString(), "eq");
		flt.addFilter("quantityAfter", "null", "isnull");
		flt.addFilter("statusId", "3", "le");
		List<WmInventoryPos> inventList = HibernateUtil.getObjectList(WmInventoryPos.class, flt.getFilterList(), rowCount, false, "inventoryId,inventoryPosId");

		return inventList;
	}

	public List<WmInventoryPos> getPosListByUser(WmUser user){

		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("executorId", user.getUserId().toString(), "eq");
		flt.addFilter("quantityAfter", "null", "isnull");
		flt.addFilter("statusId", "3", "le");
		List<WmInventoryPos> inventList = HibernateUtil.getObjectList(WmInventoryPos.class, flt.getFilterList(), 1, false, "inventoryId,inventoryPosId");

		return inventList;
	}

	public WmInventoryPos getCurrentActivePos(WmUser user)
	{
		List<WmInventoryPos> posList = getPosListByUser(user);
		if(posList.size()==0) return null;
		return posList.get(0);
	}


	public WmInventory getInventById(Long inventId){
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("inventoryId", inventId));
		return (WmInventory)HibernateUtil.getUniqObject(WmInventory.class, restList, false);
	}

	public List<WmInventory> getInventoryListByFilter(String inventFlt, String initerFlt, String dateFlt,
			String creatorFlt, String statusFlt, String hidehost, int rowCnt,
			String areaCodeFlt, String rackFromFlt, String rackToFlt, String levelFromFlt, String levelToFlt, String binFromFlt, String binToFlt )
	{
		CriterionFilter flt = new CriterionFilter();
		if(!inventFlt.isEmpty()) flt.addFilter("inventoryId", inventFlt, "eq");
		if(!dateFlt.isEmpty()) flt.addFilter("createDate", dateFlt, "date");
		if(!statusFlt.isEmpty()) flt.addFilter("statusId", statusFlt, "eq");
		if(hidehost.equals("true")) flt.addFilter("inventoryType", DefaultValue.INITIATOR_WMS.toString(), "eq");

		if(!areaCodeFlt.isEmpty()) flt.addFilter("areaCodeFilter", areaCodeFlt, "eq");
		if(!rackFromFlt.isEmpty()) flt.addFilter("rackFromFilter", rackFromFlt, "eq");
		if(!rackToFlt.isEmpty()) flt.addFilter("rackToFilter", rackToFlt, "eq");
		if(!levelFromFlt.isEmpty()) flt.addFilter("levelFromFilter", levelFromFlt, "eq");
		if(!levelToFlt.isEmpty()) flt.addFilter("levelToFilter", levelToFlt, "eq");
		if(!binFromFlt.isEmpty()) flt.addFilter("binFromFilter", binFromFlt, "eq");
		if(!binToFlt.isEmpty()) flt.addFilter("binToFilter", binToFlt, "eq");

		if(!initerFlt.isEmpty()){
			WmInventoryInitiator initor = new InventoryController().getInitiator(initerFlt);
			flt.addFilter("initiatorId", initor.getInitiatorId().toString(), "eq");
		}

		List<WmInventory> invList = HibernateUtil.getObjectList(WmInventory.class, flt.getFilterList(), 0, false, "inventoryId");
		return invList;
	}

	public WmInventoryPos getInventoryPos(Long inventId, Long posId)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("inventoryId", inventId));
		restList.add(Restrictions.eq("inventoryPosId", posId));
		WmInventoryPos pos = (WmInventoryPos)HibernateUtil.getUniqObject(WmInventoryPos.class, restList, false);

		return pos;
	}

	public List<WmInventory> getInventoryListByType(Long inventoryType)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("inventoryType", inventoryType.toString(), "eq");
		List<WmInventory> invList = HibernateUtil.getObjectList(WmInventory.class, flt.getFilterList(), 0, false, "inventoryId");
		return invList;
	}

	public List<WmInventoryPos> getInventoryPosListByFilter(WmInventory invent, HttpSession session)
	{
		CriterionFilter flt = new CriterionFilter();

		flt.addFilter("inventoryId", invent.getInventoryId().toString(), "eq");
		if(session.getAttribute("posflt") != null)
			flt.addFilter("inventoryPosId", session.getAttribute("posflt").toString(), "<>");

		if(!session.getAttribute("executorflt").toString().isEmpty()){
			String users = new UserController().getUserListStringByUserFlt(session.getAttribute("executorflt").toString());
			if(users.length()>0) flt.addFilter("executorId", users, "in");
		}

		if(!session.getAttribute("qualityflt").toString().isEmpty()){
			flt.addFilter("qualityStateId", session.getAttribute("qualityflt").toString(), "eq");
		}

		if(!session.getAttribute("statusflt").toString().isEmpty()){
			flt.addFilter("statusId", session.getAttribute("statusflt").toString(), "eq");
		}

		if(!session.getAttribute("beforeqtyflt").toString().isEmpty()){
			flt.addFilter("quantityBefore", session.getAttribute("beforeqtyflt").toString(), "<>");
		}

		if(!session.getAttribute("afterqtyflt").toString().isEmpty()){
			flt.addFilter("quantityAfter", session.getAttribute("afterqtyflt").toString(), "<>");
		}

		if(!session.getAttribute("binflt").toString().isEmpty())
		{
			String bins = new BinController().getBinListStringByBinFlt(session.getAttribute("binflt").toString());
			if(bins.length()>0) flt.addFilter("binId", bins, "in");
		}

		if(!session.getAttribute("unitflt").toString().isEmpty()){
			WmUnit unit = new UnitController().getUnitByUnitCode(session.getAttribute("unitflt"));
			if(unit instanceof WmUnit) flt.addFilter("unitId", unit.getUnitId().toString(), "eq");
		}

		if(!session.getAttribute("artcodeflt").toString().isEmpty())
			flt.addFilter("articleCode", session.getAttribute("artcodeflt").toString(), "like");

		if(!session.getAttribute("artdescrflt").toString().isEmpty())
			flt.addFilter("articleDescript", session.getAttribute("artdescrflt").toString(), "like");

		List<WmInventoryPos> posList = HibernateUtil.getObjectList(WmInventoryPos.class, flt.getFilterList(), 0, false, "inventoryPosId");
		return posList;
	}

	public List<WmInventoryPos> getPosList(WmInventory invent, WmBin bin, WmUnit unit, WmArticle article)
	{
		CriterionFilter flt = new CriterionFilter();
										 flt.addFilter("inventoryId", invent.getInventoryId().toString(), "eq");
		if(bin instanceof WmBin) 		 flt.addFilter("binId", bin.getBinId().toString(), "eq");
		if(unit instanceof WmUnit) 		 flt.addFilter("unitId", unit.getUnitId().toString(), "eq");
		if(article instanceof WmArticle) flt.addFilter("articleId", article.getArticleId().toString(), "eq");

		return HibernateUtil.getObjectList(WmInventoryPos.class, flt.getFilterList(), 0, false, "inventoryId,inventoryPosId");
	}


	public List<WmInventoryPos> getPosList(WmInventory invent, WmBin bin, WmUnit unit)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("inventoryId", invent.getInventoryId().toString(), "eq");
		flt.addFilter("binId", bin.getBinId().toString(), "eq");
		flt.addFilter("unitId", unit.getUnitId().toString(), "eq");
		return HibernateUtil.getObjectList(WmInventoryPos.class, flt.getFilterList(), 0, false, "inventoryPosId");
	}

	/**@deprecated
	 * @see getPosList(WmInventory invent, WmBin bin, WmUnit unit)
	 * */
	public List<WmInventoryPos> getPosList(WmInventory invent, WmBin bin)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("inventoryId", invent.getInventoryId().toString(), "eq");
		flt.addFilter("binId", bin.getBinId().toString(), "eq");
		return HibernateUtil.getObjectList(WmInventoryPos.class, flt.getFilterList(), 0, false, "inventoryPosId");
	}


	public List<WmInventoryPos> getInventoryPosListByWmInventory(WmInventory invent)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("inventoryId", invent.getInventoryId().toString(), "eq");
		List<WmInventoryPos> posList = HibernateUtil.getObjectList(WmInventoryPos.class, flt.getFilterList(), 0, false, "inventoryPosId", true);
		return posList;
	}

	public List<WmInventoryPos> getInventoryPosListByWmInventory(WmInventory invent, WmArticle article)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("inventoryId", invent.getInventoryId().toString(), "eq");
		flt.addFilter("articleId", article.getArticleId().toString(), "eq");
		List<WmInventoryPos> posList = HibernateUtil.getObjectList(WmInventoryPos.class, flt.getFilterList(), 0, false, "inventoryPosId");
		return posList;
	}


	public boolean eraseInventory()
	{
		List<WmInventoryPos> posList = new ArrayList<WmInventoryPos>();

		List<WmInventory> invList = getInventoryListByType(DefaultValue.INITIATOR_HOST);
		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();

		try{
			tx.begin();

		for(WmInventory invent : invList)
		{
			posList = getInventoryPosListByWmInventory(invent);
			for(WmInventoryPos pos : posList)
			{
				session.delete(pos);
			}
			session.delete(invent);
		}
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


	/**
	 * 		Delete all Data from WmInventory & WmInventoryPos's
	 *  @deprecated
	 *  @see eraseInventory()
	 * */
	public boolean eraseInventoryData(){

		boolean result = false;
		Session session = HibernateUtil.getSession();

		try{
			session.getTransaction().begin();

			@SuppressWarnings("unchecked")
			List<WmInventoryPos> invPosList = (List<WmInventoryPos>) session.createCriteria(WmInventoryPos.class).list();
			if(invPosList != null){
				delInventoryPosList(session, invPosList);
			}

			@SuppressWarnings("unchecked")
			List<WmInventory> invList = (List<WmInventory>) session.createCriteria(WmInventory.class).list();
			if(invList != null){
				delInventoryList(session, invList);
			}

			session.getTransaction().commit();
			result = true;
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally{
			session.close();
		}

		return result;

	}

	/** @deprecated */
	private void delInventoryList(Session session, List<WmInventory> invList){
		for(WmInventory invent : invList){
			session.delete(invent);
		}
	}

	/** @deprecated */
	private void delInventoryPosList(Session session, List<WmInventoryPos> invPosList){
		for(WmInventoryPos invPos : invPosList){
			session.delete(invPos);
		}
	}


	public WmInventory getInventoryByDate(Timestamp createDate){
		session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(WmInventory.class);
		criteria.add(Restrictions.eq("inventoryType", DefaultValue.INITIATOR_HOST));
		criteria.add(Restrictions.ge("createDate", createDate));
		criteria.setMaxResults(1);

		WmInventory invent = (WmInventory) criteria.uniqueResult();
		if(invent instanceof WmInventory) return invent;

		return null;
	}

	public WmInventory createInventory(Long inventoryType, WmUser user)
	{
		WmInventory invent = new WmInventory();
		invent.setInventoryId(getNextInventId());
		invent.setCreateDate(new Timestamp(System.currentTimeMillis()));
		invent.setCreateUser(user.getUserId());
		invent.setStatusId(DefaultValue.STATUS_CREATED);
		invent.setInitiatorId(DefaultValue.INITIATOR_WMS);
		invent.setInventoryType(DefaultValue.INITIATOR_WMS);


		return invent;
	}

	public WmInventoryPos createInventoryPos(WmInventory invent, long lastPosId, WmBin bin, WmUnit unit, WmSku sku, Long quantityBefore)
	{
		WmInventoryPos pos = new WmInventoryPos();
		pos.setInventoryId(invent.getInventoryId());
		pos.setInventoryPosId(Long.valueOf(lastPosId));
		pos.setStatusId(1L);
		if(bin != null) pos.setBinId(bin.getBinId());

		if(unit != null){
			pos.setUnitId(unit.getUnitId());
			if(pos.getBinId()==null) pos.setBinId(unit.getBinId());
		}

		if(sku != null)
		{
			pos.setSkuId(sku.getSkuId());
			pos.setArticleId(sku.getArticleId());

			WmArticle article = new ArticleManager().getArticleById(sku.getArticleId());
			if(article instanceof WmArticle){
				pos.setArticleCode(article.getArticleCode());
				pos.setArticleDescript(article.getDescription());
			}

		}

		if(quantityBefore != null)pos.setQuantityBefore(quantityBefore);

		pos.setCreateUser(invent.getCreateUser());
		pos.setCreateDate(invent.getCreateDate());

		return pos;
	}


	public WmInventory createInventoryToday(Long initiatorId)
	{
		WmInventory invent = getInventoryByDate(AppUtil.getTodayTamestamp());
		if(!(invent instanceof WmInventory)) {
			invent = new WmInventory();
			invent.setInventoryId(getNextInventId());
			invent.setCreateDate(new Timestamp(System.currentTimeMillis()));
			invent.setStatusId(StatusController.getFirstStatus());
			invent.setInitiatorId(initiatorId);
			invent.setInventoryType(DefaultValue.INITIATOR_HOST);
			return invent;

		}

		return null;
	}


	public WmInventoryPos getOrCreateInventPos(WmInventory invent, HostSku hostSku, long lastPosId){
		WmInventoryPos inventPos = new WmInventoryPos();
		inventPos.setInventoryId(invent.getInventoryId());
		inventPos.setInventoryPosId(Long.valueOf(++lastPosId)); // <----Строки!!!
		inventPos.setClientId(1L);
		inventPos.setCreateUser(invent.getCreateUser());
		inventPos.setCreateDate(invent.getCreateDate());
		inventPos.setArticleCode(hostSku.getArticleCode());
		inventPos.setArticleDescript(hostSku.getDescription());
		inventPos.setQuantityBefore(Double.valueOf(hostSku.getHostQuantity()).longValue());
		WmSku sku = new SkuController().getBaseSkuByArticleCode(hostSku.getArticleCode());
		inventPos.setArticleId(sku==null?null:sku.getArticleId());
		inventPos.setSkuId(sku==null?null:sku.getSkuId());

		return inventPos;
	}

	private long getNextInventId(){
		return ((BigInteger) HibernateUtil.getSession().createSQLQuery("select nextval('seq_inventory_id')").uniqueResult()).longValue();
	}

	public WmInventory getActualInventory(Long inventoryType){
		session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(WmInventory.class);
		criteria.add(Restrictions.eq("inventoryType", inventoryType));
		criteria.addOrder(Order.desc("createDate"));
		criteria.setMaxResults(1);

		WmInventory invent = (WmInventory) criteria.uniqueResult();
		if(invent instanceof WmInventory) return invent;

		return null;
	}


	public WmInventoryPos getActualInventPos(Long articleId){

		WmInventory invent = getActualInventory(DefaultValue.INITIATOR_HOST);

		session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(WmInventoryPos.class);
		criteria.add(Restrictions.eq("inventoryId", invent.getInventoryId()));
		criteria.add(Restrictions.eq("articleId", articleId));
		criteria.setMaxResults(1);

		WmInventoryPos inventPos = (WmInventoryPos) criteria.uniqueResult();
		if(inventPos instanceof WmInventoryPos) return inventPos;

		return new WmInventoryPos();
	}


	public List<WmInventory> getJobInventoryList(Long inventoryType){
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("inventoryType", inventoryType.toString(), "eq");
		flt.addFilter("createDate", AppUtil.getToday(), "date");
		List<WmInventory> invList = HibernateUtil.getObjectList(WmInventory.class, flt.getFilterList(), 0, false, "inventoryId", true);

		return invList;
	}


	public int getFactInventoryQty(long articleId)
	{
		WmArticle article = new ArticleController().getArticle(articleId);
		return getFactInventoryQty(article);
	}

	private int getPosSumQtyAfter(List<WmInventoryPos> posList)
	{
		int qty = 0;

		for(WmInventoryPos pos : posList){
			qty += pos.getQuantityAfter()==null?0:pos.getQuantityAfter().intValue();
		}
		return qty;
	}

	public int getFactInventoryQty(WmArticle article)
	{
		int i = 0;
		int[] compQty = new int[2];
		List<WmInventory> inventList = getJobInventoryList(DefaultValue.INITIATOR_WMS);

		for(WmInventory invent : inventList)
		{
			List<WmInventoryPos> posList = getInventoryPosListByWmInventory(invent, article);
			compQty[i] = getPosSumQtyAfter(posList);
			/* Если результат последних двух проверок не идентичен, результат не принимается! */
			if(i > 0) return (compQty[0]-compQty[1])==0?compQty[0]:0;
			i++;
		}

		return compQty[0];
	}



}

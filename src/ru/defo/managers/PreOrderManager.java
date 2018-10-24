package ru.defo.managers;

import java.math.BigInteger; 
import java.sql.Timestamp;
import java.util.ArrayList; 
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.controllers.HistoryController;
import ru.defo.controllers.PreOrderController;
import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmOrder;
import ru.defo.model.WmOrderPos;
import ru.defo.model.WmOrderType;
import ru.defo.model.WmPreOrder;
import ru.defo.model.WmPreOrderPos;
import ru.defo.model.WmRoute;
import ru.defo.model.WmStatus;
import ru.defo.model.views.Vpreorderpos;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;
import ru.defo.util.EANcontrolNum;
import ru.defo.util.HibernateUtil;

public class PreOrderManager extends ManagerTemplate {
	public PreOrderManager() {
		super(WmPreOrder.class);
	}

	public List<WmPreOrderPos> getPosByOrderId(WmPreOrder preOrder){

		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("orderId", preOrder.getOrderId().toString(), "eq");
		return HibernateUtil.getObjectList(WmPreOrderPos.class, flt.getFilterList(), 0, false, "orderPosId");
	}

	public List<WmPreOrder> getPreOrderListByOrder(WmOrder order){
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("wmOrderLink", order.getOrderId().toString(), "eq");
		return HibernateUtil.getObjectList(WmPreOrder.class, flt.getFilterList(), 0, false, "orderId");
	}

	public WmPreOrder getPreOrderById(Long orderId) {
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(WmPreOrder.class);
		criteria.add(Restrictions.eq("orderId", orderId));
		criteria.setMaxResults(1);
		WmPreOrder preOrder = (WmPreOrder) criteria.uniqueResult();
		//session.close();
		return preOrder;
	}

	public WmPreOrder getPreOrderByCode(String orderCode, boolean needClose) {
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(WmPreOrder.class);
		criteria.add(Restrictions.eq("orderCode", orderCode));
		criteria.setMaxResults(1);
		WmPreOrder preOrder = (WmPreOrder) criteria.uniqueResult();
		if(needClose)session.close();
		return preOrder;
	}

	public long getNextPreOrderId() {
		return ((BigInteger) session.createSQLQuery("select nextval('seq_pre_order_id')").uniqueResult()).longValue();
	}

	public void saveOrUpdatePreOrder(WmPreOrder preOrder) {
		Session session = HibernateUtil.getSession();
		session.getTransaction().begin();
		session.saveOrUpdate(preOrder);
		session.getTransaction().commit();
		session.close();
	}

	public void savePreOrder(WmPreOrder preOrder) {
		Session session = HibernateUtil.getSession();
		session.getTransaction().begin();
		session.persist(preOrder);
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<WmPreOrder> getPreOrderListByFilter(String shipmentflt, String clientdocflt, String dateflt,
			String typeflt, String statusflt, String wmsshptflt, String clientdescrflt, String routeflt, String rdateflt, String carflt,
			String carmarkflt, int rowCount) {

		List<WmPreOrder> wmPreOrdList = new ArrayList<WmPreOrder>();
		List<WmRoute> routeList = new ArrayList<WmRoute>();

		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria((WmPreOrder.class).getName());

		if (shipmentflt != null && !shipmentflt.isEmpty())
			criteria.add(Restrictions.ilike("orderCode", shipmentflt, MatchMode.ANYWHERE));

		if (clientdocflt != null && !clientdocflt.isEmpty())
			criteria.add(Restrictions.ilike("clientDocCode", clientdocflt, MatchMode.ANYWHERE));

		if (clientdescrflt != null &&  !clientdescrflt.isEmpty())
			criteria.add(Restrictions.ilike("clientDocDescription", clientdescrflt, MatchMode.ANYWHERE));

		if (!dateflt.isEmpty()) {
			criteria.add(Restrictions.ge("expectedDate", AppUtil.stringToTimestamp(dateflt)));
			criteria.add(Restrictions.lt("expectedDate", AppUtil.getNextDay(dateflt)));
		}

		if (!typeflt.isEmpty()) {
			criteria.add(Restrictions.eq("orderTypeId", Long.valueOf(typeflt)));
		}

		if (!statusflt.isEmpty()) {
			criteria.add(Restrictions.eq("statusId", Long.valueOf(statusflt)));
		}

		Criteria criteriaRoute = session.createCriteria((WmRoute.class).getName());

		if(!routeflt.isEmpty()){
			criteriaRoute.add(Restrictions.ilike("routeCode", routeflt, MatchMode.ANYWHERE));
			routeList = (List<WmRoute>)criteriaRoute.list();
			if(routeList.size() > 0){
				criteria.add(Restrictions.in("route", routeList.toArray()));
			} else {
				criteria.add(Restrictions.eq("route", null));
			}
		}

		if(!rdateflt.isEmpty()){
			criteriaRoute.add(Restrictions.ge("expectedDate", AppUtil.stringToTimestamp(rdateflt)));
			criteriaRoute.add(Restrictions.lt("expectedDate", AppUtil.getNextDay(rdateflt)));
			routeList = (List<WmRoute>)criteriaRoute.list();
			if(routeList.size() > 0){
				criteria.add(Restrictions.in("route", routeList.toArray()));
			} else {
				criteria.add(Restrictions.eq("route", null));
			}
		}



		/*
		 * if(!wmsshptflt.isEmpty()){
		 * criteria.add(Restrictions.eq("wmOrderLink",
		 * Long.valueOf(wmsshptflt))); }
		 */
		try {
			setFilter(criteria, "eq", "wmOrderLink", wmsshptflt);
		} catch (Exception e) {

			e.printStackTrace();
		}

		 criteria.addOrder(Order.asc("expectedDate"));

		if (rowCount > 0)
			criteria.setMaxResults(rowCount);

		try{
			wmPreOrdList = (List<WmPreOrder>) criteria.list();
		} catch(Exception e){
			e.printStackTrace();
		}


/*
		for(WmPreOrder preOrder : wmPreOrdList){
			session.refresh(preOrder);
		}
		*/
		//session.close();

		return wmPreOrdList;
	}

	 
	public List<WmOrderType> getOrderTypeList() {
		/*
		Criteria criteria = HibernateUtil.getSession().createCriteria(WmOrderType.class);
		criteria.addOrder(Order.asc("orderTypeId"));
		return (List<WmOrderType>) criteria.list();
		*/
		CriterionFilter filter = new CriterionFilter();
		return HibernateUtil.getObjectList(WmOrderType.class, filter.getFilterList(), 0, false,"");
	}

	public List<WmStatus> getStatusList() {
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(WmStatus.class);
		criteria.addOrder(Order.asc("statusId"));

		@SuppressWarnings("unchecked")
		List<WmStatus> statusList = (List<WmStatus>) criteria.list();

		//session.close();

		return statusList;
	}

	public void setWmOrderLink() {

	}

	public void addPreOrderToOrder(Long preOrderId, Long nextOrderId, Timestamp expDate) {
		// Проверка что нет связи с объединяющей отгрузкой
		WmPreOrder preOrder = getPreOrderById(preOrderId);
		if (preOrder != null && preOrder.getWmOrderLink() == null) {
			preOrder.setWmOrderLink(nextOrderId);

			//Session session = HibernateUtil.getSession();
			//session.getTransaction().begin();
			WmOrder order = new OrderManager().getOrderById(nextOrderId);
			if (order == null) {
				// Если объединяющая отгрузка еще не создана, то создать
				order = createOrder(preOrder, expDate);
			}
			//System.out.println("BEFORE LINES: "+new Date().toGMTString());
			List<WmPreOrderPos> preOrdPosList = new PreOrderPosManager().getOrderPosListById(preOrder.getOrderId(), false);
			//System.out.println("AFTER LINES: "+new Date().toGMTString());


			Session session = HibernateUtil.getSession();
			Transaction tx = session.getTransaction();
			try{
				tx.begin();
				/* add qty to OrderPos */
				addPreOrderLinesToOrderLines(session, preOrder, preOrdPosList, order);

				checkOrderQty(session,order);

				session.merge(order);
				session.merge(preOrder);
				tx.commit();
			} catch( Exception e)
			{
				tx.rollback();
				e.printStackTrace();
			} finally{
			//session.refresh(order);
				//session.flush();
		        session.clear();
				session.close();
			}

		}

	}

	public void checkOrderQty(Session session, WmOrder order)
	{
		int sumQty = 0;
		List<WmOrderPos> ordPosList = new OrderManager().getOrderPosListByOrderId(order.getOrderId(), false);
		for(WmOrderPos ordPos : ordPosList){
			sumQty = new PreOrderPosManager().getSumQtyPreOrdersByOrderPos(ordPos);
			if(ordPos.getExpectQuantity().intValue() != sumQty){
				ordPos.setErrorId(7L);
			}
		}

	}


	public void addPreOrderLinesToOrderLines(Session session, WmPreOrder preOrder, List<WmPreOrderPos> preOrdPosList, WmOrder order) {
		OrderPosManager ordPosMgr = new OrderPosManager();
		Timestamp ts = AppUtil.stringToTimestamp(AppUtil.getToday());

		try{
			for (WmPreOrderPos preOrdPos : preOrdPosList) {

				WmOrderPos orderPos0 = ordPosMgr.getOrderPosByArticleQState(preOrder.getWmOrderLink(),
						preOrdPos.getArticleId(), preOrdPos.getQualityStateId(), false);

				if (orderPos0 != null) {
					preOrdPos.setWmPosOrderLink(orderPos0.getOrderPosId());
					orderPos0.setExpectQuantity((orderPos0.getExpectQuantity() + preOrdPos.getExpectQuantity()));
					session.merge(preOrdPos);
					session.merge(orderPos0);
				} else {
					WmOrderPos ordPos = new WmOrderPos();
					ordPos.setOrderId(preOrder.getWmOrderLink());
					ordPos.setOrderPosId(new PreOrderPosManager().getNextOrderId());

					ordPos.setArticleId(preOrdPos.getArticleId());
					ordPos.setSkuId(preOrdPos.getSkuId());
					ordPos.setQualityStateId(preOrdPos.getQualityStateId());
					ordPos.setLotId(preOrdPos.getLotId());
					ordPos.setStatusId(1L);
					ordPos.setCreateDate(ts);
					ordPos.setErrorComment(preOrdPos.getErrorComment());
					ordPos.setExpectQuantity(preOrdPos.getExpectQuantity());

					preOrdPos.setWmPosOrderLink(ordPos.getOrderPosId());

					session.merge(preOrdPos);
					session.merge(ordPos);
				}

				/*if allPreOrderPos has WmOrderLinks set status for preOrder*/
				if(new PreOrderController().wmLinkedPreOrderPosAll(preOrdPosList)){
					preOrder.setStatusId(DefaultValue.STATUS_PREORDER_LINKED);
				}

				System.out.println("Pre Order Pos : " + preOrder.getOrderCode() + " / " + preOrdPos.getOrderId() + " / " + preOrdPos.getOrderPosId());
			}
		} catch(Exception e){
			preOrder.setErrorId(5L);
			preOrder.setErrorComment(new ErrorManager().getErrorById(5L).getDescription());
			e.printStackTrace();
		}

	}



	public WmPreOrder createOrUpdate(Session session, WmPreOrder preOrder)
	{
		WmPreOrder preOrder0 = getPreOrderByCode(preOrder.getOrderCode(), false);
		if(preOrder.getOrderCode() != null)
		{
			if(preOrder0 instanceof WmPreOrder)
			{
				if(!preOrder.equals(preOrder0) && preOrder0.getWmOrderLink()==null)
				{
					preOrder0.setRouteId(preOrder.getRouteId()==null?null:preOrder.getRouteId());
					preOrder0.setExpectedDate(preOrder.getExpectedDate());
					if(preOrder.getErrorId()!=null){
						preOrder0.setStatusId(10L);
					} else {
						preOrder0.setStatusId(preOrder.getStatusId()==null?1L:preOrder.getStatusId());
					}
					preOrder0.setClientDocDescription(preOrder.getClientDocDescription()==null?null:preOrder.getClientDocDescription());
					preOrder0.setErrorId(preOrder.getErrorId());
					preOrder0.setErrorComment(preOrder.getErrorComment());
				    session.update(preOrder0);
				}
			} else
			{
				preOrder0 = new WmPreOrder();
				preOrder0.setOrderId(getNextPreOrderId());
				preOrder0.setOrderCode(preOrder.getOrderCode());
				preOrder0.setRouteId(preOrder.getRouteId());
				if(preOrder.getErrorId()!=null){
					preOrder0.setStatusId(10L);
				} else {
					preOrder0.setStatusId(preOrder.getStatusId()==null?1L:preOrder.getStatusId());
				}
				preOrder0.setOrderTypeId(1L);
				preOrder0.setClientId(1L);
				preOrder0.setClientDocCode(preOrder.getClientDocCode());
				preOrder0.setClientDocDescription(preOrder.getClientDocDescription() );
				preOrder0.setExpectedDate(preOrder.getExpectedDate());
				preOrder0.setErrorComment(preOrder.getErrorComment()==null?"":preOrder.getErrorComment());
				preOrder0.setErrorId(preOrder.getErrorId());
				session.persist(preOrder0);
				new HistoryController().addLoadDataEntry(session, 0L, "WmPreOrder", preOrder0.getOrderId()+" ["+preOrder0.getOrderCode()+"]");
			}
		}

		return preOrder0;
	}

	public WmOrder createOrder(WmPreOrder preOrder, Timestamp expDate) {

		WmOrder order = new WmOrder();
		order.setOrderId(preOrder.getWmOrderLink());
		order.setClientId(preOrder.getClientId());
		order.setOrderTypeId(preOrder.getOrderTypeId());
		order.setExpectedDate(expDate);

		String bc = String.valueOf((222000000000L + preOrder.getWmOrderLink().longValue()));
		StringBuilder strBldr = new StringBuilder(bc);
		strBldr.append(EANcontrolNum.getControlNum(bc));

		order.setOrderCode(strBldr.toString());
		order.setStatusId(1L);

		return order;
	}

	public List<WmPreOrder> getPreOrderListByRoute(Long routeId){
		CriterionFilter filter = new CriterionFilter();
		if(routeId != null)
			filter.addFilter("routeId", routeId.toString(), "eq");
		List<WmPreOrder> preOrder = HibernateUtil.getObjectList(WmPreOrder.class, filter.getFilterList(), 0, true,"");

		return preOrder;
	}

	@SuppressWarnings("unchecked")
	public List<WmPreOrder> getPreOrderByWmOrderLinkFlt(String wmOrderLinkFlt) {

		Long wmOrderLink = Long.valueOf(wmOrderLinkFlt);

		Criteria criteria = HibernateUtil.getSession().createCriteria(WmPreOrder.class);
		criteria.add(Restrictions.eq("wmOrderLink", wmOrderLink));
		return (List<WmPreOrder>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<WmPreOrder> getPreOrderByWmOrderLink(Long wmOrderLink) {
		Criteria criteria = HibernateUtil.getSession().createCriteria(WmPreOrder.class);
		criteria.add(Restrictions.eq("wmOrderLink", wmOrderLink));
		return (List<WmPreOrder>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<WmPreOrderPos> getPreOrderPosByWmPosOrderLink(Long wmPosOrderLink) {
		Criteria criteria = HibernateUtil.getSession().createCriteria(WmPreOrderPos.class);
		criteria.add(Restrictions.eq("wmPosOrderLink", wmPosOrderLink));
		return (List<WmPreOrderPos>) criteria.list();
	}

	public String getClientDocDescription(Long wmOrderLink) {
		Criteria criteria = HibernateUtil.getSession().createCriteria(WmPreOrder.class);
		criteria.add(Restrictions.eq("wmOrderLink", wmOrderLink));
		criteria.addOrder(Order.asc("orderId"));
		criteria.setMaxResults(1);
		WmPreOrder preOrder = (WmPreOrder) criteria.uniqueResult();
		if(preOrder == null) return "";
		String clientDocDescription = preOrder.getClientDocDescription();
		return clientDocDescription == null ? "" : clientDocDescription;
	}

	public String getClientDocDescByPreOrderId(Long preOrderId){
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("orderId", preOrderId));
		WmPreOrder preOrder = (WmPreOrder)HibernateUtil.getUniqObject(WmPreOrder.class, restList, false);
		if(!(preOrder instanceof WmPreOrder)) return "";
		return preOrder.getClientDocDescription();
		/*
		Criteria criteria = HibernateUtil.getSession().createCriteria(WmPreOrder.class);
		criteria.add(Restrictions.eq("orderId", preOrderId));
		WmPreOrder preOrder = (WmPreOrder) criteria.uniqueResult();

		if(preOrder instanceof WmPreOrder) return preOrder.getClientDocDescription();

		return null;
		*/
	}

	@SuppressWarnings("unchecked")
	public List<Vpreorderpos> getVPreOrderPosList(Long orderId, String posflt, String articleidflt, String artdescrflt,
			String skuflt, String expqtyflt, String factqtyflt, String statusflt, String linkflt) {

		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(Vpreorderpos.class);
		criteria.add(Restrictions.eq("orderId", orderId));

		try {
			setFilter(criteria, "eq", "orderId", orderId);
			setFilter(criteria, "eq", "orderPosId", posflt);
			setFilter(criteria, "like", "articleCode", articleidflt);
			setFilter(criteria, "like", "articleName", artdescrflt);
			setFilter(criteria, "like", "skuName", skuflt);
			setFilter(criteria, "<>", "expectQuantity", expqtyflt);
			setFilter(criteria, "<>", "factQuantity", factqtyflt);
			setFilter(criteria, "eq", "statusId", statusflt);
			setFilter(criteria, "like", "wmPosOrderLink", linkflt);

		} catch (Exception e) {
			e.printStackTrace();
		}

		criteria.addOrder(Order.asc("articleCode"));

		List<Vpreorderpos> vOrderPosList = (List<Vpreorderpos>) criteria.list();
		for (Vpreorderpos orderPos : vOrderPosList) {
			session.refresh(orderPos);
		}

		return vOrderPosList;

	}

	public WmPreOrder initPreOrder(){
		WmPreOrder preOrder = new WmPreOrder();
		preOrder.setOrderId(Long.valueOf(getNextPreOrderId()));
		preOrder.setOrderTypeId(1L);
		preOrder.setStatusId(1L);
		preOrder.setClientId(1L);
		return preOrder;
	}


}

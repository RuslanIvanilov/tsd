package ru.defo.controllers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.defo.filters.CriterionFilter;
import ru.defo.managers.DeliveryManager;
import ru.defo.managers.OrderPosManager;
import ru.defo.managers.PreOrderManager;
import ru.defo.managers.PreOrderPosManager;
import ru.defo.model.WmOrderPos;
import ru.defo.model.WmOrderType;
import ru.defo.model.WmPreOrder;
import ru.defo.model.WmPreOrderPos;
import ru.defo.model.WmRoute;
import ru.defo.model.WmStatus;
import ru.defo.model.views.Vpreorderpos;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

public class PreOrderController {

	PreOrderManager preOrdMgr;
	PreOrderPosManager preOrdPosMgr;
	OrderPosManager ordPosMgr;

	public PreOrderController() {
		preOrdMgr = new PreOrderManager();
		preOrdPosMgr = new PreOrderPosManager();
		ordPosMgr = new OrderPosManager();
	}

	public List<WmPreOrder> getPreOrderListByLinkFlt(String wmOrderLInkFlt){
		System.out.println(" getPreOrderListByLinkFlt::wmOrderLInkFlt = ["+wmOrderLInkFlt+"]");
		return new PreOrderManager().getPreOrderByWmOrderLinkFlt(wmOrderLInkFlt);
	}

	public List<WmPreOrder> getPreOrderListByRoute(WmRoute route)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("routeId", route.getRouteId().toString(), "eq");
		List<WmPreOrder> preOrderList = HibernateUtil.getObjectList(WmPreOrder.class, flt.getFilterList(), 0, true, "orderId");

		return preOrderList;
	}

	public boolean wmLinkedPreOrderPosAll(List<WmPreOrderPos> preOrderPosList){

		for(WmPreOrderPos preOrderPos : preOrderPosList){
			if(preOrderPos.getWmPosOrderLink()==null) return false;
		}
		return true;
	}

	public WmPreOrder getPreOrderByOrderId(Long orderId) {
		return preOrdMgr.getPreOrderById(orderId);
	}

	public List<WmPreOrder> getPreOrderList(String shipmentflt, String clientdocflt, String dateflttext, String typeflt,
			String statusflt, String wmsshptflt, String clientdescrflt, String routeflt, String rdateflt, String carflt,
			String carmarkflt, String rowCount) {

		int rowCnt;

		try {
			rowCnt = Integer.decode(rowCount);
		} catch (Exception e) {
			e.printStackTrace();
			rowCnt = 0;
		}

		List<WmPreOrder> preOrderList = new PreOrderManager().getPreOrderListByFilter(shipmentflt, clientdocflt, dateflttext, typeflt, statusflt,
				wmsshptflt, clientdescrflt, routeflt, rdateflt, carflt, carmarkflt, rowCnt);

		if(preOrderList == null) return null;

		return preOrderList;
	}

	public List<WmOrderType> getOrderTypeList() {
		return preOrdMgr.getOrderTypeList();
	}

	public List<WmStatus> getStatusList() {
		return preOrdMgr.getStatusList();
	}

	public void createOrder(String preOrderIdTxt, Long nextOrderId, String expDate) {
		if (nextOrderId != null && !preOrderIdTxt.isEmpty() && !expDate.isEmpty()) {

			Long preOrderId = null;

			try {
				preOrderId = Long.decode(preOrderIdTxt);
			} catch (Exception e) {
				e.printStackTrace();
			}

			preOrdMgr.addPreOrderToOrder(preOrderId, nextOrderId, AppUtil.stringToTimestamp(expDate));
		}
	}

	public String getClientDocDescription(Long wmOrderLink) {

		return preOrdMgr.getClientDocDescription(wmOrderLink);
	}

	public String getClientDocDescription(String preOrderIdTxt) {
		Long preOrderId = Long.decode(preOrderIdTxt);
		if (!(preOrderId instanceof Long))
			return null;

		return preOrdMgr.getClientDocDescByPreOrderId(preOrderId);
	}

	public List<Vpreorderpos> getVPreOrderPosList(String orderIdTxt, String posflt, String articleidflt,
			String artdescrflt, String skuflt, String expqtyflt, String factqtyflt, String statusflt, String linkflt) {

		Long orderId = Long.decode(orderIdTxt);

		return preOrdMgr.getVPreOrderPosList(orderId, posflt, articleidflt, artdescrflt, skuflt, expqtyflt, factqtyflt,
				statusflt, linkflt);
	}

	public WmPreOrder getPreOrderById(Object orderIdObj){
		Long preOrderId = null;
		try{
			preOrderId = AppUtil.strToLong(orderIdObj.toString());
		}catch(NumberFormatException e){
			e.printStackTrace();
		}

		if(preOrderId ==null) return null;

		return getPreOrderById(preOrderId);
	}

	public WmPreOrder getPreOrderById(Long orderId) {
		WmPreOrder preOrder = preOrdMgr.getPreOrderById(orderId);
		if (!(preOrder instanceof WmPreOrder))
			return null;
		return preOrder;
	}

	public void createOrdersByRouteId(String routeIdTxt) {

		Long routeId = Long.valueOf(routeIdTxt);
		List<WmPreOrder> preOrderList = preOrdMgr.getPreOrderListByRoute(routeId);

		WmRoute route = new DeliveryManager().getById(routeId);

		Long newOrderId = new OrderController().getNextOrderId();

		for (WmPreOrder preOrder : preOrderList) {
			if(preOrder.getErrorId() == null)
				createOrder(preOrder.getOrderId().toString(), newOrderId, route.getExpectedDate().toString());
		}

	}

	private void clearWmOrderLink(WmPreOrderPos preOrderPos) {
		preOrderPos.setFactQuantity(null);
		preOrderPos.setWmPosOrderLink(null);
		// HibernateUtil.persist(preOrderPos);
		HibernateUtil.update(preOrderPos);
	}

	/**@deprecated
	 * */
	private void clearWmOrderLink(WmPreOrder preOrder) {
		preOrder.setWmOrderLink(null);
		HibernateUtil.persist(preOrder, false);

	}

	private void clearWmOrderLink(Session session, WmPreOrder preOrder) {
		preOrder.setWmOrderLink(null);
		preOrder.setStatusId(DefaultValue.STATUS_CREATED);
		session.merge(preOrder);

	}

	/**@deprecated
	 * */
	private void minusOrderPosExpectedQty(WmPreOrderPos preOrderPos, WmOrderPos orderPos) {
		orderPos.setFactQuantity(null);
		orderPos.setExpectQuantity(orderPos.getExpectQuantity() - preOrderPos.getExpectQuantity());
		if (orderPos.getExpectQuantity() < 1) {
			HibernateUtil.delete(orderPos);
		} else {
			HibernateUtil.persist(orderPos, false);
		}

		preOrderPos.setWmPosOrderLink(null);
		HibernateUtil.update(preOrderPos);

	}


	private void minusOrderPosExpectedQty(Session session, WmPreOrderPos preOrderPos, WmOrderPos orderPos) {
		orderPos.setFactQuantity(null);
		orderPos.setExpectQuantity(orderPos.getExpectQuantity() - preOrderPos.getExpectQuantity());
		if (orderPos.getExpectQuantity() < 1) {
			session.delete(orderPos);
		} else {
			session.persist(orderPos);
		}

		preOrderPos.setWmPosOrderLink(null);
		session.update(preOrderPos);

	}


	public void delOrderLink(Long orderId) {
		WmPreOrder preOrder = preOrdMgr.getPreOrderById(orderId);
		if (!(preOrder instanceof WmPreOrder) || preOrder.getWmOrderLink() == null)
			return;

		Session session = HibernateUtil.getSession();
		Transaction trn = session.getTransaction();

		try {
			trn.begin();

			List<WmPreOrderPos> preOrderPosList = preOrdPosMgr.getOrderPosListById(orderId, false);
			if (preOrderPosList != null) {
				for (WmPreOrderPos preOrderPos : preOrderPosList) {

					WmOrderPos orderPos = ordPosMgr.getOrderByPosId(preOrder.getWmOrderLink(),
							preOrderPos.getWmPosOrderLink());
					if (!(orderPos instanceof WmOrderPos))
						continue;

					minusOrderPosExpectedQty(session, preOrderPos, orderPos);
					// clearWmOrderLink(preOrderPos);
				}
			}
			clearWmOrderLink(session, preOrder);
			trn.commit();
		} catch (Exception e) {
			trn.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}

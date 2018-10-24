package ru.defo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.defo.filters.CriterionFilter;
import ru.defo.managers.DeliveryManager;
import ru.defo.managers.OrderManager;
import ru.defo.managers.PreOrderManager;
import ru.defo.model.WmOrder;
import ru.defo.model.WmPreOrder;
import ru.defo.model.WmRoute;
import ru.defo.util.HibernateUtil;

public class DeliveryController {

	public void deliveryPrinted(WmRoute route)
	{
		DeliveryManager delivMgr = new DeliveryManager();

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		try{
			tx.begin();
			delivMgr.setPrinted(session, route);
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			session.close();
		}/*finally{
			session.close();
		}*/
	}

	public WmRoute getRouteById(Long routeId){
		return new DeliveryManager().getById(routeId);
	}



	public List<WmRoute> getRouteListByDateFilter(String dateFilter){
		CriterionFilter flt = new CriterionFilter();
    	flt.addFilter("expectedDate", dateFilter, "date");
    	List<WmRoute> routeList = HibernateUtil.getObjectList(WmRoute.class, flt.getFilterList(), 0, true, "routeCode");

		return routeList;
	}


	public List<WmOrder> getOrderList(WmRoute route)
	{
		OrderManager orderMgr = new OrderManager();
		List<WmPreOrder> preOrderList = new PreOrderManager().getPreOrderListByRoute(route.getRouteId());

		List<WmOrder> orderList = new ArrayList<WmOrder>();

		for(WmPreOrder preOrder : preOrderList)
		{
			if(preOrder.getWmOrderLink()!= null){
				WmOrder order = orderMgr.getOrderById(preOrder.getWmOrderLink());
				if(order instanceof WmOrder) orderList.add(order);
			}
		}

		return orderList;
	}


}

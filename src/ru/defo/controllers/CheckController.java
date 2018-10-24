package ru.defo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;

import ru.defo.filters.CriterionFilter;
import ru.defo.managers.CheckManager;
import ru.defo.managers.OrderPosManager;
import ru.defo.managers.PreOrderManager;
import ru.defo.model.WmCheck;
import ru.defo.model.WmOrder; 
import ru.defo.model.WmPreOrder;
import ru.defo.model.WmUnit;
import ru.defo.model.views.Vcheck;
import ru.defo.util.ConfirmMessage;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

public class CheckController {

	public String getConfirmText(WmOrder order, WmUnit unit)
	{
		String confText = null;
		if(new CheckManager().checkPicked(order, unit))
			confText = ConfirmMessage.ASK_UNIT_RECONTROL.message(new ArrayList<String>(Arrays.asList(unit.getUnitCode(), order.getOrderCode())));

		return confText;
	}

	public void setDocsStatus(Session session, WmOrder order, Long statusId)
	{
		order.setStatusId(statusId);
		session.persist(order);

		List<WmPreOrder> preOrdList = new PreOrderManager().getPreOrderListByOrder(order);
		for(WmPreOrder preOrder : preOrdList)
		{
			preOrder.setStatusId(statusId);
			session.persist(preOrder);
		}

	}

	public List<Vcheck> getVcheckList(String orderIdTxt, String orderPosIdTxt, String unitFlt, String qtyFlt, String dateFlt, String surNameFlt, String firstNameFlt)
	{
		List<Vcheck> checkList = new CheckManager().getVcheckByFlt(orderIdTxt, orderPosIdTxt, unitFlt, qtyFlt, dateFlt, surNameFlt, firstNameFlt);
		if(checkList == null) return null;
		return checkList;
	}

	public void delCheckedUnit(Session session, WmOrder order, WmUnit unit, Long userId)
	{
		if(session == null){
			session = HibernateUtil.getSession();
		}

		OrderPosManager ordPosMgr = new OrderPosManager();
		List<WmCheck> checkList =   new CheckManager().getCheckByUnitOrder(order, unit);

		try{
		session.getTransaction().begin();
		for(WmCheck check : checkList){
			ordPosMgr.decreaseOrderPosQty(check);
			check.setScanQuantity(null);
			new HistoryController().addEntryByCheck(session, check, "Контроль Отмена. кол-во "+check.getScanQuantity(), userId);
			session.delete(check);
		}
		    setDocsStatus(session, order, DefaultValue.STATUS_PREORDER_LINKED);

			session.getTransaction().commit();
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			//session.close();
		} finally{
			session.close();
		}

	}

	public List<WmCheck> getCheckListByOrderUnit(WmOrder order, WmUnit unit)
	{
		CriterionFilter flt = new CriterionFilter();

		System.out.println("order: "+order.getOrderId() + " unit: "+unit.getUnitCode());

		if(order instanceof WmOrder){
			flt.addFilter("orderId", order.getOrderId().toString(), "eq");
		}

		if(unit instanceof WmUnit){
			flt.addFilter("unitId", unit.getUnitId().toString(), "eq");
		}

		List<WmCheck> checkList = HibernateUtil.getObjectList(WmCheck.class, flt.getFilterList(), 0, false, "");

		return checkList;
	}


}

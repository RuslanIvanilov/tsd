package ru.defo.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.filters.CriterionFilter;
import ru.defo.managers.CheckManager;
import ru.defo.managers.OrderManager;
import ru.defo.managers.OrderPosManager;
import ru.defo.managers.PickManager;
import ru.defo.managers.UnitManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmCheck;
import ru.defo.model.WmOrder;
import ru.defo.model.WmOrderPos;
import ru.defo.model.WmPick;
import ru.defo.model.WmQuant;
import ru.defo.model.WmUnit;
import ru.defo.model.local.ShptUnitsBalance;
import ru.defo.model.views.Vpick;
import ru.defo.util.AppUtil;
import ru.defo.util.ConfirmMessage;
import ru.defo.util.DefaultValue;
import ru.defo.util.EntryType;
import ru.defo.util.HibernateUtil;

public class PickController {

	public WmPick getPick(WmOrderPos orderPos, Long destUnitId){

		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("orderId", orderPos.getOrderId()));
		restList.add(Restrictions.eq("orderPosId", orderPos.getOrderPosId()));
		restList.add(Restrictions.eq("destUnitId", destUnitId));
		restList.add(Restrictions.eq("articleId", orderPos.getArticleId()));

		WmPick pick = (WmPick) HibernateUtil.getUniqObject(WmPick.class, restList, false);

		return pick;
	}



	public void splitQuantByOrderPos(Session session, WmOrderPos orderPos, WmUnit srcUnit, Long destUnitId, Long userId)
	{
		WmArticle article = new ArticleController().getArticle(orderPos.getArticleId());
		WmQuant srcQuant = new QuantController().getQuantByUnitArticle(srcUnit, article);
		if(srcQuant instanceof WmQuant)
		{
			srcQuant.setQuantity(new QuantController().getQuantByQuantId(srcQuant.getQuantId()).getQuantity().longValue()-1L);
			if(srcQuant.getQuantity().intValue()<1)
			{
				session.delete(srcQuant);
			} else {
				session.update(srcQuant);
			}

			WmUnit unit = new UnitController().getUnitById(destUnitId);
			WmQuant destQuant = new QuantController().getQuantByUnitArticle(unit, article);

			if(destQuant instanceof WmQuant){
				destQuant.setQuantity(destQuant.getQuantity()+1);
				session.update(destQuant);
			} else {
			 destQuant = new QuantController().copyQuant(srcQuant, userId, destUnitId);
			 destQuant.setQuantity(destQuant.getQuantity()+1);
			 destQuant.setQualityStateId(1L);
			 session.persist(destQuant);
			}

		}

	}


	public void addPickSKU(WmOrderPos orderPos, Long srcUnitId, Long destUnitId, Long userId){

		Session session = HibernateUtil.getSession();
		Transaction trn = session.getTransaction();

		WmUnit srcUnit = new UnitManager().getUnitById(srcUnitId);

		try{
		trn.begin();

		orderPos.setFactQuantity((orderPos.getFactQuantity()==null?0L:orderPos.getFactQuantity())+1);
		if((orderPos.getFactQuantity()==null?0:orderPos.getFactQuantity().intValue())==orderPos.getExpectQuantity().intValue()) orderPos.setStatusId(DefaultValue.PICKED_END_STATUS);
		session.update(orderPos);

		//session = HibernateUtil.getSession();
		/*
		if(new OrderController().isOrderPicked(orderPos.getOrderId())){
			//if(
				new OrderController().setPickedOrder(orderPos.getOrderId(), session);
			//   )
			//	new OrderController().setPickedPreOrder(orderPos.getOrderId(), session);  Не успел доработать!
		} //******************************
		 */

		//session = HibernateUtil.getSession();
		WmPick pick = getPick(orderPos, destUnitId);
		if(pick instanceof WmPick){
			pick.setQuantity(pick.getQuantity()+1);
			session.update(pick);
		}else{
			pick = new PickManager().fillPick(userId, orderPos, srcUnit, destUnitId, 1L);
			session.persist(pick);
		}

			splitQuantByOrderPos(session, orderPos, srcUnit, destUnitId, userId);

			new HistoryController().createPickHistory(session, pick, new OrderController().getOrderByOrderId(orderPos.getOrderId()), 1L);

			trn.commit();

		}catch(Exception e){
			e.printStackTrace();
			trn.rollback();
			session.close();
		}/*finally{
			if(session.isOpen()) session.close();
		}*/


	}

	public List<WmPick> getPickListByOrderPosUnit(WmOrderPos orderPos, WmUnit unit){

		CriterionFilter flt = new CriterionFilter();
		if(orderPos instanceof WmOrderPos)
		{
			flt.addFilter("orderId", orderPos.getOrderId().toString(), "eq");
			flt.addFilter("orderPosId", orderPos.getOrderPosId().toString(), "eq");
		}

		if(unit instanceof WmUnit){
			flt.addFilter("destUnitId", unit.getUnitId().toString(), "eq");
		}

		List<WmPick> pickList = HibernateUtil.getObjectList(WmPick.class, flt.getFilterList(), 0, false, "");

		return pickList;
	}

	public int getSumSkuUnitOrder(Long orderId, String unitCode){
		int result = 0;
		WmUnit unit = null;
		try {
			unit = new UnitManager().getUnitByCode(unitCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(unit instanceof WmUnit )
			result =  new PickManager().getSumSkuUnitOrderChecked(unit.getUnitId(), orderId);

		return result;
	}

	public boolean idDestUnitOrder(String orderIdTxt, String unitIdTxt){

		Long orderId = Long.valueOf(orderIdTxt);
		Long unitId = Long.valueOf(unitIdTxt);

		return new PickManager().isDestUnitOrder(orderId, unitId);
	}

	public ShptUnitsBalance getUnitsBalance(Object orderIdObj)
	{
		ShptUnitsBalance balance = null;
		PickManager pickMgr = new PickManager();
		Long orderId = OrderController.getId(orderIdObj.toString());
		if(orderId instanceof Long){
			balance = new ShptUnitsBalance(orderId.longValue());
			balance.setPreparedUnitsCount(pickMgr.getUnitsBalanceByOrderStatus(orderId, DefaultValue.SHIPPED_UNIT_STATUS, true));
			balance.setShippedUnitsCount( pickMgr.getUnitsBalanceByOrderStatus(orderId, DefaultValue.SHIPPED_UNIT_STATUS, false));
		}

		 return balance;
	}

	/* Создание строк подбора */
	private void createPickByCheckList(List<WmCheck> checkList)
	{
		Session session = HibernateUtil.getSession();
		try{
			session.getTransaction().begin();

			for(WmCheck check : checkList){
				WmOrderPos orderPos = new OrderPosManager().getOrderByPosId(check.getOrderId(), check.getOrderPosId());
				WmUnit unit = new UnitManager().getUnitById(check.getUnitId());
				WmPick pick = new PickManager().fillPick(check.getCreateUser(), orderPos, unit, unit.getUnitId(), check.getScanQuantity());
				session.persist(pick);
			}
			session.getTransaction().commit();
		} catch(Exception e){
			session.getTransaction().rollback();
		}
	}

	public boolean processShipmentEnd(HttpSession session)
	{
		Long orderId = Long.valueOf(session.getAttribute("orderid").toString());
		WmOrder order = new OrderManager().getOrderById(orderId);

		WmUnit unit = new UnitManager().getUnitByCode(session.getAttribute("unitcode").toString());

		List<WmPick> pickList = new PickManager().getPickListByOrderUnit(order, unit);
		if(pickList.size()==0)
		{
			List<WmCheck> checkList = new CheckController().getCheckListByOrderUnit(order, unit);
			createPickByCheckList(checkList);
		}


		removeShptData(session.getAttribute("userid"), session.getAttribute("orderid"), session.getAttribute("unitcode"), Long.valueOf(DefaultValue.SHIPPED_UNIT_STATUS));
		return true;
	}

	public void removeShptData(Object userIdObj, Object orderIdObj, Object unitCodeObj, Long statusId){
		String unitCode = unitCodeObj.toString();

		Long unitId = new UnitController().getUnitId(unitCode);
		Long orderId = orderIdObj==null?null:OrderController.getId(orderIdObj.toString());


		Session session = HibernateUtil.getSession();
		session.getTransaction().begin();
		if(orderId != null)
			new PickManager().setOrderUnitStatus(session, orderId, unitId, statusId);
		new QuantController().delQuantByUnitCode(session, userIdObj, unitCodeObj, EntryType.SHIPMENT);

		try{
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}

	}

	public boolean isShippedUnit(String orderIdTxt, String unitIdTxt){

		Long unitId = UnitController.getId(unitIdTxt);
		Long orderId = OrderController.getId(orderIdTxt);
		Long statusId = new PickManager().getOrderUnitStatus(orderId, unitId);

		System.out.println("orderIdTxt: "+orderIdTxt +" statusId : "+statusId);

		return (statusId.longValue()== DefaultValue.SHIPPED_UNIT_STATUS);
	}

	public List<WmPick> getPickList(String orderIdTxt, String orderPosIdTxt){

		Long ordId = Long.decode(orderIdTxt);
		Long ordPosId = Long.decode(orderPosIdTxt);

		List<WmPick> pickList = new PickManager().getPickListByOrderPos(ordId, ordPosId);

		return pickList;
	}

	public List<Vpick> getVpickList(String orderIdTxt, String orderPosIdTxt, String unitflt,
			String binflt, String qsflt, String qtyflt, String dateflt, String surnameflt, String firstnameflt){

		Long ordId = Long.decode(orderIdTxt);
		Long ordPosId = Long.decode(orderPosIdTxt);
		Timestamp tsDateflt = null;

		if(!dateflt.isEmpty())
			tsDateflt = AppUtil.stringToTimestamp(dateflt);

		List<Vpick> pickList = new PickManager().getVpickListByOrderPos(ordId, ordPosId, unitflt, binflt, qsflt, qtyflt, tsDateflt, surnameflt, firstnameflt);

		return pickList;
	}

	public boolean isUnitChecked(Object orderIdObj, Object unitCodeObj){
		Long orderId = OrderController.getId(orderIdObj);
		WmOrder order = new OrderManager().getOrderById(orderId);
		WmUnit unit = new UnitController().getUnitByUnitCode(unitCodeObj);
		if(!(unit instanceof WmUnit)) return false;
		if(!(order instanceof WmOrder)) return false;

		return new PickManager().isDestUnitOrder(order, unit);
	}

	public boolean isUnitPicked(Object orderIdObj, Object unitCodeObj){
		Long orderId = OrderController.getId(orderIdObj);
		WmUnit unit = new UnitController().getUnitByUnitCode(unitCodeObj);
		if(!(unit instanceof WmUnit)) return false;

		return new PickManager().isDestUnitOrder(orderId, unit.getUnitId());
	}


	public boolean unitPicked(String orderCode, String unitCode){

		WmOrder order = new OrderManager().getOrderByCode(orderCode, false);
		if(order == null) return false;

		WmUnit unit = null;
		try{
			unit = new UnitManager().getUnitByCode(unitCode);
		} catch(Exception e){
			e.printStackTrace();
		}
		if(unit instanceof WmUnit == false) return false;

		return new PickManager().isDestUnitOrder(order.getOrderId(), unit.getUnitId());
	}

	public String getConfirmText(String orderCode, String unitCode){
		String confText = null;
		if(unitPicked(orderCode, unitCode))
			confText = ConfirmMessage.ASK_UNIT_RECONTROL.message(new ArrayList<String>(Arrays.asList(unitCode, orderCode)));

		return confText;
	}

	public void delPickedUnit(String orderCode, String unitCode, Long userId){
		WmOrder order = new OrderManager().getOrderByCode(orderCode, false);

		OrderPosManager ordPosMgr = new OrderPosManager();

		if(order == null) return;

		WmUnit unit = null;

		try{
			unit = new UnitManager().getUnitByCode(unitCode);
		} catch(Exception e){
			e.printStackTrace();
		}

		if(unit instanceof WmUnit == false) return;

		Session session = HibernateUtil.getSession();
		try{
			session.getTransaction().begin();

		 List<WmPick> pickList = new PickManager().getPickByUnitOrder(order.getOrderId(), unit.getUnitId());
		 for(WmPick pick : pickList){

			 WmOrderPos orderPos = ordPosMgr.getOrderByPosId(pick.getOrderId(), pick.getOrderPosId());

			 if(orderPos instanceof WmOrderPos){
				 if(((orderPos.getFactQuantity()==null?0L:orderPos.getFactQuantity())-pick.getQuantity())<=0){
					 orderPos.setFactQuantity(null);
				 } else {
					 orderPos.setFactQuantity(orderPos.getFactQuantity()-pick.getQuantity());
				 }
				 orderPos.setStatusId(1L);
				 session.persist(orderPos);
			 }

		 }

		 	new PickManager().delPickedUnit(session, order.getOrderId(), unit.getUnitId(), userId);

		  	session.getTransaction().commit();
		} catch(Exception e){
			session.getTransaction().rollback();
		} finally{
			session.close();
		}



	}

	public void delCheckedUnit(WmOrder order, WmUnit unit, Long userId){

		OrderPosManager ordPosMgr = new OrderPosManager();

		if(order == null || unit ==null) return;

		Session session = HibernateUtil.getSession();
		try{
			session.getTransaction().begin();

		 List<WmCheck> checkList = new CheckManager().getCheckByUnitOrder(order, unit);
		 for(WmCheck check : checkList){

			 WmOrderPos orderPos = ordPosMgr.getOrderByPosId(check.getOrderId(), check.getOrderPosId());

			 if(orderPos instanceof WmOrderPos){
				 if(((orderPos.getFactQuantity()==null?0L:orderPos.getFactQuantity())-check.getScanQuantity())<=0){
					 orderPos.setFactQuantity(null);
				 } else {
					 orderPos.setFactQuantity(orderPos.getFactQuantity()-check.getScanQuantity());
				 }
				 orderPos.setStatusId(1L);
				 session.persist(orderPos);
			 }

		 }

		 	new PickManager().delPickedUnit(session, order.getOrderId(), unit.getUnitId(), userId);

		  	session.getTransaction().commit();
		} catch(Exception e){
			session.getTransaction().rollback();
		} finally{
			session.close();
		}



	}

}

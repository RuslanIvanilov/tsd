package ru.defo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.defo.filters.FieldFilter;
import ru.defo.filters.VorderPosFilter;
import ru.defo.managers.ArticleManager;
import ru.defo.managers.CarManager;
import ru.defo.managers.DeliveryManager;
import ru.defo.managers.JobManager;
import ru.defo.managers.OrderManager;
import ru.defo.managers.OrderPosManager;
import ru.defo.managers.PreOrderManager;
import ru.defo.managers.UnitManager;
import ru.defo.model.WmCar;
import ru.defo.model.WmJob;
import ru.defo.model.WmJobType;
import ru.defo.model.WmOrder;
import ru.defo.model.WmOrderPos;
import ru.defo.model.WmPreOrder;
import ru.defo.model.WmPreOrderPos;
import ru.defo.model.WmRoute;
import ru.defo.model.WmUser;
import ru.defo.model.WmVendorGroup;
import ru.defo.model.views.Vorderpos;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;
import ru.defo.util.HibernateUtil;

public class OrderController {

	OrderManager ordMgr = new OrderManager();

	/**
	 * @param - simple convert String unitIdText to unitId Long
	 * */
	public static Long getId(String orderIdText){
		Long orderId = null;
		try{
			orderId = Long.valueOf(orderIdText);
		} catch(NumberFormatException e){
			e.printStackTrace();
		}
		return orderId;
	}

	public WmOrder gerOrderByOrderId(Object orderIdObj){
		return gerOrderByOrderId(orderIdObj.toString());
	}

	public WmOrder gerOrderByOrderId(String orderIdTxt){
		return getOrderByOrderId(Long.valueOf(orderIdTxt));
	}

	public boolean checkPreOrderByLinks(WmPreOrder preOrder){
		boolean result = true;
		if(preOrder.getWmOrderLink()==null) return result;

		List<WmPreOrderPos> posList = new PreOrderManager().getPosByOrderId(preOrder);
		for(WmPreOrderPos pos : posList){
			if(pos.getWmPosOrderLink()==null)
				setErrorOrdStatus(preOrder);
				result = false;
		}

		return result;
	}

	public void checkOrderByLinks(WmOrder order){

		List<WmPreOrder> preOrderList  = new PreOrderManager().getPreOrderByWmOrderLink(order.getOrderId());
		for(WmPreOrder preOrder : preOrderList){
			if(preOrder.getWmOrderLink()==null) continue;

			checkPreOrderByLinks(preOrder);
		}

	}

	private void setErrorOrdStatus(WmPreOrder preOrder){

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		preOrder.setStatusId(DefaultValue.STATUS_INTEGR_ERROR);
		preOrder.setErrorId(5L);
		session.merge(preOrder);
		tx.commit();
		session.close();
	}


	public WmOrderPos getTempOrderPosByIdPosId(String orderIdTxt, String orderPosIdTxt)
	{
		Long orderId, orderPosId;

		try{
			orderId = Long.valueOf(orderIdTxt);
			orderPosId = Long.valueOf(orderPosIdTxt);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

		WmOrderPos orderPos = new WmOrderPos();
		orderPos.setOrderId(orderId);
		orderPos.setOrderPosId(orderPosId);

		return orderPos;
	}

	public WmOrderPos getOrderPosByTemp(WmOrderPos tempOrderPos)
	{
		try{
			return getOrderPosByIdPosId(tempOrderPos.getOrderId(), tempOrderPos.getOrderPosId());
		} catch(NullPointerException npe){
			return null;
		}
	}

	public WmOrderPos getOrderPosByIdPosId(Long orderId, Long orderPosId)
	{
		return  new OrderManager().getOrderPosByIdPosId(orderId, orderPosId);
	}


	public Long getPickRemainsOrderPos(String orderPosIdTxt){
		Long orderPosId = Long.valueOf(orderPosIdTxt);
		WmOrderPos orderPos = new OrderManager().getOrderPosByPosId(orderPosId);

		return orderPos.getExpectQuantity().longValue()-(orderPos.getFactQuantity()==null?0:orderPos.getFactQuantity().longValue());
	}

	public static Long getId(Object orderIdObj){
		String orderIdTxt =	String.valueOf(orderIdObj);
		return getId(orderIdTxt);
	}

	public List<WmOrderPos> getOrderPosByOrderId(Long orderId, boolean needCloseSession)
	{
		List<WmOrderPos> orderPosList =  new OrderManager().getOpenOrderPosListByOrderId(orderId, needCloseSession);
		return orderPosList;
	}

	public List<WmOrderPos> getOrderPosByOrderCode(String orderCode){
		WmOrder order = getOrderByCode(orderCode);
		List<WmOrderPos> orderPosList = getOrderPosByOrderId(order.getOrderId(), true);
		return orderPosList;
	}

	public boolean isOrderPicked(Long orderId)
	{
		return getOpenPickGroupListByOrderId(orderId).isEmpty()==false;
	}

	public boolean setOrderPosFinished(HttpSession httpSession, Session hiberSession){

		Long orderId = null;
		Long orderPosId = null;
		try{
			orderId = Long.valueOf(httpSession.getAttribute("orderid").toString());
			orderPosId = Long.valueOf(httpSession.getAttribute("orderposid").toString());
		} catch(Exception e){
			System.out.println("Íåñóùåñòâóþùèé ÊËÞ×: orderid ["+httpSession.getAttribute("orderid")+"] pos ["+httpSession.getAttribute("orderposid")+"]");
			return false;
		}

		WmOrderPos orderPos = new OrderPosManager().getOrderByPosId(orderId, orderPosId);
		if(orderPos.getStatusId().longValue()==DefaultValue.STATUS_LOST) return true;

		System.out.println("OrderController. setOrderPosFinished needUpdate "+orderPos.getOrderPosId());
		orderPos.setStatusId(DefaultValue.STATUS_LOST);
		if(orderPos.getFactQuantity()==null) orderPos.setFactQuantity(0L);

		try{
			hiberSession.getTransaction().begin();
			hiberSession.update(orderPos);
			hiberSession.getTransaction().commit();
		} catch(Exception e){
			e.printStackTrace();
			hiberSession.getTransaction().rollback();
			//hiberSession.close();
		}finally{
			hiberSession.close();
		}


		return true;
	}

	public boolean setPickedOrder(Long orderId, Session session){
		try{
			//session = HibernateUtil.getSession();
			WmOrder order = getOrderByOrderId(orderId);
			order.setStatusId(DefaultValue.PICKED_END_STATUS);
			session.update(order);
		} catch(Exception e){
			session.close();
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void setPickedPreOrder(Long orderId, Session session)
	{
		try{
			session = HibernateUtil.getSession();
			/* ÍÓÆÍÎ ×ÅÐÅÇ WmOrderLINK !!! */
			WmPreOrder preOrder = new PreOrderController().getPreOrderByOrderId(orderId);
			preOrder.setStatusId(DefaultValue.PICKED_END_STATUS);
			session.update(preOrder);
		}catch(Exception e){
			session.close();
			e.printStackTrace();
		}

	}

	public WmOrderPos getFisrtOpenOrderPos(WmOrder order, String vendorGroupId, WmUser user)
	{
		WmOrderPos selectedOrderPos = null;
		JobManager jobMgr = new JobManager();
		WmJobType jobType = jobMgr.getJobTypeById(2);
		Long vndGroupId = Long.valueOf(vendorGroupId);

		selectedOrderPos = jobMgr.getOrderPosJob(user, order, jobType);

		if(selectedOrderPos == null){
			List<WmOrderPos> orderPosList = getOrderPosByOrderCode(order.getOrderCode());
			orderPosList.sort(Comparator.comparing(WmOrderPos::getOrderPosId));
			for(WmOrderPos orderPos : orderPosList)
			{
				List<WmJob> jobList = jobMgr.getJobForAnyUser(jobType, order, new ArticleManager().getArticleById(orderPos.getArticleId()), user);
				if(jobList.size()>0) orderPos.setSequence((orderPos.getOrderPosId().longValue()+1000000000000L));


				if(new UnitManager().isISOunitType(orderPos.getArticleId()))
				{
					orderPos.setSequence((orderPos.getOrderPosId().longValue() -1000L));
				} else {
					if(orderPos.getSequence()<1) orderPos.setSequence((orderPos.getOrderPosId().longValue()));
				}
				//System.out.println("posId : "+orderPos.getOrderPosId()+" -> seq: "+orderPos.getSequence());
			}


			orderPosList.sort(Comparator.comparing(WmOrderPos::getSequence));


			for(WmOrderPos orderPos : orderPosList){
				//System.out.println("sorted :: posId : "+orderPos.getOrderPosId()+" -> seq: "+orderPos.getSequence());
				WmVendorGroup posArticleVendorGroup = new ArticleController().getVendorGroupByArticle(new ArticleManager().getArticleById(orderPos.getArticleId()));
				if(!(posArticleVendorGroup instanceof WmVendorGroup))
				{
					posArticleVendorGroup = new WmVendorGroup();
					posArticleVendorGroup.setVendorGroupId(0L);
				}

				if(orderPos.getExpectQuantity().longValue()>(orderPos.getFactQuantity()==null?0L:orderPos.getFactQuantity().longValue())
						                                && posArticleVendorGroup.getVendorGroupId().longValue() == vndGroupId.longValue()){
					if(selectedOrderPos!=null) break;
					selectedOrderPos = orderPos;
				}

			}
		}


		return selectedOrderPos;
	}

	public Map<WmVendorGroup, Long> getOpenPickGroupListByOrderId(Long orderId)
	{
		Map<WmVendorGroup, Long> GroupPickCount = new HashMap<WmVendorGroup, Long>();

		Vorderpos ordP = new Vorderpos();
		ordP.setOrderId(orderId);
		ordP.setStatusId(3L);

		FieldFilter filter = new FieldFilter();
		filter.add("statusId", "<");

		List<Vorderpos> vOrdPosList =  new OrderManager().getVOrderPosList(ordP, filter);

		//int i = 0;
		for(Vorderpos vOrderPos : vOrdPosList){
			//i++;

			WmVendorGroup group = new ArticleManager().getVendorGroupById(vOrderPos.getVendorGroupId());

			if(!(group instanceof WmVendorGroup)){
				group = new WmVendorGroup();
				group.setVendorGroupId(0L);
				group.setDescription("<Íåîïðåäåëåíî>");
			}


			if(GroupPickCount.get(group)==null){
				GroupPickCount.put(group, 1L);
			} else {
				GroupPickCount.replace(group, GroupPickCount.get(group), GroupPickCount.get(group)+1);
			}

			//System.out.println(i+"."+"\tGROUP: "+vOrderPos.getVendorGroupId()+"\t / "+vOrderPos.getOrderId()+" / "+vOrderPos.getOrderPosId() +"..."+GroupPickCount.get(group));
		}

		return GroupPickCount;
	}

	public WmRoute getDeliveryByOrderId(Long orderId){
		WmOrder order = new OrderManager().getOrderById(orderId);
		if(!(order instanceof WmOrder)) return null;

		List<WmPreOrder> preOrderList = new PreOrderManager().getPreOrderByWmOrderLink(order.getOrderId());

		WmRoute route = new DeliveryManager().getById(preOrderList.get(0).getRouteId());
		if(!(route instanceof WmRoute)) return null;

		return route;
	}

	public WmCar getCarByOrderId(Long orderId){
		WmRoute route = getDeliveryByOrderId(orderId);
		if(!(route instanceof WmRoute) || route.getCarId()==null) return null;

		WmCar car = new CarManager().getCarById(route.getCarId());
		if(!(car instanceof WmCar)) return null;

		return car;
	}

	public Long getNextOrderId(){
		return ordMgr.getNextOrderId();
	}

	public WmOrder getOrderByOrderId(Long orderId)
	{
		WmOrder order =  ordMgr.getOrderById(orderId);
		if(!(order instanceof WmOrder)){

			WmOrder ord = new WmOrder();
			ord.setOrderId(orderId);

			return ord;
		}
		return order;
	}

	public List<Vorderpos> getVOrderPosList(HttpSession sessionH){

		VorderPosFilter flt = new VorderPosFilter();

		/*
		 System.out.println("orderid : "+sessionH.getAttribute("orderid").toString()+
						   "posflt : "+sessionH.getAttribute("posflt").toString()+
						   "articleidflt : "+sessionH.getAttribute("articleidflt").toString()+
						   "artdescrflt : "+sessionH.getAttribute("artdescrflt").toString()+
						   "skuflt : "+sessionH.getAttribute("skuflt").toString()+
						   "expqtyflt : "+sessionH.getAttribute("expqtyflt").toString()+
						   "factqtyflt : "+sessionH.getAttribute("factqtyflt").toString()+
						   "ctrlqtyflt : "+sessionH.getAttribute("ctrlqtyflt").toString()+
						   "statusposflt : "+sessionH.getAttribute("statusposflt").toString()+
						   "vendoridflt : "+sessionH.getAttribute("vendoridflt").toString()
						   );
		 */

		Long orderId = Long.valueOf(sessionH.getAttribute("orderid").toString());
		flt.setOrderId(orderId);
		flt.setPosflt(sessionH.getAttribute("posflt").toString());
		flt.setArticleidflt(sessionH.getAttribute("articleidflt").toString());
		flt.setArtdescrflt(sessionH.getAttribute("artdescrflt").toString());
		flt.setSkuflt(sessionH.getAttribute("skuflt").toString());
		flt.setExpqtyflt(sessionH.getAttribute("expqtyflt").toString());
		flt.setFactqtyflt(sessionH.getAttribute("factqtyflt").toString());
		flt.setCtrlqtyflt(sessionH.getAttribute("ctrlqtyflt").toString());
		flt.setStatusflt(sessionH.getAttribute("statusposflt").toString());
		flt.setVendoridflt(sessionH.getAttribute("vendoridflt").toString());

		List<Vorderpos> vordlist = ordMgr.getVOrderPosList(flt);
		if(vordlist==null) return null;

		return vordlist;
	}


	/**@deprecated
	 * */
	public List<Vorderpos> getVOrderPosList(String orderIdTxt, String posflt, String articleidflt, String artdescrflt,
											String skuflt, String expqtyflt, String factqtyflt, String statusflt, String vendoridflt){

		Long orderId  = Long.decode(orderIdTxt);

		return ordMgr.getVOrderPosList(orderId, posflt, articleidflt, artdescrflt, skuflt, expqtyflt, factqtyflt, statusflt, vendoridflt);
	}

	public List<Vorderpos> getVOrderPosList(String orderCodeTxt){

		WmOrder order = new OrderManager().getOrderByCode(orderCodeTxt, false);

		if(order == null) return null;

		String orderIdTxt = order.getOrderId().toString();

		return getVOrderPosList(orderIdTxt, null, null, null, null, null, null, null, null);
	}

	public WmOrder getOrderByCode(String orderCode)
	{
		return ordMgr.getOrderByCode(orderCode, false);
	}

	public WmOrderPos getOrderPosByOrderArticleQState(String orderCode, Long articleId, Long qualityStateId){
		System.out.println("in here");
		Long orderId = getOrderByCode(orderCode).getOrderId();

		OrderPosManager ordPosMgr = new OrderPosManager();
		return ordPosMgr.getOrderPosByArticleQState(orderId, articleId, qualityStateId, false);
	}

	public String delOrder(String orderCode){

		WmOrder order = new OrderController().getOrderByCode(orderCode);
		if(order == null)
			return ErrorMessage.ORDER_NOT_EXISTS.message(new ArrayList<String>(Arrays.asList(orderCode)));

		if(order.getStatusId() != 1L)
			return ErrorMessage.WRONG_ORDER_STATUS4DEL.message(new ArrayList<String>(Arrays.asList(order.getStatus().getDescription())));

		ordMgr.delOrder(order);

		return null;
	}


}

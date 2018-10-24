package ru.defo.managers;

import java.math.BigInteger; 
import java.util.ArrayList; 
import java.util.List;

import org.hibernate.Criteria; 
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.model.WmArticle;
import ru.defo.model.WmCheck;
import ru.defo.model.WmOrder;
import ru.defo.model.WmOrderPos;
import ru.defo.model.WmPick; 
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

public class OrderPosManager extends ManagerTemplate {
	public OrderPosManager() {
		super(WmOrderPos.class);
	}

	public int getExpectedQtyByOrderPosList(List<WmOrderPos> orderPosList)
	{
		int expQty = 0;
		for(WmOrderPos pos : orderPosList){
			expQty = pos.getExpectQuantity().intValue();
		}
		return expQty;
	}

	public int getControlQtyByOrderPosList(List<WmOrderPos> orderPosList)
	{
		int ctrlQty = 0;
		for(WmOrderPos pos : orderPosList){
			ctrlQty = pos.getCtrlQuantity()==null?0:pos.getCtrlQuantity().intValue();
		}
		return ctrlQty;
	}

	public int getFactQtyByOrderPosList(List<WmOrderPos> orderPosList)
	{
		int factQty = 0;
		for(WmOrderPos pos : orderPosList){
			factQty = pos.getFactQuantity()==null?0:pos.getFactQuantity().intValue();
		}
		return factQty;
	}

	public List<WmOrderPos> getOrderPosListByOrderArticle(WmOrder order, WmArticle article){
		List<WmOrderPos> resultList = new ArrayList<WmOrderPos>();
		List<WmOrderPos> posList = getOrderPosList(order.getOrderId());

		for(WmOrderPos orderPos : posList){
			if(orderPos.getArticleId().longValue()==article.getArticleId().longValue()){
				resultList.add(orderPos);
			}
		}

		return resultList;
	}


	public WmOrderPos getOrderByPosId(Long orderId, Long orderPosId)
	{
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmOrderPos.class).getName());
		criteria.add(Restrictions.eq("orderId", orderId));
		criteria.add(Restrictions.eq("orderPosId", orderPosId));
		criteria.setMaxResults(1);
		return (WmOrderPos) criteria.uniqueResult();
	}

	public void decreaseOrderPosQty(WmPick pick){
		decreaseOrderPosQty(pick.getOrderId(), pick.getOrderPosId(), pick.getQuantity());
	}

	public void decreaseOrderPosQty(WmCheck check){
		decreaseOrderPosQty(check.getOrderId(), check.getOrderPosId(), check.getScanQuantity());
	}

	public void decreaseOrderPosQty(Long orderId, Long orderPosId, Long quantity)
	{
		WmOrderPos orderPos = getOrderByPosId(orderId, orderPosId);
		if(orderPos != null){
			if(((orderPos.getCtrlQuantity()==null?0:orderPos.getCtrlQuantity())-quantity)>0){
				orderPos.setCtrlQuantity((orderPos.getCtrlQuantity()==null?0:orderPos.getCtrlQuantity())-quantity);
			} else {orderPos.setCtrlQuantity(null);}
				//if(orderPos.getCtrlQuantity()==0L)
				if(new PickManager().getPickByOrderPos(orderPos).size()>0){
					orderPos.setStatusId(DefaultValue.PICKED_END_STATUS);
				} else {
					orderPos.setStatusId(DefaultValue.STATUS_CREATED);
				}
			    session.persist(orderPos);

		}
	}

	public long getNextOrderPosId(){
		return ((BigInteger) session.createSQLQuery("select nextval('seq_orderpos_id')").uniqueResult()).longValue();
	}

	public void addOrUpdateOrderPos(WmOrderPos orderPos){
		WmOrderPos orderPos0 = getOrderPosByArticleQState(orderPos.getOrderId(), orderPos.getArticleId(), orderPos.getQualityStateId(), true);
		session.getTransaction().begin();
		if(orderPos0 == null)
		{
			orderPos0 = orderPos;
		} else
		{
			orderPos0.setFactQuantity(orderPos0.getFactQuantity() + orderPos.getFactQuantity());
		}
		session.persist(orderPos0);

		session.getTransaction().commit();
	}

	public void saveOrderPos(WmOrderPos orderPos){
		session.getTransaction().begin();
		session.update(orderPos);
		session.getTransaction().commit();
	}

	public WmOrderPos getOrderPosByArticleQState(Long orderId,  Long articleId, Long qualityStateId, boolean needClose)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("orderId", orderId));
		restList.add(Restrictions.eq("articleId", articleId));
		if(qualityStateId != null && qualityStateId != DefaultValue.QUALITY_STATE)
			restList.add(Restrictions.eq("qualityStateId", qualityStateId));
		WmOrderPos orderPos = (WmOrderPos)HibernateUtil.getUniqObject(WmOrderPos.class, restList, false);
		if(needClose)session.close();

		return orderPos;
	}

	@SuppressWarnings("unchecked")
	public List<WmOrderPos> getOrderPosList(Long orderId){

		Criteria criteria = session.createCriteria((WmOrderPos.class).getName());
		criteria.add(Restrictions.eq("orderId", orderId));

		return (List<WmOrderPos>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<WmOrderPos> getClosedOrderPosList(Long orderId){

		Criteria criteria = session.createCriteria((WmOrderPos.class).getName());
		criteria.add(Restrictions.eq("orderId", orderId));
		criteria.add(Restrictions.eq("statusId", DefaultValue.STATUS_LOST));

		return (List<WmOrderPos>) criteria.list();
	}

}

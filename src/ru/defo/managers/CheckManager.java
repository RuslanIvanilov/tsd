package ru.defo.managers;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmCheck;
import ru.defo.model.WmOrder;
import ru.defo.model.WmOrderPos; 
import ru.defo.model.WmUnit;
import ru.defo.model.views.Vcheck;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

public class CheckManager extends ManagerTemplate {

	public long getNextPickId()
	{
		return ((BigInteger) HibernateUtil.getSession().createSQLQuery("select nextval('seq_check_id')").uniqueResult()).longValue();
	}

	public boolean isFullOrderChecked(WmOrderPos pos){
		WmOrder order = new OrderManager().getOrderById(pos.getOrderId());
		return isFullOrderChecked(order);
	}

	public boolean isFullOrderShiped(WmOrderPos pos){
		WmOrder order = new OrderManager().getOrderById(pos.getOrderId());
		return isFullOrderShiped(order);
	}

	public boolean isFullOrderChecked(WmOrder order)
	{

		List<WmOrderPos> posList = new OrderManager().getOpenOrderPosListByOrderId(order.getOrderId(), false);
		for(WmOrderPos pos : posList)
		{
			if(pos.getStatusId().longValue()< DefaultValue.STATUS_CONTROL_FINISHED) return false;
		}

		return true;
	}

	public boolean isFullOrderShiped(WmOrder order)
	{

		List<WmOrderPos> posList = new OrderManager().getOpenOrderPosListByOrderId(order.getOrderId(), false);
		for(WmOrderPos pos : posList)
		{
			if(pos.getStatusId().longValue()< DefaultValue.SHIPPED_UNIT_STATUS) return false;
		}

		return true;
	}

	public List<Vcheck> getVcheckByFlt(String orderIdTxt, String orderPosIdTxt, String unitFlt, String qtyFlt, String dateFlt, String surNameFlt, String firstNameFlt)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("orderId", orderIdTxt, "eq");
		flt.addFilter("orderPosId", orderPosIdTxt, "eq");
		flt.addFilter("unitCode", unitFlt, "like");
		flt.addFilter("quantity", qtyFlt, "<>");
		flt.addFilter("unitCode", dateFlt, "date");
		flt.addFilter("surname", surNameFlt, "like");
		flt.addFilter("firstName", firstNameFlt, "like");

		List<Vcheck> checkList = HibernateUtil.getObjectList(Vcheck.class, flt.getFilterList(), 0, false, "unitId");

		for(Vcheck check : checkList)
		{
			HibernateUtil.getSession().refresh(check);
		}
		return checkList;
	}

	public List<WmCheck> getCheckByUnitOrder(WmOrder order, WmUnit unit)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("orderId", order.getOrderId().toString(), "eq");
		flt.addFilter("unitId", unit.getUnitId().toString(), "eq");
		List<WmCheck> checkList = HibernateUtil.getObjectList(WmCheck.class, flt.getFilterList(), 0, false, "orderPosId");

		for(WmCheck check : checkList)
		{
			HibernateUtil.getSession().refresh(check);
		}

		return checkList;
	}


	public boolean checkPicked(WmOrder order, WmUnit unit)
	{
		return (getCheckByUnitOrder(order, unit).size()>0);
	}

	public WmCheck fillCheck(Long unitId, Long articleId, Long skuId, Long quantity, Long orderId, Long orderPosId, Long userId)
	{
		WmCheck check = new WmCheck();
		check.setCheckId(getNextPickId());
		check.setUnitId(unitId);
		check.setArticleId(articleId);
		check.setSkuId(skuId);
		check.setScanQuantity(quantity);
		check.setOrderId(orderId);
		check.setOrderPosId(orderPosId);
		check.setCreateUser(userId);
		check.setCreateDate(new Timestamp(System.currentTimeMillis()));
		return check;
	}

	public WmCheck getCheckByUniqPrms(WmCheck check){
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmCheck.class).getName());
		criteria.add(Restrictions.eq("orderId", check.getOrderId()));
		criteria.add(Restrictions.eq("orderPosId", check.getOrderPosId()));
		criteria.add(Restrictions.eq("unitId", check.getUnitId()));
		criteria.add(Restrictions.eq("articleId", check.getArticleId()));
		criteria.add(Restrictions.eq("skuId", check.getSkuId()));
		return (WmCheck) criteria.uniqueResult();
	}

	public void addOrUpdateCheck(Session session, WmCheck check){
		WmCheck check0 = getCheckByUniqPrms(check);

		if(!(check0 instanceof WmCheck)){
			check0 = check;
		} else {
			check0.setScanQuantity(check0.getScanQuantity() + check.getScanQuantity());
		}
		session.persist(check0);
	}

}

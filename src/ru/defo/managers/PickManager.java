package ru.defo.managers;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ru.defo.controllers.HistoryController;
import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmBin;
import ru.defo.model.WmCheck;
import ru.defo.model.WmOrder;
import ru.defo.model.WmOrderPos;
import ru.defo.model.WmPick;
import ru.defo.model.WmUnit;
import ru.defo.model.views.Vpick;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

public class PickManager extends ManagerTemplate {

	@SuppressWarnings("unchecked")
	public List<WmPick> getPickListByOrderUnit(WmOrder order, WmUnit unit)
	{
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmPick.class).getName());
		criteria.add(Restrictions.eq("orderId", order.getOrderId()));
		criteria.add(Restrictions.eq("destUnitId", unit.getUnitId()));
		return (List<WmPick>) criteria.list();
	}


	@SuppressWarnings("unchecked")
	public List<WmPick> getPickListByOrderPos(Long orderId, Long orderPosId) {
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmPick.class).getName());
		criteria.add(Restrictions.eq("orderId", orderId));
		criteria.add(Restrictions.eq("orderPosId", orderPosId));
		return (List<WmPick>) criteria.list();
	}

	public long getBinTypePickAval(){  return DefaultValue.BIN_TYPE_PICK_AVAL; }

	public boolean isBinPickAval(WmBin bin)
	{
		return(bin.getBinTypeId() <= DefaultValue.BIN_TYPE_PICK_AVAL && bin.getBinStatusId().intValue()!= 4 && !bin.getBlockOut());
	}

	/*session.getAttribute("unitflt").toString(),
	  session.getAttribute("binflt").toString(),
	  session.getAttribute("qsflt").toString(),
	  session.getAttribute("qtyflt").toString(),
	  session.getAttribute("dateflt").toString(),
	  session.getAttribute("surnameflt").toString(),
	  session.getAttribute("firstnameflt").toString()
	 */

	/**
	 * @param unitId - destination unit from WmPick
	 * @param orderId - Id from WmOrder
	 * */
	@SuppressWarnings("unchecked")
	public int getSumSkuUnitOrder(Long unitId, Long orderId){
		Long qtyl = 0L;
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmPick.class).getName());
		criteria.add(Restrictions.eq("orderId", orderId));
		criteria.add(Restrictions.eq("destUnitId", unitId));
		List<WmPick> pickList = (List<WmPick>) criteria.list();
		for(WmPick pick : pickList){
			qtyl = qtyl+pick.getQuantity();
		}
		return qtyl.intValue();
	}

	@SuppressWarnings("unchecked")
	public int getSumSkuUnitOrderChecked(Long unitId, Long orderId){
		Long qtyl = 0L;
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmCheck.class).getName());
		criteria.add(Restrictions.eq("orderId", orderId));
		criteria.add(Restrictions.eq("unitId", unitId));
		List<WmCheck> checkList = (List<WmCheck>) criteria.list();
		for(WmCheck check : checkList){
			qtyl = qtyl+check.getScanQuantity();
		}
		return qtyl.intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Vpick> getVpickListByOrderPos(Long orderId, Long orderPosId, String unitflt,
			String binflt, String qsflt, String qtyflt, Timestamp createDate, String surnameflt, String firstnameflt ) {
		Criteria criteria = HibernateUtil.getSession().createCriteria((Vpick.class).getName());

		try {
			setFilter(criteria, "eq","orderId", orderId);

			setFilter(criteria, "eq","orderPosId", orderPosId);

			setFilter(criteria, "like", "unitCode", unitflt);
			setFilter(criteria, "like","binCode", binflt);
			setFilter(criteria, "eq","qualityStateId", qsflt);

			setFilter(criteria, "<>","quantity", qtyflt);
			setFilter(criteria, "date","create_date", createDate);

			setFilter(criteria, "like","surname", surnameflt);
			setFilter(criteria, "like","firstName", firstnameflt);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (List<Vpick>) criteria.list();
	}

	public long getNextPickId(){
		return ((BigInteger) HibernateUtil.getSession().createSQLQuery("select nextval('seq_pick_id')").uniqueResult()).longValue();
	}

	public WmPick fillPick(Long userId, WmOrderPos orderPos, WmUnit srcUnit, Long destUnitId, Long quantity)
	{
		WmPick pick = new WmPick();
		pick.setPickId(getNextPickId());
		pick.setSourceBinId(srcUnit.getBinId());
		pick.setSourceUnitId(srcUnit.getUnitId());
		pick.setDestUnitId(destUnitId);
		pick.setArticleId(orderPos.getArticleId());
		pick.setSkuId(orderPos.getSkuId());
		pick.setQualityStateId(orderPos.getQualityStateId()==null?1L:orderPos.getQualityStateId());
		pick.setQuantity(quantity);
		pick.setOrderId(orderPos.getOrderId());
		pick.setOrderPosId(orderPos.getOrderPosId());
		pick.setCreateUser(userId);
		pick.setCreateDate(new Timestamp(System.currentTimeMillis()));

		return pick;
	}

	public WmPick fillPick(Long srcBinId, Long srcUnitId, Long destUnitId, Long articleId, Long skuId,
			Long qualityStateId, Long quantity, Long orderId, Long orderPosId, Long userId) {

		WmPick pick = new WmPick();
		pick.setPickId(getNextPickId());
		pick.setSourceBinId(srcBinId);
		pick.setSourceUnitId(srcUnitId);
		pick.setDestUnitId(destUnitId);
		pick.setArticleId(articleId);
		pick.setSkuId(skuId);
		pick.setQualityStateId(qualityStateId);
		pick.setQuantity(quantity);
		pick.setOrderId(orderId);
		pick.setOrderPosId(orderPosId);
		pick.setCreateUser(userId);
		pick.setCreateDate(new Timestamp(System.currentTimeMillis()));
		return pick;
	}


	public WmPick getPickByUniqPrms(WmPick pick){
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmPick.class).getName());
		criteria.add(Restrictions.eq("orderId", pick.getOrderId()));
		criteria.add(Restrictions.eq("orderPosId", pick.getOrderPosId()));
		criteria.add(Restrictions.eq("destUnitId", pick.getDestUnitId()));
		criteria.add(Restrictions.eq("sourceBinId", pick.getSourceBinId()));
		criteria.add(Restrictions.eq("sourceUnitId", pick.getSourceUnitId()));
		criteria.add(Restrictions.eq("articleId", pick.getArticleId()));
		criteria.add(Restrictions.eq("skuId", pick.getSkuId()));
		criteria.add(Restrictions.eq("qualityStateId", pick.getQualityStateId()));
		return (WmPick) criteria.uniqueResult();
	}

	public Long getOrderUnitStatus(Long orderId, Long unitId)
	{
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmPick.class).getName());
		criteria.add(Restrictions.eq("orderId", orderId));
		criteria.add(Restrictions.eq("destUnitId", unitId));
		criteria.setMaxResults(1);
		WmPick pick = (WmPick)criteria.uniqueResult();
		if(!(pick instanceof WmPick)) return 0L;

		HibernateUtil.getSession().refresh(pick);

		return pick.getStatusId()==null?0L:pick.getStatusId();
	}

	public void addOrUpdatePick(Session session, WmPick pick){
		WmPick pick0 = getPickByUniqPrms(pick);

		if(!(pick0 instanceof WmPick)){
			pick0 = pick;
		} else {
			pick0.setQuantity(pick0.getQuantity() + pick.getQuantity());
		}
		session.persist(pick0);
	}

	/**@deprecated
	 * */
	public void addOrUpdatePick(WmPick pick){

		WmPick pick0 = getPickByUniqPrms(pick);
		try{
			session = HibernateUtil.getSession();
			session.getTransaction().begin();
			if(!(pick0 instanceof WmPick)){
				pick0 = pick;
			} else {
				pick0.setQuantity(pick0.getQuantity() + pick.getQuantity());
			}
			session.persist(pick0);
			session.getTransaction().commit();
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
		} /*finally{
			//session.evict(pick0);
		}*/

	}

	/**@deprecated
	 * */
	public void savePick(WmPick pick){
		session = HibernateUtil.getSession();
		session.getTransaction().begin();
		session.persist(pick);
		session.getTransaction().commit();
		session.clear();
	}

	@SuppressWarnings("unchecked")
	public List<WmCheck> getCheckByUnitOrder(WmOrder order, WmUnit unit)
	{
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmCheck.class).getName());
		criteria.add(Restrictions.eq("orderId", order.getOrderId()));
		criteria.add(Restrictions.eq("unitId", unit.getUnitId()));

		List<WmCheck> checkList = (List<WmCheck>) criteria.list();
		for(WmCheck check : checkList){
			HibernateUtil.getSession().refresh(check);
		}
		return checkList;
	}

	@SuppressWarnings("unchecked")
	public List<WmPick> getPickByUnitOrder(Long orderId, Long unitId){
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmPick.class).getName());
		if(orderId != null)
			criteria.add(Restrictions.eq("orderId", orderId));

		criteria.add(Restrictions.eq("destUnitId", unitId));

		List<WmPick> pickList = (List<WmPick>) criteria.list();
		for(WmPick pick : pickList){
			HibernateUtil.getSession().refresh(pick);
		}

		return pickList;
	}

	public List<WmPick> getPickByOrderPos(WmOrderPos orderPos)
	{
		CriterionFilter filter = new CriterionFilter();
		filter.addFilter("orderId",  orderPos.getOrderId().toString(), "eq");
		filter.addFilter("orderPosId", orderPos.getOrderPosId().toString(), "eq");
		List<WmPick> pickList = HibernateUtil.getObjectList(WmPick.class, filter.getFilterList(), 0, false, "orderPosId");
		if(pickList == null) return null;

		return pickList;
	}

	public boolean isDestUnitOrder(WmOrder order, WmUnit unit){
		List<WmCheck> checkList =  getCheckByUnitOrder(order, unit);
		return (checkList.size() > 0);
	}

	public boolean isDestUnitOrder(Long orderId, Long unitId){
		List<WmPick> pickLIst =  getPickByUnitOrder(orderId, unitId);
		return (pickLIst.size() > 0);
	}

	public void delPickedUnit(Long orderId, Long unitId){
		OrderPosManager ordPosMgr = new OrderPosManager();
		List<WmPick> pickList =  getPickByUnitOrder(orderId, unitId);
		session = HibernateUtil.getSession();
		session.getTransaction().begin();
		for(WmPick pick : pickList){
			ordPosMgr.decreaseOrderPosQty(pick);
			session.delete(pick);
		}
		session.getTransaction().commit();
		session.clear();
	}

	public void delPickedUnit(Session session, Long orderId, Long unitId, Long userId){
		OrderPosManager ordPosMgr = new OrderPosManager();
		List<WmPick> pickList =  getPickByUnitOrder(orderId, unitId);

		for(WmPick pick : pickList){
			ordPosMgr.decreaseOrderPosQty(pick);
			pick.setQuantity(-pick.getQuantity());
			new HistoryController().addEntryByPick(session, pick, "Контроль Отмена. кол-во "+pick.getQuantity(), userId);
			session.delete(pick);
		}

	}

	public void setOrderUnitStatus(Session session, Long orderId, Long unitId, Long statusId){
		List<WmPick> pickList = getPickByUnitOrder(orderId, unitId);
		for(WmPick pick : pickList){
			pick.setStatusId(statusId);
			session.persist(pick);
		}
	}


	public int getUnitsBalanceByOrderStatus(Long orderId, Long statusId, boolean negative){
			try{
				if(negative)
					return ((Integer)HibernateUtil.getSession().createSQLQuery("select cast(sum(cnt) as integer) scnt  from vpickunitstatus where order_id = :orderId and status_id <> :statusId limit 1")
							.setParameter("orderId", orderId)
							.setParameter("statusId", statusId)
							.uniqueResult())
							.intValue();

					return ((BigInteger)HibernateUtil.getSession().createSQLQuery("select cnt from vpickunitstatus where order_id = :orderId and status_id = :statusId limit 1")
							.setParameter("orderId", orderId)
							.setParameter("statusId", statusId)
							.uniqueResult())
							.intValue();
			}catch(Exception e){
				return 0;
			}


	}

}

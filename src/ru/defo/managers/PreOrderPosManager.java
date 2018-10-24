package ru.defo.managers;

import java.math.BigInteger;
import java.sql.Timestamp; 
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ru.defo.controllers.HistoryController;
import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmOrderPos;
import ru.defo.model.WmPreOrder;
import ru.defo.model.WmPreOrderPos;
import ru.defo.util.HibernateUtil;

public class PreOrderPosManager extends ManagerTemplate {
	public PreOrderPosManager() {
		super(WmPreOrderPos.class);
	}

	public int getSumQtyPreOrdersByOrderPos(WmOrderPos pos)
	{
		int qty = 0;
		List<WmPreOrderPos> preOrdPosList =  getPreOrderPosListByLink(pos);
		qty = getSumQtyPreOrdPosList(preOrdPosList);

		return qty;
	}

	public int getSumQtyPreOrdPosList(List<WmPreOrderPos> preOrderPosList)
	{
		int qty = 0;

		for(WmPreOrderPos pos : preOrderPosList)
		{
			qty += pos.getExpectQuantity().intValue();
		}

		return qty;
	}


	public List<WmPreOrderPos> getPreOrderPosListByLink(WmOrderPos orderPos)
	{
		CriterionFilter f = new CriterionFilter();
		f.addFilter("wmPosOrderLink", orderPos.getOrderPosId().toString(), "eq");
		List<WmPreOrderPos> preOrdList = HibernateUtil.getObjectList(WmPreOrderPos.class, f.getFilterList(), 0, false, null);

		return preOrdList;
	}

	public List<WmPreOrderPos> getOrderPosListById(Long orderId, boolean needClose)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("orderId", orderId.toString(), "eq");
		List<WmPreOrderPos> preOrdList = HibernateUtil.getObjectList(WmPreOrderPos.class, flt.getFilterList(), 0, needClose, null);

		return preOrdList;
	}

	public WmPreOrderPos getPreOrderPos(Long orderId, Long orderPosId)
	{
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmPreOrderPos.class).getName());
		criteria.add(Restrictions.eq("orderId", orderId));
		criteria.add(Restrictions.eq("orderPosId", orderPosId));
		WmPreOrderPos preOrderPos = (WmPreOrderPos)criteria.uniqueResult();
		if(!(preOrderPos instanceof WmPreOrderPos)) return null;

		return preOrderPos;
	}

	public long getNextOrderId(){
		return ((BigInteger) session.createSQLQuery("select nextval('seq_pre_orderpos_id')").uniqueResult()).longValue();
	}

	public void addOrUpdatePreOrderPos(WmPreOrderPos preOrderPos){
		WmPreOrderPos preOrderPos0 = getPreOrderPosByArticleQState(preOrderPos.getOrderId(), preOrderPos.getArticleId(), preOrderPos.getQualityStateId());
		session.getTransaction().begin();
		if(preOrderPos0 == null)
		{
			preOrderPos0 = preOrderPos;
		} else
		{
			preOrderPos0.setFactQuantity(preOrderPos0.getFactQuantity() + preOrderPos.getFactQuantity());
			if(preOrderPos0.getFactQuantity()==0L){
				preOrderPos0.setErrorId(4L);

				WmPreOrder preOrder = new PreOrderManager().getPreOrderById(preOrderPos0.getOrderId());
				if(preOrder.getErrorId()==null){
					preOrder.setErrorId(4L);
				}
			}
		}
		session.save(preOrderPos0);

		session.getTransaction().commit();
	}

	public WmPreOrderPos getPreOrderPosByArticleQState(Long orderId,  Long articleId, Long qualityStateId){
		this.criteria.add(Restrictions.eq("orderId", orderId));
		this.criteria.add(Restrictions.eq("articleId", articleId));
		this.criteria.add(Restrictions.eq("qualityStateId", qualityStateId));
		this.criteria.setMaxResults(1);
		return (WmPreOrderPos) this.criteria.uniqueResult();
	}

	public boolean isReadyForUpdate(WmPreOrderPos sourcePos, WmPreOrderPos destinationPos){
		return (destinationPos.getWmPosOrderLink()==null && destinationPos.equals(sourcePos)==false);
	}

	public void markOrderPosBeforeUpdate(WmPreOrder preOrder){
		List<WmPreOrderPos> preOrderPosList = getOrderPosListById(preOrder.getOrderId(), false);
		for(WmPreOrderPos preOrderPos : preOrderPosList){
			preOrderPos.setErrorId(999L);
		}
	}

	public void deleteRemovedPos(WmPreOrder preOrder){
		Session session = HibernateUtil.getSession();

		List<WmPreOrderPos> preOrderPosList = getOrderPosListById(preOrder.getOrderId(), false);
		for(WmPreOrderPos preOrderPos : preOrderPosList){
			if(preOrderPos.getErrorId() != null && preOrderPos.getErrorId() == 999L){
				new HistoryController().addLoadDataEntry(session, 0L, "WmPreOrderPos:delete", preOrderPos.getOrderId()+" : "+preOrderPos.getOrderPosId());
				session.delete(preOrderPos);
			}
		}
	}


	public boolean createOrUpdate(Session session, WmPreOrderPos preOrderPos, WmPreOrder preOrder)
	{
		WmPreOrderPos pos = getPreOrderPos(preOrderPos.getOrderId(), preOrderPos.getOrderPosId());
		if(!(pos instanceof WmPreOrderPos))
		{
			pos = new WmPreOrderPos();
			pos.setOrderId(preOrderPos.getOrderId());
			pos.setOrderPosId(preOrderPos.getOrderPosId());

			pos.setArticleId(preOrderPos.getArticleId());
			pos.setSkuId(preOrderPos.getSkuId());
			pos.setExpectQuantity(preOrderPos.getExpectQuantity());
			pos.setStatusId(1L);
			pos.setQualityStateId(preOrderPos.getQualityStateId());

			pos.setCreateUser(0L);
			pos.setCreateDate(new Timestamp(System.currentTimeMillis()));

			session.persist(pos);
			new HistoryController().addLoadDataEntry(session, 0L, "WmPreOrderPos:create", pos.getOrderId()+" : "+pos.getOrderPosId());
		} else {
			if(isReadyForUpdate(preOrderPos, pos))
			{
				System.out.println("update WmPreOrderPos "+preOrderPos.getOrderId()+":"+preOrderPos.getOrderPosId());
				new HistoryController().addLoadDataEntry(session, 0L, "WmPreOrderPos:update.before", pos.getOrderId()+" : "+pos.getOrderPosId());
				pos.setArticleId(preOrderPos.getArticleId());
				pos.setSkuId(preOrderPos.getSkuId());
				pos.setExpectQuantity(preOrderPos.getExpectQuantity());
				pos.setStatusId(1L);
				//pos.setErrorId(null);
				pos.setQualityStateId(preOrderPos.getQualityStateId());
				pos.setCreateDate(new Timestamp(System.currentTimeMillis()));
				//session.persist(pos);
				new HistoryController().addLoadDataEntry(session, 0L, "WmPreOrderPos:update.after", pos.getOrderId()+" : "+pos.getOrderPosId());
			}

			pos.setErrorId(null);
			session.persist(pos);
		}

		return true;
	}

}

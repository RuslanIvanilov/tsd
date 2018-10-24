package ru.defo.managers;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.defo.model.WmAdvicePos;
import ru.defo.util.HibernateUtil;

public class AdvicePosManager extends ManagerTemplate {
	public AdvicePosManager(){
			super(WmAdvicePos.class);
	}

	public WmAdvicePos getAdviceByPosId(Long adviceId, Long advicePosId)
	{
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmAdvicePos.class).getName());
		criteria.add(Restrictions.eq("adviceId", adviceId));
		criteria.add(Restrictions.eq("advicePosId", advicePosId));
		criteria.setMaxResults(1);
		return (WmAdvicePos) criteria.uniqueResult();
	}

	public WmAdvicePos getAdvicePosByArticleQState(Long adviceId,  Long articleId, Long qualityStateId){
		this.criteria.add(Restrictions.eq("adviceId", adviceId));
		this.criteria.add(Restrictions.eq("articleId", articleId));
		this.criteria.add(Restrictions.eq("qualityStateId", qualityStateId));
		this.criteria.setMaxResults(1);
		return (WmAdvicePos) this.criteria.uniqueResult();
	}

	public void addOrUpdateAdvicePos(WmAdvicePos advicePos){
		WmAdvicePos advPos0 = getAdvicePosByArticleQState(advicePos.getAdviceId(), advicePos.getArticleId(), advicePos.getQualityStateId());
		session.getTransaction().begin();
		if(advPos0 == null)
		{
			advPos0 = advicePos;
		} else
		{
		//if(advPos0.equals(advicePos))
			advPos0.setFactQuantity(advPos0.getFactQuantity() + advicePos.getFactQuantity());
		}
		session.save(advPos0);

		session.getTransaction().commit();
	}

	/*public long getNextHistoryId(){

		SQLQuery query = session.createSQLQuery("select nextval('seq_history_id')");
		int nextHistoryId = (int) ((BigInteger) query.uniqueResult()).longValue();

		return nextHistoryId;
	}*/

	public Long getNextAdvicePosId(){
		return ((BigInteger) session.createSQLQuery("select nextval('seq_advicepos_id')").uniqueResult()).longValue();

	}

	@SuppressWarnings({ "unused" })
	public Long getNextAdviceposByAdvice(Long adviceId, Long articleId, Long skuId, Long qStateId){

		WmAdvicePos advPos = null;
		Long nextLine = null;

		this.criteria.add(Restrictions.eq("adviceId", adviceId));
		this.criteria.add(Restrictions.eq("articleId", articleId));
		this.criteria.add(Restrictions.eq("skuId", skuId));
		this.criteria.add(Restrictions.eq("qualityStateId", qStateId));
		this.criteria.addOrder(Order.desc("advicePosId"));
		this.criteria.setMaxResults(1);
		advPos = (WmAdvicePos) this.criteria.uniqueResult();
		if(advPos != null){
			return advPos.getAdvicePosId();
		} else {
			this.criteria. add(Restrictions.eq("adviceId", adviceId));
			this.criteria.addOrder(Order.desc("advicePosId"));
			this.criteria.setMaxResults(1);
			advPos = (WmAdvicePos) this.criteria.uniqueResult();
			if(advPos != null) return advPos.getAdvicePosId()+1;
		}

		return getNextAdvicePosId();
	}

	@SuppressWarnings("unchecked")
	public List<WmAdvicePos> getAdvicePosByAdviceId(Long adviceId)
	{
		this.criteria.add(Restrictions.eq("adviceId", adviceId));
		this.criteria.addOrder(Order.asc("articleId"));
		return (List<WmAdvicePos>) this.criteria.list();
	}

	public WmAdvicePos getAdvicePosByPK(Long adviceId, Long advicePosId){
		this.criteria.add(Restrictions.eq("adviceId", adviceId));
		this.criteria.add(Restrictions.eq("advicePosId", advicePosId));
		return (WmAdvicePos) this.criteria.uniqueResult();
	}

}

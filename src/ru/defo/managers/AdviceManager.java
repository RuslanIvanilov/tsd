package ru.defo.managers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.filters.AdvicePosFilter;
import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmAdvice;
import ru.defo.model.WmAdvicePos;
import ru.defo.model.WmArticle;
import ru.defo.model.WmPreAdvice;
import ru.defo.model.WmPreAdvicePos;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

public class AdviceManager extends ManagerTemplate{
	public AdviceManager() {
		super(WmAdvice.class);
	}

	public List<WmAdvicePos> getAdvicePosList(AdvicePosFilter advicePosFilter){
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("adviceId", advicePosFilter.getAdviceId().toString(), "eq");
		flt.addFilter("advicePosId", advicePosFilter.getPosflt(), "eq");
		//
		if(advicePosFilter.getArticleidflt() != null || advicePosFilter.getArtdescrflt() != null || advicePosFilter.getSkuflt() != null){
			CriterionFilter artFlt = new CriterionFilter();
			artFlt.addFilter("articleCode", advicePosFilter.getArticleidflt(), "like");
			artFlt.addFilter("description", advicePosFilter.getArtdescrflt(), "like");
			List<WmArticle> articleList = HibernateUtil.getObjectList(WmArticle.class, artFlt.getFilterList(), 0, false, "articleCode");
		}


		//
		flt.addFilter("expectQuantity", advicePosFilter.getExpqtyflt(), "<>");
		flt.addFilter("factQuantity", advicePosFilter.getFactqtyflt(), "<>");
		//flt.addFilter("ctrlQuantity", advicePosFilter.getCtrlqtyflt(), "<>");
		flt.addFilter("statusId", advicePosFilter.getStatusflt(), "eq");
		flt.addFilter("vendorGroupId", advicePosFilter.getVendoridflt(), "eq");

		List<WmAdvicePos> advicePosList = HibernateUtil.getObjectList(WmAdvicePos.class, flt.getFilterList(), 0, true,"");

		Session session = HibernateUtil.getSession();
		if(advicePosList.size()>0)
			for(WmAdvicePos advPos : advicePosList){
				session.refresh(advPos);
			}

		//session.close();

		return advicePosList;
	}


	public WmAdvice getAdviceByClientDocCode(String adviceCode)
	{
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmAdvice.class).getName());
		criteria.add(Restrictions.eq("clientDocCode", adviceCode));
		criteria.setMaxResults(1);
		return (WmAdvice) criteria.uniqueResult();
	}

	public WmAdvice getAdviceByAdviceCode(String adviceCode)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("adviceCode", adviceCode));
		WmAdvice advice = (WmAdvice) HibernateUtil.getUniqObject(WmAdvice.class, restList, false);
		if(advice instanceof WmAdvice)
		{
		 Session session = HibernateUtil.getSession();
		 session.refresh(advice);
		 session.close();
		}
		return advice;
		/*
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmAdvice.class).getName());
		criteria.add(Restrictions.eq("adviceCode", adviceCode));
		criteria.setMaxResults(1);
		return (WmAdvice) criteria.uniqueResult();
		*/
	}

	@SuppressWarnings("unchecked")
	public List<WmAdvice> getAdviceListByClientDocFilter(String codeFilter)
	{
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmAdvice.class).getName());
		criteria.add(Restrictions.ilike("clientDocCode", codeFilter, MatchMode.ANYWHERE));
		criteria.add(Restrictions.in("statusId", new Long[] {1l, 2l, 3l}));
		return (List<WmAdvice>) criteria.list();
	}


	public WmAdvice getAdviceById(Long adviceId)
	{
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmAdvice.class).getName());
		criteria.add(Restrictions.eq("adviceId", adviceId));
		criteria.setMaxResults(1);
		return (WmAdvice) criteria.uniqueResult();
	}

	public void saveOrUpdateAdvice(WmAdvice advice){
		session.getTransaction().begin();
		session.saveOrUpdate(advice);
		session.getTransaction().commit();
	}

	public void setNewAdviceStatus(Long adviceId, Long newStatusId){
		WmAdvice advice = getAdviceById(adviceId);
		advice.setStatusId(newStatusId);
		saveOrUpdateAdvice(advice);
	}

	public void setAdviceDok(Long adviceId, Long binId){
		WmAdvice advice = getAdviceById(adviceId);
		advice.setDockId(binId);
		saveOrUpdateAdvice(advice);
	}

	public void setAdviceCar(Long adviceId, Long carId){
		WmAdvice advice = getAdviceById(adviceId);
		advice.setCarId(carId);
		saveOrUpdateAdvice(advice);
	}

	public long getNextAdviceId(){
		return ((BigInteger) HibernateUtil.getSession().createSQLQuery("select nextval('seq_advice_id')").uniqueResult()).longValue();
	}

	public void delAdvice(WmAdvice advice){

		PreAdviceManager preAdvMgr = new PreAdviceManager();

		List<WmAdvicePos> advicePosList = new AdvicePosManager().getAdvicePosByAdviceId(advice.getAdviceId());
		Session session = HibernateUtil.getSession();
		Transaction trn = session.getTransaction();

		List<WmPreAdvice> preAdviceList = preAdvMgr.getPreAdviceByWmAdviceLink(advice.getAdviceId());
		try{
			trn.begin();
		for(WmPreAdvice preAdvice  : preAdviceList){
			preAdvice.setWmAdviceLink(null);
			preAdvice.setStatusId(DefaultValue.STATUS_CREATED);
			session.persist(preAdvice);
		}


		for(WmAdvicePos advicePos : advicePosList){
			List<WmPreAdvicePos> preAdvicePosList = preAdvMgr.getPreAdvicePosByWmPosAdviceLink(advicePos.getAdvicePosId());
			for(WmPreAdvicePos preAdvicePos : preAdvicePosList){
				preAdvicePos.setWmAdvicePosLink(null);
				session.persist(preAdvicePos);
			}
			session.delete(advicePos);
		}
			session.delete(advice);
			trn.commit();
		}catch(Exception e){
			trn.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}

	}

}

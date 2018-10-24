package ru.defo.managers;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.controllers.HistoryController;
import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmArticle;
import ru.defo.model.WmBin;
import ru.defo.model.WmQualityState;
import ru.defo.model.WmQuant;
import ru.defo.model.WmSku;
import ru.defo.model.WmUnit;
import ru.defo.model.WmUser;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

public class QuantManager extends ManagerTemplate {
	public QuantManager() {
		super();
	}

	public List<WmQuant> getAll()
	{
		return HibernateUtil.getObjectList(WmQuant.class, new CriterionFilter().getFilterList(), 0, true, "quantId");
	}

	public List<WmQuant> getQuantListByUnit(WmUnit unit){
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("unitId", unit.getUnitId().toString(), "eq");
		List<WmQuant> quantList = HibernateUtil.getObjectList(WmQuant.class, flt.getFilterList(), 0, false, "quantId");

		return quantList;
	}


	public WmQuant getQuantByQuantId(Long quantId)
	{
		Criteria criteria = session.createCriteria(WmQuant.class);
		criteria.add(Restrictions.eq("quantId", quantId));
		WmQuant quant = (WmQuant)criteria.uniqueResult();
		if(!(quant instanceof WmQuant)) return null;

		return quant;
	}

	public List<WmQuant> getQuantListByArticleId(Long articleId)
	{
		return getQuantListByArticleId(articleId.toString());
	}

	public List<WmQuant> getQuantListPickAvalable(List<WmQuant> quantList){

		List<WmQuant> avalQuantList = new ArrayList<WmQuant>();

		for(WmQuant quant : quantList){
			WmUnit unit = new UnitManager().getUnitById(quant.getUnitId());
			if(unit.getBinId()!=null){
				WmBin bin = new BinManager().getBinById(unit.getBinId());
				/* BinTypes for picking:  */
				if(new PickManager().isBinPickAval(bin)){
					avalQuantList.add(quant);
				}
			}

		}

		return avalQuantList;
	}

	public WmQuant getQuantByUnitId(Long unitId){
		/*
		WmQuant quant = (WmQuant) HibernateUtil.getSession().createQuery("from WmQuant where unitId = :unitId").setParameter("unitId", unitId).uniqueResult();
		if(!(quant instanceof WmQuant)) return null;
		*/
		WmQuant quant = null;
		if(!(unitId instanceof Long)) return quant;

		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("unitId", unitId.toString(), "eq");
		List<WmQuant> quantList = HibernateUtil.getObjectList(WmQuant.class, flt.getFilterList(), 0, false, "quantId");
		if(quantList.size()==1) quant = quantList.get(0);

		return quant;
	}



	public Long getQuantityByUnitArticle(Long unitId, Long articleId){

		if(!(unitId instanceof Long) || !(articleId instanceof Long)) return null;

		Long qtyId = null;
		session = HibernateUtil.getSession();
		this.criteria = session.createCriteria((WmQuant.class).getName());
		this.criteria.setProjection(Projections.sum("quantity"));
		this.criteria.add(Restrictions.eq("unitId", unitId));
		this.criteria.add(Restrictions.eq("articleId", articleId));
		try{
			qtyId = (Long)this.criteria.uniqueResult();
		} catch(Exception e){
			e.printStackTrace();
		}

		return qtyId;
	}

	public List<WmQuant> getQuantListByUnitArticle(Long unitId, Long articleId){
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("articleId", articleId.toString(), "eq");
		flt.addFilter("unitId", unitId.toString(), "eq");
		List<WmQuant> quantList = HibernateUtil.getObjectList(WmQuant.class, flt.getFilterList(), 0, false,"");

		return quantList;
	}

	public List<WmQuant> getQuantListByArticleId(String articleIdFlt){
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("articleId", articleIdFlt, "eq");

		List<WmQuant> quant = HibernateUtil.getObjectList(WmQuant.class, flt.getFilterList(), 0, false,"");

		return quant;
	}

	/**@deprecated
	 * */
	public WmQuant getQuantByUnitArticle(Long unitId, Long articleId){
		this.criteria = HibernateUtil.getSession().createCriteria((WmQuant.class).getName());
		this.criteria.add(Restrictions.eq("unitId", unitId));
		this.criteria.add(Restrictions.eq("articleId", articleId));
		this.criteria.setMaxResults(1);
		WmQuant quant = (WmQuant) this.criteria.uniqueResult();

		if(!(quant instanceof WmQuant)) return null;

		return quant;
	}

	public WmQuant getQuantByUnitArticle(WmUnit unit, WmArticle article)
	{
		if(!(unit instanceof WmUnit) || !(article instanceof WmArticle)) return null;

		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("unitId", unit.getUnitId() ));
		restList.add(Restrictions.eq("articleId", article.getArticleId() ));

		WmQuant quant = (WmQuant)HibernateUtil.getUniqObject(WmQuant.class, restList, false);
		if(!(quant instanceof WmQuant)) return null;

		return quant;
	}


	public int getCountArticleInUnit(Long unitId){
		return session.createSQLQuery("select count(article_Id) from Wm_Quant where unit_Id = :unitId group by article_Id").setParameter("unitId", unitId).list().size();
	}


	@SuppressWarnings("unchecked")
	public List<WmQuant> getQuantListByUnitId(Long unitId){
		List<WmQuant> quantlist = new ArrayList<WmQuant>();
		quantlist = HibernateUtil.getSession().createQuery("from WmQuant where unitId = :unitId").setParameter("unitId", unitId).list();
		return quantlist;
	}

	public void delQuantListByUnitId(Session session, WmUnit unit, WmUser user)
	{
		List<WmQuant> quantList = getQuantListByUnitId(unit.getUnitId());
		for(WmQuant quant : quantList){
			new HistoryController().addEntry(session, user.getUserId(), unit.getUnitId(), DefaultValue.INVENTORY+"."+DefaultValue.DEL_QUANT, quant);
			session.delete(quant);
		}


	}

	public void delQuantListByUnitId(Session session, WmUnit unit, WmArticle article, WmUser user)
	{
		List<WmQuant> quantList = getQuantListByUnitArticle(unit.getUnitId(),  article.getArticleId());
		for(WmQuant quant : quantList){
			new HistoryController().addEntry(session, user.getUserId(), unit.getUnitId(), DefaultValue.INVENTORY+"."+DefaultValue.DEL_QUANT, quant);
			session.delete(quant);
		}

	}


	/**
	 * @deprecated
	 * */
	public void delQuantListByUnitId(Long unitId, Long userId){
		//session.getTransaction().begin();
		WmQuant quant;

		List<WmQuant> quantList = getQuantListByUnitId(unitId);
		Iterator<WmQuant> iterator = quantList.iterator();
		while(iterator.hasNext()){
			quant = iterator.next();
			new HistoryController().addEntry(userId, unitId, DefaultValue.INVENTORY+"."+DefaultValue.DEL_QUANT, quant);
			session = HibernateUtil.getSession();
			try{
				session.getTransaction().begin();
				session.delete(quant);
				session.getTransaction().commit();
			} catch(Exception e){
				e.printStackTrace();
				session.getTransaction().rollback();
				session.close();
			} /*finally{
				session.close();
			}*/

		}

		//session.getTransaction().commit();
	}

	/**@deprecated
	 * */
	public void delQuantListByUnitId(Long unitId, Long userId, String commentText){
		WmQuant quant;

		List<WmQuant> quantList = getQuantListByUnitId(unitId);
		Iterator<WmQuant> iterator = quantList.iterator();
		while(iterator.hasNext()){
			quant = iterator.next();
			new HistoryController().addEntry(userId, unitId, commentText, quant);
			session = HibernateUtil.getSession();
			try{
				session.getTransaction().begin();
				session.delete(quant);
				session.getTransaction().commit();
			} catch(Exception e){
				e.printStackTrace();
				session.getTransaction().rollback();
				session.close();
			} /*finally{
				session.close();
			}*/
		}
	}

	public void delQuantListByUnitId(Session session, Long unitId, Long userId, String commentText){
		WmQuant quant;

		List<WmQuant> quantList = getQuantListByUnitId(unitId);
		Iterator<WmQuant> iterator = quantList.iterator();
		while(iterator.hasNext()){
			quant = iterator.next();
			new HistoryController().addEntry(session, userId, unitId, commentText, quant);
			session.delete(quant);
		}
	}

	public void delQuant(Session session, WmQuant quant){
		session.delete(quant);
	}

	public void delQuantByQuantId(Long quantId){
		session = HibernateUtil.getSession();
		try{
		  	session.getTransaction().begin();
			session.delete(getQuantByQuantId(quantId));
			session.getTransaction().commit();
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
		} /*finally {
			session.close();
		}*/
	}

	public void addQuantityToQuant(WmQuant quant, Long quantity){
		session = HibernateUtil.getSession();
		try{
			session.getTransaction().begin();
			quant.setQuantity(quant.getQuantity()+quantity);
			session.persist(quant);
			session.getTransaction().commit();
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
		} /*finally{
			session.close();
		} */
	}

	public void createQuant(WmQuant quant){
		session = HibernateUtil.getSession();
		try{
			session.getTransaction().begin();
			session.persist(quant);
			session.getTransaction().commit();
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
		}/*finally{
			session.close();
		}*/

	}

	public WmQuant initQuant(WmArticle article, WmSku sku, WmQualityState state, Long quantity, WmUnit unit, Long adviceId, Long advicePosId, WmUser user)
	{
		WmQuant quant = new WmQuant();
		quant.setQuantId(Long.valueOf(new QuantManager().getNextQuantId()));
		quant.setArticleId(article.getArticleId());
		quant.setSkuId(sku.getSkuId());
		quant.setQualityStateId(state.getQualityStateId());
		quant.setQuantity(quantity);
		quant.setClientId(DefaultValue.DEFAULT_CLIENT_ID);
		quant.setUnitId(unit.getUnitId());
		quant.setAdviceId(adviceId);
		quant.setAdvicePosId(advicePosId);
		quant.setNeedCheck(false);
		quant.setCreateDate(new Timestamp(System.currentTimeMillis()));
		quant.setCreateUser(user.getUserId());

		return quant;
	}

	public long getNextQuantId(){
		return ((BigInteger) HibernateUtil.getSession().createSQLQuery("select nextval('seq_quant_id')").uniqueResult()).longValue();
	}

	@SuppressWarnings("unchecked")
	public List<WmQuant> getListWmQuant(Long unitId){
		Session session = HibernateUtil.getSession();
		List<WmQuant> quantlist = new ArrayList<WmQuant>();
		try {
			quantlist = (List<WmQuant>) session.createQuery("from WmQuant where unitId = :unitId").setParameter("unitId", unitId).list();
			for(WmQuant quant : quantlist){
				session.refresh(quant);
			}
		} catch (Exception e) {
			System.out.println("ERR userid : ["+unitId==null?"":unitId+"] quantlist.size = "+quantlist.size());
			e.printStackTrace();
		} finally {
			//session.close();
		}

		return quantlist;
	}

	public WmQuant getQuantById(Long quantId){
		WmQuant quant = (WmQuant) HibernateUtil.getSession().createQuery("from WmQuant where quantId = :quantId").setParameter("quantId", quantId).uniqueResult();
		return quant;
	}

    public boolean equals(WmQuant quant1, WmQuant quant2){

    	return (
    			quant1.getUnitId().equals(quant2.getUnitId()) &&
    	        quant1.getArticleId().equals(quant2.getArticleId()) &&
    	        quant1.getSkuId().equals(quant2.getSkuId()) &&
    	        quant1.getQualityStateId().equals(quant2.getQualityStateId()) &&
    	        quant1.getAdviceId() == quant2.getAdviceId() &&
    	        quant1.getAdvicePosId() == quant2.getAdvicePosId() &&
    	        quant1.getLotId() == quant2.getLotId()  &&
    	        quant1.getBoxBarcode() == quant2.getBoxBarcode() &&
    	        quant1.getClientId() == quant2.getClientId()
    	        );
	}

    public void updateQuant(WmQuant quant){
    	session = HibernateUtil.getSession();
    	try{
	    	session.getTransaction().begin();
			session.saveOrUpdate(quant);
			session.getTransaction().commit();
    	} catch(Exception e){
    		e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
    	} /*finally{
    		session.close();
    	}*/
    }

}

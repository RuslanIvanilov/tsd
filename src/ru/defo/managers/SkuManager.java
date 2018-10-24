package ru.defo.managers;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Session;

import ru.defo.controllers.HistoryController;
import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmArticle;
import ru.defo.model.WmSku;
import ru.defo.model.WmUser;
import ru.defo.util.HibernateUtil;

public class SkuManager extends ManagerTemplate {
	public SkuManager() {
		super();
	}

	public WmSku getSkuById(Long skuId)
	{
		return (WmSku) HibernateUtil.getSession().createQuery("from WmSku where skuId = :skuId").setParameter("skuId", skuId).uniqueResult();
	}

	public List<WmSku> getSkuList(WmArticle article){
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("articleId", article.getArticleId().toString(), "eq");
		List<WmSku> skuList = HibernateUtil.getObjectList(WmSku.class, flt.getFilterList(), 0, true, "skuId");

		return skuList;
	}


	public void createSku(WmSku sku){
		session = HibernateUtil.getSession();
		session.getTransaction().begin();
		session.save(sku);
		session.getTransaction().commit();
		session.close();
	}

	public long getNextSkuId(){
		return ((BigInteger) HibernateUtil.getSession().createSQLQuery("select nextval('seq_sku_id')").uniqueResult()).longValue();
	}

    public WmSku getBaseSkyByArticle(Long articleId){
    	return (WmSku) HibernateUtil.getSession().createQuery("from WmSku where articleId = :articleId and isBase = true").setParameter("articleId", articleId).uniqueResult();
	}

    public void update(WmSku sku, WmUser user){
    	Session hSession = HibernateUtil.getSession();
		try{
			hSession.getTransaction().begin();
			hSession.update(sku);
			new HistoryController().addSkuChangeEntry(hSession, sku, user);
			hSession.getTransaction().commit();
		} catch(Exception e){
			e.printStackTrace();
			hSession.getTransaction().rollback();
			hSession.close();
		}
    }


}

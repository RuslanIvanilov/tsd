package ru.defo.managers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmArticle;
import ru.defo.model.WmArticleType;
import ru.defo.model.WmBarcode;
import ru.defo.model.WmQuant;
import ru.defo.model.WmVendorGroup;
import ru.defo.util.HibernateUtil;

public class ArticleManager extends ManagerTemplate {
	public ArticleManager() {
		super();
	}

	public WmArticleType getArticlrTypeByArticle(WmArticle article)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("articleTypeId", article.getArticleTypeId()));
		WmArticleType artType = (WmArticleType)HibernateUtil.getUniqObject(WmArticleType.class, restList, true);
		return artType;
	}

	public Set<Long> getArticleIdSet(){
		List<WmQuant> quantList = new QuantManager().getAll();
		List<Long> artIdList = new ArrayList<Long>();
		for(WmQuant quant : quantList){
			artIdList.add(quant.getArticleId());
		}

		Set<Long> set = new LinkedHashSet<>(artIdList);

		return set;
	}

	public List<WmArticle> getAll()
	{
		return HibernateUtil.getObjectList(WmArticle.class, new CriterionFilter().getFilterList(), 0, false, "articleCode");
	}

	@SuppressWarnings("unchecked")
	public List<WmVendorGroup> getVendorGroupList() {
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(WmVendorGroup.class);
		criteria.addOrder(Order.asc("vendorGroupId"));
		List<WmVendorGroup> vendGroupList = (List<WmVendorGroup>) criteria.list();
		session.close();

		return vendGroupList;
	}

	public WmVendorGroup getVendorGroupById(Long vendorGroupId){
		if(vendorGroupId == null) return null;
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("vendorGroupId", vendorGroupId));

		WmVendorGroup vendorGroup = null;

		try{
			vendorGroup = (WmVendorGroup) HibernateUtil.getUniqObject(WmVendorGroup.class, restList, false);
		}catch(Exception e){
			System.out.println("ERROR ArticleManager.getVendorGroupById wrong vendorGroupId = ["+vendorGroupId+"]");
		}

		if(!(vendorGroup instanceof WmVendorGroup)) return null;
		return vendorGroup;
	}

	public WmArticle getArticleById(Long articleId){
		/*
		Session session = HibernateUtil.getSession();
		WmArticle article =  null;

		try{
			article = (WmArticle) session.createQuery("from WmArticle where articleId = :articleId").setParameter("articleId", articleId).uniqueResult();
			if(article instanceof WmArticle)
				session.refresh(article);
		} catch(Exception e){
			System.out.println("ERROR: ArticleManager.getArticleById articleId: "+articleId);
			e.printStackTrace();
		}
		*/
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("articleId", articleId));
		WmArticle article = (WmArticle)HibernateUtil.getUniqObject(WmArticle.class, restList, false);

	    return article;
	}

	public WmArticle getArticleByCode(String articleCode){
		WmArticle article = null;
		try{
		  article = (WmArticle) HibernateUtil.getSession().createQuery("from WmArticle where articleCode = :articleCode").setParameter("articleCode", articleCode).uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return article;
	}

	/**
	 * @deprecated
	 * */
	public void createArticle(WmArticle article){
		session = HibernateUtil.getSession();
		session.getTransaction().begin();
		session.save(article);
		session.getTransaction().commit();
		session.close();
	}

	public void saveArticle(WmArticle article){
		session = HibernateUtil.getSession();
		session.getTransaction().begin();
		session.persist(article);
		session.getTransaction().commit();
		session.close();
	}

	public long getNextArticleId(){
		return ((BigInteger) HibernateUtil.getSession().createSQLQuery("select nextval('seq_article_id')").uniqueResult()).longValue();
	}

	@SuppressWarnings("unchecked")
	public List<WmArticle> getArticleListByArtCodeFilter(String articleCodeFilter){
		return (List<WmArticle>)HibernateUtil.getSession().createQuery("from WmArticle where ( upper(articleCode) like '%' || :articleCodeFilter || '%' or upper(description) like '%' || :articleCodeFilter || '%') and articleKitId is not null")
				                           .setParameter("articleCodeFilter", articleCodeFilter.toUpperCase()).list();
	}

	@SuppressWarnings("unchecked")
	public List<WmArticle> getArticleList(){
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmArticle.class).getName());
		List<WmArticle> articles = null;

		try{
			articles = (List<WmArticle>) criteria.list();
			for(WmArticle article : articles) session.refresh(article);
		} catch(Exception e){
			e.printStackTrace();
		}

		return articles;
	}

	public List<WmBarcode> getBarcodeListAll(){
		CriterionFilter flt = new CriterionFilter();
		List<WmBarcode> bcList =  HibernateUtil.getObjectList(WmBarcode.class, flt.getFilterList(), 0, false, "articleId");

		return bcList;
	}

}

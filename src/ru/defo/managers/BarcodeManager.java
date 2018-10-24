package ru.defo.managers;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmArticle;
import ru.defo.model.WmBarcode; 
import ru.defo.util.HibernateUtil;

public class BarcodeManager extends ManagerTemplate {

	public BarcodeManager() {
		super();

	}

	public WmBarcode gerBarCode(String barCode)
	{
		//WmBarcode bc = null;

		String bctxt = ru.defo.util.Bc.symbols(barCode.trim().replace("%23","#").replace("%40","@").replace("%2B","+").replace("%2A","*"));
/*
		bc = (WmBarcode) HibernateUtil.getSession().createQuery("from WmBarcode where barCode = :barCode")
				.setParameter("barCode", bctxt.trim()).uniqueResult();
		if(bc == null && bctxt.length()>0){
			bctxt = bctxt.substring(0, bctxt.length()-1);
			bc = (WmBarcode) HibernateUtil.getSession().createQuery("from WmBarcode where barCode = :barCode")
					.setParameter("barCode", bctxt.trim()).uniqueResult();
		}
*/
		WmBarcode bc = null;

		try{
			Criteria criteria = HibernateUtil.getSession().createCriteria(WmBarcode.class);
			criteria.add(Restrictions.ilike("barCode", bctxt, MatchMode.ANYWHERE));
			criteria.setMaxResults(1);
			bc = (WmBarcode) criteria.uniqueResult();
		}catch(HibernateException e){
			e.printStackTrace();
		}

		if(bc instanceof WmBarcode){
			return bc ;
		} else
		return null;
/*
		if(bc instanceof WmBarcode)
			session.refresh(bc);

		return bc;
*/
	}

	public String getLastBarcodeArticle(Long articleId){

		return (String)HibernateUtil.getSession().createQuery("select max(barCode)from WmBarcode where articleId = :articleId")
				.setParameter("articleId", articleId).uniqueResult();
	}

	public void createBarcode(WmBarcode barcode)
	{
		session = HibernateUtil.getSession();
		session.getTransaction().begin();
		session.persist(barcode);
		session.getTransaction().commit();
		session.close();
	}

	public void deleteBarCode(WmBarcode barcode){
		session = HibernateUtil.getSession();
		session.getTransaction().begin();
		session.delete(barcode);
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<WmBarcode> getBarcodeListByFilter(String barcodeFilter){
		return (List<WmBarcode>)HibernateUtil.getSession().createQuery("from WmBarcode where not barCode like '%' || :barcodeFilter || '%' and length(barCode) = 12").setParameter("barcodeFilter", barcodeFilter).list();
	}

	public WmBarcode getBcByArticle(WmArticle article)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("articleId", article.getArticleId().toString(), "eq");
		List<WmBarcode> bcList = HibernateUtil.getObjectList(WmBarcode.class, flt.getFilterList(), 0, false, "skuId", true);
		WmBarcode bc = bcList.get(0);

		return bc;
	}

	public String BcByArticleId(Long articleId){
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(WmBarcode.class);
		criteria.add(Restrictions.eq("articleId", articleId));
		criteria.addOrder(Order.desc("skuId"));
		criteria.setMaxResults(1);
		WmBarcode bc = (WmBarcode) criteria.uniqueResult();
		session.close();
		if(bc != null){
			return bc.getBarCode();
		} else
		return "";
	}

}

package ru.defo.managers;
 
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.defo.filters.CriterionFilter; 
import ru.defo.model.views.Varticleqty;
import ru.defo.model.views.Varticleqtyr;
import ru.defo.util.HibernateUtil;

public class VArticleQtyManager extends ManagerTemplate {

	public VArticleQtyManager(){
		super(Varticleqty.class);
	}
    /* Not ZERO values !!!*/
	public List<Varticleqtyr> getArticleQtyrListByFilter(String articleFilter, String articleDescFilter, String qtyFilter,
			String qtyFactFilter, String qtyHostFilter, String dWmsFactFilter,
			String dWmsHostFilter, String dFactHostFilter, int rowCount)
	{ 
		String[] strArray = null; 

		CriterionFilter flt = new CriterionFilter();
		if(articleFilter != null) flt.addFilter("articleCode", articleFilter, "like");

		if(articleDescFilter != null) strArray = articleDescFilter.split(" ");
		for(String chars : strArray){ flt.addFilter("articleName", chars,"like"); }

		setQtyFilter(qtyFilter, "qtySum", flt);
		setQtyFilter(qtyHostFilter, "hostQuantity", flt);
		setQtyFilter(qtyFactFilter, "factQuantity", flt);

		setQtyFilter(dWmsFactFilter, "dWmsFact", flt);
		setQtyFilter(dWmsHostFilter, "dWmsHost", flt);
		setQtyFilter(dFactHostFilter, "dFactHost", flt);


		List<Varticleqtyr> vartQtyListr = HibernateUtil.getObjectList(Varticleqtyr.class, flt.getFilterList(), rowCount, true, "articleCode");

		return vartQtyListr;
	}

	public List<Varticleqty> getArticleQtyListByFilter(String articleFilter, String articleDescFilter, String qtyFilter,
														String qtyFactFilter, String qtyHostFilter, String dWmsFactFilter,
														String dWmsHostFilter, String dFactHostFilter, int rowCount)
	{
		 
		String[] strArray = null; 

		CriterionFilter flt = new CriterionFilter();
		if(articleFilter != null) flt.addFilter("articleCode", articleFilter, "like");

		if(articleDescFilter != null) strArray = articleDescFilter.split(" ");
		for(String chars : strArray){ flt.addFilter("articleName", chars,"like"); }

		setQtyFilter(qtyFilter, "qtySum", flt);
		setQtyFilter(qtyHostFilter, "hostQuantity", flt);
		setQtyFilter(qtyFactFilter, "factQuantity", flt);

		setQtyFilter(dWmsFactFilter, "dWmsFact", flt);
		setQtyFilter(dWmsHostFilter, "dWmsHost", flt);
		setQtyFilter(dFactHostFilter, "dFactHost", flt);


		List<Varticleqty> vartQtyList = HibernateUtil.getObjectList(Varticleqty.class, flt.getFilterList(), rowCount, true, "articleCode");

		return vartQtyList;
	}

	private void setQtyFilter(String qtyFilter, String fieldName, CriterionFilter flt)
	{
		String qty = null;
		if(!qtyFilter.isEmpty()){
			try{
				qty = qtyFilter.replaceAll("[^\\.0123456789]","");
			}catch(Exception e){
				e.printStackTrace();
			}

			switch(qtyFilter.charAt(0)){
			case '>' :
				flt.addFilter(fieldName, qty, "gt");
			break;
			case '<' :
				flt.addFilter(fieldName, qty, "lt");
				//flt.addFilter(fieldName, "0", "gt");
			break;
			default:
				flt.addFilter(fieldName, qty, "eq");
			}
		}

	}

	@SuppressWarnings("unchecked")
	public List<Varticleqty> getVarticleQtyListByFilter(String articleFilter, String articleDescFilter, String qtyFilter, int rowCount){
		String[] strArray = null;
		Long qty = 0L;
		this.criteria = session.createCriteria(Varticleqty.class);

		if(articleFilter != null)
			this.criteria.add(Restrictions.ilike("articleCode", articleFilter, MatchMode.ANYWHERE));

		if(articleDescFilter != null)

			strArray = articleDescFilter.split(" ");
			for(String chars : strArray){
				this.criteria.add(Restrictions.ilike("articleName", chars, MatchMode.ANYWHERE));
			}

		//this.criteria.add(Restrictions.ilike("articleName", articleDescFilter, MatchMode.ANYWHERE));

		if(!qtyFilter.isEmpty()){
			try{
				qty = Long.decode(qtyFilter.replaceAll("[^\\.0123456789]",""));
			}catch(Exception e){
				e.printStackTrace();
			}

			switch(qtyFilter.charAt(0)){
			case '>' :
				this.criteria.add(Restrictions.gt("qtySum", qty));
			break;
			case '<' :
				this.criteria.add(Restrictions.lt("qtySum", qty));
				this.criteria.add(Restrictions.gt("qtySum", 0L));
			break;
			default:
				this.criteria.add(Restrictions.eq("qtySum", qty));
			}
		}

		if(rowCount > 0)
			this.criteria.setMaxResults(rowCount);

		this.criteria.addOrder(Order.asc("articleCode"));

		List<Varticleqty> vartQtyList = (List<Varticleqty>) this.criteria.list();

		try{
			for(Varticleqty artqty : vartQtyList){
				session.refresh(artqty);
			}
		} catch(Exception e){
			System.out.println("ERROR VArticleQtyManager.getVarticleQtyListByFilter --> session.refresh(artqty)");
			e.printStackTrace();
		}

		return vartQtyList;
	}

}

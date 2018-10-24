package ru.defo.managers;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.controllers.ArticleController;
import ru.defo.model.WmArticle;
import ru.defo.model.WmPreAdvice;
import ru.defo.model.WmPreAdvicePos;
import ru.defo.model.WmSku;
import ru.defo.model.local.advice.AdviceInvoice;
import ru.defo.model.local.advice.Element;
import ru.defo.model.local.advice.Nomenclature;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

public class PreAdvicePosManager extends ManagerTemplate {

	public PreAdvicePosManager() {
		super(WmPreAdvicePos.class);
	}

	public WmPreAdvicePos getByAdviceArticle(WmPreAdvice preAdvice, WmArticle article)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("adviceId", preAdvice.getAdviceId()));
		restList.add(Restrictions.eq("articleId", article.getArticleId()));
		WmPreAdvicePos preAdvicePos = (WmPreAdvicePos)HibernateUtil.getUniqObject(WmPreAdvicePos.class, restList, false);

		return preAdvicePos;
	}

	public WmPreAdvicePos getByAdvicePos(WmPreAdvicePos pos)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("adviceId", pos.getAdviceId()));
		restList.add(Restrictions.eq("advicePosId", pos.getAdvicePosId()));
		WmPreAdvicePos preAdvicePos = (WmPreAdvicePos)HibernateUtil.getUniqObject(WmPreAdvicePos.class, restList, false);

		return preAdvicePos;
	}

	public long getNextAdvicePosId(){
		return ((BigInteger) HibernateUtil.getSession().createSQLQuery("select nextval('seq_pre_advicepos_id')").uniqueResult()).longValue();
	}

	private WmPreAdvicePos initPreAdvicePos(WmPreAdvice preAdvice, Element elm)
	{
	    WmArticle article = new ArticleManager().getArticleByCode(elm.getCode());
	    WmSku sku = new ArticleController().getBaseSkuByArticleId(article.getArticleId());

	    Long quantityLong = Long.valueOf(elm.getQuantity());

		WmPreAdvicePos pos = new WmPreAdvicePos();

		pos.setAdviceId(preAdvice.getAdviceId());
		pos.setAdvicePosId(getNextAdvicePosId());
		pos.setCreateDate(new Timestamp(System.currentTimeMillis()));
		pos.setCreateUser(0L);
		pos.setArticleId(article.getArticleId());
		pos.setExpectQuantity(quantityLong);
		pos.setStatusId(DefaultValue.STATUS_CREATED);
		pos.setQualityStateId(1L);
		pos.setSkuId(sku.getSkuId());

		return pos;
	}

	public boolean toPersist(WmPreAdvicePos pos)
	{
		WmPreAdvicePos advPos = getByAdvicePos(pos);
		return(advPos == null || advPos.getWmAdvicePosLink() == null || advPos.getErrorId().intValue() != pos.getErrorId().intValue());

	}

	public List<WmPreAdvicePos> initPosListByAdviceInvoice(WmPreAdvice preAdv, AdviceInvoice advInv)
	{
		List<WmPreAdvicePos> posList = new ArrayList<WmPreAdvicePos>();

		for(Nomenclature nomen : advInv.getNomenclatureList()){
			for(Element elm : nomen.getElementList()){
				WmPreAdvicePos pos = initPreAdvicePos(preAdv, elm);
				posList.add(pos);
			}
		}

		return posList;
	}

	@SuppressWarnings("unchecked")
	public List<WmPreAdvicePos> getAdvicePosListById(Long adviceId)
	{
		this.criteria.add(Restrictions.eq("adviceId", adviceId));
		return (List<WmPreAdvicePos>) this.criteria.list();
	}


	public WmPreAdvicePos getPreAdvicePosByArticleQState(Long adviceId,  Long articleId, Long qualityStateId){

		List<SimpleExpression> restList = new LinkedList<SimpleExpression>();
		restList.add(Restrictions.eq("adviceId", adviceId));
		restList.add(Restrictions.eq("articleId", articleId));
		restList.add(Restrictions.eq("qualityStateId", qualityStateId));

		WmPreAdvicePos preAdvPos = (WmPreAdvicePos)HibernateUtil.getUniqObject(WmPreAdvicePos.class, restList, true);
		if(!(preAdvPos instanceof WmPreAdvicePos)) return null;

		return preAdvPos;
	}


	public void addOrUpdatePreAdvicePos(WmPreAdvicePos advicePos){
		WmPreAdvicePos advPos0 = getPreAdvicePosByArticleQState(advicePos.getAdviceId(), advicePos.getArticleId(), advicePos.getQualityStateId());
		Session session = HibernateUtil.getSession();
		session.getTransaction().begin();
		if(advPos0 == null)
		{
			advPos0 = advicePos;
		} else
		{
		//if(advPos0.equals(advicePos))
			advPos0.setFactQuantity(advPos0.getFactQuantity() + advicePos.getFactQuantity());
		}

		try{
		session.save(advPos0);
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
		} finally{
			session.close();
		}

	}

}

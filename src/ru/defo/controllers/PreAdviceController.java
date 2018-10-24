package ru.defo.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.defo.filters.CriterionFilter;
import ru.defo.managers.AdvicePosManager;
import ru.defo.managers.PreAdviceManager;
import ru.defo.managers.PreAdvicePosManager;
import ru.defo.managers.PreOrderManager;
import ru.defo.model.WmAdvicePos;
import ru.defo.model.WmAdviceType;
import ru.defo.model.WmArticle;
import ru.defo.model.WmPreAdvice;
import ru.defo.model.WmPreAdvicePos;
import ru.defo.model.WmStatus;
import ru.defo.model.WmUser;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;


public class PreAdviceController {

	public void createAdvice(String preAdviceIdTxt, Long nextAdviceId, String expDate) {
		if (nextAdviceId != null && !preAdviceIdTxt.isEmpty() && !expDate.isEmpty()) {

			Long preAdviceId = null;

			try {
				preAdviceId = Long.decode(preAdviceIdTxt);
			} catch (Exception e) {
				e.printStackTrace();
			}

			new PreAdviceManager().addPreAdviceToAdvice(preAdviceId, nextAdviceId, AppUtil.stringToTimestamp(expDate));
		}
	}

	public List<WmPreAdvicePos> getPreAdvicePosList(HttpSession session)
	{
		List<WmPreAdvicePos> resultPosList = new ArrayList<WmPreAdvicePos>();
		Long adviceId = Long.valueOf(session.getAttribute("adviceid").toString());
		List<WmArticle> articleList = new ArrayList<WmArticle>();

		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("adviceId", adviceId.toString(), "eq");
		if(session.getAttribute("posflt")!=null) flt.addFilter("advicePosId", session.getAttribute("posflt").toString(), "eq");
		if(session.getAttribute("expqtyflt")!=null) flt.addFilter("expectQuantity", session.getAttribute("expqtyflt").toString(), "<>");
		if(session.getAttribute("statusposflt")!=null) flt.addFilter("statusId", session.getAttribute("statusposflt").toString(), "eq");

		List<WmPreAdvicePos> preAdvicePosList = HibernateUtil.getObjectList(WmPreAdvicePos.class, flt.getFilterList(), 0, false, "advicePosId");

		if(session.getAttribute("articleidflt") != null || session.getAttribute("artdescrflt") != null ){
			CriterionFilter artFlt = new CriterionFilter();
			if(session.getAttribute("articleidflt")!=null)artFlt.addFilter("articleCode", session.getAttribute("articleidflt").toString(), "like");
			if(session.getAttribute("artdescrflt")!=null)artFlt.addFilter("description", session.getAttribute("artdescrflt").toString(), "like");
			articleList = HibernateUtil.getObjectList(WmArticle.class, artFlt.getFilterList(), 0, false, "articleId");
		}

		if(articleList.size()>0){
			resultPosList.clear();
			for(WmArticle article : articleList){
				for(WmPreAdvicePos pos : preAdvicePosList){
					if(article.getArticleId().longValue() == pos.getArticleId().longValue()){
						resultPosList.add(pos);
					}
				}
			}
		} //else{ resultPosList = preAdvicePosList; }

		return resultPosList;
	}

	public WmPreAdvice getPreAdviceByAdviceId(Long adviceId)
	{
		return new PreAdviceManager().getPreAdviceById(adviceId);
	}


	public List<WmStatus> getStatusList() {
		return  new PreOrderManager().getStatusList();
	}

	public List<WmAdviceType> getAdviceTypeList() {
		return new PreAdviceManager().getAdviceTypeList();
	}


	public List<WmPreAdvice> getPreAdviceList(HttpSession session) {

		int rowCnt;

		try {
			rowCnt = Integer.decode(session.getAttribute("rowcount").toString());
		} catch (Exception e) {
			e.printStackTrace();
			rowCnt = 0;
		}

		return new PreAdviceManager().getPreAdviceListByFilter(session, rowCnt);
	}

	public String getClientDocDescription(Long wmAdviceLink) {

		return new PreOrderManager().getClientDocDescription(wmAdviceLink);
	}


	public void delAdviceLink(WmUser user, Long adviceId) {
		WmPreAdvice preAdvice = new PreAdviceManager().getPreAdviceById(adviceId);
		if (!(preAdvice instanceof WmPreAdvice) || preAdvice.getWmAdviceLink() == null)
			return;

		Session session = HibernateUtil.getSession();
		Transaction trn = session.getTransaction();

		try {
			trn.begin();

			List<WmPreAdvicePos> preAdvicePosList = new PreAdvicePosManager().getAdvicePosListById(adviceId);
			if (preAdvicePosList != null) {
				for (WmPreAdvicePos preAdvicePos : preAdvicePosList) {

					WmAdvicePos advicePos = new AdvicePosManager().getAdviceByPosId(preAdvice.getWmAdviceLink(),
							preAdvicePos.getWmAdvicePosLink());
					if (!(advicePos instanceof WmAdvicePos))
						continue;

					minusAdvicePosExpectedQty(session, preAdvicePos, advicePos);
					// clearWmOrderLink(preOrderPos);
				}
			}
			System.out.println("to HIstory preAdvice "+preAdvice.getAdviceCode()+" / link: "+preAdvice.getWmAdviceLink());

			new HistoryController().addEntry_DelAdviceLink(session, user, preAdvice);
			clearWmAdviceLink(session, preAdvice);
			trn.commit();
		} catch (Exception e) {
			trn.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	private void minusAdvicePosExpectedQty(Session session, WmPreAdvicePos preAdvicePos, WmAdvicePos advicePos) {
		advicePos.setFactQuantity(null);
		advicePos.setExpectQuantity(advicePos.getExpectQuantity() - preAdvicePos.getExpectQuantity());
		if (advicePos.getExpectQuantity() < 1) {
			session.delete(advicePos);
		} else {
			session.persist(advicePos);
		}

		preAdvicePos.setWmAdvicePosLink(null);
		session.update(preAdvicePos);

	}

	private void clearWmAdviceLink(Session session, WmPreAdvice preAdvice) {
		preAdvice.setWmAdviceLink(null);
		preAdvice.setStatusId(DefaultValue.STATUS_CREATED);
		session.merge(preAdvice);

	}

}

package ru.defo.controllers;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;

import ru.defo.managers.AdvicePosManager;
import ru.defo.managers.ArticleManager;
import ru.defo.managers.SkuManager;
import ru.defo.model.WmAdvicePos;
import ru.defo.model.WmArticle;
import ru.defo.model.WmQualityState;
import ru.defo.model.WmSku;

public class AdvicePosController {

	AdvicePosManager advPosMgr;
	WmAdvicePos advicePos = null;

	public AdvicePosController(){
		advPosMgr = new AdvicePosManager();
	}

	public WmAdvicePos initAdvicePos(Long adviceId, Long articleId, Long skuId, Long expectQuantity, Long factQuantity, Long qualityStateId,
			Long lotId, Long statusId, Long userId){

		/*
		 Long modUser;
    	 Timestamp modDate;
		*/

		advicePos = new WmAdvicePos();
		advicePos.setAdviceId(adviceId);
		advicePos.setAdvicePosId(advPosMgr.getNextAdviceposByAdvice(adviceId, articleId, skuId, qualityStateId));
		advicePos.setArticleId(articleId);
		advicePos.setSkuId(skuId);
		advicePos.setExpectQuantity(expectQuantity);
		advicePos.setFactQuantity(factQuantity);
		advicePos.setQualityStateId(qualityStateId);
		advicePos.setLotId(lotId);
		advicePos.setStatusId(statusId);
		advicePos.setCreateUser(userId);
		advicePos.setCreateDate(new Timestamp(System.currentTimeMillis()));
		advPosMgr.addOrUpdateAdvicePos(advicePos);

		return advicePos;
	}


	public void addAdvicePos(WmAdvicePos advPos){
		new AdvicePosManager().addOrUpdateAdvicePos(advPos);
	}

	public List<WmAdvicePos> getAdvicePosByAdviceId(String adviceId)
	 {
		 return advPosMgr.getAdvicePosByAdviceId(Long.decode(adviceId));
	 }

	public void fillAdvicePosInfo(HttpSession session, Long adviceId, Long advicePosId){
		WmAdvicePos advPos = new AdvicePosManager().getAdvicePosByPK(adviceId, advicePosId);
		WmArticle article = new ArticleManager().getArticleById(advPos.getArticleId());
		WmSku sku = new SkuManager().getSkuById(advPos.getSkuId());
		WmQualityState qState = new QualityStateController().getQStateById(advPos.getQualityStateId());

		if(advPos != null){
			session.setAttribute("articleid", advPos.getArticleId());
			session.setAttribute("quantity", advPos.getFactQuantity());
			session.setAttribute("expquantity", advPos.getExpectQuantity());
		}

		if(article != null){
			session.setAttribute("articlecode", article.getArticleCode());
			session.setAttribute("articlename", article.getDescription());
		}

		if(sku != null){
			session.setAttribute("skuname", sku.getDescription());
			session.setAttribute("skuid", sku.getSkuId());
		}

		if(qState != null){
			session.setAttribute("qualityname", qState.getDescription());
			session.setAttribute("qualitystateid", advPos.getQualityStateId());
		}

	}

}

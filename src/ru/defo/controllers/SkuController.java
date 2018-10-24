package ru.defo.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import ru.defo.managers.SkuManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmSku;
import ru.defo.model.WmUser;
import ru.defo.util.AppUtil;

public class SkuController {

	public List<WmSku> getSkuList(WmArticle article)
	{
		return new SkuManager().getSkuList(article);
	}


	public WmSku getSku(Object skIdObj){
		Long skuId;
		try{
			skuId = Long.valueOf(skIdObj.toString());
		}catch(NumberFormatException e){
			e.printStackTrace();
			return null;
		}

		return new SkuManager().getSkuById(skuId);
	}

	public WmSku getSku(HttpSession httpSession){
		return getSku(httpSession.getAttribute("skuid"));
	}

	public WmSku getSku(Long skuId) {
		return new SkuManager().getSkuById(skuId);
	}

	public WmSku getBaseSkuByArticleCode(String articleCode){
		ArticleController artCtrl = new ArticleController();
		WmArticle  article = artCtrl.getArticleByCode(articleCode);
		if(!(article instanceof WmArticle)) return null;

		WmSku sku = artCtrl.getBaseSkuByArticleId(article.getArticleId());
		if(!(sku instanceof WmSku)) return null;
		return sku;
	}

	public void update(HttpSession session)
	{
		WmSku sku = getSku(session.getAttribute("skuid"));
		WmUser user = new UserController().getUserById(session.getAttribute("userid"));
		if(user==null) return;

		if(sku != null)
		{
			if(session.getAttribute("descrtxt")!=null && !session.getAttribute("descrtxt").toString().isEmpty()) sku.setDescription(session.getAttribute("descrtxt").toString());
			sku.setIsBase(session.getAttribute("isbasetxt").toString().equals("true"));

			Long crushId = AppUtil.strToLong(session.getAttribute("crushtxt").toString());
			Long weight = AppUtil.strToLong(session.getAttribute("weighttxt").toString());
			Long length = AppUtil.strToLong(session.getAttribute("lengthtxt").toString());
			Long width = AppUtil.strToLong(session.getAttribute("widthtxt").toString());
			Long heigh = AppUtil.strToLong(session.getAttribute("heightxt").toString());

			if(crushId != null)sku.setSkuCrushId(crushId);
			if(weight != null)sku.setWeight(weight);
			if(length != null)sku.setLength(length);
			if(width != null)sku.setWidth(width);
			if(heigh != null)sku.setHeigh(heigh);

			new SkuManager().update(sku, user);
		}
	}




}

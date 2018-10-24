package test.quant;

import java.util.List;

import org.junit.Test;

import ru.defo.controllers.QuantController;
import ru.defo.managers.ArticleManager;
import ru.defo.managers.QuantManager;
import ru.defo.managers.UnitManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmQuant;
import ru.defo.model.WmUnit;

public class GetQuantByUnitArticle {

	@Test
	public void test() {
/*
		WmUnit unit = new UnitController().getUnitByUnitCode("EU00012856");
		//WmArticle article = new ArticleManager().getArticleByCode("1@8187+00");
		WmArticle article = new ArticleController().getArticleByBarcode("1@8187+00");

		//WmQuant quant = new QuantManager().getQuantByUnitArticle(unit, article);
		//WmQuant quant = new QuantController().getQuantByUnitArticle(unit, article);
		//WmQuant quant = new QuantController().getQuantByUnitArticle(unit.getUnitCode(), article.getArticleCode());
		WmQuant quant = new QuantController().getQuantByUnitArticle(unit, article);
		//if(!(quant instanceof WmQuant)) fail("quant is not WmQuant object !");
		System.out.println("quant : "+quant.getQuantId());
*/
		int i=0;
		List<WmUnit> unitList = new UnitManager().getUnitListAll();
		for(WmUnit u : unitList)
		{
			System.out.println(u.getUnitCode());
			List<WmQuant> quantList = new QuantManager().getListWmQuant(u.getUnitId());
			for(WmQuant q : quantList)
			{
				i++;
				System.out.println(i+"\t\tquantId: "+q.getQuantId());
				WmArticle a = new ArticleManager().getArticleById(q.getArticleId());
				System.out.println("articleId: "+a.getArticleId()+" code "+a.getArticleCode());
				WmQuant quantResult = new QuantController().getQuantByUnitArticle(u, a);
				System.out.println(u.getUnitCode()+" / "+a.getArticleCode()+" / "+ quantResult.getQuantId()+" qty: "+quantResult.getQuantity());

			}

		}


	}

}

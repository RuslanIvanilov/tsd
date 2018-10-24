package test.article;

import org.junit.Test;

import ru.defo.managers.ArticleManager;
import ru.defo.managers.BarcodeManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmBarcode;

public class GetBarCode {

	@Test
	public void test() {
		WmBarcode bc = new BarcodeManager().gerBarCode("A4814917207737");
		WmArticle article = new ArticleManager().getArticleById(bc.getArticleId());
		System.out.println(bc.getArticleId()+" --> "+article.getArticleCode());
	}

}

package test.article;

import org.junit.Test;

import ru.defo.controllers.ArticleController;
import ru.defo.managers.ArticleManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmVendorGroup;

public class VendorGroupByArticle {

	@Test
	public void test() {
		WmArticle article = new ArticleManager().getArticleByCode("7#74937+00");  //0#00020+00
		WmVendorGroup group = new ArticleController().getVendorGroupByArticle(article);
		if(group != null) System.out.println(group.getVendorGroupId()+" / "+group.getDescription());
		System.out.println("End Test.");
	}

}

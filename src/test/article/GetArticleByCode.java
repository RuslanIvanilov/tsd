package test.article;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ru.defo.controllers.ArticleController;
import ru.defo.model.WmArticle;

public class GetArticleByCode {

	@Test
	public void test() {
		List<String> unitBarCodeList = Arrays.asList("A7#88147+00", "D7#88147+00", null, "");
		for(String articleCode : unitBarCodeList){
			WmArticle article = new ArticleController().getArticleByCode(articleCode);
			System.out.println((article==null?"null":article.getArticleId())+" / "+(article==null?"null":article.getArticleCode()));
		}
	}

}

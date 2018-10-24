package test.article;

import java.util.HashSet;

import org.junit.Test;

import ru.defo.controllers.ArticleController;
import ru.defo.model.Sku;

public class updateArticle {

	@Test
	public void test() {
		Sku sku = new Sku("7#63047",
				          "OTL СТОн2.46 Стол ученич. рег, двухм.с накл (120*50)H64-76(р4-6)(бук/сер)",
				          "7#63047+02",
				          "3/3Каркас+фурOTL СТОн2.46Стол учен.рег,двухм.с накл(120*50)H64-76(р4-6)(бук/сер)",
				          new HashSet<String>()
				          );

		new ArticleController().createSku(sku);
	}

}

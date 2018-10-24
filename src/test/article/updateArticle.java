package test.article;

import java.util.HashSet;

import org.junit.Test;

import ru.defo.controllers.ArticleController;
import ru.defo.model.Sku;

public class updateArticle {

	@Test
	public void test() {
		Sku sku = new Sku("7#63047",
				          "OTL ����2.46 ���� ������. ���, �����.� ���� (120*50)H64-76(�4-6)(���/���)",
				          "7#63047+02",
				          "3/3������+���OTL ����2.46���� ����.���,�����.� ����(120*50)H64-76(�4-6)(���/���)",
				          new HashSet<String>()
				          );

		new ArticleController().createSku(sku);
	}

}

package test.inventory;

import java.util.List;

import org.junit.Test;

import ru.defo.filters.CriterionFilter;
import ru.defo.managers.InventoryManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmInventoryPos;
import ru.defo.util.HibernateUtil;

public class GetActualInventPos {

	@Test
	public void test() {
		CriterionFilter flt = new CriterionFilter();
		List<WmArticle> articleList = HibernateUtil.getObjectList(WmArticle.class, flt.getFilterList(), 0, true, "articleCode");

		for(WmArticle article : articleList){
			WmInventoryPos invPos = new InventoryManager().getActualInventPos(article.getArticleId());
			System.out.println("art:"+article.getArticleId()+":"+article.getArticleCode()+" :: "+invPos.getInventoryId()+"/"+invPos.getInventoryPosId()+"/"+invPos.getArticleCode());
		}

	}

}

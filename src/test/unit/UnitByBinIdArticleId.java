package test.unit;

import java.util.List;

import org.junit.Test;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.BinController;
import ru.defo.managers.UnitManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmBin;
import ru.defo.model.WmUnit;

public class UnitByBinIdArticleId {

	@Test
	public void test() {

		WmArticle article = new ArticleController().getArticleByCode("8#04178+00");
		WmBin bin = null;//new BinController().getBin("WLL.022.003.01");

		List<WmUnit> unitList = new UnitManager().getUnitListByBinArticle(null, article);

		for(WmUnit unit : unitList){
			System.out.println(unit.getUnitId()+" : "+unit.getUnitCode()+" : "+ new BinController().getBinCode(unit==null?null:unit.getBinId()));
		}

	}

}

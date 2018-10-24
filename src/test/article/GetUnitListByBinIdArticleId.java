package test.article;

import java.util.List;

import org.junit.Test;

import ru.defo.managers.UnitManager;
import ru.defo.model.WmUnit;

public class GetUnitListByBinIdArticleId {

	@Test
	public void test() {
		List<WmUnit> unitList = new UnitManager().getUnitListByBinIdArticleId(28260L, 177090L);
		for(WmUnit unit : unitList){
			System.out.println(unit.getUnitCode());
		}
	}

}

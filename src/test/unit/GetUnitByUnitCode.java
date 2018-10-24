package test.unit;

import java.util.List;

import org.junit.Test;

import ru.defo.controllers.UnitController;
import ru.defo.model.WmUnit;
import ru.defo.util.HibernateUtil;

public class GetUnitByUnitCode {

	@Test
	public void test() {

		List<WmUnit> unitList = HibernateUtil.getObjectList(WmUnit.class, null, 0, true, "unitCode");

		for(WmUnit u : unitList){
			WmUnit unit = new UnitController().getUnitByUnitCode("D"+u.getUnitCode());
			System.out.println(u.getUnitCode()+" is "+unit.getUnitCode());
		}
	}

}

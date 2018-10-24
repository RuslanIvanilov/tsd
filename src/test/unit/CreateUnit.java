package test.unit;
 

import org.junit.Test;

import ru.defo.managers.UnitManager;
import ru.defo.model.WmUnit;

public class CreateUnit {

	@Test
	public void test() {
		WmUnit unit = new UnitManager().createUnit("EU");

		System.out.println(unit.getUnitTypeCode()+" / "+unit.getUnitCode()+" / "+unit.getUnitId());
	}

}

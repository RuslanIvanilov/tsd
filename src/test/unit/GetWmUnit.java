package test.unit;

import org.junit.Test;

import ru.defo.controllers.UnitController;
import ru.defo.model.WmUnit;

public class GetWmUnit {

	@Test
	public void test() {
		WmUnit unit = new UnitController().getWmUnit("EU00040027");
		System.out.println("EU00040027 : "+unit.getUnitId());
	}

}

package test.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ru.defo.controllers.UnitController;

public class VerifyUnitBcExists {
	@Test
	public void test() {
		boolean result = new UnitController().isUnitBc("EU00000001");
		assertEquals(true, result);
	}

}

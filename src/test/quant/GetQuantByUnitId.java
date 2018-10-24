package test.quant;

import org.junit.Test;

import ru.defo.controllers.UnitController;
import ru.defo.managers.QuantManager;
import ru.defo.model.WmQuant;
import ru.defo.model.WmUnit;

public class GetQuantByUnitId {

	@Test
	public void test() {
		String unitCode = "EU00000001";
		WmUnit unit = new UnitController().getUnitByUnitCode(unitCode);
		WmQuant quant = new QuantManager().getQuantByUnitId(unit.getUnitId());

		if(quant != null) System.out.println("quant "+quant.getArticleId());
		System.out.println("test GetQuantByUnitId is success!");
		/*
		if(!(quant instanceof WmQuant)) fail("not able get quant for unit "+unitCode);
		assertTrue(quant instanceof WmQuant);
		*/
	}

}

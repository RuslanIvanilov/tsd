package test.quant;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ru.defo.managers.QuantManager;
import ru.defo.model.WmQuant;

public class hasWmQuant {

	@Test
	public void test() {
		WmQuant quant = new QuantManager().getQuantByQuantId(85981L);
		assertTrue(quant instanceof WmQuant);
	}

}

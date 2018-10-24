package test.unit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WrkTest {

	@Test
	public void test() {
		Object str = null;
		boolean result = (str instanceof String);

		assertTrue(result);
	}

}

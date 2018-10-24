package test.bin;

import org.junit.Test;

import ru.defo.controllers.BinController;
import ru.defo.model.WmBin;

public class GetBin {

	@Test
	public void test() {

		Object binBcObj = "DFRN.007.011.03";
		WmBin bin = new BinController().getBin(binBcObj);
		System.out.println(bin.getBinCode());
	}

}

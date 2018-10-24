package test.inventory;
 

import java.util.List;

import org.junit.Test;

import ru.defo.controllers.BinController; 
import ru.defo.managers.UserManager;
import ru.defo.model.WmBin;
import ru.defo.model.WmUser;

public class CreateInventoryByBinList {

	@Test
	public void test() {

		//new BinController().getBinList("FRN.001.007.01", "FRN.003.001.07");

		List<WmBin> binList = new BinController().getBinList("FRN.001.001.01", "FRN.001.004.07");
		WmUser user = new UserManager().getUserById(1L);


		//assertTrue(new InventoryController().createInventoryByBinList(binList, user));

	}

}

package test.inventory;
 

import java.util.List;

import org.junit.Test;

import ru.defo.controllers.BinController;
import ru.defo.managers.BinManager;
import ru.defo.model.WmBin;

public class InventoryFilter {

	@Test
	public void test() {

		 List<WmBin> binList = new BinManager().getAll();
		 for(WmBin bin : binList){
			System.out.println(bin.getBinCode()+" --> "+ new BinController().getBinNo(bin)+" / "+bin.getBinRackNo());
		 }
		 System.out.println("Test is END");

	}

}

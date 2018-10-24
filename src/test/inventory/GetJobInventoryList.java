package test.inventory;
 

import java.util.List;

import org.junit.Test;

import ru.defo.managers.InventoryManager;
import ru.defo.model.WmInventory;
import ru.defo.util.DefaultValue;

public class GetJobInventoryList {

	@Test
	public void test() {
		List<WmInventory> inventList = new InventoryManager().getJobInventoryList(DefaultValue.INITIATOR_WMS);

		for(WmInventory invent : inventList){
			System.out.println(invent.getInventoryId()+" --- "+invent.getCreateDate());
		}

	}

}

package test.inventory;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import ru.defo.managers.InventoryManager;
import ru.defo.model.WmInventory;
import ru.defo.model.WmInventoryPos;
import ru.defo.util.DefaultValue;

public class ChkInvJobResults {

	@Test
	public void test() {

		List<WmInventory> inventList = new InventoryManager().getInventoryListByType(DefaultValue.INITIATOR_WMS);
		for(WmInventory invent : inventList){
			List<WmInventoryPos>  posList = new InventoryManager().getInventoryPosListByWmInventory(invent);
			for(WmInventoryPos pos : posList){
				int qty = new InventoryManager().getFactInventoryQty(pos.getArticleId());
				System.out.println("Article Code : "+pos.getArticleCode()+" qty : "+qty);
			}
		}

	}

}

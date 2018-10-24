package test.order;
 

import java.util.List;

import org.junit.Test;

import ru.defo.managers.PreOrderManager; 
import ru.defo.model.WmPreOrder;

public class GetPreOrderByWmOrderLinkFlt {

	@Test
	public void test() {
		String wmOrderLinkFlt = "3103";
		List<WmPreOrder> preOrderList = new PreOrderManager().getPreOrderByWmOrderLinkFlt(wmOrderLinkFlt);
		for(WmPreOrder preOrder : preOrderList){
			System.out.println("preOrder : "+preOrder.getOrderId()+" : "+preOrder.getOrderCode());
		}
	}

}

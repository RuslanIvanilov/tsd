package test.order;

import org.junit.Test;

import ru.defo.controllers.PreOrderController;
import ru.defo.model.WmPreOrder;

public class GetPreOrderById {

	@Test
	public void test() {
		String preOrderObj = "729614";
		WmPreOrder preOrder = new PreOrderController().getPreOrderById(preOrderObj);
		System.out.println("preOrder : "+preOrder.getOrderCode());
	}

}

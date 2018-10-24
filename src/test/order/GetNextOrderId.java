package test.order;

import org.junit.Test;

import ru.defo.controllers.OrderController;

public class GetNextOrderId {

	@Test
	public void test() {
		Long nextOrderId = new OrderController().getNextOrderId();
		System.out.println("nextOrderId : "+nextOrderId);
	}

}

package test.order;
 

import java.util.List;

import org.junit.Test;

import ru.defo.managers.OrderManager;
import ru.defo.managers.PreOrderPosManager;
import ru.defo.model.WmOrder;
import ru.defo.model.WmOrderPos;

public class CheckPosQty {

	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		int sumQty = 0;
		List<WmOrderPos> orderPosList =  new OrderManager().getPosAll("2018-07-01"); //"2018-04-17"

		for(WmOrderPos pos : orderPosList)
		{
			sumQty = new PreOrderPosManager().getSumQtyPreOrdersByOrderPos(pos);
			if(sumQty >0 && sumQty < pos.getExpectQuantity().intValue())
			{
				WmOrder order = new OrderManager().getOrderById(pos.getOrderId());
				if(order == null)
				{
					System.out.println("ordId : "+pos.getOrderId() +" is NULL!");
				} else
				{
					System.out.println("date: "+order.getExpectedDate().toLocaleString()+" ord: "+order.getOrderId()+" "+order.getOrderCode()+" :: pos: "+pos.getOrderPosId()+" qty: "+(pos.getExpectQuantity()==null?0L:pos.getExpectQuantity())+" sumQty: "+sumQty);
				}
			}
		}

		System.out.println("END.");

		//fail("Not yet implemented");
	}

}

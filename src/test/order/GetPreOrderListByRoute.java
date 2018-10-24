package test.order;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ru.defo.controllers.PreOrderController;
import ru.defo.managers.DeliveryManager;
import ru.defo.model.WmPreOrder;
import ru.defo.model.WmRoute;

public class GetPreOrderListByRoute {

	@Test
	public void test() {

		List<WmRoute> routeList = new DeliveryManager().getAll();
		List<WmPreOrder> preOrderList =  new ArrayList<WmPreOrder>();

		System.out.println("routeList size: "+routeList.size());

		for(WmRoute route : routeList){
			preOrderList =  new PreOrderController().getPreOrderListByRoute(route);
			System.out.println("preOrderList size: "+preOrderList.size()+" for route : "+route.getRouteCode()+" / "+route.getRouteId());
			for(WmPreOrder preOrder : preOrderList){
				if(preOrder.getErrorId() != null)
					System.out.println("ERROR doc: "+preOrder.getOrderId()+" / "+preOrder.getOrderCode());
			}
		}



	}

}

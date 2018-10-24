package ru.defo.controllers;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ru.defo.model.WmOrder;

public class OrderJoinWorker implements Runnable {

	Thread thread;
	HttpSession session;
	HttpServletRequest request;
	Long newOrderId = null;

	public OrderJoinWorker(HttpSession session){

		this.session = session;
		if(newOrderId == null){
			newOrderId = new OrderController().getNextOrderId();
			thread = new Thread(this, "Thread for join preOrders into WmOrder");
			thread.start();
		}
	}

	@SuppressWarnings("deprecation")
	private void process(){
		System.out.println("Начало: "+( new Date()));
		List<String> list = new ArrayList<String>(Arrays.asList(session.getAttribute("marked").toString().split(",")));
		PreOrderController preOrdCtrl = new PreOrderController();

		/*if(!list.get(0).isEmpty())
		{
			newOrderId = new OrderController().getNextOrderId();
			System.out.println("newOrderId: "+newOrderId);
		}*/

		for(String orderIdTxt : list){
			System.out.println("request wmorderlink: "+session.getAttribute("wmorderlink")+" session: "+session.getAttribute("wmorderlink")+" expdate: "+session.getAttribute("expdate")+" for: "+orderIdTxt);

			System.out.println("ОТ: "+new Date().toGMTString());


			if(session.getAttribute("wmorderlink")!=null && session.getAttribute("wmorderlink").toString().length()>0)
			{
				//Проверить номер WMS заказа
				Long ordId = Long.decode(session.getAttribute("wmorderlink").toString());
				WmOrder order = new OrderController().getOrderByOrderId(ordId);
				if(order != null)
					preOrdCtrl.createOrder(orderIdTxt, order.getOrderId(), (order.getExpectedDate()!=null?order.getExpectedDate().toString():session.getAttribute("expdate").toString()));
			} else
			{
				preOrdCtrl.createOrder(orderIdTxt, newOrderId, session.getAttribute("expdate").toString());
			}

			System.out.println("ДО: "+new Date().toGMTString());

		}

		//session.setAttribute("expdate", null);
		session.setAttribute("marked", null);
		System.out.println("Конец: "+( new Date()));
	}

	@Override
	public void run() {
		System.out.println("Второй поток ------>");
		process();
		System.out.println("END Второй поток ------>");

	}

}

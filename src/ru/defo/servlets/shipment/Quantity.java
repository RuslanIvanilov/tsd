package ru.defo.servlets.shipment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.OrderController;
import ru.defo.controllers.PickController;
import ru.defo.controllers.PreOrderController;
import ru.defo.model.WmOrder;
import ru.defo.util.AppUtil;
import ru.defo.util.ConfirmMessage;
import ru.defo.util.ErrorMessage;


/**
 * Servlet implementation class AssignStart
 */
@WebServlet("/shipment/Quantity")
public class Quantity extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());
		session.setAttribute("tosave", null);

		Long orderId = Long.valueOf(session.getAttribute("orderid").toString());
		WmOrder order =  new OrderController().getOrderByOrderId(orderId);
		if(!(order instanceof WmOrder)){
			request.getRequestDispatcher("Shipment").forward(request, response);
			return;
		}

		System.out.println("request.getParameter. enteredqty : "+request.getParameter("enteredqty"));

		if(request.getParameter("enteredqty")== null || request.getParameter("enteredqty").isEmpty() ||
			Integer.parseInt(request.getParameter("enteredqty"))==0){
			session.setAttribute("orderdescr",new PreOrderController().getClientDocDescription(order.getOrderId()));
			//session.setAttribute("ordercode", order.getOrderCode());
			session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("backpage", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+Unit.class.getSimpleName());
			request.getRequestDispatcher( "/" +AppUtil.getPackageName(this)+"/"+"quantity.jsp").forward(request, response);
			return;
		}

		int qty  = Integer.parseInt(request.getParameter("enteredqty"));
		System.out.println("qty = "+qty);

		int checkedQty = new PickController().getSumSkuUnitOrder(orderId, session.getAttribute("unitcode").toString());
		System.out.println("qty checked in unit: "+checkedQty);

		if(qty != checkedQty){
			request.removeAttribute("enteredqty");
			session.setAttribute("message", ErrorMessage.QTY_NOT_EQUAL_PICK_QTY.message(new ArrayList<String>(Arrays.asList(String.valueOf(qty), session.getAttribute("unitcode").toString()))));
			session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			request.getRequestDispatcher("/errorn.jsp").forward(request, response);
			return;
		}

		session.setAttribute("question",ConfirmMessage.ASK_SHIPMENT_UNIT.message(new ArrayList<String>(Arrays.asList(session.getAttribute("unitcode").toString()))));
		session.setAttribute("yes_page", request.getContextPath() + "/" +AppUtil.getPackageName(new MakeShipment())+"/"+new MakeShipment().getClass().getSimpleName());
		//session.setAttribute("no_page",  request.getContextPath() + "/" +AppUtil.getPackageName(new ReturnRequest())+"/"+new ReturnRequest().getClass().getSimpleName());
		session.setAttribute("no_page", "");
		session.setAttribute("back_page", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
		//Нужно реализовать возврат неотгруженного на склад!
		request.getRequestDispatcher("/" +"confirm3.jsp").forward(request, response);
		return;

		//new QuantController().delQuantByUnitCode(session.getAttribute("userid"), session.getAttribute("unitcode"), EntryType.SHIPMENT);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

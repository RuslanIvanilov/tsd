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
import ru.defo.model.WmCar;
import ru.defo.model.local.ShptUnitsBalance;
import ru.defo.servlets.Mmenu;
import ru.defo.util.AppUtil;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/shipment/Info")
public class Info extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		WmCar car = new OrderController().getCarByOrderId(OrderController.getId(session.getAttribute("orderid")));

		ShptUnitsBalance balance = new PickController().getUnitsBalance(session.getAttribute("orderid"));
		int readyShpt = balance.getPreparedUnitsCount();
		int isShipped = balance.getShippedUnitsCount();

		session.setAttribute("carcode", car==null?"":"â "+car.getCarCode());
		session.setAttribute("preparedcnt", readyShpt);
		session.setAttribute("shippedcnt", isShipped);
		session.setAttribute("actionpage", request.getContextPath() + "/" +AppUtil.getPackageName(new Unit())+"/"+new Unit().getClass().getSimpleName());
		if(readyShpt> 0){
			request.getRequestDispatcher("info.jsp").forward(request, response);
		} else {
			session.setAttribute("message", ErrorMessage.ORDER_UNIT_END.message(new ArrayList<String>(Arrays.asList(session.getAttribute("orderid").toString()))));
			session.setAttribute("action",  (Shipment.class).getSimpleName());
			request.getRequestDispatcher("/errorn.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

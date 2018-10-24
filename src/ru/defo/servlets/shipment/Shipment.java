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
import ru.defo.controllers.SessionController;
import ru.defo.model.WmOrder;
import ru.defo.servlets.Mmenu;
import ru.defo.util.AppUtil;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class AssignStart
 */
@WebServlet("/shipment/Shipment")
public class Shipment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());
		new SessionController().initAll(session);

		session.setAttribute("actionpage", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
		session.setAttribute("backpage", request.getContextPath()+"/"+Mmenu.class.getSimpleName());

		if(request.getParameter("ordercode")==null){
			request.getRequestDispatcher("order.jsp").forward(request, response);
			return;
		}

		WmOrder order = new OrderController().getOrderByCode(ru.defo.util.Bc.symbols(request.getParameter("ordercode")));
		if(!(order instanceof WmOrder)){
			session.setAttribute("message", ErrorMessage.ORDER_NOT_EXISTS.message(new ArrayList<String>(Arrays.asList(request.getParameter("ordercode")))));
			session.setAttribute("action",  (Shipment.class).getSimpleName());
			request.getRequestDispatcher("/errorn.jsp").forward(request, response);
			return;
		}

		session.setAttribute("orderid", order.getOrderId());
		//request.getRequestDispatcher("DestUnit").forward(request, response);

		request.getRequestDispatcher("Info").forward(request, response);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

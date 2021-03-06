package ru.defo.servlets.shpt_ctrl_det;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.OrderController;
import ru.defo.servlets.gui.ShipmentDoc;
import ru.defo.servlets.gui.ShipmentList;
import ru.defo.util.AppUtil;


/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/shpt_ctrl_det/DelWmOrder")
public class DelWmOrder extends HttpServlet {
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

		String errorTxt = new OrderController().delOrder(session.getAttribute("ordercode").toString());
		if((errorTxt==null?"":errorTxt).length() > 0){
			session.setAttribute("message", errorTxt);
			session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(new ShipmentDoc())+"/"+new ShipmentDoc().getClass().getSimpleName());
			request.getRequestDispatcher("/errorn.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher("/" +AppUtil.getPackageName(new ShipmentList())+"/"+new ShipmentList().getClass().getSimpleName()).forward(request, response);

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

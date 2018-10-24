package ru.defo.servlets.shpt_ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.PreOrderController;
import ru.defo.controllers.SessionController;
import ru.defo.managers.OrderManager;
import ru.defo.model.WmOrder;
import ru.defo.servlets.Mmenu;
import ru.defo.util.AppUtil;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/shpt_ctrl/Order")
public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		new SessionController().initAll(session);
		System.out.println(this.getClass().getSimpleName());
		WmOrder order = null;

		session.setAttribute("ordercode", AppUtil.getReqSessionAttribute(request, session, "scantxt", "", true));

		if (!session.getAttribute("ordercode").toString().isEmpty()) {
			order = new OrderManager().getOrderByCode(session.getAttribute("ordercode").toString(), false);
			if (order == null) {
				session.setAttribute("message", ErrorMessage.WRONG_ORDERCODE.message(new ArrayList<String>(Arrays.asList(session.getAttribute("ordercode").toString()))));
				session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());

				session.setAttribute("ordercode", null);

				request.getRequestDispatcher("/errorn.jsp").forward(request, response);
				return;
			}
		}

		if (session.getAttribute("ordercode").toString().isEmpty()) {
			session.setAttribute("actionpage",  request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("backpage", request.getContextPath() + "/" + (Mmenu.class).getSimpleName());
			request.getRequestDispatcher("order.jsp").forward(request, response);
			return;
		} else {
			//System.out.println("-->Unit "+request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+new Unit().getClass().getSimpleName());
			session.setAttribute("clientdescr",new PreOrderController().getClientDocDescription(order.getOrderId()));
			request.getRequestDispatcher("/" +AppUtil.getPackageName(this)+"/"+new Unit().getClass().getSimpleName()).forward(request, response);
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

package ru.defo.servlets.shpt_ctrl_det;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.AuthorizationController;
import ru.defo.controllers.CheckController;
import ru.defo.controllers.OrderController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmOrder;
import ru.defo.model.WmUnit;


/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/shpt_ctrl_det/DelUnitCheck")
public class DelUnitCheck extends HttpServlet {
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

		Long userId = Long.valueOf(session.getAttribute("userid").toString());
		WmOrder order = new OrderController().getOrderByCode(session.getAttribute("ordercode").toString());
		WmUnit unit = new UnitController().getUnitByUnitCode(session.getAttribute("unitcode"));

		if(userId == null || order == null || unit == null){
			new AuthorizationController().errorMessage(request, response);
			return;
		}

		new CheckController().delCheckedUnit(null, order, unit, userId);

		request.getRequestDispatcher(new Unit().getClass().getSimpleName()).forward(request, response);

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

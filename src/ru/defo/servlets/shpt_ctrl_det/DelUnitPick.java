package ru.defo.servlets.shpt_ctrl_det;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.AuthorizationController;
import ru.defo.controllers.PickController;


/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/shpt_ctrl_det/DelUnitPick")
public class DelUnitPick extends HttpServlet {
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
		if(userId == null) new AuthorizationController().errorMessage(request, response);

		new PickController().delPickedUnit(session.getAttribute("ordercode").toString(), session.getAttribute("unitcode").toString(), userId);

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

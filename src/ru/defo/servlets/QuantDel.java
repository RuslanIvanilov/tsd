package ru.defo.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.QuantController;
import ru.defo.controllers.SessionController;
import ru.defo.controllers.UnitController;
import ru.defo.util.ConfirmMessage;

/**
 * Servlet implementation class QuantDel
 */
@WebServlet("/QuantDel")
public class QuantDel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");

		SessionController.check(request, response);

		System.out.println("/" + this.getClass().getSimpleName() + " quant : " + session.getAttribute("quantid")
				+ " action : " + session.getAttribute("action"));


		if (session.getAttribute("quantid") != null) {
			if (session.getAttribute("action") == null) {
				session.setAttribute("question", ConfirmMessage.ASK_DEL_QUANT_FROM_UNIT.message(new ArrayList<String>(Arrays.asList(String.valueOf(session.getAttribute("unitcode"))))));
				session.setAttribute("action_page", "QuantDel");
				session.setAttribute("back_page", "quant_info.jsp");
				session.setAttribute("action", "DEL");
				request.getRequestDispatcher("confirmn.jsp").forward(request, response);
				return;
			} else {

				QuantController qCtrl = new QuantController();
				qCtrl.delQuantByQuantId(session.getAttribute("quantid"),
						session.getAttribute("userid"));

				/* IR+ 16.05.2017 { */
				/*
				System.out.println("unitid : "+session.getAttribute("unitid"));
				WmUnit unit = new UnitController().getWmUnit(String.valueOf(session.getAttribute("unitcode")));
				List<WmQuant> quantList = qCtrl.getQuantList(unit.getUnitId());
				if(quantList.isEmpty()){
					new UnitController().delUnitFromBin(session.getAttribute("unitcode"), session.getAttribute("userid"));
				}
				*/
				/* IR+ 16.05.2017 } */

				qCtrl.initQuantAttributes(session, new UnitController().getWmUnit(String.valueOf(session.getAttribute("unitcode"))).getUnitId());
				session.setAttribute("quantid", null);
				session.setAttribute("action", null);
			}
		}
		request.getRequestDispatcher("unit_article_list.jsp").forward(request, response);
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

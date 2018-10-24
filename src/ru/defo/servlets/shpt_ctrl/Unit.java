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

import ru.defo.controllers.AuthorizationController;
import ru.defo.controllers.BinController;
import ru.defo.controllers.CheckController;
import ru.defo.controllers.OrderController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmOrder;
import ru.defo.model.WmUnit;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/shpt_ctrl/Unit")
public class Unit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		//new SessionController().initAll(session);
		System.out.println(this.getClass().getSimpleName());
		String confMsg = null;

		WmOrder order = new OrderController().getOrderByCode(session.getAttribute("ordercode").toString());
		if(order == null){
			new AuthorizationController().errorMessage(request, response);
			return;
		}

		session.setAttribute("unitcode", AppUtil.getReqSessionAttribute(request, session, "scanunit", "", true));

		if (!session.getAttribute("unitcode").toString().isEmpty()) {
			WmUnit unit = new UnitController().getWmUnit(session.getAttribute("unitcode").toString());

			if (unit == null) {
				session.setAttribute("message", ErrorMessage.BC_NOT_UNIT.message(new ArrayList<String>(Arrays.asList(session.getAttribute("unitcode").toString()))));
				session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());

				session.setAttribute("unitcode", null);

				request.getRequestDispatcher("/errorn.jsp").forward(request, response);
				return;
			}
			//System.out.println(new BinController().getBinById(unit.getBinId()).getAreaCode()+" == "+DefaultValue.CONTROL_AREA_CODE);
			if(unit.getBinId() == null ||( !(new BinController().getBinById(unit.getBinId()).getAreaCode().equals(DefaultValue.CONTROL_AREA_CODE))
					&& !(new BinController().getBinById(unit.getBinId()).getAreaCode().equals(DefaultValue.CONTROL_SHPT_AREA_CODE))
					)
			   ){
				//session.setAttribute("message", ErrorMessage.UNIT_ZERO_BIN.message(new ArrayList<String>(Arrays.asList(session.getAttribute("unitcode").toString()))));
				session.setAttribute("message", ErrorMessage.UNIT_NOT_IN_CONTROL_AREA.message(new ArrayList<String>(Arrays.asList(session.getAttribute("unitcode").toString(), DefaultValue.CONTROL_AREA_CODE))));
				session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());

				session.setAttribute("unitcode", null);

				request.getRequestDispatcher("/errorn.jsp").forward(request, response);
				return;
			}

			confMsg = new CheckController().getConfirmText(order, unit);
			if(confMsg != null){
				session.setAttribute("question", confMsg);
				session.setAttribute("action_page", request.getContextPath() + "/" + AppUtil.getPackageName(new DelUnitCheck()) + "/"
						+ new DelUnitCheck().getClass().getSimpleName());
				session.setAttribute("back_page", request.getContextPath() + "/" + AppUtil.getPackageName(new Order()) + "/"
						+ new Order().getClass().getSimpleName());
				session.setAttribute("continue_page", request.getContextPath() + "/" + AppUtil.getPackageName(new Info()) + "/"
						+ new Info().getClass().getSimpleName());
				request.getRequestDispatcher("/" +"confirmn.jsp").forward(request, response);
				return;
			}

		}

		if (session.getAttribute("unitcode").toString().isEmpty()) {
			session.setAttribute("actionpage", request.getContextPath() + "/" + AppUtil.getPackageName(this) + "/"
					+ this.getClass().getSimpleName());
			session.setAttribute("backpage", request.getContextPath() + "/" + AppUtil.getPackageName(new Order()) + "/"
					+ new Order().getClass().getSimpleName());

			session.setAttribute("listpage", request.getContextPath() + "/" + AppUtil.getPackageName(new OrderPos()) + "/"
					+ new OrderPos().getClass().getSimpleName());

			request.getRequestDispatcher("unit.jsp").forward(request, response);
			return;
		} else {
			request.getRequestDispatcher("Info").forward(request, response);
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

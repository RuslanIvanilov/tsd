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

import ru.defo.controllers.PickController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmUnit;
import ru.defo.util.AppUtil;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/shipment/Unit")
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

		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("unitcode", AppUtil.getReqSessionAttribute(request, session, "scanunit", "", true));
		String unitCode = session.getAttribute("unitcode").toString();

		if (unitCode == null || unitCode.isEmpty()) {
			session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("backpage", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+Shipment.class.getSimpleName());
			request.getRequestDispatcher( "/" +AppUtil.getPackageName(this)+"/"+"unit.jsp").forward(request, response);
			return;
		}


		WmUnit unit = new UnitController().getWmUnit(unitCode);

		if (unit == null) {
			session.setAttribute("message", ErrorMessage.BC_NOT_UNIT.message(new ArrayList<String>(Arrays.asList(unitCode))));
			session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("unitcode", null);
			request.getRequestDispatcher("/errorn.jsp").forward(request, response);
			return;
		}

		if(!(new PickController().isUnitChecked(session.getAttribute("orderid"), session.getAttribute("unitcode")))){
			session.setAttribute("message", ErrorMessage.UNIT_NOT_FROM_SHIPMENT.message(new ArrayList<String>(Arrays.asList(unitCode, session.getAttribute("orderid").toString()))));
			session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("unitcode", null);
			request.getRequestDispatcher("/errorn.jsp").forward(request, response);
			return;
		}


		if(new PickController().isShippedUnit(session.getAttribute("orderid").toString(), unit.getUnitId().toString())){
			session.setAttribute("message", ErrorMessage.UNIT_IS_SHIPPED.message(new ArrayList<String>(Arrays.asList(unitCode, session.getAttribute("orderid").toString()))));
			session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("unitcode", null);
			request.getRequestDispatcher("/errorn.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher(Quantity.class.getSimpleName()).forward(request, response);

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

package ru.defo.servlets.pick;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmUnit;
import ru.defo.servlets.shpt_ctrl.OrderPos;
import ru.defo.util.AppUtil;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/pick/DestUnit")
public class DestUnit extends HttpServlet {
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
		session.setAttribute("selectedbin",null);

		if (!session.getAttribute("unitcode").toString().isEmpty()) {
			WmUnit unit = new UnitController().getWmUnit(session.getAttribute("unitcode").toString());

			if (unit == null) {
				session.setAttribute("message", ErrorMessage.BC_NOT_UNIT.message(new ArrayList<String>(Arrays.asList(session.getAttribute("unitcode").toString()))));
				session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());

				session.setAttribute("unitcode", null);

				request.getRequestDispatcher("/errorn.jsp").forward(request, response);
				return;
			}

			/* Запрос списка незавершенных отгрузок из WM_Pick для этого поддона    DefaultValue.STATUS_CLOSED */

			//new OrderController().


		}

		if (session.getAttribute("unitcode").toString().isEmpty()) {

			if(session.getAttribute("vndgroup")!=null && !session.getAttribute("vndgroup").toString().equals("0")){
				Long vendorGroupId = Long.valueOf(session.getAttribute("vndgroup").toString());
				session.setAttribute("vndgroupname", new ArticleController().getVendorGroupById(vendorGroupId).getDescription());
			}

			session.setAttribute("actionpage", request.getContextPath() + "/" + AppUtil.getPackageName(this) + "/"+ this.getClass().getSimpleName());
			session.setAttribute("backpage", request.getContextPath() + "/" + AppUtil.getPackageName(new ScanDoc()) + "/"+ new ScanDoc().getClass().getSimpleName());
			session.setAttribute("listpage", request.getContextPath() + "/" + AppUtil.getPackageName(new OrderPos()) + "/"+ new OrderPos().getClass().getSimpleName());

			request.getRequestDispatcher("unit.jsp").forward(request, response);
			return;
		} else {
			request.getRequestDispatcher("ArticleSelect").forward(request, response);
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

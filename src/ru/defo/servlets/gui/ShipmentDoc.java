package ru.defo.servlets.gui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.SessionController;
import ru.defo.servlets.shpt_ctrl.DelWmOrder;
import ru.defo.util.AppUtil;


/**
 * Servlet implementation class ArticleQtyList
 */
@WebServlet("/gui/ShipmentDoc")
public class ShipmentDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		new SessionController().initPickList(session);
		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("orderid", AppUtil.getReqSessionAttribute(request, session, "orderid", "", false));
		session.setAttribute("posflt", AppUtil.getReqSessionAttribute(request, session, "posflt", "", false));
		session.setAttribute("articleidflt", AppUtil.getReqSessionAttribute(request, session, "articleidflt", "", false));
		session.setAttribute("artdescrflt", AppUtil.getReqSessionAttribute(request, session, "artdescrflt", "", false));
		session.setAttribute("skuflt", AppUtil.getReqSessionAttribute(request, session, "skuflt", "", false));
		session.setAttribute("expqtyflt", AppUtil.getReqSessionAttribute(request, session, "expqtyflt", "", false));
		session.setAttribute("factqtyflt", AppUtil.getReqSessionAttribute(request, session, "factqtyflt", "", false));
		session.setAttribute("ctrlqtyflt", AppUtil.getReqSessionAttribute(request, session, "ctrlqtyflt", "", false));
		session.setAttribute("statusposflt", AppUtil.getReqSessionAttribute(request, session, "statusposflt", "", false));
		session.setAttribute("showbc", AppUtil.getReqSessionAttribute(request, session, "showbc", "", false));
		session.setAttribute("vendoridflt", AppUtil.getReqSessionAttribute(request, session, "vendoridflt", "", false));

		session.setAttribute("backpage","gui/"+ new ShipmentList().getClass().getSimpleName());
		session.setAttribute("delpage","shpt_ctrl/"+ new DelWmOrder().getClass().getSimpleName());

		request.getRequestDispatcher("/gui/wm_shipment_doc.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package ru.defo.servlets.gui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.util.AppUtil;


/**
 * Servlet implementation class ArticleQtyList
 */
@WebServlet("/gui/CheckList")
public class CheckList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("orderid", AppUtil.getReqSessionAttribute(request, session, "orderid", "", false));
		session.setAttribute("orderposid", AppUtil.getReqSessionAttribute(request, session, "orderposid", "", false));
		session.setAttribute("unitflt", AppUtil.getReqSessionAttribute(request, session, "unitflt", "", false));
		session.setAttribute("qtyflt", AppUtil.getReqSessionAttribute(request, session, "qtyflt", "", false));
		session.setAttribute("surnameflt", AppUtil.getReqSessionAttribute(request, session, "surnameflt", "", false));
		session.setAttribute("firstnameflt", AppUtil.getReqSessionAttribute(request, session, "firstnameflt", "", false));
		session.setAttribute("dateflt", AppUtil.getReqSessionAttribute(request, session, "dateflt", "", false));

		session.setAttribute("backpage","gui/"+ new ShipmentDoc().getClass().getSimpleName());

		request.getRequestDispatcher("/gui/check_list.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

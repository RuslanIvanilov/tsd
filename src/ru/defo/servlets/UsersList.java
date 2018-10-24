package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.SessionController;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/UsersList")
public class UsersList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		new SessionController().initAll(session);

		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("actionpage", (ControlProcess.class).getSimpleName());
		session.setAttribute("backpage", (Mmenu.class).getSimpleName());

		session.setAttribute("surnamefilter", AppUtil.getReqSessionAttribute(request, session, "surnamefilter", "", false));
		session.setAttribute("firstnamefilter", AppUtil.getReqSessionAttribute(request, session, "firstnamefilter", "", false));
		session.setAttribute("patronymfilter", AppUtil.getReqSessionAttribute(request, session, "patronymfilter", "", false));
		session.setAttribute("positionfilter", AppUtil.getReqSessionAttribute(request, session, "positionfilter", "", false));
		session.setAttribute("loginfilter", AppUtil.getReqSessionAttribute(request, session, "loginfilter", "", false));
		session.setAttribute("blockfilter", AppUtil.getReqSessionAttribute(request, session, "blockfilter", "", false));
		session.setAttribute("rowcount", AppUtil.getReqSessionAttribute(request, session, "rowcount", String.valueOf(DefaultValue.TABLE_ROW_COUNT), false));

		request.getRequestDispatcher("user_list.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

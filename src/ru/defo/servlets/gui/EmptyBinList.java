package ru.defo.servlets.gui;

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
 * Servlet implementation class ScanDoc
 */
@WebServlet("/gui/EmptyBinList")
public class EmptyBinList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());
		new SessionController().initAll(session);

		session.setAttribute("areacodeflt", AppUtil.getReqSessionAttribute(request, session, "areacodeflt", "", false));
		session.setAttribute("racknoflt", AppUtil.getReqSessionAttribute(request, session, "racknoflt", "", false));
		session.setAttribute("levelnoflt", AppUtil.getReqSessionAttribute(request, session, "levelnoflt", "", false));
		session.setAttribute("bincodeflt", AppUtil.getReqSessionAttribute(request, session, "bincodeflt", "", false));
		session.setAttribute("rowcount", AppUtil.getReqSessionAttribute(request, session, "rowcount", String.valueOf(DefaultValue.TABLE_ROW_COUNT),false));

		request.getRequestDispatcher("empty_bin_list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package ru.defo.servlets.shpt_ctrl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/shpt_ctrl/ScanList")
public class ScanList extends HttpServlet {
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

		session.setAttribute("backpage", request.getContextPath()+"/"+AppUtil.getPackageName(new Article())+"/"+new Article().getClass().getSimpleName());
		session.setAttribute("actionname", DefaultValue.TODELETE);
		session.setAttribute("actiondel", "1");
		session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(new DelFromList())+"/"+new DelFromList().getClass().getSimpleName());
		request.getRequestDispatcher("scanlist.jsp").forward(request, response);

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

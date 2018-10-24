package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AssignFind
 */
@WebServlet("/AssignFind")
public class AssignFind extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/AssignFind");

		System.out.println("AssignFind.backpage "+session.getAttribute("backpage"));

		if(request.getParameter("articlefilter") == null){
			session.setAttribute("backpage", "AssignStart");
			request.getRequestDispatcher("assign_filter.jsp").forward(request, response);
			return;
		}

		if(request.getParameter("articlefilter") != null){
			String articlefilter = request.getParameter("articlefilter");
		  if(request.getParameter("articlefilter").length()==11) articlefilter = ru.defo.util.Bc.symbols(request.getParameter("articlefilter"));

		  session.setAttribute("articlefilter", articlefilter.toUpperCase());
		}
		session.setAttribute("backpage", "AssignFind");
		request.getRequestDispatcher("article_list.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PrintArticleFind
 */
@WebServlet("/PrintArticleFind")
public class PrintArticleFind extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		System.out.println("/PrintArticleFind");
		session.setAttribute("barcode", null);

		if(request.getParameter("articlefilter") == null){
			session.setAttribute("backpage", "Mmenu");
			session.setAttribute("savepage", "PrintArticleFind");
			request.getRequestDispatcher("article_filter.jsp").forward(request, response);
			return;
		}

		if(request.getParameter("articlefilter") != null){
			String articlefilter = request.getParameter("articlefilter");
			if(request.getParameter("articlefilter").length()==11) articlefilter = ru.defo.util.Bc.symbols(request.getParameter("articlefilter"));
			  session.setAttribute("articlefilter", articlefilter.toUpperCase());
		}
			session.setAttribute("backpage", "PrintArticleFind");
			request.getRequestDispatcher("article_list2.jsp").forward(request, response);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

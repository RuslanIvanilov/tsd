package ru.defo.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.SessionController;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/ControlDelFromList")
public class ControlDelFromList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		Long articleId = Long.decode(request.getParameter("article").toString());
		String articleCode = new ArticleController().getArticle(articleId).getArticleCode();

		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) session.getAttribute("scanmap");

		map.remove(articleCode);

		new SessionController().initControlScan(session);
		request.getRequestDispatcher((ControlScan.class).getSimpleName()).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

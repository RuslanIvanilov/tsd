package ru.defo.servlets;

import java.io.IOException; 
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController; 
import ru.defo.model.WmArticle; 
import ru.defo.util.DefaultValue; 

/**
 * Servlet implementation class InvOrdDelScaned
 */
@WebServlet("/InvOrdDelScaned")
public class InvOrdDelScaned extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		if(session.getAttribute("userid")==null){
			request.getRequestDispatcher(DefaultValue.FORM_START).forward(request, response);
			return;
		}

		WmArticle article = new ArticleController().getArticle(session.getAttribute("artIdSelected"));

		@SuppressWarnings("unchecked")
		HashMap<Integer, String> map = (HashMap<Integer, String>) session.getAttribute("datalist");
		if(map != null){
			map.remove(article.getArticleId().intValue());
			session.setAttribute("datalist", map);
		}

		session.setAttribute("artIdSelected", null);

		request.getRequestDispatcher(new InvOrdScanList().getClass().getSimpleName()).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

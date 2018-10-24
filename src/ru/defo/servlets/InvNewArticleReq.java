package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.HistoryController;

/**
 * Servlet implementation class InvNewArticle
 */
@WebServlet("/InvNewArticleReq")
public class InvNewArticleReq extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvNewArticleReq");
		
		new HistoryController().addEntry(Long.valueOf(session.getAttribute("userid").toString()), 
				                         Long.valueOf(session.getAttribute("unitid").toString()),
				                         "Инвентаризация.Очистка паддона"
				                         );
		
		request.getRequestDispatcher("inv_scanart.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response); 		
		
	}

}

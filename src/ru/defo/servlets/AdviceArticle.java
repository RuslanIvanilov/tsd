package ru.defo.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.QuantController;
import ru.defo.util.ConfirmMessage;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/AdviceArticle")
public class AdviceArticle extends HttpServlet {
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

		session.setAttribute("articlecount", new QuantController().getVQuantInfoListByUnit(session.getAttribute("unitcode").toString()).size());

		String bc = request.getParameter("advice_art_txt");
		if(bc==null) {
			request.getRequestDispatcher("advice_article.jsp").forward(request, response);
			return;
		}

		String error = new ArticleController().getError(bc);
		if(error != null){
			/*
			session.setAttribute("message", error);
			session.setAttribute("dokpage", "AdviceDok");
			session.setAttribute("backpage", "AdviceCar");
			session.setAttribute("action", "advice_article.jsp");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
			*/
			session.setAttribute("frompage", this.getClass().getSimpleName());

			session.setAttribute("question", ConfirmMessage.ASK_BC_UNKNOWN_ASSIGN
					.message(new ArrayList<String>(Arrays.asList(bc))));
			session.setAttribute("action_page", "AssignStart");
			session.setAttribute("back_page", this.getClass().getSimpleName());
			request.getRequestDispatcher("confirmn.jsp").forward(request, response);
			return;
		}

		new ArticleController().fillArticleInfoByBarcode(session, bc, session.getAttribute("unitcode").toString());

		session.setAttribute("savepage", "AdviceEnterQty");
		session.setAttribute("backpage", "advice_unit.jsp");
		request.getRequestDispatcher("advice_article_info.jsp").forward(request, response);
        return;
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

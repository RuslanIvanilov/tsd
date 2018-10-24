package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.SessionController;
import ru.defo.util.AppUtil;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/ArticleCard")
public class ArticleCard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());
		new SessionController().initArticleCard(session);

		if(session.getAttribute("userid")==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		session.setAttribute("articleid", AppUtil.getReqSessionAttribute(request, session, "articleid", "", false));
		session.setAttribute("descriptiontxt", AppUtil.getReqSessionAttribute(request, session, "descriptiontxt", "", false));
		session.setAttribute("blockedtxt", AppUtil.getReqSessionAttribute(request, session, "blockedtxt", "", false));

		session.setAttribute("descrflt", AppUtil.getReqSessionAttribute(request, session, "descrflt", "", false));
		session.setAttribute("isbaseflt", AppUtil.getReqSessionAttribute(request, session, "isbaseflt", "", false));
		session.setAttribute("weightflt", AppUtil.getReqSessionAttribute(request, session, "weightflt", "", false));
		session.setAttribute("lengthflt", AppUtil.getReqSessionAttribute(request, session, "lengthflt", "", false));
		session.setAttribute("widthflt", AppUtil.getReqSessionAttribute(request, session, "widthflt", "", false));
		session.setAttribute("heighflt", AppUtil.getReqSessionAttribute(request, session, "heighflt", "", false));
		session.setAttribute("crushflt", AppUtil.getReqSessionAttribute(request, session, "crushflt", "", false));


		session.setAttribute("save_mode", AppUtil.getReqSessionAttribute(request, session, "save_mode", "", false));


		if(!session.getAttribute("save_mode").toString().isEmpty()){
			new ArticleController().update(session);
			session.setAttribute("save_mode",null);
		}

		session.setAttribute("backpage","/gui/ArticleQtyList");

		request.getRequestDispatcher("article_card.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

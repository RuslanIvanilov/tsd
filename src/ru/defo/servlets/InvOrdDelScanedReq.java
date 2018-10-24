package ru.defo.servlets;

import java.io.IOException; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController; 
import ru.defo.model.WmArticle; 
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue; 

/**
 * Servlet implementation class InvOrdDelScanedReq
 */
@WebServlet("/InvOrdDelScanedReq")
public class InvOrdDelScanedReq extends HttpServlet {
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

		session.setAttribute("artIdSelected", AppUtil.getReqSessionAttribute(request, session, "artIdSelected", "", false));

		WmArticle article = new ArticleController().getArticle(session.getAttribute("artIdSelected"));

		session.setAttribute("question", "<b>"+article.getArticleCode()+"</b><br>"
				 +"<i>"+article.getDescription()+"</i><br><b>  Удалить из списка сканирования?</b>" );
		session.setAttribute("action_page",  new InvOrdDelScaned().getClass().getSimpleName());
		session.setAttribute("back_page", new InvOrdScanList().getClass().getSimpleName());
		request.getRequestDispatcher("/confirmn.jsp").forward(request, response);
		return;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

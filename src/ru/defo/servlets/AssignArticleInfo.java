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
import ru.defo.model.WmArticle;
import ru.defo.model.WmSku;

/**
 * Servlet implementation class AssignArticleInfo
 */
@WebServlet("/AssignArticleInfo")
public class AssignArticleInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/AssignArticleInfo");
		ArticleController articleCtrl = new ArticleController();
		
		String barcode = String.valueOf(session.getAttribute("barcode"));
		String articleFilter = String.valueOf(session.getAttribute("articlefilter"));
		String backPage = String.valueOf(session.getAttribute("backpage")); 
		new SessionController().initAll(session);
		
		
		WmArticle article = articleCtrl.getArticle(Long.valueOf(request.getParameter("selectedart")));
		WmSku sku = articleCtrl.getBaseSkuByArticleId(article.getArticleId());
		
		session.setAttribute("articlecode", article.getArticleCode());
		session.setAttribute("articlename", article.getDescription());
		session.setAttribute("articleid", article.getArticleId());
		session.setAttribute("skuname", sku.getDescription());
		session.setAttribute("skuid", sku.getSkuId());
		session.setAttribute("barcode", barcode);
		session.setAttribute("articlefilter", articleFilter);
		session.setAttribute("backpage", backPage);
		
		
		request.getRequestDispatcher("assign_article_info.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doGet(request, response);
	}

}

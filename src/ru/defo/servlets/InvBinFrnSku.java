package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.SkuController;
import ru.defo.managers.BarcodeManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmBarcode;
import ru.defo.model.WmSku;

/**
 * Servlet implementation class InvBinFrnSku
 */
@WebServlet("/InvBinFrnSku")
public class InvBinFrnSku extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvBinFrnSku v2");


		if(request.getParameter("scanedsku") == null){
			request.getRequestDispatcher("inv_bin_frn_sku.jsp").forward(request, response);
			return;
		}

		WmBarcode bc = new BarcodeManager().gerBarCode(ru.defo.util.Bc.symbols(request.getParameter("scanedsku").trim()));
		if(bc == null){
			session.setAttribute("message", new ArticleController().getErrorText(ru.defo.util.Bc.symbols(request.getParameter("scanedsku"))));
			session.setAttribute("action", this.getClass().getSimpleName());
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		ArticleController ctrlArticle = new ArticleController();
		WmArticle article = ctrlArticle.getArticle(bc.getArticleId());
		WmSku sku = new SkuController().getSku(bc.getSkuId());
		session.setAttribute("skuid", bc.getSkuId());
		session.setAttribute("articleid", bc.getArticleId());
		session.setAttribute("articlecode", article.getArticleCode());
		session.setAttribute("articlename", article.getDescription());
		session.setAttribute("skuname", sku.getDescription());

		request.getRequestDispatcher("InvBinFrnQnt").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

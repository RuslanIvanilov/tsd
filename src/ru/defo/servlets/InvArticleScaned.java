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
import ru.defo.model.WmArticle;
import ru.defo.model.WmBarcode;
import ru.defo.model.WmSku;

/**
 * Servlet implementation class InvArticleScaned
 */
@WebServlet("/InvArticleScaned")
public class InvArticleScaned extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvArticleScaned");

		ArticleController ctrl = new ArticleController();
	   	WmBarcode barcode = ctrl.getBarCode(ru.defo.util.Bc.symbols(request.getParameter("scanedtext")));
	   	if(barcode == null){
	   		session.setAttribute("message", "Отсканированный штрих-код [" + request.getParameter("scanedtext")
			+ "] не числится в системе как штрих-код товара!");
	        request.getRequestDispatcher("errorh.jsp").forward(request, response);
	        return;
	   	}

	   	WmArticle article = ctrl.getArticle(barcode.getArticleId());
	   	WmSku sku = new SkuController().getSku(barcode.getSkuId());
	   	session.setAttribute("articleid", article.getArticleId());
	   	session.setAttribute("articlecode", article.getArticleCode());
	   	session.setAttribute("articlename", article.getDescription());
	   	session.setAttribute("skuname",  sku.getDescription());
	   	session.setAttribute("skuid", sku.getSkuId());
	   	session.setAttribute("quantity",0);
	   	session.setAttribute("backpage","inv_article_info.jsp");

	   	request.getRequestDispatcher("inv_article_info.jsp").forward(request, response);
	   	//request.getRequestDispatcher("enter_qty.jsp").forward(request, response);

	}

}

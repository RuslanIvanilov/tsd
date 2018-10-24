package ru.defo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.QuantController;
import ru.defo.controllers.SkuController;
import ru.defo.model.WmArticle;
import ru.defo.model.WmBarcode;
import ru.defo.model.WmSku;
import ru.defo.model.views.VQuantInfoShort;
import ru.defo.util.AppUtil;

/**
 * Servlet implementation class InvSku
 */
@WebServlet("/InvSku")
public class InvSku extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvSku v2");

		ArticleController ctrlArt = new ArticleController();
		WmBarcode barcode = ctrlArt.getBarCode(session.getAttribute("barcode"));

		String errMsgBcBlocked = new ArticleController().getErrorTextBlockedBC(session.getAttribute("barcode"));
		if(errMsgBcBlocked != null){
			session.setAttribute("message", errMsgBcBlocked);
			session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
			request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
			return;
		}


		WmArticle article = ctrlArt.getArticle(barcode.getArticleId());
		WmSku sku = new SkuController().getSku(barcode.getSkuId());

		session.setAttribute("articleid", article.getArticleId());
		session.setAttribute("articlecode", article.getArticleCode());
		session.setAttribute("articlename", article.getDescription());
		session.setAttribute("skuname", sku.getDescription());
		session.setAttribute("skuid", sku.getSkuId());
		session.setAttribute("totalqty", "");
		session.setAttribute("savepage", "InvArticle");

		QuantController ctrlQuant = new QuantController();
		List<VQuantInfoShort> vquantInfoShrt = ctrlQuant.getVQuantInfoListByArticle(article.getArticleCode());
		int placeCnt = ctrlQuant.getPlacesCount(vquantInfoShrt);
		session.setAttribute("placescount", placeCnt);

		request.getRequestDispatcher("article_info.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

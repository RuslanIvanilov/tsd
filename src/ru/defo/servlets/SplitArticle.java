package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.QualityStateController;
import ru.defo.controllers.QuantController;
import ru.defo.controllers.SessionController;
import ru.defo.controllers.SkuController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmArticle;
import ru.defo.model.WmQuant;
import ru.defo.model.WmUnit;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/SplitArticle")
public class SplitArticle  extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/SplitArticle");

		SessionController.check(request, response);

		ArticleController artCtrl = new ArticleController();

		if(request.getParameter("scanedart") instanceof String == false){
			request.getRequestDispatcher("splitarticle.jsp").forward(request, response);
			return;
		}

		if(artCtrl.checkArticleBc(ru.defo.util.Bc.symbols(request.getParameter("scanedart")), "splitarticle.jsp", request, response)==false) return;

		WmArticle article = artCtrl.getArticle(artCtrl.getBarCode(ru.defo.util.Bc.symbols(request.getParameter("scanedart"))).getArticleId());

		session.setAttribute("articlecode", article.getArticleCode());
		session.setAttribute("articlename", article.getDescription());
		WmUnit unit = new UnitController().getUnitByUnitCode(session.getAttribute("unitsrc"));

		if(new UnitController().checkUnitHasArticle(unit, article, "splitarticle.jsp", request, response)==false) return;

		WmQuant quant = new QuantController().getQuantByUnitArticle(unit, article);
		if(quant != null){
			session.setAttribute("skuname", new SkuController().getSku(quant.getSkuId()).getDescription());
			session.setAttribute("quantity", quant.getQuantity());
			session.setAttribute("qualityname", new QualityStateController().getQualityStateDescriptionById(quant.getQualityStateId()));
		}

		request.getRequestDispatcher("splitqty.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

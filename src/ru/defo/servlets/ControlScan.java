package ru.defo.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.model.WmArticle;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/ControlScan")
public class ControlScan extends HttpServlet {
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
		ArticleController artCtrl = new ArticleController();
		String vol = null;

		String barcode = ru.defo.util.Bc.symbols(request.getParameter("scaned_art_text"));

		if (barcode != null) {
			WmArticle article = artCtrl.getArticleByBarcode(barcode);
			if(article == null){
				session.setAttribute("message", artCtrl.getErrorText(barcode));
				session.setAttribute("action", "control_scan.jsp");
				request.getRequestDispatcher("errorn.jsp").forward(request, response);
				return;
			}

			session.setAttribute("articlecode", "");
			session.setAttribute("articlename", "");
			session.setAttribute("skuname", "");

			if (article != null) {
				session.setAttribute("articlecode", article.getArticleCode());
				session.setAttribute("articlename", article.getDescription());
				session.setAttribute("skuname", artCtrl.getBaseSkuByArticleId(article.getArticleId()).getDescription());

				@SuppressWarnings("unchecked")
				LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) session.getAttribute("scanmap");

				vol = map.get(article.getArticleCode());
				vol = (vol == null? "1" : String.valueOf((Integer.decode(vol) + 1)));

				map.remove(article.getArticleCode());
				map.put(article.getArticleCode(), vol);

				session.setAttribute("scanmap", map);
				session.setAttribute("quantity", vol);

			}
		}

		session.setAttribute("actionpage", this.getClass().getSimpleName());
		session.setAttribute("backpage", (ControlProcess.class).getSimpleName());
		session.setAttribute("scanlist", (ControlScanList.class).getSimpleName());
		session.setAttribute("endpage", (ControlEnd.class).getSimpleName());
		request.getRequestDispatcher("control_scan.jsp").forward(request, response);

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

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
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class ScanArticle
 */
@WebServlet("/ScanArticle")
public class ScanArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		String scanedBarCode = ru.defo.util.Bc.symbols(request.getParameter("scaned_art_text"));

		System.out.println("/ScanArticle");
		//session.setAttribute("savepage", "InvBinFrnQSt");
		//session.setAttribute("backpage", "InvBinFrnSku");


		if (scanedBarCode == null) {
			session.setAttribute("quantity", 0);
		}

		if (scanedBarCode != "" && scanedBarCode != null) {
			if (!new ArticleController().isBarcodeArticle(scanedBarCode, session.getAttribute("articleid"))) {
				session.setAttribute("message", ErrorMessage.BC_NOT_THIS_SKU.message(new ArrayList<String>(
						Arrays.asList(scanedBarCode, String.valueOf(session.getAttribute("articlecode"))))));
				session.setAttribute("action","scan_article.jsp");
				request.getRequestDispatcher("errorn.jsp").forward(request, response);
				return;
			}

			session.setAttribute("quantity", (Integer.valueOf(String.valueOf(session.getAttribute("quantity")))) + 1);
		}
		request.getRequestDispatcher("scan_article.jsp").forward(request, response);

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

package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.QuantController;
import ru.defo.model.WmBin;

/**
 * Servlet implementation class InvArticle
 */
@WebServlet("/InvArticle")
public class InvArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvArticle");

		Long quantId = null;

		if(request.getParameter("selectedquant") != null)
		quantId = Long.valueOf(request.getParameter("selectedquant"));

		if (quantId != null) {
			WmBin bin = new QuantController().getBinByQuantId(quantId);

			session.setAttribute("quantid", quantId);
			if(bin !=null){
				session.setAttribute("scanedtext", bin.getBinCode());
				session.setAttribute("binlevel", bin.getLevelNo());
			} else {
				session.setAttribute("scanedtext", null);
				session.setAttribute("binlevel", null);
			}

			System.out.println("InvArticle.binlevel : "+session.getAttribute("binlevel"));

			request.getRequestDispatcher("ArticleBinInfo").forward(request, response);
			//request.getRequestDispatcher("Inventory").forward(request, response);
		} else {
			request.getRequestDispatcher("bin_list.jsp").forward(request, response);
		}
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

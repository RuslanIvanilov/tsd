package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.QuantController;
import ru.defo.model.views.VQuantInfoShort;

/**
 * Servlet implementation class QuantInfo
 */
@WebServlet("/QuantInfo")
public class QuantInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/"+this.getClass().getSimpleName()+" quantId : "+request.getParameter("quantid")); 
		
		session.setAttribute("quantid", request.getParameter("quantid"));
		
		QuantController quantCtrl = new QuantController();
		VQuantInfoShort vQuantShort = quantCtrl.getVQuantInfoShort(quantCtrl.getQuantId(request.getParameter("quantid")));		
		session.setAttribute("skuname", vQuantShort.getSkuName());
		session.setAttribute("quantity", vQuantShort.getQuantity());
		session.setAttribute("qualityname", vQuantShort.getQualityName());
		session.setAttribute("articlecode", vQuantShort.getArticleCode());
		session.setAttribute("articlename", vQuantShort.getArtName());
		request.getRequestDispatcher("quant_info.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

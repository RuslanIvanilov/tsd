package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.AdviceController;
import ru.defo.controllers.BinController;
import ru.defo.controllers.UnitController;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/AdviceUnit")
public class AdviceUnit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/AdviceUnit");
		//new SessionController().cancelAdviceArticle(session);
		BinController binCtrl = new BinController();
		UnitController unitCtrl = new UnitController();
		String unitCode = null;
		String adviceBinTxt = null;

		session.setAttribute("backpage", "advice_info.jsp");
		session.setAttribute("actionpage", "AdviceUnit");

		if(session.getAttribute("binCode") != null)
			adviceBinTxt = session.getAttribute("binCode").toString();

		if(request.getParameter("advice_bin_txt") != null && adviceBinTxt == null){
			new AdviceController().setAdviceDok(Long.decode(session.getAttribute("adviceId").toString()) , ru.defo.util.Bc.symbols(request.getParameter("advice_bin_txt")));
			adviceBinTxt = ru.defo.util.Bc.symbols(request.getParameter("advice_bin_txt"));
		}

		unitCode = request.getParameter("advice_unit_txt");// == null ? ( session.getAttribute("unitcode") != null ? session.getAttribute("unitcode").toString() : null ) :request.getParameter("advice_unit_txt") ;

		if(adviceBinTxt != null){
			String err = binCtrl.getAdviceBinErrorText(adviceBinTxt);
			if(err != null){
				session.setAttribute("message", err);
				session.setAttribute("action", "AdviceUnit");
				request.getRequestDispatcher("errorn.jsp").forward(request, response);
				return;
			}
				session.setAttribute("adviceunitplace", adviceBinTxt);
		}

		if(session.getAttribute("adviceunitplace") == null){
			request.getRequestDispatcher("advice_bin.jsp").forward(request, response);
			return;
		}

		if(unitCode == null){
			session.setAttribute("dokpage", "AdviceDok");
			session.setAttribute("backpage", "AdviceCar");
			request.getRequestDispatcher("advice_unit.jsp").forward(request, response);
			return;
		}

		String error = unitCtrl.getErrorText(unitCode);
		if(error != null) {
			session.setAttribute("message", unitCtrl.getErrorText(unitCode));
			session.setAttribute("action", "AdviceUnit");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		session.setAttribute("unitcode", unitCode);
		session.setAttribute("actionpage", "AdviceArticle");
		session.setAttribute("backpage", "advice_info.jsp");
		session.setAttribute("fromadvice", "true");
		session.setAttribute("printpage", "PrintStart");
		session.setAttribute("artlist", "unit_article_list.jsp");
		request.getRequestDispatcher("advice_article.jsp").forward(request, response);
		return;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

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
import ru.defo.controllers.HistoryController;
import ru.defo.util.DefaultValue;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/AdviceProcess")
public class AdviceProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/AdviceProcess");

		session.setAttribute("adviceunitplace", null);

		Long userId = Long.decode(session.getAttribute("userid").toString());
		Long adviceId = Long.decode(session.getAttribute("adviceId").toString());
		Long adviceType = Long.decode(session.getAttribute("adviceTypeId").toString());
		Long docId = new BinController().getBin(session.getAttribute("binCode").toString()).getBinId();

		if(new AdviceController().setStartAdvice(session.getAttribute("adviceId").toString()))
			new HistoryController().addAdviceStartEntry(userId, adviceId, adviceType, docId, DefaultValue.ADVICE_STARTED);

		request.getRequestDispatcher("AdviceUnit").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

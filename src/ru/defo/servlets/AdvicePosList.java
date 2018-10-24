package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.AdvicePosController;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/AdvicePosList")
public class AdvicePosList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/AdvicePosList");

		if(request.getParameter("advice_pos_id_txt") == null){
			session.setAttribute("backpage", "advice_info.jsp");
			request.getRequestDispatcher("advice_pos_list.jsp").forward(request, response);
			return;
		}

		/* Подробнее: */
		session.setAttribute("adviceposid", request.getParameter("advice_pos_id_txt"));

		Long adviceId = Long.decode(session.getAttribute("adviceId").toString());
		Long advicePosId = 	Long.decode(session.getAttribute("adviceposid").toString());

		new AdvicePosController().fillAdvicePosInfo(session, adviceId, advicePosId);

		request.getRequestDispatcher("advice_det_info.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

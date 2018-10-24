package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BinController;
import ru.defo.util.HttpMessage;
import ru.defo.util.HttpMessageContent;

/**
 * Servlet implementation class TransfBin
 */
@WebServlet("/TransfBin")
public class TransfBin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/TransfBin");

		if (request.getParameter("scanedbintext") == null && session.getAttribute("bincode") == null) {
			request.getRequestDispatcher("transf_bin.jsp").forward(request, response);
			return;
		}

		if(request.getParameter("scanedbintext") != null) session.setAttribute("bincode", null);

		/* Проверка штрих-кода ячейки */
		BinController ctrl = new BinController();
		if (session.getAttribute("bincode") == null) {
			String msg = ctrl.check(ru.defo.util.Bc.symbols(request.getParameter("scanedbintext")));
			if (msg != null) {
				session.setAttribute("message", msg);
				session.setAttribute("action", this.getClass().getSimpleName());
				request.getRequestDispatcher("errorn.jsp").forward(request, response);
				return;
			} else {
				if (request.getParameter("scanedbintext") != null)
					session.setAttribute("bincode", ru.defo.util.Bc.symbols(request.getParameter("scanedbintext")));
			}
		}
		/* Если ячейка фронтальная, то выбор этажа */
		System.out.println("level entered : "+request.getParameter("enteredtext"));

		String LeveledBin = ctrl.getLeveledBin(session.getAttribute("bincode"), request.getParameter("enteredtext"));
		if (LeveledBin == null) {
			session.setAttribute("servlet_name", "TransfBin");
			session.setAttribute("backpage", "transf_bin.jsp");
			request.getRequestDispatcher("enter_leveln.jsp").forward(request, response);
			return;
		}

		String err = new BinController().getError(LeveledBin);

		if(err != null){
			new HttpMessage(new HttpMessageContent(err, new TransfUnit(), "errorn.jsp"), request, response);
			return;
		}

		session.setAttribute("bincode", LeveledBin);
		request.getRequestDispatcher("TransfEnd").forward(request, response);
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

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
 * Servlet implementation class PrintStart
 */
@WebServlet("/BinLevel")
public class BinLevel extends HttpServlet {
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

		if(session.getAttribute("srcbincode") == null){
			request.getRequestDispatcher(TransfFromBin.class.getSimpleName()).forward(request, response);
			return;
		}

		String LeveledBin = new BinController().getLeveledBin(session.getAttribute("srcbincode"), request.getParameter("enteredtext"));
		if (LeveledBin == null) {
			session.setAttribute("bincode", session.getAttribute("srcbincode").toString());
			session.setAttribute("servlet_name", "BinLevel");
			session.setAttribute("backpage", "TransfFromBin");
			request.getRequestDispatcher("enter_leveln.jsp").forward(request, response);
			return;
		}

		String err = new BinController().check(LeveledBin);
		if(err == null){
			err = new BinController().getError(LeveledBin);
		}

		if(err != null){
			new HttpMessage(new HttpMessageContent(err, new BinLevel(), "errorn.jsp"), request, response);
			return;
		}


		session.setAttribute("srcbincode", LeveledBin);

		request.getRequestDispatcher("TransfUnit").forward(request, response);

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

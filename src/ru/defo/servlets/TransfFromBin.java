package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BinController;
import ru.defo.controllers.SessionController;

/**
 * Servlet implementation class TransfUnit
 */
@WebServlet("/TransfFromBin")
public class TransfFromBin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/"+this.getClass().getSimpleName());
		new SessionController().initAll(session);
		session.setAttribute("srcbincode", null);

		if(request.getParameter("scanedbintext") != null)
			session.setAttribute("srcbincode", ru.defo.util.Bc.symbols(request.getParameter("scanedbintext")));

		if (session.getAttribute("srcbincode") == null) {
			session.setAttribute("backpage", "Mmenu");
			session.setAttribute("bintypename","источника");
			session.setAttribute("actionpage","TransfFromBin");
			request.getRequestDispatcher("transf_bin.jsp").forward(request, response);
			return;
		}

		String err = new BinController().check(session.getAttribute("srcbincode").toString());
		if(err != null){
			session.setAttribute("message", err);
			session.setAttribute("action", this.getClass().getSimpleName());
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}


		request.getRequestDispatcher(BinLevel.class.getSimpleName()).forward(request, response);

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

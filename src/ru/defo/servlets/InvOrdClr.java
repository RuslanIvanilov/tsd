package ru.defo.servlets;

import java.io.IOException; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import ru.defo.controllers.InventoryController; 

/**
 * Servlet implementation class InvOrdClr
 */
@WebServlet("/InvOrdClr")
public class InvOrdClr extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		String err = new InventoryController().setBinEmpty(session);

		if(err.length()>0){
			session.setAttribute("message", err);
			session.setAttribute("page", "Mmenu");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		session.setAttribute("unitcode", null);
		request.getRequestDispatcher("InvStart").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

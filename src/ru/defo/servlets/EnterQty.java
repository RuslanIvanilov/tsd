package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EnterQty
 */
@WebServlet("/EnterQty")
public class EnterQty extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		if(request.getParameter("enteredqty") == null || Integer.valueOf((request.getParameter("enteredqty")==""? "0":request.getParameter("enteredqty"))) == 0){
			request.getRequestDispatcher("enter_qty.jsp").forward(request, response);
			return;
		}

		System.out.println("Entered qty : "+request.getParameter("enteredqty"));

		session.setAttribute("quantity", request.getParameter("enteredqty"));
		request.getRequestDispatcher("InvBinFrnQSt").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

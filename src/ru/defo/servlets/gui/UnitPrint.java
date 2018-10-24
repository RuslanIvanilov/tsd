package ru.defo.servlets.gui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class ArticleQtyList
 */
@WebServlet("/gui/UnitPrint")
public class UnitPrint extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		//new SessionController().initUnitPrint(session);
		System.out.println(this.getClass().getSimpleName());

		if(request.getParameter("unitqty")!=null && request.getParameter("unitqty").equals("")==false)
		{
			session.setAttribute("unittype", request.getParameter("unittype"));
			session.setAttribute("unitqty", request.getParameter("unitqty").equals("0")==true?null:request.getParameter("unitqty"));
		}

		if(session.getAttribute("unitqty")==null || session.getAttribute("unittype")==null){
			request.getRequestDispatcher("/gui/unit_print_request.jsp").forward(request, response);
			return;
		}

		System.out.println("unitqty : "+session.getAttribute("unitqty"));
		request.getRequestDispatcher("/gui/unit_print.jsp").forward(request, response);
		session.setAttribute("unitqty",null);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}



}

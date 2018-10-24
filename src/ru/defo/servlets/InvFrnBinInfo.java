package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class InvFrnBinInfo
 */
@WebServlet("/InvFrnBinInfo")
public class InvFrnBinInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvFrnBinInfo");
		
		if((session.getAttribute("unitcode")!="")&&(session.getAttribute("unitcode")!=null))
		{
			session.setAttribute("question", "”далить поддон "+session.getAttribute("unitcode")+" из €чейки "+session.getAttribute("bincode")+"?");
		
		session.setAttribute("confirm_page", "/InvClearUnit");	
		request.getRequestDispatcher("confirm.jsp").forward(request, response);
		} 
		else 
		{
			request.getRequestDispatcher("inv_scan_unit.jsp").forward(request, response);
		}
	}

}

package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.QuantController;
import ru.defo.controllers.UnitController;

/**
 * Servlet implementation class InvBinFrnClr
 */
@WebServlet("/InvBinFrnClr")
public class InvBinFrnClr extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvBinFrnClr v2");
 
		new QuantController().delQuantByUnitCode(session.getAttribute("userid"), session.getAttribute("unitcode"));	
	    new UnitController().delUnitFromBin(session.getAttribute("unitcode"), session.getAttribute("userid"));	    
	 		
		request.getRequestDispatcher("InvBinFrnUnit").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

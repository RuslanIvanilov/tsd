package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BinController; 

/**
 * Servlet implementation class InvBin
 */
@WebServlet("/InvBin")
public class InvBin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvBin v2");
				
		
		/* ќпределение €чейка фронтальна€ или набивна€*/
		if(new BinController().getBinDepthCount(session.getAttribute("bincode")) > 1){
			session.setAttribute("blkunit", null);
			request.getRequestDispatcher("InvBinBlk").forward(request, response);
		} else {
			request.getRequestDispatcher("InvBinFrn").forward(request, response);
		} 
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package ru.defo.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.util.ConfirmMessage;

/**
 * Servlet implementation class InvBinFrnAction
 */
@WebServlet("/InvBinFrnAction")
public class InvBinFrnAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvBinFrnAction v2");
		
		if(session.getAttribute("unitcode")!=null)
		{
			String binCode = String.valueOf(session.getAttribute("bincode"));
			String unitCode = String.valueOf(session.getAttribute("unitcode"));
			
			session.setAttribute("question", ConfirmMessage.ASK_DEL_UNITBIN.message(new ArrayList<String>(Arrays.asList(unitCode, binCode))));
			session.setAttribute("action_page", "InvBinFrnClr");	
			session.setAttribute("back_page", "InvStart");	
			session.setAttribute("continue_page", "inv_bin_frn_unit_info.jsp");	
			request.getRequestDispatcher("confirmx.jsp").forward(request, response);
			return;
		} 
		else 
		{
			request.getRequestDispatcher("InvBinFrnUnit").forward(request, response);
		}
					 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

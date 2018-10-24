package ru.defo.servlets;

import java.io.IOException; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import ru.defo.controllers.InventoryController; 
import ru.defo.util.DefaultValue; 

/**
 * Servlet implementation class InvOrdToSave
 */
@WebServlet("/InvOrdToSave")
public class InvOrdToSave extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		if(session.getAttribute("userid")==null){
			request.getRequestDispatcher(DefaultValue.FORM_START).forward(request, response);
			return;
		}

		String err = new InventoryController().saveInvPos(session);
		if(!err.isEmpty()){
			session.setAttribute("message", err);
			session.setAttribute("action", new InvOrdEnterQSt().getClass().getSimpleName());
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher(new InvStart().getClass().getSimpleName()).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

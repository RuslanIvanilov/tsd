package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.QuantController;
import ru.defo.controllers.SessionController; 

/**
 * Servlet implementation class InventEnd
 */
@WebServlet("/InventEnd")
public class InventEnd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InventEnd");

		  System.out.println("\nQuality State list.servlet.doGet() userid: "
		  +session.getAttribute("userid")+
		  "\nmodule: "+session.getAttribute("module")+
		  "\nbincode: "+session.getAttribute("bincode")+
		  "\nunitcode: "+session.getAttribute("unitcode")+
		  "\nunitid: "+session.getAttribute("unitid")+
		  "\narticleid: "+session.getAttribute("articleid")+
		  "\narticlecode: "+session.getAttribute("articlecode")+
		  "\narticlename: "+session.getAttribute("articlename")+
		  "\nquantity: "+session.getAttribute("quantity")+
		  "\nskuid: "+session.getAttribute("skuid")+
		  "\nskuname: "+session.getAttribute("skuname")+
		  "\nqualityname: "+session.getAttribute("qualityname")+
		  "\nstate: "+session.getAttribute("state")+"\n\n" );


		// create WmQuant
		/*
		new QuantController().createQuant(session.getAttribute("articleid"), session.getAttribute("skuid"),
				session.getAttribute("state"), session.getAttribute("quantity"), session.getAttribute("unitid"),
				session.getAttribute("userid"), "Инвентаризация.Создание содержимого поддона");
		*/

		String err = new QuantController().createQuant(session, "Инвентаризация.Создание содержимого поддона");
		if(err.length() != 0){
			session.setAttribute("message", err+"\tОперация отменена!");
			session.setAttribute("action", "InvStart");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		new SessionController();
		request.getRequestDispatcher("invent_start.jsp").forward(request, response);
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

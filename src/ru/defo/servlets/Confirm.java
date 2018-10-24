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
 * Servlet implementation class Confirm
 */
@WebServlet("/Confirm")
public class Confirm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/Confirm");		
		 
		   System.out.println(":::::::::::::::Активация удаления из кванта !!!:::::::::::::::");
		   System.out.println("Очистка содержимого поддона и запись в историю операций");
		   System.out.println("Quality State list.servlet.doGet() userid: "
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
		   "\nstate: "+session.getAttribute("state") );		   
		   System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	 		
		/* Очистка содержимого поддона и запись в историю операций */
		 
		if(session.getAttribute("quantity")!=null && session.getAttribute("quantity")!=""){			
			new QuantController().delQuantByUnitCode(session.getAttribute("userid"), session.getAttribute("unitcode"));
			new SessionController().initUnitAttributes(session);
		}
		
		request.getRequestDispatcher(session.getAttribute("confirm_page").toString()).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doGet(request, response);
	}

}

package ru.defo.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.SessionController;
import ru.defo.controllers.UnitController;
import ru.defo.controllers.UserController;
/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/SplitEnd")
public class SplitEnd  extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/SplitEnd");

		if(new UserController().checkUserSession(session.getAttribute("userid"), request, response)==false) return;

		SessionController.check(request, response);
		//System.out.println(session.getAttribute("unitsrc")+" --> "+session.getAttribute("quantity")+" - "+session.getAttribute("stlitedqty")+" --> "+session.getAttribute("unitdest") );

		try{
			System.out.println("time: "+new Date().toString());
			System.out.println("unitsrc: "+session.getAttribute("unitsrc").toString());
			System.out.println("unitdest: "+session.getAttribute("unitdest").toString());
			System.out.println("articlecode: "+session.getAttribute("articlecode").toString());
			System.out.println("stlitedqty: "+session.getAttribute("stlitedqty").toString());
			System.out.println("userid: "+session.getAttribute("userid").toString()+" :::::::::::::::::: ");

			new UnitController().saveSplitedEntry(String.valueOf(session.getAttribute("unitsrc")),
					                              String.valueOf(session.getAttribute("unitdest")),
					                              String.valueOf(session.getAttribute("articlecode")),
					                              String.valueOf(session.getAttribute("stlitedqty")),
					                              Long.valueOf(String.valueOf(session.getAttribute("userid"))));
		} catch(Exception e){
			System.out.println("ERROR in SplitEnd! Some of value is null!!!");
			//e.printStackTrace();
			if(new UserController().checkUserSession(session.getAttribute("unitsrc"), request, response)==false) return;
		}
	    request.getRequestDispatcher("SplitStart").forward(request, response);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

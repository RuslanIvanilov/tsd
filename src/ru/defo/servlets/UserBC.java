package ru.defo.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.defo.controllers.AuthorizationController;
import ru.defo.managers.UserManager;
import ru.defo.model.WmUser;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/UserBC")
public class UserBC extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		System.out.println("/"+ this.getClass().getSimpleName());

		AuthorizationController aCtrl = new AuthorizationController();
		WmUser user = aCtrl.getUserByFIO(request.getParameter("surnametext"), request.getParameter("firstnametext"));

		if(user != null && (request.getParameter("surnametext")!= null) && (request.getParameter("yeartext") != null)){

		   String bc = request.getParameter("surnametext")+request.getParameter("yeartext");
		   user.setUserBarcode(String.valueOf(bc.hashCode()));
		   //System.out.println("USER : "+request.getParameter("surnametext")+" year : "+request.getParameter("yeartext")+" bc : "+String.valueOf((request.getParameter("surnametext")+request.getParameter("yeartext")).hashCode())+" // fact:  "+user.getUserBarcode());

		   new UserManager().setUser(user);

	   	   printBC(request.getParameter("surnametext"), request.getParameter("firstnametext"), request.getParameter("yeartext"), request.getParameter("printeriptext"), user.getUserBarcode());
		}
		request.getRequestDispatcher("userbc.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void printBC(String surname, String firstname, String year, String printerIp, String bc){
		System.out.println("Готовлю к печати");
		try(Socket s = new Socket(printerIp, 6101)){
			OutputStream out = s.getOutputStream();
			PrintWriter writer = new PrintWriter(out, true);
		      String ZPLString=
		    		  "^XA"+
		    		  "^FB400,6,10,C,0"+
		              "^CI33"+
		    		  "^FO5,200^A@N,30,30,E:TT0003M_.FNT"+
		    		  "^FD"+surname+"  "+firstname+"^FS"+
		    		  "^FO50,250^BY2"+
		    		  "^BCN,100,N,N,N"+
		    		  "^FD"+bc+"^FS"+
		    		  "^XZ";
		        writer.println(ZPLString);
		        writer.flush();
		        System.out.println("Отправил на печать");
			} catch(Exception e){
				e.printStackTrace();
			}
	}

}

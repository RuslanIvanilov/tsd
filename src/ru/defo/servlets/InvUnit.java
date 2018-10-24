package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class InvUnit
 */
@WebServlet("/InvUnit")
public class InvUnit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		
	}
   
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvUnit");
		
		System.out.println("InvUnit.servlet.doPost() userid: "+session.getAttribute("userid")+
		           "\nmodule: "+session.getAttribute("module")+
		           "\nbincode: "+session.getAttribute("bincode")+
		           "\nunitcode: "+session.getAttribute("unitcode")+
		           "\narticlecode: "+session.getAttribute("articlecode")+
		           "\narticlename: "+session.getAttribute("articlename")+
		           "\nquantity: "+session.getAttribute("quantity")+
		           "\nskuname: "+session.getAttribute("skuname")+
		           "\nqualityname: "+session.getAttribute("qualityname")+
		           "\nstate: "+request.getParameter("state") 
          ); 
		
		if((session.getAttribute("bincode") == null) || (session.getAttribute("bincode") == "")){
			request.getRequestDispatcher("inv_scan_bin.jsp").forward(request, response);
			return;
		}
		
		if(session.getAttribute("quantity")!="") {      
			session.setAttribute("question", "Очистить поддон "+session.getAttribute("unitcode")+" удалив с него весь товар?");
			session.setAttribute("confirm_page", "inv_scan_article.jsp");		
		  request.getRequestDispatcher("confirm.jsp").forward(request, response);
		} 
		else {			
			request.getRequestDispatcher("inv_scan_article.jsp").forward(request, response); 
		}
	}

}

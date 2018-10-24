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

import ru.defo.util.ErrorMessage;


/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/SplitQty")
public class SplitQty  extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/SplitQty");

		/* Введенное кол-во <= Остатка товара на полддоне */
		int enteredQty;
		try{
		  enteredQty = Integer.valueOf(request.getParameter("enteredqty").equals("0")==true?null:request.getParameter("enteredqty"));
		} catch(Exception e){
			session.setAttribute("message", ErrorMessage.WRONG_QTY.message(new ArrayList<String>(Arrays.asList(request.getParameter("enteredqty")))));
			session.setAttribute("action", "splitqty.jsp");
			request.getRequestDispatcher("/errorn.jsp").forward(request, response);
			return;
		}

		int unitQty =  Integer.valueOf(session.getAttribute("quantity")==null?"0":session.getAttribute("quantity").toString());

		if(enteredQty > unitQty){
			request.getRequestDispatcher("splitqty.jsp").forward(request, response);
			return;
		}

		session.setAttribute("stlitedqty", enteredQty);
		request.getRequestDispatcher("splitdest.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

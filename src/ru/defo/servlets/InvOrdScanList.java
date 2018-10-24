package ru.defo.servlets;

import java.io.IOException; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import ru.defo.util.DefaultValue; 

/**
 * Servlet implementation class InvOrdScanList
 */
@WebServlet("/InvOrdScanList")
public class InvOrdScanList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("selectedvalue", "artIdSelected");
		session.setAttribute("backpage", new InvOrderSku().getClass().getSimpleName());
		session.setAttribute("btnOkName", "Удалить");
		session.setAttribute("detailpage", new InvOrdDelScanedReq().getClass().getSimpleName());
		session.setAttribute("listheader", "На поддоне "+session.getAttribute("unitcode").toString());

		request.getRequestDispatcher(DefaultValue.FORM_LIST).forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.HistoryController;
import ru.defo.controllers.UnitController;

/**
 * Servlet implementation class InvScanNewUnit
 */
@WebServlet("/InvClearUnit")
public class InvClearUnit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvClearUnit");
		
		new HistoryController().addEntry(Long.valueOf(session.getAttribute("userid").toString()), 
                 Long.valueOf(session.getAttribute("unitid").toString()),
                 "Инвентаризация.Очистка ячейки"
                 );
		 
		 new UnitController().del_UnitFromBin(Long.valueOf(session.getAttribute("unitid").toString()));
		 
		 request.getRequestDispatcher("inv_scan_unit.jsp").forward(request, response);
	}

}

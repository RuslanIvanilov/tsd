package ru.defo.servlets;

import java.io.IOException; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import ru.defo.controllers.InventoryController;
import ru.defo.controllers.UnitController; 
import ru.defo.model.WmUnit; 

/**
 * Servlet implementation class InvOrdClrUnit
 */
@WebServlet("/InvOrdClrUnit")
public class InvOrdClrUnit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		UnitController unitCtrl = new UnitController();

		WmUnit unit = unitCtrl.getUnitByUnitCode(session.getAttribute("unitcode"));
		if(unit instanceof WmUnit && !unitCtrl.isEmpty(unit.getUnitId()))
		{
			String err = new InventoryController().setBinEmpty(session);

			if(err.length()>0){
				session.setAttribute("message", err);
				session.setAttribute("page", "Mmenu");
				request.getRequestDispatcher("errorn.jsp").forward(request, response);
				return;
			}
		}

		request.getRequestDispatcher(new InvOrderSku().getClass().getSimpleName()).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

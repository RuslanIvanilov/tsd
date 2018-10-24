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

import ru.defo.controllers.BinController;
import ru.defo.controllers.PermissionController;
import ru.defo.controllers.SessionController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmBin;
import ru.defo.model.WmUnit;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class TransfUnit
 */
@WebServlet("/TransfUnit")
public class TransfUnit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/TransfUnit");

		if(session.getAttribute("userid") == null){
		  request.getRequestDispatcher("index.jsp").forward(request, response);
		  return;
		}



		if (request.getParameter("scanedtext") == null) {
			new SessionController().initAll(session); // Инициализация всех
														// атрибутов кроме Id
														// пользователя
			if(new PermissionController().hasPerm4TransUnSource(session.getAttribute("userid"))==false){
				session.setAttribute("backpage", "TransfFromBin");
			} else {
				session.setAttribute("backpage", "Mmenu");
			}
			request.getRequestDispatcher("transf_unit.jsp").forward(request, response);
			return;
		}

		/* Проверка штрих-кода поддона */
		UnitController ctrl = new UnitController();
		WmUnit unit = ctrl.getWmUnit(ru.defo.util.Bc.symbols(request.getParameter("scanedtext").toUpperCase()));
		if (unit == null) {
			String errMrg = new UnitController().getErrorText(ru.defo.util.Bc.symbols(request.getParameter("scanedtext").toUpperCase()));
			if (errMrg != null) {
				session.setAttribute("message", errMrg);
				session.setAttribute("action", this.getClass().getSimpleName());
				request.getRequestDispatcher("errorn.jsp").forward(request, response);
				return;
			}
		}

		/* Проверка, что поддон прописан в какой-то ячейке! */
		WmBin bin = null;
		try{
			bin = new BinController().getBinById(unit.getBinId());
		}catch(NullPointerException e){
		    System.out.println("ERROR: unit ["+unit.getUnitCode()+"] with out bin!");
		    //e.printStackTrace();
		}

		if(bin==null){
			session.setAttribute("message", ErrorMessage.TRANSF_UNIT_NOT_BIN.message(new ArrayList<String>(Arrays.asList(unit.getUnitCode()))));
			session.setAttribute("action", this.getClass().getSimpleName());
			request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
			return;
		}

		session.setAttribute("unitcode", ru.defo.util.Bc.symbols(request.getParameter("scanedtext").toUpperCase()));
		session.setAttribute("backpage", "TransfUnit");
		session.setAttribute("bintypename","назначения");
		session.setAttribute("actionpage","TransfBin");
		request.getRequestDispatcher("transf_bin.jsp").forward(request, response);

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

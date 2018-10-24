package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.UnitController;
import ru.defo.model.WmUnit;

/**
 * Created by syn-wms on 04.04.2017.
 */
@WebServlet("/Transfer")
public class Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");			

		/* Проверка штрих-кода поддона */
		UnitController ctrl = new UnitController();
		if (session.getAttribute("unitid") == null || session.getAttribute("unitid") == "") {
			if (ctrl.isUnitBarCode(ru.defo.util.Bc.symbols(request.getParameter("scanedtext")))) {
				WmUnit unit = ctrl.getWmUnit(ru.defo.util.Bc.symbols(request.getParameter("scanedtext")));
				if (unit == null) {
					session.setAttribute("message", "Отсканированный штрих-код [..."
							+ request.getParameter("scanedtext") + "] не определен системой как штрих-код поддона!");
					session.setAttribute("page", "transfer_start.jsp");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					return;
				}
				session.setAttribute("unitid", unit.getUnitId());
				session.setAttribute("unitcode", unit.getUnitCode());
				request.getRequestDispatcher("transfer_bin.jsp").forward(request, response);
				return;
			}
		}

		request.getRequestDispatcher("TransferBin").forward(request, response);
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}

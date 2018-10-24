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
import ru.defo.controllers.InventoryController;
import ru.defo.controllers.UnitController;
import ru.defo.controllers.UserController;
import ru.defo.model.WmUnit;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/SplitDest")
public class SplitDest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/SplitDest");

		if(session.getAttribute("userid")==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}


		String unitBarcode = ru.defo.util.Bc.symbols(request.getParameter("scaneddest"));

		if(unitBarcode == null){
			request.getRequestDispatcher("splitdest.jsp").forward(request, response);
			return;
		}

		UnitController unitCtrl = new UnitController();

		if(!unitCtrl.isUnitBarCode(unitBarcode)){
			session.setAttribute("message", unitCtrl.getErrorText(unitBarcode));
			session.setAttribute("action", "splitdest.jsp");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		if(unitBarcode.equals(session.getAttribute("unitsrc").toString())){
			session.setAttribute("message", ErrorMessage.EQUALS_FROM_TO_UNITS.message(new ArrayList<String>(Arrays.asList(unitBarcode))));
			session.setAttribute("action", "splitdest.jsp");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		/* Проверка что поддон и ячейка, где он находится вне задания! */
		if(new InventoryController().hasOpenedInventByUnit(unitBarcode)){
			session.setAttribute("message", ErrorMessage.OPEN_INVENT_POS.message(new ArrayList<String>(Arrays.asList(unitBarcode))));
			session.setAttribute("action", "splitdest.jsp");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		WmUnit unit = unitCtrl.getWmUnit(unitBarcode);
		if(unit == null){
			if(new UserController().checkUserSession(session.getAttribute("userid"), request, response)==false) return;
			unitCtrl.createUnit(unitBarcode, null, DefaultValue.SPLIT_CREATE_UNIT_TEXT, Long.valueOf(session.getAttribute("userid").toString()));
		}

		session.setAttribute("unitdest", unitBarcode);

		if(unit != null){
		  String binCode =  new BinController().getBinCode(unit.getBinId());
		  if(binCode.length() > 1) session.setAttribute("bincode", binCode);
		}

		request.getRequestDispatcher("split_info.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

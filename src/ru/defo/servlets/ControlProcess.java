package ru.defo.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BinController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmBin;
import ru.defo.model.WmUnit;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/ControlProcess")
public class ControlProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());
		WmBin bin = null;

		String unitCode  = request.getParameter("scanedtext")==null ? session.getAttribute("unitcode").toString() : ru.defo.util.Bc.symbols(request.getParameter("scanedtext"));

		if(unitCode == null){
			request.getRequestDispatcher((ControlStart.class).getSimpleName()).forward(request, response);
			return;
		}

		UnitController unitCtrl = new UnitController();

		WmUnit unit = unitCtrl.getWmUnit(unitCode);

		if((!unitCtrl.isUnitBarCode(unitCode)) || (unit == null)){
			session.setAttribute("message", ErrorMessage.BC_NOT_UNIT.message(new ArrayList<String>(Arrays.asList(unitCode))));
			session.setAttribute("action",  (ControlStart.class).getSimpleName());
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}
/*
	    if(unit.getBinId() == null || new BinController().getBinById(unit.getBinId()).getAreaCode()!= DefaultValue.CONTROL_AREA_CODE){
	    	session.setAttribute("message", ErrorMessage.UNIT_NOT_IN_CONTROL_AREA.message(new ArrayList<String>(Arrays.asList(unitCode, DefaultValue.CONTROL_AREA_CODE))));
			session.setAttribute("action",  (ControlStart.class).getSimpleName());
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
	    }
*/
		session.setAttribute("unitcode", unitCode);


		Long binId = unit.getBinId();
		if(binId != null)
			bin = new BinController().getBinById(binId);

		session.setAttribute("bincode", bin !=null?bin.getBinCode():null);

		session.setAttribute("articlecount", unitCtrl.getArticleCount(unit.getUnitId()));

		session.setAttribute("scanmap", new LinkedHashMap<String, String>());

		session.setAttribute("actionpage", (ControlScan.class).getSimpleName());
		session.setAttribute("articlelist", (ControlArtList.class).getSimpleName());
		session.setAttribute("backpage",  (ControlStart.class).getSimpleName());
		request.getRequestDispatcher("control_start_info.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

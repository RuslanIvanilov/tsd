package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BinController;
import ru.defo.controllers.SessionController;
import ru.defo.controllers.UnitController;
import ru.defo.managers.BinManager;
import ru.defo.model.WmBin;
import ru.defo.model.WmUnit;
import ru.defo.util.HttpMessage;
import ru.defo.util.HttpMessageContent;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/SplitSrc")
public class SplitSrc extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		new SessionController().initAll(session);
		System.out.println("/SplitSrc");

		if(session.getAttribute("userid")==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		UnitController unitCtrl = new UnitController();

		if(unitCtrl.checkUnitBcExists(request.getParameter("scanedsrc")==null?"":ru.defo.util.Bc.symbols(request.getParameter("scanedsrc")), "splitsrc.jsp", request, response) == false) return;
		if(unitCtrl.checkUnitNotEmpty(ru.defo.util.Bc.symbols(request.getParameter("scanedsrc")), "splitsrc.jsp", request, response)== false) return;

		session.setAttribute("unitsrc", ru.defo.util.Bc.symbols(request.getParameter("scanedsrc")));

		WmUnit unit = unitCtrl.getWmUnit(ru.defo.util.Bc.symbols(request.getParameter("scanedsrc")));
		WmBin bin =  new BinManager().getBinById(unit.getBinId());
		if(bin instanceof WmBin){
			String err = new BinController().getError(bin);

			if(err != null){
				new HttpMessage(new HttpMessageContent(err, new SplitSrc(), "errorn.jsp"), request, response);
				return;
			}

			session.setAttribute("bincode", bin.getBinCode());
		}



		/*
		String binCode =  new BinController().getBinCode(unitCtrl.getWmUnit(ru.defo.util.Bc.symbols(request.getParameter("scanedsrc"))).getBinId());
		if(binCode.length() > 1) session.setAttribute("bincode", binCode);
		*/

		request.getRequestDispatcher("splitarticle.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

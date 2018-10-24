package ru.defo.servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

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

/**
 * Servlet implementation class InvBinBlk
 */
@WebServlet("/InvBinBlkDelAll")
public class InvBinBlkDelAll extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BinController binCtrl = new BinController();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvBinBlkDelAll");
		UnitController unitCtrl = new UnitController();

		WmBin bin = new BinController().getBin(String.valueOf(session.getAttribute("bincode")));
		if(bin != null){
			List<WmUnit> units = new UnitController().getUnitListByBinId(bin.getBinId());
			Iterator<WmUnit> iterator = units.iterator();
			while(iterator.hasNext()){
				WmUnit unit = iterator.next();
				System.out.println("DEL unit : "+unit.getUnitCode()+" from bin : "+String.valueOf(session.getAttribute("bincode")));
				unitCtrl.delUnitFromBin(unit.getUnitCode(), session.getAttribute("userid"));
			}

		}

		request.getRequestDispatcher("InvBin").forward(request, response);
		return;

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

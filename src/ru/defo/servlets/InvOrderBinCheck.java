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
import ru.defo.managers.BinManager;
import ru.defo.managers.UnitManager;
import ru.defo.model.WmBin;
import ru.defo.model.WmInventoryPos;
import ru.defo.model.WmUnit;
import ru.defo.servlets.pick.BinRequest;
import ru.defo.servlets.pick.CancelMenu;
import ru.defo.util.AppUtil;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class InvOrderBinCheck
 */
@WebServlet("/InvOrderBinCheck")
public class InvOrderBinCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("nextcolor",null);

		String scanedBarcode = new BinController().getLeveledBin(session.getAttribute("bincode"), session.getAttribute("level").toString());

		/* Строка инвентаризации и ячейки */
		WmInventoryPos pos = new InventoryController().getPos(session.getAttribute("inventoryid").toString(), session.getAttribute("inventoryposid").toString());
		WmBin bin = new BinManager().getBinById(pos.getBinId());
		WmUnit unit =  new UnitManager().getUnitById(pos.getUnitId());
		if(unit instanceof WmUnit){
			session.setAttribute("unitcode", unit.getUnitCode());
		} else {
			session.setAttribute("unitcode", null);
		}

		/* Проверка отсканированного ш/к и ячейки из задания */
		//if(session.getAttribute("rescan") == null && (scanedBarcode.length()<14 || !bin.getBinCode().substring(0, 12).equals(scanedBarcode.substring(0, 12)))){
		if(session.getAttribute("rescan") == null && (scanedBarcode.length()<14 || !bin.getBinCode().equals(scanedBarcode))){
			session.setAttribute("message", ErrorMessage.WRONG_INVENT_BIN.message(new ArrayList<String>(Arrays.asList(scanedBarcode, bin.getBinCode() ))));
			session.setAttribute("action", new InvStart().getClass().getSimpleName());
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		/* отсканированная ячейка из задания пользователя */
		if(session.getAttribute("rescan")!=null && !new InventoryController().isInventOrdBin(bin, pos.getInventoryId().toString(), session.getAttribute("userid"))){
			session.setAttribute("message", ErrorMessage.WRONG_INVENT_RESCAN_BIN.message(new ArrayList<String>(Arrays.asList(scanedBarcode, pos.getInventoryId().toString() ))));
			session.setAttribute("action", new InvStart().getClass().getSimpleName());
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		session.setAttribute("rescan", null);
		//if(session.getAttribute("bincode") == null)session.setAttribute("bincode", scanedBarcode);
		session.setAttribute("bincode", (session.getAttribute("bincode") == null && bin == null?scanedBarcode: ( bin == null?session.getAttribute("bincode"):bin.getBinCode())));

		request.getRequestDispatcher(new InvOrderUnit().getClass().getSimpleName()).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

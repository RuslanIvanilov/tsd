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

import ru.defo.controllers.BarcodeController;
import ru.defo.controllers.InventoryController;
import ru.defo.controllers.SessionController;
import ru.defo.controllers.UserController;
import ru.defo.model.WmInventoryPos;
import ru.defo.model.WmUser;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class InvStart
 */
@WebServlet("/InvStart")
public class InvStart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		new SessionController().initAll(session);
		System.out.println("/InvStart v2");

		session.setAttribute("rescan",null);

		WmUser user = new UserController().getUserById(session.getAttribute("userid"));
		if(!(user instanceof WmUser)){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		WmInventoryPos pos = new InventoryController().getUserInventPos(user);
		if(pos instanceof WmInventoryPos){
			session.setAttribute("inventoryid", pos.getInventoryId());
			session.setAttribute("inventoryposid", pos.getInventoryPosId());
			request.getRequestDispatcher("InvOrderBin").forward(request, response);
			return;
		}

		if (request.getParameter("scanedtext") == null){
			request.getRequestDispatcher("inv_start.jsp").forward(request, response);
			return;
		}

		System.out.println("InvStart.scanedtext : ["+request.getParameter("scanedtext")+"]");
		String barCode = ru.defo.util.Bc.symbols(request.getParameter("scanedtext").trim());
		System.out.println("barCode : "+barCode+" / type : "+new BarcodeController().getBarcodeType(barCode));

		switch (new BarcodeController().getBarcodeType(barCode)) {

		case BIN:
			session.setAttribute("bincode", barCode);
			request.getRequestDispatcher("InvBin").forward(request, response);
			break;

		case UNIT:
			session.setAttribute("unitcode", barCode);
			session.setAttribute("backpage", "InvStart");
			request.getRequestDispatcher("InvUnit2").forward(request, response);
			break;

		case SKU:
			session.setAttribute("barcode", barCode);
			request.getRequestDispatcher("InvSku").forward(request, response);
			break;

		default:
			session.setAttribute("message", ErrorMessage.BC_UNKNOWN
					.message(new ArrayList<String>(Arrays.asList(request.getParameter("scanedtext")))));
			session.setAttribute("page", "invent_start.jsp");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			break;
		}


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

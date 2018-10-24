package ru.defo.servlets.gui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.InventoryController;
import ru.defo.controllers.SessionController;
import ru.defo.util.AppUtil;

/**
 * Servlet implementation class InventCreate
 */
@WebServlet("/gui/InventCreate")
public class InventCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		new SessionController().initCreateInventory(session);
		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("areatxt", AppUtil.getReqSessionAttribute(request, session, "areatxt", "", false));
		session.setAttribute("rackstart", AppUtil.getReqSessionAttribute(request, session, "rackstart", "", false));
		session.setAttribute("rackend", AppUtil.getReqSessionAttribute(request, session, "rackend", "", false));
		session.setAttribute("levelstart", AppUtil.getReqSessionAttribute(request, session, "levelstart", "", false));
		session.setAttribute("levelend", AppUtil.getReqSessionAttribute(request, session, "levelend", "", false));
		session.setAttribute("binstart", AppUtil.getReqSessionAttribute(request, session, "binstart", "", false));
		session.setAttribute("binend", AppUtil.getReqSessionAttribute(request, session, "binend", "", false));
		session.setAttribute("articletxt", AppUtil.getReqSessionAttribute(request, session, "articletxt", "", false));

		if(new InventoryController().createInventory(session)){
			request.getRequestDispatcher("InventList").forward(request, response);
			return;
		}

		request.getRequestDispatcher("/gui/invent_create.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}



}

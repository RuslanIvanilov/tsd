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
 * Servlet implementation class InventDoc
 */
@WebServlet("/gui/InventDoc")
public class InventDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		new SessionController().initAll(session);
		System.out.println(this.getClass().getSimpleName());


		session.setAttribute("inventid", AppUtil.getReqSessionAttribute(request, session, "inventid", "", false));
		session.setAttribute("posflt", AppUtil.getReqSessionAttribute(request, session, "posflt", "", false));
		session.setAttribute("executorflt", AppUtil.getReqSessionAttribute(request, session, "executorflt", "", false));
		session.setAttribute("qualityflt", AppUtil.getReqSessionAttribute(request, session, "qualityflt", "", false));
		session.setAttribute("beforeqtyflt", AppUtil.getReqSessionAttribute(request, session, "beforeqtyflt", "", false));
		session.setAttribute("afterqtyflt", AppUtil.getReqSessionAttribute(request, session, "afterqtyflt", "", false));
		session.setAttribute("binflt", AppUtil.getReqSessionAttribute(request, session, "binflt", "", false));
		session.setAttribute("unitflt", AppUtil.getReqSessionAttribute(request, session, "unitflt", "", false));
		session.setAttribute("artcodeflt", AppUtil.getReqSessionAttribute(request, session, "artcodeflt", "", false));
		session.setAttribute("artdescrflt", AppUtil.getReqSessionAttribute(request, session, "artdescrflt", "", false));
		session.setAttribute("executorselect", AppUtil.getReqSessionAttribute(request, session, "executorselect", "", false));
		session.setAttribute("statusflt", AppUtil.getReqSessionAttribute(request, session, "statusflt", "", false));

		session.setAttribute("marked", AppUtil.getReqSessionAttribute(request, session, "marked", "", false));

		System.out.println("Executor: "+session.getAttribute("executorselect"));

		//session.setAttribute("rowcount", AppUtil.getReqSessionAttribute(request, session, "rowcount", String.valueOf(DefaultValue.TABLE_ROW_COUNT), false));


		if(request.getParameter("marked") != null)
		{
			new InventoryController().setExecutor(session);
		}



		session.setAttribute("backpage","/gui/"+new InventList().getClass().getSimpleName());
		request.getRequestDispatcher("/gui/invent_doc.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}



}

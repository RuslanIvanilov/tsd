package ru.defo.servlets.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.SessionController;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;


/**
 * Servlet implementation class InventList
 */
@WebServlet("/gui/InventList")
public class InventList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		new SessionController().initAll(session);
		System.out.println(this.getClass().getSimpleName());


		if(request.getParameter("marked") != null && request.getParameter("expdate") != null){
			List<String> list = new ArrayList<String>(Arrays.asList(request.getParameter("marked").split(",")));

		}

		session.setAttribute("inventflt", AppUtil.getReqSessionAttribute(request, session, "inventflt", "", false));
		session.setAttribute("initerflt", AppUtil.getReqSessionAttribute(request, session, "initerflt", "", false));
		session.setAttribute("dateflt", AppUtil.getReqSessionAttribute(request, session, "dateflt", "", false));
		session.setAttribute("creatorflt", AppUtil.getReqSessionAttribute(request, session, "creatorflt", "", false));
		session.setAttribute("binsflt", AppUtil.getReqSessionAttribute(request, session, "binsflt", "", false));
		session.setAttribute("statusflt", AppUtil.getReqSessionAttribute(request, session, "statusflt", "", false));

		session.setAttribute("areaflt", 		AppUtil.getReqSessionAttribute(request, session, "areaflt", "", false));
		session.setAttribute("rackfromflt", 	AppUtil.getReqSessionAttribute(request, session, "rackfromflt", "", false));
		session.setAttribute("racktoflt", 		AppUtil.getReqSessionAttribute(request, session, "racktoflt", "", false));
		session.setAttribute("levelfromflt", 	AppUtil.getReqSessionAttribute(request, session, "levelfromflt", "", false));
		session.setAttribute("leveltoflt", 		AppUtil.getReqSessionAttribute(request, session, "leveltoflt", "", false));
		session.setAttribute("binfromflt", 		AppUtil.getReqSessionAttribute(request, session, "binfromflt", "", false));
		session.setAttribute("bintoflt", 		AppUtil.getReqSessionAttribute(request, session, "bintoflt", "", false));

		session.setAttribute("hidehost", AppUtil.getReqSessionAttribute(request, session, "hidehost", "true", false));

		session.setAttribute("rowcount", AppUtil.getReqSessionAttribute(request, session, "rowcount", String.valueOf(DefaultValue.TABLE_ROW_COUNT), false));


		request.getRequestDispatcher("/gui/invent_list.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}



}

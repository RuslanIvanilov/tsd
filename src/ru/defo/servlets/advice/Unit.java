package ru.defo.servlets.advice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.SessionController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmUnit;
import ru.defo.util.AppUtil;

/**
 * Servlet implementation class ScanDoc
 */
@WebServlet("/advice/Unit")
public class Unit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());
		WmUnit unit = null;

		System.out.println("inputfield "+request.getParameter("inputfield"));

		if(session.getAttribute("unitcode") != null){
			unit = new UnitController().getUnitByUnitCode(request.getParameter("inputfield"));
			if(!(unit instanceof WmUnit)){
				session.setAttribute("unitcode",null);

			}
		}

		if(unit instanceof WmUnit)
		{
			SessionController.clear(session);
			request.getRequestDispatcher("/" +AppUtil.getPackageName(this)+"/"+new Unit().getClass().getSimpleName()).forward(request, response);
			return;
		}

		session.setAttribute("unitcode", AppUtil.getReqSessionAttribute(request, session, "inputfield", "", true));


		session.setAttribute("SourceClassName",this.getClass().getSimpleName());
		session.setAttribute("Title","TSD Приемка");
		session.setAttribute("SubmitAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
		session.setAttribute("HeaderCaption", "");
		session.setAttribute("TopText","Приемка поддон");
		session.setAttribute("CenterText","Сканируйте штрих-код <b><u>Поддона</u> на который принимается товар</b>");
		session.setAttribute("CancelName","Назад");
		session.setAttribute("CancelAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new Place().getClass().getSimpleName());
		request.getRequestDispatcher("/form_templates/request_text.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

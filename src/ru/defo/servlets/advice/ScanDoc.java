package ru.defo.servlets.advice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.AdviceController;
import ru.defo.controllers.SessionController;
import ru.defo.model.WmAdvice;
import ru.defo.servlets.Mmenu;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class ScanDoc
 */
@WebServlet("/advice/ScanDoc")
public class ScanDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());
		new SessionController().initAdviceDoc(session);

		//System.out.println("inputfield "+request.getParameter("inputfield"));

		session.setAttribute("advicecode", AppUtil.getReqSessionAttribute(request, session, "inputfield", "", true));
		WmAdvice advice = new AdviceController().getAdviceByAdviceCode(session.getAttribute("advicecode").toString());
		if(request.getParameter("inputfield") != null && advice == null)
		{
			session.setAttribute("message", ErrorMessage.WRONG_ADVICE_CODE.message(new ArrayList<String>(Arrays.asList(request.getParameter("inputfield")))));
			session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("advicecode", null);
			request.getRequestDispatcher("/errorn.jsp").forward(request, response);
			return;
		}

		if(advice instanceof WmAdvice && advice.getStatusId().longValue()>DefaultValue.STATUS_START_ADVICE)
		{
			session.setAttribute("message", ErrorMessage.WRONG_ADVICE_STATUS.message(new ArrayList<String>(Arrays.asList(advice.getAdviceCode(), advice.getStatus().getDescription()+"а" ))));
			session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("advicecode", null);
			request.getRequestDispatcher("/errorn.jsp").forward(request, response);
			advice = null;
			session.setAttribute("advicecode",null);
			return;
		}

		if(advice != null)
		{
			SessionController.clear(session);
			request.getRequestDispatcher("/" +AppUtil.getPackageName(this)+"/"+new Place().getClass().getSimpleName()).forward(request, response);
			return;
		}

		session.setAttribute("SourceClassName",this.getClass().getSimpleName());
		session.setAttribute("Title","TSD Приемка");
		session.setAttribute("SubmitAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
		session.setAttribute("HeaderCaption", "");
		session.setAttribute("TopText","Приемка документ");
		session.setAttribute("CenterText","Сканируйте штрих-код документа <b><u>Приемки</u></b>");
		session.setAttribute("CancelName","Назад");
		session.setAttribute("CancelAction", request.getContextPath()+"/"+new Mmenu().getClass().getSimpleName());
		request.getRequestDispatcher("/form_templates/request_text.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

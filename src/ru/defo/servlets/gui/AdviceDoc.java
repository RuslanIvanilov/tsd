package ru.defo.servlets.gui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.SessionController;
import ru.defo.servlets.shpt_ctrl.DelWmAdvice;
import ru.defo.util.AppUtil;


/**
 * Servlet implementation class ArticleQtyList
 */
@WebServlet("/gui/AdviceDoc")
public class AdviceDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		new SessionController().initAdviceDoc(session);
		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("adviceid", AppUtil.getReqSessionAttribute(request, session, "adviceid", "", false));
		session.setAttribute("posflt", AppUtil.getReqSessionAttribute(request, session, "posflt", "", false));
		session.setAttribute("articleidflt", AppUtil.getReqSessionAttribute(request, session, "articleidflt", "", false));
		session.setAttribute("artdescrflt", AppUtil.getReqSessionAttribute(request, session, "artdescrflt", "", false));
		session.setAttribute("skuflt", AppUtil.getReqSessionAttribute(request, session, "skuflt", "", false));
		session.setAttribute("expqtyflt", AppUtil.getReqSessionAttribute(request, session, "expqtyflt", "", false));
		session.setAttribute("factqtyflt", AppUtil.getReqSessionAttribute(request, session, "factqtyflt", "", false));
		session.setAttribute("statusposflt", AppUtil.getReqSessionAttribute(request, session, "statusposflt", "", false));
		session.setAttribute("showbc", AppUtil.getReqSessionAttribute(request, session, "showbc", "", false));
		session.setAttribute("vendoridflt", AppUtil.getReqSessionAttribute(request, session, "vendoridflt", "", false));

		session.setAttribute("backpage","gui/"+ new PreAdviceList().getClass().getSimpleName());
		session.setAttribute("delpage","gui/"+ new DelWmAdvice().getClass().getSimpleName());

		request.getRequestDispatcher("/gui/advice_doc.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

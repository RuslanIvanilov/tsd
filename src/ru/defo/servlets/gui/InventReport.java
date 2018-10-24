package ru.defo.servlets.gui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.SessionController;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;
import ru.defo.workers.ArticleLoadWorker;


/**
 * Servlet implementation class InventReport
 */
@WebServlet("/gui/InventReport")
public class InventReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		new SessionController().initArticleQtyList(session);
		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("articlefilter", AppUtil.getReqSessionAttribute(request, session, "articlefilter", "", false));
		session.setAttribute("articledescfilter", AppUtil.getReqSessionAttribute(request, session, "articledescfilter", "", false));
		session.setAttribute("qtyfilter", AppUtil.getReqSessionAttribute(request, session, "qtyfilter", "", false));

		session.setAttribute("qtyfctflt", AppUtil.getReqSessionAttribute(request, session, "qtyfctflt", "", false));
		session.setAttribute("qtyhostflt", AppUtil.getReqSessionAttribute(request, session, "qtyhostflt", "", false));
		session.setAttribute("dwmsfctflt", AppUtil.getReqSessionAttribute(request, session, "dwmsfctflt", "", false));
		session.setAttribute("dwmshostflt", AppUtil.getReqSessionAttribute(request, session, "dwmshostflt", "", false));
		session.setAttribute("dfcthostflt", AppUtil.getReqSessionAttribute(request, session, "dfcthostflt", "", false));


		session.setAttribute("startloader", AppUtil.getReqSessionAttribute(request, session, "startloader", "", false));

		//session.setAttribute("rowcount", AppUtil.getReqSessionAttribute(request, session, "rowcount", String.valueOf(DefaultValue.TABLE_ROW_COUNT),false));

		if(request.getParameter("startloader") != null && request.getParameter("startloader").equals(session.getAttribute("startloader")!=null?session.getAttribute("startloader").toString():"")==false)
		{
			System.out.println("..--> 1. startloader "+request.getParameter("startloader"));
			session.setAttribute("startloader", request.getParameter("startloader"));
			startArticleLoader(session);
		}

		session.setAttribute("rowcount", 250000);
		session.setAttribute("notzeroqty", 1);
		session.setAttribute("backpage", this.getClass().getSimpleName());
		request.getRequestDispatcher("/gui/invent_report.jsp").forward(request, response);
		session.setAttribute("rowcount", DefaultValue.TABLE_ROW_COUNT);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void startArticleLoader(HttpSession session){
		{
			System.out.println("START : startArticleLoader = ["+session.getAttribute("startloader")+"]  --->");

			Thread workerThread = new Thread(new ArticleLoadWorker(session));
			workerThread.start();

		}


	}

}

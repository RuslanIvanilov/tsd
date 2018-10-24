package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.SkuController;
import ru.defo.util.AppUtil;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/SkuCard")
public class SkuCard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		if(session.getAttribute("userid")==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		session.setAttribute("skuid", AppUtil.getReqSessionAttribute(request, session, "skuid", "", false));
		session.setAttribute("descrtxt", AppUtil.getReqSessionAttribute(request, session, "descrtxt", "", false));

		session.setAttribute("isbasetxt", AppUtil.getReqSessionAttribute(request, session, "isbasetxt", "", false));
		session.setAttribute("weighttxt", AppUtil.getReqSessionAttribute(request, session, "weighttxt", "", false));
		session.setAttribute("lengthtxt", AppUtil.getReqSessionAttribute(request, session, "lengthtxt", "", false));
		session.setAttribute("widthtxt", AppUtil.getReqSessionAttribute(request, session, "widthtxt", "", false));
		session.setAttribute("heightxt", AppUtil.getReqSessionAttribute(request, session, "heightxt", "", false));
		session.setAttribute("crushtxt", AppUtil.getReqSessionAttribute(request, session, "crushtxt", "", false));


		session.setAttribute("save_mode", AppUtil.getReqSessionAttribute(request, session, "save_mode", "", false));


		if(!session.getAttribute("save_mode").toString().isEmpty()){
			new SkuController().update(session);
			session.setAttribute("save_mode",null);
		}
		session.setAttribute("backpage","ArticleCard");

		request.getRequestDispatcher("sku_card.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

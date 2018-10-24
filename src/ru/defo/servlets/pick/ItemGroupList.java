package ru.defo.servlets.pick;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.util.AppUtil;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/pick/ItemGroupList")
public class ItemGroupList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");

		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("vndgroup", AppUtil.getReqSessionAttribute(request, session, "vndgroup", "", true));

		if(session.getAttribute("vndgroup").equals(""))
		{
			session.setAttribute("actionname", "Ok");
			session.setAttribute("actionpage", request.getContextPath() + "/" + AppUtil.getPackageName(this) + "/"+ this.getClass().getSimpleName());
			session.setAttribute("backpage", request.getContextPath() + "/" + AppUtil.getPackageName(new ScanDoc()) + "/"+ new ScanDoc().getClass().getSimpleName());
			request.getRequestDispatcher("item_group_list.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher("DestUnit").forward(request, response);
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

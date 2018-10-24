package ru.defo.servlets.pick;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.SessionController;
import ru.defo.managers.JobManager;
import ru.defo.managers.OrderManager;
import ru.defo.model.WmOrder;
import ru.defo.servlets.Mmenu;
import ru.defo.util.AppUtil;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class ScanDoc
 */
@WebServlet("/pick/ScanDoc")
public class ScanDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());
		new SessionController().initPick(session);
		WmOrder order = null;

		if(session.getAttribute("userid")==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}

		new JobManager().closeJob(session.getAttribute("userid").toString(), 2L);

		session.setAttribute("ordercode", AppUtil.getReqSessionAttribute(request, session, "scantxt", "", true));

		if (!session.getAttribute("ordercode").toString().isEmpty()) {
			order = new OrderManager().getOrderByCode(session.getAttribute("ordercode").toString(), true);
			if (order == null) {
				session.setAttribute("message", ErrorMessage.WRONG_SHPT_ORDERCODE.message(new ArrayList<String>(Arrays.asList(session.getAttribute("ordercode").toString()))));
				session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
				session.setAttribute("ordercode", null);
				request.getRequestDispatcher("/errorn.jsp").forward(request, response);
				return;
			}

			new OrderManager().tryOpenClosedPos(order);

			if(!new OrderManager().hasOpenedOrderLines(order.getOrderId())){
				session.setAttribute("message", ErrorMessage.NOT_OPEN_SHPT_LINES.message(new ArrayList<String>(Arrays.asList(session.getAttribute("ordercode").toString()))));
				session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
				session.setAttribute("ordercode", null);
				request.getRequestDispatcher("/errorn.jsp").forward(request, response);
				return;
			}

		} else {
			session.setAttribute("actionpage",request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("backpage", request.getContextPath() + "/" + (Mmenu.class).getSimpleName());
			request.getRequestDispatcher("scan_order.jsp").forward(request, response);
			return;
		}

		session.setAttribute("orderid", order.getOrderId());
		request.getRequestDispatcher("ItemGroupList").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

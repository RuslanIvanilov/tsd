package ru.defo.servlets.gui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.PreOrderController;
import ru.defo.controllers.SessionController;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;


/**
 * Servlet implementation class ArticleQtyList
 */
@WebServlet("/gui/DeliveryList")
public class DeliveryList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		new SessionController().initRouteList(session);
		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("deliveryflt", AppUtil.getReqSessionAttribute(request, session, "deliveryflt", "", false));
		session.setAttribute("dateflt", AppUtil.getReqSessionAttribute(request, session, "dateflt", "", false));
		session.setAttribute("statusflt", AppUtil.getReqSessionAttribute(request, session, "statusflt", "", false));
		session.setAttribute("carcodeflt", AppUtil.getReqSessionAttribute(request, session, "carcodeflt", "", false));
		session.setAttribute("carmarkflt", AppUtil.getReqSessionAttribute(request, session, "carmarkflt", "", false));
		session.setAttribute("preordercntflt", AppUtil.getReqSessionAttribute(request, session, "preordercntflt", "", false));
		session.setAttribute("orderlinkcntflt", AppUtil.getReqSessionAttribute(request, session, "orderlinkcntflt", "", false));

		session.setAttribute("printflt", AppUtil.getReqSessionAttribute(request, session, "printflt", "", false));
		session.setAttribute("commentflt", AppUtil.getReqSessionAttribute(request, session, "commentflt", "", false));
		session.setAttribute("chCollapsedFlt", AppUtil.getReqSessionAttribute(request, session, "chCollapsedFlt", "", false));

		session.setAttribute("rowcount", AppUtil.getReqSessionAttribute(request, session, "rowcount", String.valueOf(DefaultValue.TABLE_ROW_COUNT), false));

		String routeStr = request.getParameter("routelist[]");
		if(routeStr != null && !routeStr.contains("undefined") && routeStr.length() != 0){
			String[] routeList = routeStr.split(",");
			for(int i=0; i<routeList.length; i++){
				//System.out.println("ROUTE_ID : "+routeList[i]+" Create WmOrder !");
				new PreOrderController().createOrdersByRouteId(routeList[i]);
				/*Long routeId = AppUtil.strToLong(routeList[i]);
				WmRoute route = new DeliveryController().getRouteById(routeId);
				if(new DeliveryController().getOrderList(route).size()>0)
					new DeliveryController().deliveryPrinted(route);*/
			}


		}

		request.getRequestDispatcher("/gui/delivery_list.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}



}

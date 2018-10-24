package ru.defo.servlets.gui;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.DeliveryController;
import ru.defo.controllers.SessionController;
import ru.defo.model.WmRoute;
import ru.defo.model.WmVendorGroup;
import ru.defo.util.AppUtil;


/**
 * Servlet implementation class ArticleQtyList
 */
@WebServlet("/gui/PrintDayList")
public class PrintDayList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		new SessionController().initPrintDayList(session);
		System.out.println(this.getClass().getSimpleName());
		Long vendorGroupId = null;
		DeliveryController delivCtrl = new DeliveryController();

		session.setAttribute("chCollapsedFlt", AppUtil.getReqSessionAttribute(request, session, "chCollapsedFlt", "", false));

		System.out.println("selectvg : "+request.getParameter("selectvg"));
		System.out.println("carcodeflt : "+request.getParameter("carcodeflt"));

		if(request.getParameter("selectvg")==null){
			session.setAttribute("selectvg",null);
		} else {
			session.setAttribute("selectvg",request.getParameter("selectvg"));
		}

		if(request.getParameter("carcodeflt")==null){
			session.setAttribute("carcodeflt",null);
		} else {
			session.setAttribute("carcodeflt",request.getParameter("carcodeflt"));
		}

		String routeStr = request.getParameter("routelist[]");
		if(routeStr != null && !routeStr.contains("undefined") && routeStr.length() != 0){
			String[] routeList = routeStr.split(",");
			for(int i=0; i<routeList.length; i++){
				//System.out.println("ROUTE_ID : "+routeList[i]);

				Long routeId = AppUtil.strToLong(routeList[i]);
				WmRoute route = delivCtrl.getRouteById(routeId);
				if(delivCtrl.getOrderList(route).size()>0)
					delivCtrl.deliveryPrinted(route);
			}

			System.out.println("routelist[] : "+request.getParameter("routelist[]"));
			session.setAttribute("routelist", request.getParameter("routelist[]"));

		}


		//session.setAttribute("carcodeflt", AppUtil.getReqSessionAttribute(request, session, "carcodeflt", ""));
		//session.setAttribute("selectvg", AppUtil.getReqSessionAttribute(request, session, "selectvg", ""));
		session.setAttribute("dateflt", AppUtil.getReqSessionAttribute(request, session, "dateflt", "", false));

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		try{
			Date date = formatter.parse(session.getAttribute("dateflt").toString());
			session.setAttribute("reportdate",new SimpleDateFormat("dd.MM.yyyy").format(date));
		}catch(ParseException e){
			e.printStackTrace();
		}

		if(session.getAttribute("selectvg")!=null && !session.getAttribute("selectvg").toString().isEmpty())
			vendorGroupId = Long.valueOf(session.getAttribute("selectvg").toString());
		WmVendorGroup vendorGroup = new ArticleController().getVendorGroupById(vendorGroupId);
		if(vendorGroup instanceof WmVendorGroup)
			session.setAttribute("vgname", vendorGroup.getDescription());

		if(session.getAttribute("chCollapsedFlt").toString().equals("true")){
			request.getRequestDispatcher("/gui/print_day_list2.jsp").forward(request, response);
			return;
		} else{
			request.getRequestDispatcher("/gui/print_day_list.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}



}

package ru.defo.servlets.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.defo.controllers.OrderController;
import ru.defo.controllers.OrderJoinWorker;
import ru.defo.controllers.PreOrderController;
import ru.defo.controllers.SessionController;
import ru.defo.model.WmOrder;
import ru.defo.model.WmPreOrder;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;


/**
 * Servlet implementation class ArticleQtyList
 */
@WebServlet("/gui/ShipmentList")
public class ShipmentList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		new SessionController().initShipmentList(session);
		System.out.println(this.getClass().getSimpleName());
		PreOrderController preOrdCtrl = new PreOrderController();
		Long newOrderId = null;

		System.out.println("session id: "+session.getId());

		if(request.getParameter("marked") != null && request.getParameter("expdate") != null){
			List<String> list = new ArrayList<String>(Arrays.asList(request.getParameter("marked").split(",")));

			if(!list.get(0).isEmpty())
			{
				System.out.println("Есть документы для объединения.");
				request.changeSessionId();
				System.out.println("expdate : "+(session.getAttribute("expdate")==null?"<null>":session.getAttribute("expdate").toString())+" wmorderlink: "+request.getParameter("wmorderlink")
				+"marked : "+(session.getAttribute("marked")==null?"<null>":session.getAttribute("marked").toString()+" .")
						);
				/*
				if(request.getParameter("expdate")!= null
						&& request.getParameter("marked")!= null
						&& (session.getAttribute("expdate")==null?"":session.getAttribute("expdate").toString()) != request.getParameter("expdate")
						&& (session.getAttribute("marked")==null?"":session.getAttribute("marked").toString())  != request.getParameter("marked")
						)

				{
					System.out.println("НАЖАТА КНОПКА ! Расчет выполняется.");
					session.setAttribute("expdate", request.getParameter("expdate"));
					session.setAttribute("wmorderlink", request.getParameter("wmorderlink"));
					session.setAttribute("marked", request.getParameter("marked"));
					new OrderJoinWorker(session);
				}*/
				newOrderId = new OrderController().getNextOrderId();
				/////}

					for(String orderIdTxt : list){

						//Проверка что у заказа нет ошибок

						//WmPreOrder preOrder = new PreOrderController().getPreOrderById(orderIdTxt);
						//if(preOrder.getErrorId() != null){
						//	session.setAttribute("message", ErrorMessage.ORDER_ERROR.message(new ArrayList<String>(Arrays.asList(preOrder.getOrderCode() ))));
						//	session.setAttribute("action", "/gui/shipment_list.jsp");
						//	request.getRequestDispatcher("errorn.jsp").forward(request, response);
						//	return;
						//}
						System.out.println("ОТ: "+new Date().toGMTString());
						if(request.getParameter("wmorderlink").length()>0)
						{
							//Проверить номер WMS заказа
							Long ordId = Long.decode(request.getParameter("wmorderlink"));
							WmOrder order = new OrderController().getOrderByOrderId(ordId);
							if(order != null)
								preOrdCtrl.createOrder(orderIdTxt, order.getOrderId(), (order.getExpectedDate()!=null?order.getExpectedDate().toString():request.getParameter("expdate")));
						} else
						{
							preOrdCtrl.createOrder(orderIdTxt, newOrderId, request.getParameter("expdate"));
						}

						System.out.println("ДО: "+new Date().toGMTString());

					}


			}

		}

		session.setAttribute("shipmentflt", AppUtil.getReqSessionAttribute(request, session, "shipmentflt", "", false));
		session.setAttribute("clientdocflt", AppUtil.getReqSessionAttribute(request, session, "clientdocflt", "", false));
		session.setAttribute("clientdescrflt", AppUtil.getReqSessionAttribute(request, session, "clientdescrflt", "", false));
		session.setAttribute("dateflt", AppUtil.getReqSessionAttribute(request, session, "dateflt", "", false));
		session.setAttribute("typeflt", AppUtil.getReqSessionAttribute(request, session, "typeflt", "", false));
		session.setAttribute("statusflt", AppUtil.getReqSessionAttribute(request, session, "statusflt", "", false));
		session.setAttribute("wmsshptflt", AppUtil.getReqSessionAttribute(request, session, "wmsshptflt", "", false));

		session.setAttribute("routeflt", AppUtil.getReqSessionAttribute(request, session, "routeflt", "", false));
		session.setAttribute("rdateflt", AppUtil.getReqSessionAttribute(request, session, "rdateflt", "", false));
		session.setAttribute("carflt", AppUtil.getReqSessionAttribute(request, session, "carflt", "", false));
		session.setAttribute("carmarkflt", AppUtil.getReqSessionAttribute(request, session, "carmarkflt", "", false));

		session.setAttribute("rowcount", AppUtil.getReqSessionAttribute(request, session, "rowcount", String.valueOf(DefaultValue.TABLE_ROW_COUNT), false));

		if(!session.getAttribute("wmsshptflt").toString().isEmpty()){
			List<WmPreOrder> preOrderList =  new PreOrderController().getPreOrderListByLinkFlt(session.getAttribute("wmsshptflt").toString());
			for(WmPreOrder preOrder : preOrderList){
				new OrderController().checkPreOrderByLinks(preOrder);
			}
		}

		request.getRequestDispatcher("/gui/shipment_list.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}



}

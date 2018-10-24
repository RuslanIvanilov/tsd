package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 

import ru.defo.controllers.AuthorizationController;
import ru.defo.controllers.PermissionController;
import ru.defo.controllers.TsdPermissionController;
import ru.defo.model.WmPermission; 

/**
 * Servlet implementation class Mmenu
 */
@WebServlet("/Mmenu")
public class Mmenu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/Mmenu");
		if(session.getAttribute("userid")==null) {
			request.getRequestDispatcher("Login").forward(request, response);
			return;
		}

		session.setAttribute("binocode",null);
		session.setAttribute("hidehost","true");
		session.setAttribute("chCollapsedFlt","false");

		if(request.getParameter("module") != null){
		  session.setAttribute("module", Integer.decode(request.getParameter("module")));
		} else {
			request.getRequestDispatcher("main_menu.jsp").forward(request, response);
			return;
		}

		WmPermission wmPermission = new TsdPermissionController(Integer.decode(request.getParameter("userid"))).getPermission(Long.decode(request.getParameter("module")));

		if(wmPermission == null && wmPermission.getPermissionCode()==null){
			session.setAttribute("message", "Не найден модуль [" + request.getParameter("module")
			+ "] ->Mmenu.doGet !");
	        session.setAttribute("page", "index.jsp");
	        request.getRequestDispatcher("error.jsp").forward(request, response);
	        return;
		}

		switch(wmPermission.getPermissionCode().toUpperCase()){
		case "INVENTORY":
			//session.setAttribute("userid", Integer.decode(request.getParameter("userid")));
			//session.setAttribute("userbarcode",new AuthorizationController().getUserBarCode(Integer.decode(request.getParameter("userid"))));
			session.setAttribute("userbarcode",new AuthorizationController().getUserBarCode(Integer.decode(session.getAttribute("userid").toString())));
			//request.getRequestDispatcher("/invent_start.jsp").forward(request, response);
			request.getRequestDispatcher("InvStart").forward(request, response);
			//session.setAttribute("userid", Integer.decode(request.getParameter("userid")));

		break;
		case "TRANSFER":
			if(new PermissionController().hasPerm4TransUnSource(session.getAttribute("userid"))){
				request.getRequestDispatcher("TransfUnit").forward(request, response);
			} else {
				request.getRequestDispatcher("TransfFromBin").forward(request, response);
			}
		break;
		case "ASSIGN":

			request.getRequestDispatcher("/AssignStart").forward(request, response);

		break;
		case "PRINT":

			request.getRequestDispatcher("/PrintStart").forward(request, response);

		break;
		case "USERBC":

			request.getRequestDispatcher("userbc.jsp").forward(request, response);

		break;
		case "SPLIT":
			request.getRequestDispatcher("/SplitStart").forward(request, response);

		break;
		case "ADVICE":
			request.getRequestDispatcher("advice/ScanDoc").forward(request, response);
		break;
		case "ADVICE_GUI":
			//request.getRequestDispatcher("/AdviceGUIStart").forward(request, response);
			request.getRequestDispatcher("/gui/PreAdviceList").forward(request, response);
		break;

		case "ARTICLE_GUI":
			request.getRequestDispatcher("/gui/ArticleQtyList").forward(request, response);
		break;

		case "SHIPMENT_GUI":
			request.getRequestDispatcher("/gui/ShipmentList").forward(request, response);
		break;

		case "DELIVERY_GUI":
			request.getRequestDispatcher("/gui/DeliveryList").forward(request, response);
		break;

		case "CONTROL":
			request.getRequestDispatcher("/ControlStart").forward(request, response);
		break;

		case "SHPT_CTRL":
			request.getRequestDispatcher("shpt_ctrl/Order").forward(request, response);
		break;

		case "USER_GUI":
			request.getRequestDispatcher("/UserStart").forward(request, response);
		break;

		case "SHIPMENT":
			request.getRequestDispatcher("shipment/Shipment").forward(request, response);
		break;

		case "SHPT_CTRL_DET":
			request.getRequestDispatcher("shpt_ctrl_det/Order").forward(request, response);
		break;

		case "INVENT_GUI":
			request.getRequestDispatcher("gui/InventList").forward(request, response);
		break;

		case "UNIT_PRINT":
			request.getRequestDispatcher("gui/UnitPrint").forward(request, response);
		break;

		case "PICK":
			request.getRequestDispatcher("pick/ScanDoc").forward(request, response);
		break;

		case "EMPTY_BINS_GUI":
			request.getRequestDispatcher("gui/EmptyBinList").forward(request, response);
		break;

		default:
			session.setAttribute("message", "Выбранный модуль [" + wmPermission.getPermissionCode().toUpperCase()
			+ "] пока не реализован для работы с ТСД.");
	        session.setAttribute("page", "index.jsp");
	        request.getRequestDispatcher("error.jsp").forward(request, response);
		break;
		}



	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

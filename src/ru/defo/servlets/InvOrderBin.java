package ru.defo.servlets;

import java.io.IOException; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BinController;
import ru.defo.controllers.InventoryController;
import ru.defo.controllers.UserController;
import ru.defo.managers.BinManager;
import ru.defo.managers.UnitManager;
import ru.defo.model.WmBin;
import ru.defo.model.WmInventory;
import ru.defo.model.WmInventoryPos;
import ru.defo.model.WmUnit;
import ru.defo.model.WmUser; 

/**
 * Servlet implementation class InvOrderBin
 */
@WebServlet("/InvOrderBin")
public class InvOrderBin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		WmInventoryPos pos = null;
		session.setAttribute("nextcolor",null);
 

		/* Строка инвентаризации и ячейки */
		if(session.getAttribute("rescan")==null || session.getAttribute("bincode") == null){
			pos = new InventoryController().getPos(session.getAttribute("inventoryid").toString(), session.getAttribute("inventoryposid").toString());
		} else {
			WmBin bin = new BinController().getBin(session.getAttribute("bincode"));
			WmInventory invent = new InventoryController().getInventById(session.getAttribute("inventoryid"));
			WmUser user = new UserController().getUserById(session.getAttribute("userid"));
			pos = new InventoryController().getFirstPos(user, invent, bin);
			session.setAttribute("inventoryposid", pos.getInventoryPosId());
		}


		WmBin bin = new BinManager().getBinById(pos.getBinId());
		WmUnit unit =  new UnitManager().getUnitById(pos.getUnitId());
		if(unit instanceof WmUnit){
			session.setAttribute("unitcode", unit.getUnitCode());
		} else {
			session.setAttribute("unitcode", null);
		}

		/* Запрос штрих-кода ячейки*/
		if(session.getAttribute("bincode") == null &&
		   request.getParameter("inputfield") != null &&
		   !request.getParameter("inputfield").isEmpty())
			 session.setAttribute("bincode",ru.defo.util.Bc.symbols(request.getParameter("inputfield").trim()));

		if(session.getAttribute("bincode") == null || session.getAttribute("bincode").toString().isEmpty()){
			session.setAttribute("SourceClassName",this.getClass().getSimpleName());
			session.setAttribute("Title","TSD Инвентаризация задание");
			session.setAttribute("SubmitAction", this.getClass().getSimpleName());
			session.setAttribute("HeaderCaption", "");
			session.setAttribute("TopText","Инвентаризация <small>"+pos.getInventoryId()+":"+pos.getInventoryPosId()+"</small>" );
			session.setAttribute("CenterText","Сканируйте ячейку <small><b><u>"+(session.getAttribute("rescan")!=null?"":bin.getBinCode())+"</u></b></small>");

			session.setAttribute("CancelName","Назад");
			session.setAttribute("CancelAction", new Mmenu().getClass().getSimpleName());
			session.setAttribute("Btn2Name","Перепроверка");
			session.setAttribute("Btn2Action", "InvOrdRescan");

			request.getRequestDispatcher("/form_templates/request_text.jsp").forward(request, response);
			return;
		}

		/* Запрос Этажа ячейки*/
		if(session.getAttribute("level") == null &&
		   request.getParameter("inputlevel") != null &&
		   !request.getParameter("inputlevel").isEmpty())
			 session.setAttribute("level",  request.getParameter("inputlevel") );

		if(session.getAttribute("level") == null || session.getAttribute("level").toString().isEmpty()){
			session.setAttribute("inputfieldname", "inputlevel");
			session.setAttribute("SourceClassName",this.getClass().getSimpleName());
			session.setAttribute("Title","TSD Инвентаризация задание");
			session.setAttribute("SubmitAction", this.getClass().getSimpleName());
			session.setAttribute("HeaderCaption", "");
			session.setAttribute("TopText","Инвентаризация <small>"+pos.getInventoryId()+":"+pos.getInventoryPosId()+"</small>" );
			session.setAttribute("CenterText","Введите номер <big>этажа</big>");

			session.setAttribute("CancelName","Назад");
			session.setAttribute("CancelAction", new InvStart().getClass().getSimpleName());
			session.setAttribute("Btn2Name",null);
			session.setAttribute("Btn2Action",null);

			request.getRequestDispatcher("/form_templates/request_num_ex.jsp").forward(request, response);
			return;
		}



		request.getRequestDispatcher(new InvOrderBinCheck().getClass().getSimpleName()).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

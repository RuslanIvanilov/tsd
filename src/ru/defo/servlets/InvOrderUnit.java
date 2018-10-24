package ru.defo.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BinController;
import ru.defo.controllers.InventoryController;
import ru.defo.controllers.UnitController;
import ru.defo.managers.BinManager;
import ru.defo.model.WmBin;
import ru.defo.model.WmInventoryPos;
import ru.defo.model.WmUnit;
import ru.defo.servlets.pick.BinList;
import ru.defo.servlets.pick.BinRequest;
import ru.defo.servlets.pick.CancelMenu;
import ru.defo.util.AppUtil;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class InvOrderUnit
 */
@WebServlet("/InvOrderUnit")
public class InvOrderUnit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		String scanedUnitCode = null;
		session.setAttribute("rescan",null);
		session.setAttribute("level", null);

		if(session.getAttribute("bincodeblock") != null)
		{
			session.setAttribute("bincodeblock", null);
			request.getRequestDispatcher("InvOrdBinBlock").forward(request, response);
			return;
		}

		/* Запрос штрих-кода Поддона*/
		if(//session.getAttribute("unitcode") == null &&
		   request.getParameter("inputunit") != null &&
		   !request.getParameter("inputunit").isEmpty())
			scanedUnitCode =  ru.defo.util.Bc.symbols(request.getParameter("inputunit").trim());

		if(scanedUnitCode == null || scanedUnitCode.length()==0){
			session.setAttribute("inputfieldname", "inputunit");
			session.setAttribute("SourceClassName",this.getClass().getSimpleName());
			session.setAttribute("Title","TSD Инвентаризация задание");
			session.setAttribute("SubmitAction", this.getClass().getSimpleName());
			session.setAttribute("HeaderCaption", "");
			session.setAttribute("TopText","Инвентаризация <small>"+session.getAttribute("inventoryid").toString()+":"+session.getAttribute("inventoryposid").toString()+"</small>"
					+"<br>ячейка <b>"+(session.getAttribute("bincode")==null?"":session.getAttribute("bincode").toString())+"</b>");
			session.setAttribute("CenterText","Сканируйте поддон <small><b><u>"+(session.getAttribute("unitcode")==null?"":session.getAttribute("unitcode").toString())+"</u></b></small>");

			session.setAttribute("CancelName","Назад");
			session.setAttribute("CancelAction", new Mmenu().getClass().getSimpleName());

			session.setAttribute("Btn2Name","Завершить яч.");
			session.setAttribute("Btn2Action", new InvOrdClr().getClass().getSimpleName());

			session.setAttribute("Btn3Name", null);
			session.setAttribute("Btn3Action", null);

			request.getRequestDispatcher("/form_templates/request_text_ex.jsp").forward(request, response);
			return;
		}

		/* А поддон ли отсканирован? */
		WmUnit unitChk = new UnitController().getUnitByUnitCode(scanedUnitCode);
		if(unitChk == null){
			session.setAttribute("message", ErrorMessage.BC_NOT_UNIT.message(new ArrayList<String>(Arrays.asList(scanedUnitCode ))));
			session.setAttribute("action", this.getClass().getSimpleName());
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		/* Проверка отсканированного ш/к и поддона из задания */
		/*
		if(session.getAttribute("unitcode")!= null && !session.getAttribute("unitcode").toString().equals(scanedUnitCode)){
			session.setAttribute("message", ErrorMessage.WRONG_INVENT_UNIT.message(new ArrayList<String>(
					Arrays.asList(scanedUnitCode, session.getAttribute("unitcode").toString() )
					                                                                                     )));
			session.setAttribute("page", this.getClass().getSimpleName());
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}
		*/

		/* Проверка, если поддон прописан в другой ячейке, то требовать удаления ш/к и наклейке нового */
		WmBin unitBin = new BinController().getBin(unitChk);
		WmBin invBin = new BinController().getBin(session.getAttribute("bincode").toString());
		if(unitBin.getBinId() !=null && !unitBin.equals(invBin)){
			session.setAttribute("message", ErrorMessage.WRONG_UNIT_BIN.message(new ArrayList<String>(
					Arrays.asList(unitChk.getUnitCode(), unitBin.getBinCode(), invBin.getBinCode() )
					                                                                                     )));
			session.setAttribute("action", this.getClass().getSimpleName());
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}
		if(session.getAttribute("unitcode")!=null && !unitChk.getUnitCode().equals(session.getAttribute("unitcode").toString())){
			String err = new InventoryController().closeUnitInventPosList(session);
			if(!err.isEmpty()){
				session.setAttribute("message", err);
				session.setAttribute("action", this.getClass().getSimpleName());
				request.getRequestDispatcher("errorn.jsp").forward(request, response);
				return;
			}
		}

		if(session.getAttribute("unitcode")==null || session.getAttribute("unitcode")!=unitChk.getUnitCode())
			session.setAttribute("unitcode", unitChk.getUnitCode());

		//request.getRequestDispatcher("InvOrdEnterCnt").forward(request, response);
		request.getRequestDispatcher("InvOrdBinBlock").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

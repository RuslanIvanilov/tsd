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

/**
 * Servlet implementation class InvOrdBinBlock
 */
@WebServlet("/InvOrdBinBlock")
public class InvOrdBinBlock extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		if(session.getAttribute("inputbin") == null &&
		   request.getParameter("inputbin") != null &&
		   !request.getParameter("inputbin").isEmpty())
				session.setAttribute("bincodeblock",  ru.defo.util.Bc.symbols(request.getParameter("inputbin").trim()) );

		if(session.getAttribute("bincodeblock")==null){
			session.setAttribute("inputfieldname", "inputbin");
			session.setAttribute("SourceClassName",this.getClass().getSimpleName());
			session.setAttribute("Title","TSD Инвентаризация задание");
			session.setAttribute("SubmitAction", this.getClass().getSimpleName());
			session.setAttribute("HeaderCaption", "");
			session.setAttribute("TopText","Инвентаризация"
					+"<br><b><u>Блокирование</u></b> ячеек занятых поддоном <b>"+(session.getAttribute("unitcode")==null?"":session.getAttribute("unitcode").toString())+"</b>");
			session.setAttribute("CenterText","Сканируйте ячейку для блокирования");

			session.setAttribute("CancelName","Назад");
			session.setAttribute("CancelAction", new InvOrderUnit().getClass().getSimpleName());

			session.setAttribute("Btn2Name","Зеркальн");
			session.setAttribute("Btn2Action", new InvOrderBinBlockMirr().getClass().getSimpleName());

			session.setAttribute("Btn3Name","Далее");
			session.setAttribute("Btn3Action", new InvOrdEnterCnt().getClass().getSimpleName());

			request.getRequestDispatcher("/form_templates/request_text_ex.jsp").forward(request, response);
			return;
		}

		if(session.getAttribute("level") == null &&
				   request.getParameter("inputlevel") != null &&
				   !request.getParameter("inputlevel").isEmpty())
					 session.setAttribute("level",  request.getParameter("inputlevel") );

		/* Запрос Этажа ячейки*/
		if(session.getAttribute("level")==null){
			session.setAttribute("inputfieldname", "inputlevel");
			session.setAttribute("SourceClassName",this.getClass().getSimpleName());
			session.setAttribute("Title","TSD Инвентаризация задание");
			session.setAttribute("SubmitAction", this.getClass().getSimpleName());
			session.setAttribute("HeaderCaption", "");
			session.setAttribute("TopText","");
			session.setAttribute("CenterText","Введите номер <big>этажа</big>");

			session.setAttribute("CancelName","Назад");
			session.setAttribute("CancelAction", new InvOrderUnit().getClass().getSimpleName());
			session.setAttribute("Btn2Name",null);
			session.setAttribute("Btn2Action",null);

			request.getRequestDispatcher("/form_templates/request_num_ex.jsp").forward(request, response);
			return;
		}

		session.setAttribute("bincodeblock", new BinController().getLeveledBin(session.getAttribute("bincodeblock"), session.getAttribute("level").toString()));

		new InventoryController().closeInventByBin(session.getAttribute("bincodeblock"));

		request.getRequestDispatcher(new InvOrdBinBlockOn().getClass().getSimpleName()).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

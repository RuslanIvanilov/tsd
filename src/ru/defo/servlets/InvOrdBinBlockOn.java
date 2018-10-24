package ru.defo.servlets;

import java.io.IOException; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; 
import ru.defo.controllers.BinController; 

/**
 * Servlet implementation class InvOrdBinBlockOn
 */
@WebServlet("/InvOrdBinBlockOn")
public class InvOrdBinBlockOn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		BinController binCtrl = new BinController();

		/* Проверки и блокирование отсканированной ячейки */
		String err = binCtrl.checkBinForReserv(session);
		if(!err.isEmpty()){
			session.setAttribute("message", err);
			session.setAttribute("action", new InvOrderUnit().getClass().getSimpleName());
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		if(binCtrl.setReserv(session)){
			/* Ячейка заблокирована */
			session.setAttribute("inputfieldname", "inputunit");
			session.setAttribute("SourceClassName",this.getClass().getSimpleName());
			session.setAttribute("Title","TSD Инвентаризация задание");
			session.setAttribute("SubmitAction", this.getClass().getSimpleName());
			session.setAttribute("HeaderCaption", "<small>Инвентаризация "+session.getAttribute("inventoryid").toString()+":"+session.getAttribute("inventoryposid").toString()+"</small>");
			session.setAttribute("TopText","Ячейка: <b><small>"+session.getAttribute("bincodeblock").toString()+"</small></b>" );
			session.setAttribute("CenterText","Заблокирована поддоном: <b>"+session.getAttribute("unitcode").toString()+"</b>");

			session.setAttribute("Btn1Name","Ок");
			session.setAttribute("Btn1Action", new InvOrdBinBlock().getClass().getSimpleName());

			session.setAttribute("Btn2Name",null);
			session.setAttribute("Btn2Action", null);

			session.setAttribute("Btn3Name",null);
			session.setAttribute("Btn3Action", null);

			session.setAttribute("bincodeblock", null);
			session.setAttribute("level", null);

			request.getRequestDispatcher("/form_templates/info_text.jsp").forward(request, response);

			return;
		} else {

			session.setAttribute("message", "Блокирование не выполнено!<br>Повторите операцию!");
			session.setAttribute("action", new InvOrderUnit().getClass().getSimpleName());
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
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

package ru.defo.servlets;

import java.io.IOException; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
  
import ru.defo.util.DefaultValue; 

/**
 * Servlet implementation class InvOrderSkuQty
 */
@WebServlet("/InvOrderSkuQty")
public class InvOrderSkuQty extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("SourceClassName",this.getClass().getSimpleName());
		session.setAttribute("Title","TSD Инвентаризация задание");
		session.setAttribute("SubmitAction", this.getClass().getSimpleName());
		session.setAttribute("HeaderCaption", "");
		session.setAttribute("TopText","Упаковка: "+session.getAttribute("skuname").toString()+"<br>Артикул: <b>"+session.getAttribute("articlecode").toString()+"</b>"
		       +"<br>Наименование: <small>"+session.getAttribute("articlename")+"</small>"
				);
		session.setAttribute("CenterText","<small><b>Способ ввода количества товара?</b></small>");

		session.setAttribute("Btn1Name","Назад");
		session.setAttribute("Btn1Action", new InvOrderSku().getClass().getSimpleName());

		session.setAttribute("Btn2Name","Вручную");
		session.setAttribute("Btn2Action", new InvOrdEnterQty().getClass().getSimpleName());

		session.setAttribute("Btn3Name","Сканир-е");
		session.setAttribute("Btn3Action", new InvOrdClrRequest().getClass().getSimpleName());

		request.getRequestDispatcher(DefaultValue.FORM_INFO_TXT).forward(request, response);
		return;


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

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
 * Servlet implementation class InvOrdEnterQty
 */
@WebServlet("/InvOrdEnterQty")
public class InvOrdEnterQty extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String INPUT_FIELD_NAME = "enteredqty";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		if(request.getParameter(INPUT_FIELD_NAME) == null || Integer.valueOf((request.getParameter(INPUT_FIELD_NAME)==""? "0":request.getParameter(INPUT_FIELD_NAME))) == 0){

			session.setAttribute("inputfieldname", INPUT_FIELD_NAME);
			session.setAttribute("SourceClassName",this.getClass().getSimpleName());
			session.setAttribute("Title","TSD Подбор ввод кол-ва");
			session.setAttribute("SubmitAction", this.getClass().getSimpleName());
			session.setAttribute("HeaderCaption", "");
			session.setAttribute("TopText","Товар: <small>"+session.getAttribute("articlecode").toString()+" "+session.getAttribute("articlename").toString()+"</small>"
								+"<br>Упаковка: "+session.getAttribute("skuname").toString()
					             );
			session.setAttribute("CenterText","<br><small>Введите количество</small>");
			session.setAttribute("CancelName","Назад");
			session.setAttribute("CancelAction", new InvOrderSkuQty().getClass().getSimpleName());
			request.getRequestDispatcher(DefaultValue.FORM_REQUEST_NUM).forward(request, response);
			return;
		}

		session.setAttribute("quantity", request.getParameter(INPUT_FIELD_NAME));
		request.getRequestDispatcher(new InvOrdEnterQSt().getClass().getSimpleName()).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

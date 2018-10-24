package ru.defo.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
  
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class InvOrdSkuList
 */
@WebServlet("/InvOrdSkuList")
public class InvOrdSkuList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		@SuppressWarnings("unchecked")
		HashMap<Integer, String> map = (HashMap<Integer, String>) session.getAttribute("datalist");
		int cnt = Integer.decode(session.getAttribute("countCartons").toString());
		int scaned = AppUtil.getSkuCountMap(map);
		if(scaned < cnt)
		{
			session.setAttribute("message", ErrorMessage.NOT_FULL_SCANED_SKU.message(new ArrayList<String>(Arrays.asList(String.valueOf(scaned), String.valueOf(cnt)))));
			session.setAttribute("action", new InvOrderUnitInfo().getClass().getSimpleName());
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		session.setAttribute("selectedvalue", "artIdSelected");
		session.setAttribute("backpage", new InvOrderUnitInfo().getClass().getSimpleName());
		session.setAttribute("btnOkName", "Сохранить");
		session.setAttribute("detailpage", new InvOrdEnterQSt().getClass().getSimpleName());
		session.setAttribute("listheader", "На поддоне "+session.getAttribute("unitcode").toString());

		request.getRequestDispatcher(DefaultValue.FORM_LIST).forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

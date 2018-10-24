package ru.defo.servlets;

import java.io.IOException; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 

/**
 * Servlet implementation class InvOrdEnterCnt
 */
@WebServlet("/InvOrdEnterCnt")
public class InvOrdEnterCnt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("nextcolor",null);

		int countCartons = 0;

		if(request.getParameter("inputcount") != null &&  !request.getParameter("inputcount").isEmpty()){
			try{
				countCartons =  Integer.decode(request.getParameter("inputcount").trim());
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
		}

		if(countCartons==0){
			session.setAttribute("inputfieldname", "inputcount");
			session.setAttribute("SourceClassName",this.getClass().getSimpleName());
			session.setAttribute("Title","TSD Инвентаризация задание");
			session.setAttribute("SubmitAction", this.getClass().getSimpleName());
			session.setAttribute("HeaderCaption", "");
			session.setAttribute("TopText","Инвентаризация <small>"+session.getAttribute("inventoryid").toString()+":"+session.getAttribute("inventoryposid").toString()+"</small>"
					+"<br>ячейка <b>"+(session.getAttribute("bincode")==null?"":session.getAttribute("bincode").toString())+"</b>"+"<br>Поддон: <b>"+(session.getAttribute("unitcode")==null?"":session.getAttribute("unitcode").toString())+"</b>");
			session.setAttribute("CenterText","Введите пересчитанное кол-во коробок на поддоне" );

			session.setAttribute("CancelName","Назад");
			session.setAttribute("CancelAction", new InvOrdBinBlock().getClass().getSimpleName());

			session.setAttribute("Btn2Name",null);
			session.setAttribute("Btn2Action",null);

			request.getRequestDispatcher("/form_templates/request_num_ex.jsp").forward(request, response);
			return;
		}

		session.setAttribute("countCartons", countCartons);
		request.getRequestDispatcher(new InvOrderUnitInfo().getClass().getSimpleName()).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

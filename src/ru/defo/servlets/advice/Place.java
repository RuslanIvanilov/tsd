package ru.defo.servlets.advice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BinController;
import ru.defo.controllers.SessionController;
import ru.defo.model.WmBin;
import ru.defo.util.AppUtil;

/**
 * Servlet implementation class ScanDoc
 */
@WebServlet("/advice/Place")
public class Place extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());
		WmBin bin = null;

		if(session.getAttribute("advicecode")!=null && !request.getParameter("inputfield").contains(session.getAttribute("advicecode").toString())){

			session.setAttribute("bincode", AppUtil.getReqSessionAttribute(request, session, "inputfield", "", true));

			if(session.getAttribute("bincode") != null){
				bin = new BinController().getBin(session.getAttribute("bincode").toString());
				if(!(bin instanceof WmBin)){
					session.setAttribute("bincode",null);
					System.out.println("WRONG BARCODE for BIN :: inputfield "+request.getParameter("inputfield"));
				}
			}
		}


		if(bin instanceof WmBin)
		{
			SessionController.clear(session);
			request.getRequestDispatcher("/" +AppUtil.getPackageName(this)+"/"+new Unit().getClass().getSimpleName()).forward(request, response);
			return;
		}

		session.setAttribute("SourceClassName",this.getClass().getSimpleName());
		session.setAttribute("Title","TSD Приемка");
		session.setAttribute("SubmitAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
		session.setAttribute("HeaderCaption", "");
		session.setAttribute("TopText","Приемка место");
		session.setAttribute("CenterText","Сканируйте штрих-код <b><u>Места приемки</u> (ячейки)</b>");
		session.setAttribute("CancelName","Назад");
		session.setAttribute("CancelAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new ScanDoc().getClass().getSimpleName());
		request.getRequestDispatcher("/form_templates/request_text.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

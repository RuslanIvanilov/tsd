package ru.defo.servlets.pick;

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
import ru.defo.model.WmBin;
import ru.defo.util.AppUtil;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class ScanDoc
 */
@WebServlet("/pick/ShptBinRequest")
public class ShptBinRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());
		session.setAttribute("bincode",null);
		WmBin bin = null;

		System.out.println("pick/ShptBinRequest dest unit : "+session.getAttribute("unitcode")+" bin for shpt "+request.getParameter("inputfield")+" : articlecode :"+session.getAttribute("articlecode"));

		/* Проверка ячейки размещения */
		if(request.getParameter("inputfield") != null && request.getParameter("inputfield").equals(session.getAttribute("articlecode").toString()) == false)
		session.setAttribute("bincode", AppUtil.getReqSessionAttribute(request, session, "inputfield", "", true));

		if(session.getAttribute("bincode") !=null && (!session.getAttribute("bincode").toString().isEmpty())){
			bin = new BinController().getBin(session.getAttribute("bincode").toString());
			if(!(bin instanceof WmBin)){
				session.setAttribute("message", ErrorMessage.BC_NOT_BIN.message(new ArrayList<String>(Arrays.asList(String.valueOf(session.getAttribute("bincode"))))));
				session.setAttribute("action", this.getClass().getSimpleName());
				request.getRequestDispatcher("/errorn.jsp").forward(request, response);
				session.setAttribute("bincode", null);
				request.getParameter("inputfield").replaceAll("\\w|\\d", "");
				return;
			}
		}

		if(session.getAttribute("bincode") == null || session.getAttribute("bincode").toString().isEmpty()){
			session.setAttribute("SourceClassName",this.getClass().getSimpleName());
			session.setAttribute("Title","TSD Подбор запрос ячейки консолидации");
			session.setAttribute("SubmitAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("HeaderCaption", "Подбор "+session.getAttribute("ordercode")+" ");
			session.setAttribute("TopText","");
			session.setAttribute("CenterText","Сканируйте штрих-код ячейки размещения поддона <br><b>"+session.getAttribute("unitcode")+"</b> для контроля");

			session.setAttribute("CancelName","Назад");
			//session.setAttribute("CancelAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new ArticleSelect().getClass().getSimpleName());
			session.setAttribute("CancelAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new DestUnit().getClass().getSimpleName());

			request.getRequestDispatcher("/form_templates/request_text.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher("ShptLevelRequest").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

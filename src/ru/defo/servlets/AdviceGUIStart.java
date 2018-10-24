package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.AdviceController;
import ru.defo.controllers.CarController;
import ru.defo.controllers.SessionController;
import ru.defo.util.AppUtil;


/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/AdviceGUIStart")
public class AdviceGUIStart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 
		HttpSession session = request.getSession();
		new SessionController().initGUIadvList(session);
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());
		AdviceController advCtrl = new AdviceController();

		session.setAttribute("date_filter", AppUtil.getReqSessionAttribute(request, session, "date_filter", AppUtil.getToday(), false));
		session.setAttribute("doc_filter", AppUtil.getReqSessionAttribute(request, session, "doc_filter", "", false));
		session.setAttribute("status_filter", AppUtil.getReqSessionAttribute(request, session, "status_filter", "", false));
		session.setAttribute("car_filter", AppUtil.getReqSessionAttribute(request, session, "car_filter", "", false));
		session.setAttribute("dok_filter", AppUtil.getReqSessionAttribute(request, session, "dok_filter", "", false));
		session.setAttribute("comment_filter", AppUtil.getReqSessionAttribute(request, session, "comment_filter", "", false));
		session.setAttribute("error_filter", AppUtil.getReqSessionAttribute(request, session, "error_filter", "", false));

		System.out.println("request : "+request.getParameter("carcode"));

		if(request.getParameter("carcode") != null){
			if(!request.getParameter("carcode").contains("undefined")){
				String err = new CarController().getErrorText(request.getParameter("carcode"));
				if(err != null){
					session.setAttribute("message", err);
					session.setAttribute("action", "AdviceGUIStart");
					request.getRequestDispatcher("errorn.jsp").forward(request, response);
					return;
				}

			}



			String advStr = request.getParameter("advlist[]");
			if(advStr != null && !advStr.contains("undefined") && advStr.length() != 0)
			{
				String[] advList = advStr.split(",");

				for(int i=0; i<advList.length; i++){
					//System.out.println("POST::: advList "+advList[i]+" --> "+request.getParameter("carcode"));
					advCtrl.setAdviceCar(Long.valueOf(advList[i]), request.getParameter("carcode"));

					if(request.getParameter("dok").length()>0){
						int dok = Integer.decode(request.getParameter("dok"));
						if(dok >0 && dok < 9){
							advCtrl.setAdviceDok(Long.valueOf(advList[i]), dok);
						}
					}

				}
			}

		}
		request.getRequestDispatcher("/gui/adv_list.jsp").forward(request, response);
		return;

	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

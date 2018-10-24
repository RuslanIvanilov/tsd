package ru.defo.servlets.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.AdviceController;
import ru.defo.controllers.PreAdviceController;
import ru.defo.controllers.SessionController;
import ru.defo.model.WmAdvice;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;


/**
 * Servlet implementation class ArticleQtyList
 */
@WebServlet("/gui/PreAdviceList")
public class PreAdviceList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		new SessionController().initPreAdviceList(session);
		System.out.println(this.getClass().getSimpleName());
		PreAdviceController preAdvCtrl = new PreAdviceController();
		Long newAdviceId = null;

		if(request.getParameter("marked") != null && request.getParameter("expdate") != null){
			List<String> list = new ArrayList<String>(Arrays.asList(request.getParameter("marked").split(",")));

			if(!list.get(0).isEmpty())
			{
				newAdviceId = new AdviceController().getNextAdviceId();
			}

			for(String adviceIdTxt : list){

				//Проверка что у приемки нет ошибок


				if(request.getParameter("wmadvicelink").length()>0)
				{
					//Проверить номер WMS приемки
					Long advId = Long.decode(request.getParameter("wmadvicelink"));
					WmAdvice advice = new AdviceController().getAdviceById(advId);
					if(advice != null)
						preAdvCtrl.createAdvice(adviceIdTxt, advice.getAdviceId(), advice.getExpectedDate().toString());
				} else
				{
					preAdvCtrl.createAdvice(adviceIdTxt, newAdviceId, request.getParameter("expdate"));
				}
			}
		}

		session.setAttribute("adviceflt", AppUtil.getReqSessionAttribute(request, session, "adviceflt", "", false));
		session.setAttribute("clientdocflt", AppUtil.getReqSessionAttribute(request, session, "clientdocflt", "", false));
		session.setAttribute("clientdescrflt", AppUtil.getReqSessionAttribute(request, session, "clientdescrflt", "", false));
		session.setAttribute("dateflt", AppUtil.getReqSessionAttribute(request, session, "dateflt", "", false));
		session.setAttribute("typeflt", AppUtil.getReqSessionAttribute(request, session, "typeflt", "", false));
		session.setAttribute("statusflt", AppUtil.getReqSessionAttribute(request, session, "statusflt", "", false));
		session.setAttribute("wmsshptflt", AppUtil.getReqSessionAttribute(request, session, "wmsshptflt", "", false));

		session.setAttribute("routeflt", AppUtil.getReqSessionAttribute(request, session, "routeflt", "", false));
		session.setAttribute("rdateflt", AppUtil.getReqSessionAttribute(request, session, "rdateflt", "", false));
		session.setAttribute("carflt", AppUtil.getReqSessionAttribute(request, session, "carflt", "", false));
		session.setAttribute("carmarkflt", AppUtil.getReqSessionAttribute(request, session, "carmarkflt", "", false));

		session.setAttribute("rowcount", AppUtil.getReqSessionAttribute(request, session, "rowcount", String.valueOf(DefaultValue.TABLE_ROW_COUNT), false));

		request.getRequestDispatcher("/gui/advice_list.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}



}

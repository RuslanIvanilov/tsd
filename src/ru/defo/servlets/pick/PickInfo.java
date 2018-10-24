package ru.defo.servlets.pick;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.OrderController;
import ru.defo.managers.JobManager;
import ru.defo.model.WmOrderPos;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/pick/PickInfo")
public class PickInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());
		session.setAttribute("ShowInfo", AppUtil.getReqSessionAttribute(request, session, "ShowInfo", "", false));
		OrderController ordCtrl = new OrderController();

		System.out.println("ordId: "+session.getAttribute("orderid")+" ordPos: "+session.getAttribute("orderposid"));
		WmOrderPos orderPos = ordCtrl.getOrderPosByTemp(ordCtrl.getTempOrderPosByIdPosId(session.getAttribute("orderid").toString(), session.getAttribute("orderposid").toString()));

		new JobManager().closeJob(session.getAttribute("userid").toString(), 2L);

		/* Вывод инфо по подобранной позиции */

		session.setAttribute("SourceClassName",this.getClass().getSimpleName());
		session.setAttribute("Title","TSD Подбор "+session.getAttribute("orderid"));
		session.setAttribute("SubmitAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new ArticleSelect().getClass().getSimpleName());
		session.setAttribute("HeaderCaption", "Подбор товара "+session.getAttribute("articlecode")+"<b> завершен!</b>");
		session.setAttribute("TopText","Подобрано: "+orderPos==null?0:(orderPos.getFactQuantity()==null?0:orderPos.getFactQuantity())+" шт.");
		session.setAttribute("CenterText",null);
		session.setAttribute("Btn1Name","Ок");
		session.setAttribute("Btn1Action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new ArticleSelect().getClass().getSimpleName());
		session.setAttribute("Btn2Name",null);

		request.getRequestDispatcher(DefaultValue.FORM_INFO_TXT).forward(request, response);
		return;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

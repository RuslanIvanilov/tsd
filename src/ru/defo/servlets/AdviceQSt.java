package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.QualityStateController;
import ru.defo.controllers.QuantController;
import ru.defo.model.WmQualityState;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class InvBinFrnQSt
 */
@WebServlet("/AdviceQSt")
public class AdviceQSt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/AdviceQSt");

        QuantController quantCtrl = new QuantController();
		Long unitQState = quantCtrl.getUnitQualityState(String.valueOf(session.getAttribute("unitcode")), session.getAttribute("userid").toString(), DefaultValue.ADVICE);
		Long qStateSelected = null;

		if(request.getParameter("state") != null){
			qStateSelected = Long.valueOf(request.getParameter("state"));
			WmQualityState qState = new QualityStateController().getQStateById(qStateSelected);
			session.setAttribute("qualityname", qState.getDescription());
			session.setAttribute("qualitystateid", qState.getQualityStateId());

		}

		if(qStateSelected == null){
			session.setAttribute("actionpage", "AdviceQSt");
			request.getRequestDispatcher("qstate_list.jsp").forward(request, response);
			return;
		}

		/* Если товар такой же есть на поддоне, проверить состояние качества */
		if(unitQState != null  && unitQState != qStateSelected){
			session.setAttribute("message", ErrorMessage.DIF_QAULITY_STATE.message());
			session.setAttribute("action", "advice_article_info.jsp");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher("AdviceFinish").forward(request, response);
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

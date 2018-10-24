package ru.defo.servlets;

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
import ru.defo.controllers.SessionController;
import ru.defo.model.WmAdvice;
import ru.defo.model.views.Vadvice;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/AdviceDoc")
public class AdviceDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/AdviceDoc");
		Long adviceId = null;
		String advClientDocNoFlt = null;

		AdviceController advCtrl = new AdviceController();

		if(request.getParameter("advice_id_txt") != null)
			adviceId = Long.decode(request.getParameter("advice_id_txt"));

		if(request.getParameter("advice_txt") != null)
			advClientDocNoFlt = request.getParameter("advice_txt");

		if(adviceId != null){
			WmAdvice advice = advCtrl.getAdviceById(adviceId);
			if(advice != null)
				advClientDocNoFlt = advice.getAdviceCode();
		}

		if(advClientDocNoFlt == null){
			request.getRequestDispatcher("AdviceStart").forward(request, response);
		}

		List<Vadvice> vAdviceList = advCtrl.getVAdviceListByAdviceCodeFilter(advClientDocNoFlt);

		if (vAdviceList.isEmpty()) {
			session.setAttribute("message",
					ErrorMessage.WRONG_ADVICE_FILTER.message(new ArrayList<String>(Arrays.asList(advClientDocNoFlt))));
			session.setAttribute("page", "advice_doc.jsp");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		if (vAdviceList.size() == 1) {
			new SessionController().fillVAdviceSession(vAdviceList.get(0), session);
			request.getRequestDispatcher("advice_info.jsp").forward(request, response);
			return;
		}

		if(adviceId == null){
			request.getRequestDispatcher("advice_list.jsp").forward(request, response);
			return;
		}

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

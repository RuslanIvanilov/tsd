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
import ru.defo.model.views.Vadvice;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/AdviceCar")
public class AdviceCar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/AdviceCar");

		AdviceController advCtrl = new AdviceController();

		String carFilter = request.getParameter("advice_car_txt");
		if(carFilter != null)
		session.setAttribute("carfilter", carFilter);
		List<Vadvice> vAdviceList = advCtrl.getVAdviceListByCarFilter(session.getAttribute("carfilter").toString());

		if (vAdviceList.isEmpty()) {
			session.setAttribute("message",
					ErrorMessage.WRONG_CAR_FILTER.message(new ArrayList<String>(Arrays.asList(carFilter))));
			session.setAttribute("action", "AdviceStart");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		if (vAdviceList.size() == 1) {
			fillSessionData(vAdviceList.get(0), session);
			request.getRequestDispatcher("advice_info.jsp").forward(request, response);
			return;
		}

		session.setAttribute("backpage", "AdviceStart");
		request.getRequestDispatcher("advice_list.jsp").forward(request, response);
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

	private void fillSessionData(Vadvice vAdvice, HttpSession session)
	{
		session.setAttribute("adviceId", vAdvice.getAdviceId());
		session.setAttribute("adviceTypeId", vAdvice.getAdviceTypeId());
		session.setAttribute("adviceCode", vAdvice.getAdviceCode());
		session.setAttribute("expectedDate", vAdvice.getExpectedDate());
		session.setAttribute("carId", vAdvice.getCarId());
		session.setAttribute("placeCount", vAdvice.getPlaceCount());
		session.setAttribute("statusId", vAdvice.getStatusId());
		session.setAttribute("clientDocCode", vAdvice.getClientDocCode());
		session.setAttribute("type", vAdvice.getType());
		session.setAttribute("carCode", vAdvice.getCarCode());
		session.setAttribute("carMark", vAdvice.getCarMark());
		session.setAttribute("carModel", vAdvice.getCarModel());
		session.setAttribute("binCode", vAdvice.getBinCode());
		session.setAttribute("status", vAdvice.getStatus());
	}

}

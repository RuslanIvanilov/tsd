package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BarcodeController;

/**
 * Servlet implementation class AssignSave
 */
@WebServlet("/AssignSave")
public class AssignSave extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/AssignSave");

		/* Через конфирм запрос подтверждения операции */
		/* --Еще одно подтверждение - немного перебор, но оставлю на всякий случай ;-)

		if (session.getAttribute("assign") == null) {
			String barcode = String.valueOf(session.getAttribute("barcode"));
			String skuname = String.valueOf(session.getAttribute("skuname"));
			String articlename = String.valueOf(session.getAttribute("articlename"));

			session.setAttribute("question", ConfirmMessage.ASK_ASSIGN_BC_SKU
					.message(new ArrayList<String>(Arrays.asList(barcode, skuname, articlename))));
			session.setAttribute("action_page", "AssignSave");
			session.setAttribute("back_page", "AssignArticleInfo");
			session.setAttribute("assign", 1);
			request.getRequestDispatcher("confirmn.jsp").forward(request, response);
			return;
		}
		*/

		/* Сама запись в БД */
		BarcodeController bcCtrl = new BarcodeController();
		bcCtrl.addBarcode(String.valueOf(session.getAttribute("barcode")).trim(), String.valueOf(session.getAttribute("articleid")), String.valueOf(session.getAttribute("skuid")), String.valueOf(session.getAttribute("userid")));
		request.getRequestDispatcher("AssignStart").forward(request, response);
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

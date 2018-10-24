package ru.defo.servlets.pick;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BinController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmBin;
import ru.defo.model.WmUnit;
import ru.defo.util.AppUtil;

/**
 * Servlet implementation class PrintArticleFind
 */
@WebServlet("/pick/BinChangeRequest")
public class BinChangeRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		System.out.println(this.getClass().getSimpleName());

		WmBin bin = new BinController().getBin(session.getAttribute("selectedbin").toString());
		WmUnit unit = new UnitController().getUnitByUnitCode(session.getAttribute("expunitcode"));

		new BinController().markBinUnitNeedCheck(session, bin, unit);

		session.setAttribute("Btn2Name", null);

		session.setAttribute("selectedbin", null);
		session.setAttribute("srcunitcode", null);
		session.setAttribute("expunitcode", null);

		request.getRequestDispatcher("/" +AppUtil.getPackageName(this)+"/"+new BinRequest().getClass().getSimpleName()).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

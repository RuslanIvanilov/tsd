package ru.defo.servlets.shpt_ctrl_det;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.SessionController;
import ru.defo.controllers.UnitController;
import ru.defo.managers.UnitManager;
import ru.defo.model.WmBin;
import ru.defo.model.WmUnit;
import ru.defo.util.AppUtil;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/shpt_ctrl_det/Info")
public class Info extends HttpServlet {
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
		new SessionController().initControlScan(session);

		session.setAttribute("scanmap", new LinkedHashMap<String, String>());

		UnitManager unitMgr = new UnitManager();

		WmUnit unit = new UnitController().getWmUnit(session.getAttribute("unitcode").toString());
		if(unit != null)
		{
			session.setAttribute("articlecount", new UnitController().getArticleCount(unit.getUnitId()));

			WmBin bin = unitMgr.getBinByUnitId(unit.getUnitId());
			if(bin != null)
				session.setAttribute("bincode", bin.getBinCode());
		}


		session.setAttribute("actionpage",request.getContextPath() + "/" + AppUtil.getPackageName(new Article()) + "/"+(new Article()).getClass().getSimpleName());
		session.setAttribute("backpage",  request.getContextPath() + "/" + AppUtil.getPackageName(new Unit()) + "/"+ (new Unit()).getClass().getSimpleName());
		request.getRequestDispatcher("info.jsp").forward(request, response);

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

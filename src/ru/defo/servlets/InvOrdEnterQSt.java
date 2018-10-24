package ru.defo.servlets;

import java.io.IOException; 
import java.util.HashMap;

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
 * Servlet implementation class InvOrdEnterQSt
 */
@WebServlet("/InvOrdEnterQSt")
public class InvOrdEnterQSt extends HttpServlet {
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

		if(session.getAttribute("userid")==null){
			request.getRequestDispatcher(DefaultValue.FORM_START).forward(request, response);
			return;
		}

		if(request.getParameter("qtStateSelected")==null || request.getParameter("qtStateSelected").isEmpty()){
			session.setAttribute("articlelist", session.getAttribute("datalist"));

			HashMap<Integer, String> quantMap = new  QualityStateController().getQualityStateMapAll();
			session.setAttribute("datalist", quantMap);

			session.setAttribute("selectedvalue", "qtStateSelected");
			session.setAttribute("backpage", new InvOrdEnterQty().getClass().getSimpleName());
			session.setAttribute("btnOkName", "Далее");
			session.setAttribute("detailpage", this.getClass().getSimpleName());
			session.setAttribute("listheader", "Состояние качества");

			request.getRequestDispatcher(DefaultValue.FORM_LIST).forward(request, response);
			return;
		}

		Long qStateFromUnitLong = new QuantController().getUnitQualityState(String.valueOf(session.getAttribute("unitcode")), session.getAttribute("userid").toString(), DefaultValue.INVENTORY);
		int qualityStateFromUnit = qStateFromUnitLong==null?0:qStateFromUnitLong.intValue();
		if(qStateFromUnitLong !=null){
			if(Integer.decode(request.getParameter("qtStateSelected")) != qualityStateFromUnit){
				session.setAttribute("message", ErrorMessage.DIF_QAULITY_STATE.message());
				session.setAttribute("action", this.getClass().getSimpleName());
				request.getRequestDispatcher("errorn.jsp").forward(request, response);
				return;
			}
		}

		session.setAttribute("qtStateSelected", request.getParameter("qtStateSelected"));
		WmQualityState qstate = new QualityStateController().getQStateById(Long.valueOf(request.getParameter("qtStateSelected")));

		request.getRequestDispatcher(new InvOrdToSave().getClass().getSimpleName()).forward(request, response);
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

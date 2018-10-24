package ru.defo.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.defo.controllers.QuantController;
import ru.defo.controllers.UnitController;
import ru.defo.managers.UserManager;
import ru.defo.model.WmBin;
import ru.defo.model.WmUnit;
import ru.defo.model.WmUser;
import ru.defo.util.ConfirmMessage;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;
import ru.defo.util.HibernateUtil;

/**
 * Servlet implementation class InvUnitAction
 */
@WebServlet("/InvUnitAction")
public class InvUnitAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvUnitAction v2 ");

		WmUnit unit = new UnitController().getUnitByUnitCode(session.getAttribute("unitcode"));

		if(session.getAttribute("quantity") != null)
		{
			WmBin bin = new UnitController().getBinByUnit(unit);
			if(!bin.getAreaCode().contains(DefaultValue.INCOME_AREA_CODE))
			{
				session.setAttribute("message", ErrorMessage.WRONG_UNIT_CLEAN_PLACE.message(new ArrayList<String>(Arrays.asList(DefaultValue.INCOME_AREA_CODE))));
				session.setAttribute("action", "InvUnit2");
				request.getRequestDispatcher("errorn.jsp").forward(request, response);
				return;
			}


			session.setAttribute("question", ConfirmMessage.ASK_CLEAR_UNIT.message(new ArrayList<String>(Arrays.asList(String.valueOf(session.getAttribute("unitcode"))))));
			session.setAttribute("action_page", "InvUnitAction");
			session.setAttribute("back_page", "InvStart");
			session.setAttribute("quantity", null);
			request.getRequestDispatcher("confirmn.jsp").forward(request, response);
			return;
		}
		/*
		new QuantController().delQuantByUnitId(Long.valueOf(session.getAttribute("userid").toString()),
					Long.valueOf(session.getAttribute("unitid").toString()), DefaultValue.INVENT_CLEAR_UNIT_TEXT);
		*/
		Session sessionH = HibernateUtil.getSession();
		Transaction trn = sessionH.getTransaction();
		try{
			trn.begin();
			Long userId = Long.valueOf(session.getAttribute("userid").toString());
			WmUser user = new UserManager().getUserById(userId);
			//WmUnit unit = new UnitManager().getUnitByCode(session.getAttribute("unitcode").toString());
			//WmUnit unit = new UnitController().getUnitByUnitCode(session.getAttribute("unitcode"));

			new QuantController().delQuantByUnitCode(sessionH, user, unit);
			new UnitController().delUnitFromBin(sessionH, unit, user);
			trn.commit();
		} catch(Exception e){
			trn.rollback();
			sessionH.close();
		}


		request.getRequestDispatcher("inv_unit_action.jsp").forward(request, response);
		session.setAttribute("bincode", null);
		session.setAttribute("binid", null);
		session.setAttribute("scanedtext", null);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

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

import ru.defo.controllers.BinController;
import ru.defo.controllers.QuantController;
import ru.defo.controllers.SessionController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmUnit;
import ru.defo.util.ConfirmMessage;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

/**
 * Servlet implementation class InvBinFrnUnit
 */
@WebServlet("/InvBinFrnUnit")
public class InvBinFrnUnit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvBinFrnUnit v2");

		SessionController.check(request, response);

		WmUnit unit = null;
		String unitBarcode;

		UnitController unitCtrl = new UnitController();

		if(request.getParameter("scanedunit") == null && session.getAttribute("unitcode") == null){
			request.getRequestDispatcher("inv_bin_frn_unit.jsp").forward(request, response);
			return;
		}

		if(session.getAttribute("unitcode") == null){
		  unitBarcode = ru.defo.util.Bc.symbols(request.getParameter("scanedunit"));
		} else {
			unitBarcode = String.valueOf(session.getAttribute("unitcode"));
		}

		if (!unitCtrl.isUnitBarCode(unitBarcode)) {
			session.setAttribute("message", unitCtrl.getErrorText(unitBarcode));
			session.setAttribute("action", this.getClass().getSimpleName());
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		if (session.getAttribute("unitcode") == null) {
			/* Поддон существует? Если нет, то создать */
			unit = unitCtrl.getOrCreateUnit(HibernateUtil.getSession(), unitBarcode, null, session.getAttribute("userid"), DefaultValue.INVENTORY);
			session.setAttribute("unitcode", unit.getUnitCode());
		}

		if (session.getAttribute("action") == null) {
			/* Поддон в другой ячейке? */
			BinController binCtrl = new BinController();
			String currBincode = String.valueOf(session.getAttribute("bincode"));

			if (unit != null && unit.getBinId() != null) {
				if (!binCtrl.getBinCode(unit.getBinId()).equals(currBincode)) {
					session.setAttribute("question",
							ConfirmMessage.AKS_MOVE_UNIT_FROM_BIN.message(new ArrayList<String>(
									Arrays.asList(unitBarcode, binCtrl.getBinCode(unit.getBinId()), currBincode))));
					session.setAttribute("action_page", "InvBinFrnUnit");
					session.setAttribute("back_page", "InvBinFrn");
					session.setAttribute("action", "MOV");
					session.setAttribute("quantid", request.getParameter("quantid"));
					request.getRequestDispatcher("confirmn.jsp").forward(request, response);
					return;
				}
			}
		}

		if(!new BinController().checkBin(request, response)) return ;

		unitCtrl.setBinUnit(session.getAttribute("bincode"), unitBarcode, DefaultValue.INVENT_UNIT_TO_BIN_TEXT,
				session.getAttribute("userid"));
		session.setAttribute("action",null);

		new QuantController().initQuantAttributes(session, unitCtrl.getWmUnit(unitBarcode).getUnitId());

		request.getRequestDispatcher("inv_bin_frn_unit_info.jsp").forward(request, response);

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

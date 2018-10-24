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

import ru.defo.controllers.QualityStateController;
import ru.defo.controllers.QuantController;
import ru.defo.controllers.UnitController;
import ru.defo.managers.BinManager;
import ru.defo.model.WmBin;
import ru.defo.model.WmUnit;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class InvBinFrnQSt
 */
@WebServlet("/InvBinFrnQSt")
public class InvBinFrnQSt extends HttpServlet {
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
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		QuantController quantCtrl = new QuantController();
		Long qualityStateFromUnit = new QuantController().getUnitQualityState(String.valueOf(session.getAttribute("unitcode")), session.getAttribute("userid").toString(), DefaultValue.INVENTORY);
		Long qualityStateSelected = null;

		if(request.getParameter("state") != null){
			qualityStateSelected = Long.valueOf(request.getParameter("state"));
		}

		if(qualityStateSelected == null){
			request.getRequestDispatcher("inv_bin_frn_qst.jsp").forward(request, response);
			return;
		}

		/* Если товар такой же есть на поддоне, проверить состояние качества */
		if(qualityStateFromUnit != null  && qualityStateFromUnit != qualityStateSelected){
			session.setAttribute("message", ErrorMessage.DIF_QAULITY_STATE.message());
			session.setAttribute("action", "InvBinFrnSku");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		if(session.getAttribute("bincode")==null){
			session.setAttribute("message", ErrorMessage.UNIT_ZERO_BIN.message(new ArrayList<String>(Arrays.asList(session.getAttribute("unitcode")==null?"":session.getAttribute("unitcode").toString()))));
			session.setAttribute("action", "InvStart");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		session.setAttribute("state", qualityStateSelected);
		session.setAttribute("qualityname", new QualityStateController().getQualityStateDescriptionById(qualityStateSelected));

		Long unitId = new UnitController().getWmUnit(String.valueOf(session.getAttribute("unitcode"))).getUnitId();


		/*quantCtrl.createQuant(session.getAttribute("articleid"), session.getAttribute("skuid"),
				session.getAttribute("state"), session.getAttribute("quantity"), unitId,
				session.getAttribute("userid"), DefaultValue.INVENT_ADD_QUANT_UNIT_TEXT);
				*/
		String err = quantCtrl.createQuant(session, DefaultValue.INVENT_ADD_QUANT_UNIT_TEXT);
		if(err.length()!=0){
			session.setAttribute("message", err+"\tОперация отменена!");
			session.setAttribute("action", "InvStart");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		quantCtrl.initQuantAttributes(session, unitId);
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

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

import ru.defo.controllers.BarcodeController;
import ru.defo.controllers.BinController;
import ru.defo.controllers.QuantController;
import ru.defo.controllers.SessionController;
import ru.defo.controllers.UnitController;
import ru.defo.managers.UnitManager;
import ru.defo.model.WmBin;
import ru.defo.model.WmUnit;
import ru.defo.util.ConfirmMessage;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class InvBinFrn
 */
@WebServlet("/InvBinFrn")
public class InvBinFrn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BinController binCtrl = new BinController();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvBinFrn v2");
		session.setAttribute("action",null);

		if(session.getAttribute("userid")==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		/* запрос этажа */
		if (session.getAttribute("binlevel") == null) {
			if (request.getParameter("enteredlevel") == null || request.getParameter("enteredlevel") == "") {
				initServlet(session);

				session.setAttribute("servlet_name", "InvBinFrn");
				session.setAttribute("backpage", "InvStart");
				request.getRequestDispatcher("level_bin.jsp").forward(request, response);
				return;
			} else {
				if (binCtrl.isLevelExist(session.getAttribute("bincode"), request.getParameter("enteredlevel"))) {
					session.setAttribute("binlevel", request.getParameter("enteredlevel"));
				} else {
					request.getRequestDispatcher("level_bin.jsp").forward(request, response);
					return;
				}

			}

		}

		/* Добавление этажа к коду ячейки */
		if (session.getAttribute("binlevel") != null)
			session.setAttribute("bincode",
					binCtrl.getBinWithLevel(session.getAttribute("bincode"), session.getAttribute("binlevel")));

		if (!new BarcodeController().isBinBarcode(session.getAttribute("bincode"))) {
			session.setAttribute("message", ErrorMessage.BC_NOT_BIN
					.message(new ArrayList<String>(Arrays.asList(String.valueOf(session.getAttribute("bincode"))))));
			session.setAttribute("action", "InvStart");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			session.setAttribute("bincode", null);
			return;
		}

		/* Если ячейка заблокирована другим поддоном */
		/* Сообщить каким поддоном  */
		WmBin bin = new BinController().getBin(session.getAttribute("bincode"));
		if(bin.getReservForUnitId()!= null){
			WmUnit unit = new UnitManager().getUnitById(bin.getReservForUnitId());
			/*
			session.setAttribute("message", session.getAttribute("bincode").toString()+" - эта "+DefaultValue.BIN_RESERV_FOR_UNIT+" "+unit.getUnitCode()+" !");
			session.setAttribute("action", "InvStart");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			*/

			session.setAttribute("question", ConfirmMessage.ASK_CLEAR_BIN_RESERV_FOR_UNIT.message(new ArrayList<String>(Arrays.asList(session.getAttribute("bincode").toString(),
																																	   unit.getUnitCode()
					                                                                                                                  )
					                                                                                                    )
					                                                                              )
					             );
			session.setAttribute("action_page", new InvOrdClrBinReserv().getClass().getSimpleName());
			session.setAttribute("back_page", new InvStart().getClass().getSimpleName());
			request.getRequestDispatcher("confirmn.jsp").forward(request, response);


			return;
		}


		/* сбор информации по ячейке и вызов JSP-info */
		WmUnit unit = new UnitController().getUnitFromBincode(session.getAttribute("bincode"));
		if (unit != null) {
			session.setAttribute("unitcode", unit.getUnitCode());
			new QuantController().initQuantAttributes(session, unit.getUnitId());
		}

		if (session.getAttribute("binlevel") != null) {
			session.setAttribute("binlevel", null);

			if(session.getAttribute("unitcode") == null){
				request.getRequestDispatcher("InvBinFrnUnit").forward(request, response);
				return;
			} else
			request.getRequestDispatcher("inv_bin_frn_unit_info.jsp").forward(request, response);
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
/*
	protected void initQuantAttributes(HttpSession session, Long unitId) {
		VQuantInfoShort quantInfoShort = null;
		QuantController quantCtrl = new QuantController();

		// articlecount
		List<WmQuant> quantList = quantCtrl.getQuantList(unitId);
		session.setAttribute("articlecount", quantList.size());

		if (quantList.size() == 1) {
			WmQuant quant = quantList.get(0);
			if (quant != null) {
				quantInfoShort = quantCtrl.getVQuantInfoShort(quant.getQuantId());
				session.setAttribute("quantid", quant.getQuantId());
			}
			if (quantInfoShort != null) {
				session.setAttribute("articlecode", quantInfoShort.getArticleCode());
				session.setAttribute("articlename", quantInfoShort.getArtName());
				session.setAttribute("quantity", quantInfoShort.getQuantity());
				session.setAttribute("skuname", quantInfoShort.getSkuName());
				session.setAttribute("qualityname", quantInfoShort.getQualityName());
			}
		}

	}
*/
	protected void initServlet(HttpSession session){
		String binBarcode = String.valueOf(session.getAttribute("bincode"));
		new SessionController().initAll(session);
		session.setAttribute("bincode", binBarcode);
	}

}

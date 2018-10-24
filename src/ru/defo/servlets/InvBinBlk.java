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
import ru.defo.model.WmBin;
import ru.defo.model.WmUnit;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class InvBinBlk
 */
@WebServlet("/InvBinBlk")
public class InvBinBlk extends HttpServlet {
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
		System.out.println("/InvBinBlk v2");

		/* У доков только первый этаж! */
		if(session.getAttribute("bincode") != null){
			//WmBin bin = new BinController().getBin(String.valueOf(session.getAttribute("bincode")));
			@SuppressWarnings("deprecation")
			WmBin bin = new BinController().getBin(session.getAttribute("bincode").toString());
			if(bin instanceof WmBin && (bin.getAreaCode().equals(DefaultValue.INCOME_AREA_CODE) ||
					                    bin.getAreaCode().equals(DefaultValue.CONTROL_SHPT_AREA_CODE) ||
					                    bin.getAreaCode().equals(DefaultValue.LOST_AREA_CODE)))
				session.setAttribute("binlevel", 1);
		}else {
			session.setAttribute("message", ErrorMessage.UNIT_ZERO_BIN.message(new ArrayList<String>(Arrays.asList(session.getAttribute("unitcode")==null?"":session.getAttribute("unitcode").toString()))));
			session.setAttribute("action", "InvStart");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		//Insert 21.04.2017 {
		/* запрос этажа */
		if (session.getAttribute("binlevel") == null) {
			if (request.getParameter("enteredlevel") == null || request.getParameter("enteredlevel") == "") {
				initServlet(session);

				session.setAttribute("servlet_name", "InvBinBlk");
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
		//Insert 21.04.2017 }

		// Проверка поддона
		switch (getActionType(session.getAttribute("blkunit"))) {
		case 0:
			session.setAttribute("blkunit", null);
			removeUnit(request, response, session);
			break;

		case 1:
			session.setAttribute("blkunit", null);
			addUnit(request, response, session);
			break;

		default:
			session.setAttribute("unitcount",
					new UnitController().getBinUnitCount(session.getAttribute("bincode")) + " шт.");
			request.getRequestDispatcher("inv_bin_blk.jsp").forward(request, response);
			break;
		}

		//session.setAttribute("blkunit", null);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected int getActionType(Object actionType) {
		try {
			return Integer.valueOf(String.valueOf(actionType));
		} catch (Exception e) {
			return -1;
		}
	}

	protected boolean checkBarCode(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			String page) throws ServletException, IOException {

		//if (!new UnitController().isUnitBarCode(request.getParameter("scanedtext"))) {
			String errMsg = new UnitController().getErrorText(ru.defo.util.Bc.symbols(request.getParameter("scanedtext")));

			if (errMsg != null) {
				session.setAttribute("message", errMsg);
				session.setAttribute("action", page);
				request.getRequestDispatcher("errorn.jsp").forward(request, response);
                return false;
			} else {
		      return true;
			}
	}

	protected void addUnit(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		if(!checkBarCode(request, response, session, "inv_blk_addunit.jsp")) return;

		if(session.getAttribute("bincode")==null || session.getAttribute("bincode").toString().isEmpty()){
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}

		String unitCode = ru.defo.util.Bc.symbols(request.getParameter("scanedtext"));

		String msg = new BinController().getErrorTextBinFull(session.getAttribute("bincode"), unitCode);
		if(msg != null){
			session.setAttribute("message", msg);
			session.setAttribute("action", "InvStart");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		} else {

			if(!new BinController().checkBin(request, response)) return ;


		new UnitController().setBinUnit(session.getAttribute("bincode"), unitCode, DefaultValue.INVENT_UNIT_TO_BIN_TEXT,
				session.getAttribute("userid"));

		/* Проверка, что поддон пустой. */
		//if(new QuantController().unitIsEmpty(request.getParameter("scanedtext")) == true){
			session.setAttribute("unitcode", unitCode);

			int articleCount = new QuantController().getCountArticleInUnit(unitCode);

			session.setAttribute("articlecount", articleCount);
			request.getRequestDispatcher("inv_bin_frn_unit_info.jsp").forward(request, response);
			return;
		//}

		}

		//session.setAttribute("blkunit", null);
		//request.getRequestDispatcher("InvBinBlk").forward(request, response);
	}

	protected void removeUnit(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		checkBarCode(request, response, session, "inv_blk_remunit.jsp");

		WmUnit unit = new UnitController().getWmUnit(ru.defo.util.Bc.symbols(request.getParameter("scanedtext")));
		if (!new BinController().getBinCode(unit.getBinId()).equals(String.valueOf(session.getAttribute("bincode")))) {
			session.setAttribute("message", ErrorMessage.UNIT_NOT_FROM_BIN.message(new ArrayList<String>(Arrays.asList(ru.defo.util.Bc.symbols(request.getParameter("scanedtext")), String.valueOf(session.getAttribute("bincode"))))));
			session.setAttribute("action", "inv_blk_remunit.jsp");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		new UnitController().delUnitFromBin(unit.getUnitCode(), session.getAttribute("userid"), DefaultValue.INVENT_DEL_UNIT_TEXT);

		session.setAttribute("blkunit", null);
		request.getRequestDispatcher("InvBinBlk").forward(request, response);
	}

	protected void initServlet(HttpSession session){
		String binBarcode = String.valueOf(session.getAttribute("bincode"));
		new SessionController().initAll(session);
		session.setAttribute("bincode", binBarcode);
	}

}

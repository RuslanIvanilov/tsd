package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BinController;
import ru.defo.controllers.HistoryController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmUnit;
import ru.defo.util.DefaultValue;

/**
 * Servlet implementation class InvBlkBin
 */
@WebServlet("/InvBlkBin")
public class InvBlkBin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UnitController ctrl;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/InvBlkBin <----- DEPRECATED !!!");
		ctrl = new UnitController();

		if (Integer.valueOf(String.valueOf(session.getAttribute("blkunit"))) > 0) {
			addBlkBinUnit(request, response, session);
		} else {
			delBlkBinUnit(request, response, session);
		}

		session.setAttribute("blkunit", "");
	}

	protected void addBlkBinUnit(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		String unitBarCode = ru.defo.util.Bc.symbols(request.getParameter("scanedtext"));
		Long userId = Long.valueOf(String.valueOf(session.getAttribute("userid")));

		if (ctrl.isUnitBarCode(unitBarCode)) {
			WmUnit unit = ctrl.getWmUnit(unitBarCode);
			if (unit == null) {
				unit = ctrl.getUnitById(ctrl.createUnit(unitBarCode, null, DefaultValue.INVENT_CREATE_UNIT_TEXT, userId));
			}

			if(!new BinController().checkBin(request, response)) return ;

			if (unit.getBinId() == null) {
				ctrl.setBinUnit(new BinController().getBin((String) session.getAttribute("bincode")).getBinId(),
						unit.getUnitId(), DefaultValue.INVENT_UNIT_TO_BIN_TEXT, userId);
			}

			setUnitCount(ctrl, session);
			request.getRequestDispatcher("inv_blkbin_info.jsp").forward(request, response);

		} else {
			errMsgNotUnit(request, response, session, "inv_blk_addunit.jsp");
		}
	}

	protected void delBlkBinUnit(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		WmUnit unit = ctrl.getWmUnit(ru.defo.util.Bc.symbols(request.getParameter("scanedtext")));
		if (unit == null) {
			errMsgNotUnit(request, response, session, "inv_blk_remunit.jsp");
		}

		if (!new BinController().getBinCode(unit.getBinId()).equals(String.valueOf(session.getAttribute("bincode")))) {
			errMsgNotBinUnit(request, response, session, "inv_blk_remunit.jsp");
		}

		new HistoryController().addEntry(Long.valueOf(session.getAttribute("userid").toString()), unit.getUnitId(),
				DefaultValue.INVENT_DEL_UNITBLK_TEXT);
		ctrl.del_UnitFromBin(unit.getUnitId());

		setUnitCount(ctrl, session);
		request.getRequestDispatcher("inv_blkbin_info.jsp").forward(request, response);
	}

	protected void setUnitCount(UnitController ctrl, HttpSession session) {
		session.setAttribute("unitcount",
				ctrl.getUnitListSize(ctrl.getUnitListByBinId(
						new BinController().getBin(String.valueOf(session.getAttribute("bincode"))).getBinId()))
						+ " шт.");
	}

	protected void errMsgNotUnit(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			String page) throws ServletException, IOException {
		session.setAttribute("message", "Отсканированный штрих-код [..." + request.getParameter("scanedtext")
				+ "] не определен системой как штрих-код поддона!");
		session.setAttribute("page", "inv_blk_remunit.jsp");
		request.getRequestDispatcher("error.jsp").forward(request, response);
		return;
	}

	protected void errMsgNotBinUnit(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			String page) throws ServletException, IOException {
		session.setAttribute("message", "Отсканированный поддон [..." + request.getParameter("scanedtext")
				+ "] не числится в ячейке " + session.getAttribute("bincode") + "!");
		session.setAttribute("page", page);
		request.getRequestDispatcher("error.jsp").forward(request, response);
		return;
	}
/*  21.04.2017 --Ѕольше не используетс€, т.к. везде разрешено размещение нескольких артикулов на одной паллете
	protected void initQuantAttributes(HttpSession session, Long unitId) {
		VQuantInfoShort quantInfoShort = null;
		QuantController quantCtrl = new QuantController();

		WmQuant quant = quantCtrl.getWmQuant(unitId);
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
*/
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

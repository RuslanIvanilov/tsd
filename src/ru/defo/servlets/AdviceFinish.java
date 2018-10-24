package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.AdviceController;
import ru.defo.controllers.AdvicePosController;
import ru.defo.controllers.BinController;
import ru.defo.controllers.HistoryController;
import ru.defo.controllers.QuantController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmAdvicePos;
import ru.defo.model.WmQuant;
import ru.defo.model.WmUnit;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/AdviceFinish")
public class AdviceFinish extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdvicePosController advPosCtrl = new AdvicePosController();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/AdviceFinish");

		if(request.getParameter("action") == null){
			request.getRequestDispatcher("advice_article_fnlinfo.jsp").forward(request, response);
			return;
		}

		WmAdvicePos advPos = createAdvicePos(session);
		createQuantUnitByAdvPos(session, advPos);
		//advPosCtrl.addAdvicePos(advPos);


		System.out.println("create +advice_line, save data, +move to advice_info.jsp, clear session attributes. ");

		//request.getRequestDispatcher("advice_info.jsp").forward(request, response);
		request.getRequestDispatcher("AdviceUnit?advice_unit_txt="+session.getAttribute("unitcode").toString()).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected WmAdvicePos createAdvicePos(HttpSession session){
		Long adviceId = Long.decode(session.getAttribute("adviceId").toString());
		Long articleId = Long.decode(session.getAttribute("articleid").toString());
		Long skuId = Long.decode(session.getAttribute("skuid").toString());
		Long expectQuantity = Long.decode("0");
		Long factQuantity = Long.decode(session.getAttribute("quantity").toString());
		Long qualityStateId = Long.decode(session.getAttribute("qualitystateid").toString());
		Long lotId = null;
		Long statusId = Long.decode(session.getAttribute("statusId").toString());
		Long userId = Long.decode(session.getAttribute("userid").toString());

		return advPosCtrl.initAdvicePos(adviceId, articleId, skuId, expectQuantity, factQuantity, qualityStateId, lotId, statusId, userId);
	}

	protected void createQuantUnitByAdvPos(HttpSession session, WmAdvicePos advPos){
		UnitController unitCtrl = new UnitController();
		QuantController quantCtrl = new QuantController();
		WmUnit unit = unitCtrl.getWmUnit(session.getAttribute("unitcode").toString());
		Long unitId = unit.getUnitId();

		unit.setBinId(new BinController().getBin(session.getAttribute("binCode").toString()).getBinId());

		WmQuant newQuant = quantCtrl.addOrCreateQuant(HibernateUtil.getSession(), advPos.getArticleId(), advPos.getSkuId(), advPos.getLotId(), advPos.getQualityStateId(),
				null, advPos.getFactQuantity(), DefaultValue.DEFAULT_CLIENT_ID, unitId, advPos.getAdviceId(), advPos.getAdvicePosId(), advPos.getCreateUser());

		WmQuant quant = quantCtrl.getQuantByQuantId(newQuant.getQuantId());

		Long adviceTypeId = new AdviceController().getAdviceById(advPos.getAdviceId()).getAdviceTypeId();
		new HistoryController().addAdvicePosEntry(advPos.getCreateUser(), adviceTypeId, unit.getBinId(), quant, DefaultValue.ADVICE_POS_ADDED+" "+session.getAttribute("clientDocCode"));

	}

}

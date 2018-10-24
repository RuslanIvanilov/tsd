package ru.defo.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.HistoryController;
import ru.defo.controllers.QuantController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmArticle;
import ru.defo.model.WmQuant;
import ru.defo.model.WmUnit;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;
import ru.defo.util.HibernateUtil;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/ControlSave")
public class ControlSave extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		String diffText = "";
		String quantInfo = "";
		Long quantQty = 0L;
		ArticleController artCtrl = new ArticleController();

		System.out.println(this.getClass().getSimpleName());

		if(session.getAttribute("unitcode")==null){
			System.out.println("ВНИМАНИЕ!!! нулевой поддон у сотрудника "+session.getAttribute("userid"));

			session.setAttribute("message", ErrorMessage.FAKE_UNIT.message(new ArrayList<String>(Arrays.asList("NULL"))));
			session.setAttribute("action",  (ControlStart.class).getSimpleName());
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}
		WmUnit unit = new UnitController().getUnitByUnitCode(session.getAttribute("unitcode"));

		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> mapScan = (LinkedHashMap<String, String>) session.getAttribute("scanmap");

		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> mapResult = (LinkedHashMap<String, String>) session.getAttribute("finalmap");

		for(Map.Entry<String, String> entry : mapResult.entrySet()){
			WmArticle article = artCtrl.getArticleByCode(entry.getKey());
			WmQuant quant = new QuantController().getQuantByUnitArticle(unit, article);

			if(quant == null){
				//WmArticle article = artCtrl.getArticleByCode(entry.getKey());
				quant = new WmQuant();
				quant.setArticleId(article.getArticleId());
				quant.setSkuId(artCtrl.getBaseSkuByArticleId(article.getArticleId()).getSkuId());
				quant.setQuantity(0L);
			}

			quantQty = (quant.getQuantity()==null ? 0 : quant.getQuantity());
			quantInfo = " " +DefaultValue.QUANT_QTY+" пользователем "+quant.getCreateUser()+" : "+quantQty;

			String scanedQtyStr = mapScan.get(entry.getKey());

			Long balanceQty = Long.decode(scanedQtyStr==null?"0":scanedQtyStr)-quantQty;
			diffText = "."+DefaultValue.DIFF_TEXT+" ["+balanceQty+"]"+ quantInfo;

			String comment = DefaultValue.CONTROL+diffText;
			new HistoryController().addEntry(HibernateUtil.getSession(), Long.decode(session.getAttribute("userid").toString()), unit.getUnitId(), comment, (quant==null ? null : quant), Long.decode(entry.getValue().isEmpty()?"0":entry.getValue()));
		}

		session.setAttribute("scanmap", null);
		session.setAttribute("finalmap", null);
		request.getRequestDispatcher((ControlStart.class).getSimpleName()).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

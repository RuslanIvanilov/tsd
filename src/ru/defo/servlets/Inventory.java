package ru.defo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.BinController;
import ru.defo.controllers.QuantController;
import ru.defo.controllers.SessionController;
import ru.defo.controllers.SkuController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmArticle;
import ru.defo.model.WmBarcode;
import ru.defo.model.WmBin;
import ru.defo.model.WmSku;
import ru.defo.model.WmUnit;
import ru.defo.util.DefaultValue;

/**
 * Servlet implementation class Inventory
 */
@WebServlet("/Inventory")
public class Inventory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		new SessionController().initAttributes(session);
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/Inventory");

		if (request.getParameter("scanedtext") != null)
			session.setAttribute("scanedtext", request.getParameter("scanedtext"));


		/* Check is BIN */
		if (createBinPage(request, response, session)) {
			return;
		}

		/* Check is UNIT */
		UnitController ctrl = new UnitController();
		if (ctrl.isUnitBarCode((String) session.getAttribute("scanedtext"))) {
			WmUnit unit = ctrl.getWmUnit((String) session.getAttribute("scanedtext"));
			if (unit == null) {
				unit = ctrl.getUnitById(ctrl.createUnit(String.valueOf(session.getAttribute("scanedtext")), null,
						DefaultValue.INVENT_CREATE_UNIT_TEXT,
						Long.valueOf(String.valueOf(session.getAttribute("userid")))));
			}

			if (unit != null) {
				session.setAttribute("unitid", unit.getUnitId());
				session.setAttribute("unitcode", unit.getUnitCode());

				if (unit.getBinId() != null) {
					session.setAttribute("bincode", new BinController().getBinCode(unit.getBinId()));
				} else {
					session.setAttribute("bincode", "");
				}

				new QuantController().initQuantAttributes(session, unit.getUnitId());
			}

			request.getRequestDispatcher("inv_unit_info.jsp").forward(request, response);
			return;
		}

		/* Check is ARTICLE */
		if (createArticlePage(request, response, session)) {
			return;
		}

		/* Check ERROR */
		session.setAttribute("message", "Отсканированный штрих-код [..." + (String) session.getAttribute("scanedtext")
				+ "] не определен системой как штрих-код товара, ячейки или поддона!");
		session.setAttribute("page", "invent_start.jsp");
		request.getRequestDispatcher("error.jsp").forward(request, response);

	}
/*
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
	protected boolean createBinPage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		BinController binController = new BinController();
		WmBin bin = binController.getBin((String) session.getAttribute("scanedtext"));
		if (bin != null) {
			session.setAttribute("bincode", bin.getBinCode());

			if (bin.getDepthCount() == 1) {
				/* Замена на ячейку с выбранным этажом */
				if (request.getParameter("enteredtext") != null) {
					if (binController.isLevelExist(bin.getBinId(),
							Integer.decode(request.getParameter("enteredtext")))) {
						session.setAttribute("binlevel", request.getParameter("enteredtext"));
						/* Этаж */
						session.setAttribute("bincode", binController.getBinWithLevel(bin.getBinCode(),
								Integer.decode(request.getParameter("enteredtext"))));
						bin = (WmBin) binController.getBin((String) session.getAttribute("bincode"));
					}
				}

				/* Запрос этажа */
				if ((session.getAttribute("binlevel") == "") || (session.getAttribute("binlevel") == null)) {
					session.setAttribute("servlet_name", "Inventory");
					request.getRequestDispatcher("enter_level.jsp").forward(request, response);
					return true;
				}

				WmUnit unit = new UnitController()
						.getUnitListFirst(new UnitController().getUnitListByBinId(bin.getBinId()));
				if (unit != null) {
					session.setAttribute("unitid", unit.getUnitId());
					session.setAttribute("unitcode", unit.getUnitCode());

					new QuantController().initQuantAttributes(session, unit.getUnitId());
				}

				session.setAttribute("binlevel","");
				request.getRequestDispatcher("inv_frnbin_info.jsp").forward(request, response);
				return true;
			} else {
			/* Bulk bin */
				UnitController unitCtrl = new UnitController();
				List<WmUnit> units = unitCtrl.getUnitListByBinId(bin.getBinId());
				session.setAttribute("unitcount", unitCtrl.getUnitListSize(units) + " шт.");

		        request.getRequestDispatcher("inv_blkbin_info.jsp").forward(request, response);
			    return true;

			}
		}

		return (bin != null);
	}

	protected boolean createArticlePage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		WmArticle article = null;

		ArticleController ctrl = new ArticleController();
		WmBarcode barcode = ctrl.getBarCode((String) session.getAttribute("scanedtext"));
		if (barcode != null) {
			article = ctrl.getArticle(barcode.getArticleId());
			WmSku sku = new SkuController().getSku(barcode.getSkuId());

			if (article != null) {
				session.setAttribute("articleid", article.getArticleId());
				session.setAttribute("articlecode", article.getArticleCode());
				session.setAttribute("articlename", article.getDescription());
				session.setAttribute("skuname", sku.getDescription());
				session.setAttribute("skuid", sku.getSkuId());
				session.setAttribute("totalqty", "");
				session.setAttribute("savepage", "InvArticle");

				QuantController ctrlQuant = new QuantController();
				session.setAttribute("placescount", ctrlQuant.getPlacesCount(ctrlQuant.getVQuantInfoListByArticle(String.valueOf(session.getAttribute("articlecode")))));

				request.getRequestDispatcher("article_info.jsp").forward(request, response);
			}
		}
		return (article != null);
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

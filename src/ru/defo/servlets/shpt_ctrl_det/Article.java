package ru.defo.servlets.shpt_ctrl_det;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.managers.OrderManager;
import ru.defo.managers.OrderPosManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmOrder;
import ru.defo.model.WmOrderPos;
import ru.defo.util.AppUtil;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/shpt_ctrl_det/Article")
public class Article extends HttpServlet {
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
		String vol = null;

		if(request.getParameter("scaned_art_text") != null && !request.getParameter("scaned_art_text").trim().isEmpty())
		{
			WmArticle article = new ArticleController().getArticleByBarcode(ru.defo.util.Bc.symbols(request.getParameter("scaned_art_text").trim()));
			if(article == null)
			{
				session.setAttribute("message", new ArticleController().getErrorText(ru.defo.util.Bc.symbols(request.getParameter("scaned_art_text").trim())));
				session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
				request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
				return;
			}

			if(session.getAttribute("ordercode")==null){
				session.setAttribute("message", ErrorMessage.WRONG_SHPT_ORDERCODE.message(new ArrayList<String>(Arrays.asList("Не указано!"))));
				session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ new Order().getClass().getSimpleName());
				request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
				return;
			}

			/* scaned_art_text штрих-код артикула которого нет в документе отгрузки */
			WmOrder order = new OrderManager().getOrderByCode(session.getAttribute("ordercode").toString(), false);
			List<WmOrderPos> orderPos = new OrderPosManager().getOrderPosListByOrderArticle(order, article);
			if(orderPos.size()==0){
				session.setAttribute("message", ErrorMessage.ORDER_WRONG_ARTICLE.message(new ArrayList<String>(Arrays.asList(order.getOrderCode(), article.getArticleCode()))));
				session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
				request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
				return;
			}

			session.setAttribute("articlecode", article.getArticleCode());
			session.setAttribute("articlename", article.getDescription());
			session.setAttribute("skuname", new ArticleController().getBaseSkuByArticleId(article.getArticleId()).getDescription());

			@SuppressWarnings("unchecked")
			HashMap<String, String> map = (HashMap<String, String>) session.getAttribute("scanmap");

			vol = map==null?null:map.get(article.getArticleCode());
			vol = (vol == null? "1" : String.valueOf((Integer.decode(vol) + 1)));

			/* scaned_art_text штрих-код коробки артикула уже лишней в данном заказе */
			int expQtyByOrder =  new OrderPosManager().getExpectedQtyByOrderPosList(orderPos);
			int shptQtyByOrder = new OrderPosManager().getFactQtyByOrderPosList(orderPos);
			if(expQtyByOrder<(shptQtyByOrder+Integer.decode(vol))){
				session.setAttribute("message", ErrorMessage.EXTRA_CONTROL_SKU.message(new ArrayList<String>(Arrays.asList(article.getArticleCode(), article.getDescription()))));
				session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
				request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
				return;
			}


			map.remove(article.getArticleCode());
			map.put(article.getArticleCode(), vol);

			session.setAttribute("scanmap", map);
			session.setAttribute("quantity", vol);
		}


		session.setAttribute("actionpage", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
		session.setAttribute("backpage", request.getContextPath()+"/"+AppUtil.getPackageName(new Info())+"/"+new Info().getClass().getSimpleName());
		session.setAttribute("scanlist", request.getContextPath()+"/"+AppUtil.getPackageName(new ScanList())+"/"+new ScanList().getClass().getSimpleName());
		session.setAttribute("endpage",  request.getContextPath()+"/"+AppUtil.getPackageName(new DiffList())+"/"+new DiffList().getClass().getSimpleName());
		request.getRequestDispatcher("article.jsp").forward(request, response);

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

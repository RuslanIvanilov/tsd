package ru.defo.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController; 
import ru.defo.controllers.SessionController;
import ru.defo.managers.BarcodeManager; 
import ru.defo.model.WmArticle;
import ru.defo.model.WmBarcode; 
import ru.defo.model.WmSku; 
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class InvOrderSku
 */
@WebServlet("/InvOrderSku")
public class InvOrderSku extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		//session.setAttribute("nextcolor",null);

		if(session.getAttribute("inputfieldname")==null || !session.getAttribute("inputfieldname").toString().equals("inputsku"))
			new SessionController().initInvOrderSku(session);

		HashMap<Integer, String> map;

		int vol;
		WmArticle article = new WmArticle();

		if(request.getParameter("inputsku") != null && !request.getParameter("inputsku").isEmpty())
		{
			WmBarcode bc = new BarcodeManager().gerBarCode(request.getParameter("inputsku").trim());
			if(!(bc instanceof WmBarcode)){
				session.setAttribute("message", ErrorMessage.BC_NOT_SKU.message(new ArrayList<String>(
						Arrays.asList(request.getParameter("inputsku") )
						                                                                                     )));
				session.setAttribute("page", this.getClass().getSimpleName());
				request.getRequestDispatcher("errorn.jsp").forward(request, response);
				return;
			}

			article = new ArticleController().getArticleByBc(bc);
		}

		if(article.getArticleId()!=null){
			WmSku sku = new ArticleController().getBaseSkuByArticleId(article.getArticleId());

			new SessionController().fillArticleSku(session, article, sku);

			if(session.getAttribute("datalist") == null){
				map = new HashMap<Integer, String>();
			} else {
				map = (HashMap<Integer, String>) session.getAttribute("datalist");
			}

			vol = AppUtil.getValueDataList(map.get(article.getArticleId().intValue()), DefaultValue.DELIMITER)+1;

			session.setAttribute("scancount", (Integer.decode(session.getAttribute("scancount")==null?"0":session.getAttribute("scancount").toString())+1));

			int cnt = Integer.decode(session.getAttribute("countCartons").toString());
			if(AppUtil.getSkuCountMap(map) >=cnt)
			{
				session.setAttribute("message", ErrorMessage.EXTRA_SCANED_SKU.message(new ArrayList<String>(Arrays.asList(String.valueOf(cnt)))));
				session.setAttribute("action", this.getClass().getSimpleName());
				request.getRequestDispatcher("errorn.jsp").forward(request, response);
				return;
			}

			WmBarcode bc = new BarcodeManager().getBcByArticle(article);


			if(map != null) map.remove(article.getArticleId().intValue());
			map.put(article.getArticleId().intValue(), bc.getBarCode()+" "+DefaultValue.DELIMITER+" "+vol);

			session.setAttribute("datalist", map);
			session.setAttribute("quantity", vol);
		}

			//System.out.println("InvOrdSku *--> invent: "+session.getAttribute("inventoryid").toString()+" : "+session.getAttribute("inventoryposid").toString());

			if(session.getAttribute("nextcolor")==null) { session.setAttribute("nextcolor","1");} else { session.setAttribute("nextcolor",null); }

			session.setAttribute("inputfieldname", "inputsku");
			session.setAttribute("SourceClassName",this.getClass().getSimpleName());
			session.setAttribute("Title","TSD Инвентаризация задание");
			session.setAttribute("SubmitAction", this.getClass().getSimpleName());
			session.setAttribute("HeaderCaption", "");
			session.setAttribute("TopText","<small>Ячейка: <b>"+(session.getAttribute("bincode")==null?"":session.getAttribute("bincode").toString())+"</b>"
									  +"<br>Поддон: <b>"+(session.getAttribute("unitcode")==null?"":session.getAttribute("unitcode").toString())+"</b>"
					                  +"<br>"+(session.getAttribute("articlecode")==null?"":"Товар: <b>"+session.getAttribute("articlecode").toString()+"</b><br>"+session.getAttribute("articlename").toString())
									  +"</small>"
									  +"<br><big><b>"+(session.getAttribute("scancount")==null?"":session.getAttribute("scancount").toString())+"</b></big>"
					             );
			session.setAttribute("CenterText","Сканируйте <b><u>Товар</u></b>");

			session.setAttribute("CancelName","Назад");
			session.setAttribute("CancelAction", new InvOrdEnterCnt().getClass().getSimpleName());
			session.setAttribute("Btn2Name","Товары");
			session.setAttribute("Btn2Action", new InvOrdScanList().getClass().getSimpleName());

			session.setAttribute("Btn3Name","Хватит");
			session.setAttribute("Btn3Action", new InvOrderUnitInfo().getClass().getSimpleName());

			request.getRequestDispatcher("/form_templates/request_text_ex.jsp").forward(request, response);
			return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

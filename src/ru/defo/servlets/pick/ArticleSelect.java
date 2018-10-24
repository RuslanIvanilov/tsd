package ru.defo.servlets.pick;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.OrderController;
import ru.defo.controllers.PickController;
import ru.defo.controllers.UserController;
import ru.defo.managers.JobManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmJobType;
import ru.defo.model.WmOrder;
import ru.defo.model.WmOrderPos;
import ru.defo.model.WmUser;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class PrintArticleFind
 */
@WebServlet("/pick/ArticleSelect")
public class ArticleSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		System.out.println(this.getClass().getSimpleName());
		session.setAttribute("barcode", AppUtil.getReqSessionAttribute(request, session, "scanbc", "", true));

		if(session.getAttribute("ordercode") == null || session.getAttribute("userid") == null){
			  request.getRequestDispatcher("index.jsp").forward(request, response);
			  return;
			}
		WmArticle article = null;
		WmJobType jobType = new JobManager().getJobTypeById(2);

		session.setAttribute("bincode", null);
		session.setAttribute("expectedqty", null);
		session.setAttribute("orderposid",null);
		session.setAttribute("orderid",null);
		session.setAttribute("zerolevelbin",null);
		session.setAttribute("selectedbin",null);
		session.setAttribute("barcode", null);

		WmOrder order = new OrderController().getOrderByCode(session.getAttribute("ordercode").toString());
		WmUser user = new UserController().getUserById(session.getAttribute("userid").toString());

		WmOrderPos orderPos = new OrderController().getFisrtOpenOrderPos(order, session.getAttribute("vndgroup").toString(), user);

		/* Нет открытых строк, поддон подбора переместить в зону консолидации  ??? */
		if(!(orderPos instanceof WmOrderPos)){
			/* Если паллет подбора по WMPick пустой, то запрос сканирования нового документа {  */
			//WmOrder order = new OrderController().getOrderByCode(session.getAttribute("ordercode").toString());

			if(new PickController().getSumSkuUnitOrder(order.getOrderId(), session.getAttribute("unitcode").toString()) ==0){
				session.setAttribute("SourceClassName",this.getClass().getSimpleName());
				session.setAttribute("Title","TSD Подбор завершен");
				session.setAttribute("SubmitAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new ScanDoc().getClass().getSimpleName());
				session.setAttribute("HeaderCaption", "Подбор "+session.getAttribute("ordercode")+" ");
				session.setAttribute("TopText","Подбор данного заказа завершен!");
				session.setAttribute("CenterText",null);

				session.setAttribute("Btn1Name","Ок");
				//session.setAttribute("CancelAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new ArticleSelect().getClass().getSimpleName());
				session.setAttribute("Btn1Action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new ScanDoc().getClass().getSimpleName());

				request.getRequestDispatcher(DefaultValue.FORM_INFO_TXT).forward(request, response);
				return;
			}

			/* } иначе запрос ячейки временного хранения! (а-ля перемещение)*/
			if(request.getParameter("inputfield") != null) request.getParameter("inputfield").replaceAll("\\w|\\d", "");
			request.getRequestDispatcher("/" +AppUtil.getPackageName(this)+"/"+new ShptBinRequest().getClass().getSimpleName()).forward(request, response);
			return;

		}


		String bc = new ArticleController().getBcByArticleId(orderPos.getArticleId());
		if(bc != null){
			session.setAttribute("barcode", bc);
			session.setAttribute("expectedqty", orderPos.getExpectQuantity().longValue()-(orderPos.getFactQuantity()==null?0L:orderPos.getFactQuantity().longValue()));
			session.setAttribute("orderposid", orderPos.getOrderPosId());
			session.setAttribute("orderid", orderPos.getOrderId());
		}

		if(!session.getAttribute("barcode").toString().isEmpty())
		{
			if(!new ArticleController().checkArticleBc(session.getAttribute("barcode"), "article.jsp", request, response))
			{
				session.setAttribute("barcode", "");
				return;
			}


			article = new ArticleController().getArticleByBarcode(session.getAttribute("barcode").toString());
			if(!(article instanceof WmArticle)){
				session.setAttribute("barcode", null);
				session.setAttribute("message", new ArticleController().getErrorText(ru.defo.util.Bc.symbols(request.getParameter("scanbc").trim())));
				session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
				request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
				return;
			}

			if(!(new OrderController().getOrderPosByOrderArticleQState(session.getAttribute("ordercode").toString(), article.getArticleId(), 1L) instanceof WmOrderPos))
			{
				session.setAttribute("barcode", null);
				session.setAttribute("message", ErrorMessage.ORDER_WRONG_UNPICKED_ARTICLE.message(new ArrayList<String>(Arrays.asList(session.getAttribute("ordercode").toString(), article.getArticleCode()))));
				session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
				request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
				return;
			}
		}


		if(session.getAttribute("barcode") == null ||session.getAttribute("barcode").toString().isEmpty()){
			session.setAttribute("actionpage", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("backpage", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+new DestUnit().getClass().getSimpleName());
			request.getRequestDispatcher("article.jsp").forward(request, response);
			return;
		}

		session.setAttribute("articlename", article.getDescription());
		session.setAttribute("articlecode", article.getArticleCode());
		session.setAttribute("articleid", article.getArticleId());

		int qty = orderPos.getExpectQuantity().intValue()-(orderPos.getFactQuantity()==null?0:orderPos.getFactQuantity().intValue());
		new JobManager().setJob(user, jobType, order, article, qty);

		request.getRequestDispatcher("/" +AppUtil.getPackageName(this)+"/"+new BinRequest().getClass().getSimpleName()).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

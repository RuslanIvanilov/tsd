package ru.defo.servlets.pick;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.OrderController;
import ru.defo.controllers.PickController;
import ru.defo.controllers.QuantController;
import ru.defo.controllers.UnitController;
import ru.defo.managers.OrderPosManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmOrderPos;
import ru.defo.model.WmPick;
import ru.defo.model.WmUnit;
import ru.defo.util.AppUtil;
import ru.defo.util.ErrorMessage;
import ru.defo.util.HibernateUtil;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/pick/ArticleRequest")
public class ArticleRequest extends HttpServlet {
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

		Long orderId = Long.valueOf(session.getAttribute("orderid").toString());
		Long orderPosId = Long.valueOf(session.getAttribute("orderposid").toString());


		if(session.getAttribute("barcode")==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		System.out.println("inputfield : "+request.getParameter("inputfield"));
		if(request.getParameter("inputfield") != null && !session.getAttribute("srcunitcode").toString().equals(request.getParameter("inputfield"))
				//&& !session.getAttribute("articlecode").toString().equals(ru.defo.util.Bc.symbols(request.getParameter("inputfield").trim()))
				)
		{
			WmArticle article = null;
			if(request.getParameter("inputfield").trim().length()>3)
				article = new ArticleController().getArticleByBarcode(ru.defo.util.Bc.symbols(request.getParameter("inputfield").trim()));
			request.getParameter("inputfield").replaceAll("\\w|\\d", "");

			if(article instanceof WmArticle){
				if(article.getArticleId().longValue()!= Long.valueOf(session.getAttribute("articleid").toString()))
				{
					String needArticleTxt = session.getAttribute("articlecode").toString()+" "+session.getAttribute("articlename").toString();
					String scanedArticleTxt = article.getArticleCode()+" "+article.getDescription();

					session.setAttribute("message", ErrorMessage.WRONG_ARTICLE.message(new ArrayList<String>(Arrays.asList(needArticleTxt,  scanedArticleTxt))));
					session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
					request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
					return;
				}

				/* Проверка что не лишняя коробка */
				Long pickedQty = 0L;
				WmOrderPos orderPos = new OrderController().getOrderPosByOrderArticleQState(session.getAttribute("ordercode").toString(), article.getArticleId(), 1L);

				List<WmPick> pickList = new PickController().getPickListByOrderPosUnit(orderPos, null);
				for(WmPick pick : pickList){
					pickedQty += pick.getQuantity();
				}

				if(orderPos.getExpectQuantity().longValue() < pickedQty.longValue()){
					session.setAttribute("message", ErrorMessage.EXTRA_SKU.message(new ArrayList<String>(Arrays.asList(session.getAttribute("ordercode").toString(), orderPos.getExpectQuantity().toString(), article.getArticleCode()))));
					session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
					request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
					return;
				} else {
					/* Коробка не лишняя */
					/* Сохранение в БД */
					WmUnit srcUnit = new UnitController().getUnitByUnitCode(session.getAttribute("srcunitcode"));
					WmUnit destUnit = new UnitController().getUnitByUnitCode(session.getAttribute("unitcode"));
					Long userId = Long.valueOf(session.getAttribute("userid").toString());
					if(request.getParameter("inputfield") !=null) request.getParameter("inputfield").replaceAll("\\w|\\d", "");
					new PickController().addPickSKU(orderPos, srcUnit.getUnitId(), destUnit.getUnitId(), userId);
					if(new OrderController().isOrderPicked(orderPos.getOrderId())){
						//if(
						 	Session sessionHbr = HibernateUtil.getSession();
						 	Transaction tr = sessionHbr.getTransaction();
						 	try{
						 		tr.begin();
						 		new OrderController().setPickedOrder(orderPos.getOrderId(), sessionHbr);
								tr.commit();
						 	}catch(Exception e){
						 		tr.rollback();
								//sessionHbr.close();
						 		e.printStackTrace();
						 	}finally{
						 		if(sessionHbr.isOpen()) sessionHbr.close();
						 	}
						//   )
						//	new OrderController().setPickedPreOrder(orderPos.getOrderId(), session);  Не успел доработать!
					}
				}



			}
		}






			WmOrderPos orderPos = new OrderPosManager().getOrderByPosId(orderId, orderPosId);

			/* Все подобрано */
			if((orderPos.getExpectQuantity().longValue()-(orderPos.getFactQuantity()==null?0L:orderPos.getFactQuantity().longValue()))==0L ||
					new QuantController().isEmptyPickPlace(session.getAttribute("articlecode").toString(), session.getAttribute("srcunitcode").toString())
			   )
			{
				request.getRequestDispatcher("/" +AppUtil.getPackageName(this)+"/"+new PickInfo().getClass().getSimpleName()).forward(request, response);
				return;
			}


			session.setAttribute("SourceClassName",this.getClass().getSimpleName());
			session.setAttribute("Title","TSD Подбор товар сканирование");
			session.setAttribute("SubmitAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("HeaderCaption", "");
			session.setAttribute("TopText","Подбор <b>"+session.getAttribute("articlecode")+"</b><br><small>Всего: <font color='BLUE'><b>"+orderPos.getExpectQuantity()+"</b></font> Остаток: <font color='GREEN'><b>"+(orderPos.getExpectQuantity().longValue()-(orderPos.getFactQuantity()==null?0L:orderPos.getFactQuantity().longValue()))+"</b></font><br>Подобрано на <b>"+session.getAttribute("unitcode")+"</b>: "+(orderPos.getFactQuantity()==null?0:orderPos.getFactQuantity())+"<br>"+session.getAttribute("articlename")+"</small>");
			session.setAttribute("CenterText","Сканируйте штрих-код <b><u>товара</u></b>");

			session.setAttribute("CancelName","Назад");
			session.setAttribute("CancelAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new BinRequest().getClass().getSimpleName());
			session.setAttribute("Btn2Name","Меню");
			session.setAttribute("Btn2Action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new CancelMenu().getClass().getSimpleName());
			request.getRequestDispatcher("/form_templates/request_text.jsp").forward(request, response);

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

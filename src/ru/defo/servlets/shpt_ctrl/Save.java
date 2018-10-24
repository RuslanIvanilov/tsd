package ru.defo.servlets.shpt_ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap; 
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.AuthorizationController;
import ru.defo.controllers.CheckController;
import ru.defo.controllers.HistoryController;
import ru.defo.controllers.OrderController;
import ru.defo.controllers.QuantController;
import ru.defo.controllers.UnitController;
import ru.defo.managers.CheckManager;
import ru.defo.managers.OrderManager; 
import ru.defo.model.WmArticle;
import ru.defo.model.WmCheck;
import ru.defo.model.WmOrder;
import ru.defo.model.WmOrderPos; 
import ru.defo.model.WmQuant;
import ru.defo.model.WmUnit;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;
import ru.defo.util.HibernateUtil;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/shpt_ctrl/Save")
public class Save extends HttpServlet {
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
		WmQuant quant = null;
		Session sessinH = HibernateUtil.getSession();
		try{

			if(session.getAttribute("userid")==null) {
				new AuthorizationController().errorMessage(request, response);
				return;
			}


			sessinH.getTransaction().begin();

			System.out.println(this.getClass().getSimpleName());

			if(session.getAttribute("unitcode")==null){
				System.out.println("ВНИМАНИЕ!!! нулевой поддон у сотрудника "+session.getAttribute("userid"));

				session.setAttribute("message", ErrorMessage.FAKE_UNIT.message(new ArrayList<String>(Arrays.asList("NULL"))));
				session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(new Order())+"/"+new Order().getClass().getSimpleName());
				request.getRequestDispatcher("errorn.jsp").forward(request, response);
				return;
			}
			WmUnit unit = new UnitController().getUnitByUnitCode(session.getAttribute("unitcode"));

			@SuppressWarnings("unchecked")
			HashMap<String, String> mapScan = (HashMap<String, String>) session.getAttribute("scanmap");

			@SuppressWarnings("unchecked")
			HashMap<String, String> mapResult = (HashMap<String, String>) session.getAttribute("finalmap");
			WmOrderPos orderPos  = null;

			for(Map.Entry<String, String> entry : mapResult.entrySet()){
				WmArticle article = artCtrl.getArticleByCode(entry.getKey());
				quant = new QuantController().getQuantByUnitArticle(unit, article);

				if(quant == null){
					//WmArticle article = artCtrl.getArticleByCode(entry.getKey());
					quant = new WmQuant();
					quant.setArticleId(article.getArticleId());
					quant.setSkuId(artCtrl.getBaseSkuByArticleId(article.getArticleId()).getSkuId());
					quant.setQualityStateId(1L);
					quant.setQuantity(0L);
				}

				quantQty = (quant.getQuantity()==null ? 0 : quant.getQuantity());
				quantInfo = " " +DefaultValue.QUANT_QTY+" пользователем "+quant.getCreateUser()+" : "+quantQty;

				String scanedQtyStr = mapScan.get(entry.getKey());

				Long balanceQty = Long.decode(scanedQtyStr==null?"0":scanedQtyStr);//-quantQty;
				diffText = "."+DefaultValue.DIFF_TEXT+" ["+(balanceQty-quantQty)+"]"+ quantInfo;

				String comment = DefaultValue.CONTROL+diffText;

				/**
				 * @comment: save to Wm_Pick & Wm_OrderPos
				 * {
				 * */
				WmCheck check = null;

				if(!scanedQtyStr.isEmpty()){
					Long unitId = unit.getUnitId();
					Long userId = Long.decode(session.getAttribute("userid").toString());
					//WmPick pick = null;

					orderPos  =  new OrderController().getOrderPosByOrderArticleQState(session.getAttribute("ordercode").toString(), quant.getArticleId(), quant.getQualityStateId());

		        	if(orderPos == null){
		        		System.out.println("ERROR: Нет такой строки!");
		        	} else {
		        		check = new CheckManager().fillCheck(unitId, quant.getArticleId(), quant.getSkuId(), Long.decode(scanedQtyStr), orderPos.getOrderId(), orderPos.getOrderPosId(), userId);
		        		new CheckManager().addOrUpdateCheck(sessinH, check);
		        		orderPos.setCtrlQuantity((orderPos.getCtrlQuantity()==null?0:orderPos.getCtrlQuantity())+Long.decode(scanedQtyStr));
		        		if(orderPos.getExpectQuantity().longValue()==orderPos.getCtrlQuantity().longValue())
		        			orderPos.setStatusId(DefaultValue.STATUS_CONTROL_FINISHED);

		        		sessinH.update(orderPos);
		        	}
					/**}
					 * */
				}

				if(check instanceof WmCheck)
					new HistoryController().addEntryByCheck(sessinH, check, comment, Long.decode(session.getAttribute("userid").toString()));
			}

			if(new CheckManager().isFullOrderChecked(orderPos))
			{
				WmOrder order = new OrderManager().getOrderById(orderPos.getOrderId());
				new CheckController().setDocsStatus(sessinH, order, DefaultValue.STATUS_CONTROL_FINISHED);
			}

			sessinH.getTransaction().commit();
		} catch(Exception e){
			e.printStackTrace();
			sessinH.getTransaction().rollback();
			sessinH.close();
			/*System.out.println("ERROR INFO\n"+
				       "User id: "+session.getAttribute("userid")==null?"":session.getAttribute("userid").toString()+"\n"+
					   "UnitCode: "+session.getAttribute("unitcode")==null?"":session.getAttribute("unitcode").toString()+"\n"+
				       "BinCode: "+session.getAttribute("bincode")==null?"":session.getAttribute("bincode").toString()+"\n"+
					   "OrderCode: "+session.getAttribute("ordercode")==null?"":session.getAttribute("ordercode").toString()
					   );*/
			//sessinH.clear();
		} /*finally {
			sessinH.close();
		}*/

		session.setAttribute("scanmap", null);
		session.setAttribute("finalmap", null);
		request.getRequestDispatcher(new Unit().getClass().getSimpleName()).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

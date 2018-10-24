package ru.defo.servlets.pick;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.BinController;
import ru.defo.controllers.OrderController;
import ru.defo.model.WmArticle;
import ru.defo.model.WmBin;
import ru.defo.util.AppUtil;
import ru.defo.util.HibernateUtil;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/pick/BinRequest")
public class BinRequest extends HttpServlet {
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

		if(session.getAttribute("barcode")==null || session.getAttribute("articleid")==null || session.getAttribute("orderposid") == null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}


		/* Find optimal bin for picking {*/
		//Map<WmBin, Long> binMap = new HashMap<WmBin, Long>();
		Long articleId = Long.valueOf(session.getAttribute("articleid").toString());
		WmArticle article = new ArticleController().getArticle(articleId);

		WmBin selectedBin = null;

		/*
		List<VQuantInfoShort> vquantinfoList = new QuantController().getVQuantInfoListByArticle(article.getArticleCode());
		for(VQuantInfoShort quantInfo : vquantinfoList)
		{
			@SuppressWarnings("deprecation")
			WmBin bin = new BinController().getBin(quantInfo.getBinCode());
			if(bin!=null && bin.getBinTypeId() <=2L && bin.getBlockOut()==false && bin.getNeedCheck()==false){
				Long binNo = new BinController().getBinNoByBinCode(bin.getBinCode());


				long needQty = new OrderController().getPickRemainsOrderPos(session.getAttribute("orderposid").toString()).longValue();

				Long weight = bin.getLevelNo()*50+bin.getRackNo()*20+binNo+ Math.abs((quantInfo.getQuantity().longValue()-needQty)*100);
				System.out.println("BinRequest:doGet:findbin "+bin.getBinCode()+" weight: "+weight+" qty_bin: "+ quantInfo.getQuantity()+" qty_ord: "+needQty);
				binMap.put(bin, weight);
			}
		}
		*/

		Map<WmBin, Long> binMap = new BinController().getBinMapByOrderPosArticle(session.getAttribute("orderposid").toString(), article.getArticleCode());

		Map<WmBin, Long> sortedBinMap = new BinController().getSortedBinMap(binMap);

		/*
		Map<WmBin, Long> result = binMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue)->oldValue, LinkedHashMap::new));
		*/

		if(session.getAttribute("selectedbin")==null){
			for(Map.Entry<WmBin, Long> entry : sortedBinMap.entrySet()){
				if(selectedBin != null) break;
				selectedBin = entry.getKey();
				session.setAttribute("selectedbin",selectedBin.getBinCode());
				System.out.println(entry.getKey().getBinCode()+" : "+entry.getValue());
			}
		} else {
			selectedBin = new BinController().getBin(session.getAttribute("selectedbin"));

		}

		if(session.getAttribute("selectedbin")==null){
			System.out.println("BinRequest ordpos "+session.getAttribute("orderposid"));

			new OrderController().setOrderPosFinished(session, HibernateUtil.getSession());
			request.getRequestDispatcher(new ArticleSelect().getClass().getSimpleName()).forward(request, response);
			return;
		}

		/* } */

		// Check zero level BIN code
		System.out.println("inputfield ["+request.getParameter("inputfield")+"] ");

		if(request.getParameter("inputfield")!=null  // && !request.getParameter("inputfield").equals(session.getAttribute("articlecode").toString())
				){
			WmBin zeroLevelBin = new BinController().getBin(ru.defo.util.Bc.symbols(request.getParameter("inputfield").trim()));
			if(zeroLevelBin instanceof WmBin && selectedBin != null && new BinController().equalBins(selectedBin.getBinCode(), zeroLevelBin.getBinCode()))
			  {
				session.setAttribute("bincode", zeroLevelBin.getBinCode());
				session.setAttribute("zerolevelbin", zeroLevelBin.getBinCode());
			  }
			  {
				if(zeroLevelBin==null){
					session.setAttribute("message", new BinController().getErrorTextWrongBin((selectedBin==null?request.getParameter("inputfield"):selectedBin.getBinCode()), zeroLevelBin==null?request.getParameter("inputfield"):zeroLevelBin.getBinCode()));
					session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
					request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
					return;
				}
			  }
		}

		// Request zero level BIN code
		if(session.getAttribute("bincode")==null){
			session.setAttribute("SourceClassName",this.getClass().getSimpleName());
			session.setAttribute("Title","TSD Подбор запрос ячейки");
			session.setAttribute("SubmitAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("HeaderCaption", "Подбор "+session.getAttribute("articlecode")+" ");
			session.setAttribute("TopText","<b>"+session.getAttribute("expectedqty")+"</b> шт. <small>"+session.getAttribute("articlename")+"</small>");
			session.setAttribute("CenterText","Сканируйте штрих-код ячейки <br><b>"+session.getAttribute("selectedbin")+"</b>");

			session.setAttribute("CancelName","Назад");
			//session.setAttribute("CancelAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new ArticleSelect().getClass().getSimpleName());
			session.setAttribute("CancelAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new DestUnit().getClass().getSimpleName());

			session.setAttribute("Btn2Name","Ячейки");
			session.setAttribute("backpage", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
			session.setAttribute("Btn2Action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new BinList().getClass().getSimpleName());

			request.getRequestDispatcher("/form_templates/request_text.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher("BinLevelRequest").forward(request, response);
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

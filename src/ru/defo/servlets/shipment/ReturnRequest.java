package ru.defo.servlets.shipment;

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
import ru.defo.model.WmArticle;
import ru.defo.util.AppUtil;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/shipment/ReturnRequest")
public class ReturnRequest extends HttpServlet {
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

		session.setAttribute("skubc", AppUtil.getReqSessionAttribute(request, session, "skubc", "", true));
		session.setAttribute("tosave", AppUtil.getReqSessionAttribute(request, session, "tosave", "", false));

		String barcode = session.getAttribute("skubc")==null?"":session.getAttribute("skubc").toString();
		int toSave = session.getAttribute("tosave").toString().isEmpty()?0:1;

		if(toSave==0 && barcode.equals("")){
			session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("backpage", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+Quantity.class.getSimpleName());
			System.out.println("/" +AppUtil.getPackageName(this)+"/"+"return.jsp");
			request.getRequestDispatcher( "/" +AppUtil.getPackageName(this)+"/"+"return.jsp").forward(request, response);
			return;
		}

		/*Проверки*/
		WmArticle article = new ArticleController().getArticleByBarcode(barcode);
		if(!(article instanceof WmArticle)){
			session.setAttribute("message", ErrorMessage.BC_NOT_SKU.message(new ArrayList<String>(Arrays.asList(barcode))));
			session.setAttribute("action", request.getContextPath() + "/" +AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("skubc", null);
			request.getRequestDispatcher("/errorn.jsp").forward(request, response);
			return;
		}

		/*Добавление в массив возвращаемого*/

		session.setAttribute("skubc",null);
		session.setAttribute("tosave",null);

		/*Хватит*/




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

package ru.defo.servlets;

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
import ru.defo.controllers.BarcodeController;
import ru.defo.controllers.SessionController;
import ru.defo.controllers.TsdPermissionController;
import ru.defo.model.WmArticle;
import ru.defo.util.BarcodeType;
import ru.defo.util.ConfirmMessage;
import ru.defo.util.DefaultValue;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class AssignStart
 */
@WebServlet("/AssignStart")
public class AssignStart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/AssignStart");
		new SessionController().initAll(session);

		if(session.getAttribute("userid")==null) {
			request.getRequestDispatcher("Login").forward(request, response);
			return;
		}

		int userId = Integer.decode(session.getAttribute("userid").toString());
		TsdPermissionController permissinCtrl = new TsdPermissionController(userId);

		if(request.getParameter("scanedtext") == null){
			session.setAttribute("backpage", "Mmenu");
			request.getRequestDispatcher("assign_start.jsp").forward(request, response);
			return;
		}

		/* Проверка*/
		BarcodeController bcCtrl = new BarcodeController();
		String scanedtext = ru.defo.util.Bc.symbols(request.getParameter("scanedtext"));

		BarcodeType bcType = bcCtrl.getBarcodeType(scanedtext);
		WmArticle article = new ArticleController().getArticleByBarcode(scanedtext);

		boolean hasPermission = permissinCtrl.hasPermission(Long.decode(session.getAttribute("userid").toString()), permissinCtrl.getPermissionId(DefaultValue.ASSIGN_DEL_BARCODE));

		if(bcType != BarcodeType.UNKNOWN){
			if(!hasPermission){
				session.setAttribute("message", ErrorMessage.BC_UNABLE_AS_SKU.message(new ArrayList<String>(Arrays.asList(scanedtext, bcType.typeName()))));
				session.setAttribute("action", this.getClass().getSimpleName());
				request.getRequestDispatcher("errorn.jsp").forward(request, response);
				return;
			} else{
				session.setAttribute("barcode", scanedtext);
				session.setAttribute("question", ConfirmMessage.ASK_DELETE_BARCODE_SKU
						.message(new ArrayList<String>(Arrays.asList(scanedtext, article.getArticleCode()))));
				session.setAttribute("action_page", "AssignDelete");
				session.setAttribute("back_page", "AssignStart");
				request.getRequestDispatcher("confirmn.jsp").forward(request, response);
				return;
			}
		}

		session.setAttribute("barcode", scanedtext);
		request.getRequestDispatcher("AssignFind").forward(request, response);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

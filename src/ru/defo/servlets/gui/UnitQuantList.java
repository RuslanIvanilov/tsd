package ru.defo.servlets.gui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.model.WmArticle;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;


/**
 * Servlet implementation class ArticleQtyList
 */
@WebServlet("/gui/UnitQuantList")
public class UnitQuantList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		//new SessionController().initUnitQuantList(session);
		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("articleid", AppUtil.getReqSessionAttribute(request, session, "articleid", "", false));
		session.setAttribute("articlename", AppUtil.getReqSessionAttribute(request, session, "articlename", "", false));
		session.setAttribute("unitflt", AppUtil.getReqSessionAttribute(request, session, "unitflt", "", false));
		session.setAttribute("binflt", AppUtil.getReqSessionAttribute(request, session, "binflt", "", false));
		session.setAttribute("areaflt", AppUtil.getReqSessionAttribute(request, session, "areaflt", "", false));
		session.setAttribute("rackflt", AppUtil.getReqSessionAttribute(request, session, "rackflt", "", false));
		session.setAttribute("levelflt", AppUtil.getReqSessionAttribute(request, session, "levelflt", "", false));
		session.setAttribute("qtyflt", AppUtil.getReqSessionAttribute(request, session, "qtyflt", "",false));
		session.setAttribute("skuflt", AppUtil.getReqSessionAttribute(request, session, "skuflt", "", false));
		session.setAttribute("qstateflt", AppUtil.getReqSessionAttribute(request, session, "qstateflt", "", false));
		session.setAttribute("needchkflt", AppUtil.getReqSessionAttribute(request, session, "needchkflt", "", false));
		session.setAttribute("crdateflt", AppUtil.getReqSessionAttribute(request, session, "crdateflt", "", false));
		session.setAttribute("surnameflt", AppUtil.getReqSessionAttribute(request, session, "surnameflt", "", false));
		session.setAttribute("fstnameflt", AppUtil.getReqSessionAttribute(request, session, "fstnameflt", "", false));
		session.setAttribute("advflt", AppUtil.getReqSessionAttribute(request, session, "advflt", "", false));
		session.setAttribute("advposflt", AppUtil.getReqSessionAttribute(request, session, "advposflt", "", false));
		session.setAttribute("expdateflt", AppUtil.getReqSessionAttribute(request, session, "expdateflt", "", false));
		session.setAttribute("groupflt", AppUtil.getReqSessionAttribute(request, session, "groupflt", "", false));

		session.setAttribute("articleflt", AppUtil.getReqSessionAttribute(request, session, "articleflt", "", false));
		session.setAttribute("artnameflt", AppUtil.getReqSessionAttribute(request, session, "artnameflt", "", false));

		session.setAttribute("rowcount", AppUtil.getReqSessionAttribute(request, session, "rowcount", String.valueOf(DefaultValue.TABLE_ROW_COUNT), false));

		if(session.getAttribute("articleid")!= null && session.getAttribute("articleid")!="" && !session.getAttribute("articleid").toString().equals("undefined")){
			Long articleId = Long.decode(session.getAttribute("articleid").toString());
			WmArticle article = new ArticleController().getArticle(articleId);
			session.setAttribute("articlecode", article.getArticleCode());
			session.setAttribute("articlename", article.getDescription());


			if(article.getArticleKitId() != null){
				WmArticle articleParent = new ArticleController().getArticle(article.getArticleKitId());
				if(articleParent instanceof WmArticle)
					session.setAttribute("articleparentcode", articleParent.getArticleCode());
			}
		} else {
			session.setAttribute("articleid", null);
			session.setAttribute("articleparentcode",null);
		}

		session.setAttribute("backpage",AppUtil.getPackageName(this)+"/"+ ArticleQtyList.class.getSimpleName());
		request.getRequestDispatcher("/gui/unit_quant_list.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

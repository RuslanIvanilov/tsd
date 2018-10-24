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

import ru.defo.controllers.UnitController;
import ru.defo.model.WmUnit;
import ru.defo.util.AppUtil;
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/pick/SrcUnitRequest")
public class SrcUnitRequest extends HttpServlet {
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
		session.setAttribute("leveledbin", null);
		WmUnit expectedUnit = new UnitController().getUnitFromBincodeArticleCode(session.getAttribute("selectedbin"), session.getAttribute("articlecode"));

		if(session.getAttribute("barcode")==null || expectedUnit == null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}


		// Check zero level BIN code

		if(request.getParameter("inputfield")!=null && request.getParameter("inputfield").length()>1)
		{
			String errorText = new UnitController().getErrorText(ru.defo.util.Bc.symbols(request.getParameter("inputfield").trim()));
			if(errorText != null)
			{
				session.setAttribute("message", errorText);
				session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
				request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
				return;
			}

			if(!expectedUnit.getUnitCode().equalsIgnoreCase(ru.defo.util.Bc.symbols(request.getParameter("inputfield").trim())))
			{
				session.setAttribute("message", ErrorMessage.UNIT_NOT_FROM_JOB.message(new ArrayList<String>(Arrays.asList(request.getParameter("inputfield"), expectedUnit.getUnitCode()))));
				session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
				request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
				return;
			}

			session.setAttribute("srcunitcode", ru.defo.util.Bc.symbols(request.getParameter("inputfield").trim()));
			request.getParameter("inputfield").replaceAll("\\w|\\d", "");
		}


		// Request source unitCode
		if(session.getAttribute("srcunitcode")==null){

			session.setAttribute("expunitcode", expectedUnit.getUnitCode());

			session.setAttribute("SourceClassName",this.getClass().getSimpleName());
			session.setAttribute("Title","TSD Подбор поддон-источник");
			session.setAttribute("SubmitAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("HeaderCaption", "Подбор "+session.getAttribute("articlecode")+" ");
			session.setAttribute("TopText","<small>"+session.getAttribute("articlename")+"</small>");
			session.setAttribute("CenterText","Сканируйте штрих-код <u>поддона</u> <b>"+expectedUnit.getUnitCode()+"</b>"//+" в ячейке <b>"+session.getAttribute("selectedbin")+"</b>"
			);

			session.setAttribute("CancelName","Назад");
			session.setAttribute("CancelAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new BinRequest().getClass().getSimpleName());
			session.setAttribute("Btn2Name","Отмена");
			session.setAttribute("Btn2Action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new BinChangeRequest().getClass().getSimpleName());
			request.getRequestDispatcher("/form_templates/request_text.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher("ArticleRequest").forward(request, response);
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

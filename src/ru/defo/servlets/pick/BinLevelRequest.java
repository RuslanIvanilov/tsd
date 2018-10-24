package ru.defo.servlets.pick;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BinController;
import ru.defo.util.AppUtil;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/pick/BinLevelRequest")
public class BinLevelRequest extends HttpServlet {
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
		session.setAttribute("bincode",null);

		if(session.getAttribute("barcode")==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}


		if(request.getParameter("inputfield")!=null && request.getParameter("inputfield").length()<=2){
			System.out.println("LEVEL: "+request.getParameter("inputfield"));

			String leveledBinCode = new BinController().getLeveledBin(session.getAttribute("zerolevelbin").toString(), request.getParameter("inputfield"));
			if(leveledBinCode != null && leveledBinCode.equals(session.getAttribute("selectedbin").toString())){
				session.setAttribute("leveledbin", leveledBinCode);
			}else{
				session.setAttribute("message", new BinController().getErrorTextWrongLevel(session.getAttribute("selectedbin").toString(), request.getParameter("inputfield")));
				session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
				request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
				return;
			}

			request.getParameter("inputfield").replaceAll("\\w|\\d", "");

		}

		// Request level for BIN
		if(session.getAttribute("leveledbin")==null){
			session.setAttribute("SourceClassName",this.getClass().getSimpleName());
			session.setAttribute("Title","TSD Подбор запрос этажа ячейки");
			session.setAttribute("SubmitAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			//session.setAttribute("HeaderCaption", "Подбор "+session.getAttribute("articlecode")+" ");
			session.setAttribute("HeaderCaption", "Подбор ");
			//session.setAttribute("TopText","<small>"+session.getAttribute("articlename")+"</small>");
			session.setAttribute("TopText","");
			session.setAttribute("CenterText","Введите <b><u>ЭТАЖ</u></b> ячейки <br><br><b>"+session.getAttribute("selectedbin")+"</b>");
			session.setAttribute("CancelName","Назад");
			session.setAttribute("CancelAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new BinRequest().getClass().getSimpleName());
			request.getRequestDispatcher("/form_templates/request_num.jsp").forward(request, response);
			return;
		}

		request.getParameter("inputfield").replaceAll("\\w|\\d", "");
		session.setAttribute("srcunitcode",null);
		request.getRequestDispatcher("SrcUnitRequest").forward(request, response);
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

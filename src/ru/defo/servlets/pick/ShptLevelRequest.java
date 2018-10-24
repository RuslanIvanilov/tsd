package ru.defo.servlets.pick;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BinController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmBin;
import ru.defo.util.AppUtil;

/**
 * Servlet implementation class ScanDoc
 */
@WebServlet("/pick/ShptLevelRequest")
public class ShptLevelRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		//System.out.println("*** "+session.getAttribute("bincode").toString());

		if(request.getParameter("inputfield")!=null && request.getParameter("inputfield").length()<=2)
		{
			String leveledBinCode = new BinController().getLeveledBin(session.getAttribute("bincode").toString(), request.getParameter("inputfield"));

			//System.out.println("*bincode ["+session.getAttribute("bincode")+"]  leveledbin ["+session.getAttribute("leveledbin")+"] : "+leveledBinCode +" level: "+request.getParameter("inputfield"));

			WmBin bin = new BinController().getBin(leveledBinCode);

			if(bin instanceof WmBin){
				session.setAttribute("leveledbin", leveledBinCode);
			}else{
				session.setAttribute("message", new BinController().getErrorTextBinCode(leveledBinCode));
				session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
				request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);

				session.setAttribute("leveledbin", null);
				return;
			}

			request.getParameter("inputfield").replaceAll("\\w|\\d", "");

		}

		if(session.getAttribute("leveledbin")==null){
			session.setAttribute("SourceClassName",this.getClass().getSimpleName());
			session.setAttribute("Title","TSD Подбор запрос этажа ячейки");
			session.setAttribute("SubmitAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+this.getClass().getSimpleName());
			session.setAttribute("HeaderCaption", "Подбор ");
			session.setAttribute("TopText","");
			session.setAttribute("CenterText","Введите <b><u>ЭТАЖ</u></b> ячейки <br><br><b>"+session.getAttribute("bincode")+"</b>");
			session.setAttribute("CancelName","Назад");
			session.setAttribute("CancelAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+new ShptBinRequest().getClass().getSimpleName());
			request.getRequestDispatcher("/form_templates/request_num.jsp").forward(request, response);
			return;
		}
/*
		System.out.println("put unit to bin: "+session.getAttribute("leveledbin").toString());
		return;
*/
		new UnitController().setBinUnit(session.getAttribute("leveledbin"), session.getAttribute("unitcode"), "Подбор. Размещение для контроля", session.getAttribute("userid"));

		request.getRequestDispatcher("ScanDoc").forward(request, response);
		return;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

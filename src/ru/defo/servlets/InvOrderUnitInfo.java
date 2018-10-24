package ru.defo.servlets;

import java.io.IOException; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import ru.defo.controllers.QuantController; 

/**
 * Servlet implementation class InvOrderUnitInfo
 */
@WebServlet("/InvOrderUnitInfo")
public class InvOrderUnitInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("articlecount",new QuantController().getCountArticleInUnit(String.valueOf(session.getAttribute("unitcode"))));
		session.setAttribute("scancount", null);
		session.setAttribute("nextcolor",null);

		/*
		HashMap<Integer, String> quantMap = new QuantController().getArticleQtyMapByUnitObj(session.getAttribute("unitcode"));
		session.setAttribute("datalist", quantMap);

		session.setAttribute("articlecode", null);
		session.setAttribute("articleid", null);
		session.setAttribute("articlename", null);
		session.setAttribute("quantity", null);
		*/

		session.setAttribute("inputfieldname", "inputunit");
		session.setAttribute("SourceClassName",this.getClass().getSimpleName());
		session.setAttribute("Title","TSD �������������� �������");
		session.setAttribute("SubmitAction", this.getClass().getSimpleName());
		session.setAttribute("HeaderCaption", "<small>�������������� "+session.getAttribute("inventoryid").toString()+":"+session.getAttribute("inventoryposid").toString()+"</small>");
		session.setAttribute("TopText","������: <b><small>"+session.getAttribute("bincode").toString()+"</small></b>"
		       +"<br>������: <b><small>"+(session.getAttribute("unitcode")==null?"":session.getAttribute("unitcode").toString())+"</small></b>"
				);
		session.setAttribute("CenterText","����������� ���-�� ��������: <b>"+session.getAttribute("countCartons").toString()+"</b>"); //"���������: <b>"+(session.getAttribute("articlecount")==null?"":session.getAttribute("articlecount").toString())+"</b>");

		session.setAttribute("Btn1Name","�����");
		session.setAttribute("Btn1Action", new InvOrdEnterCnt().getClass().getSimpleName());

		session.setAttribute("Btn2Name","������.");
		session.setAttribute("Btn2Action", new InvOrdClrRequest().getClass().getSimpleName());

		session.setAttribute("Btn3Name","���������");
		session.setAttribute("Btn3Action", new InvOrdSkuList().getClass().getSimpleName());

		request.getRequestDispatcher("/form_templates/info_text.jsp").forward(request, response);
		return;


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

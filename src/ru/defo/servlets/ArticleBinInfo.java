package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.QuantController;
import ru.defo.model.WmUnit;
import ru.defo.model.views.VQuantInfoShort;

/**
 * Servlet implementation class UnitArticleList
 */
@WebServlet("/ArticleBinInfo")
public class ArticleBinInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/ArticleBinInfo");
		System.out.println("selectedquant : "+request.getParameter("selectedquant"));

		/* ���� ������� � ������, ����� ����� ����� �������, ������� ������� */
		WmUnit unit = new QuantController().getUnitByQuantId(Long.decode(request.getParameter("selectedquant")));

		session.setAttribute("unitcode", unit.getUnitCode());

		/* ���-�� �������� ������ � ��������� �������� */
		QuantController quantCtrl = new QuantController();

		VQuantInfoShort qtyInfo =  quantCtrl.getVQuantInfoShort(Long.decode(request.getParameter("selectedquant")));
		if(qtyInfo != null){
			session.setAttribute("qualityname",qtyInfo.getQualityName());
			session.setAttribute("quantity",qtyInfo.getQuantity());
		}

		/* ���-�� ��������� �� ������� */
		session.setAttribute("articlecount",quantCtrl.getCountArticleInUnit(String.valueOf(session.getAttribute("unitcode"))));

		request.getRequestDispatcher("article_bin_info.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

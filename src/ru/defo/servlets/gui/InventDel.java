package ru.defo.servlets.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.InventoryController;

/**
 * Servlet implementation class InventDel
 */
@WebServlet("/gui/InventDel")
public class InventDel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		InventoryController invCtrl = new InventoryController();

		if(request.getParameter("marked") != null){
			List<String> list = new ArrayList<String>(Arrays.asList(request.getParameter("marked").split(",")));
			for(String inventIdTxt : list){
				invCtrl.delInventory(inventIdTxt);
			}
		}

		request.getRequestDispatcher("InventList").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}



}

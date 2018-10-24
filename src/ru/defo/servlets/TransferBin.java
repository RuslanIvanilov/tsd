package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BinController;
import ru.defo.model.WmBin;

/**
 * Servlet implementation class TransferBin
 */
@WebServlet("/TransferBin")
public class TransferBin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		WmBin bin = null;
		BinController ctrlBin = new BinController();
		
		
		/* Проверки ячейки назначения */
		System.out.println("TransferBin.scanedtext : " + request.getParameter("scanedtext"));		

		if (session.getAttribute("bincode") == null || session.getAttribute("bincode") == "") {			
			bin = ctrlBin.getBin(ru.defo.util.Bc.symbols(request.getParameter("scanedtext")));
			if (bin == null) {
				session.setAttribute("message", "Отсканированный штрих-код [..." + request.getParameter("scanedtext")
						+ "] не определен системой как штрих-код ячейки!");
				session.setAttribute("page", "transfer_start.jsp");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}
			session.setAttribute("bincode", bin.getBinCode());
			session.setAttribute("binid", bin.getBinId());
		}
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doGet(request, response);
	}

}

package ru.defo.servlets.pick;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BinController;
import ru.defo.model.WmBin;
import ru.defo.util.AppUtil;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/pick/BinList")
public class BinList extends HttpServlet {
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
		System.out.println("bincode "+request.getParameter("bincode"));
		Long binId = null;
		session.setAttribute("bincode", AppUtil.getReqSessionAttribute(request, session, "bincode", "", true));

	    try{
		binId = Long.valueOf(session.getAttribute("bincode").toString());
		WmBin bin = new BinController().getBinById(binId);
			if(bin instanceof WmBin)
			{
				session.setAttribute("selectedbin", bin.getBinCode());
				session.setAttribute("zerolevelbin", bin.getBinCode());
				request.getRequestDispatcher("/" +AppUtil.getPackageName(this)+"/"+new BinRequest().getClass().getSimpleName()).forward(request, response);
				return;
			}
	    } catch(Exception e){
	    	session.setAttribute("bincode",null);
	    }


		System.out.println("...............................get Bin List for PICK "+request.getParameter("bincode")+"/"+request.getParameter("bincode"));

		session.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
		request.getRequestDispatcher("binlist.jsp").forward(request, response);

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

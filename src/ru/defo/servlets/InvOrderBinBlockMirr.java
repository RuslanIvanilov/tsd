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

import ru.defo.controllers.BinController; 
import ru.defo.controllers.UnitController;
import ru.defo.controllers.UserController; 
import ru.defo.model.WmBin; 
import ru.defo.model.WmUnit;
import ru.defo.model.WmUser;  
import ru.defo.util.ErrorMessage;

/**
 * Servlet implementation class InvOrderBinBlockMirr
 */
@WebServlet("/InvOrderBinBlockMirr")
public class InvOrderBinBlockMirr extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println(this.getClass().getSimpleName());

		BinController binCtrl = new BinController();

		WmUser user = new UserController().getUserById(session.getAttribute("userid"));
		if(!(user instanceof WmUser)){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		WmBin bin = binCtrl.getBin(session.getAttribute("bincode"));
		WmBin mirroredBin = binCtrl.getMirrorBin(bin);
		WmUnit unit = new UnitController().getUnitByUnitCode(session.getAttribute("unitcode"));

		if(session.getAttribute("mirrored")==null)
		{
			session.setAttribute("mirrored", "1");
			session.setAttribute("question", "Ячейку ["+mirroredBin.getBinCode()+"] отметить как зянятую поддоном ["+unit.getUnitCode()+"] ?");
			session.setAttribute("action_page",  new InvOrderBinBlockMirr().getClass().getSimpleName());
			session.setAttribute("back_page", new InvOrdBinBlock().getClass().getSimpleName());
			request.getRequestDispatcher("/confirmn.jsp").forward(request, response);
			return;
		}

		if(session.getAttribute("mirrored")!= null){
			String err = binCtrl.checkBinForReserv(mirroredBin);

			if(!(bin instanceof WmBin)){
				err = ErrorMessage.NOT_EXISTS_MIRRORED_BIN.message(new ArrayList<String>(Arrays.asList(session.getAttribute("bincode").toString())));
			}

			if(!err.isEmpty()){
				session.setAttribute("message", err);
				session.setAttribute("action", new InvOrdBinBlock().getClass().getSimpleName());
				request.getRequestDispatcher("errorn.jsp").forward(request, response);
				return;
			}

			binCtrl.setReserv(mirroredBin, unit, user);
		}

		request.getRequestDispatcher(new InvOrdBinBlock().getClass().getSimpleName()).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

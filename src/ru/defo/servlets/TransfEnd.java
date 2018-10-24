package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.BinController;
import ru.defo.controllers.HistoryController;
import ru.defo.controllers.PermissionController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmUnit;
import ru.defo.util.DefaultValue;

/**
 * Servlet implementation class TransfEnd
 */
@WebServlet("/TransfEnd")
public class TransfEnd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("/TransfEnd "+session.getAttribute("bincode")+" : unit "+session.getAttribute("unitcode"));

		if(session.getAttribute("userid")==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		if(session.getAttribute("saved") != null){
			session.setAttribute("saved", null);
			if(new PermissionController().hasPerm4TransUnSource(session.getAttribute("userid"))){
				request.getRequestDispatcher("TransfUnit").forward(request, response);
			} else {
				request.getRequestDispatcher(TransfFromBin.class.getSimpleName()).forward(request, response);
			}
			return;
		}


		String msg = new BinController().getErrorTextBinFull(session.getAttribute("bincode"), session.getAttribute("unitcode"));
		if(msg != null){
			session.setAttribute("message",msg);
			session.setAttribute("action", "TransfBin");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		if(!new BinController().checkBin(request, response)) return;

		WmUnit unit = new UnitController().getWmUnit(session.getAttribute("unitcode").toString());
		String unitBinCode = null;
		if(unit instanceof WmUnit && unit.getBinId() != null) unitBinCode = new BinController().getBinCode(unit.getBinId());

		if(session.getAttribute("srcbincode")!= null){
			String comment = DefaultValue.TRANSF_UNIT_FROM_BIN_TEXT+"["+session.getAttribute("srcbincode")+"]";
			if(unitBinCode != null && !unitBinCode.equals(session.getAttribute("srcbincode").toString()))
				comment = "!"+DefaultValue.TRANSF_UNIT_FROM_BIN_TEXT+" "+session.getAttribute("srcbincode")+" но числился в "+unitBinCode;

			new HistoryController().addEntry(session.getAttribute("userid"), session.getAttribute("unitcode"), session.getAttribute("srcbincode"), comment);
		}

		new UnitController().setBinUnit(session.getAttribute("bincode"), session.getAttribute("unitcode"), DefaultValue.TRANSF_UNIT_TO_BIN_TEXT, session.getAttribute("userid"));
		session.setAttribute("saved", 1);
		request.getRequestDispatcher("transf_end.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

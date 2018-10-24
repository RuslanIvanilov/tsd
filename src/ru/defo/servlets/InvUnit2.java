package ru.defo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;

import ru.defo.controllers.BinController;
import ru.defo.controllers.QuantController;
import ru.defo.controllers.UnitController;
import ru.defo.model.WmBin;
import ru.defo.model.WmQuant;
import ru.defo.model.WmUnit;
import ru.defo.model.views.VQuantInfoShort;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

/**
 * Servlet implementation class InvUnit2
 */
@WebServlet("/InvUnit2")
public class InvUnit2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");

		if(session.getAttribute("userid") == null){
			request.getRequestDispatcher("Login").forward(request, response);
			return;
		}

		if(request.getParameter("unitcode") != null){
			session.setAttribute("unitcode", ru.defo.util.Bc.symbols(request.getParameter("unitcode")));
		}

		System.out.println("/InvUnit2 unitcode : "+session.getAttribute("unitcode")+" : bincode "+session.getAttribute("bincode")+" : qty "+session.getAttribute("quantity"));

		//WmBin bin = new BinManager().getBinByBarcode(session.getAttribute("bincode")==null?null:session.getAttribute("bincode").toString());
		WmBin bin = new BinController().getBin(session.getAttribute("bincode"));
		WmUnit unit = null;

		Session sessionH  = HibernateUtil.getSession();
		Transaction trn = sessionH.getTransaction();
		try{
			trn.begin();
			unit = new UnitController().getOrCreateUnit(sessionH, session.getAttribute("unitcode"), bin==null?null:bin, session.getAttribute("userid"), DefaultValue.INVENTORY);
			trn.commit();
		}catch(TransactionException e){
			e.printStackTrace();
			trn.rollback();
			if(sessionH.isOpen()) sessionH.close();
		}

		fillData(session, unit);
		System.out.println("bincode : "+session.getAttribute("bincode"));
		if(session.getAttribute("bincode") != null)
		{
			if(new BinController().getLevelFromBinCode(String.valueOf(session.getAttribute("bincode")))==0)
			{
				session.setAttribute("bin_comment", "Транзит");
			}
		}

		//session.setAttribute("backpage", "InvStart");
		request.getRequestDispatcher("inv_unit2.jsp").forward(request, response);
		return;
	}

	@SuppressWarnings("deprecation")
	protected void fillData(HttpSession session, WmUnit unit){
		session.setAttribute("unitid", unit.getUnitId());

		if (unit.getBinId() != null) {
			session.setAttribute("bincode", new BinController().getBinCode(unit.getBinId()));
		} else {
			session.setAttribute("bincode", null);
		}

		VQuantInfoShort quantInfoShort = null;
		QuantController quantCtrl = new QuantController();

		List<WmQuant> quantList = quantCtrl.getQuantList(session.getAttribute("unitid"));
		if(quantList.size() > 1){
			session.setAttribute("quantity", quantList.size());
			session.setAttribute("articleid", null);
			session.setAttribute("articlecode", null);
			session.setAttribute("articlename", null);
		} else {
			WmQuant quant = quantCtrl.getWmQuant(unit.getUnitId());
			if (quant != null) {
				quantInfoShort = quantCtrl.getVQuantInfoShort(quant.getQuantId());
				session.setAttribute("quantid", quant.getQuantId());
			}
			if (quantInfoShort != null) {
				session.setAttribute("articlecode", quantInfoShort.getArticleCode());
				session.setAttribute("articlename", quantInfoShort.getArtName());
				session.setAttribute("quantity", quantInfoShort.getQuantity());
				session.setAttribute("skuname", quantInfoShort.getSkuName());
				session.setAttribute("qualityname", quantInfoShort.getQualityName());
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

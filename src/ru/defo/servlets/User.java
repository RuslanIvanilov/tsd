package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import ru.defo.controllers.UserController;
import ru.defo.managers.PermissionManager;
import ru.defo.model.WmUserPermission;
import ru.defo.util.AppUtil;
import ru.defo.util.HibernateUtil;

/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/User")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		Long userId = null;
		WmUserPermission userPermission = null;
		PermissionManager permMgr = new PermissionManager();

		if(session.getAttribute("userid")==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		} else {
			userId = Long.valueOf(session.getAttribute("userid").toString());
		}

		System.out.println("surnametxt "+request.getParameter("surnametxt"));

		System.out.println(this.getClass().getSimpleName());

		session.setAttribute("actionpage", (ControlProcess.class).getSimpleName());
		session.setAttribute("backpage", (UsersList.class).getSimpleName());

		session.setAttribute("userflt", AppUtil.getReqSessionAttribute(request, session, "userflt", "", false));
		session.setAttribute("save_mode", AppUtil.getReqSessionAttribute(request, session, "save_mode", "", false));

		session.setAttribute("surnametxt", AppUtil.getReqSessionAttribute(request, session, "surnametxt", "", false));
		session.setAttribute("positiontxt", AppUtil.getReqSessionAttribute(request, session, "positiontxt", "", false));
		session.setAttribute("firstnametxt", AppUtil.getReqSessionAttribute(request, session, "firstnametxt", "", false));
		session.setAttribute("logintxt", AppUtil.getReqSessionAttribute(request, session, "logintxt", "", false));
		session.setAttribute("patronymtxt", AppUtil.getReqSessionAttribute(request, session, "patronymtxt", "", false));
		session.setAttribute("birthtxt", AppUtil.getReqSessionAttribute(request, session, "birthtxt", "", false));
		session.setAttribute("blockedtxt", AppUtil.getReqSessionAttribute(request, session, "blockedtxt", "", false));
		session.setAttribute("emailtxt", AppUtil.getReqSessionAttribute(request, session, "emailtxt", "", false));
		session.setAttribute("pwdtxt", AppUtil.getReqSessionAttribute(request, session, "pwdtxt", "", false));
		session.setAttribute("barcodetxt", AppUtil.getReqSessionAttribute(request, session, "barcodetxt", "", false));

		session.setAttribute("permcodeflt", AppUtil.getReqSessionAttribute(request, session, "permcodeflt", "", false));
		session.setAttribute("desriptionflt", AppUtil.getReqSessionAttribute(request, session, "desriptionflt", "", false));
		session.setAttribute("typeflt", AppUtil.getReqSessionAttribute(request, session, "typeflt", "", false));
		session.setAttribute("createrflt", AppUtil.getReqSessionAttribute(request, session, "createrflt", "", false));
		session.setAttribute("createdateflt", AppUtil.getReqSessionAttribute(request, session, "createdateflt", "", false));


		if(!session.getAttribute("save_mode").toString().isEmpty()){
			new UserController().update(session);
		}

		Long slaveUserId = Long.valueOf(session.getAttribute("userflt").toString());
		String permStr = request.getParameter("permlist[]");
		Session sessionH = HibernateUtil.getSession();
		if(permStr != null && !permStr.contains("undefined") && !permStr.isEmpty() && slaveUserId!= null){
			String[] advList = permStr.split(",");
			sessionH.getTransaction().begin();
			for(int i=0; i<advList.length; i++)
			{
				userPermission = permMgr.getUserPermission(slaveUserId, Long.valueOf(advList[i]));
				if(userPermission instanceof WmUserPermission){
					permMgr.delUserPermission(sessionH, userPermission);
				} else {
					userPermission = permMgr.initUserPermission(slaveUserId, Long.valueOf(advList[i]), userId);
					permMgr.createUserPermission(sessionH, userPermission);
				}
			}

			sessionH.getTransaction().commit();
			//sessionH.close();
		}

		request.getRequestDispatcher("user.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

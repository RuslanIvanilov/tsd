package ru.defo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.AuthorizationController;
import ru.defo.controllers.HistoryController;
import ru.defo.controllers.SessionController;
import ru.defo.controllers.TsdPermissionController;
import ru.defo.model.WmUser;
import ru.defo.util.HibernateUtil;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.removeAttribute("scanedtext");

		System.out.println("/Login.get");
		WmUser user = new AuthorizationController().getUser(request.getParameter("scanedtext"));
		if(user == null){
		  request.getRequestDispatcher("index.jsp").forward(request, response);
		  return;
		}
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

			System.out.println("/Login.post");
			response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			HttpSession session = request.getSession(true);

			new SessionController().initAll(session);
			WmUser user = new AuthorizationController().getUser(request.getParameter("scanedtext"));


			response.setContentType("text/html;charset=utf-8");

			/**
			 * if oldbrowser != null it's OLD WebBrowser!!! in WinMobile 6.1 and older
			 * */
			System.out.println("oldbrowser : [" + request.getParameter("oldbrowser")+"]");
			if(request.getParameter("oldbrowser") != null)
				session.setAttribute("oldbrowser", Integer.decode(request.getParameter("oldbrowser")) > 0 ? 1 : null);

			if (user == null) {
				session.setAttribute("message", "Отсканированный штрих-код [" + request.getParameter("scanedtext")
				+ "] не является личным штрих-кодом сотрудника");
				session.setAttribute("page", "index.jsp");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}

			new HistoryController().addAuthorizationEntry(user.getUserId());

			TsdPermissionController tsdPermissionController = new TsdPermissionController(user.getUserId().intValue());
			if (!tsdPermissionController.hasPermissions()) {
				session.setAttribute("message", "Сотруднику: " + user.getSurname() + "<br>" + user.getFirstName() + "<br>"
						+ user.getPatronymic() + " не назначены права доступа для операций с ТСД");
				session.setAttribute("page", "index.jsp");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}

			System.out.println("userid : "+user.getUserId()+" name : "+user.getSurname()+" "+user.getFirstName()+" "+user.getPatronymic());

			session.setAttribute("modules", tsdPermissionController.getModulesList());
			session.setAttribute("userid", user.getUserId().intValue());
			//request.getRequestDispatcher("main_menu.jsp").forward(request, response);
			request.getRequestDispatcher("Mmenu").forward(request, response);




	}

}

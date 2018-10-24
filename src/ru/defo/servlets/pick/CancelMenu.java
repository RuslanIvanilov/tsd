package ru.defo.servlets.pick;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.util.AppUtil;

/**
 * Servlet implementation class Mmenu
 */
@WebServlet("/pick/CancelMenu")
public class CancelMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		System.out.println(this.getClass().getSimpleName());

		if(session.getAttribute("userid")==null) {
			request.getRequestDispatcher("Login").forward(request, response);
			return;
		}

		System.out.println("cancel_item ["+request.getParameter("cancel_item")+"]");
		if(request.getParameter("cancel_item") ==null){
			session.setAttribute("cancelAction", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ new ArticleRequest().getClass().getSimpleName());
			request.getRequestDispatcher("cancel_menu.jsp").forward(request, response);
			return;
		}


		switch(request.getParameter("cancel_item")){
		case "1":
			/*  €чейке больше нет нужного товара */
			request.getRequestDispatcher("/"+AppUtil.getPackageName(this)+"/"+new BinChangeRequest().getClass().getSimpleName()).forward(request, response);
		break;

		case "2":
			/* ѕоддон завершить подбор и вывезти в зону консолидации */
			request.getRequestDispatcher("/" +AppUtil.getPackageName(this)+"/"+new ShptBinRequest().getClass().getSimpleName()).forward(request, response);
		break;

		case "3":
			/* —писок €чеек товара дл€ подбора -€чейки из дока и с поддонами из WmPick */
			session.setAttribute("backpage", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ new ArticleRequest().getClass().getSimpleName());
			request.getRequestDispatcher("/" +AppUtil.getPackageName(this)+"/"+new BinList().getClass().getSimpleName()).forward(request, response);
		break;

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

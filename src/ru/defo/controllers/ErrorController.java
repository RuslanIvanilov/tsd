package ru.defo.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.util.AppUtil;

public class ErrorController {
 
	public void getCommitError(HttpServletRequest request, HttpServletResponse response, Object servletObj, String errorText) throws ServletException, IOException{

		HttpSession httpSession = request.getSession();

		httpSession.setAttribute("message", errorText);
		httpSession.setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(servletObj)+"/"+ servletObj.getClass().getSimpleName());
		request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
		return;
	}

}

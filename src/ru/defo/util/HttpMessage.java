package ru.defo.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpMessage {

	public HttpMessage(HttpMessageContent content, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		session.setAttribute("message", content.getMessageText());
		session.setAttribute("action", content.getActionName()); ///this.getClass().getSimpleName()
		request.getRequestDispatcher(content.jspUrl).forward(request, response);
	}

}

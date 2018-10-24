package ru.defo.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.managers.UserManager;
import ru.defo.model.WmUser;
import ru.defo.servlets.Login;

public class AuthorizationController {

	public WmUser getUser(String barCode){
		if(barCode ==null || barCode.isEmpty()) return null;
		if(barCode.charAt(0)=='D') barCode = barCode.substring(1, barCode.length());
		return new UserManager().getBarCodeUser(barCode);
	}

	public WmUser getUser(int userId){
		UserManager mgr = new UserManager();
		return mgr.getUserById((long)userId);
	}

	public String getUserBarCode(int userId){
		return getUser(userId).getUserBarcode();
	}

	public WmUser getUserByFIO(String surName, String firstName){

		return new UserManager().getUserByFIO(surName, firstName);
	}

	public List<WmUser> getUserForAdvice(){
		return new UserManager().getUserForAdvice();
	}

	public void errorMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");

		session.setAttribute("message", "Сессия прервана. Требуется повторная авторизация.");
		session.setAttribute("action",  (Login.class).getSimpleName());
		request.getRequestDispatcher("errorn.jsp").forward(request, response);
		return;

	}

}

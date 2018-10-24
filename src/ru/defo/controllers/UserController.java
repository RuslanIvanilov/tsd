package ru.defo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import ru.defo.managers.UserManager; 
import ru.defo.model.WmUser;
import ru.defo.util.HibernateUtil;

public class UserController {
	UserManager userMgr;

	public UserController() {
		userMgr = new UserManager();
	}

	public WmUser getUser(HttpSession httpSession){
		return getUserById(httpSession.getAttribute("userid"));
	}

	public List<WmUser> getUserListByUserFlt(String userFilter){
		String[] usrFltArr = userFilter.split(" ");
		List<WmUser> userList = new ArrayList<WmUser>();
		UserManager userMgr = new UserManager();

		for(String user : usrFltArr){
			List<WmUser> surList = userMgr.getUserListByUserFlt(user, "surname");
			userList.addAll(surList);
			List<WmUser> frstList = userMgr.getUserListByUserFlt(user, "firstName");
			userList.addAll(frstList);
		}

		return userList;
	}

	public WmUser getUserByUserFlt(String userFilter){
		String[] usrFltArr = userFilter.split(" ");
		List<WmUser> userList = new ArrayList<WmUser>();
		UserManager userMgr = new UserManager();

		for(String user : usrFltArr){
			List<WmUser> surList = userMgr.getUserListByUserFlt(user, "surname");
			userList.addAll(surList);

			for(WmUser user0 : userList){
				if(user0.getFirstName().contains(user))
					return user0;
			}

		}

		return null;
	}

	public String getUserListStringByUserFlt(String userFilter)
	{
		StringBuilder sb = new StringBuilder("");

		List<WmUser> userList = getUserListByUserFlt(userFilter);
		for(WmUser user : userList){
			sb.append(","+user.getUserId().toString());
		}

		return sb.toString().substring(1, sb.length());
	}

	public String getSFPbyId(Long userId){

		WmUser user = new UserManager().getUserById(userId);
		if(user==null) return "";
		return user.getSurname()+" "+user.getFirstName().charAt(0)+". "+user.getPatronymic().charAt(0)+".";
	}

	public void update(HttpSession session){

		Long birth = null;
		if(!session.getAttribute("birthtxt").toString().isEmpty()) birth = Long.valueOf(session.getAttribute("birthtxt").toString());

		boolean blocked = session.getAttribute("blockedtxt").toString().equals("true");

		//WmUser user0 = new UserManager().getByLogin(session.getAttribute("logintxt").toString());
		WmUser user0 = new UserManager().getUserById(Long.valueOf(session.getAttribute("userflt").toString()));

		if(!user0.getUserId().toString().equals(session.getAttribute("userflt").toString())) {
			session.setAttribute("userflt", user0.getUserId());
		}
		
		user0.setBlocked(blocked);
		user0.setFirstName(session.getAttribute("firstnametxt").toString());
		user0.setPatronymic(session.getAttribute("patronymtxt").toString());
		user0.setPosition(session.getAttribute("positiontxt").toString());
		user0.setSurname(session.getAttribute("surnametxt").toString());
		user0.setEmailCode(session.getAttribute("emailtxt").toString());
		user0.setPwd(String.valueOf(session.getAttribute("pwdtxt").hashCode()));
		user0.setUserBarcode(session.getAttribute("barcodetxt").toString());
		user0.setUserLogin(session.getAttribute("logintxt").toString());
		user0.setBirth(birth);

		Session hSession = HibernateUtil.getSession();
		try{
			hSession.getTransaction().begin();
			//new UserManager().createOrUpdate(hSession, user0);
			hSession.merge(user0);
			hSession.getTransaction().commit();
		} catch(Exception e){
			e.printStackTrace();
			hSession.getTransaction().rollback();
			hSession.close();
		} /*finally{
			hSession.close();
		}*/


	}

	public List<WmUser> getUsersList(String surnameFilter, String firstNameFilter, String patronymFilter,
			String positionFilter, String loginFilter, String blockFilter, String rowCount)
	{
		int rowCnt;

		try {
			rowCnt = Integer.decode(rowCount);
		} catch (Exception e) {
			e.printStackTrace();
			rowCnt = 0;
		}

		return userMgr.getUsersList(surnameFilter, firstNameFilter, patronymFilter, positionFilter, loginFilter, blockFilter,
				rowCnt);
	}


	public WmUser getUserById(Object userIdObj){
		Long userId;
		try{
			userId = Long.valueOf(userIdObj.toString());
		}catch(NumberFormatException e){
			e.printStackTrace();
			return null;
		}

		return userMgr.getUserById(userId);
	}


	public WmUser getUserById(String userIdText){

		Long userId = null;

		try{
			userId = Long.decode(userIdText);
		} catch(Exception e){
			e.printStackTrace();
		}
		WmUser user = userMgr.getUserById(userId);
		return user instanceof WmUser?user:new WmUser();
	}

	public boolean checkUserSession(Object obj, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String useridtxt;

		try{
			useridtxt = obj.toString();
		}catch(Exception e){
			useridtxt = null;
		}

		if(useridtxt instanceof String == false){
			request.getSession().setAttribute("message", "* ѕринудительное завершение сессии, нужно отсканировать повторно свой штрих-код!");
			request.getSession().setAttribute("page", "index.jsp");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return false;
		}

		return true;
	}

}

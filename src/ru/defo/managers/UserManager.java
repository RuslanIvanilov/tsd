package ru.defo.managers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria; 
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.controllers.HistoryController;
import ru.defo.filters.CriterionFilter; 
import ru.defo.model.WmPermission;
import ru.defo.model.WmUser;
import ru.defo.model.WmUserPermission;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

public class UserManager extends ManagerTemplate
{
/*
	public UserManager() {
		super(WmUser.class);
	}
*/
	public long getNextUserId(){
		return ((BigInteger) HibernateUtil.getSession().createSQLQuery("select nextval('seq_user_id')").uniqueResult()).longValue();
	}

	public void createOrUpdate(Session session, WmUser user) { session.merge(user); }

	public WmUser getByLogin(String user_login)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("userLogin", user_login));
		WmUser user = (WmUser)HibernateUtil.getUniqObject(WmUser.class, restList, true);
		if(!(user instanceof WmUser)) return null;

		return user;
	}

	public List<WmUser> getAll(boolean activityOnly){
		CriterionFilter flt = new CriterionFilter();
		if(activityOnly) flt.addFilter("blocked", "false", "bool");
		List<WmUser> userList = HibernateUtil.getObjectList(WmUser.class, flt.getFilterList(), 0, false, "surname");

		return userList;
	}

	public List<WmUser> getUserListByUserFlt(String userFlt, String fieldName){
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter(fieldName, userFlt, "like");
		List<WmUser> userList = HibernateUtil.getObjectList(WmUser.class, flt.getFilterList(), 0, false, "userId");

		return userList;
	}


	public WmUser getBarCodeUser(String barCode)
	{
		/*
		Criteria userCreteria = session.createCriteria(WmUser.class);
		userCreteria.add(Restrictions.eq("userBarcode", barCode));
		userCreteria.add(Restrictions.eq("blocked", false));
		return  (WmUser)userCreteria.uniqueResult();
		*/

		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("userBarcode", barCode));
		restList.add(Restrictions.eq("blocked", false));
		WmUser user = (WmUser)HibernateUtil.getUniqObject(WmUser.class, restList, false);
		return user;

	}

	public WmUser getUserById(Long userId) {
		WmUser user = null;
		/*
		Session session = HibernateUtil.getSession();
		try{
		user = (WmUser) session.createQuery("from WmUser where user_id = :userid")
				.setParameter("userid", userId).uniqueResult();
		}catch(HibernateException e){
			e.printStackTrace();
			if(session.isOpen()) session.close();
		}*/
		/*finally{
			if(session.isOpen()) session.close();
		}*/
		//session.close();
		//session.refresh(user);

		if(userId == -1) {
			user= new WmUser();
			user.setUserId(getNextUserId());
			user.setUserLogin("");
			user.setSurname("");
			user.setFirstName("");
			user.setPatronymic("");
			user.setEmailCode("");
			user.setBlocked(false);
			user.setPwd("");			
		} else {			
			List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
			restList.add(Restrictions.eq("userId", userId));
			user =  (WmUser)HibernateUtil.getUniqObject(WmUser.class, restList, false);
			if(!(user instanceof WmUser)) return null;
		}

		return user;
	}

	@Deprecated
	@SuppressWarnings("unchecked")
	public List<Long> getListModuls(int userId) {
		List<Long> modules = new ArrayList<Long>();
		try {
			modules = (List<Long>) session.getNamedQuery("getUserModules").setInteger("userId", userId).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//session.close();
		}

		return modules;
	}


	@SuppressWarnings("unchecked")
	public List<WmUserPermission> getUserPermissionList(Session session, Long userId){
		Session sessionH = HibernateUtil.getSession();
		List<WmUserPermission> permissionList = null;
		Criteria criteria = sessionH.createCriteria(WmUserPermission.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.addOrder(Order.asc("permissionId"));

		permissionList = (List<WmUserPermission>)criteria.list();

		for(WmUserPermission permission : permissionList){
			sessionH.refresh(permission);
		}

		return permissionList;
	}

	public WmPermission getPermission(Session session, Long permissionId){
		Criteria criteria = session.createCriteria(WmPermission.class);
		criteria.add(Restrictions.eq("permissionId", permissionId));
		WmPermission permission = (WmPermission)criteria.uniqueResult();

		if(permission instanceof WmPermission){
			return permission;
		}
		return null;
	}


	public List<Long> getListModules(Session session, Long userId)
	{
		List<Long> modules = new ArrayList<Long>();
		WmPermission permission = null;
		List<WmUserPermission> permissionList = getUserPermissionList(session, userId);
		if(permissionList.isEmpty()) return null;

		for(WmUserPermission userPerm : permissionList){
			permission = getPermission(session, userPerm.getPermissionId());
			if(permission.getGroupCode()==null?false:permission.getGroupCode().equals("TSD"))
				modules.add(permission.getPermissionId());
		}

		return modules;
	}


	public Long getUserId(int userId){
		Long userIdL = Long.valueOf(String.valueOf(userId));
		return userIdL;
	}


	public WmUser getUserByFIO(String surname, String firstName){

		return (WmUser) session.createQuery("from WmUser where surname = :surname and firstName = :firstName")
				.setParameter("surname", surname).setParameter("firstName", firstName).uniqueResult();

	}


	public void setUser(WmUser user){
		String historyString = DefaultValue.PRINT_USER_BC+" ["+user.getUserBarcode() +"] : "+ user.getUserId()+" : "+user.getSurname();

		Session session = HibernateUtil.getSession();
		Transaction trn = session .getTransaction();
		trn.begin();
		new HistoryController().addBcPrintEntry(session, user.getUserId(), historyString);
		session.saveOrUpdate(user);
		trn.commit();
	}

	@SuppressWarnings("unchecked")
	public List<WmUser> getUserForAdvice()
	{
		String[] positions = {"Кладовщик", "Контролер"};

		Criteria userCreteria = session.createCriteria(WmUser.class);
		userCreteria.add(Restrictions.in("position", positions));
		return (List<WmUser>)userCreteria.list();

	}

	@SuppressWarnings("unchecked")
	public List<WmUser> getUsersList(String surnameFilter, String firstNameFilter, String patronymFilter, String positionFilter,
			 String loginFilter, String blockFilter, int rowCount)
	{
		Criteria criteria = session.createCriteria((WmUser.class).getName());

		try {
			setFilter(criteria, "like", "surname", surnameFilter);
			setFilter(criteria, "like","firstName", firstNameFilter);
			setFilter(criteria, "like","patronymic", patronymFilter);
			setFilter(criteria, "like","position", positionFilter);
			setFilter(criteria, "like","userLogin", loginFilter);
			setFilter(criteria, "bl","blocked", blockFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if(rowCount > 0)
			criteria.setMaxResults(rowCount);

		criteria.addOrder(Order.asc("surname"));

		List<WmUser> userList = (List<WmUser>) criteria.list();

		for(WmUser user : userList){
			session.refresh(user);
		}

		return userList;
	}


}

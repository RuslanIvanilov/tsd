package ru.defo.managers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.model.WmPermission;
import ru.defo.model.WmUserPermission;
import ru.defo.util.HibernateUtil;

public class PermissionManager extends ManagerTemplate {

	public PermissionManager(){ super(); }

	public WmPermission getPermission(Long permissinId){
		Criteria criteria = HibernateUtil.getSession().createCriteria(WmPermission.class);
		criteria.add(Restrictions.eq("permissionId", permissinId));
		criteria.setMaxResults(1);
		return (WmPermission) criteria.uniqueResult();
	}

	public boolean hasPermission(Long userId, Long permissionId)
	{
		Criteria criteria = HibernateUtil.getSession().createCriteria(WmUserPermission.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("permissionId", permissionId));
		criteria.setMaxResults(1);
		WmUserPermission userPermissin = (WmUserPermission) criteria.uniqueResult();
		return (userPermissin instanceof WmUserPermission);
	}

	public WmUserPermission getUserPermission(Long userId, Long permissionId){

		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("userId", userId));
		restList.add(Restrictions.eq("permissionId", permissionId));

		WmUserPermission userPerm = (WmUserPermission)HibernateUtil.getUniqObject(WmUserPermission.class, restList, false);
		if(!(userPerm instanceof WmUserPermission)) return null;

		return userPerm;
	}

	public void delUserPermission(Session session, WmUserPermission userPermission){
		session.delete(userPermission);
	}

	public void createUserPermission(Session session, WmUserPermission userPermission){
		session.persist(userPermission);
	}

	public WmUserPermission initUserPermission(Long userId, Long permissionId, Long createdUserId){
		WmUserPermission userPerm = new WmUserPermission();
		userPerm.setPermissionId(permissionId);
		userPerm.setUserId(userId);
		userPerm.setCreateUser(createdUserId);
		userPerm.setCreateDate(new Timestamp(System.currentTimeMillis()));

		return userPerm;
	}

	public Long getPermissionId(String permission_code)
	{
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(WmPermission.class);
		criteria.setLockMode(LockMode.NONE);
		criteria.add(Restrictions.eq("permissionCode", permission_code));
		criteria.setMaxResults(1);
		WmPermission permission = (WmPermission) criteria.uniqueResult();
		//session.close();

		return permission.getPermissionId();
	}

	public boolean hasPermission(Long userId, String permissionCode)
	{
		Long permissionId = getPermissionId(permissionCode);
		return hasPermission(userId, permissionId);
	}

}

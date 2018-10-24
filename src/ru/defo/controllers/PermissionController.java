package ru.defo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.filters.CriterionFilter;
import ru.defo.managers.PermissionManager;
import ru.defo.model.WmPermission;
import ru.defo.model.WmUserPermission;
import ru.defo.util.HibernateUtil;

public class PermissionController {

	public WmUserPermission getUserPermission(Object userIdObj, Object permissionIdObj)
	{
		Long userId = Long.valueOf(userIdObj.toString());
		Long permissionId = Long.valueOf(permissionIdObj.toString());

		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		if(userId != null) restList.add(Restrictions.eq("userId", userId));
		if(permissionId != null) restList.add(Restrictions.eq("permissionId", permissionId));

		WmUserPermission userPerm = (WmUserPermission)HibernateUtil.getUniqObject(WmUserPermission.class, restList, false);
		if(!(userPerm instanceof WmUserPermission)) return null;

		return userPerm;
	}

	public List<WmPermission> getAll()
	{
		CriterionFilter flt = new CriterionFilter();
		List<WmPermission> perList = HibernateUtil.getObjectList(WmPermission.class, flt.getFilterList(), 0, false,"permissionId");

		return perList;
	}

	public boolean hasPermissionByCode(String userIdtxt, String PermissionCode) {
		Long userId = Long.decode(userIdtxt);
		return new PermissionManager().hasPermission(userId, PermissionCode);
	}

	public boolean hasPermissionByCode(Object userIdObj, String PermissionCode) {
		Long userId = Long.decode(userIdObj.toString());
		return new PermissionManager().hasPermission(userId, PermissionCode);
	}

	public boolean hasPerm4WmOrderDelete(String userIdtxt) {
		return hasPermissionByCode(userIdtxt, "WMORDER.DELETE");
	}

	public boolean harPerm4ShowBC(String userIdtxt) {
		return hasPermissionByCode(userIdtxt, "WMORDER.SHOWBC");
	}

	public boolean harPerm4Joining(String userIdtxt) {
		return hasPermissionByCode(userIdtxt, "PREORDER.JOINING");
	}

	public boolean harPerm4Takeback(String userIdtxt) {
		return hasPermissionByCode(userIdtxt, "PREORDER.TAKEBACK");
	}

	public boolean hasPerm4TransUnSource(Object userIdObj) {
		return hasPermissionByCode(userIdObj, "TRANFER.UNSOURE");
	}

}

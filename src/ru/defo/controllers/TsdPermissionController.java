package ru.defo.controllers;

import java.util.List;

import ru.defo.managers.PermissionManager;
import ru.defo.managers.UserManager;
import ru.defo.model.WmPermission;
import ru.defo.util.HibernateUtil;
public class TsdPermissionController {

	List<Long> listModulesTsd;

	/**
	 *
	 * @see need check throughput after modification !
	 *
	 * */
	public TsdPermissionController(int userId){
		UserManager userMgr = new UserManager();
		//listModulesTsd = userMgr.getListModuls(userId);

		Long userIdL = userMgr.getUserId(userId);
		listModulesTsd = userMgr.getListModules(HibernateUtil.getSession(), userIdL);
	}

	public boolean hasPermission(Long userId, Long permissionId)
	{
		return new PermissionManager().hasPermission(userId, permissionId);
	}

	public List<Long> getModulesList(){
		return listModulesTsd;
	}

	public boolean hasPermissions()
	{
		if(listModulesTsd==null) return false;
		return (listModulesTsd.size() > 0);
	}

	public WmPermission getPermission(Long permissionId){
		PermissionManager permissionMgr = new PermissionManager();
		return permissionMgr.getPermission(permissionId);
	}

	public Long getPermissionId(String permissionCode)
	{
		return new PermissionManager().getPermissionId(permissionCode);
	}

}

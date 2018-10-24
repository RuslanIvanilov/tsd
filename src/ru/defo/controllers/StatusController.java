package ru.defo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.managers.StatusManager;
import ru.defo.model.WmStatus;
import ru.defo.util.HibernateUtil;

public class StatusController {

	StatusManager statusMgr;

	public StatusController(){
		statusMgr = new StatusManager();
	}

	public List<WmStatus> getStatusList(){
		return statusMgr.getStatusList();
	}

	public static Long getFirstStatus(){  return 1L; }

	public WmStatus getById(Long statusId)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("statusId", statusId));
		WmStatus status = (WmStatus)HibernateUtil.getUniqObject(WmStatus.class, restList, false);

		return status;
	}

}

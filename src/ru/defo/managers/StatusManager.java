package ru.defo.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmStatus;
import ru.defo.util.HibernateUtil;

public class StatusManager extends ManagerTemplate {
	public StatusManager() {
		super(WmStatus.class);
	}


	public WmStatus getStatusById(Long statusId){
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("statusId", statusId));
		WmStatus status = (WmStatus)HibernateUtil.getUniqObject(WmStatus.class, restList, false);

		return status;
	}

	public List<WmStatus> getAll(){
		return HibernateUtil.getObjectList(WmStatus.class, new CriterionFilter().getFilterList(), 0, true, "statusId");
	}

	/**@deprecated
	 * @see getAll()
	 * */
	public List<WmStatus> getStatusList(){
		CriterionFilter flt = new CriterionFilter();
		List<WmStatus> statusList = HibernateUtil.getObjectList(WmStatus.class, flt.getFilterList(), 0, true, "statusId");

		return statusList;
	}




}

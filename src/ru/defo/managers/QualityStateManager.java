package ru.defo.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmQualityState;
import ru.defo.util.HibernateUtil;


public class QualityStateManager extends ManagerTemplate {
	public QualityStateManager(){ super(); }

	/**@deprecated
	 * @see getAll()
	 * */
	@SuppressWarnings("unchecked")
	public List<WmQualityState> getListQualityState() {

		List<WmQualityState> wmQualityState = (List<WmQualityState>)session.createQuery("from WmQualityState").list();

		return wmQualityState;

	}

	public List<WmQualityState> getAll()
	{
		CriterionFilter flt = new CriterionFilter();
		List<WmQualityState> stateList = HibernateUtil.getObjectList(WmQualityState.class, flt.getFilterList(), 0, false, "qualityStateId");
		return stateList;
	}


	public WmQualityState getQualityStateById(Long qualityStateId)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("qualityStateId", qualityStateId));
		WmQualityState qualityState = (WmQualityState)HibernateUtil.getUniqObject(WmQualityState.class, restList, false);

		return qualityState;
	}

}

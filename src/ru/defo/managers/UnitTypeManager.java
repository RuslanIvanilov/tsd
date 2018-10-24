package ru.defo.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.model.WmUnitType;
import ru.defo.util.HibernateUtil;

public class UnitTypeManager extends ManagerTemplate {
	public UnitTypeManager() {
		super();
	}

	public WmUnitType getUnitTypeByCode(String unitTypeCode)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("unitTypeCode", unitTypeCode));
		WmUnitType wmUnitType = (WmUnitType) HibernateUtil.getUniqObject(WmUnitType.class, restList, false);
		/*
		WmUnitType wmUnitType = (WmUnitType) session.createQuery("from WmUnitType where unitTypeCode = :unitTypeCode")
				.setParameter("unitTypeCode", unitTypeCode).uniqueResult();
		*/
		//session.close();

		return wmUnitType;
	}
}

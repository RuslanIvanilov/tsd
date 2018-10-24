package ru.defo.util.data;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

public class RestFilter {

	List<SimpleExpression> restList;

	public RestFilter(){
		@SuppressWarnings("unused")
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
	}

	public List<SimpleExpression> add(String fieldName, Object fieldValue){
		restList.add(Restrictions.eq(fieldName, fieldValue.toString()));
		return restList;
	}

	public List<SimpleExpression> getFilters(){
		return restList;
	}

}

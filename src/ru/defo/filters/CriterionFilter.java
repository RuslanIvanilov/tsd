package ru.defo.filters;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ru.defo.util.AppUtil;

public class CriterionFilter {

	List<Criterion> restList;

	public CriterionFilter(){
		restList = new LinkedList<Criterion>();
	}

	public List<Criterion> getFilterList(){ return restList; }

	public void addFilter(String fieldName, String filterValue, String filterType){
		if (filterValue.isEmpty() || filterValue.length()==0) return;

    	switch (filterType){

    		case "eq":
			switch(Integer.decode((String) filterValue.toString())){
    		//switch(filterValue.toString()){
				case -1: restList.add(Restrictions.isNull(fieldName));
				break;

			 default:
				 restList.add(Restrictions.eq(fieldName, Long.decode((String) filterValue.toString())));
			}
			break;

    		case "nullDate":

    			switch(filterValue.toString().toUpperCase()){
    			case "0": case "N": case "Í":
    				restList.add(Restrictions.isNull(fieldName));
    				break;

    			case "1": case "Y": case "Ä":
    				restList.add(Restrictions.isNotNull(fieldName));
    				break;
    			}

    		break;

    		case "isnull":
    			restList.add(Restrictions.isNull(fieldName));
    		break;


    		case "ne":
    			restList.add(Restrictions.ne(fieldName, Long.decode((String) filterValue.toString())));
    		break;

    		case "in":

    			String[] rt = filterValue.toString().split(",");
    			List<Long> routs = new ArrayList<Long>();
    			for(int i=0; i<rt.length; i++){
    				routs.add(Long.valueOf(rt[i]));
    			}

    			restList.add(Restrictions.in(fieldName, routs));
    		break;

    		case "between":
    			String[] qq = filterValue.toString().split(",");
    			restList.add(Restrictions.between(fieldName, Long.valueOf(qq[0]), Long.valueOf(qq[1])));
    		break;

    		case "bool":
    			restList.add(Restrictions.eq(fieldName, Boolean.parseBoolean(filterValue)));
    		break;

    		case "lt":
    			restList.add(Restrictions.lt(fieldName, Long.decode((String) filterValue.toString())));
    		break;

    		case "gt":
    			restList.add(Restrictions.gt(fieldName, Long.decode((String) filterValue.toString())));
    		break;

    		case "le":
    			restList.add(Restrictions.le(fieldName, Long.decode((String) filterValue.toString())));
    		break;

    		case "ge":
    			restList.add(Restrictions.ge(fieldName, Long.decode((String) filterValue.toString())));
    		break;

	    	case "date":
	    		restList.add(Restrictions.ge(fieldName, AppUtil.stringToTimestamp(filterValue)));
	    		restList.add(Restrictions.lt(fieldName, AppUtil.getNextDay(filterValue)));
	    	break;

	    	case "date..":
	    		restList.add(Restrictions.ge(fieldName, AppUtil.stringToTimestamp(filterValue)));
	    	break;

	    	case "<>":
				Long fltValueLong = 0L;

				try {
					fltValueLong = Long.decode(((String) filterValue).replaceAll("[^\\.0123456789]", ""));
				} catch (Exception e) {
					e.printStackTrace();
				}

				switch (((String) filterValue).charAt(0)) {
					case '>':
						restList.add(Restrictions.gt(fieldName, fltValueLong));
						break;
					case '<':
						restList.add(Restrictions.lt(fieldName, fltValueLong));
						restList.add(Restrictions.gt(fieldName, 0L));
						break;
					default:
						restList.add(Restrictions.eq(fieldName, fltValueLong));
				}


			break;

	    	case "like":
				if (((String) filterValue).contains("<>''")) {
					restList.add(Restrictions.isNotNull(fieldName));
				} else {
					if (((String) filterValue).contains("''")) {
						restList.add(Restrictions.isNull(fieldName));
					} else {
						restList.add(Restrictions.ilike(fieldName, (String) filterValue, MatchMode.ANYWHERE));

					}
				}

			break;

    	}

	}



}

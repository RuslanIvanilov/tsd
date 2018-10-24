package ru.defo.managers;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ru.defo.util.HibernateUtil;

public class ManagerTemplate {
	Session session;
	Criteria criteria;

	public ManagerTemplate() {
		initSession();
	}

	public ManagerTemplate(Class<?> t) {
		initSession();
		criteria = session.createCriteria(t.getName());
	}

	public void finilize(){
		session.close();
		HibernateUtil.getSessionFactory().close();
	}


	public void initSession() {
		session = HibernateUtil.getSession();
	}

	public void closeSession() {
		session.close();
	}

	public void closeConnection(){
		session.close();
		HibernateUtil.getSessionFactory().close();
	}

	 
	protected void setFilter(Criteria criteria, String filterType, String fieldName, Object filterValue) throws Exception {
		if (filterValue != null && !filterValue.toString().isEmpty()) {
			switch (filterType) {
			case "bl":

				switch (filterValue.toString().toUpperCase().charAt(0)) {
					case 'Y':
					case 'Ä':
					case '1':
						criteria.add(Restrictions.eq(fieldName, true));
					break;

					case 'N':
					case 'Í':
					case '0':
						criteria.add(Restrictions.eq(fieldName, false));
					break;
				}
				break;

			case "like":
				if (((String) filterValue).contains("<>''")) {
					criteria.add(Restrictions.isNotNull(fieldName));
				} else {
					if (((String) filterValue).contains("''")) {
						criteria.add(Restrictions.isNull(fieldName));
					} else {
					/*try{
							Long longValue = Long.decode((String)filterValue.toString());
							if(longValue > 0)
							{
								criteria.add(Restrictions.eq(fieldName, longValue));
								if(criteria.list().size()==0)
									criteria.add(Restrictions.ilike(fieldName, (String) filterValue, MatchMode.ANYWHERE));
							}
						} catch(Exception e){
							e.printStackTrace();*/
							criteria.add(Restrictions.ilike(fieldName, (String) filterValue, MatchMode.ANYWHERE));
					   // }
					}
				}

				break;

			case "eq":
				switch(Integer.decode((String) filterValue.toString())){
					case -1: criteria.add(Restrictions.isNull(fieldName));
					break;

				 default:
					criteria.add(Restrictions.eq(fieldName, Long.decode((String) filterValue.toString())));

				}
				break;

			case "date":
				criteria.add(Restrictions.sqlRestriction("date_trunc('day'," + fieldName + ") = ?", filterValue,
						org.hibernate.type.StandardBasicTypes.DATE));

				break;

			case "<":
				criteria.add(Restrictions.lt(fieldName, filterValue));
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
					criteria.add(Restrictions.gt(fieldName, fltValueLong));
					break;
				case '<':
					criteria.add(Restrictions.lt(fieldName, fltValueLong));
					criteria.add(Restrictions.gt(fieldName, 0L));
					break;
				default:
					criteria.add(Restrictions.eq(fieldName, fltValueLong));
				}

				break;
			}

		}
	}


}

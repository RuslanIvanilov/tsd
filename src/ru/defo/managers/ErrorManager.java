package ru.defo.managers;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ru.defo.model.WmError;
import ru.defo.util.HibernateUtil;

public class ErrorManager extends ManagerTemplate {

	public WmError getErrorById(Long errorId){
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria((WmError.class).getName());
		criteria.add(Restrictions.eq("errorId", errorId));
		WmError err = (WmError) criteria.uniqueResult();
		//if(session.isOpen()) session.close();
		return err;
	}

}

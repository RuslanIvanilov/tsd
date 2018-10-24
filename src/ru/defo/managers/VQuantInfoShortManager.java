package ru.defo.managers;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ru.defo.model.views.VQuantInfoShort;
import ru.defo.util.HibernateUtil;

public class VQuantInfoShortManager extends ManagerTemplate {
	public VQuantInfoShortManager() {
		super();
	}

public VQuantInfoShort getQuantByUnitId(Long quantId){
	Session session = HibernateUtil.getSession();
	Criteria criteria = session.createCriteria(VQuantInfoShort.class);
	criteria.add(Restrictions.eq("quantId", quantId));
	VQuantInfoShort quant = (VQuantInfoShort)criteria.uniqueResult();
	try{
	session.refresh(quant);
	}catch(Exception e){
		e.printStackTrace();
		//session.close();
	}finally{
		if(session.isOpen()) session.close();
	}

	return quant;

	}

@SuppressWarnings("unchecked")
public List<VQuantInfoShort> getListQuantInfo(String articleCode) {

	Session session = HibernateUtil.getSession();
	Query query = session.createQuery("from VQuantInfoShort where articleCode = :articleCode").setParameter("articleCode", articleCode);
	List<VQuantInfoShort> vQuantInfoShort = query.list();

	try{
		for(VQuantInfoShort vquant : vQuantInfoShort){
			session.refresh(vquant);
		}
	}catch(HibernateException e){
		System.out.println("ERROR: getListQuantInfo for Article : "+articleCode);
		e.printStackTrace();
	}finally{
		if(session.isOpen()) session.close();
	}

	return vQuantInfoShort;

}

@SuppressWarnings("unchecked")
public List<VQuantInfoShort> getListQuantInfoUnit(String unitCode) {
	Session session = HibernateUtil.getSession();
	List<VQuantInfoShort> vQuantInfoShort = (List<VQuantInfoShort>)session.createQuery("from VQuantInfoShort where unitCode = :unitCode").setParameter("unitCode", unitCode).list();

	try{
		for(VQuantInfoShort quant : vQuantInfoShort){
			session.refresh(quant);
		}
	}catch(HibernateException e){
		e.printStackTrace();
	}finally{
		if(session.isOpen()) session.close();
	}

	return vQuantInfoShort;

}

}

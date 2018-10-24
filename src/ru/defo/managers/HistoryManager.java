package ru.defo.managers;
 

import org.hibernate.Criteria; 
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.defo.model.WmHistory;
import ru.defo.util.HibernateUtil;

public class HistoryManager extends ManagerTemplate {

	public HistoryManager(){ super(); }


	public void insertHistory(WmHistory wmHistory)
	{

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		try
		{
			tx.begin();
			session.persist(wmHistory);
			tx.commit();
		}catch(Exception e){
			System.out.println("ERR: HistoryManager::insertHistory ");
			e.printStackTrace();
			tx.rollback();
			session.close();
		}
	}

	/*public long getNextHistoryId(){
		SQLQuery query = HibernateUtil.getSession().createSQLQuery("select nextval('seq_history_id')");
		int nextHistoryId = (int) ((BigInteger) query.uniqueResult()).longValue();

		return nextHistoryId;
	}*/

	/*
	public long getNextHistoryId(){
		return ((BigInteger)HibernateUtil.getSession().createSQLQuery("select nextval('seq_history_id')").uniqueResult()).longValue();
	}
	*/

	public Long getLastBinIdByUnitId(Long unitId){

		Criteria history = HibernateUtil.getSession().createCriteria(WmHistory.class);
		history.add(Restrictions.eq("unitId", unitId));
		history.add(Restrictions.isNotNull("binId"));
		history.addOrder(Order.desc("historyId"));
		history.setMaxResults(1);
		WmHistory histRef = (WmHistory)history.uniqueResult();
		if(histRef == null) return null;
		return histRef.getBinId();
	}

}

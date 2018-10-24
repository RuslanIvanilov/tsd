package ru.defo.util;

import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.util.data.RestFilter;


public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static Session session;

    static  {
        try {
        	sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        	session = sessionFactory.openSession();
        	session.setCacheMode(CacheMode.IGNORE);
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
    	if(!session.isOpen()){
    		session = sessionFactory.openSession();
        	session.setCacheMode(CacheMode.IGNORE);
    	}
        return session;
    }

	public static void closeAll(){
    	if(session.isOpen()) session.close();
    		session.clear();
    		sessionFactory.close();
    }

	public static void close(){ if(session.isOpen()) session.close(); }

	@SuppressWarnings("rawtypes")
	public static Object getUniqObject(Class modelClass, RestFilter flt, boolean needCloseSession){
    	Session session = getSession();
    	Criteria criteria = session.createCriteria(modelClass);
    	for(Criterion criterion : flt.getFilters())
    		criteria.add(criterion);
    		criteria.setMaxResults(1);
		Object resultObj =  criteria.uniqueResult();
		if(needCloseSession) session.close();
    	return resultObj;
    }


    @SuppressWarnings({ "rawtypes" })
	public static Object getUniqObject(Class modelClass, List<SimpleExpression> criterionList, boolean needCloseSession){
    	Session session = getSession();
    	Criteria criteria = session.createCriteria(modelClass);
    	for(Criterion criterion : criterionList)
    		criteria.add(criterion);
    		criteria.setMaxResults(1);
		Object resultObj =  criteria.uniqueResult();
		if(needCloseSession) session.close();
    	return resultObj;
    }

    @SuppressWarnings({ "rawtypes"})
    public static <T> List<T> getObjectList(Class modelClass, List<Criterion> criterionList, int rowCount, boolean needCloseSession, String orderByColumn){
    	return getObjectList(modelClass, criterionList, rowCount, needCloseSession, orderByColumn, false);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> getObjectList(Class modelClass, List<Criterion> criterionList, int rowCount, boolean needCloseSession, String orderByColumn, boolean orderByDesc){
    	Session session = getSession();
    	Criteria criteria =session.createCriteria(modelClass);
    	criteria.setLockMode(LockMode.NONE);
    	if(criterionList != null)
    		for(Criterion criterion : criterionList)
    			criteria.add(criterion);

    	if (rowCount > 0)
			criteria.setMaxResults(rowCount);

    	if(orderByColumn != null && !orderByColumn.isEmpty()){
    		String[] strArr = orderByColumn.split(",");

    		for(String strOrder : strArr){
	    		if(orderByDesc){
	    			criteria.addOrder(Order.desc(strOrder.trim()));
	    		} else {
	    			criteria.addOrder(Order.asc(strOrder.trim()));
	    		}
    		}
    	}

    	List<T> resultObj = (List<T>)criteria.list();
    	//if(needCloseSession) session.close();
    	if(resultObj == null) return null;

    	return resultObj;
    }

    /** Complitly save transaction
     * */
    public static void persist(Object obj, boolean needClose){
    	Session session = getSession();
    	Transaction trn = session.getTransaction();
    	try{
			trn.begin();
			session.persist(obj);
			trn.commit();
			} catch(Exception e){
				e.printStackTrace();
				trn.rollback();
			} finally{
				if(needClose)session.close();
			}
    }

    public static void merge(Object obj){
    	Session s = getSession();
    	Transaction tx = s.getTransaction();
    	try{
    		tx.begin();
    		s.merge(obj);
    		tx.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    		tx.rollback();
    		s.close();
    	}


    }



    /**@deprecated
     * */
    public static void update(Object obj)
    {
    		session.merge(obj);
    }

    public static void update(Session session, Object obj)
    {
    		session.merge(obj);
    }

    /**
     * @category Save or update + commit transaction.
     * */
    public static void saveOrUpdate(Object obj){
    	session = getSession();
		session.getTransaction().begin();
		session.merge(obj);
		session.getTransaction().commit();
		session.close();
    }

    public static void delete(Object obj)
    {
    	getSession().delete(obj);
    }

    public static void save(Object obj){
    	saveOrUpdate(obj);
    }

}

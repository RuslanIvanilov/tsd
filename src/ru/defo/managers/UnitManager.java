package ru.defo.managers;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmArticle;
import ru.defo.model.WmBin;
import ru.defo.model.WmQuant;
import ru.defo.model.WmUnit;
import ru.defo.model.WmUnitType;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

public class UnitManager extends ManagerTemplate {
	public UnitManager() {
		super();
	}

	public void clearUnitReserv(Session session, WmUnit unit)
	{
		List<WmBin> binList = new BinManager().getReservBinListByUnit(unit);

		for(WmBin bin : binList){
			bin.setReservForUnitId(null);
			session.merge(bin);
		}

	}

	public List<WmUnit> getUnitListAll()
	{
		List<WmUnit> unitList = HibernateUtil.getObjectList(WmUnit.class, new CriterionFilter().getFilterList(), 0, true, "unitId");
		return unitList;
	}

	public void setMakeCheck(Session session, WmUnit unit){
		unit.setNeedCheck(false);
		session.persist(unit);
	}

	public WmUnit getUnitById(Long unitId){
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("unitId", unitId));
		WmUnit unit = (WmUnit)HibernateUtil.getUniqObject(WmUnit.class, restList, false);

		//WmUnit unit = (WmUnit) HibernateUtil.getSession().createQuery("from WmUnit where unitId = :unitid").setParameter("unitid", unitId).uniqueResult();

		if(!(unit instanceof WmUnit)) return null;
		return unit;
	}

    public WmBin getBinByUnitId(Long unitId)
    {
		WmUnit unit = getUnitById(unitId);
		return new BinManager().getBinById(unit.getBinId());
	}

    /**
     * @see UnitController.getWmUnit() | UnitController.getUnitId()
     * */
	public WmUnit getUnitByCode(String unitCode)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("unitCode", unitCode));
		WmUnit unit = (WmUnit)HibernateUtil.getUniqObject(WmUnit.class, restList, false);
		if(!(unit instanceof WmUnit)) return null;
		/*
		session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(WmUnit.class);
		criteria.add(Restrictions.eq("unitCode", unitCode));
		WmUnit unit = null;
		if(criteria.list().size() == 1)
			unit = (WmUnit) criteria.uniqueResult();
		*/

		return unit;


	}

	public List<WmUnit> getUnitListByBin(WmBin bin){
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("binId", bin.getBinId().toString(), "eq");
		return HibernateUtil.getObjectList(WmUnit.class, flt.getFilterList(), 0, false, "unitCode");
	}

	@SuppressWarnings("unchecked")
	public List<WmUnit> getUnitListByBinId(Long binId)
	{
		return (List<WmUnit>)HibernateUtil.getSession().createQuery("from WmUnit where binId = :binId").setParameter("binId", binId).list();
	}

	/**@deprecated
	 * */
	public void clearBinId(long unitId){
		/*
		WmUnit unit = getUnitById(unitId);
		unit.setBinId(null);
		session.save--(unit);
		*/
		session = HibernateUtil.getSession();
		try{
			session.getTransaction().begin();
			session.createQuery("update WmUnit set binId = null where unitId = :unitId").setParameter("unitId", unitId).executeUpdate();
			session.getTransaction().commit();

		}catch(Exception e){
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();
		} /*finally{
			session.close();
		}*/
	}

	public void clearBinId(Session session, WmUnit unit){
		unit.setBinId(null);
		session.persist(unit);
	}

	public void clearBinId(Session session, long unitId)
	{
		try{
			session.createQuery("update WmUnit set binId = null where unitId = :unitId").setParameter("unitId", unitId).executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			session.close();
		}
	}

	/**@deprecated
	 * */
	public void updateUnit(WmUnit unit)
	{
		session = HibernateUtil.getSession();
		try{
		session.getTransaction().begin();
		//session.saveOrUpdate(unit);
		session.persist(unit);
		session.getTransaction().commit();
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
		} /*finally{
			session.close();
		}*/
	}

	public void updateUnit(Session session, WmUnit unit) throws SQLException
	{
		session.persist(unit);
	}

	/**@deprecated
	 * */
	public void createWmUnit(WmUnit wmUnit)
	{
		session = HibernateUtil.getSession();

		try{
			session.getTransaction().begin();
			session.merge(wmUnit);
			session.getTransaction().commit();
		} catch(Exception e){
			System.out.println("ERROR: createUnit(WmUnit wmUnit)");
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();
		}/* finally{
			session.close();
		}*/
	}


	public WmUnit createUnit(String unitTypeCode)
	{
		WmUnit unit = new WmUnit();

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		try{
			tx.begin();
			unit.setUnitId(getNextUnitId());
			unit.setUnitCode(unitTypeCode+String.valueOf((100000000+unit.getUnitId())).substring(1));
			unit.setUnitTypeCode(unitTypeCode);
			//HibernateUtil.save(unit);
			session.persist(unit);
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			System.out.println("ERROR: createUnit(String unitTypeCode)");
			e.printStackTrace();
		}

		unit = new UnitManager().getUnitByCode(unit.getUnitCode());

		return unit;
	}


	public long getNextUnitId(){
		return ((BigInteger) HibernateUtil.getSession().createSQLQuery("select nextval('seq_unit_id')").uniqueResult()).longValue();
	}


	public String getUnitBinArticle(Long binId, Long articleId){

		return ((String) HibernateUtil.getSession().createSQLQuery("select u.unit_code from wm_unit u inner join wm_quant q on u.unit_id = q.unit_id where u.bin_id = :binId and q.article_id = :articleId limit 1")
		.setParameter("binId", binId).setParameter("articleId", articleId).uniqueResult());
	}

	public List<WmUnit> getUnitListByBinArticle(WmBin bin, WmArticle article){

		return getUnitListByBinIdArticleId(bin==null?null:bin.getBinId(), article==null?null:article.getArticleId());
	}

	public List<WmUnit> getUnitListByBinIdArticleId(Long binId, Long articleId){

		CriterionFilter flt = new CriterionFilter();
		if(binId != null) flt.addFilter("binId", binId.toString(), "eq");
		flt.addFilter("needCheck", "false", "bool");
		List<WmUnit> unitList = HibernateUtil.getObjectList(WmUnit.class, flt.getFilterList(), 0, true,"");

		CriterionFilter filter = new CriterionFilter();
		filter.addFilter("articleId", articleId.toString(), "eq");
		filter.addFilter("needCheck", "false", "bool");
		List<WmQuant> quantList = HibernateUtil.getObjectList(WmQuant.class, filter.getFilterList(), 0, true,"");

		List<WmUnit> result = new ArrayList<WmUnit>();

		for(WmUnit unit : unitList){
			 for(WmQuant quant : quantList){
				 if(quant.getUnitId().longValue()== unit.getUnitId().longValue() ){
					 result.add(unit);
				 }
			 }
		}

		/*
		@SuppressWarnings("unchecked")
		List<WmUnit> result = (List<WmUnit>)HibernateUtil.getSession().createSQLQuery("select * from wm_unit where unit_id in(select u.unit_id from wm_unit u inner join wm_quant q on u.unit_id = q.unit_id where u.bin_id = :binId and q.article_id = :articleId) ")
				.setParameter("binId", binId).setParameter("articleId", articleId).list();
 		*/

		return result;

	}

	public boolean isISOunitType(Long articleId){
		List<WmQuant> quantList = new QuantManager().getQuantListByArticleId(articleId.toString());

		for(WmQuant quant : quantList){
			WmUnit unit = new UnitManager().getUnitById(quant.getUnitId());
			String unitType = new UnitManager().getUnitType(unit.getUnitCode());
			//System.out.println("unitType = "+unitType);
			if(unitType.equals(DefaultValue.ISO_UNIT_TYPE_NAME)) return true;
		}

		return false;
	}


	public String getUnitType(String barCode){

		UnitTypeManager unitTypeMgr = new UnitTypeManager();
		WmUnitType unitType = unitTypeMgr.getUnitTypeByCode(barCode.substring(0, 2));
		if(unitType != null){
			return unitType.getUnitTypeCode();
		}
		return null;

	}

}

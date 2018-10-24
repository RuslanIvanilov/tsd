package ru.defo.managers;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ru.defo.controllers.HistoryController;
import ru.defo.controllers.PreOrderController;
import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmPreOrder;
import ru.defo.model.WmRoute;
import ru.defo.util.HibernateUtil;

public class DeliveryManager extends ManagerTemplate {

	public DeliveryManager(){
		super(WmRoute.class);
	}

	public List<WmRoute> getAll(){
		CriterionFilter flt = new CriterionFilter();
		List<WmRoute> routeList = HibernateUtil.getObjectList(WmRoute.class, flt.getFilterList(), 0, false, "routeId");

		return routeList;
	}

	public boolean hasErrorPreOrdersByRoute(Long routeId)
	{
		WmRoute route = getById(routeId);
		List<WmPreOrder> preOrderList = new PreOrderController().getPreOrderListByRoute(route);

		for(WmPreOrder preOrder : preOrderList)
		{
			if(preOrder.getErrorId() != null){
				return true;
			}
		}

		return false;
	}

	public void setPrinted(Session session, WmRoute route)
	{
		route.setPrintDate(new Timestamp(System.currentTimeMillis()));
		session.merge(route);
	}

	public WmRoute getByCode(String routeCode){
		Criteria criteria = HibernateUtil.getSession().createCriteria((WmRoute.class).getName());
		criteria.add(Restrictions.eq("routeCode", routeCode));
		WmRoute route = (WmRoute)criteria.uniqueResult();
		if(route instanceof WmRoute)
			HibernateUtil.getSession().refresh(route);
		return route;
	}

	public WmRoute getById(Long routeId){
		Session session  = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria((WmRoute.class).getName());
		criteria.add(Restrictions.eq("routeId", routeId));
		WmRoute route = (WmRoute)criteria.uniqueResult();

		session.close();

		if(!(route instanceof WmRoute)) return new WmRoute();
		return route;
	}

	public long getNextRouteId(){
		return ((BigInteger) HibernateUtil.getSession().createSQLQuery("select nextval('seq_route_id')").uniqueResult()).longValue();
	}

	public WmRoute createOrUpdate(Session session, WmRoute route)
	{
		boolean needUpdate = false;

		WmRoute route0 = getByCode(route.getRouteCode());
		if(route0 instanceof WmRoute){
			if(route.getCarId() != null) {
				route0.setCarId(route.getCarId());
				needUpdate = true;
			}
			if(route.getExpectedDate() != null && !(route.getExpectedDate().equals(route0.getExpectedDate()))){
				route0.setExpectedDate(route.getExpectedDate());
				needUpdate = true;
			}
			if(route.getBatchId() != null && !(route.getBatchId().equals(route0.getBatchId()))) {
				route0.setBatchId(route.getBatchId());
				needUpdate = true;
			}
			if(route.getVolume() != null && !(route.getVolume().equals(route0.getVolume()))) {
				route0.setVolume(route.getVolume());
				needUpdate = true;
			}
			if(route.getWeight() != null && !(route.getWeight().equals(route0.getWeight()))) {
				route0.setWeight(route.getWeight());
				needUpdate = true;
			}

			if(needUpdate)session.update(route0);
		} else {
			route0 = new WmRoute();
			route0.setRouteId(getNextRouteId());
			route0.setRouteCode(route.getRouteCode());
			route0.setCarId(route.getCarId());
			route0.setExpectedDate(route.getExpectedDate());
			route0.setStatusId(1L);
			route0.setVolume(route.getVolume());
			route0.setWeight(route.getWeight());
			session.persist(route0);
			new HistoryController().addLoadDataEntry(session, 0L, "WmRoute/Маршрут", route0.getRouteId()+" ["+route0.getRouteCode()+"]");
		}

		return route0;
	}

}

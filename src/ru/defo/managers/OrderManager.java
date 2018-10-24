package ru.defo.managers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.filters.CriterionFilter;
import ru.defo.filters.FieldFilter;
import ru.defo.filters.VorderPosFilter;
import ru.defo.model.WmOrder;
import ru.defo.model.WmOrderPos;
import ru.defo.model.WmPreOrder;
import ru.defo.model.WmPreOrderPos;
import ru.defo.model.WmQuant;
import ru.defo.model.WmRoute;
import ru.defo.model.views.Vorderpos;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;

public class OrderManager extends ManagerTemplate {
	public OrderManager() {
		super(WmOrder.class);
	}


	public List<WmOrderPos> getPosAll(String dateFilter)
	{
		CriterionFilter f = new CriterionFilter();
		if(dateFilter!=null) f.addFilter("createDate", dateFilter, "date..");
		List<WmOrderPos> orderPosList = HibernateUtil.getObjectList(WmOrderPos.class, f.getFilterList(), 0, true, "orderId");

		return orderPosList;
	}

	public Set<WmOrder> getOrderListByRoute(WmRoute route){
		CriterionFilter filter = new CriterionFilter();
		filter.addFilter("routeId", route.getRouteId().toString(), "eq");
		List<WmPreOrder> preOrderList = HibernateUtil.getObjectList(WmPreOrder.class, filter.getFilterList(), 0, true, "orderCode");

		Set<WmOrder> orderLinkSet = new HashSet<WmOrder>();
		for(WmPreOrder preOrder : preOrderList){
			if(preOrder.getWmOrderLink() !=null){
				WmOrder order = new OrderManager().getOrderById(preOrder.getWmOrderLink());
				if(order instanceof WmOrder) orderLinkSet.add(order);
			}
		}

		return orderLinkSet;
	}

	public void tryOpenClosedPos(WmOrder order)
	{
		Session session = HibernateUtil.getSession();
		List<WmOrderPos> orderPosList = new OrderPosManager().getClosedOrderPosList(order.getOrderId());
		for(WmOrderPos pos : orderPosList){
			if(pos.getExpectQuantity() > (pos.getFactQuantity()==null?0:pos.getFactQuantity())){
				List<WmQuant> quantList = new QuantManager().getQuantListByArticleId(pos.getArticleId());
				if(!new QuantManager().getQuantListPickAvalable(quantList).isEmpty()){
					Transaction tx = session.getTransaction();
					tx.begin();
					pos.setStatusId(DefaultValue.STATUS_CREATED);
					session.persist(pos);
					tx.commit();
					session.close();
				}

			}
		}

	}

	public WmOrderPos getOrderPosByPosId(Long orderPosId){
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("orderPosId", orderPosId));
		WmOrderPos orderPos = (WmOrderPos)HibernateUtil.getUniqObject(WmOrderPos.class, restList, false);

		return orderPos;
	}

	public WmOrderPos getOrderPosByIdPosId(Long orderId, Long orderPosId)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("orderId", orderId));
		restList.add(Restrictions.eq("orderPosId", orderPosId));
		WmOrderPos orderPos = (WmOrderPos)HibernateUtil.getUniqObject(WmOrderPos.class, restList, false);
		if(!(orderPos instanceof WmOrderPos)) return null;

		return orderPos;
	}

	public List<WmOrderPos> getOrderPosListByOrderId(Long orderId, boolean needCloseSession){
		CriterionFilter filter = new CriterionFilter();
		filter.addFilter("orderId", orderId.toString(), "eq");
		return HibernateUtil.getObjectList(WmOrderPos.class, filter.getFilterList(), 0, needCloseSession,"");
	}

	public List<WmOrderPos> getOpenOrderPosListByOrderId(Long orderId, boolean needCloseSession){
		CriterionFilter filter = new CriterionFilter();
		filter.addFilter("orderId", orderId.toString(), "eq");
		filter.addFilter("statusId", "3", "lt");
		List<WmOrderPos> orderPos = HibernateUtil.getObjectList(WmOrderPos.class, filter.getFilterList(), 0, needCloseSession,"");
		return orderPos;
	}


	public boolean hasOpenedOrderLines(Long orderId)
	{
		List<WmOrderPos> orderPos = getOpenOrderPosListByOrderId(orderId, true);
		return orderPos.size()>0;
	}

	public WmOrder getOrderById(Long orderId)
	{
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(WmOrder.class);
		criteria.add(Restrictions.eq("orderId", orderId));
		criteria.setMaxResults(1);
		WmOrder order = (WmOrder) criteria.uniqueResult();

		//session.evict(order);
		//session.close();
		if(!(order instanceof WmOrder)) return null;
		//session.refresh(order);
		return order;
	}

	public WmOrder getOrderByCodeFlt(String codeFilter){
		if(codeFilter.isEmpty()) return null;

		Criteria criteria = HibernateUtil.getSession().createCriteria(WmOrder.class);
		criteria.add(Restrictions.ilike("orderCode", codeFilter, MatchMode.ANYWHERE));
		return (WmOrder) criteria.uniqueResult();
	}

	public WmOrder getOrderByCode(String orderCode, boolean needClose){
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(WmOrder.class);
		criteria.add(Restrictions.eq("orderCode", orderCode));
		criteria.setMaxResults(1);
		WmOrder order = (WmOrder) criteria.uniqueResult();
		if(needClose) session.close();

		return order;
	}

	public long getNextOrderId(){
		return ((BigInteger) HibernateUtil.getSession().createSQLQuery("select nextval('seq_order_id')").uniqueResult()).longValue();
	}

	public List<Vorderpos> getVOrderPosList(Vorderpos vOrdPos, FieldFilter filter)
	{
		Criteria criteria = HibernateUtil.getSession().createCriteria(Vorderpos.class);
		criteria.addOrder(Order.asc("vendorGroupId"));
		criteria.add(Restrictions.eq("orderId", vOrdPos.getOrderId()));

		try{
			if(vOrdPos.getStatusId()!= null) setFilter(criteria, filter.get().get("statusId"), "statusId", vOrdPos.getStatusId());
		}catch(Exception e){
			System.out.println("ERROR: "+this.getClass().getSimpleName()+".getVOrderPosList()");
			e.printStackTrace();
		}



		@SuppressWarnings("unchecked")
		List<Vorderpos> vOrderPosList = (List<Vorderpos>) criteria.list();

		if(vOrderPosList.size()>0)
			try{
				for(Vorderpos orderPos : vOrderPosList){
					HibernateUtil.getSession().refresh(orderPos);
				}
			}catch(Exception e){
				System.out.println("ERROR OrderManager.getVOrderPosList --> session.refresh(orderPos) ResultSet == NULL");
				e.printStackTrace();
			}

		return vOrderPosList;
	}

	public List<Vorderpos> getVOrderPosList(VorderPosFilter orderPosFilter){
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("orderId", orderPosFilter.getOrderId().toString(), "eq");
		flt.addFilter("orderPosId", orderPosFilter.getPosflt(), "eq");
		flt.addFilter("articleCode", orderPosFilter.getArticleidflt(), "like");
		flt.addFilter("articleName", orderPosFilter.getArtdescrflt(), "like");
		flt.addFilter("skuName", orderPosFilter.getSkuflt(), "like");

		flt.addFilter("expectQuantity", orderPosFilter.getExpqtyflt(), "<>");
		flt.addFilter("factQuantity", orderPosFilter.getFactqtyflt(), "<>");
		flt.addFilter("ctrlQuantity", orderPosFilter.getCtrlqtyflt(), "<>");

		flt.addFilter("statusId", orderPosFilter.getStatusflt(), "eq");
		flt.addFilter("vendorGroupId", orderPosFilter.getVendoridflt(), "eq");

		List<Vorderpos> vOrderPosList = HibernateUtil.getObjectList(Vorderpos.class, flt.getFilterList(), 0, true,"");

		Session session = HibernateUtil.getSession();
		try{
		if(vOrderPosList.size()>0)
			for(Vorderpos ordPos : vOrderPosList){
				session.refresh(ordPos);
			}
		}catch(HibernateException e){
			e.printStackTrace();
		}finally{
			if(session.isOpen()) session.close();
		}

		//session.close();

		return vOrderPosList;
	}

	@SuppressWarnings("unchecked")
	public List<Vorderpos> getVOrderPosList(Long orderId, String posflt, String articleidflt, String artdescrflt,
			String skuflt, String expqtyflt, String factqtyflt, String statusflt, String vendoridflt){

		Session session = HibernateUtil.getSession();
		Criteria criteria = HibernateUtil.getSession().createCriteria(Vorderpos.class);
		criteria.add(Restrictions.eq("orderId", orderId));

		try {
			setFilter(criteria, "eq","orderId", orderId);
			setFilter(criteria, "eq","orderPosId", posflt);
			setFilter(criteria, "like", "articleCode", articleidflt);
			setFilter(criteria, "like","articleName", artdescrflt);
			setFilter(criteria, "like","skuName", skuflt);
			setFilter(criteria, "<>","expectQuantity", expqtyflt);
			setFilter(criteria, "<>","factQuantity", factqtyflt);
			setFilter(criteria, "eq","statusId", statusflt);
			setFilter(criteria, "eq","vendorGroupId", vendoridflt);


		} catch (Exception e) {
			e.printStackTrace();
			if(session.isOpen()) session.close();
		}

		criteria.addOrder(Order.asc("articleCode"));

		List<Vorderpos> vOrderPosList = (List<Vorderpos>) criteria.list();

		session = HibernateUtil.getSession();
		try{
			if(vOrderPosList != null)
				for(Vorderpos orderPos : vOrderPosList){
				session.refresh(orderPos);
				}
		}catch(HibernateException e){
			e.printStackTrace();
			if(session.isOpen()) session.close();
		}/*finally{
			if(session.isOpen()) session.close();
		}*/

			//session.close();

			return vOrderPosList;
	}

	public void delOrder(WmOrder order){

		PreOrderManager preOrdMgr = new PreOrderManager();

		List<WmOrderPos> orderPosList = new OrderPosManager().getOrderPosList(order.getOrderId());
		Session session = HibernateUtil.getSession();
		Transaction trn = session.getTransaction();

		List<WmPreOrder> preOrderList = preOrdMgr.getPreOrderByWmOrderLink(order.getOrderId());
		try{
			trn.begin();
		for(WmPreOrder preOrder  : preOrderList){
			preOrder.setWmOrderLink(null);
			preOrder.setStatusId(DefaultValue.STATUS_CREATED);
			session.persist(preOrder);
		}


		for(WmOrderPos orderPos : orderPosList){
			List<WmPreOrderPos> preOrderPosList = preOrdMgr.getPreOrderPosByWmPosOrderLink(orderPos.getOrderPosId());
			for(WmPreOrderPos preOrderPos : preOrderPosList){
				preOrderPos.setWmPosOrderLink(null);
				session.persist(preOrderPos);
			}
			session.delete(orderPos);
		}
			session.delete(order);
			trn.commit();
		}catch(Exception e){
			trn.rollback();
			e.printStackTrace();
			if(session.isOpen()) session.close();
		}/*finally{
			session.close();
		}*/

	}


}

package ru.defo.managers;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.defo.model.views.Vadvice;
import ru.defo.util.DefaultValue;

public class VAdviceManager extends ManagerTemplate{
	public VAdviceManager()
	{
		super(Vadvice.class);
	}

	public Vadvice getVAdviceByAdviceId(Long adviceId)
	{
		this.criteria.add(Restrictions.eq("adviceId", adviceId));
		this.criteria.setMaxResults(1);
		return (Vadvice)this.criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Vadvice> getVAdviceListByClientDocFilter(String codeFilter)
	{
		this.criteria.add(Restrictions.ilike("clientDocCode", codeFilter, MatchMode.ANYWHERE));
		//this.criteria.add(Restrictions.in("statusId", new Long[] {1l, 2l, 3l}));
		List<Vadvice> vAdviceList = (List<Vadvice>) this.criteria.list();

		for(Vadvice vadvice : vAdviceList){
			session.refresh(vadvice);
		}

		return vAdviceList;
	}

	@SuppressWarnings("unchecked")
	public List<Vadvice> getVAdviceListByAdviceCodeFilter(String codeFilter)
	{
		this.criteria.add(Restrictions.ilike("adviceCode", codeFilter, MatchMode.ANYWHERE));
		//this.criteria.add(Restrictions.in("statusId", new Long[] {1l, 2l, 3l}));
		List<Vadvice> vAdviceList = (List<Vadvice>) this.criteria.list();

		for(Vadvice vadvice : vAdviceList){
			session.refresh(vadvice);
		}

		return vAdviceList;
	}

	@SuppressWarnings("unchecked")
	public List<Vadvice> getVAdviceListByCarFilter(String codeFilter)
	{
		this.criteria.add(Restrictions.ilike("carCode", codeFilter, MatchMode.ANYWHERE));
		List<Vadvice> vAdviceList = (List<Vadvice>) this.criteria.list();

		for(Vadvice vadvice : vAdviceList){
			session.refresh(vadvice);
		}

		return vAdviceList;
	}


	@SuppressWarnings("unchecked")
	public List<Vadvice> getAdviceList(boolean activeOnly, Timestamp expDate, String adviceCodeFilter,
			String statusFilter,  String carFilter, String dokFilter, String commentFilter, String errorFilter)
	{
		List<Vadvice> vAdviceList = null;
		try{
		if(activeOnly)
			this.criteria.add(Restrictions.ne("statusId", DefaultValue.ARCHIVE_STATUS));
		this.criteria.add(Restrictions.ge("expectedDate", expDate));

		if(adviceCodeFilter.length()>0)
			this.criteria.add(Restrictions.ilike("adviceCode", adviceCodeFilter,  MatchMode.ANYWHERE));

		if(!statusFilter.equals("0"))
			if(statusFilter.matches("[1-9]")){
				this.criteria.add(Restrictions.eq("statusId", Long.valueOf(statusFilter)));
			}

		if(carFilter.length()>0)
			this.criteria.add(Restrictions.ilike("carCode", carFilter, MatchMode.ANYWHERE));

		if(dokFilter.length()>0)
			this.criteria.add(Restrictions.ilike("binCode", dokFilter, MatchMode.ANYWHERE));

		if(commentFilter.length()>0)
			this.criteria.add(Restrictions.ilike("errorComment", commentFilter, MatchMode.ANYWHERE));

		if(errorFilter.length()>0){

			if(errorFilter.matches("[1-9]")){
				this.criteria.add(Restrictions.eq("errorId", Long.valueOf(errorFilter)));
			}

			if(errorFilter.contains(">0")){
				this.criteria.add(Restrictions.isNotNull("errorId"));
			}

			if(errorFilter.equals("0")){
				this.criteria.add(Restrictions.isNull("errorId"));
			}


		}

		this.criteria.addOrder(Order.asc("expectedDate"));
		vAdviceList = (List<Vadvice>) this.criteria.list();

		if(vAdviceList != null){
			for(Vadvice vadvice : vAdviceList){
				session.refresh(vadvice);
			}
		}

		} catch(Exception e){
			e.printStackTrace();
		}

		return vAdviceList;
	}

}

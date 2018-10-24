package ru.defo.managers;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ru.defo.model.views.Vunitinfo;

public class VUnitInfoManager extends ManagerTemplate {
	public VUnitInfoManager() {
		super(Vunitinfo.class);
	}

	@SuppressWarnings("unchecked")
	public List<Vunitinfo> getVunitinfoListByArticleId(Long articleId, String unitFlt, String binFlt, String areaFlt,
			String rackFlt, String levelFlt, String qtyFlt, String skuFlt, String qStateFlt, String needChkFlt,
			Timestamp createDate,
			String surnameFlt, String fstnameFlt, String adviceFlt, String advicePosFlt,
			Timestamp expectedDate, String groupFlt, String articleflt, String artnameflt,
			int rowCount) throws Exception {

		setFilter("eq","articleId", articleId);

		setFilter("like", "unitCode", unitFlt);

		setFilter("like","binCode", binFlt);
		setFilter("like","areaCode", areaFlt);

		setFilter("eq","rackNo", rackFlt);
		setFilter("eq","levelNo", levelFlt);

		setFilter("<>","quantity", qtyFlt);
		setFilter("like","skuName", skuFlt);
		setFilter("like","qstateName", qStateFlt);
		setFilter("bl","needCheck", needChkFlt);

		setFilter("date","create_date", createDate);

		setFilter("like","surname", surnameFlt);
		setFilter("like","firstName", fstnameFlt);
		setFilter("like","adviceCode", adviceFlt);
		setFilter("eq","advicePosId", advicePosFlt);

		setFilter("date","expected_date", expectedDate);
		setFilter("like","vendorGroupName", groupFlt);

		setFilter("like","articleCode", articleflt);
		setFilter("like","articleName", artnameflt);

		if(rowCount > 0)
			this.criteria.setMaxResults(rowCount);

		List<Vunitinfo> infolist = (List<Vunitinfo>) this.criteria.list();
		for(Vunitinfo info : infolist){
			session.refresh(info);
		}

		return infolist;
	}



	private void setFilter(String filterType, String fieldName, Object filterValue) throws Exception{
		if(filterValue != null && !filterValue.toString().isEmpty()){
		switch(filterType)
		{
			case "bl" :

				switch(filterValue.toString().toUpperCase().charAt(0)){
					case 'Y' & 'Ä' & '1' :
						this.criteria.add(Restrictions.eq(fieldName, true));
					break;
					case 'N' & 'Í' & '0' :
						this.criteria.add(Restrictions.eq(fieldName, false));
					break;
				}
			break;

			case "like" :
				if(((String)filterValue).contains("<>''")){
					this.criteria.add(Restrictions.isNotNull(fieldName));
				} else {
					if(((String)filterValue).contains("''")){
						this.criteria.add(Restrictions.isNull(fieldName));
					} else {
						this.criteria.add(Restrictions.ilike(fieldName, (String)filterValue, MatchMode.ANYWHERE));
					}
				}

			break;

			case "eq" :
				this.criteria.add(Restrictions.eq(fieldName,  Long.decode((String)filterValue.toString())));
			break;

			case "date" :
				this.criteria.add(Restrictions.sqlRestriction("date_trunc('day',"+fieldName+") = ?", filterValue, org.hibernate.type.StandardBasicTypes.DATE));

			break;

			case "<>" :
				Long fltValueLong = 0L;

					try{
						fltValueLong = Long.decode(((String)filterValue).replaceAll("[^\\.0123456789]",""));
					}catch(Exception e){
						e.printStackTrace();
					}

					switch(((String)filterValue).charAt(0)){
					case '>' :
						this.criteria.add(Restrictions.gt(fieldName, fltValueLong));
					break;
					case '<' :
						this.criteria.add(Restrictions.lt(fieldName, fltValueLong));
						this.criteria.add(Restrictions.gt(fieldName, 0L));
					break;
					default:
						this.criteria.add(Restrictions.eq(fieldName, fltValueLong));
					}

			break;
		}

	}
	}

}

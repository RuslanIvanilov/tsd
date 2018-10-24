package ru.defo.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session; 
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.filters.CriterionFilter;
import ru.defo.model.WmArea;
import ru.defo.model.WmBin;
import ru.defo.model.WmBinType;
import ru.defo.model.WmUnit;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil; 

public class BinManager extends ManagerTemplate {

	private WmBin bin;

	public BinManager() {
		super();
	}

	public List<WmArea> getAreaList()
	{
		return HibernateUtil.getObjectList(WmArea.class, new CriterionFilter().getFilterList(), 0, false, "areaCode");
	}

	public List<WmBin> getBinListByBinFlt(String binFlt){
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("binCode", binFlt, "like");
		List<WmBin> binList = HibernateUtil.getObjectList(WmBin.class, flt.getFilterList(), 0, false, "binCode");
		return binList;
	}

	public List<WmBin> getReservBinListByUnit(WmUnit unit)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("reservForUnitId", unit.getUnitId().toString(), "eq");
		return HibernateUtil.getObjectList(WmBin.class, flt.getFilterList(), 0, false, "binId");
	}

	public WmBin getMirroredBin(WmBin bin)
	{
		if(bin == null || bin.getMirrorBinId() == null)
			return null;

		return new BinManager().getBinById(bin.getMirrorBinId());
	}

	public WmBin getBin(String areaCode, Long rackNo, Long levelNo, Long binRackNo)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("areaCode", areaCode));
		restList.add(Restrictions.eq("rackNo", rackNo));
		restList.add(Restrictions.eq("levelNo", levelNo));
		restList.add(Restrictions.eq("binRackNo", binRackNo));

		return(WmBin)HibernateUtil.getUniqObject(WmBin.class, restList, false);
	}

	public List<WmBin> getBinListBetweenBins(WmBin binS, WmBin binF)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("binId", binS.getBinId().toString(), "ge");
		flt.addFilter("binId", binF.getBinId().toString(), "le");

		flt.addFilter("rackNo", binS.getRackNo().toString(), "ge");
		flt.addFilter("rackNo", binF.getRackNo().toString(), "le");

		flt.addFilter("levelNo", binS.getLevelNo().toString(), "ge");
		flt.addFilter("levelNo", binF.getLevelNo().toString(), "le");


		List<WmBin> binList = HibernateUtil.getObjectList(WmBin.class, flt.getFilterList(), 0, false, "binCode");

		/*
		for(WmBin bin : binList){
			System.out.println( bin.getBinId()+" : "+bin.getBinCode()+" / "+bin.getRackNo());
		}
		*/

		return binList;
	}


	public void setMakeCheck(Session session, WmBin bin){
		bin.setNeedCheck(false);
		session.persist(bin);
	}

	public WmBin getBinById(Long binId){
		Session session = HibernateUtil.getSession();
		if(binId ==null) return null;
		bin = (WmBin) session.createQuery("from WmBin where binId = :binId")
				.setParameter("binId", binId).uniqueResult();

		if(bin instanceof WmBin)
			session.refresh(bin);

		return bin;
	}

	public List<WmBin> getAll(){
		CriterionFilter flt = new CriterionFilter();
		List<WmBin> binList = HibernateUtil.getObjectList(WmBin.class, flt.getFilterList(), 0, true, "binCode");
		return binList;
	}

	public WmBin getBinByBarcode(String binBarcode)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("binCode", binBarcode));
		WmBin bin = (WmBin)HibernateUtil.getUniqObject(WmBin.class, restList, false);
		if(!(bin instanceof WmBin)) return null;

		return bin;
	}

	public int getMaxSectionLevel(Long sectionId){

		int maxSectionLevel = ((Long)HibernateUtil.getSession().createQuery("select max(levelNo) from WmBin where sectionId = :sectionId")
				.setParameter("sectionId", sectionId).uniqueResult()).intValue();

		return maxSectionLevel;
	}

	public int getDepthCount(String binbarcode){
		WmBin bin = getBinByBarcode(binbarcode);
		if(!(bin instanceof WmBin)) return 0;
		return bin.getDepthCount()==null?0:bin.getDepthCount().intValue();

	}


	@SuppressWarnings("unchecked")
	public List<WmBin> getDokList(){
		Criteria dokList = session.createCriteria(WmBin.class);
		dokList.add(Restrictions.eq("areaCode", DefaultValue.INCOME_AREA_CODE));
		dokList.addOrder(Order.asc("binCode"));
		return (List<WmBin>)dokList.list();

	}

	public WmBin getDokByNumber(int dok_no){
		Criteria criteria = session.createCriteria(WmBin.class);
		criteria.add(Restrictions.eq("binTypeId", DefaultValue.DOK_BIN_TYPE));

		String binNum = String.valueOf((1000+dok_no)).substring(1);

		criteria.add(Restrictions.ilike("binCode", "%."+binNum+".01" ));
		return (WmBin) criteria.uniqueResult();
	}

	public WmBinType getBinTypeById(Long binTypeId)
	{
	 	Criteria criteria = HibernateUtil.getSession().createCriteria(WmBinType.class);
	 	criteria.add(Restrictions.eq("binTypeId", binTypeId));
	 	criteria.setMaxResults(1);
	 	WmBinType binType = (WmBinType)criteria.uniqueResult();
		if(!(binType instanceof WmBinType)) return null;

		return binType;
	}

	public List<WmBinType> getBinTypeList(){
		return HibernateUtil.getObjectList(WmBinType.class, null, 0, false,"");
	}

}

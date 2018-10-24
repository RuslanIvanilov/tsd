package ru.defo.managers;

import java.util.List;

import ru.defo.filters.CriterionFilter;
import ru.defo.filters.VemptyBinFilter;
import ru.defo.model.views.Vemptybin;
import ru.defo.util.HibernateUtil;

public class VEmptyBinManager extends ManagerTemplate {


	 public List<Vemptybin> getListAll(){
		 CriterionFilter flt = new CriterionFilter();
		 List<Vemptybin> emptyBinList = HibernateUtil.getObjectList(Vemptybin.class, flt.getFilterList(), 0, false,"");

		 return emptyBinList;
	 }

	 public List<Vemptybin> getList(VemptyBinFilter filter){
		 CriterionFilter flt = new CriterionFilter();
		 	flt.addFilter("areaCode", filter.getAreaCode(), "like");
		  	flt.addFilter("rackNo", filter.getRackNo(), "<>");
		  	flt.addFilter("levelNo", filter.getLevelNo(), "<>");
		 	flt.addFilter("binCode", filter.getBinCode(), "like");

		 List<Vemptybin> emptyBinList = HibernateUtil.getObjectList(Vemptybin.class, flt.getFilterList(), filter.getRowCount(), false,"");

		 return emptyBinList;
	 }

}

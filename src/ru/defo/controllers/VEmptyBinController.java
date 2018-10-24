package ru.defo.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import ru.defo.filters.VemptyBinFilter;
import ru.defo.managers.VEmptyBinManager;
import ru.defo.model.views.Vemptybin;

public class VEmptyBinController {

	public List<Vemptybin> getList(HttpSession session){
		int rowCnt = 0;

		VemptyBinFilter flt = new VemptyBinFilter();
		if(session.getAttribute("areacodeflt") != null) flt.setAreaCode(session.getAttribute("areacodeflt").toString());
		if(session.getAttribute("racknoflt") != null) flt.setRackNo(session.getAttribute("racknoflt").toString());
		if(session.getAttribute("levelnoflt") != null) flt.setLevelNo(session.getAttribute("levelnoflt").toString());
		if(session.getAttribute("bincodeflt") != null) flt.setBinCode(session.getAttribute("bincodeflt").toString());

		if(session.getAttribute("rowcount") != null) {
			try{
				rowCnt  = Integer.decode(session.getAttribute("rowcount").toString());
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		flt.setRowCount(rowCnt);

		List<Vemptybin> emptyBinList = new VEmptyBinManager().getList(flt);

		return emptyBinList;
	}

}

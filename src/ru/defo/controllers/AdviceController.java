package ru.defo.controllers;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import ru.defo.filters.AdvicePosFilter;
import ru.defo.managers.AdviceManager;
import ru.defo.managers.BinManager;
import ru.defo.managers.CarManager;
import ru.defo.managers.VAdviceManager;
import ru.defo.model.WmAdvice;
import ru.defo.model.WmAdvicePos;
import ru.defo.model.views.Vadvice;
import ru.defo.util.ErrorMessage;

public class AdviceController {

	 AdviceManager advMgr;

	 public AdviceController(){
		 advMgr = new AdviceManager();
	 }


	 public String delAdvice(String adviceIdTxt){

		 Long adviceId = Long.valueOf(adviceIdTxt);
			WmAdvice advice = getAdviceById(adviceId);
			if(advice == null)
				return ErrorMessage.ORDER_NOT_EXISTS.message(new ArrayList<String>(Arrays.asList(adviceIdTxt)));

			if(advice.getStatusId() != 1L)
				return ErrorMessage.WRONG_ORDER_STATUS4DEL.message(new ArrayList<String>(Arrays.asList(advice.getStatus().getDescription())));

			advMgr.delAdvice(advice);

			return null;
		}


	public List<WmAdvicePos> getAdvicePosList(HttpSession sessionH)
	{
			AdvicePosFilter flt = new AdvicePosFilter();

			Long adviceId = Long.valueOf(sessionH.getAttribute("adviceid").toString());
			flt.setAdviceId(adviceId);
			flt.setPosflt(sessionH.getAttribute("posflt").toString());
			flt.setArticleidflt(sessionH.getAttribute("articleidflt").toString());
			flt.setArtdescrflt(sessionH.getAttribute("artdescrflt").toString());
			flt.setSkuflt(sessionH.getAttribute("skuflt").toString());
			flt.setExpqtyflt(sessionH.getAttribute("expqtyflt").toString());
			flt.setFactqtyflt(sessionH.getAttribute("factqtyflt").toString());
			//flt.setCtrlqtyflt(sessionH.getAttribute("ctrlqtyflt").toString());
			flt.setStatusflt(sessionH.getAttribute("statusposflt").toString());
			flt.setVendoridflt(sessionH.getAttribute("vendoridflt").toString());

			List<WmAdvicePos> advicePoslist = new AdviceManager().getAdvicePosList(flt);
			if(advicePoslist==null) return null;

			return advicePoslist;
	}

	public WmAdvice getAdviceByAdviceCode(String adviceCode)
	{
		WmAdvice advice =  new AdviceManager().getAdviceByAdviceCode(adviceCode);
		if(!(advice instanceof WmAdvice)) return null;
		return advice;
	}

	public WmAdvice getAdviceByAdviceId(Long adviceId)
	{
			WmAdvice advice =  new AdviceManager().getAdviceById(adviceId);
			if(!(advice instanceof WmAdvice)) return null;
			return advice;
	}

	 public Long getNextAdviceId(){
			return advMgr.getNextAdviceId();
		}

	 public List<WmAdvice> getAdviceListByFilter(String codeFilter)
	 {
		 return advMgr.getAdviceListByClientDocFilter(codeFilter);
	 }

	 public List<Vadvice> getVAdviceListByClientDocFilter(String codeFilter)
	 {
		 return new VAdviceManager().getVAdviceListByClientDocFilter(codeFilter);
	 }

	 public List<Vadvice> getVAdviceListByAdviceCodeFilter(String codeFilter)
	 {
		 return new VAdviceManager().getVAdviceListByAdviceCodeFilter(codeFilter);
	 }

	 public List<Vadvice> getVAdviceListByCarFilter(String codeFilter)
	 {
		 return new VAdviceManager().getVAdviceListByCarFilter(codeFilter);
	 }

	 public WmAdvice getAdviceById(Long adviceId){
		 return advMgr.getAdviceById(adviceId);
	 }

	 public Long getStatusIdByAdviceId(Long adviceId){
		 return new AdviceManager().getAdviceById(adviceId).getStatusId();
	 }

	 public boolean setStartAdvice(String adviceIdtxt){
		 Long adviceId = Long.decode(adviceIdtxt);

		 if(getStatusIdByAdviceId(adviceId) < 2){
			new AdviceManager().setNewAdviceStatus(adviceId, 2L);
			return true;
		 }
		 return false;
	 }

	 public void setAdviceDok(Long adviceId, String binCode){
		 Long binId = new BinManager().getBinByBarcode(binCode).getBinId();
		 advMgr.setAdviceDok(adviceId, binId);
	 }

	 public void setAdviceDok(Long adviceId, int dokno){
		 Long binId =  new BinManager().getDokByNumber(dokno).getBinId();
		 advMgr.setAdviceDok(adviceId, binId);
	 }

	 public void setAdviceCar(Long adviceId, String carCode){
		 Long carId = new CarManager().getCarByCode(carCode).getCarId();
		 advMgr.setAdviceCar(adviceId, carId);
	 }


	 public List<Vadvice> getAdviceList(boolean activeOnly, String dateFilter, String docFilter, String statusFilter,  String carFilter, String dokFilter, String commentFilter, String errorFilter) throws ParseException{

		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		 if(dateFilter.isEmpty())
			 dateFilter = "2000-01-01";

     	 Date date = format.parse(dateFilter);

		 return new VAdviceManager().getAdviceList(activeOnly, new Timestamp(date.getTime()), docFilter, statusFilter, carFilter, dokFilter, commentFilter, errorFilter);
	 }

}

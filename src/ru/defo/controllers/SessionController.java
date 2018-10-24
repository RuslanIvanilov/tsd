package ru.defo.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.model.WmArticle;
import ru.defo.model.WmSku;
import ru.defo.model.views.Vadvice;
import ru.defo.servlets.Login; 

public class SessionController {

	public static void check(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		if(request.getSession().getAttribute("userid")==null){
			request.getRequestDispatcher((Login.class).getSimpleName()).forward(request, response);
			return;
		}
	}

	public void fillArticleSku(HttpSession session, WmArticle article, WmSku sku){
		session.setAttribute("articlecode", article.getArticleCode());
		session.setAttribute("articleid", article.getArticleId());
		session.setAttribute("articlename", article.getDescription());

		session.setAttribute("skuid", sku.getSkuId());
		session.setAttribute("skuname", sku.getDescription());
	}

	public void initInvOrderSku(HttpSession session){
		session.setAttribute("datalist", null);
		session.setAttribute("articlelist", null);
		session.setAttribute("quantity", null);
		session.setAttribute("articlecode", null);
		session.setAttribute("articleid", null);
		session.setAttribute("articlename", null);
		session.setAttribute("skuname", null);
		session.setAttribute("skuid", null);

	}

	public void initCreateInventory(HttpSession session)
	{
		session.setAttribute("areatxt", null);
		session.setAttribute("rackstart", null);
		session.setAttribute("rackend", null);
		session.setAttribute("levelstart", null);
		session.setAttribute("levelend", null);
		session.setAttribute("binstart", null);
		session.setAttribute("binend", null);
		session.setAttribute("articletxt", null);
	}

	public static void clear(HttpSession session)
	{
		session.setAttribute("SourceClassName",null);
		session.setAttribute("Title",null);
		session.setAttribute("SubmitAction", null);
		session.setAttribute("HeaderCaption", null);
		session.setAttribute("TopText",null);
		session.setAttribute("CenterText",null);
		session.setAttribute("CancelName",null);
		session.setAttribute("CancelAction", null);
	}

	public void initUnitPrint(HttpSession session){
		session.setAttribute("unitqty", null);
		session.setAttribute("unitnext", null);
	}

	public void initPreAdviceList(HttpSession session){

	}

	public void initAdviceDoc(HttpSession session){

	}

	public void initArticleCard(HttpSession session){
		session.setAttribute("descrflt", null);
		session.setAttribute("isbaseflt", null);
		session.setAttribute("weightflt", null);
		session.setAttribute("lengthflt", null);
		session.setAttribute("widthflt", null);
		session.setAttribute("heighflt", null);
		session.setAttribute("crushflt", null);
	}

	public void initPick(HttpSession session){
		session.setAttribute("vndgroupname", null);
		session.setAttribute("vndgroup", null);
		session.setAttribute("ordercode", null);
		session.setAttribute("selectedbin", null);
		session.setAttribute("articlename", null);
		session.setAttribute("articlecode", null);
		session.setAttribute("articleid", null);
		session.setAttribute("srcunitcode", null);
	}


	public void initAttributes(HttpSession session){
		session.setAttribute("unitid", "");
		session.setAttribute("unitcode", "");
		session.setAttribute("articlecode", "");
		session.setAttribute("articlename", "");
		session.setAttribute("quantity", "");
		session.setAttribute("skuname", "");
		session.setAttribute("skuid", "");
		session.setAttribute("qualityname", "");
		session.setAttribute("quantid", "");
		session.setAttribute("bincode","");
		//session.setAttribute("binlevel","");
		session.setAttribute("servlet_name","");
		session.setAttribute("state","");
		session.setAttribute("backpage","");
		session.setAttribute("savepage","");
		session.setAttribute("placescount","");
		session.setAttribute("unitcount","");
		//session.setAttribute("module","");
	}

	public void initUnitAttributes(HttpSession session){
		session.setAttribute("articlecode", "");
		session.setAttribute("articlename", "");
		session.setAttribute("quantity", "");
		session.setAttribute("skuname", "");
		session.setAttribute("skuid", "");
		session.setAttribute("qualityname", "");
		session.setAttribute("quantid", "");
		session.setAttribute("state","");
		session.setAttribute("backpage","");
		session.setAttribute("savepage","");
		session.setAttribute("placescount","");
		session.setAttribute("unitcount","");
	}

	public void initAll(HttpSession session){
		session.setAttribute("bincodeblock", null);
		session.setAttribute("countCartons", null);
		session.setAttribute("frompage", null);
		session.setAttribute("barcode", null);
		session.setAttribute("unitid", null);
		session.setAttribute("unitcode", null);
		session.setAttribute("articlecode", null);
		session.setAttribute("articleid", null);
		session.setAttribute("articlename", null);
		session.setAttribute("quantity", null);
		session.setAttribute("expquantity", null);
		session.setAttribute("skuname", null);
		session.setAttribute("skuid", null);
		session.setAttribute("qualityname", null);
		session.setAttribute("quantid", null);
		session.setAttribute("bincode",null);
		session.setAttribute("level", null);
		session.setAttribute("mirrored",null);
		session.setAttribute("binlevel",null);
		session.setAttribute("servlet_name",null);
		session.setAttribute("state",null);
		session.setAttribute("backpage",null);
		session.setAttribute("savepage",null);
		session.setAttribute("actionpage", null);
		session.setAttribute("placescount",null);
		session.setAttribute("unitcount",null);
		session.setAttribute("module",null);
		session.setAttribute("saved", null);
		session.setAttribute("blkunit", null);
		session.setAttribute("message",null);
		session.setAttribute("articlecount", null);
		session.setAttribute("action", null);
		session.setAttribute("continue_page", null);
		session.setAttribute("articlecount", null);
		session.setAttribute("articlefilter", null);
		session.setAttribute("assign", null);
		session.setAttribute("printer_ip", null);
		session.setAttribute("bin_comment", null);
		session.setAttribute("unitsrc", null);
		session.setAttribute("unitdest", null);
		session.setAttribute("stlitedqty", null);
		session.setAttribute("quantid", null);
		session.setAttribute("adviceId", null);
		session.setAttribute("adviceTypeId", null);
		session.setAttribute("adviceCode", null);
		session.setAttribute("expectedDate", null);
		session.setAttribute("carId", null);
		session.setAttribute("placeCount", null);
		session.setAttribute("statusId", null);
		session.setAttribute("clientDocCode",null);
		session.setAttribute("type", null);
		session.setAttribute("carCode", null);
		session.setAttribute("carMark", null);
		session.setAttribute("carModel", null);
		session.setAttribute("binCode", null);
		session.setAttribute("status", null);
		session.setAttribute("carfilter", null);
		session.setAttribute("adviceunitplace", null);
		session.setAttribute("state", null);
		session.setAttribute("adviceposid", null);
		session.setAttribute("scanmap", null);
		session.setAttribute("actiondel", null);
		session.setAttribute("shipmentflt", null);
		session.setAttribute("qsflt", null);
		session.setAttribute("unitflt", null);
		session.setAttribute("binflt", null);
		session.setAttribute("qtyflt", null);
		session.setAttribute("userflt", null);
		session.setAttribute("dateflt", null);
		session.setAttribute("question",null);
		session.setAttribute("clientdescr",null);
		session.setAttribute("startloader", null);
		session.setAttribute("preparedcnt",  null);
		session.setAttribute("shippedcnt", null);
		session.setAttribute("skubc", null);
		session.setAttribute("save_mode", null);
		session.setAttribute("surnametxt",null);
		session.setAttribute("positiontxt",null);
		session.setAttribute("firstnametxt",null);
		session.setAttribute("logintxt", null);
		session.setAttribute("patronymtxt", null);
		session.setAttribute("birthtxt", null);
		session.setAttribute("blockedtxt", null);
		session.setAttribute("emailtxt", null);
		session.setAttribute("pwdtxt", null);
		session.setAttribute("barcodetxt", null);
		session.setAttribute("permcodeflt", null);
		session.setAttribute("desriptionflt", null);
		session.setAttribute("typeflt", null);
		session.setAttribute("createrflt", null);
		session.setAttribute("createdateflt",null);
		session.setAttribute("inventoryid", null);
		session.setAttribute("inventoryposid", null);

	}

	public void fillVAdviceSession(Vadvice vAdvice, HttpSession session)
	{
		session.setAttribute("adviceId", vAdvice.getAdviceId());
		session.setAttribute("adviceTypeId", vAdvice.getAdviceTypeId());
		session.setAttribute("adviceCode", vAdvice.getAdviceCode());
		session.setAttribute("expectedDate", vAdvice.getExpectedDate());
		session.setAttribute("carId", vAdvice.getCarId());
		session.setAttribute("placeCount", vAdvice.getPlaceCount());
		session.setAttribute("statusId", vAdvice.getStatusId());
		session.setAttribute("clientDocCode", vAdvice.getClientDocCode());
		session.setAttribute("type", vAdvice.getType());
		session.setAttribute("carCode", vAdvice.getCarCode());
		session.setAttribute("carMark", vAdvice.getCarMark());
		session.setAttribute("carModel", vAdvice.getCarModel());
		session.setAttribute("binCode", vAdvice.getBinCode());
		session.setAttribute("status", vAdvice.getStatus());
	}

	public void cancelAdviceArticle(HttpSession session){
		session.setAttribute("articleid", null);
		session.setAttribute("articlecode", null);
		session.setAttribute("skuname", null);
		session.setAttribute("skuid", null);
		session.setAttribute("articlename", null);
		session.setAttribute("barcode", null);
		session.setAttribute("quantity", null);
		session.setAttribute("expquantity", null);
		session.setAttribute("qualityname", null);
		session.setAttribute("qualitystateid", null);
		session.setAttribute("adviceposid", null);
	}

	public void initControlScan(HttpSession session){
		session.setAttribute("articlecode", null);
		session.setAttribute("articlename", null);
		session.setAttribute("skuname", null);
		session.setAttribute("quantity", null);
	}

	public void initGUIadvList(HttpSession session){
		session.setAttribute("date_filter", null);
		session.setAttribute("doc_filter", null);
		session.setAttribute("status_filter", null);
		session.setAttribute("car_filter", null);
		session.setAttribute("dok_filter", null);
		session.setAttribute("comment_filter", null);
		session.setAttribute("error_filter", null);
	}

	public void initArticleQtyList(HttpSession session){
		session.setAttribute("backpage", null);
		session.setAttribute("articleFilter", null);
		session.setAttribute("articleDescFilter", null);
		session.setAttribute("qtyfilter", null);
		session.setAttribute("articleid", null);
		session.setAttribute("articleid", null);
		session.setAttribute("unitflt", null);
		session.setAttribute("binflt", null);
		session.setAttribute("areaflt", null);
		session.setAttribute("rackflt", null);
		session.setAttribute("levelflt", null);
		session.setAttribute("qtyflt", null);
		session.setAttribute("skuflt", null);
		session.setAttribute("qstateflt", null);
		session.setAttribute("needchkflt", null);
		session.setAttribute("crdateflt", null);
		session.setAttribute("surnameflt", null);
		session.setAttribute("fstnameflt", null);
		session.setAttribute("advflt", null);
		session.setAttribute("advposflt", null);
		session.setAttribute("expdateflt", null);
		session.setAttribute("groupflt", null);
		session.setAttribute("articleflt", null);
		session.setAttribute("artnameflt", null);

	}

	public void initUnitQuantList(HttpSession session){
		session.setAttribute("articleid", null);
	}

	public void initShipmentList(HttpSession session){
		session.setAttribute("shipmentflt", null);
		session.setAttribute("dateflt", null);
		session.setAttribute("showbc", null);
		session.setAttribute("dellink", null);
	}

	public void initRouteList(HttpSession session){
		session.setAttribute("routeflt", null);
		session.setAttribute("dateflt", null);
		//session.setAttribute("chCollapsedFlt",null);

		session.setAttribute("printflt", null);
		session.setAttribute("commentflt", null);
	}

	public void initPickList(HttpSession session){
		session.setAttribute("qsflt", null);
		session.setAttribute("unitflt", null);
		session.setAttribute("binflt", null);
		session.setAttribute("qtyflt", null);
		session.setAttribute("userflt", null);
		session.setAttribute("dateflt", null);
	}

	public void initPrintDayList(HttpSession session){
		session.setAttribute("reportdate", null);
		session.setAttribute("vgname", null);
		session.setAttribute("routelist",null);
	}

	public void initLogin(HttpSession session){
		System.out.println("SessionController::initLogin");
		session.setAttribute("userid", null);
	}

}

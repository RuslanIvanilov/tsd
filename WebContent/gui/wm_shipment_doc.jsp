<%@page import="ru.defo.util.HibernateUtil"%>
<%@page import="ru.defo.model.WmVendorGroup"%>
<%@page import="ru.defo.model.views.Vorderpos"%>
<%
	System.out.println("GUI/wm_shipment_doc.jsp");
%>

<%@page import="java.util.*"%>
<%@page import="ru.defo.model.views.Vorderpos"%>
<%@page import="ru.defo.model.WmOrder"%>
<%@page import="ru.defo.model.WmStatus"%>
<%@page import="ru.defo.model.WmOrderType"%>
<%@page import="ru.defo.controllers.OrderController"%>
<%@page import="ru.defo.controllers.PreOrderController"%>
<%@page import="ru.defo.controllers.ArticleController"%>
<%@page import="ru.defo.controllers.PermissionController"%>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<!-- <meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate'> -->
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>

<title>WMS Дэфо Складская Отгрузка</title>



<script>
function moveToFactQtyDetails(orderid, orderposid){
	document.location="${pageContext.request.contextPath}/gui/PickList?orderid="+orderid+"&orderposid="+orderposid;
}

function moveToCtrlQtyDetails(orderid, orderposid){
	document.location="${pageContext.request.contextPath}/gui/CheckList?orderid="+orderid+"&orderposid="+orderposid;
}

function setfilter(){
	document.location="${pageContext.request.contextPath}/gui/ShipmentDoc?orderid=${orderid}"
							 +"&posflt="+document.getElementById('posflt').value
							 +"&articleidflt="+document.getElementById('articleidflt').value.replace("#","%23").replace("@","%40").replace("+","%2B").replace("*","%2A")
							 +"&artdescrflt="+document.getElementById('artdescrflt').value
							 +"&skuflt="+document.getElementById('skuflt').value
							 +"&expqtyflt="+document.getElementById('expqtyflt').value
							 +"&factqtyflt="+document.getElementById('factqtyflt').value
							 +"&ctrlqtyflt="+document.getElementById('ctrlqtyflt').value
							 +"&statusposflt="+document.getElementById('selectsts').options[document.getElementById('selectsts').selectedIndex].value
							 +"&showbc="+document.getElementById('showbc').value
							 +"&vendoridflt="+document.getElementById('selectvendor').options[document.getElementById('selectvendor').selectedIndex].value;
}

function goBack(){
	document.location.href='${pageContext.request.contextPath}/${backpage}'
	//history.back()
}

function toDel(orderCode){
	if(confirm('Удалить этот документ складской приемки?'))
		document.location.href='${pageContext.request.contextPath}/${delpage}';
}

function switchArticleBC(pShowBc){

	if(pShowBc===1){
		document.getElementById('showbc').value = 0;
	} else {
		document.getElementById('showbc').value = 1;
	}


	setfilter();
}

function getShowBc(){
	return document.getElementById('showbc').value;
}

</script>



</head>
<body>

<h3>Складская отгрузка</h3>
<pre>
<input type="button" id="btnHome" title="В главное меню" value="&#x1f3e0" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'"> <input type="button" id="btnBack" value="Назад" onclick="goBack()">		  	     <%if(session.getAttribute("userid")!=null && new PermissionController().hasPerm4WmOrderDelete(session.getAttribute("userid").toString())) {%> <input type="button" id="btnDel" value="Удалить" onclick="toDel()"> <% } %>
</pre>
<form>
<%
WmOrder order = null;

if(session.getAttribute("orderid") != null){
	Long ordId = Long.valueOf(session.getAttribute("orderid").toString());
 	order = new OrderController().getOrderByOrderId(ordId);
 	//order = HibernateUtil.getSession().get(WmOrder.class, ordId);
 	if(order != null) session.setAttribute("ordercode",order.getOrderCode());

%>
<!-- <p style= "font-family: UpcBwrP72Tt"><%=order==null?"":order.getOrderCode()%></p>   -->

<!--  -->

<table id="docheader" border="0px">
<tr>
	<td align="left"><%= order.getOrderType()==null?"...":order.getOrderType().getDescription()%> Но.</td>
	<td><%=order==null?"":order.getOrderCode()%></td>
	<td title="<%=order==null?"":order.getOrderId()%>" align="left">
	<img class="img-responsive"
src="http://toolki.com/en/barcode/?foreground=000000&amp;code=<%=order==null?"":order.getOrderCode()%>&amp;font_size=0&amp;module_width=0.6&amp;module_height=10.0&amp;text_distance=5.0&amp;background=FFFFFF&amp;quiet_zone=6.5&amp;center_text=True&amp;type=ean13"
alt="">
	</td>
</tr>
<tr>
	<td align="left">Дата Отгрузки</td><td align="left"><%=order.getExpectedDate()==null?"...":order.getExpectedDate().toLocaleString().substring(0, 10)%></td>
	<td><%=new PreOrderController().getClientDocDescription(order.getOrderId()) %></td>
</tr>
<tr>
	<td align="left">Статус</td><td align="left"><%=order.getStatus()==null?"...":order.getStatus().getDescription()%></td>
</tr>
<tr>
	<td align="left">Док</td><td align="left"><%=order==null?"":(order.getBin()==null?"":order.getBin().getBinCode())%></td>
	<td align="right">
	<% if(new PermissionController().harPerm4ShowBC(session.getAttribute("userid").toString())) {%>
	<input type="button" id="btnBCshow" value="штрих-кода товаров" onclick="switchArticleBC(${showbc})">
	<% } %>
	</td>

</tr>
</table>
<%
}
%>

<br>

<table id="docline" border="1px">
<tr bgcolor="#DCDCDC">
	<td align="center">#</td>
	<td align="center"><b>Строка</b></td>
	<td align="center"><b>Товар</b></td>
	<% if( session.getAttribute("showbc").toString().contains("1")){ %>
		<td align="center"><b>Штрих-код</b></td>
	<% } %>
	<td align="center"><small><b>Наименование</b></small></td>
	<td align="center"><b>Упаковка</b></td>
	<td align="center"><small><b>Ожид. кол-во</b></small></td>
	<td align="center"><small><b>Факт. кол-во</b></small></td>
	<td align="center"><small><b>Контроль кол-во</b></small></td>
	<td align="center"><b>Статус</b></td>
	<td align="center">Группа</td>
</tr>

<tr>
<td></td>
<td><input size="4" style="border : none" type="text" id="posflt" onchange="setfilter()" value="<%=session.getAttribute("posflt")%>"></td>
<td><input style="border : none" type="text" id="articleidflt" onchange="setfilter()" value="<%=session.getAttribute("articleidflt")%>"></td>
<% if( session.getAttribute("showbc").toString().contains("1")){ %>
		<td></td>
	<% } %>
<td><input style="border : none" type="text" id="artdescrflt" onchange="setfilter()" value="<%=session.getAttribute("artdescrflt")%>"></td>
<td><input size="1" style="border : none" type="text" id="skuflt" onchange="setfilter()" value="<%=session.getAttribute("skuflt")%>"></td>
<td><input size="1" style="border : none" type="text" id="expqtyflt" onchange="setfilter()" value="<%=session.getAttribute("expqtyflt")%>"></td>
<td><input size="1" style="border : none" type="text" id="factqtyflt" onchange="setfilter()" value="<%=session.getAttribute("factqtyflt")%>"></td>
<td><input size="1" style="border : none" type="text" id="ctrlqtyflt" onchange="setfilter()" value="<%=session.getAttribute("ctrlqtyflt")%>"></td>
<td>
<select style="border : none" id="selectsts" onchange="setfilter()">
		<option value="" class='option'></option>
	<%
	System.out.println("statusList ОТ: "+new Date().toGMTString());
	Long statusIdFlt = 0L;
	List<WmStatus> statusList = new PreOrderController().getStatusList();
	for(WmStatus status : statusList){

		if(!session.getAttribute("statusposflt").toString().isEmpty()){
			statusIdFlt = Long.valueOf(session.getAttribute("statusposflt").toString());
		}

		if(statusIdFlt==status.getStatusId())	{
		%>
			<option selected value='<%=status.getStatusId()%>' class='option'><%=status.getDescription()%></option>
		<% } else { %>
			<option value='<%=status.getStatusId()%>' class='option'><%=status.getDescription()%></option>
		<% } %>
	<%
	}
	System.out.println("statusList ДО: "+new Date().toGMTString());
	%>
	</select>
</td>
<td><select style="border : none" id="selectvendor" onchange="setfilter()">
		<option value="" class='option'></option>
		<!-- <option value="-1" class='option'><пусто></option>  -->
	<%
	System.out.println("vendorGroupList ОТ: "+new Date().toGMTString());
	Long vendorIdFlt = 0L;
	List<WmVendorGroup> vendorGroupList = new ArticleController().getVendorGroupList();

	vendorGroupList.add(0,new WmVendorGroup(-1L, "<пусто>"));
	for(WmVendorGroup vendorGroup : vendorGroupList){

		if(!session.getAttribute("vendoridflt").toString().isEmpty()){
			vendorIdFlt = Long.valueOf(session.getAttribute("vendoridflt").toString());
		}

		if(vendorIdFlt==vendorGroup.getVendorGroupId())	{
		%>
			<option selected value='<%=vendorGroup.getVendorGroupId()%>' class='option'><%=vendorGroup.getDescription()%></option>
		<% } else { %>
			<option value='<%=vendorGroup.getVendorGroupId()%>' class='option'><%=vendorGroup.getDescription()%></option>
		<% } %>
	<%
	}
	System.out.println("vendorGroupList ДО: "+new Date().toGMTString());
	%>
	</select>
</tr>

<%
	ArticleController artCtrl = new ArticleController();
	int i = 0;
	int countExpQty = 0;
	int countCtrlQty = 0;
	List<Vorderpos> ordposList = null;
	System.out.println("VOrderPosList ОТ: "+new Date().toGMTString());
	if(session.getAttribute("orderid") != null)
		ordposList = new OrderController().getVOrderPosList(session);
/*
		ordposList = new OrderController().getVOrderPosList(session.getAttribute("orderid").toString(),
															session.getAttribute("posflt").toString(),
															session.getAttribute("articleidflt").toString(),
															session.getAttribute("artdescrflt").toString(),
															session.getAttribute("skuflt").toString(),
															session.getAttribute("expqtyflt").toString(),
															session.getAttribute("factqtyflt").toString(),
															session.getAttribute("statusposflt").toString(),
															session.getAttribute("vendoridflt").toString());
*/
	System.out.println("VOrderPosList ДО: "+new Date().toGMTString());
	if(ordposList != null)
	{
		System.out.println("ordposList ОТ: "+new Date().toGMTString());
		for(Vorderpos vordpos : ordposList){
			i++;
			countExpQty+=vordpos.getExpectQuantity()==null?0:vordpos.getExpectQuantity();
			countCtrlQty+=vordpos.getCtrlQuantity()==null?0:vordpos.getCtrlQuantity();
%>
<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
   <td align="center"><small><%=i %></small></td>
   <td align="center" title="OrderId : <%=vordpos.getOrderId()%> Line : <%=vordpos.getOrderPosId()%>"><%=vordpos.getOrderPosId()==null?"":vordpos.getOrderPosId()%></td>
   <td align="center"><%=vordpos.getArticleCode()==null?"":vordpos.getArticleCode()%></td>
   <% if(session.getAttribute("showbc").toString().contains("1")){ %>
		<!-- <td align="center"><small><%=artCtrl.getBcByArticleId(vordpos.getArticleId()) ==null?"":artCtrl.getBcByArticleId(vordpos.getArticleId())%></small></td> -->
		<td align="center" title='<%=artCtrl.getBcByArticleId(vordpos.getArticleId()) ==null?"":artCtrl.getBcByArticleId(vordpos.getArticleId())%>'>

			<!-- <img class="img-responsive"
src="http://toolki.com/en/barcode/?foreground=000000&amp;code=<%=artCtrl.getBcByArticleId(vordpos.getArticleId()) ==null?"":artCtrl.getBcValueByArticleId(vordpos.getArticleId()) %>&amp;font_size=0&amp;module_width=0.6&amp;module_height=10.0&amp;text_distance=5.0&amp;background=FFFFFF&amp;quiet_zone=6.5&amp;center_text=True&amp;type=ean13"
alt="">  -->

<img class="img-responsive" src="http://toolki.com/en/barcode/?foreground=000000&amp;code=<%=artCtrl.getBcValueByArticleId(vordpos.getArticleId())%>;font_size=0&amp;module_width=0.4&amp;module_height=6.0&amp;text_distance=1.0&amp;background=FFFFFF&amp;quiet_zone=6.5&amp;center_text=True&amp;type=code39" alt="">

		</td>

	<% } %>
   <td align="left"><small><%=vordpos.getArticleName()==null?"":vordpos.getArticleName()%></small></td>
   <td align="center"><small><%=vordpos.getSkuName()==null?"":vordpos.getSkuName()%></small></td>
   <td align="right"><small><%=vordpos.getExpectQuantity()==null?"":vordpos.getExpectQuantity()%></small></td>
   <%
   if(vordpos.getFactQuantity()!=null)
   {   %>
   <td align="right" onclick="moveToFactQtyDetails(<%=vordpos.getOrderId()%>, <%=vordpos.getOrderPosId()%>)"><small><%=vordpos.getFactQuantity()==0?"":vordpos.getFactQuantity()%></small></td>
   <% } else { %>
   <td align="right"><small></small></td>
   <% } %>

   <%
   if(vordpos.getCtrlQuantity()!=null)
   {   %>
   <td align="right" onclick="moveToCtrlQtyDetails(<%=vordpos.getOrderId()%>, <%=vordpos.getOrderPosId()%>)"><small><%=vordpos.getCtrlQuantity()==0?"":vordpos.getCtrlQuantity()%></small></td>
   <% } else { %>
   <td align="right"><small></small></td>
   <% } %>


   <td align="center"><small><%=vordpos.getStatusName()==null?"":vordpos.getStatusName()%></small></td>

   <td align="center"><small><%=vordpos.getVendorGroupName()==null?"":vordpos.getVendorGroupName()%></small></td>
<% 		}
		System.out.println("ordposList ДО: "+new Date().toGMTString());
	}
%>
</table>
   <p>Всего количество Ожидаемое: <%=countExpQty%> , а Проконтролированное: <%=countCtrlQty%> </p>
</form>

<input type="hidden" id="showbc" value="${showbc}">

</body>
</html>
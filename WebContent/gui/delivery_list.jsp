<%
	System.out.println("GUI/delivery_list.jsp");
System.out.println("chCollapsedFlt : "+session.getAttribute("chCollapsedFlt").toString() );
%>

<%@page import="java.util.*"%>
<%@page import="ru.defo.model.views.Vroute"%>
<%@page import="java.time.format.*"%>

<%@page import="ru.defo.model.WmPreOrder"%>
<%@page import="ru.defo.model.WmRoute"%>
<%@page import="ru.defo.model.WmOrderType"%>
<%@page import="ru.defo.model.WmStatus"%>
<%@page import="ru.defo.model.WmCar"%>
<%@page import="ru.defo.model.WmVendorGroup"%>
<%@page import="ru.defo.managers.DeliveryManager"%>
<%@page import="ru.defo.managers.CarManager"%>
<%@page import="ru.defo.managers.PreOrderManager"%>
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
<meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>

<title>WMS Дэфо Доставки</title>
<script>
//var routelist = [];

function setfilter(routelist){

	//alert("in setfilter");

	//if(routelist != null)
	//for(var r=0; r<routelist.length; ++r){
	//	alert(r+" --> "+routelist[r]);
	//}
	//alert("in setfilter2");

	document.location="${pageContext.request.contextPath}/gui/DeliveryList?deliveryflt="+document.getElementById('deliveryflt').value
							 +"&dateflt="+document.getElementById('dateflt').value
							 +"&statusflt="+document.getElementById('statusflt').value
							 +"&rowcount="+document.getElementById('rowcount').value
							 +"&carcodeflt="+document.getElementById('carcodeflt').value
							 +"&carmarkflt="+document.getElementById('carmarkflt').value
							 +"&preordercntflt="+document.getElementById('preordercntflt').value
							 +"&orderlinkcntflt="+document.getElementById('orderlinkcntflt').value
							 +"&printflt="+document.getElementById('printflt').value
							 +"&commentflt="+document.getElementById('commentflt').value
							 +"&routelist[]="+routelist
							 +"&chCollapsedFlt="+document.getElementById('chCollapsedFlt').checked
							 ;
}

function setMarkedOnly(){
	var artlist = [];

	list = document.getElementById("routetable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			advlist.push(list[i].getElementsByTagName("td")[0].childNodes[0].id);
		}
	}
	setfilter();

}

function setMarkAll(){

	list = document.getElementById("routetable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		 if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			 document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).click();
		 } else {
		 	document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked = "true";
		 }
	}
}


function setMarked(routeId){
	//alert("is setMarked! ");
}


function createRouteList(toprint){
	var routelist = [];
	list = document.getElementById("routetable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			routelist.push(list[i].getElementsByTagName("td")[0].childNodes[0].id);
			document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).click();
		 }
	}
	if(toprint===""){
		setfilter(routelist);
	} else {
		OpenPrint(routelist);
	}
}

function OpenPrint(routelist){
	//alert("chCollapsedFlt : "+document.getElementById('chCollapsedFlt').checked)
	if(document.getElementById('selectvg').options[0].selected && document.getElementById('chUseTableFlt').checked==false){
		if(confirm("Не выбран поставщик! будут созданы ведомости доставки по всем группам поставщиков.")==false)
		return;
	}

	if(document.getElementById('dateflt').value===""){
		alert("Не отфильтрованы документы по дате!");
		return;
	}

	if(document.getElementById('chUseTableFlt').checked){
		window.open("${pageContext.request.contextPath}/gui/PrintDayList?carcodeflt="+document.getElementById('carcodeflt').value+"&dateflt="+document.getElementById('dateflt').value+"&chCollapsedFlt="+document.getElementById('chCollapsedFlt').checked );
		return;
	} else {

	window.open("${pageContext.request.contextPath}/gui/PrintDayList?selectvg="+document.getElementById('selectvg').options[document.getElementById('selectvg').selectedIndex].value
																						+"&dateflt="+document.getElementById('dateflt').value+"&routelist[]="+routelist+"&chCollapsedFlt="+document.getElementById('chCollapsedFlt').checked
				);
	routelist=null;
	//setfilter(routelist);
	}
}

function usetableflt(){
	if(document.getElementById('chUseTableFlt').checked){
		document.getElementById('selectvg').options[0].selected = true;
		document.getElementById('selectvg').disabled = true;
	}else {
		document.getElementById('selectvg').disabled = false;
	}
}

function collapsedFlt(){
	if(document.getElementById('chCollapsedFlt').checked){
		document.getElementById('selectvg').options[0].selected = true;
		document.getElementById('selectvg').disabled = true;
		document.getElementById('chUseTableFlt').checked = false;
		document.getElementById('chUseTableFlt').disabled = true;
	}else{
		document.getElementById('selectvg').disabled = false;
		document.getElementById('chUseTableFlt').disabled = false;
	}
}

</script>
</head>
<body>

<!-- <h3>Список доставок на отгрузку</h3>   -->
<!-- <p>  -->
<table>
<tr>
<td colspan="6">
<big>
<b>Список доставок на отгрузку</b>
</big>
</td>
</tr>
<tr>
<td>
<input type="button" id="btnHome" title="В главное меню" value="&#x1f3e0" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'">
<input type="button" id="btnBack" value="Назад" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'">
</td>
<td><pre>      </pre></td>
<td>
<input type="button" id="btnCreateOrds" value="Создать складские отгрузки" onclick='createRouteList("")'>
</td>
<td><pre>      </pre></td>
<td>
<select  id="selectvg">
		<option value="" class='option'><small>Выбор поставщика</small></option>
	<%

	Long vgFlt = 0L;
	List<WmVendorGroup> vgList = new ArticleController().getVendorGroupList();
	for(WmVendorGroup vendorGroup : vgList){
/*
		if(!session.getAttribute("vgflt").toString().isEmpty()){
			vgFlt = Long.valueOf(session.getAttribute("vgflt").toString());
		}
*/
		if(vgFlt==vendorGroup.getVendorGroupId())	{
		%>
			<option selected value='<%=vendorGroup.getVendorGroupId()%>' class='option'><%=vendorGroup.getDescription().toUpperCase()%></option>
		<% } else { %>
			<option value='<%=vendorGroup.getVendorGroupId()%>' class='option'><%=vendorGroup.getDescription().toUpperCase()%></option>
		<% } %>
	<%
	}
	%>
		<option value='<%=-1 %>' class='option'><неустановлена></option>
	</select>
</td>
<td>
<input type="button" id="btnOpenPrint" value="Отчет доставок на дату" onclick="createRouteList('1')">
</td>

</tr>

<tr>
<td colspan="4">&nbsp;</td>
<td colspan="2" align="left"><input type="checkbox" id="chUseTableFlt" onchange="usetableflt()">
<small><label for="chUseTableFlt">Использовать фильтр <b>"Машина Но."</b> для печати</label></small>
</td>
</tr>
<tr>
<td colspan="4">
<small>Показывать строк не более: </small><input type="text" id="rowcount" value="<%=session.getAttribute("rowcount")%>" onchange="setfilter()" size="2" onkeyup = 'this.value=parseInt(this.value) | 0'>
</td>
<td colspan="2" align="left"><input type="checkbox" id="chCollapsedFlt" onchange="collapsedFlt()" <%=session.getAttribute("chCollapsedFlt").toString().equals("true")?"checked='checked'":""%>>
<small><label for="chCollapsedFlt">Отчет без детализации по строкам</label></small>
</td>
</tr>
</table>
<!-- <small>Показывать строк не более: </small><input type="text" id="rowcount" value="<%=session.getAttribute("rowcount")%>" onchange="setfilter()" size="2" onkeyup = 'this.value=parseInt(this.value) | 0'> -->
<!-- </p>  -->
<br>
<form>
<table id="routetable" border="1px">
<tr bgcolor="#DCDCDC">
	<td align="center"> </td>
	<td align="center">#</td>
	<td align="center"><small><b>Дата доставки</b></small></td>
	<td align="center"><b>Доставка Но.</b></td>
	<td align="center"><b>Статус</b></td>
	<td align="center"><b>Машина Но.</b></td>
	<td align="center"><small><b>Марка машины</b></small></td>
	<td align="center"><small><b>Кол-во заказов</b></small></td>
	<td align="center"><small><b>Кол-во заказов<br>в отгрузке</b></small></td>
	<td align="center"><small><b>Печать</b></small></td>
	<td align="center"><small><b>Комментарии</b></small></td>
</tr>

<tr>
	<td align="center"><input type="button" id="mark_all" onclick="setMarkAll()"></td>
	<td></td>
	<td><input style="border : none"  			type="date" id="dateflt" 			onchange="setfilter()" value="<%=session.getAttribute("dateflt")%>"></td>
	<td><input style="border : none" size="14" 	type="text" id="deliveryflt" 		onchange="setfilter()" value="<%=session.getAttribute("deliveryflt")%>"></td>
	<td><input style="border : none" size="4" 	type="text" id="statusflt" 			onchange="setfilter()" value="<%=session.getAttribute("statusflt")%>"></td>
	<td><input Style="border : None" Size="9" 	Type="text" Id="carcodeflt" 		Onchange="setfilter()" Value="<%=session.getAttribute("carcodeflt")%>"></td>
	<td><input Style="border : None" Size="10" 	Type="text" Id="carmarkflt" 		Onchange="setfilter()" Value="<%=session.getAttribute("carmarkflt")%>"></td>
	<td><input Style="border : None" Size="8" 	Type="text" Id="preordercntflt" 	Onchange="setfilter()" Value="<%=session.getAttribute("preordercntflt")%>"></td>
	<td><input Style="border : None" Size="4" 	Type="text" Id="orderlinkcntflt" 	Onchange="setfilter()" Value="<%=session.getAttribute("orderlinkcntflt")%>"></td>
	<td><input style="border : none" Size="2"   type="text" id="printflt" 			onchange="setfilter()" value="<%=session.getAttribute("printflt")%>" title="Фильтры: Y, Д, 1 - только если есть комментарии; N, Н, 1 - только если нет комментариев "></td>
	<td><input style="border : none" size="4" 	type="text" id="commentflt" 		onchange="setfilter()" value="<%=session.getAttribute("commentflt")%>"></td>
</tr>


<%
    int i = 0;
	List<Vroute> routeList = Vroute.getVrouteList(session.getAttribute("dateflt").toString(),
																			 session.getAttribute("deliveryflt").toString(),
																			 session.getAttribute("statusflt").toString(),
																			 session.getAttribute("carcodeflt").toString(),
																			 session.getAttribute("carmarkflt").toString(),
																			 session.getAttribute("preordercntflt").toString() ,
																			 session.getAttribute("orderlinkcntflt").toString(),
																			 session.getAttribute("printflt").toString(),
																			 session.getAttribute("commentflt").toString(),
			                                                                 Integer.parseInt(session.getAttribute("rowcount").toString())
			                                                                 );
	if(routeList != null){
		for (Vroute route : routeList) {
			i++;

%>
<% if(new DeliveryManager().hasErrorPreOrdersByRoute(route.getRouteId())){ %> <tr bgcolor="#F6CECE"> <%} else { if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else {  %> <tr> <% } } %>
   <td align="center"><input style="border : none" type="checkbox" id=<%=route.getRouteId()%> title="OrderId : <%=route.getRouteId()%>" onclick="setMarked(<%=route.getRouteId()%>)"></td>
   <td align="center"><small><%=i %></small></td>
   <td align="center" title="routeId : <%=route.getRouteId()%>"><%=route.getExpectedDate()==null?"":route.getExpectedDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))%> </td>
   <td align="left" title="routeId : <%=route.getRouteId()%>"><%=route.getRouteCode()==null?"":route.getRouteCode()%> </td>
   <td title="statusId : <%=route.getStatusId()%>"><small><%=route.getStatusName()%></small></td>
   <td align="center"><small><%=route.getCarCode()==null?"":route.getCarCode()%></small></td>
   <td align="center"><small><%=route.getCarMark()==null?"":route.getCarMark()%></small></td>
   <td align="center"><small><%=route.getPreOrderCount()==0L?"":route.getPreOrderCount()%></small></td>
   <td align="center"><small><%=route.getOrderLinkCount()==0L?"":route.getOrderLinkCount()%></small></td>
   <td align="center" ><small><input style="border : none" type="checkbox" id=<%=route.getRouteId()%> title="PrintDate : <%=route.getPrintDate()%>"  <%=route.getPrintDate()!=null?"checked='checked'":"" %> onclick="return false"></small></td>
   <td align="center" title="<%=route.getCommentId()==null?"":route.getCommentId()%>"><small><%=route.getCommentId()==null?"":"Есть"%></small></td>
   </tr>
<% 		}

	}
%>

</table>

<input type="hidden" id="marked">
<input type="hidden" id="wmorderlink">
</form>
</body>
</html>
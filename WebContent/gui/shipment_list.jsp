<%
	System.out.println("GUI/shipment_list.jsp");
%>

<%@page import="java.util.*"%>
<%@page import="ru.defo.model.WmPreOrder"%>
<%@page import="ru.defo.model.WmOrderType"%>
<%@page import="ru.defo.model.WmStatus"%>
<%@page import="ru.defo.model.WmCar"%>
<%@page import="ru.defo.managers.DeliveryManager"%>
<%@page import="ru.defo.managers.CarManager"%>
<%@page import="ru.defo.controllers.PreOrderController"%>
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

<title>WMS Дэфо Заказы</title>
<script>
function showWmPreOrder(orderid){
		document.location="${pageContext.request.contextPath}/gui/PreShipmentDoc?orderid="+orderid;
}

function showWmOrder(orderid){
	document.location="${pageContext.request.contextPath}/gui/ShipmentDoc?orderid="+orderid;
}

function setfilter(){


	document.location="${pageContext.request.contextPath}/gui/ShipmentList?shipmentflt="
			+document.getElementById('shipmentflt').value.replace("#","%23").replace("@","%40").replace("+","%2B").replace("*","%2A")
							 +"&clientdocflt="+document.getElementById('clientdocflt').value
							 +"&dateflt="+document.getElementById('dateflt').value
							 +"&typeflt="+document.getElementById('select').options[document.getElementById('select').selectedIndex].value
							 +"&statusflt="+document.getElementById('selectsts').options[document.getElementById('selectsts').selectedIndex].value
							 +"&wmsshptflt="+document.getElementById('wmsshptflt').value
							 +"&rowcount="+document.getElementById('rowcount').value
							 +"&marked="+document.getElementById('marked').value
							 +"&expdate="+document.getElementById('expdate').value
							 +"&wmorderlink="+document.getElementById('wmorderlink').value
							 +"&clientdescrflt="+document.getElementById('clientdescrflt').value
							 +"&routeflt="+document.getElementById('routeflt').value
							 +"&rdateflt="+document.getElementById('rdateflt').value
							 +"&carflt="+document.getElementById('carflt').value
							 +"&carmarkflt="+document.getElementById('carmarkflt').value
							 ;
}

function setMarkedOnly(){
	var artlist = [];

	list = document.getElementById("shpttable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			advlist.push(list[i].getElementsByTagName("td")[0].childNodes[0].id);
		}
	}
	setfilter();

}

function setMarkAll(){

	list = document.getElementById("shpttable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		 if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			 document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).click();
		 } else {
			if(showError(document.getElementById("shpttable").rows[i].cells[8].innerHTML)===0)
		 	document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked = "true";
		 }
	}
}

function createShpt(){
	markedCount = 0;
	var marked = [];

	if(document.getElementById('expdate').value.length < 1){
		alert("Не указано на какую дату объединяются документы в отгрузку!");
		throw "stop";
	}

	list = document.getElementById("shpttable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){

			//alert("["+list[i].getElementsByTagName("td")[7].innerText +"]");

			if(list[i].getElementsByTagName("td")[7].innerText.length  > 0){
				alert('Документ '+
						list[i].getElementsByTagName("td")[2].innerHTML
				     +' уже включен в складскую отгрузку '+list[i].getElementsByTagName("td")[7].innerHTML
				     +' ! Никуда более его включить нельзя!'
				     );
			} else {
			marked[markedCount] = list[i].getElementsByTagName("td")[0].childNodes[0].id ;
			markedCount =markedCount+1;
			}
		}
	}

	if(markedCount==0){
		alert('Не выбрано документов для объединения в складскую отгрузку!');
	} else {
	 	action = confirm('Создать складскую отгрузку по выбранным документам?');
	 	if(action) document.getElementById('marked').value = marked;
	 	setfilter();
	}

}

function openCreateOrderDlg(orderCode, preOrderId){
	var wmOrderLink = prompt('Введите номер Складской Отгрузки для присвоения документу : '+orderCode, '');
	if(wmOrderLink != null){
		document.getElementById('marked').value = preOrderId;
		document.getElementById('wmorderlink').value = wmOrderLink;
		setfilter();
	}
}

function showError(errorId){
	if(errorId > 0){
		alert("Нельзя запускать в работу документ имеющий ошибки!");
		return 1;
	} else { return 0; }

}


</script>
</head>
<body>

<h3>Список заданий на <u>Отгрузку</u></h3>
<p>

<input type="button" id="btnBack" value="Назад" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'">
<% if(new PermissionController().harPerm4Joining(session.getAttribute("userid").toString())) {%>
<strong style="padding-left: 30px">Документы на дату</strong> <input type="date" id="expdate">
<input type="button" id="btnCreateShpt" value="Объединить в отгрузку" onclick="createShpt()">
<% } else { %>
<input type="hidden" id="expdate">
<% } %>
<br>
<br><small>Показывать строк не более: </small><input type="text" id="rowcount" value="<%=session.getAttribute("rowcount")%>" onchange="setfilter()" size="2" onkeyup = 'this.value=parseInt(this.value) | 0'>
</p>
<form>
<table id="shpttable" border="1px">
<tr bgcolor="#DCDCDC">
	<td align="center"> </td>
	<td align="center">#</td>
	<td align="center"><b>Документ Но.</b></td>
	<td align="center"><b>Клиент Документ Но.</b></td>
	<td align="center"><b>Дата документа</b></td>
	<td align="center"><b>Тип отгрузки</b></td>
	<td align="center"><b>Статус</b></td>
	<td align="center"><small><b>Складская отгрузка Но.</b></small></td>
	<td align="center"><b>Код ошибки</b></td>
	<td align="center"><b>Комментарии</b></td>
	<td align="center"><b>Клиент Наименование</b></td>
	<td align="center"><b>Доставка</b></td>
	<td align="center"><b>Дата отгрузки</b></td>
	<td align="center"><b>Машина</b></td>
	<td align="center"><b>Марка машины</b></td>

</tr>

<tr>
	<td align="center"><input type="button" id="mark_all" onclick="setMarkAll()"></td>
	<td></td>
	<td><input style="border : none" size="20" 	type="text" id="shipmentflt" 		onchange="setfilter()" value="<%=session.getAttribute("shipmentflt")%>"></td>
	<td><input style="border : none" size="12" 	type="text" id="clientdocflt" 	onchange="setfilter()" value="<%=session.getAttribute("clientdocflt")%>"></td>
	<td><input style="border : none" type="date" id="dateflt" 			onchange="setfilter()" value="<%=session.getAttribute("dateflt")%>"></td>
	<td><select style="border : none" id="select" onchange="setfilter()">
		<option value="" class='option'></option>
	<%
	Long typeIdFlt = 0L;
	List<WmOrderType> orderTypeList = new PreOrderController().getOrderTypeList();
	for(WmOrderType orderType : orderTypeList){

		if(!session.getAttribute("typeflt").toString().isEmpty()){
			typeIdFlt = Long.valueOf(session.getAttribute("typeflt").toString());
		}

		if(typeIdFlt==orderType.getOrderTypeId())	{
		%>
			<option selected value='<%=orderType.getOrderTypeId()%>' class='option'><%=orderType.getDescription()%></option>
		<% } else { %>
			<option value='<%=orderType.getOrderTypeId()%>' class='option'><%=orderType.getDescription()%></option>
		<% } %>
	<%
	}
	%>
	</select></td>
	<td>
	<small>
	<select style="border : none" id="selectsts" onchange="setfilter()">
		<option value="" class='option'></option>
	<%

	Long statusIdFlt = 0L;
	List<WmStatus> statusList = new PreOrderController().getStatusList();
	for(WmStatus status : statusList){

		if(!session.getAttribute("statusflt").toString().isEmpty()){
			statusIdFlt = Long.valueOf(session.getAttribute("statusflt").toString());
		}

		if(statusIdFlt==status.getStatusId())	{
		%>
			<option selected value='<%=status.getStatusId()%>' class='option'><%=status.getDescription()%></option>
		<% } else { %>
			<option value='<%=status.getStatusId()%>' class='option'><%=status.getDescription()%></option>
		<% } %>
	<%
	}
	%>
	</select>
	</small>
	</td>
	<td><input style="border : none" size="12" 	type="text" id="wmsshptflt" 		onchange="setfilter()" value="<%=session.getAttribute("wmsshptflt")%>"></td>
	<td></td>
	<td></td>
	<td><input Style="border : None" Size="20" 	Type="text" Id="clientdescrflt" 	Onchange="setfilter()" Value="<%=session.getAttribute("clientdescrflt")%>"></td>

	<td><input Style="border : None" Size="9" 	Type="text" Id="routeflt" 	Onchange="setfilter()" Value="<%=session.getAttribute("routeflt")%>"></td>
	<td><input Style="border : None" Size="4" 	Type="date" Id="rdateflt" 	Onchange="setfilter()" Value="<%=session.getAttribute("rdateflt")%>"></td>
	<td><input Style="border : None" Size="6" 	Type="text" Id="carflt" 	Onchange="setfilter()" Value="<%=session.getAttribute("carflt")%>"></td>
	<td><input Style="border : None" Size="4" 	Type="text" Id="carmarkflt" 	Onchange="setfilter()" Value="<%=session.getAttribute("carmarkflt")%>"></td>
</tr>


<%
    int i = 0;
	List<WmPreOrder> preOrderList = new PreOrderController().getPreOrderList(session.getAttribute("shipmentflt").toString(),
																			 session.getAttribute("clientdocflt").toString(),
																			 session.getAttribute("dateflt").toString(),
																			 session.getAttribute("typeflt").toString(),
																			 session.getAttribute("statusflt").toString(),
																			 session.getAttribute("wmsshptflt").toString(),
																			 session.getAttribute("clientdescrflt").toString(),

																			 session.getAttribute("routeflt").toString(),
																			 session.getAttribute("rdateflt").toString(),
																			 session.getAttribute("carflt").toString(),
																			 session.getAttribute("carmarkflt").toString(),

			                                                                 session.getAttribute("rowcount").toString());

	if(preOrderList != null){
		for (WmPreOrder preOrder : preOrderList) {
			i++;
			WmCar car= new WmCar();
			if(preOrder.getRoute() != null)
				car = new CarManager().getCarById(preOrder.getRoute().getCarId());
%>

<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
   <td align="center"><input style="border : none" type="checkbox" id=<%=preOrder.getOrderId()%> title="OrderId : <%=preOrder.getOrderId()%>" onclick="if(showError('<%=preOrder.getErrorId()==null?"0":preOrder.getErrorId()%>')!=0) return false "></td>
   <td align="center"><small><%=i %></small></td>
   <td align="right" title="OrderId : <%=preOrder.getOrderId()%>"><%=preOrder.getOrderCode()==null?"":preOrder.getOrderCode()%>
   	<input type="button" id="btnLookup" value="->" onclick="showWmPreOrder(<%=preOrder.getOrderId() %>)"">
   </td>
   <td><small><%=preOrder.getClientDocCode()%></small></td>
   <td align="center"><small><%=preOrder.getExpectedDate()%></small></td>
   <td align="center"><%=preOrder.getOrderType().getDescription()%></td>
   <td Size="4" align="center"><small><%=preOrder.getStatus().getDescription()%></small></td>
   <% if(preOrder.getWmOrderLink()!=null) {%>
   <!-- <td align="left" onclick="showWmOrder(<%=preOrder.getWmOrderLink() %>)"><%=preOrder.getWmOrderLink()%></td>  -->
   <td align="right">
   <%=preOrder.getWmOrderLink()%>
   <input type="button" id="btnLookup" value="->" onclick="showWmOrder(<%=preOrder.getWmOrderLink() %>)"">
   </td>
   <% } else { %>
   <!-- <td align="left" onclick="openCreateOrderDlg('<%=preOrder.getOrderCode()%>', '<%=preOrder.getOrderId()%>')"></td>  -->
   <td align="right">
   <input type="button" id="btnCreate" value="..." onclick="if(showError('<%=preOrder.getErrorId()==null?"0":preOrder.getErrorId()%>')===0) openCreateOrderDlg('<%=preOrder.getOrderCode()%>', '<%=preOrder.getOrderId()%>')">
   </td>
   <% } %>
   <td align="center"><%=preOrder.getErrorId()==null?"":preOrder.getErrorId()%></td>
   <td align="left"><%=preOrder.getErrorComment()==null?"":preOrder.getErrorComment()%></td>
   <td align="left"><small><%= preOrder.getClientDocDescription()  %></small></td> <!-- //new PreOrderController().getClientDocDescription(preOrder.getOrderId().toString())  -->

   <td align="center"><small><%=preOrder.getRoute()==null?"":preOrder.getRoute().getRouteCode()%></small></td>
   <td align="center"><small><%=preOrder.getRoute()==null?"":preOrder.getRoute().getExpectedDate().toString().substring(0, 10)%></small></td>
   <td align="center"><small><%=car.getCarCode()==null?"":car.getCarCode()%></small></td>
   <td align="center"><small><%=car.getCarMark()==null?"":car.getCarMark()%></small></td>

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
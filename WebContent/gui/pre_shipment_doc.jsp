<%
	System.out.println("GUI/pre_shipment_doc.jsp");
%>

<%@page import="java.util.*"%>
<%@page import="ru.defo.util.DefaultValue"%>
<%@page import="ru.defo.model.views.Vorderpos"%>
<%@page import="ru.defo.model.views.Vpreorderpos"%>
<%@page import="ru.defo.model.WmPreOrder"%>
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
<meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>

<title>WMS Дэфо Отгрузка <%=DefaultValue.HOST_NAME%></title>



<script>
function moveToFactQtyDetails(orderid, orderposid){
	document.location="${pageContext.request.contextPath}/gui/PickList?orderid="+orderid+"&orderposid="+orderposid;
}

function setfilter(){
	document.location="${pageContext.request.contextPath}/gui/PreShipmentDoc?orderid=${orderid}"
							 +"&posflt="+document.getElementById('posflt').value
							 +"&articleidflt="+document.getElementById('articleidflt').value.replace("#","%23").replace("@","%40").replace("+","%2B").replace("*","%2A")
							 +"&artdescrflt="+document.getElementById('artdescrflt').value
							 +"&skuflt="+document.getElementById('skuflt').value
							 +"&expqtyflt="+document.getElementById('expqtyflt').value
							 +"&factqtyflt="+document.getElementById('factqtyflt').value
							 +"&statusposflt="+document.getElementById('selectsts').options[document.getElementById('selectsts').selectedIndex].value
							 +"&linkflt="+document.getElementById('linkflt').value
							 +"&dellink="+document.getElementById('dellink').value
							 ;
}

function goBack(){
	document.location='${pageContext.request.contextPath}/${backpage}';
	//history.back()
}

function goLink(wmorderid){
	window.open("${pageContext.request.contextPath}/gui/ShipmentDoc?orderid="+wmorderid,'',scrollbars=0,true);
}

function delLink(wmorderid){
	if(confirm('Изъять этот документ из складской отгрузки?'))
		document.getElementById('dellink').value = "1";
	setfilter();
}

</script>



</head>
<body>

<h3>Отгрузка <%=DefaultValue.HOST_NAME%></h3>
<pre>
<input type="button" id="btnHome" title="В главное меню" value="&#x1f3e0" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'"> <input type="button" id="btnBack" value="Назад" onclick="goBack()">
</pre>
<form>
<%
WmPreOrder order = null;

if(session.getAttribute("orderid") != null){
	Long ordId = Long.valueOf(session.getAttribute("orderid").toString());
 	order = new PreOrderController().getPreOrderByOrderId(ordId);
 	session.setAttribute("ordercode",order.getOrderCode());

%>
<!-- <p style= "font-family: UpcBwrP72Tt"><%=order.getOrderCode()%></p>   -->

<!--  -->
<table id="docheader" border="0px">
<tr>
	<td align="left"><%=order.getOrderType().getDescription()%> Но.</td>
	<td><%=order.getOrderCode()%></td>
	<td title="<%=order.getOrderId()%> : <%=order.getWmOrderLink() %>" align="right">
	<%if(order.getWmOrderLink() != null){ %>
	Складская отгрузка <%=order.getWmOrderLink() %>
	<input title="Провалиться в документ Складской отгрузки" type="button" id="btnToLink" value="-> " onclick="goLink(<%=order.getWmOrderLink() %>)">
	<% if(new PermissionController().harPerm4Takeback(session.getAttribute("userid").toString())) {%>
	<input title="Удалить из складской отгрузки именно этот документ" type="button" id="btnDelLink" value="изъять" onclick="delLink(<%=order.getWmOrderLink() %>)">
	<%} %>
	<% } %>
	</td>
</tr>
<tr>
	<td align="left">Дата Отгрузки</td><td align="left"><%=order.getExpectedDate().toLocaleString().substring(0, 10)%></td>
	<td><%=order.getClientDocDescription() %></td>
</tr>
<tr>
	<td align="left">Статус</td><td align="left"><%=order.getStatus().getDescription()%></td>
	<td></td>
</tr>
<tr>
	<td align="left"></td><td align="left"></td>
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
	<td align="center"><b>Статус</b></td>
	<td align="center"><small><b>Код ошибки</b></small></td>
	<td align="center"><small><b>Комментарии</b></small></td>
	<td align="center" title="Ссылка на строку складского документа"><small><b>Ссылка скд. док.стр.</b></small></td>
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
<td>
<select style="border : none" id="selectsts" onchange="setfilter()">
		<option value="" class='option'></option>
	<%

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
	%>
	</select>
</td>
<td></td>
<td></td>
<td><input size="1" style="border : none" type="text" id="linkflt" onchange="setfilter()" value="<%=session.getAttribute("linkflt")%>"></td>
</tr>

<%

	ArticleController artCtrl = new ArticleController();
	int i = 0;
	int countExpQty = 0;
	int countFactQty = 0;
	List<Vpreorderpos> ordposList = null;
	if(session.getAttribute("orderid") != null)
		ordposList = new PreOrderController().getVPreOrderPosList(session.getAttribute("orderid").toString(),
															session.getAttribute("posflt").toString(),
															session.getAttribute("articleidflt").toString(),
															session.getAttribute("artdescrflt").toString(),
															session.getAttribute("skuflt").toString(),
															session.getAttribute("expqtyflt").toString(),
															session.getAttribute("factqtyflt").toString(),
															session.getAttribute("statusposflt").toString(),
															session.getAttribute("linkflt").toString()
															);

	if(ordposList != null)
	{
		for(Vpreorderpos vordpos : ordposList){
			i++;
			countExpQty+=vordpos.getExpectQuantity()==null?0:vordpos.getExpectQuantity();
			countFactQty+=vordpos.getFactQuantity()==null?0:vordpos.getFactQuantity();
%>

<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
   <td align="center"><small><%=i %></small></td>
   <td align="center" title="OrderId : <%=vordpos.getOrderId()%> Line : <%=vordpos.getOrderPosId()%>"><%=vordpos.getOrderPosId()==null?"":vordpos.getOrderPosId()%></td>
   <td align="center"><%=vordpos.getArticleCode()==null?"":vordpos.getArticleCode()%></td>
   <td align="left"><small><%=vordpos.getArticleName()==null?"":vordpos.getArticleName()%></small></td>
   <td align="center"><small><%=vordpos.getSkuName()==null?"":vordpos.getSkuName()%></small></td>
   <td align="right"><small><%=vordpos.getExpectQuantity()==null?"":vordpos.getExpectQuantity()%></small></td>
   <%
   if(vordpos.getFactQuantity()!=null)
   {   %>
   <td align="right" onclick="moveToFactQtyDetails(<%=vordpos.getOrderId()%>, <%=vordpos.getOrderPosId()%>)"><small><%=vordpos.getFactQuantity()%></small></td>
   <% } else { %>
   <td align="right"><small></small></td>
   <% } %>
   <td align="center"><small><%=vordpos.getStatusName()==null?"":vordpos.getStatusName()%></small></td>
   <td align="center"><small><%=vordpos.getErrorId()==null?"":vordpos.getErrorId()%></small></td>
   <td align="left"><small><%=vordpos.getErrorComment()==null?"":vordpos.getErrorComment()%></small></td>
   <td align="left"><small><%=vordpos.getWmPosOrderLink()==null?"":vordpos.getWmPosOrderLink()%></small></td>
<% 		}
	}
%>
</table>
 <p>Всего количество Ожидаемое: <%=countExpQty%> , а Фактическое количество: <%=countFactQty%>  </p>
</form>

<input type="hidden" id="dellink">
</body>
</html>
<%
	System.out.println("GUI/pick_list.jsp");
%>
<%@page import="java.util.*"%>
<%@page import="ru.defo.model.views.Vpick"%>
<%@page import="ru.defo.model.WmOrder"%>
<%@page import="ru.defo.model.WmQualityState"%>
<%@page import="ru.defo.controllers.PickController"%>
<%@page import="ru.defo.controllers.QualityStateController"%>
<%@page import="ru.defo.controllers.OrderController"%>
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

<title>WMS Дэфо Подборы</title>
<script>
function setfilter(){
	document.location="${pageContext.request.contextPath}/gui/PickList?orderid=${orderid}&orderposid=${orderposid}"
							 +"&unitflt="+document.getElementById('unitflt').value
							 +"&binflt="+document.getElementById('binflt').value
							 +"&qsflt="+document.getElementById('selectqs').options[document.getElementById('selectqs').selectedIndex].value
							 +"&qtyflt="+document.getElementById('qtyflt').value
							 +"&dateflt="+document.getElementById('dateflt').value
							 +"&surnameflt="+document.getElementById('surnameflt').value
							 +"&firstnameflt="+document.getElementById('firstnameflt').value
							 ;
}

function goBack(){
	document.location.href='${pageContext.request.contextPath}/${backpage}'
	//history.back()
}


</script>
</head>
<body>
<%
WmOrder order = null;

if(session.getAttribute("orderid") != null){
	Long ordId = Long.valueOf(session.getAttribute("orderid").toString());
 	order = new OrderController().getOrderByOrderId(ordId);
}
%>
<h3>Список подборов к складской отгрузке <%=order.getOrderCode()==null?"":order.getOrderCode()%> по строке ${orderposid}</h3>
<p>
<input type="button" id="btnHome" title="В главное меню" value="&#x1f3e0" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'">
<input type="button" id="btnBack" value="Назад" onclick="goBack()">
<br>
<!-- <br><small>Показывать строк не более: </small><input type="text" id="rowcount" value="<%=session.getAttribute("rowcount")%>" onchange="setfilter()" size="2" onkeyup = 'this.value=parseInt(this.value) | 0'>  -->
</p>
<form>
<table id="shpttable" border="1px">
<tr bgcolor="#DCDCDC">
	<td align="center">#</td>
	<td align="center"><b>Поддон</b></td>
	<td align="center"><b>Ячейка</b></td>
	<td align="center"><b>Качество</b></td>
	<td align="center"><b>Количество</b></td>
	<td align="center"><b>Дата создания</b></td>
	<td align="center"><b>Фамилия</b></td>
	<td align="center"><b>Имя</b></td>
</tr>

<tr>
	<td></td>
	<td><input style="border : none" size="8" 	type="text" id="unitflt" onchange="setfilter()" value="<%=session.getAttribute("unitflt")%>"></td>
	<td><input style="border : none" size="11" 	type="text" id="binflt" 	onchange="setfilter()" value="<%=session.getAttribute("binflt")%>"></td>
	<td>
	<select style="border : none" id="selectqs" onchange="setfilter()">
		<option value="" class='option'></option>
	<%
	Long qsIdFlt = 0L;
	List<WmQualityState> qsList = new QualityStateController().getQualityStateList();
	for(WmQualityState qs : qsList){

		if(!session.getAttribute("qsflt").toString().isEmpty()){
			qsIdFlt = Long.valueOf(session.getAttribute("qsflt").toString());
		}

		if(qsIdFlt==qs.getQualityStateId())	{
		%>
			<option selected value='<%=qs.getQualityStateId()%>' class='option'><%=qs.getDescription()%></option>
		<% } else { %>
			<option value='<%=qs.getQualityStateId()%>' class='option'><%=qs.getDescription()%></option>
		<% } %>
	<%
	}
	%>
	</select>
	</td>
	<td><input style="border : none" size="8" 	type="text" id="qtyflt" 		onchange="setfilter()" value="<%=session.getAttribute("qtyflt")%>"></td>
	<td><small><input style="border : none" size="12" type="date" id="dateflt" onchange="setfilter()" value="<%=session.getAttribute("dateflt")%>"></small></td>
	<td><input style="border : none" size="14" 	type="text" id="surnameflt" 		onchange="setfilter()" value="<%=session.getAttribute("surnameflt")%>"></td>
	<td><input style="border : none" size="14" 	type="text" id="firstnameflt" 		onchange="setfilter()" value="<%=session.getAttribute("firstnameflt")%>"></td>

</tr>



<%

    int i = 0;
	List<Vpick> vPickList = new PickController().getVpickList(session.getAttribute("orderid").toString(),
															  session.getAttribute("orderposid").toString(),
															  session.getAttribute("unitflt").toString(),
															  session.getAttribute("binflt").toString(),
															  session.getAttribute("qsflt").toString(),
															  session.getAttribute("qtyflt").toString(),
															  session.getAttribute("dateflt").toString(),
															  session.getAttribute("surnameflt").toString(),
															  session.getAttribute("firstnameflt").toString()
															  );

	if(vPickList != null){
		for (Vpick pick : vPickList) {
			i++;
%>
<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
   <td align="center"><small><%=i %></small></td>
   <td align="center" title="pickId : <%=pick.getPickId()%> unitId : <%=pick.getUnitId()%>"><%=pick.getUnitCode()%></td>
   <td align="center"><%=pick.getBinCode()==null?"":pick.getBinCode()%></td>
   <td align="center"><%=pick.getQstateName()%></td>
   <td align="center"><%=pick.getQuantity()%></td>
   <td align="center"><small><%=pick.getCreateDate()%></small></td>
   <td align="left"><%=pick.getSurname()%></td>
   <td align="left"><%=pick.getFirstName()%></td>
   </tr>
<% 		}

	}
%>

</table>


</form>
</body>
</html>
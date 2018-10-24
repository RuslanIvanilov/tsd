<%
	System.out.println("GUI/empty_bin_list.jsp");
%>
<%@page import="java.util.*"%>
<%@page import="ru.defo.model.views.Vemptybin"%>
<%@page import="ru.defo.controllers.VEmptyBinController"%>
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

<title>WMS Дэфо Пустые ячейки</title>
<script>
function setfilter(){
	window.location="${pageContext.request.contextPath}/gui/EmptyBinList?areacodeflt="
						 +document.getElementById('areacodeflt').value
						 +"&racknoflt="+document.getElementById('racknoflt').value
						 +"&levelnoflt="+document.getElementById('levelnoflt').value
						 +"&rowcount="+document.getElementById('rowcount').value
						 +"&bincodeflt="+document.getElementById('bincodeflt').value
						 ;
}



</script>
</head>
<body>

<h3>Список пустых ячеек</h3>
<p>
<input type="button" id="btnHome" title="В главное меню" value="&#x1f3e0" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'">
<br>
<br><small>Показывать строк не более: </small><input type="text" id="rowcount" value="<%=session.getAttribute("rowcount")%>" onchange="setfilter()" size="2" onkeyup = 'this.value=parseInt(this.value) | 0'>
</p>
<form>
<table id="shpttable" border="1px">
<tr bgcolor="#DCDCDC">
	<td align="center">#</td>
	<td align="center"><b>Зона склада</b></td>
	<td align="center"><b>Стеллаж</b></td>
	<td align="center"><b>Этаж</b></td>
	<td align="center"><b>Ячейка</b></td>
</tr>

<tr>
	<td></td>
	<td><input size="9" style="border : none" type="text" id="areacodeflt" onchange="setfilter()" value="<%=session.getAttribute("areacodeflt")%>"></td>
	<td><input size="6" style="border : none" type="text" id="racknoflt" onchange="setfilter()" value="<%=session.getAttribute("racknoflt")%>"></td>
	<td><input size="2" style="border : none" type="text" id="levelnoflt" onchange="setfilter()" value="<%=session.getAttribute("levelnoflt")%>"></td>
	<td><input size="14" style="border : none" type="text" id="bincodeflt" onchange="setfilter()" value="<%=session.getAttribute("bincodeflt")%>"></td>

</tr>
<%
    int i = 0;
	List<Vemptybin> vEmptyBin = new VEmptyBinController().getList(session);

	if(vEmptyBin != null){
		for (Vemptybin bins : vEmptyBin) {
			i++;
%>
<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
   <td align="center"><small><%=i %></small></td>
   <td align="center" title="binId : <%=bins.getBinId()%>"><%=bins.getAreaCode()%></td>
   <td align="center"><%=bins.getRackNo()==null?"":bins.getRackNo()%></td>
   <td align="center"><%=bins.getLevelNo()%></td>
   <td align="center"><%=bins.getBinCode()%></td>
   </tr>
<% 		}

	}
%>

</table>


</form>
</body>
</html>
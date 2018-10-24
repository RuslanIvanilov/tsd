<%
	System.out.println("GUI/invent_create.jsp");
%>

<%@page import="java.util.*"%>
<%@page import="ru.defo.model.WmArea"%>
<%@page import="ru.defo.model.WmArticle"%>
<%@page import="ru.defo.model.WmInventory"%>
<%@page import="ru.defo.managers.BinManager"%>
<%@page import="ru.defo.managers.ArticleManager"%>
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

<title>WMS Дэфо Создание инвентаризации</title>
<script>
function doCreate(){
	document.location="${pageContext.request.contextPath}/gui/InventCreate?areatxt="+document.getElementById('areatxt').value
					+"&rackstart="+document.getElementById('rackstart').value
					+"&rackend="+document.getElementById('rackend').value
					+"&levelstart="+document.getElementById('levelstart').value
					+"&levelend="+document.getElementById('levelend').value
					+"&binstart="+document.getElementById('binstart').value
					+"&binend="+document.getElementById('binend').value
					+"&articletxt="+document.getElementById('articletxt').value.replace("#","%23").replace("@","%40").replace("+","%2B").replace("*","%2A")
					;
}

</script>
</head>
<body>

<h3>Создание задания на инвентаризацию</h3>
<p>
<input type="button" id="btnBack" value="Назад" onclick="document.location.href='${pageContext.request.contextPath}/gui/InventList'">
</p>
<form>
<table id="ptable" border="0px">
<tr>
<td align="right">Зона склада:</td><td>&nbsp;&nbsp;&nbsp;
<input size="2px" list="arealist" id="areatxt">
<datalist id="arealist">
<% List<WmArea> areaList = new BinManager().getAreaList();
   for(WmArea area : areaList){ %>
 	<option value='<%=area.getAreaCode()%>'></option>
<% } %>
</datalist>
</td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
<td align="right">Стеллежи</td><td align="right">с:<input size="3px" type="text" id="rackstart" onkeyup = 'this.value=parseInt(this.value) | 0'></td><td>&nbsp;&nbsp;&nbsp;&nbsp;по:<input size="3px" type="text" id="rackend" onkeyup = 'this.value=parseInt(this.value) | 0'></td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
<td align="right">Этажи</td>
<td align="right">с:<input size="3px" type="text" id="levelstart" onkeyup = 'this.value=parseInt(this.value) | 0'>
</td>

<td>&nbsp;&nbsp;&nbsp;&nbsp;по:<input size="3px" type="text" id="levelend" onkeyup = 'this.value=parseInt(this.value) | 0'></td>

</tr>
<tr><td>&nbsp;</td></tr>
<tr>
<td align="right">Ячейки</td><td>с:<input size="3px" type="text" id="binstart" onkeyup = 'this.value=parseInt(this.value) | 0'></td><td>&nbsp;&nbsp;&nbsp;&nbsp;по:<input size="3px" type="text" id="binend" onkeyup = 'this.value=parseInt(this.value) | 0'></td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
<td align="right"><!-- Товар: --></td>
<td colspan="2">&nbsp;&nbsp;&nbsp;<input size="17"  type="hidden" id="articletxt"></td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
<td colspan="3"><input type="button" id="createinvent" value="Создать Инвентаризацию" onclick="doCreate()"></td>
</tr>
</table>

</form>
</body>
</html>
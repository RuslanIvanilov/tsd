<%
	System.out.println("GUI/unit_print_request.jsp");
%>

<%@page import="java.util.*"%>
<%@page import="ru.defo.model.views.Varticleqty"%>
<%@page import="ru.defo.model.WmInventoryPos"%>
<%@page import="ru.defo.controllers.ArticleController"%>
<%@page import="ru.defo.managers.InventoryManager"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate, max-age=0'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>

<title>WMS Дэфо Печать поддонов</title>
<script>
function startPrint(){
	window.open("${pageContext.request.contextPath}/gui/UnitPrint?unitqty="+document.getElementById('unitqty').value
														            +"&unittype="+document.getElementById('unittype').value);
}
</script>
</head>
<body>

<h3>Печать поддонов</h3>
<p>
<input type="button" id="btnBack" value="Назад" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'">

<form>
<table>
<tr><td>Тип поддона :</td>					  <td><input style="text-align:right;" size="1" type="text" id="unittype" value="EU"></td></tr>
<tr><td>Кол-во листов<br>этикеток поддонов :</td><td><input style="text-align:right;" size="1" type="text" id="unitqty" value="" onkeyup = 'this.value=parseInt(this.value) | 0'>  (1 лист = 4 поддона)</td></tr>
<tr><td></td><td align="right"><b><input type="button" id="btnPrintUnit" value="Печать поддонов" onclick="startPrint()"></b></td></tr>
</table>
</form>
</p>

</body>
</html>
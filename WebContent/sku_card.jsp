<%@page import="ru.defo.managers.ArticleManager"%>
<% System.out.println("sku_card userid : "+session.getAttribute("userid").toString()); %>
<%
	System.out.println("sku_card");
%>
<%@page import="java.util.*"%>
<%@page import="ru.defo.model.WmArticle"%>
<%@page import="ru.defo.model.WmSku"%>
<%@page import="ru.defo.model.WmArticleType"%>
<%@page import="ru.defo.model.WmPermission"%>
<%@page import="ru.defo.model.WmUserPermission"%>
<%@page import="ru.defo.controllers.ArticleController"%>
<%@page import="ru.defo.controllers.SkuController"%>
<%@page import="ru.defo.controllers.PermissionController"%>
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

<title>WMS Дэфо Товар</title>
<script>
function setfilter(){
 	document.location="${pageContext.request.contextPath}/SkuCard?"
					 +"&save_mode="+document.getElementById('save_mode').value
					 +"&descrtxt="+document.getElementById('descrtxt').value
					 +"&isbasetxt="+document.getElementById('isbasetxt').checked
					 +"&weighttxt="+document.getElementById('weighttxt').value
					 +"&lengthtxt="+document.getElementById('lengthtxt').value
					 +"&widthtxt="+document.getElementById('widthtxt').value
					 +"&heightxt="+document.getElementById('heightxt').value
					 +"&crushtxt="+document.getElementById('crushtxt').value

					 ;


}


function save()
{
	if(confirm('Сохранить введенные изменения?')){
		document.getElementById('save_mode').value = 1;
		setfilter();
	}else{
		document.getElementById('save_mode').value = "";
		alert('Нажата кнопка отмены.');
	}
}


</script>

<%
	WmArticle article = new ArticleController().getArticle(session.getAttribute("articleid"));
	WmSku sku = new SkuController().getSku(session.getAttribute("skuid"));
%>
</head>
<body>
<p>
<table>
<tr>
<td><input type="button" id="btnHome" title="В главное меню" value="&#x1f3e0" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'"></td>
<td><input type="button" id="btnBack" value="Назад" onclick="document.location.href='${pageContext.request.contextPath}/${backpage}'">
<!-- <input type="button" id="btnBack" value="Назад" onclick="javascript:history.back()"> -->
</td>
<td><% if(new PermissionController().hasPermissionByCode(session.getAttribute("userid"), "SKU.UPDATE")){%><input type="button" id="btnSave" value="Сохранить изменения" onclick="save()"><% } %></td>
<tr>
</table>

<h3>Упаковка</h3>

<form id="card">
<table border="0px">
<tr>
<td align="right">Наименование:</td><td><input type="text" id="descrtxt" value='<%=sku.getDescription()==null?"":sku.getDescription()%>'></td>
</tr>
<tr>
<td align="right">Класс Хрупкости:</td><td><input type="text" id="crushtxt" value='<%=sku.getSkuCrushId()==null?"":sku.getSkuCrushId()%>' onkeyup = 'this.value=parseInt(this.value) | 0'></td>
</tr>
<tr>
<td align="right" title="Базовая упаковка для товара - наименьшая неделимая упаковка">Базовая:</td><td><input type="checkbox" id="isbasetxt" <%=sku.getIsBase()==true?"checked='checked'":"" %> ></td>
</tr>
<tr>
<td align="right">Вес (г):</td><td><input type="text" style='text-align:right;' id="weighttxt" value='<%=sku.getWeight()==null?"":sku.getWeight()%>' onkeyup = 'this.value=parseInt(this.value) | 0'></td>
</tr>
<tr>
<td align="right">Длина (мм):</td><td><input type="text" style='text-align:right;' id="lengthtxt" value='<%=sku.getLength()==null?"":sku.getLength()%>' onkeyup = 'this.value=parseInt(this.value) | 0'></td>
</tr>
<tr>
<td align="right">Ширина (мм):</td><td><input type="text" style='text-align:right;' id="widthtxt" value='<%=sku.getWidth()==null?"":sku.getWidth()%>' onkeyup = 'this.value=parseInt(this.value) | 0'></td>
</tr>
<tr>
<td align="right">Высота (мм):</td><td><input type="text" style='text-align:right;' id="heightxt" value='<%=sku.getHeigh()==null?"":sku.getHeigh()%>' onkeyup = 'this.value=parseInt(this.value) | 0'></td>
</tr>


</table>
</form>

</p>

<input type="hidden" id="save_mode" name="save_mode">
</body>
</html>
<%@page import="ru.defo.managers.ArticleManager"%>
<% System.out.println("article_card userid : "+session.getAttribute("userid").toString()); %>
<%
	System.out.println("article_card");
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

	document.location="${pageContext.request.contextPath}/ArticleCard?"
					 +"&save_mode="+document.getElementById('save_mode').value
					 +"&descriptiontxt="+document.getElementById('descriptiontxt').value.replace("#","%23").replace("@","%40").replace("+","%2B").replace("*","%2A").replace("\\","%5C")
					 +"&blockedtxt="+document.getElementById('blockedtxt').checked
					 +"&descrflt="+document.getElementById('descrflt').value
					 +"&isbaseflt="+document.getElementById('isbaseflt').value
					 +"&weightflt="+document.getElementById('weightflt').value
					 +"&lengthflt="+document.getElementById('lengthflt').value
					 +"&widthflt="+document.getElementById('widthflt').value
					 +"&heighflt="+document.getElementById('heighflt').value
					 +"&crushflt="+document.getElementById('crushflt').value
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

function lookup(skuid){
	document.location="${pageContext.request.contextPath}/SkuCard?skuid="+skuid;
}

</script>

<%
	WmArticle article = new ArticleController().getArticle(session.getAttribute("articleid"));
    WmArticle artParent = new ArticleController().getArticle(article.getArticleKitId());
    WmArticleType artType = new ArticleController().getArticleTypeByArticle(article);
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
<td><% if(new PermissionController().hasPermissionByCode(session.getAttribute("userid"), "ARTICLE.UPDATE")){%><input type="button" id="btnSave" value="Сохранить изменения" onclick="save()"><% } %></td>
<tr>
</table>

<h3>Товар</h3>

<form id="card">
<table border="0px">
<tr>
<td align="right">Код:</td><td><%=article.getArticleCode()==null?"":article.getArticleCode()%></td>

</tr>

<tr>
<td align="right">Наименование:</td><td colspan="8"><input size="80" type="text" id="descriptiontxt" value='<%=article.getDescription()==null?"":article.getDescription()%>'></td>

</tr>


<tr>
<td align="right">Тип:</td><td colspan="2"><small><%=artType.getDescription()==null?"":artType.getDescription()%></small></td>

</tr>
<tr>
<td align="right">Комплектный номер:</td><td><%=artParent.getArticleId()==null?"":artParent.getArticleId() %>
&nbsp;&nbsp;&nbsp; код: <%=artParent.getArticleCode()==null?"":artParent.getArticleCode() %></td>

</tr>


<tr>
<td align="right">Блокирован:</td><td><input type="checkbox" id="blockedtxt" <%=article.getBlocked()==true?"checked='checked'":"" %> ></td><td></td>
</tr>
</table>
</form>

</p>

<h3>Упаковки</h3>

<form>
<table id="permtable" border="1px">
<tr bgcolor="#DCDCDC">
	<td align="center"> </td>
	<td align="center">#</td>
	<td align="center"><b><small>Наименование</small></b></td>
	<td align="center"><b>Базовая</b></td>
	<td align="center"><b>Вес(г)</b></td>
	<td align="center"><b>Длина (мм)</b></td>
	<td align="center"><b>Ширина (мм)</b></td>
	<td align="center"><b>Высота (мм)</b></td>
	<td align="center"><b><small>Хрупкость класс</small></b></td>

</tr>

<tr>
	<td align="center"><input type="button" id="mark_all" onclick="setMarkAll()"></td>
	<td></td>
	<td><input size="8" 	type="text" id="descrflt" 		onchange="setfilter()" 	value="<%=session.getAttribute("descrflt")%>"></td>
	<td><input size="4" 	type="text" id="isbaseflt" 		onchange="setfilter()" 		value="<%=session.getAttribute("isbaseflt")%>" title="Фильтры: Y, Д, 1 - только базовые; N, Н, 1 - только Небазовые "></td>
	<td><input size="4" 	type="text" id="weightflt" 		onchange="setfilter()" 			value="<%=session.getAttribute("weightflt")%>"></td>
	<td><input size="8"		type="text" id="lengthflt" 	onchange="setfilter()" 		value="<%=session.getAttribute("lengthflt")%>"></td>
	<td><input size="11" 	type="text" id="widthflt" 	onchange="setfilter()" 		value="<%=session.getAttribute("widthflt")%>"></td>
	<td><input size="8"		type="text" id="heighflt" 	onchange="setfilter()" 		value="<%=session.getAttribute("heighflt")%>"></td>
	<td><input size="10" 	type="text" id="crushflt" 	onchange="setfilter()" 		value="<%=session.getAttribute("crushflt")%>"></td>
</tr>

<%
    int i = 0;
	List<WmSku> skuList= new SkuController().getSkuList(article);

	for (WmSku sku : skuList) {
			i++;

%>

<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
   <td align="center" title='<%=sku.getSkuId()%>'><input type="checkbox" id=<%=sku.getSkuId()%> ></td>
   <td align="center"><small><%=i %></small></td>
   <td align="right">
      <%=sku.getDescription()%>&nbsp;<input style="border: none;" type="button" value="..." onclick="lookup(<%=sku.getSkuId()%>)">
   </td>
   <td align="center"><input type="checkbox" id="b<%=sku.getSkuId()%>" <%=sku.getIsBase()==true?"checked='checked'":"" %> onclick="return false"></td>

   <td align="right"><%=sku.getWeight()==null?"":sku.getWeight()%></td>
   <td align="right"><%=sku.getLength()==null?"":sku.getLength()%></td>
   <td align="right"><%=sku.getWidth()==null?"":sku.getWidth()%></td>
   <td align="right"><%=sku.getHeigh()==null?"":sku.getHeigh()%></td>
   <td align="center"><%=sku.getSkuCrushId()==null?"":sku.getSkuCrushId()%></td>

   </tr>
<% 		}


%>

</table>
</form>

<input type="hidden" id="save_mode" name="save_mode">
</body>
</html>
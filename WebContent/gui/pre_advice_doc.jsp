<%
	System.out.println("GUI/pre_advice_doc.jsp");
%>

<%@page import="java.util.*"%>
<%@page import="ru.defo.util.DefaultValue"%>
<%@page import="ru.defo.model.views.Vorderpos"%>
<%@page import="ru.defo.model.views.Vpreorderpos"%>
<%@page import="ru.defo.model.WmPreAdvice"%>
<%@page import="ru.defo.model.WmPreAdvicePos"%>
<%@page import="ru.defo.model.WmArticle"%>
<%@page import="ru.defo.model.WmSku"%>
<%@page import="ru.defo.model.WmStatus"%>
<%@page import="ru.defo.model.WmAdviceType"%>
<%@page import="ru.defo.controllers.AdviceController"%>
<%@page import="ru.defo.controllers.PreAdviceController"%>
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

<title>WMS Дэфо Приемка <%=DefaultValue.HOST_NAME%></title>



<script>
function moveToFactQtyDetails(orderid, orderposid){
	document.location="${pageContext.request.contextPath}/gui/PickList?orderid="+orderid+"&orderposid="+orderposid;
}

function setfilter(){
	document.location="${pageContext.request.contextPath}/gui/PreAdviceDoc?adviceid=${adviceid}"
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
	document.location.href='${pageContext.request.contextPath}/${backpage}'
	//history.back()
}

function goLink(wmorderid){
	window.open("${pageContext.request.contextPath}/gui/AdviceDoc?adviceid="+wmorderid,'',scrollbars=0,true);
}

function delLink(wmorderid){
	if(confirm('Изъять этот документ из складской отгрузки?'))
		document.getElementById('dellink').value = "1";
	setfilter();
}

</script>



</head>
<body>

<h3>Приемка <%=DefaultValue.HOST_NAME%></h3>
<pre>
<input type="button" id="btnHome" title="В главное меню" value="&#x1f3e0" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'"> <input type="button" id="btnBack" value="Назад" onclick="goBack()">
</pre>
<form>
<%
WmPreAdvice advice = null;

if(session.getAttribute("adviceid") != null){
	Long advId = Long.valueOf(session.getAttribute("adviceid").toString());
 	advice = new PreAdviceController().getPreAdviceByAdviceId(advId);
 	session.setAttribute("advicecode", advice.getAdviceCode());

%>
<!-- <p style= "font-family: UpcBwrP72Tt"><%=advice.getAdviceCode()%></p>   -->

<!--  -->
<table id="docheader" border="0px">
<tr>
	<td align="left"><%=advice.getAdviceType().getDescription()%> Но.</td>
	<td><%=advice.getAdviceCode()%></td>
	<td title="<%=advice.getAdviceId()%> : <%=advice.getWmAdviceLink() %>" align="right">
	<%if(advice.getWmAdviceLink() != null){ %>
	Складская приемка <%=advice.getWmAdviceLink() %>
	<input title="Провалиться в документ Складской отгрузки" type="button" id="btnToLink" value="-> " onclick="goLink(<%=advice.getWmAdviceLink() %>)">
	<% if(new PermissionController().harPerm4Takeback(session.getAttribute("userid").toString())) {%>
	<input title="Удалить из складской отгрузки именно этот документ" type="button" id="btnDelLink" value="изъять" onclick="delLink(<%=advice.getWmAdviceLink() %>)">
	<%} %>
	<% } %>
	</td>
</tr>
<tr>
	<td align="left">Дата Отгрузки</td><td align="left"><%=advice.getExpectedDate().toLocaleString().substring(0, 10)%></td>
	<td><%=advice.getClientDocDescription() %></td>
</tr>
<tr>
	<td align="left">Статус</td><td align="left"><%=advice.getStatus().getDescription()%></td>
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

		if(session.getAttribute("statusposflt")!=null && !session.getAttribute("statusposflt").toString().isEmpty()){
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
	List<WmPreAdvicePos> advPosList = new ArrayList<WmPreAdvicePos>();
	if(session.getAttribute("adviceid") != null){
		advPosList = new PreAdviceController().getPreAdvicePosList(session);
	}

	if(advPosList != null)
	{
		for(WmPreAdvicePos pos : advPosList){
			i++;
			WmArticle article = new ArticleController().getArticle(pos.getArticleId());
			WmSku sku = new ArticleController().getBaseSkuByArticleId(article.getArticleId());
%>

<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
   <td align="center"><small><%=i %></small></td>
   <td align="center" title="AdviceId : <%=pos.getAdviceId()%> Line : <%=pos.getAdvicePosId()%>"><%=pos.getAdvicePosId()==null?"":pos.getAdvicePosId()%></td>
   <td align="center"><%=article.getArticleCode()==null?"":article.getArticleCode()%></td>
   <td align="left"><small><%=article.getDescription()==null?"":article.getDescription()%></small></td>
   <td align="center"><small><%=sku.getDescription()==null?"":sku.getDescription()%></small></td>
   <td align="right"><small><%=pos.getExpectQuantity()==null?"":pos.getExpectQuantity()%></small></td>
   <%
   if(pos.getFactQuantity()!=null)
   {   %>
   <td align="right" onclick="moveToFactQtyDetails(<%=pos.getAdviceId()%>, <%=pos.getAdvicePosId()%>)"><small><%=pos.getFactQuantity()%></small></td>
   <% } else { %>
   <td align="right"><small></small></td>
   <% } %>
   <td align="center"><small><%=pos.getStatus()==null?"":pos.getStatus().getDescription()%></small></td>
   <td align="center"><small><%=pos.getErrorId()==null?"":pos.getErrorId()%></small></td>
   <td align="left"><small><%=pos.getErrorComment()==null?"":pos.getErrorComment()%></small></td>
   <td align="left"><small><%=pos.getWmAdvicePosLink()==null?"":pos.getWmAdvicePosLink()%></small></td>
<% 		}
	}
%>
</table>
</form>

<input type="hidden" id="dellink">

</body>
</html>
<%
	System.out.println("GUI/article_qty_list.jsp");
%>

<%@page import="java.util.*"%>
<%@page import="ru.defo.model.views.Varticleqty"%>
<%@page import="ru.defo.model.WmInventoryPos"%>
<%@page import="ru.defo.controllers.ArticleController"%>
<%@page import="ru.defo.controllers.InventoryController"%>
<%@page import="ru.defo.managers.InventoryManager"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<!-- <meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate, max-age=0'>  -->
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>

<title>WMS Дэфо Артикулы</title>
<script>
function openqty(articleid){
	document.location="${pageContext.request.contextPath}/gui/UnitQuantList?articleid="+articleid
/*
	+"&unitflt="
	+"&binflt="
	+"&areaflt="
	+"&rackflt="
	+"&levelflt="
	+"&qtyflt="
	+"&skuflt="
	+"&qstateflt="
	+"&needchkflt="
	+"&crdateflt="
	+"&surnameflt="
	+"&fstnameflt="
	+"&advflt="
	+"&advposflt="
	+"&expdateflt="
	+"&groupflt="
	+"&articleflt="
	+"&artnameflt="
	+"&rowcount="+document.getElementById('rowcount').value
*/
	;
}


function setfilter(articlefilter){
	window.location="${pageContext.request.contextPath}/gui/ArticleQtyList?articlefilter="
			+document.getElementById('articlefilter').value.replace("#","%23").replace("@","%40").replace("+","%2B").replace("*","%2A")
							 +"&articledescfilter="+document.getElementById('articledescfilter').value
							 +"&qtyfilter="+document.getElementById('qtyfilter').value
							 +"&rowcount="+document.getElementById('rowcount').value
							 +"&startloader="+document.getElementById('startloader').value
							 +"&qtyfctflt="+document.getElementById('qtyfctflt').value

							 +"&qtyhostflt="+document.getElementById('qtyhostflt').value
							 +"&dwmsfctflt="+document.getElementById('dwmsfctflt').value
							 +"&dwmshostflt="+document.getElementById('dwmshostflt').value
							 +"&dfcthostflt="+document.getElementById('dfcthostflt').value
							 ;
}

function setMarkedOnly(){
	var artlist = [];

	list = document.getElementById("arttable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			advlist.push(list[i].getElementsByTagName("td")[0].childNodes[0].id);
		}
	}
	setfilter(artlist);

}

function setMarkAll(){
	list = document.getElementById("arttable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			setfilter();
		} else {
			document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked = "true";
		}

	}
}

function startLoadWS(){
	document.getElementById('startloader').value = 1;
	document.getElementById('btnLoadWebData').disabled=true;
	setfilter();
	//setfilter();
	//window.location=window.location.origin+"&startloader=1"
}

function createJob(){
	alert('create jobs!');
}

function lookup(articleid){
	document.location="${pageContext.request.contextPath}/ArticleCard?articleid="+articleid;
}



</script>
</head>
<body>

<h3>Список артикулов</h3>
<p>
<input type="button" id="btnHome" title="В главное меню" value="&#x1f3e0" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'">
<input type="button" id="btnBack" value="Назад" onclick="'javascript:history.back()'">

<b><input type="button" id="btnLoadWebData" value="Загрузка" onclick="startLoadWS()" <%=session.getAttribute("startloader")==""?"":"disabled title='Выполняется'" %>></b>

<input type="button" id="btnJob" value="Создание заданий" onclick="createJob()">
<br><small>Показывать строк не более: </small><input type="text" id="rowcount" value="<%=session.getAttribute("rowcount")%>" onchange="setfilter()" size="2" onkeyup = 'this.value=parseInt(this.value) | 0'>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<small>Кол-во по 1С на дату: <%=new InventoryController().getActualHostBalanceDate() %></small>

</p>
<form>
<table id="arttable" border="1px">
<tr bgcolor="#DCDCDC">
	<td align="center"> </td>
	<td align="center">#</td>
	<td align="center"><b>Артикул Но.</b></td>
	<td align="center"><b>Наименование</b></td>
	<td align="center"><small><b>Кол-во<br>СУС</b></small></td>
	<td align="center"><small><b>Кол-во<br>Факт</b></small></td>
	<td align="center"><small><b>Кол-во<br>1С</b></small></td>
	<td align="center"><small><b>&#916<br>СУС-Факт</b></small></td>
	<td align="center"><small><b>&#916<br>СУС-1С</b></small></td>
	<td align="center"><small><b>&#916<br>Факт-1С</b></small></td>
</tr>

<tr>
	<td align="center"><input type="button" id="mark_all" onclick="setMarkAll()"></td>
	<td></td>
	<td><input size="12" 	type="text" id="articlefilter" 		onchange="setfilter()" value="<%=session.getAttribute("articlefilter")%>"></td>
	<td><input size="56" 	type="text" id="articledescfilter" 	onchange="setfilter()" value="<%=session.getAttribute("articledescfilter")%>"></td>
	<td><input size="2"	type="text" id="qtyfilter" 			onchange="setfilter()" value="<%=session.getAttribute("qtyfilter")%>"></td>
	<td><input size="2"	type="text" id="qtyfctflt" 			onchange="setfilter()" value="<%=session.getAttribute("qtyfctflt")%>"></td>
	<td><input size="2"	type="text" id="qtyhostflt" 			onchange="setfilter()" value="<%=session.getAttribute("qtyhostflt")%>"></td>
	<td><input size="5"	type="text" id="dwmsfctflt" 			onchange="setfilter()" value="<%=session.getAttribute("dwmsfctflt")%>"></td>
	<td><input size="3"	type="text" id="dwmshostflt" 			onchange="setfilter()" value="<%=session.getAttribute("dwmshostflt")%>"></td>
	<td><input size="3"	type="text" id="dfcthostflt" 			onchange="setfilter()" value="<%=session.getAttribute("dfcthostflt")%>"></td>
</tr>



<%
	WmInventoryPos pos = null;
    int i = 0;

    List<Varticleqty> vArticleQtyList = new ArticleController().getVarticleQtyList(session);

	if(vArticleQtyList != null){
		for (Varticleqty vArticleQty : vArticleQtyList) {
			i++;

%>
<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
   <td align="center"><input type="checkbox" id=<%=vArticleQty.getArticleId()%>></td>
   <td align="center"><small><%=i %></small></td>
   <td align="right" title="<%=vArticleQty.getArticleId()==null?"":vArticleQty.getArticleId()%>"><%=vArticleQty.getArticleCode()==null?"":vArticleQty.getArticleCode()%>
   <input style="border: none;" type="button" value="..." onclick="lookup(<%=vArticleQty.getArticleId()%>)">
   </td>
   <td><small><%=vArticleQty.getArticleName()%></small></td>

   <!-- Кол-во WMS -->
   <td align="right" onclick="openqty(<%=vArticleQty.getArticleId()%>)"><%=vArticleQty.getQtySum()==0?"":vArticleQty.getQtySum()%></td>

   <!-- Кол-во факт -->
   <td align="right"><%=vArticleQty.getFactQuantity()==null?"":vArticleQty.getFactQuantity()%></td>

   <!-- Кол-во 1С -->
   <td align="right" style="color: <%=vArticleQty.getHostQuantity()==null?"gray":vArticleQty.getHostQuantity().intValue()==vArticleQty.getQtySum().intValue()?"gray":"red" %>"><%=vArticleQty.getHostQuantity()==null?"":vArticleQty.getHostQuantity() %></td>

   <!-- Дельта WMS-Факт -->
   <td align="right"><%=vArticleQty.getdWmsFact()==0?"":vArticleQty.getdWmsFact() %></td>

   <!-- Дельта WMS-1С -->
   <td align="right"><%=vArticleQty.getdWmsHost()==0?"":vArticleQty.getdWmsHost() %></td>

   <!-- Дельта Факт-1С -->
   <td align="right"><%=vArticleQty.getdFactHost()==0?"":vArticleQty.getdFactHost() %></td>
   </tr>
<% 		}

	}
%>

</table>

<input style="border: none" type="text" id="startloader" disabled value=<%=session.getAttribute("startloader")%> >

</form>
</body>
</html>
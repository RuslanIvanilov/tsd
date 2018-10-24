<%
	System.out.println("GUI/history_list.jsp");
%>

<%@page import="java.util.*"%>
<%@page import="ru.defo.model.views.Varticleqty"%>
<%@page import="ru.defo.controllers.ArticleController"%>
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

<title>WMS Дэфо Артикулы</title>
<script>
function setfilter(articlefilter){
	document.location="${pageContext.request.contextPath}/gui/ArticleQtyList?articlefilter="
			+document.getElementById('articlefilter').value.replace("#","%23").replace("@","%40").replace("+","%2B").replace("*","%2A")
							 +"&articledescfilter="+document.getElementById('articledescfilter').value
							 +"&qtyfilter="+document.getElementById('qtyfilter').value
							 +"&rowcount="+document.getElementById('rowcount').value
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


</script>
</head>
<body>

<h3>Список артикулов</h3>
<p>

<input type="button" id="btnBack" value="Назад" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'">
<br><small>Показывать строк не более: </small><input type="text" id="rowcount" value="<%=session.getAttribute("rowcount")%>" onchange="setfilter()" size="2" onkeyup = 'this.value=parseInt(this.value) | 0'>
</p>
<form>
<table id="arttable" border="1px">
<tr bgcolor="#DCDCDC">
	<!-- <td align="center"> </td> -->
	<td align="center">#</td>
	<td align="center"><b>Артикул Но.</b></td>
	<td align="center"><b>Наименование</b></td>
	<td align="center"><b>Кол-во</b></td>
</tr>

<tr>
	<!-- <td align="center"><input type="button" id="mark_all" onclick="setMarkAll()"></td>  -->
	<td></td>
	<td><input size="9" 	type="text" id="articlefilter" 		onchange="setfilter()" value="<%=session.getAttribute("articlefilter")%>"></td>
	<td><input size="47" 	type="text" id="articledescfilter" 	onchange="setfilter()" value="<%=session.getAttribute("articledescfilter")%>"></td>
	<td><input 				type="text" id="qtyfilter" 			onchange="setfilter()" value="<%=session.getAttribute("qtyfilter")%>"></td>
</tr>



<%
    int i = 0;
	List<Varticleqty> vArticleQtyList = new ArticleController().getVarticleQtyList(session.getAttribute("articlefilter").toString(),
			                                                                       session.getAttribute("articledescfilter").toString(),
			                                                                       session.getAttribute("qtyfilter").toString(),
			                                                                       session.getAttribute("rowcount").toString());

	if(vArticleQtyList != null){
		for (Varticleqty vArticleQty : vArticleQtyList) {
			i++;
%>
<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
  <!-- <td align="center"><input type="checkbox" id=<%=vArticleQty.getArticleId()%>></td>  -->
   <td align="center"><small><%=i %></small></td>
   <td align="left"><%=vArticleQty.getArticleCode()==null?"":vArticleQty.getArticleCode()%></td>
   <td><small><%=vArticleQty.getArticleName()%></small></td>
   <td align="right" onclick="document.location='${pageContext.request.contextPath}/gui/UnitQuantList?articleid=<%=vArticleQty.getArticleId()%>'"><%=vArticleQty.getQtySum()%></td>
   </tr>
<% 		}

	}
%>

</table>


</form>
</body>
</html>
<%
	System.out.println("GUI/unit_quant_list.jsp");
%>

<%@page import="java.util.*"%>
<%@page import="ru.defo.model.views.Vunitinfo"%>
<%@page import="ru.defo.controllers.ArticleController"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">

<title>WMS Дэфо Артикулы</title>
<script>
function setflt(){
	//alert('inhere!');
	document.getElementById('chk').value = Date.now();

	//document.location=window.location+'&areaflt='+document.getElementById('areaflt').value;

	document.location="${pageContext.request.contextPath}/gui/UnitQuantList?"
		+"articleid=<%=session.getAttribute("articleid")==null?"":session.getAttribute("articleid")%>"
		+"&unitflt="+document.getElementById('unitflt').value
		+"&binflt="+document.getElementById('binflt').value
		+"&areaflt="+document.getElementById('areaflt').value
		+"&rackflt="+document.getElementById('rackflt').value
		+"&levelflt="+document.getElementById('levelflt').value
		+"&qtyflt="+document.getElementById('qtyflt').value
		+"&skuflt="+document.getElementById('skuflt').value
		+"&qstateflt="+document.getElementById('qstateflt').value
		+"&needchkflt="+document.getElementById('needchkflt').value
		+"&crdateflt="+document.getElementById('crdateflt').value
		+"&surnameflt="+document.getElementById('surnameflt').value
		+"&fstnameflt="+document.getElementById('fstnameflt').value
		+"&advflt="+document.getElementById('advflt').value
		+"&advposflt="+document.getElementById('advposflt').value
		+"&expdateflt="+document.getElementById('expdateflt').value
		+"&groupflt="+document.getElementById('groupflt').value
		<% if(session.getAttribute("articleid")==null) {%>
		+"&articleflt="+document.getElementById('articleflt').value.replace("#","%23").replace("@","%40").replace("+","%2B").replace("*","%2A")
		+"&artnameflt="+document.getElementById('artnameflt').value
		<% } %>
		+"&rowcount="+document.getElementById('rowcount').value
		;

}

</script>
</head>
<body>
<% if(session.getAttribute("articleid")!=null) {%>
<h3>Поддоны и места с товаром: ${articlecode} <small><i>${articlename}</i> [${articleparentcode}]</small><br></h3>
<% } %>
<p>
<input type="button" id="btnHome" title="В главное меню" value="&#x1f3e0" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'">
<% if(session.getAttribute("backpage")!= null) {%>
<input type="button" id="btnBack" value="Назад" onclick="document.location.href='${pageContext.request.contextPath}/${backpage}'">
<% } %>
<br><small>Показывать строк не более: </small><input type="text" id="rowcount" value="<%=session.getAttribute("rowcount")%>" onchange="setflt()" size="2" onkeyup = 'this.value=parseInt(this.value) | 0'>
</p>
<form>

<%
List<Vunitinfo> vUnitInfoList = new ArticleController().getVunitinfoListByArticleId(session.getAttribute("articleid")==null?"":session.getAttribute("articleid").toString(),
																			        session.getAttribute("unitflt").toString(),
																			        session.getAttribute("binflt").toString(),
																			        session.getAttribute("areaflt").toString(),
																			        session.getAttribute("rackflt").toString(),
																			        session.getAttribute("levelflt").toString(),
																			        session.getAttribute("qtyflt").toString(),
																			        session.getAttribute("skuflt").toString(),
																			        session.getAttribute("qstateflt").toString(),
																			        session.getAttribute("needchkflt").toString(),
																			        session.getAttribute("crdateflt").toString(),
																			        session.getAttribute("surnameflt").toString(),
																			        session.getAttribute("fstnameflt").toString(),
																			        session.getAttribute("advflt").toString(),
																			        session.getAttribute("advposflt").toString(),
																			        session.getAttribute("expdateflt").toString(),
																			        session.getAttribute("groupflt").toString(),
																			        session.getAttribute("articleflt").toString(),
																			        session.getAttribute("artnameflt").toString(),
																			        session.getAttribute("rowcount").toString() );
int sumQty = 0;
if(vUnitInfoList != null){
	for (Vunitinfo vUnitInfo : vUnitInfoList) {
		if(vUnitInfo.getQuantity() != null)  sumQty = sumQty+vUnitInfo.getQuantity().intValue();
	}
	session.setAttribute("sumqty", sumQty);
}

%>

<table id="arttable" border="1px">
<tr bgcolor="#DCDCDC">
	<!-- <td align="center"> </td> -->
	<td align="center">#</td>

	<td align="center"><b>Поддон</b></td>
	<td align="center"><b>Ячейка</b></td>
	<td align="center"><small><b>Зона</b></small></td>
	<td align="center"><small><b>Стеллаж</b></small></td>
	<td align="center"><small><b>Этаж</b></small></td>
	<% if(session.getAttribute("articleid")==null) {%>
	<td align="center"><b>Артикул</b></td>
	<td align="center"><small><b>Наименование товара</b></small></td>
	<% } %>

	<% if(session.getAttribute("sumqty")!=null) {%>
	<td align="center" title="Всего: ${sumqty}"><b>Кол-во</b></td>
	<% } else { %>
	<td align="center"><b>Кол-во </b></td>
	<% } %>
	<td align="center"><b>Упак.</b></td>
	<td align="center"><small><b>Сост. Качества</b></small></td>
	<td align="center"><small><b>Нужн. проверка</b></small></td>
	<td align="center"><b>Дата создания</b></td>
	<td align="center"><b>Фамилия</b></td>
	<td align="center"><b>Имя</b></td>
	<td align="center"><b>Приемка Но.</b></td>
	<td align="center"><small><b>Строка Но.</b></small></td>
	<td align="center"><b>Дата док-та</b></td>
	<td align="center"><b>Товарн. Группа</b></td>

</tr>

<tr>
	<td></td>

	<td><input size="10" type="text" id="unitflt" onchange="setflt()" value="<%=session.getAttribute("unitflt")%>"></td>
	<td><input size="12" type="text" id="binflt" onchange="setflt()" value="<%=session.getAttribute("binflt")%>"></td>
	<td><input size="2" type="text" id="areaflt" onchange="setflt()" value="<%=session.getAttribute("areaflt")%>"></td>
	<td><input size="4" type="text" id="rackflt" onchange="setflt()" value="<%=session.getAttribute("rackflt")%>"></td>
	<td><input size="2" type="text" id="levelflt" onchange="setflt()" value="<%=session.getAttribute("levelflt")%>"></td>
	<% if(session.getAttribute("articleid")==null) {%>
	<td><input size="8" type="text" id="articleflt" onchange="setflt()" value="<%=session.getAttribute("articleflt")%>"></td>
	<td><input size="30" type="text" id="artnameflt" onchange="setflt()" value="<%=session.getAttribute("artnameflt")%>"></td>
	<% } %>
	<td><input size="4" type="text" id="qtyflt" onchange="setflt()" value="<%=session.getAttribute("qtyflt")%>"></td>
	<td><input size="2" type="text" id="skuflt" onchange="setflt()" value="<%=session.getAttribute("skuflt")%>"></td>
	<td><input size="4" type="text" id="qstateflt" onchange="setflt()" value="<%=session.getAttribute("qstateflt")%>"></td>
	<td><input size="4" type="text" id="needchkflt" onchange="setflt()" value="<%=session.getAttribute("needchkflt")%>"></td>
	<td><input  		type="date" id="crdateflt" onchange="setflt()" value="<%=session.getAttribute("crdateflt")%>"></td>
	<td><input size="8" type="text" id="surnameflt" onchange="setflt()" value="<%=session.getAttribute("surnameflt")%>"></td>
	<td><input size="8" type="text" id="fstnameflt" onchange="setflt()" value="<%=session.getAttribute("fstnameflt")%>"></td>
	<td><input size="10" type="text" id="advflt" onchange="setflt()" value="<%=session.getAttribute("advflt")%>"></td>
	<td><input size="2" type="text" id="advposflt" onchange="setflt()" value="<%=session.getAttribute("advposflt")%>"></td>
	<td><input 			type="date" id="expdateflt" onchange="setflt()" value="<%=session.getAttribute("expdateflt")%>"></td>
	<td><input size="4" type="text" id="groupflt" onchange="setflt()" value="<%=session.getAttribute("groupflt")%>"></td>


</tr>

<%
    int i = 0;
	if(vUnitInfoList != null){
		for (Vunitinfo vUnitInfo : vUnitInfoList) {
			i++;

%>
<%

   if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
   <td align="center"><small><%=i %></small></td>



   <% if(session.getAttribute("articleid")==null) {%>
   <td align="center" title="<%=vUnitInfo.getArticleCode()==null?"":vUnitInfo.getArticleCode()%> <%=vUnitInfo.getArticleName()==null?"":vUnitInfo.getArticleName().replaceAll("\"", "")%>"><%=vUnitInfo.getUnitCode()==null?"":vUnitInfo.getUnitCode()%></td>
   <% } else { %>
   <td align="center"><%=vUnitInfo.getUnitCode()==null?"":vUnitInfo.getUnitCode()%></td>
   <% } %>
   <td align="center"><%=vUnitInfo.getBinCode()==null?"":vUnitInfo.getBinCode()%></td>
   <td align="center"><%=vUnitInfo.getAreaCode()==null?"":vUnitInfo.getAreaCode()%></td>
   <td align="center"><%=vUnitInfo.getRackNo()==null?"":vUnitInfo.getRackNo()%></td>
   <td align="center"><%=vUnitInfo.getLevelNo()==null?"":vUnitInfo.getLevelNo()%></td>
   <% if(session.getAttribute("articleid")==null) {%>
   <td align="center"><%=vUnitInfo.getArticleCode() ==null?"":vUnitInfo.getArticleCode()%></td>
   <td align="center"><small><%=vUnitInfo.getArticleName()==null?"":vUnitInfo.getArticleName()%></small></td>
   <% } %>
   <td align="right"><%=vUnitInfo.getQuantity()==null?"":vUnitInfo.getQuantity()%></td>
   <td align="center"><small><%=vUnitInfo.getSkuName()==null?"":vUnitInfo.getSkuName()%></small></td>
   <td align="center"><%=vUnitInfo.getQstateName()==null?"":vUnitInfo.getQstateName()%></td>
   <td align="center"><%=vUnitInfo.getNeedCheck().booleanValue()==false?"":"Да"%></td>
   <td align="center"><small><%=vUnitInfo.getCreateDate()==null?"":vUnitInfo.getCreateDate()%></small></td>
   <td align="left"><small><%=vUnitInfo.getSurname()==null?"":vUnitInfo.getSurname()%></small></td>
   <td align="left"><small><%=vUnitInfo.getFirstName()==null?"":vUnitInfo.getFirstName()%></small></td>
   <td align="center"><%=vUnitInfo.getAdviceCode()==null?"":vUnitInfo.getAdviceCode()%></td>
   <td align="center"><%=vUnitInfo.getAdvicePosId()==null?"":vUnitInfo.getAdvicePosId()%></td>
   <td align="center"><small><%=vUnitInfo.getExpectedDate()==null?"":vUnitInfo.getExpectedDate()%></small></td>
   <td align="center"><small><%=vUnitInfo.getVendorGroupName()==null?"":vUnitInfo.getVendorGroupName()%></small></td>


   </tr>
<% 		}



	}
%>

</table>



</form>

 <input type="text" id="chk">

</body>
</html>
<%@page import="ru.defo.util.HibernateUtil"%>
<%@page import="ru.defo.model.WmVendorGroup"%>
<%
	System.out.println("GUI/advice_doc.jsp");
%>

<%@page import="java.util.*"%>
<%@page import="ru.defo.model.views.Vorderpos"%>
<%@page import="ru.defo.model.WmAdvice"%>
<%@page import="ru.defo.model.WmArticle"%>
<%@page import="ru.defo.model.WmSku"%>
<%@page import="ru.defo.model.WmAdvicePos"%>
<%@page import="ru.defo.model.WmStatus"%>
<%@page import="ru.defo.model.WmAdviceType"%>
<%@page import="ru.defo.controllers.AdviceController"%>
<%@page import="ru.defo.controllers.StatusController"%>
<%@page import="ru.defo.controllers.PreAdviceController"%>
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

<title>WMS Дэфо Складская Приемка</title>



<script>
function moveToFactQtyDetails(orderid, orderposid){
	document.location="${pageContext.request.contextPath}/gui/PickList?orderid="+orderid+"&orderposid="+orderposid;
}

function moveToCtrlQtyDetails(orderid, orderposid){
	document.location="${pageContext.request.contextPath}/gui/CheckList?orderid="+orderid+"&orderposid="+orderposid;
}

function setfilter(){
	document.location="${pageContext.request.contextPath}/gui/AdviceDoc?adviceid=${adviceid}"
							 +"&posflt="+document.getElementById('posflt').value
							 +"&articleidflt="+document.getElementById('articleidflt').value.replace("#","%23").replace("@","%40").replace("+","%2B").replace("*","%2A")
							 +"&artdescrflt="+document.getElementById('artdescrflt').value
							 +"&skuflt="+document.getElementById('skuflt').value
							 +"&expqtyflt="+document.getElementById('expqtyflt').value
							 +"&factqtyflt="+document.getElementById('factqtyflt').value
							 +"&statusposflt="+document.getElementById('selectsts').options[document.getElementById('selectsts').selectedIndex].value
							 +"&showbc="+document.getElementById('showbc').value
							 +"&vendoridflt="+document.getElementById('selectvendor').options[document.getElementById('selectvendor').selectedIndex].value;
}

function goBack(){
	document.location.href='${pageContext.request.contextPath}/${backpage}'
	//history.back()
}

function toDel(orderCode){
	if(confirm('Удалить этот документ складской приемки?'))
		document.location.href='${pageContext.request.contextPath}/${delpage}';
}

function switchArticleBC(pShowBc){

	if(pShowBc===1){
		document.getElementById('showbc').value = 0;
	} else {
		document.getElementById('showbc').value = 1;
	}


	setfilter();
}

function getShowBc(){
	return document.getElementById('showbc').value;
}

</script>



</head>
<body>

<h3>Складская приемка</h3>
<pre>
<input type="button" id="btnHome" title="В главное меню" value="&#x1f3e0" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'"> <input type="button" id="btnBack" value="Назад" onclick="goBack()">		  	     <%if(session.getAttribute("userid")!=null && new PermissionController().hasPerm4WmOrderDelete(session.getAttribute("userid").toString())) {%> <input type="button" id="btnDel" value="Удалить" onclick="toDel()"> <% } %>
</pre>
<form>
<%
WmAdvice advice = null;

if(session.getAttribute("adviceid") != null){
	Long advId = Long.valueOf(session.getAttribute("adviceid").toString());
 	advice = new AdviceController().getAdviceByAdviceId(advId);
 	//order = HibernateUtil.getSession().get(WmOrder.class, ordId);
 	session.setAttribute("ordercode",advice.getAdviceCode());

%>
<!-- <p style= "font-family: UpcBwrP72Tt"><%=advice.getAdviceCode()%></p>   -->

<!--  -->

<table id="docheader" border="0px">
<tr>
	<td align="left"><%=advice.getAdviceType().getDescription()%> Но.</td>
	<td><%=advice.getAdviceCode()%></td>
	<td title="<%=advice.getAdviceId()%>" align="left">
	<img class="img-responsive"
src="http://toolki.com/en/barcode/?foreground=000000&amp;code=<%=advice.getAdviceCode()%>&amp;font_size=0&amp;module_width=0.6&amp;module_height=10.0&amp;text_distance=5.0&amp;background=FFFFFF&amp;quiet_zone=6.5&amp;center_text=True&amp;type=ean13"
alt="">
	</td>
</tr>
<tr>
	<td align="left">Дата Приемки</td><td align="left"><%=advice.getExpectedDate().toLocaleString().substring(0, 10)%></td>
	<td><%=new PreAdviceController().getClientDocDescription(advice.getAdviceId()) %></td>
</tr>
<tr>
	<td align="left">Статус</td><td align="left"><%=advice.getStatus().getDescription()%></td>
</tr>
<tr>
	<td align="left">Док</td><td align="left"><%=advice.getBin()==null?"":advice.getBin().getBinCode()%></td>
	<td align="right">
	<% if(new PermissionController().harPerm4ShowBC(session.getAttribute("userid").toString())) {%>
	<input type="button" id="btnBCshow" value="штрих-кода товаров" onclick="switchArticleBC(${showbc})">
	<% } %>
	</td>

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
	<% if( session.getAttribute("showbc").toString().contains("1")){ %>
		<td align="center"><b>Штрих-код</b></td>
	<% } %>
	<td align="center"><small><b>Наименование</b></small></td>
	<td align="center"><b>Упаковка</b></td>
	<td align="center"><small><b>Ожид. кол-во</b></small></td>
	<td align="center"><small><b>Факт. кол-во</b></small></td>
	<td align="center"><b>Статус</b></td>
	<td align="center">Группа</td>
</tr>

<tr>
<td></td>
<td><input size="4" style="border : none" type="text" id="posflt" onchange="setfilter()" value="<%=session.getAttribute("posflt")%>"></td>
<td><input style="border : none" type="text" id="articleidflt" onchange="setfilter()" value="<%=session.getAttribute("articleidflt")%>"></td>
<% if( session.getAttribute("showbc").toString().contains("1")){ %>
		<td></td>
	<% } %>
<td><input style="border : none" type="text" id="artdescrflt" onchange="setfilter()" value="<%=session.getAttribute("artdescrflt")%>"></td>
<td><input size="1" style="border : none" type="text" id="skuflt" onchange="setfilter()" value="<%=session.getAttribute("skuflt")%>"></td>
<td><input size="1" style="border : none" type="text" id="expqtyflt" onchange="setfilter()" value="<%=session.getAttribute("expqtyflt")%>"></td>
<td><input size="1" style="border : none" type="text" id="factqtyflt" onchange="setfilter()" value="<%=session.getAttribute("factqtyflt")%>"></td>
<td>
<select style="border : none" id="selectsts" onchange="setfilter()">
		<option value="" class='option'></option>
	<%

	Long statusIdFlt = 0L;
	List<WmStatus> statusList = new PreAdviceController().getStatusList();
	for(WmStatus status : statusList){

		if(!session.getAttribute("statusposflt").toString().isEmpty()){
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
<td><select style="border : none" id="selectvendor" onchange="setfilter()">
		<option value="" class='option'></option>
		<!-- <option value="-1" class='option'><пусто></option>  -->
	<%

	Long vendorIdFlt = 0L;
	List<WmVendorGroup> vendorGroupList = new ArticleController().getVendorGroupList();

	vendorGroupList.add(0,new WmVendorGroup(-1L, "<пусто>"));
	for(WmVendorGroup vendorGroup : vendorGroupList){

		if(!session.getAttribute("vendoridflt").toString().isEmpty()){
			vendorIdFlt = Long.valueOf(session.getAttribute("vendoridflt").toString());
		}

		if(vendorIdFlt==vendorGroup.getVendorGroupId())	{
		%>
			<option selected value='<%=vendorGroup.getVendorGroupId()%>' class='option'><%=vendorGroup.getDescription()%></option>
		<% } else { %>
			<option value='<%=vendorGroup.getVendorGroupId()%>' class='option'><%=vendorGroup.getDescription()%></option>
		<% } %>
	<%
	}
	%>
	</select>
</tr>

<%
	ArticleController artCtrl = new ArticleController();
	int i = 0;
	List<WmAdvicePos> advicePosList = null;
	WmArticle article = null;
	WmSku sku = null;
	WmStatus status = null;
	WmVendorGroup vendorGroup = null;
	if(session.getAttribute("adviceid") != null)
		advicePosList = new AdviceController().getAdvicePosList(session);

	if(advicePosList != null)
	{
		for(WmAdvicePos pos : advicePosList){
			i++;
			article = new ArticleController().getArticle(pos.getArticleId());
			sku = new ArticleController().getBaseSkuByArticleId(pos.getArticleId());
			status = new StatusController().getById(pos.getStatusId());
			vendorGroup = new ArticleController().getVendorGroupByArticle(article);
%>
<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
   <td align="center"><small><%=i %></small></td>
   <td align="center" title="OrderId : <%=pos.getAdviceId()%> Line : <%=pos.getAdvicePosId()%>"><%=pos.getAdvicePosId()==null?"":pos.getAdvicePosId()%></td>
   <td align="center"><%=article.getArticleCode()==null?"":article.getArticleCode()%></td>
   <% if(session.getAttribute("showbc").toString().contains("1")){ %>
		<!-- <td align="center"><small><%=artCtrl.getBcByArticleId(pos.getArticleId()) ==null?"":artCtrl.getBcByArticleId(pos.getArticleId())%></small></td> -->
		<td align="center" title='<%=artCtrl.getBcByArticleId(pos.getArticleId()) ==null?"":artCtrl.getBcByArticleId(pos.getArticleId())%>'>

			<!-- <img class="img-responsive"
src="http://toolki.com/en/barcode/?foreground=000000&amp;code=<%=artCtrl.getBcByArticleId(pos.getArticleId()) ==null?"":artCtrl.getBcValueByArticleId(pos.getArticleId()) %>&amp;font_size=0&amp;module_width=0.6&amp;module_height=10.0&amp;text_distance=5.0&amp;background=FFFFFF&amp;quiet_zone=6.5&amp;center_text=True&amp;type=ean13"
alt="">  -->

<img class="img-responsive" src="http://toolki.com/en/barcode/?foreground=000000&amp;code=<%=artCtrl.getBcValueByArticleId(pos.getArticleId())%>;font_size=0&amp;module_width=0.4&amp;module_height=6.0&amp;text_distance=1.0&amp;background=FFFFFF&amp;quiet_zone=6.5&amp;center_text=True&amp;type=code39" alt="">

		</td>

	<% } %>
   <td align="left"><small><%=article==null?"":article.getDescription()%></small></td>
   <td align="center"><small><%=sku==null?"":sku.getDescription()%></small></td>
   <td align="right"><small><%=pos.getExpectQuantity()==null?"":pos.getExpectQuantity()%></small></td>
   <%
   if(pos.getFactQuantity()!=null)
   {   %>
   <td align="right" onclick="moveToFactQtyDetails(<%=pos.getAdviceId()%>, <%=pos.getAdvicePosId()%>)"><small><%=pos.getFactQuantity()==0?"":pos.getFactQuantity()%></small></td>
   <% } else { %>
   <td align="right"><small></small></td>
   <% } %>




   <td align="center"><small><%=status==null?"":status.getDescription()%></small></td>

   <td align="center"><small><%=vendorGroup==null?"":vendorGroup.getDescription()%></small></td>
<% 		}
	}
%>
</table>
</form>

<input type="hidden" id="showbc" value="${showbc}">

</body>
</html>
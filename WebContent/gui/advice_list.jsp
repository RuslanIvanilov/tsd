<%
	System.out.println("GUI/advice_list.jsp");
%>

<%@page import="java.util.*"%>
<%@page import="ru.defo.model.WmPreAdvice"%>
<%@page import="ru.defo.model.WmAdviceType"%>
<%@page import="ru.defo.model.WmStatus"%>
<%@page import="ru.defo.model.WmCar"%>
<%@page import="ru.defo.managers.DeliveryManager"%>
<%@page import="ru.defo.managers.CarManager"%>
<%@page import="ru.defo.controllers.PreAdviceController"%>
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

<title>WMS Дэфо Приемки</title>
<script>
function showWmPreAdvice(adviceid){
		document.location="${pageContext.request.contextPath}/gui/PreAdviceDoc?adviceid="+adviceid;
}

function showWmAdvice(adviceid){
	document.location="${pageContext.request.contextPath}/gui/AdviceDoc?adviceid="+adviceid;
}

function setfilter(){

	document.location="${pageContext.request.contextPath}/gui/PreAdviceList?adviceflt="
			+document.getElementById('adviceflt').value.replace("#","%23").replace("@","%40").replace("+","%2B").replace("*","%2A")
							 +"&clientdocflt="+document.getElementById('clientdocflt').value
							 +"&dateflt="+document.getElementById('dateflt').value
							 +"&typeflt="+document.getElementById('select').options[document.getElementById('select').selectedIndex].value
							 +"&statusflt="+document.getElementById('selectsts').options[document.getElementById('selectsts').selectedIndex].value
							 +"&wmsshptflt="+document.getElementById('wmsshptflt').value
							 +"&rowcount="+document.getElementById('rowcount').value
							 +"&marked="+document.getElementById('marked').value
							 +"&expdate="+document.getElementById('expdate').value
							 +"&wmadvicelink="+document.getElementById('wmadvicelink').value
							 +"&clientdescrflt="+document.getElementById('clientdescrflt').value
							 +"&carflt="+document.getElementById('carflt').value
							 +"&carmarkflt="+document.getElementById('carmarkflt').value
							 ;
}

function setMarkedOnly(){
	var artlist = [];

	list = document.getElementById("advicetable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			advlist.push(list[i].getElementsByTagName("td")[0].childNodes[0].id);
		}
	}
	setfilter();

}

function setMarkAll(){

	list = document.getElementById("advicetable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		 if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			 document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).click();
		 } else {
		 	document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked = "true";
		 }
	}
}

function createAdvice(){
	markedCount = 0;
	var marked = [];

	if(document.getElementById('expdate').value.length < 1){
		alert("Не указано на какую дату объединяются документы в приемку!");
		throw "stop";
	}

	list = document.getElementById("advicetable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){

			//alert("["+list[i].getElementsByTagName("td")[7].innerText +"]");

			if(list[i].getElementsByTagName("td")[7].innerText.length  > 0){
				alert('Документ '+
						list[i].getElementsByTagName("td")[2].innerHTML
				     +' уже включен в складскую приемку '+list[i].getElementsByTagName("td")[7].innerHTML
				     +' ! Никуда более его включить нельзя!'
				     );
			} else {
				if(list[i].getElementsByTagName("td")[8].innerText.length  > 0){
					marked = null;
					markedCount = 0;
					alert("Нельзя запускать в работу документ имеющий Ошибку!");
				}else{
					marked[markedCount] = list[i].getElementsByTagName("td")[0].childNodes[0].id ;
					markedCount =markedCount+1;
				}
			}
		}
	}

	if(markedCount==0){
		alert('Не выбрано документов для объединения в складскую приемку!');
	} else {
	 	action = confirm('Создать складскую приемку по выбранным документам?');
	 	if(action) document.getElementById('marked').value = marked;
	 	setfilter();
	}

}

function openCreateOrderDlg(adviceCode, preAdviceId){
	var wmAdviceLink = prompt('Введите номер Складской Приемки для присвоения документу : '+adviceCode, '');
	if(wmAdviceLink != null){
		document.getElementById('marked').value = preAdviceId;
		document.getElementById('wmadvicelink').value = wmAdviceLink;
		setfilter();
	}
}

function showError(errorId){
	if(errorId > 0){
		alert("Нельзя запускать в работу документ имеющий ошибки!");
		return 1;
	} else { return 0; }
}


</script>
</head>
<body>

<h3>Список заданий на <u>Приемку</u></h3>
<p>

<input type="button" id="btnBack" value="Назад" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'">
<% if(new PermissionController().harPerm4Joining(session.getAttribute("userid").toString())) {%>
<strong style="padding-left: 30px">Документы на дату</strong> <input type="date" id="expdate">
<input type="button" id="btnCreateAdv" value="Объединить в приемку" onclick="createAdvice()">
<% } else { %>
<input type="hidden" id="expdate">
<% } %>
<br>
<br><small>Показывать строк не более: </small><input type="text" id="rowcount" value="<%=session.getAttribute("rowcount")%>" onchange="setfilter()" size="2" onkeyup = 'this.value=parseInt(this.value) | 0'>
</p>
<form>
<table id="advicetable" border="1px">
<tr bgcolor="#DCDCDC">
	<td align="center"> </td>
	<td align="center">#</td>
	<td align="center"><b>Документ Но.</b></td>
	<td align="center"><b>Клиент Документ Но.</b></td>
	<td align="center"><b>Дата документа</b></td>
	<td align="center"><b>Тип отгрузки</b></td>
	<td align="center"><b>Статус</b></td>
	<td align="center"><small><b>Складская приемка Но.</b></small></td>
	<td align="center"><b>Код ошибки</b></td>
	<td align="center"><b>Комментарии</b></td>
	<td align="center"><b>Клиент Наименование</b></td>
	<td align="center"><b>Машина</b></td>
	<td align="center"><b>Марка машины</b></td>

</tr>

<tr>
	<td align="center"><input type="button" id="mark_all" onclick="setMarkAll()"></td>
	<td></td>
	<td><input style="border : none" size="20" 	type="text" id="adviceflt" 		onchange="setfilter()" value="<%=session.getAttribute("adviceflt")%>"></td>
	<td><input style="border : none" size="12" 	type="text" id="clientdocflt" 	onchange="setfilter()" value="<%=session.getAttribute("clientdocflt")%>"></td>
	<td><input style="border : none" type="date" id="dateflt" 			onchange="setfilter()" value="<%=session.getAttribute("dateflt")%>"></td>
	<td><select style="border : none" id="select" onchange="setfilter()">
		<option value="" class='option'></option>
	<%
	Long typeIdFlt = 0L;
	List<WmAdviceType> adviceTypeList = new PreAdviceController().getAdviceTypeList();
	for(WmAdviceType adviceType : adviceTypeList){

		if(!session.getAttribute("typeflt").toString().isEmpty()){
			typeIdFlt = Long.valueOf(session.getAttribute("typeflt").toString());
		}

		if(typeIdFlt==adviceType.getAdviceTypeId())	{
		%>
			<option selected value='<%=adviceType.getAdviceTypeId()%>' class='option'><%=adviceType.getDescription()%></option>
		<% } else { %>
			<option value='<%=adviceType.getAdviceTypeId()%>' class='option'><%=adviceType.getDescription()%></option>
		<% } %>
	<%
	}
	%>
	</select></td>
	<td>
	<small>
	<select style="border : none" id="selectsts" onchange="setfilter()">
		<option value="" class='option'></option>
	<%

	Long statusIdFlt = 0L;
	List<WmStatus> statusList = new PreAdviceController().getStatusList();
	for(WmStatus status : statusList){

		if(!session.getAttribute("statusflt").toString().isEmpty()){
			statusIdFlt = Long.valueOf(session.getAttribute("statusflt").toString());
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
	</small>
	</td>
	<td><input style="border : none" size="12" 	type="text" id="wmsshptflt" 		onchange="setfilter()" value="<%=session.getAttribute("wmsshptflt")%>"></td>
	<td></td>
	<td></td>
	<td><input Style="border : None" Size="20" 	Type="text" Id="clientdescrflt" 	Onchange="setfilter()" Value="<%=session.getAttribute("clientdescrflt")%>"></td>


	<td><input Style="border : None" Size="6" 	Type="text" Id="carflt" 	Onchange="setfilter()" Value="<%=session.getAttribute("carflt")%>"></td>
	<td><input Style="border : None" Size="4" 	Type="text" Id="carmarkflt" 	Onchange="setfilter()" Value="<%=session.getAttribute("carmarkflt")%>"></td>
</tr>

<%
    int i = 0;
	List<WmPreAdvice> preAdviceList = new PreAdviceController().getPreAdviceList(session);

	if(preAdviceList != null){
		for (WmPreAdvice preAdvice : preAdviceList) {
			i++;
			WmCar car= new WmCar();
			if(preAdvice.getCarId() != null)
				car = new CarManager().getCarById(preAdvice.getCarId());
%>

<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
   <td align="center"><input style="border : none" type="checkbox" id=<%=preAdvice.getAdviceId()%> title="OrderId : <%=preAdvice.getAdviceId()%>"></td>
   <td align="center"><small><%=i %></small></td>
   <td align="right" title="OrderId : <%=preAdvice.getAdviceId()%>"><%=preAdvice.getAdviceCode()==null?"":preAdvice.getAdviceCode()%>
   	<input type="button" id="btnLookup" value="->" onclick="showWmPreAdvice(<%=preAdvice.getAdviceId() %>)"">
   </td>

   <td><small><%=preAdvice.getClientDocCode()%></small></td>
   <td align="center"><small><%=preAdvice.getExpectedDate()%></small></td>
   <td align="center"><%=preAdvice.getAdviceType().getDescription()%></td>
   <td Size="4" align="center"><small><%=preAdvice.getStatus().getDescription()%></small></td>
   <% if(preAdvice.getWmAdviceLink()!=null) {%>
   <!-- <td align="left" onclick="showWmOrder(<%=preAdvice.getWmAdviceLink() %>)"><%=preAdvice.getWmAdviceLink()%></td>  -->
   <td align="right">
   <%=preAdvice.getWmAdviceLink()%>
   <input type="button" id="btnLookup" value="->" onclick="showWmAdvice(<%=preAdvice.getWmAdviceLink() %>)"">
   </td>
   <% } else { %>
   <!-- <td align="left" onclick="openCreateOrderDlg('<%=preAdvice.getAdviceCode()%>', '<%=preAdvice.getAdviceId()%>')"></td>  -->
   <td align="right">
   <input type="button" id="btnCreate" value="..." onclick="if(showError('<%=preAdvice.getErrorId()==null?"0":preAdvice.getErrorId()%>')===0) openCreateOrderDlg('<%=preAdvice.getAdviceCode()%>', '<%=preAdvice.getAdviceId()%>')">
   </td>
   <% } %>

   <td align="center" style="color:red"><b><%=preAdvice.getErrorId()==null?"":preAdvice.getErrorId()%></b></td>
   <td align="left"><%=preAdvice.getErrorComment()==null?"":preAdvice.getErrorComment()%></td>
   <td align="left"><small><%= preAdvice.getClientDocDescription() %></small></td>

   <td align="center"><small><%=car.getCarCode()==null?"":car.getCarCode()%></small></td>
   <td align="center"><small><%=car.getCarMark()==null?"":car.getCarMark()%></small></td>

   </tr>
<% 		}

	}
%>

</table>

<input type="hidden" id="marked">
<input type="hidden" id="wmadvicelink">
</form>
</body>
</html>
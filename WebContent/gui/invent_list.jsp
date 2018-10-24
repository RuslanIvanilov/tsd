<%@page import="ru.defo.managers.StatusManager"%>
<%
	System.out.println("GUI/invent_list.jsp");
%>

<%@page import="java.util.*"%>
<%@page import="ru.defo.model.WmInventory"%>
<%@page import="ru.defo.model.WmStatus"%>
<%@page import="ru.defo.model.WmUser"%>
<%@page import="ru.defo.model.WmInventoryInitiator"%>
<%@page import="ru.defo.managers.UserManager"%>
<%@page import="ru.defo.controllers.StatusController"%>
<%@page import="ru.defo.controllers.InventoryController"%>
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

<title>WMS Дэфо Инвентаризационные ведомости</title>
<script>
function showInventory(inventid){
	document.location="${pageContext.request.contextPath}/gui/InventDoc?inventid="+inventid;
}

function setfilter(){

	document.location="${pageContext.request.contextPath}/gui/InventList?"
							 +"inventflt="+document.getElementById('inventflt').value
							 +"&initerflt="+document.getElementById('initerflt').value
							 +"&dateflt="+document.getElementById('dateflt').value
							 +"&creatorflt="+document.getElementById('creatorflt').value
							 +"&binsflt="+document.getElementById('binsflt').value
							 +"&statusflt="+document.getElementById('selectsts').options[document.getElementById('selectsts').selectedIndex].value
							 +"&hidehost="+document.getElementById('hidehost').checked
							 +"&areaflt="+document.getElementById('areaflt').value
							 +"&rackfromflt="+document.getElementById('rackfromflt').value
							 +"&racktoflt="+document.getElementById('racktoflt').value
							 +"&levelfromflt="+document.getElementById('levelfromflt').value
							 +"&leveltoflt="+document.getElementById('leveltoflt').value
							 +"&binfromflt="+document.getElementById('binfromflt').value
							 +"&bintoflt="+document.getElementById('bintoflt').value

							 +"&rowcount="+document.getElementById('rowcount').value
							 ;
}

function setMarkedOnly(){
	var artlist = [];

	list = document.getElementById("shpttable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			advlist.push(list[i].getElementsByTagName("td")[0].childNodes[0].id);
		}
	}
	setfilter();

}

function setMarkAll(){

	list = document.getElementById("shpttable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		 if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			 document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).click();
		 } else {
		 	document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked = "true";
		 }
	}
}

function onCreate(){
	document.location="${pageContext.request.contextPath}/gui/InventCreate";
}

function onDel(){
	markedCount = 0;
	var marked = [];

	list = document.getElementById("shpttable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			marked[markedCount] = list[i].getElementsByTagName("td")[0].childNodes[0].id ;
			markedCount =markedCount+1;
		}
	}

	if(markedCount==0){
		alert('Не выбрано Инвентаризаций для удаления!');
	} else {
	 	action = confirm('Удалить отмеченные документы инвентаризации?');
	 	if(action) document.getElementById('marked').value = marked;
	 	document.location="${pageContext.request.contextPath}/gui/InventDel?marked="+document.getElementById('marked').value;
	 	return;
	}
}

function onPrint(){
	window.open("${pageContext.request.contextPath}/gui/InventReport");
	return;
}

</script>
</head>
<body>

<h3>Список заданий по инвентаризации</h3>
<p>

<input type="button" id="btnBack" value="Назад" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" id="createinvent" value="Создать" onclick="onCreate()">
&nbsp;
<input type="button" id="delinvent" value="Удалить" onclick="onDel()">
&nbsp;
<input type="button" id="printreport" value="Печать ведомости" onclick="onPrint()">
<br>
<br><small>Показывать строк не более: </small><input type="text" id="rowcount" value="<%=session.getAttribute("rowcount")%>" onchange="setfilter()" size="2" onkeyup = 'this.value=parseInt(this.value) | 0'>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<small>Скрыть загрузки данных из ERP-системы <input type="checkbox" id="hidehost"
<%
   if(session.getAttribute("hidehost").toString().equals("true")){
  %> checked <% } %> onchange="setfilter()"></small>
</p>
<form>
<table id="shpttable" border="1px">
<tr bgcolor="#DCDCDC">
	<td align="center"> </td>
	<td align="center">#</td>
	<td align="center"><small><b>Документ Но.</b></small></td>
	<td align="center"><b>Инициатор</b></td>
	<td align="center"><b>Дата создания</b></td>
	<td align="center"><b>Создал</b></td>
	<td align="center"><b>Статус</b></td>
	<td align="center"><b>Время начала</b></td>
	<td align="center"><b>Время окончания</b></td>
	<td align="center"><b>Ячейки</b></td>

	<td align="center"><small><b>Зона Фильтр</b></small></td>
	<td align="center"><small><b>Ряд С Фильтр</b></small></td>
	<td align="center"><small><b>Ряд По Фильтр</b></small></td>
	<td align="center"><small><b>Этаж С Фильтр</b></small></td>
	<td align="center"><small><b>Этаж По Фильтр</b></small></td>
	<td align="center"><small><b>Ячейка С Фильтр</b></small></td>
	<td align="center"><small><b>Ячейка По Фильтр</b></small></td>
</tr>

<tr>
	<td align="center"><input type="button" id="mark_all" onclick="setMarkAll()"></td>
	<td></td>
	<td><input style="border : none" size="8" 	type="text" id="inventflt" 		onchange="setfilter()" value="<%=session.getAttribute("inventflt")%>"></td>
	<td><input style="border : none" size="12" 	type="text" id="initerflt" 	onchange="setfilter()" value="<%=session.getAttribute("initerflt")%>"></td>
	<td><input style="border : none" type="date" id="dateflt" 			onchange="setfilter()" value="<%=session.getAttribute("dateflt")%>"></td>
	<td><input Style="border : None" Size="16" 	Type="text" Id="creatorflt" 	Onchange="setfilter()" Value="<%=session.getAttribute("creatorflt")%>"></td>
	<td><input Style="border : None" Size="20" 	Type="text" Id="binsflt" 	Onchange="setfilter()" Value="<%=session.getAttribute("binsflt")%>"></td>
	<td>
	<small>
	<select style="border : none" id="selectsts" onchange="setfilter()">
		<option value="" class='option'></option>
	<%

	Long statusIdFlt = 0L;
	List<WmStatus> statusList = new StatusController().getStatusList();
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

	<td></td>
	<td></td>

	<td><input style="border : none" size="3" 	type="text" id="areaflt" 			onchange="setfilter()" value="<%=session.getAttribute("areaflt")%>"></td>
	<td><input style="border : none" size="3" 	type="text" id="rackfromflt" 		onchange="setfilter()" value="<%=session.getAttribute("rackfromflt")%>"></td>
	<td><input style="border : none" size="3" 	type="text" id="racktoflt" 			onchange="setfilter()" value="<%=session.getAttribute("racktoflt")%>"></td>
	<td><input style="border : none" size="3" 	type="text" id="levelfromflt" 		onchange="setfilter()" value="<%=session.getAttribute("levelfromflt")%>"></td>
	<td><input style="border : none" size="3" 	type="text" id="leveltoflt" 		onchange="setfilter()" value="<%=session.getAttribute("leveltoflt")%>"></td>
	<td><input style="border : none" size="3" 	type="text" id="binfromflt" 		onchange="setfilter()" value="<%=session.getAttribute("binfromflt")%>"></td>
	<td><input style="border : none" size="3" 	type="text" id="bintoflt" 			onchange="setfilter()" value="<%=session.getAttribute("bintoflt")%>"></td>

</tr>


<%
	WmStatus status = null;
	InventoryController inventCtrl = new InventoryController();
    int i = 0;
	List<WmInventory> inventList = inventCtrl.getInventList(session);

    for (WmInventory invent : inventList) {
			i++;
			WmInventoryInitiator initiator = inventCtrl.getInitiator(invent.getInitiatorId());
			status = new StatusController().getById(invent.getStatusId());
			WmUser user = new UserManager().getUserById(invent.getCreateUser());
%>

<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
   <td align="center"><input style="border : none" type="checkbox" id=<%=invent.getInventoryId()%> title="InventoryId : <%=invent.getInventoryId()%>"></td>
   <td align="center"><small><%=i %></small></td>
   <td align="right" title="InventoryId : <%=invent.getInventoryId()%>"><%=invent.getInventoryId()==null?"":invent.getInventoryId()%>
   	<input type="button" id="btnLookup" value="->" onclick="showInventory(<%=invent.getInventoryId() %>)"">
   </td>

   <td align="center"><small><%=initiator==null?invent.getInitiatorId():initiator.getDescription()%></small></td>
   <td align="center"><small><%=invent.getCreateDate()%></small></td>

   <td align="center"><%=(user==null?"":user.getSurname())+" "+(user==null?"":user.getFirstName())%></td>
   <td Size="4" align="center"><small><%=status.getDescription()%></small></td>

   <td align="center"><small><%=invent.getStartDate()==null?"":invent.getStartDate().toString().substring(0, 10)%></small></td>
   <td align="center"><small><%=invent.getEndDate()==null?"":invent.getEndDate().toString().substring(0, 10)%></small></td>
   <td align="center"><small></small></td>

   <td align="right"><%=invent.getAreaCodeFilter()==null?"":invent.getAreaCodeFilter()%></td>
   <td align="right"><%=invent.getRackFromFilter()==null?"":invent.getRackFromFilter()%></td>
   <td align="right"><%=invent.getRackToFilter()==null?"":invent.getRackToFilter()%></td>
   <td align="right"><%=invent.getLevelFromFilter()==null?"":invent.getLevelFromFilter()%></td>
   <td align="right"><%=invent.getLevelToFilter()==null?"":invent.getLevelToFilter()%></td>
   <td align="right"><%=invent.getBinFromFilter()==null?"":invent.getBinFromFilter()%></td>
   <td align="right"><%=invent.getBinToFilter()==null?"":invent.getBinToFilter()%></td>

   </tr>
<%

	}
%>

</table>

<input type="hidden" id="marked">
<input type="hidden" id="hidehostt" value="${hidehost}">
</form>
</body>
</html>
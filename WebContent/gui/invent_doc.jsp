<%@page import="ru.defo.util.DefaultValue"%>
<%@page import="ru.defo.managers.InventoryManager"%>
<%
	System.out.println("GUI/invent_doc.jsp");
%>

<%@page import="java.util.*"%>
<%@page import="ru.defo.model.WmInventory"%>
<%@page import="ru.defo.model.WmInventoryPos"%>
<%@page import="ru.defo.model.WmStatus"%>
<%@page import="ru.defo.model.WmUser"%>
<%@page import="ru.defo.model.WmBin"%>
<%@page import="ru.defo.model.WmUnit"%>
<%@page import="ru.defo.model.WmQualityState"%>
<%@page import="ru.defo.model.WmInventoryInitiator"%>
<%@page import="ru.defo.controllers.InventoryController"%>
<%@page import="ru.defo.managers.StatusManager"%>
<%@page import="ru.defo.managers.UserManager"%>
<%@page import="ru.defo.managers.BinManager"%>
<%@page import="ru.defo.managers.UnitManager"%>
<%@page import="ru.defo.managers.QualityStateManager"%>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<!-- <meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate'> -->
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>

<title>WMS Дэфо Задание Инвентаризации</title>

<script>
function setfilter(){
	document.location="${pageContext.request.contextPath}/gui/InventDoc?inventid=${inventid}"
		+"&posflt="+document.getElementById('posflt').value
		+"&executorflt="+document.getElementById('executorflt').value
		+"&qualityflt="+document.getElementById('qualityflt').options[document.getElementById('qualityflt').selectedIndex].value
		+"&beforeqtyflt="+document.getElementById('beforeqtyflt').value
		+"&afterqtyflt="+document.getElementById('afterqtyflt').value
		+"&binflt="+document.getElementById('binflt').value
		+"&unitflt="+document.getElementById('unitflt').value
		+"&artcodeflt="+document.getElementById('artcodeflt').value.replace("#","%23").replace("@","%40").replace("+","%2B").replace("*","%2A")
		+"&artdescrflt="+document.getElementById('artdescrflt').value
		+"&marked="+document.getElementById('marked').value
		+"&executorselect="+document.getElementById('executorselect').value
		+"&statusflt="+document.getElementById('statusflt').options[document.getElementById('statusflt').selectedIndex].value
		//+"&rowcount="+document.getElementById('rowcount').value
			;

}

function setExecutor(){
	markedCount = 0;
	var marked = [];

	list = document.getElementById("doclines").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked)
		{
			marked[markedCount] = list[i].getElementsByTagName("td")[0].childNodes[0].id ;
			markedCount =markedCount+1;
		}
	}


	if(markedCount==0){
		alert('Не выбрано строк для назначения исполнителя!');
	} else {

		if(document.getElementById('executorselect').value.length < 1){
			//action = confirm('Сотрудник не выбран. У отмеченных строк снять исполнителя?');
	 		//if(!action) throw "stop";
	 		alert('Не указан исполнитель выбранных заданий!');
	 		throw "stop";
		}

	 	action = confirm('Назначить исполнителя по выбранным строкам?');
	 	if(action) document.getElementById('marked').value = marked;
	 	setfilter();
	};


}



function goBack(){
	document.location.href='${pageContext.request.contextPath}/${backpage}'

}

function setMarkAll(){
	list = document.getElementById("doclines").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		 if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			 document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).click();
		 } else {
		 	document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked = "true";
		 }
	}
}

</script>
</head>

<body>

<h3>Задание на Инвентаризацию № ${inventid}</h3>
<pre>
<input type="button" id="btnHome" title="В главное меню" value="&#x1f3e0" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'"> <input type="button" id="btnBack" value="Назад" onclick="goBack()">
</pre>

<%
    InventoryController invCtrl =  new InventoryController();
	WmInventory invent = invCtrl.getInventById(session.getAttribute("inventid"));
    WmInventoryInitiator initer = invCtrl.getInitiator(invent.getInitiatorId());
    WmStatus status = new StatusManager().getStatusById(invent.getStatusId());
    WmUser user = new UserManager().getUserById(invent.getCreateUser());
%>

<table id="docheader" border="0px">
<tr><td align="right">Инвентаризация Но.: </td><td><%=invent.getInventoryId()%></td></tr>
<tr><td align="right">Инициатор: </td><td><%=initer.getDescription()%></td></tr>
<tr><td align="right" title="statusId: <%=invent.getStatusId()%>">Статус: </td><td><%=status.getDescription()%></td></tr>
<tr><td align="right">Создал: </td><td><%=(user==null?"":user.getSurname())+" "+(user==null?"":user.getFirstName())%></td></tr>
<% if(invent.getStartDate()!=null || invent.getEndDate()!=null){ %>
<tr><td align="right"><b>Время</b> начало: </td><td><%=invent.getStartDate()==null?"":invent.getStartDate().toLocaleString()%></td><td> окончание: </td><td><%=invent.getEndDate()==null?"":invent.getEndDate().toLocaleString()%></td></tr>
<% } %>
</table>
<p>
<b><big>Строки</big></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<input list="executorlist" id="executorselect">
   <datalist id="executorlist">
    <option value="<%=DefaultValue.EMPTY_OPTION%>"></option>
   <% List<WmUser> userList = new UserManager().getAll(true);
      for(WmUser u : userList){ %>
    <option value='<%=u.getSurname()+" "+u.getFirstName() %>'></option>
   <% } %>
   </datalist>

<input type="button" id="setexecutor" value="Назначить исполнителя" onclick="setExecutor()">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

</p>
<form>
<table id="doclines" border="1px">
<tr bgcolor="#DCDCDC">
	<td align="center"> </td>
	<td align="center">#</td>
	<td align="center"><b>Строка</b></td>
	<td align="center"><b>Исполнитель</b></td>
	<td align="center"><small><b>Состояние<br>качества</b></small></td>
	<td align="center"><small><b>Кол-во<br>ожидаемое</b></small></td>
	<td align="center"><small><b>Кол-во<br>фактическое</b></small></td>
	<td align="center"><b>Ячейка</b></td>
	<td align="center"><b>Поддон</b></td>
	<td align="center"><b>Товар</b></td>
	<td align="center"><b>Наименование</b></td>
	<td align="center"><b>Статус</b></td>
</tr>


<tr>
	<td align="center"><input type="button" id="mark_all" onclick="setMarkAll()"></td>
	<td></td>
	<td><input style="border : none" size="8" 	type="text" id="posflt" onchange="setfilter()" value="<%=session.getAttribute("posflt")%>"></td>
	<td><input style="border : none" size="16" 	type="text" id="executorflt" onchange="setfilter()" value="<%=session.getAttribute("executorflt")%>"></td>
	<td>
	   <select style="border : none" id="qualityflt" onchange="setfilter()">
	   		<option value="" class='option'></option>
	   		<%
	   			Long stateId = 0L;
	   			List<WmQualityState> statList = new QualityStateManager().getAll();
	   		    for(WmQualityState state : statList)
	   		    {

	   		    	if(!session.getAttribute("qualityflt").toString().isEmpty()){
	   		    		stateId = Long.valueOf(session.getAttribute("qualityflt").toString());
	   				}

	   		    	if(stateId==state.getQualityStateId())	{
	   		 		%>
	   		 			<option selected value='<%=state.getQualityStateId()%>' class='option'><%=state.getDescription()%></option>
	   		 		<% } else { %>
	   		 			<option value='<%=state.getQualityStateId()%>' class='option'><%=state.getDescription()%></option>
	   		 		<% } %>

	   		<%  } %>

	   </select>
	</td>
	<td><input style="border : none" size="8" 	type="text" id="beforeqtyflt" onchange="setfilter()" value="<%=session.getAttribute("beforeqtyflt")%>"></td>
	<td><input style="border : none" size="8" 	type="text" id="afterqtyflt" onchange="setfilter()" value="<%=session.getAttribute("afterqtyflt")%>"></td>
	<td><input style="border : none" size="16" 	type="text" id="binflt" onchange="setfilter()" value="<%=session.getAttribute("binflt")%>"></td>
	<td><input style="border : none" size="12" 	type="text" id="unitflt" onchange="setfilter()" value="<%=session.getAttribute("unitflt")%>"></td>
	<td><input style="border : none" size="10" 	type="text" id="artcodeflt" onchange="setfilter()" value="<%=session.getAttribute("artcodeflt")%>"></td>
	<td><input style="border : none" size="70" 	type="text" id="artdescrflt" onchange="setfilter()" value="<%=session.getAttribute("artdescrflt")%>"></td>

	<td>
	<!--  <input style="border : none" size="2" 	type="text" id="statusflt" onchange="setfilter()" value="<%=session.getAttribute("statusflt")%>"> -->
	<select style="border : none" id="statusflt" onchange="setfilter()">
	   		<option value="" class='option'></option>
	   		<%
	   			Long statusId = 0L;
	   			List<WmStatus> statusList = new StatusManager().getAll();
	   		    for(WmStatus st : statusList)
	   		    {

	   		    	if(!session.getAttribute("statusflt").toString().isEmpty()){
	   		    		stateId = Long.valueOf(session.getAttribute("statusflt").toString());
	   				}

	   		    	if(stateId==st.getStatusId())	{
	   		 		%>
	   		 			<option selected value='<%=st.getStatusId()%>' class='option'><%=st.getDescription()%></option>
	   		 		<% } else { %>
	   		 			<option value='<%=st.getStatusId()%>' class='option'><%=st.getDescription()%></option>
	   		 		<% } %>

	   		<%  } %>

	   </select>
	</td>

</tr>

<%
	int i = 0;
	WmBin bin = null;
	WmUnit unit = null;
	WmQualityState state = null;

	List<WmInventoryPos> posList = new InventoryManager().getInventoryPosListByFilter(invent, session);
	for(WmInventoryPos pos : posList)
   	{
	   i++;
	   user = null;
	   if(pos.getCreateUser()!= null) user = new UserManager().getUserById(pos.getExecutorId());
	   if(pos.getBinId()!= null) bin = new BinManager().getBinById(pos.getBinId());
	   if(pos.getUnitId()!= null){unit = new UnitManager().getUnitById(pos.getUnitId());} else { unit = null; }
	   if(pos.getQualityStateId()!=null){ state = new QualityStateManager().getQualityStateById(pos.getQualityStateId()); } else { state = null; }
%>

<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
<td align="center"><input style="border : none" type="checkbox" id=<%=pos.getInventoryPosId()%> title="InventoryPosId : <%=pos.getInventoryPosId()%>"></td>

<td align="center"><small><%=i %></small></td>
<td align="center" title="InventoryId : <%=pos.getInventoryId()%> posId: <%=pos.getInventoryPosId()%>"><%=pos.getInventoryPosId()%></td>
<td align="center"><small><%=(user==null?"":user.getSurname())+" "+(user==null?"":user.getFirstName())%></small></td>

<td align="center"><%=state==null?"":state.getDescription()%></td>
<td align="right"><%=pos.getQuantityBefore()==null?"":pos.getQuantityBefore()%></td>
<td align="right"><%=pos.getQuantityAfter()==null?"":pos.getQuantityAfter()%></td>

<td align="center" title="binId: <%=pos.getBinId()%>"><%=bin==null?"":bin.getBinCode()%></td>
<td align="center" title="unitId: <%=pos.getUnitId()%>"><%=unit==null?"":unit.getUnitCode()%></td>
<td align="center"><%=pos.getArticleCode()==null?"":pos.getArticleCode()%></td>
<td align="left"><small><%=pos.getArticleDescript()==null?"":pos.getArticleDescript()%></small></td>
<td alert="center"><%=new StatusManager().getStatusById(pos.getStatusId()).getDescription() %></td>
</tr>

<% } %>

</table>

<input type="hidden" id="marked" value=''>
</form>
</body>
</html>
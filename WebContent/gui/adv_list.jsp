<%
	System.out.println("GUI/adv_list.jsp");
%>

<%@page import="java.util.*"%>
<%@page import="ru.defo.util.DefaultValue"%>
<%@page import="ru.defo.model.views.Vadvice"%>
<%@page import="ru.defo.model.WmStatus"%>
<%@page import="ru.defo.controllers.AdviceController"%>
<%@page import="ru.defo.controllers.StatusController"%>
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

<title>WMS Дэфо Список приемок</title>
<script>
function setfilter(advlist, carcode){
	document.location.href="AdviceGUIStart?date_filter="+document.getElementById('date_filter').value
			+"&doc_filter="+document.getElementById('doc_filter').value
			+"&status_filter="+document.getElementById('select_status').options[document.getElementById('select_status').selectedIndex].value
			+"&car_filter="+document.getElementById('car_filter').value
			+"&dok_filter="+document.getElementById('dok_filter').value
			+"&comment_filter="+document.getElementById('comment_filter').value
			+"&error_filter="+document.getElementById('error_filter').value
			+"&advlist[]="+advlist
			+"&carcode="+carcode
			+"&dok="+document.getElementById('dok').value
			;
}

function setMarkedOnly(){
	var car = setCarCode();
	var advlist = [];

	if(car != null){
	list = document.getElementById("advtable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			advlist.push(list[i].getElementsByTagName("td")[0].childNodes[0].id);
		}
	}
	setDok();
	setfilter(advlist, car);
	}
}

function setMarkAll(){
	list = document.getElementById("advtable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			setfilter();
		} else {
			document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked = "true";
			//document.getElementById('btnCar').disabled = false;
		}

	}
}

function setDok(){
	var dok = prompt("Номер дока:", "");

	if(dok > 1 || dok <= 8){
		document.getElementById('dok').value = dok;
	}

}


function setCarCode(){

	var carCode = prompt("Номер машины:", "");
	if(carCode != null || carCode != ""){
		return carCode;
	}
		return null;
}

function showPreAdvice(adviceid){
	document.location="${pageContext.request.contextPath}/gui/PreAdviceDoc?adviceid="+adviceid;
}

</script>
</head>
<body>

<h3>Список приемок из <%=DefaultValue.HOST_NAME%></h3>
<p>
<input type="button" id="btnHome" title="В главное меню" value="&#x1f3e0" onclick="document.location.href='${pageContext.request.contextPath}/Mmenu'">
<input type="button" id="btnBack" value="Назад" onclick="document.location.href='Mmenu'">
<input type="button" id="btnCar" value="Указать машину и док" onclick="setMarkedOnly()">
</p>
<form>
<table id="advtable" border="1px">
<tr bgcolor="#DCDCDC">
<td align="center"> </td>
<td align="center"><b>Документ Но.</b></td>
<td align="center"><b>Дата создания</b></td>
<td align="center"><b>Статус</b></td>
<td align="center"><b>Машина Но.</b></td>
<td align="center"><b>Док</b></td>
<td align="center"><b>Комментарий</b></td>
<td align="center"><b>Ошибка</b></td>
</tr>

<tr>
<td align="center"><input type="button" id="mark_all" onclick="setMarkAll()"></td>
<td><input style="border : none" size="16" type="text" id="doc_filter"  onchange="setfilter()" value="<%=session.getAttribute("doc_filter")%>"></td>
<td><input type="date" id="date_filter" onchange="setfilter()" value="<%=session.getAttribute("date_filter")%>"></td>
<td>
<select id="select_status" onchange="setfilter()">
	<option value="0"> </option>
<%
	List<WmStatus> statusList = new StatusController().getStatusList();
	for(WmStatus status : statusList){
		if(session.getAttribute("status_filter")!="")
			if(status.getStatusId() == Long.decode(session.getAttribute("status_filter").toString())){
%>
    			<option value="<%=status.getStatusId()%>" selected><%=status.getDescription()%></option>
<% 			} else { %>
				<option value="<%=status.getStatusId()%>"><%=status.getDescription()%></option>
<%
       		}
 	}
%>
</select>
</td>

<td><input type="text" id="car_filter" onchange="setfilter()" value="<%=session.getAttribute("car_filter")%>"></td>
<td><input type="text" id="dok_filter" onchange="setfilter()" value="<%=session.getAttribute("dok_filter")%>"></td>
<td><input type="text" id="comment_filter" onchange="setfilter()" value="<%=session.getAttribute("comment_filter")%>"></td>
<td><input type="text" id="error_filter" onchange="setfilter()" value="<%=session.getAttribute("error_filter")%>" title=" 0 - кроме ошибок &#10 1,2,3,N - номер ошибки &#10 >0 - только ошибки"></td>

</tr>

<%
    int i = 0;
	List<Vadvice> adviceList = new AdviceController().getAdviceList(true, session.getAttribute("date_filter").toString(),
			                                                              session.getAttribute("doc_filter").toString(),
			                                                              session.getAttribute("status_filter").toString(),
			                                                              session.getAttribute("car_filter").toString(),
			                                                              session.getAttribute("dok_filter").toString(),
			                                                              session.getAttribute("comment_filter").toString(),
			                                                              session.getAttribute("error_filter").toString()
			                                                        );
	if(adviceList != null){
		for (Vadvice advice : adviceList) {
			i++;
%>
<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
   <td align="center"><input type="checkbox" id=<%=advice.getAdviceId()%> onchange="document.getElementById('btnCar').disabled = false;" ></td>
   <td align="right" title="AdviceId : <%=advice.getAdviceId()%>"><%=advice.getAdviceCode()%> <input type="button" id="btnLookup" value="->" onclick="showPreAdvice(<%=advice.getAdviceId() %>)""></td>
   <td align="center"><small><%=advice.getExpectedDate()%></small></td>
   <td><%=advice.getStatus()%></td>
   <td align="center"><%=advice.getCarCode()==null?"":advice.getCarCode()%></td>
   <td align="center"><%=advice.getBinCode()==null?"":advice.getBinCode()%></td>
   <td><%=advice.getErrorComment()==null?"":advice.getErrorComment()%></td>
   <td align="center" style="color:red"><b><%=advice.getErrorId()==null?"":advice.getErrorId()%></b></td>
   </tr>
<% 		}

	}
%>
</table>



</form>
<input type="hidden" id="dok">
</body>
</html>
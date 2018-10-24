<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<%
	System.out.println("user_list.jsp");
%>
<%@page import="java.util.*"%>
<%@page import="ru.defo.model.WmUser"%>
<%@page import="ru.defo.controllers.UserController"%>
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

<title>WMS Дэфо Сотрудники</title>
<script>
function setfilter(articlefilter){
	document.location="${pageContext.request.contextPath}/UsersList?"
							 +"surnamefilter="+document.getElementById('surnamefilter').value
							 +"&firstnamefilter="+document.getElementById('firstnamefilter').value
							 +"&patronymfilter="+document.getElementById('patronymfilter').value
							 +"&positionfilter="+document.getElementById('positionfilter').value
							 +"&loginfilter="+document.getElementById('loginfilter').value
							 +"&blockfilter="+document.getElementById('blockfilter').value
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

function createUser(){
	//document.location="${pageContext.request.contextPath}/UserProcess?createuser=1";
	document.location="${pageContext.request.contextPath}/User?userflt=-1";
}

function lookup(userid){
	document.location="${pageContext.request.contextPath}/User?userflt="+userid;
}

function permissionList(){
	alert("Форма списка прав доступа");
}

</script>
</head>
<body>

<h3>Сотрудники</h3>
<p>

<input type="button" id="btnBack" value="Назад" onclick="document.location.href='${pageContext.request.contextPath}/${backpage}'">
<input type="button" id="btnCreate" value="Создать пользователя" onclick="createUser()">
<input type="button" id="btnPermList" value="Права доступа" onclick="permissionList()">
<br>
<br><small>Показывать строк не более: </small><input type="text" id="rowcount" value="<%=session.getAttribute("rowcount")%>" onchange="setfilter()" size="2" onkeyup = 'this.value=parseInt(this.value) | 0'>
</p>
<form>
<table id="arttable" border="1px">
<tr bgcolor="#DCDCDC">
	<!-- <td align="center"> </td> -->
	<td align="center">#</td>
	<td align="center"><b>Фамилия</b></td>
	<td align="center"><b>Имя</b></td>
	<td align="center"><b>Отчество</b></td>
	<td align="center"><b>Должность</b></td>
	<td align="center"><b>Логин</b></td>
	<td align="center" title="Блокирован/Работает"><small><b>Блок.</b></small></td>
</tr>

<tr>
	<!-- <td align="center"><input type="button" id="mark_all" onclick="setMarkAll()"></td>  -->
	<td></td>
	<td><input size="14" 	type="text" id="surnamefilter" 		onchange="setfilter()" value="<%=session.getAttribute("surnamefilter")%>"></td>
	<td><input size="9" 	type="text" id="firstnamefilter" 	onchange="setfilter()" value="<%=session.getAttribute("firstnamefilter")%>"></td>
	<td><input size="12"	type="text" id="patronymfilter" 	onchange="setfilter()" value="<%=session.getAttribute("patronymfilter")%>"></td>
	<td><input size="18" 	type="text" id="positionfilter" 	onchange="setfilter()" value="<%=session.getAttribute("positionfilter")%>"></td>
	<td><input size="9"		type="text" id="loginfilter" 		onchange="setfilter()" value="<%=session.getAttribute("loginfilter")%>"></td>
	<td><input size="2"		type="text" id="blockfilter" 		onchange="setfilter()" value="<%=session.getAttribute("blockfilter")%>" title="Фильтры: Y, Д, 1 - только блокированные; N, Н, 1 - только Неблокированные "></td>
</tr>



<%
    int i = 0;
	List<WmUser> userList = new UserController().getUsersList(session.getAttribute("surnamefilter").toString(),
															  session.getAttribute("firstnamefilter").toString(),
															  session.getAttribute("patronymfilter").toString(),
															  session.getAttribute("positionfilter").toString(),
															  session.getAttribute("loginfilter").toString(),
															  session.getAttribute("blockfilter").toString(),
															  session.getAttribute("rowcount").toString());

	if(userList != null){
		for (WmUser user : userList) {
			i++;
%>

<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
  <!-- <td align="center"><input type="checkbox" id=<%=user.getUserId()%></td>  -->
   <td align="center"><small><%=i %></small></td>
   <td align="right">
      <%=user.getSurname()==null?"":user.getSurname()%>
      <input style="border: none;" type="button" value="..." onclick="lookup(<%=user.getUserId()%>)">
   </td>
   <td align="left"><%=user.getFirstName()==null?"":user.getFirstName()%></td>
   <td align="left"><%=user.getPatronymic()==null?"":user.getPatronymic()%></td>
   <td align="left" style="font-size: 80%;"><%=user.getPosition()==null?"":user.getPosition()%></td>
   <td align="left"><%=user.getUserLogin()==null?"":user.getUserLogin()%></td>
   <td align="center"><input type="checkbox" id="b<%=user.getUserId()%>" <%=user.getBlocked()==true?"checked='checked'":"" %> onclick="return false"></td>
   </tr>
<% 		}

	}
%>

</table>


</form>
</body>
</html>
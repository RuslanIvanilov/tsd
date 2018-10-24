<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<%
	System.out.println("user.jsp");
%>
<%@page import="java.util.*"%>
<%@page import="ru.defo.model.WmUser"%>
<%@page import="ru.defo.model.WmPermission"%>
<%@page import="ru.defo.model.WmUserPermission"%>
<%@page import="ru.defo.controllers.UserController"%>
<%@page import="ru.defo.controllers.PermissionController"%>
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

<title>WMS Дэфо Сотрудник</title>
<script>
function setfilter(permlist){

	document.location="${pageContext.request.contextPath}/User?"
					 +"&save_mode="+document.getElementById('save_mode').value
					 +"&surnametxt="+document.getElementById('surnametxt').value
					 +"&positiontxt="+document.getElementById('positiontxt').value
					 +"&firstnametxt="+document.getElementById('firstnametxt').value
					 +"&logintxt="+document.getElementById('logintxt').value
					 +"&patronymtxt="+document.getElementById('patronymtxt').value
					 +"&birthtxt="+document.getElementById('birthtxt').value
					 +"&barcodetxt="+document.getElementById('barcodetxt').value
					 +"&emailtxt="+document.getElementById('emailtxt').value
					 +"&blockedtxt="+document.getElementById('blockedtxt').checked
					 +"&permlist[]="+permlist
					 ;


}

function setMarkedOnly(){
	var permlist = [];
	list = document.getElementById("permtable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			permlist.push(list[i].getElementsByTagName("td")[0].childNodes[0].id);
		}
	}
	setfilter(permlist);

}

function setMarkAll(){
	list = document.getElementById("permtable").getElementsByTagName("tr");
	for(var i=2; i<list.length; ++i)
	{
		if(document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked){
			setfilter();
		} else {
			document.getElementById(list[i].getElementsByTagName("td")[0].childNodes[0].id).checked = "true";
		}

	}
}

function deleteUser(){
	alert('Удалить сотрудника');
}

function saveUser()
{
	if(document.getElementById('barcodetxt').value==""){
		alert('Не заполнено обязательное поле "Штрих-код"');
		return;
	}
	
	if(document.getElementById('positiontxt').value==""){
		alert('Не заполнено обязательное поле "Должность"');
		return;
	}
	
	if(document.getElementById('birthtxt').value==""){
		alert('Не заполнено обязательное поле "Год Рождения"');
		return;
	}
	
	if(document.getElementById('logintxt').value==""){
		alert('Не заполнено обязательное поле "Логин"');
		return;
	}
	
	if(confirm('Сохранить введенные изменения?')){
		document.getElementById('save_mode').value = 1;
		setMarkedOnly();
		//setfilter();
	}else{
		document.getElementById('save_mode').value = "";
		alert('Нажата кнопка отмены.');
	}
}

</script>

<% WmUser user = new UserController().getUserById(session.getAttribute("userflt").toString()); %>
</head>
<body>
<p>
<pre>
<input type="button" id="btnBack" value="Назад" onclick="document.location.href='${pageContext.request.contextPath}/${backpage}'"> <input type="button" id="btnSave" value="Сохранить изменения" onclick="saveUser()">		 	   <!--  <input type="button" id="btnDelete" value="Удалить пользователя" onclick="deleteUser()"> -->
</pre>

<h3>Сотрудник</h3>

<form id="card">
<table border="0px">
<tr>
<td align="right">Фамилия:</td><td><input align="right" type="text" id="surnametxt" value="<%=user.getSurname()==null?"":user.getSurname()%>"></td>
<td align="right">email:</td><td><input type="text" id="emailtxt" value="<%=user.getEmailCode()==null?"":user.getEmailCode()%>"></td>
</tr>
<tr>
<td align="right">Имя:</td><td><input type="text" id="firstnametxt" value="<%=user.getFirstName()==null?"":user.getFirstName()%>"></td>
<td align="right">Логин:</td><td><input type="text" id="logintxt" value="<%=user.getUserLogin()==null?"":user.getUserLogin()%>" ></td>
</tr>
<tr>
<td align="right">Отчество:</td><td><input type="text" id="patronymtxt" value="<%=user.getPatronymic()==null?"":user.getPatronymic()%>"></td>
<td align="right">Штрих-код:</td><td><input type="text" id="barcodetxt" value='<%=user.getUserBarcode()==null?"":user.getUserBarcode()%>'></td>
</tr>

<tr>
<td align="right">Должность:</td><td><input type="text" id="positiontxt" value="<%=user.getPosition()==null?"":user.getPosition()%>"></td>
<!-- <td align="right">Пароль:</td><td><input type="password" id="pwdtxt" ></td> -->
</tr>

<tr>
<td align="right">Год Рождения:</td><td><input type="text" id="birthtxt" value='<%=user.getBirth()==null?"":user.getBirth()%>'></td>
<!-- <td align="right">Пароль повтор:</td><td><input type="password" id="pwdtxt2" ></td>  -->
</tr>

<tr>
<td align="right">Блокирован:</td><td><input type="checkbox" id="blockedtxt" <%=user.getBlocked()==true?"checked='checked'":"" %> ></td><td></td>
</tr>
</table>
</form>

</p>
<h3>Права доступа</h3>

<form>
<table id="permtable" border="1px">
<tr bgcolor="#DCDCDC">
	<td align="center"> </td>
	<td align="center">#</td>
	<td align="center"><b>Код</b></td>
	<td align="center"><b>Наименование</b></td>
	<td align="center"><b>Тип</b></td>
	<td align="center"><b>Назначил</b></td>
	<td align="center"><b>Дата назначения</b></td>

</tr>

<tr>
	<td align="center"><input type="button" id="mark_all" onclick="setMarkAll()"></td>
	<td></td>
	<td><input size="20" 	type="text" id="permcodeflt" 		onchange="setfilter()" 	value="<%=session.getAttribute("permcodeflt")%>"></td>
	<td><input size="45" 	type="text" id="desriptionflt" 	onchange="setfilter()" 		value="<%=session.getAttribute("desriptionflt")%>"></td>
	<td><input size="9" 	type="text" id="typeflt" 	onchange="setfilter()" 			value="<%=session.getAttribute("typeflt")%>"></td>
	<td><input size="12"	type="text" id="createrflt" 	onchange="setfilter()" 		value="<%=session.getAttribute("createrflt")%>"></td>
	<td><input size="18" 	type="text" id="createdateflt" 	onchange="setfilter()" 		value="<%=session.getAttribute("createdateflt")%>"></td>
</tr>

<%
    int i = 0;
	List<WmPermission> permList = new PermissionController().getAll();
	WmUserPermission userPerm = null;
	Long userId = Long.valueOf(session.getAttribute("userflt").toString());

	if(permList != null){
		for (WmPermission perm : permList) {
			i++;
			userPerm = new PermissionController().getUserPermission(userId, perm.getPermissionId());
%>

<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
   <td align="center"><input type="checkbox" id=<%=perm.getPermissionId()%> title="<%=userPerm==null?"Назначить":"ЗАБРАТЬ"%>"></td>
   <td align="center"><small><%=i %></small></td>
   <td align="right">
      <%=perm.getPermissionCode()%>
   </td>
   <td align="left"><%=perm.getDescription()%></td>
   <td align="left"><%=perm.getGroupCode()==null?"":perm.getGroupCode()%></td>
   <td align="left" style="font-size: 80%;"><%=userPerm==null?"":new UserController().getSFPbyId(userPerm.getCreateUser()) %></td>
   <td align="left"><%=userPerm==null?"":userPerm.getCreateDate().toLocaleString()%></td>

   </tr>
<% 		}

	}
%>

</table>


</form>

<input type="hidden" id="save_mode" name="save_mode">
<input type="hidden" id="marked">
</body>
</html>
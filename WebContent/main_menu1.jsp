<% System.out.println("main_menu1.jsp"); %>
<%@page import="java.util.*"%>
<%@page import="ru.defo.controllers.TsdPermissionController"%>
<%@page import="ru.defo.model.WmPermission"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">

	<meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate'>
    <meta http-equiv='expires' content='0'>
    <meta http-equiv='pragma' content='no-cache'>
<title>TSD</title>
<script>
	setTimeout(function() {
		document.getElementById('modulselect').focus();
	}, 10);
</script>
<script>
	function requestSelectedModule(userid) {
		var e = document.getElementById('modulselect');
	    document.location.href = "Mmenu?userid="+userid+"&module="+e.options[e.selectedIndex].value;
	}
</script>

<style>
option {
	padding: 0px; /* Поля вокруг текста */
	text-overflow: ellipsis; /* Добавляем многоточие */
}

select {
	float: none;
	overflow-y: scroll;
	height: 73%;
	width: 100%;
	font-size: 13px;
	padding: 0px;
	border: hidden;
}

body {

	margin-right: -15px;
	margin-bottom: -15px;
	overflow-y: hidden;
	overflow-x: hidden;
}
</style>

</head>
<body class="body">
<!--<form  style="zoom: 200%"  -->
	<form
		action='javascript:requestSelectedModule(<%=session.getAttribute("userid") %>)'
		method="post">
		<div
			style="width: 100%; overflow-x: none; overflow-y: none; height: 73%;">
			<select id="modulselect" size="11" class="select">
				<%	@SuppressWarnings("unchecked")
					List<Object> modulesList = (List<Object>) session.getAttribute("modules");
					Iterator<Object> iterator = modulesList.iterator();
					TsdPermissionController permCtrl = new TsdPermissionController((int) session.getAttribute("userid"));
					int i=0;
					while (iterator.hasNext()) {
						WmPermission permission = permCtrl.getPermission(Long.valueOf(iterator.next().toString()));
						if(i==0){
				%>
				<option selected value='<%=permission.getPermissionId()%>' class='option'><%=permission.getDescription()%></option>
				<%  } else {%>
				<option value='<%=permission.getPermissionId()%>' class='option'><%=permission.getDescription()%></option>
				<%
				}	i++; }
				%>
			</select>
		</div>


		<div class="div">
			<input
				style="display: inline; float: inherit; height: 20%; width: 45%;"
				type="button" id="btnCancel" name="btnCancel" value="Назад"
				onclick="location.href='Login' "> <input
				style="display: inline; float: inherit; height: 20%; width: 45%;"
				type="submit" id="btnOk" name="btnOk" value="Ок">
		</div>
	</form>
</body>
</html>
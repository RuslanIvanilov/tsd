<% System.out.println("userId : "+session.getAttribute("userid"));%>
<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="main_menu1.jsp"/>
<% } %>
<% System.out.println("main_menu.jsp"); %>
<%@page import="java.util.*"%>
<%@page import="ru.defo.controllers.TsdPermissionController"%>
<%@page import="ru.defo.model.WmPermission"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0 , maximum-scale=10, user-scalable=no">

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
	    document.location.href="Mmenu?userid="+userid+"&module="+e.options[e.selectedIndex].value;
	}



</script>

<style>
option {
	padding: 0px; /* Поля вокруг текста */
	text-overflow: ellipsis; /* Добавляем многоточие */
}

select {
    font-family: "Arial Narrow", Arial, sans-serif;
	float: none;
	overflow-y: scroll;
	height: 160px;
	width: 230px;
	font-size: 34px;
	padding: 0px;
}
</style>

</head>
<body>
	<form style="zoom: 200%"
		action='javascript:requestSelectedModule(<%=session.getAttribute("userid") %>)'
		method="post"
		>
		<p>
			<b>Доступные модули</b>
		</p>
		<div
			style="width: 230px; overflow-x: none; overflow-y: none; height: 160px;">
			<select id="modulselect" size="11" class="select">
				<%	@SuppressWarnings("unchecked")
					List<Object> modulesList = (List<Object>) session.getAttribute("modules");
					Iterator<Object> iterator = modulesList.iterator();
					WmPermission permission = null;
					TsdPermissionController permCtrl = new TsdPermissionController((int) session.getAttribute("userid"));
					int i=0;
					while (iterator.hasNext()) {
						 permission = permCtrl.getPermission(Long.valueOf( iterator.next().toString() ));
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


		<div style="overflow: hidden">
			<input
				style="display: inline; float: inherit; height: 52px; width: 100px;"
				type="button" id="btnCancel" name="btnCancel" value="Назад"
				onclick=" location.replace('Login') "> <input
				style="display: inline; float: inherit; height: 52px; width: 100px;"
				type="submit" id="btnOk" name="btnOk" value="Ок">
		</div>
	</form>
</body>
</html>
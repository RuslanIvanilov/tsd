<% if(session.getAttribute("userid")==null){%>
<jsp:forward page="index.jsp"/>
<%} %>

<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>

<% System.out.println("kv_list.jsp"); %>
<%@page import="java.util.*"%>
<%@page import="ru.defo.controllers.QuantController"%>
<%@page import="ru.defo.model.views.VQuantInfoShort"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
<script>
	setTimeout(function() {
		document.getElementById('dSelect').focus();
	}, 10);
</script>
<script>
	function requestSelectedState() {
		var e = document.getElementById('dSelect');
	    document.location.href = "${detailpage}?${selectedvalue}="+e.options[e.selectedIndex].value;
	}
</script>

<style>
option {
	padding: 0px; /* Поля вокруг текста */
	text-overflow: ellipsis; /* Добавляем многоточие */
}
<% if(session.getAttribute("oldbrowser") != null) {%>
select {
	float: none;
	overflow-y: scroll;
	overflow-x: auto;
	height: 70px;
	width: 110px;
	font-size: 16px;
}
<% } else {%>
select {
	float: none;
	overflow-y: scroll;
	overflow-x: auto;
	height: 160px;
	width: 230px;
	font-size: 30px;
}
<% } %>

</style>

</head>
<% if(session.getAttribute("oldbrowser") != null) {%>
<body class="cebody">
<form action='javascript:requestSelectedState()'
		method="post">
<%} else { %>
<body>
	<form style="zoom: 200%"
		action='javascript:requestSelectedState()'
		method="post">
<% } %>
		<p>
			<b><small>${listheader}</small></b>
		</p>

<% if(session.getAttribute("oldbrowser") != null) {%>
	<div style="width: 110px; overflow-x: none; overflow-y: none; height: 70px;">
<%} else { %>
	<div style="width: 230px; overflow-x: none; overflow-y: none; height: 160px;">
<% } %>
			<select id="dSelect" size="11" class="select">
				<%
					HashMap<Integer, String> map = (HashMap<Integer, String>) session.getAttribute("datalist");
					if(map != null)
						for(Map.Entry<Integer, String> entry: map.entrySet())
						{
				%>
							<option value='<%=entry.getKey() %>' class='option'><%=entry.getValue()%></option>
				<%
						}
				%>
			</select>
		</div>
<% if(session.getAttribute("oldbrowser") != null) {%>
		<div style="overflow: hidden">

			<input
				style="display: inline; float: inherit; height: 25px; width: 45%;"
				type="button" id="btnCancel" name="btnCancel" value="Назад"
				onclick="document.location.href ='${backpage}'">
				<input
				style="display: inline; float: inherit; height: 25px; width: 45%;"
				type="submit" id="btnOk" name="btnOk" value="${btnOkName}">

		</div>
<%} else { %>
		<div style="overflow: hidden">

			<input
				style="display: inline; float: inherit; height: 52px; width: 90px;"
				type="button" id="btnCancel" name="btnCancel" value="Назад"
				onclick="document.location.href ='${backpage}'">
				<input
				style="display: inline; float: inherit; height: 52px; width: 100px;"
				type="submit" id="btnOk" name="btnOk" value="${btnOkName}">

		</div>
<% } %>

	</form>
</body>
</html>
<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="qstate_list1.jsp"/>
<% } %>
<% System.out.println("qstate_list.jsp"); %>
<%@page import="java.util.*"%>
<%@page import="ru.defo.controllers.QualityStateController"%>
<%@page import="ru.defo.model.WmQualityState"%>
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
		document.getElementById('stateSelect').focus();
	}, 10);
</script>
<script>
	function requestSelectedState() {
		var e = document.getElementById('stateSelect');
	    document.location.href = "<%=session.getAttribute("actionpage")%>?state="+e.options[e.selectedIndex].value;
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
	height: 160px;
	width: 230px;
	font-size: 34px;
}
</style>

</head>
<body>
	<form style="zoom: 200%"
		action='javascript:requestSelectedState()'
		method="post">
		<br>
		<p>
			<b>Состояние качества</b>
		</p>
		<div
			style="width: 230px; overflow-x: none; overflow-y: none; height: 160px;">
			<select id="stateSelect" size="11" class="select">
				<%
				    List<WmQualityState> states = new QualityStateController().getQualityStateList();
					Iterator<WmQualityState> iterator = states.iterator();
					while (iterator.hasNext()) {
						WmQualityState wmQualityState = (WmQualityState) iterator.next();
				%>
				<option value='<%=wmQualityState.getQualityStateId()%>' class='option'><%=wmQualityState.getDescription()%></option>
				<%
					}
				%>
			</select>
		</div>


		<div style="overflow: hidden">
			<input
				style="display: inline; float: inherit; height: 52px; width: 110px;"
				type="button" id="btnCancel" name="btnCancel" value="Назад"
				onclick="javascript:history.back()"> <input
				style="display: inline; float: inherit; height: 52px; width: 110px;"
				type="submit" id="btnOk" name="btnOk" value="Ок">
		</div>
	</form>
</body>
</html>
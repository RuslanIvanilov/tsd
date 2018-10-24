<% System.out.println("qstate_list1.jsp"); %>
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
	height: 60px;
	width: 110px;
	font-size: 13px;
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
	<form
		action='javascript:requestSelectedState()'
		method="post">
		<p>
			<b>Состояние качества</b>
		</p>
		<div
			style="width: 110px; overflow-x: none; overflow-y: none; height: 60px;">
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
				style="display: inline; float: inherit; height: 25px; width: 40px;"
				type="button" id="btnCancel" name="btnCancel" value="Назад"
				onclick="javascript:history.back()"> <input
				style="display: inline; float: inherit; height: 25px; width: 40px;"
				type="submit" id="btnOk" name="btnOk" value="Ок">
		</div>
	</form>
</body>
</html>
<% System.out.println("inv_bin_frn_qst1.jsp"); %>
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
	    document.location.href = "InvBinFrnQSt?state="+e.options[e.selectedIndex].value;
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
	height: 70px;
	width: 110px;
	font-size: 16px;
}
</style>

</head>
<body>
	<form style="zoom: 200%"
		action='javascript:requestSelectedState()'
		method="post">
		<p>
		    <big>
			   <b>Состояние качества</b>
			</big>
		</p>
		<div
			style="width: 110px; overflow-x: none; overflow-y: none; height: 70px;">
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
				style="display: inline; float: inherit; height: 25px; width: 45%;"
				type="button" id="btnCancel" name="btnCancel" value="Назад"
				onclick="javascript:history.back()"> <input
				style="display: inline; float: inherit; height: 25px; width: 45%;"
				type="submit" id="btnOk" name="btnOk" value="Ок">
		</div>
	</form>
</body>
</html>
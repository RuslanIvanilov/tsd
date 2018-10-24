<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% System.out.println("unit_article_list1.jsp"); %>
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
		document.getElementById('quantSelect').focus();
	}, 10);
</script>
<script>
	function requestSelectedState() {
		var e = document.getElementById('quantSelect');
	    document.location.href = "QuantInfo?quantid="+e.options[e.selectedIndex].value;
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
	overflow-x: auto;
	height: 70px;
	width: 110px;
	font-size: 16px;
}
</style>

</head>
<body class="cebody">
	<form action='javascript:requestSelectedState()'
		method="post">
		<!-- <p>
			<b><big>На поддоне <%=session.getAttribute("unitcode") %></big></b>
		</p> -->
		<div
			style="width: 110px; overflow-x: none; overflow-y: none; height: 70px;">
			<select id="quantSelect" size="11" class="select">
				<%
				    List<VQuantInfoShort> quantList = new QuantController().getVQuantInfoListByUnit(String.valueOf(session.getAttribute("unitcode")));
					Iterator<VQuantInfoShort> iterator = quantList.iterator();
					while (iterator.hasNext()) {
						VQuantInfoShort vQuantInfoShort = (VQuantInfoShort) iterator.next();
				%>
				<option value='<%=vQuantInfoShort.getQuantId() %>' class='option'><%=vQuantInfoShort.getArticleCode()%> : <%=vQuantInfoShort.getQuantity()%>(<%=vQuantInfoShort.getQualityName()%>)</option>
				<%
					}
				%>
			</select>
		</div>


		<div style="overflow: hidden">
			<input
				style="display: inline; float: inherit; height: 25px; width: 45%;"
				type="button" id="btnCancel" name="btnCancel" value="Назад"
				onclick="document.location.href ='${backpage}'">
				<input
				style="display: inline; float: inherit; height: 25px; width: 45%;"
				type="submit" id="btnOk" name="btnOk" value="Изменить">
		</div>

	</form>
</body>
</html>
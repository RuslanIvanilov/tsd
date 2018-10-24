<% if(session.getAttribute("userid")==null){%>
<jsp:forward page="index.jsp"/>
<%} %>

<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="unit_article_list1.jsp"/>
<% } %>
<% System.out.println("unit_article_list.jsp"); %>
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
	height: 160px;
	width: 230px;
	font-size: 30px;
}
</style>

</head>
<body>
	<form style="zoom: 200%"
		action='javascript:requestSelectedState()'
		method="post">
		<p>
			<b><small>На поддоне <%=session.getAttribute("unitcode") %></small></b>
		</p>
		<div
			style="width: 230px; overflow-x: none; overflow-y: none; height: 160px;">
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
				style="display: inline; float: inherit; height: 52px; width: 90px;"
				type="button" id="btnCancel" name="btnCancel" value="Назад"
				onclick="document.location.href ='${backpage}'">
				<input
				style="display: inline; float: inherit; height: 52px; width: 100px;"
				type="submit" id="btnOk" name="btnOk" value="Изменить">
		</div>
	</form>
</body>
</html>
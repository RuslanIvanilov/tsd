<% System.out.println("advice_info1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
<style>
body {
	margin-right: -15px;
	margin-bottom: -15px;
	overflow-y: hidden;
	overflow-x: hidden;
}
</style>
</head>
<body class="body">
<form action="<%=session.getAttribute("savepage") %>" method="post">
<br>
<p style="width: 135px">
Приемка: <b><%=session.getAttribute("adviceCode")%></b><br>
док-т статус: <%=session.getAttribute("status")%><br>
<small>Накладная: <%=session.getAttribute("clientDocCode")%></small><br>
Док: <%=session.getAttribute("binCode")%><br>
А/м: <%=session.getAttribute("carCode")%> <small><%=session.getAttribute("carMark")%> <%=session.getAttribute("carModel")%></small>
</p>

<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 25px; width: 30px;" type="button" id="btnCancel" value="Назад" onclick="location.href='advice_list.jsp' ">
<input style="display: inline; float: inherit; height: 25px; width: 30px;" type="button" id="btnSplit" value="Строки" onclick=" location.href='AdvicePosList' ">
<input style="display: inline; float: inherit; height: 25px; width: 35px;" type="button" id="btnSplit" value="В работу" onclick=" location.href='AdviceProcess' ">
</div>
</form>
</body>
</html>
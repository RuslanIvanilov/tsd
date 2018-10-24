<% System.out.println("article_bin_info1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
</head>
<body>
<form style="zoom:200%" action="<%=session.getAttribute("savepage") %>" method="post">
<br>
<p>
Ячейка: <%=session.getAttribute("scanedtext")%><br>

Поддон: <%=session.getAttribute("unitcode")%><br>

<%=session.getAttribute("articlecode")%><br>
<%=session.getAttribute("skuname")%> : <%=session.getAttribute("quantity")%> (<%=session.getAttribute("qualityname")%>)<br>
<small><%=session.getAttribute("articlename") %></small> <br>
<small>Всего артикулов на поддоне:</small> <%=session.getAttribute("articlecount") %>
</p>

<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 52px; width: 230px;" type="button" id="btnCancel" value="Назад" onclick='javascript:history.back()'>
</div>
</form>
</body>
</html>
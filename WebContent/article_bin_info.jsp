<% System.out.println("article_bin_info.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<link rel="stylesheet" type="text/css" href="tsd.css">
<title>TSD</title>
</head>
<body>
<form class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>form" action="<%=session.getAttribute("savepage") %>" method="post">
<p>
Ячейка: <%=session.getAttribute("scanedtext")%><br>

Поддон: <%=session.getAttribute("unitcode")%><br>

<%=session.getAttribute("articlecode")%><br>
<%=session.getAttribute("skuname")%> : <%=session.getAttribute("quantity")%> (<%=session.getAttribute("qualityname")%>)<br>
<small><%=session.getAttribute("articlename") %></small> <br>
<small>Всего артикулов на поддоне:</small> <%=session.getAttribute("articlecount") %>
</p>

<div class="div">
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>21bin" type="button" id="btnCancel" value="Назад" onclick='javascript:history.back()'>
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>22bin" type="button" id="btnSplit" value="Перекладка" onclick=" location.href='SplitStart' ">
</div>
</form>
</body>
</html>
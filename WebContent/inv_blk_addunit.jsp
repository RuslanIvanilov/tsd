<% System.out.println("inv_blk_addunit.jsp v2"); %>
<% session.setAttribute("blkunit", 1); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('scanedtext').focus(); }, 10);</script>
</head>
<body>
<% if(session.getAttribute("oldbrowser") != null) {%>
<form action="InvBinBlk" method="post">
<% } else { %>
<form style="zoom:200%" action="InvBinBlk" method="post">
<% } %>
<h3>Инвентаризация</h3>
<p>Сканируйте <u>Поддон</u> для <b>добавления</b> в набивную ячейку <%=session.getAttribute("bincode")%></p>

<% if(session.getAttribute("oldbrowser") != null) {%>
<input style="width: 90%;" type="text" id="scanedtext" name="scanedtext" onkeydown="if (event.keyCode == 13) submit">
<% } else { %>
<input style="width: 225px;" type="text" id="scanedtext" name="scanedtext" onkeydown="if (event.keyCode == 13) submit">
<% } %>
<div style="overflow: hidden">
<% if(session.getAttribute("oldbrowser") == null) {%>
<input style="display: block; float: inherit; height: 52px; width: 230px;" type="button" id="btnOk" value="Назад" onclick="location.href='inv_bin_blk.jsp' ">
<% } else { %>
<input style="display: block; float: inherit; height: 52px; width: 90%;" type="button" id="btnOk" value="Назад" onclick="location.href='inv_bin_blk.jsp' ">
<% } %>
</div>
</form>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
</head>
<body>
<form style="zoom:200%" action="InventEnd" method="post">

<p>
Ячейка: <%=session.getAttribute("bincode")%>
Поддон: <%=session.getAttribute("unitcode")%>
<small><%=session.getAttribute("skuname")%></small>: <%=session.getAttribute("quantity")%> (<%=session.getAttribute("qualityname")%>) <br>
Артикул: <%=session.getAttribute("articlecode") %><br>
Наименование:<small><%=session.getAttribute("articlename") %></small> 
</p>

<div style="overflow: hidden">
<small>
<input style="display: inline; float: inherit; height: 52px; width: 130px;" type="button" id="btnRestart" value="Заново" onclick='document.location.href ="invent_start.jsp"'> 	
<input style="display: inline; float: inherit; height: 52px; width: 90px;" type="submit" id="btnSave"   value="Сохранить">	 	
</small>
</div>	
</form>
</body>
</html> 
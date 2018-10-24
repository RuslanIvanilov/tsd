<% System.out.println("split_info1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>

</head>
<body>
<form action="SplitEnd" method="post">
<!-- <h3>Перекладка</h3>  -->
<p style="font-family: 'Arial Narrow', Arial, sans-serif;  width: 135px;">
с <small><%=session.getAttribute("unitsrc")%></small><br>
на <%=session.getAttribute("unitdest") %><br>
<%=session.getAttribute("articlecode") %> <small><%=session.getAttribute("articlename") %></small><br>
<%=session.getAttribute("stlitedqty")%> <%=session.getAttribute("skuname") %> (<%=session.getAttribute("qualityname") %>)<br>
<b>Выполнить перекладку ?</b></p>


<div style="overflow: hidden">
<small>
<input style="display: inline; float: inherit; height: 25px; width: 45%;" type="button" id="btnCancel" value="Назад" onclick='document.location.href="SplitDest" '>
<input style="display: inline; float: inherit; height: 25px; width: 45%;" type="submit" id="btnManual"   value="Выполнить">
</small>
</div>
</form>
</body>
</html>
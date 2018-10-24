<% System.out.println("inv_unit_action.jsp v2"); %>
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
<form style="zoom:200%" action="InvStart" method="post">
<br>
<h3>Инвентаризация</h3>
<br>
<p>С поддона <%=session.getAttribute("unitcode")%> удален весь товар! </p>


<div style="overflow: hidden">
<input style="display: block; float: inherit; height: 25px; width: 90%;" type="submit" id="btnOk" value="ок">
</div>
</form>
</body>
</html>
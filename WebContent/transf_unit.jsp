<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% System.out.println("in .....transf_unit.jsp"); %>
<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="transf_unit1.jsp"/>
<% } %>
<% System.out.println("transf_unit.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('scanedtext').focus(); }, 10);
 function scanf(){
	 document.getElementById('scanedtext').focus();
 }
 </script>
</head>
<body onload="document.getElementById('scanedtext').value = ''; scanf()">
<form style="zoom:200%" action="TransfUnit" method="post" onclick="scanf()">
<br>
<h3>Перемещение</h3>
<p>Сканируйте<br>штрих-код поддона</p>
<input style="border:none" type="text" id="scanedtext" name="scanedtext" onkeydown="if (event.keyCode == 13) submit" onblur="scanf()">
<div style="overflow: hidden">
<input style="display: block; float: inherit; height: 52px; width: 220px;" type="button" id="btnOk" value="Назад" onclick='document.location.href ="${backpage}"'>
</div>
</form>
</body>
</html>
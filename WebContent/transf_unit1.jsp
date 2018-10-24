<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% System.out.println("transf_unit.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
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
<form action="TransfUnit" method="post" onclick="scanf()">

<h2>Перемещение</h2>
<br>
<p><big>Сканируйте<br>штрих-код поддона</big></p>


<input style="border:none" type="text" id="scanedtext" name="scanedtext" onkeydown="if (event.keyCode == 13) submit" onblur="scanf()">
<div style="overflow: hidden">
<big>
<input style="display: block; float: inherit; height: 25px; width: 135px;" type="button" id="btnOk" value="Назад" onclick='document.location.href ="${backpage}"'>
</big>
</div>
</form>
</body>
</html>
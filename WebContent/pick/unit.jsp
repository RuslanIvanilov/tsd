<% System.out.println("unit.jsp");%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="ru.defo.controllers.PreOrderController"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD Подбор</title>
<link rel="stylesheet" type="text/css" href="tsd.css">
<script>setTimeout(function() { scanf(); }, 10);
 function scanf(){
	 document.getElementById('scanunit').value = "";
	 document.getElementById('scanunit').focus();
 } </script>
</head>
<body onload="scanf()">
<form class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>form" action="${actionpage}" method="post" onclick="scanf()">
<h3>Подбор <small><i>${vndgroupname}</i></small></h3>
<p><small>
${ordercode}<br>
${clientdescr}
</small>
</p>
<p>
Сканируйте штрих-код <u>Поддона</u> для подбора
</p>

<input class="wminput" type="text" id="scanunit" name="scanunit" onkeypress="if (event.keyCode == 13) submit" value = "">
<div class="div">
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>1btn" type="button" id="btnCancel" value="Назад" onfocus="scanf()"  onclick='document.location.href ="${backpage}"'>
<!-- <input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>1btn" type="button" id="btnList" value="Строки" onfocus="scanf()"  onclick='document.location.href ="${listpage}"'> -->
</div>
</form>
</body>
</html>
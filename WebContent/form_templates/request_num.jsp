<% System.out.println(session.getAttribute("SourceClassName")+"->jsp");%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="ru.defo.controllers.PreOrderController"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">

<meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>


<title>${Title}</title>
<link rel="stylesheet" type="text/css" href="tsd.css">
<script>setTimeout(function() { scanf(); }, 10);
 function scanf(){
	 document.getElementById('inputfield').value = "";
	 document.getElementById('inputfield').focus();
 } </script>
</head>
<body onload="scanf()">
<form class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>form" action="${SubmitAction}" method="post" onclick="scanf()">
<p><b>${HeaderCaption}</b></p>
${TopText}<br>
${CenterText}
<p>
${BottomText}
<input class="wminput" type="text" id="inputfield" name="inputfield" onkeyup = "this.value=parseInt(this.value) |''">
</p>
<div class="div">
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>1btn" type="button" id="btnCancel" value="${CancelName}" onfocus="scanf()"  onclick='document.location.href ="${CancelAction}"'>
</div>
</form>
</body>
</html>
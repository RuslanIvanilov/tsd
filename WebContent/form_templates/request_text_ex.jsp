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
	 document.getElementById('${inputfieldname}').value = "";
	 document.getElementById('${inputfieldname}').focus();
 } </script>
</head>
<body <%=session.getAttribute("nextcolor")==null?"":"bgcolor='#75F877'" %> onload="scanf()">
<form class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>form" action="${SubmitAction}" method="post" onclick="scanf()">
<p><b>${HeaderCaption}</b></p>
${TopText}<br><br>
${CenterText}
<p>
${BottomText}
<input class="wminput" type="text" id="${inputfieldname}" name="${inputfieldname}" onkeypress="if (event.keyCode == 13) submit" value = "">
</p>
<div class="div">
<%if(session.getAttribute("Btn3Name") != null){ %>

<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>31btn" type="button" id="btnAction1" value="${CancelName}" onclick='document.location.href ="${CancelAction}"'>
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>32btn" type="button" id="btnAction2" value="${Btn2Name}"  onclick='document.location.href ="${Btn2Action}"'>
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>33btn" type="button" id="btnAction3" value="${Btn3Name}"  onclick='document.location.href ="${Btn3Action}"'>

<% } else { %>

<%if(session.getAttribute("Btn2Name") != null){ %>
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>21btn" type="button" id="btnCancel" value="${CancelName}" onfocus="scanf()"  onclick='document.location.href ="${CancelAction}"'>
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>22btn" type="button" id="btnAction1" value="${Btn2Name}" onfocus="scanf()"  onclick='document.location.href ="${Btn2Action}"'>
<% } else { %>
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>1btn" type="button" id="btnCancel" value="${CancelName}" onfocus="scanf()"  onclick='document.location.href ="${CancelAction}"'>

<% } } %>
</div>
</form>
</body>
</html>
<% System.out.println("control_scan.jsp"); %>
<%
  if(session.getAttribute("skuname") !=null){session.setAttribute("sku_delim", ":");}else{session.setAttribute("sku_delim", "");}
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
<link rel="stylesheet" type="text/css" href="tsd.css">
<script>setTimeout(function() { document.getElementById('scaned_art_text').focus(); }, 10);
 function scanf(){
	 document.getElementById('scaned_art_text').value = "";
	 document.getElementById('scaned_art_text').focus();
 } </script>
</head>
<body onload="scanf()">
<form class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>form" action="${actionpage}" method="post" onclick="scanf()">
<p>
<big>Контроль</big> <small>${articlecode}</small><br>
<small>${articlename}</small><br>
<%=session.getAttribute("skuname")==null?"":session.getAttribute("skuname")%> <%=session.getAttribute("sku_delim")%> <b><%=session.getAttribute("quantity")==null?"":session.getAttribute("quantity") %></b><br>
Сканируйте <b>товар</b></p>

<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %> input" type="text" id="scaned_art_text" name="scaned_art_text" onkeypress="if (event.keyCode == 13) submit" onblur="scanf()">
<div class="div">
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>31bin" type="button" id="btnCancel" value="Назад" onfocus="scanf()"  onclick='document.location.href ="${backpage}"'>
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>32bin" type="button" id="btnOk" value="Сканы" onfocus="scanf()" onclick='document.location.href ="${scanlist}"'>
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>33bin" type="button" id="btnOk" value="Хватит" onfocus="scanf()" onclick='document.location.href ="${endpage}"'>
</div>
</form>
</body>
</html>
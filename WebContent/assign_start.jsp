<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="assign_start1.jsp"/>
<% } %>
<% System.out.println("assign_start.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { 	
	  //document.getElementById('scaned_art_text').focus(); 
	  //document.getElementById('btnOk').disabled = 0;
	  //document.getElementById('btnCancel').disabled = 0;
	  }, 10);
 
 function scanf(){
	 document.getElementById('scanedtext').focus();
 }
 </script>
</head>
<body onload="document.getElementById('scanedtext').value = ''; scanf()">
<form style="zoom:200%" action="AssignStart" method="post" onclick="scanf()">
<br> 
<h3>Присвоение штрих-кода товару</h3> 
<br>
<p>
Сканируйте штрих-код <u>Товара</u>
</p>


<input style="border:none" type="text" id="scanedtext" name="scanedtext" onkeypress="if (event.keyCode == 13) submit" onblur="scanf()">
<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 52px; width: 230px;" type="button" id="btnCancel" value="Назад" onfocus="scanf()"  onclick='document.location.href ="<%=session.getAttribute("backpage")%>"'> 
</div>	
</form>
</body>
</html> 
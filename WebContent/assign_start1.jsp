<% System.out.println("assign_start1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
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
<form action="AssignStart" method="post" onclick="scanf()">
<h3 style="width: 110px">Присвоение штрих-кода товару</h3>
<p>
Сканируйте штрих-код <u>Товара</u>
</p>


<input style="border:none" type="text" id="scanedtext" name="scanedtext" onkeypress="if (event.keyCode == 13) submit" onblur="scanf()">
<div style="overflow: hidden">
<big>
<input style="display: inline; float: inherit; height: 25px; width: 110px;" type="button" id="btnCancel" value="Назад" onfocus="scanf()"  onclick='document.location.href ="<%=session.getAttribute("backpage")%>"'>
</big>
</div>
</form>
</body>
</html>
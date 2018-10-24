<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="assign_filter1.jsp"/>
<% } %>
<% System.out.println("assign_filter.jsp"); %>
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
	 document.getElementById('articlefilter').focus();
 }
 </script>
</head>
<body onload="document.getElementById('articlefilter').value = ''; scanf()">
<form style="zoom:200%" action="AssignFind" method="post" onclick="scanf()">
<br> 
<h3>Поиск товара по артикулу</h3> 
<p>
Введите номер <u>артикула</u>*<br>
<small>*можно указать часть кода артикула, чем больше символов тем меньше список выбора!</small>
</p>


<input type="text" id="articlefilter" name="articlefilter" onkeypress="if (event.keyCode == 13) submit">
<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 52px; width: 230px;" type="button" id="btnCancel" value="Назад" onfocus="scanf()"  onclick='document.location.href ="<%=session.getAttribute("backpage")%>"'> 
</div>	
</form>
</body>
</html> 
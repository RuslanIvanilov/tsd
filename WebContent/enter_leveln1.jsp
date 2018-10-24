<% System.out.println("enter_levelN1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('enteredtext').focus(); }, 10);
 function scanf(){
	 init();
	 document.getElementById('enteredtext').focus();
 }
 </script>
</head>
<body onclick="scanf()">
<form style="zoom:100%" action="${servlet_name}" method="post">

<h3>Ячейка:<br><%=session.getAttribute("bincode")%></h3>
<br>
<p><big>Введите этаж номер</big></p>


<input style="border:none;" type="text" id="enteredtext" name="enteredtext" onkeyup = "this.value=parseInt(this.value) |''"> <!-- onkeydown="submit"> -->
<div style="overflow: hidden">
<big>
<input style="display: inline; float: inherit; height: 25px; width: 135px;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="${backpage}"'>
</big>
</div>
</form>
</body>
</html>
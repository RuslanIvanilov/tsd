<% System.out.println("quantity.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('enteredqty').focus(); }, 10);
 function scanf(){
	 init();
	 document.getElementById('enteredqty').focus();
 }

 function init(){
	 document.getElementById('enteredqty').value = '';
 }

 </script>
</head>
<body onclick="scanf()">
<form action="${action}" method="post">
<p style="width: 100%" onclick="scanf()">

Отгрузка: <%=session.getAttribute("orderid")%><br>
<small><%=session.getAttribute("orderdescr")%></small><br>
</p>
<p>
Поддон: <%=session.getAttribute("unitcode")%>
</p>
<p>
<b>Введите количество отгружаемых единиц</b>
</p>


<input style="border:none;" type="text" id="enteredqty" name="enteredqty" onkeyup = 'this.value=parseInt(this.value) | 0' onblur="scanf()">
<div style="overflow: hidden">
<big>
<input style="display: inline; float: inherit; height: 25px; width: 100%;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="${backpage}"'>
</big>
</div>
</form>
</body>
</html>
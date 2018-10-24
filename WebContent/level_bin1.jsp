<% System.out.println("level_bin1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('enteredlevel').focus(); }, 10);
 function scanf(){
	 document.getElementById('enteredlevel').focus();
 }
 </script>
</head>
<body>
<form style="zoom:200%" action="${servlet_name}" method="post">
<h3>Ячейка:<br><%=session.getAttribute("bincode")%></h3> 
<br>
<p onclick="scanf()"><big>Введите этаж номер</big></p>


<input style="border:none" type="text" id="enteredlevel" name="enteredlevel" onkeyup = "this.value=parseInt(this.value) | ''">  
<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 25px; width: 135px;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="${backpage}"'>  	
</div>	
</form>
</body>
</html> 
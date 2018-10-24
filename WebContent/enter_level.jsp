<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('enteredtext').focus(); }, 10);</script>
</head>
<body>
<form style="zoom:200%" action="${servlet_name}" method="post">
<br> 
<h3>Ячейка:<br><%=session.getAttribute("bincode")%></h3> 
<br>
<p>Введите этаж номер</p>


<input type="text" id="enteredtext" name="enteredtext" onkeyup = 'this.value=parseInt(this.value) | 0'> <!-- onkeydown="submit"> -->
<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 52px; width: 230px;" type="button" id="btnCancel" value="Назад" onclick='javascript:history.back()'>  	
</div>	
</form>
</body>
</html> 
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 
</head>
<body>
<form style="zoom:200%" action="Confirm" method="post">
<h3>Подтверждение</h3> 
<p>${question}</p>



<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 52px; width: 110px;" type="button" id="btnCancel" value="Назад" onclick='javascript:history.back()'> 	
<input style="display: inline; float: inherit; height: 52px; width: 110px;" type="submit" id="btnOk" value="Ок"> 	
</div>	
</form>
</body>
</html> 
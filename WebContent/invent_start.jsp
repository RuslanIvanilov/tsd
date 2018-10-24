<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('scanedtext').focus(); }, 10);</script>
 <script>
	function requestMainMenu(userbarcode) {
	    document.location.href = "Login?scanedtext="+userbarcode;
	}	
</script>
</head>
<body>
<form style="zoom:200%" action="Inventory" method="post">
<br> 
<h3>Инвентаризация</h3> 
<br>
<p>Сканируйте штрих-код ячейки, поддона или товара</p>


<input type="text" id="scanedtext" name="scanedtext" onkeydown="if (event.keyCode == 13) submit">
<div style="overflow: hidden">
<input style="display: block; float: inherit; height: 52px; width: 230px;" type="button" id="btnOk" value="Назад" onclick="location.href='main_menu.jsp' "> 	
</div>	
</form>
</body>
</html> 
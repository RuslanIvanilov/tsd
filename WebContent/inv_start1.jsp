<% System.out.println("inv_start1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<style type="text/css">
BODY {overflow: hidden }
</style>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">

<meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>

<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('scanedtext').focus(); }, 10);

 function scanf(){
	 document.getElementById('scanedtext').focus();
 }
 </script>

</head>
<body onclick="scanf()">

<form action="InvStart" method="post">
<h3 onclick="scanf()">Инвентаризация</h3>
<p style="width: 135px;" onclick="scanf()"><big>Сканируйте штрих-код ячейки, поддона или товара</big></p>


<input style="border:none; float: initial" type="text" id="scanedtext" name="scanedtext" onkeydown="if (event.keyCode == 13) submit" onblur="scanf()">
<div onclick="scanf()" style="overflow: hidden">
<input style="display: block; float: initial; height: 20%; width: 100%;" type="button" id="btnOk" value="Назад" onclick="location.href='Mmenu' ">
</div>
</form>

</body>
</html>
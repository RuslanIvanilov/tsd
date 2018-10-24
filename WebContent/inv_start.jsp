<% System.out.println("inv_start.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">

<meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
<link rel="stylesheet" type="text/css" href="tsd.css">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('scanedtext').focus(); }, 10);

 function scanf(){
	 document.getElementById('scanedtext').focus();
 }
 </script>
</head>
<body onload="scanf()">
<form class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>form" action="InvStart" method="post" onclick="scanf()">
<br>
<p><b>Инвентаризация</b></p>
<p><small>Сканируйте штрих-код ячейки, поддона или товара</small></p>


<input style="border:none" type="text" id="scanedtext" name="scanedtext" onkeydown="if (event.keyCode == 13) submit" onblur="scanf()">
<div class="div">
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>1btn" type="button" id="btnOk" value="Назад" onclick=" location.href='Mmenu' ">
</div>
</form>
</body>
</html>
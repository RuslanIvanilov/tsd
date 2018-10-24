<% System.out.println("splitdest1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">

<meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>

<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('scaneddest').focus(); }, 10);

 function scanf(){
	 document.getElementById('scaneddest').focus();
 }
 </script>
</head>
<body>
<form action="SplitDest" method="post" onclick="scanf()">
<br>
<h3>Перекладка</h3>
<br>
<p>Сканируйте штрих-код <u>поддона назначения</u></p>


<input style="border:none" type="text" id="scaneddest" name="scaneddest" onkeydown="if (event.keyCode == 13) submit" onblur="scanf()">
<div style="overflow: hidden">
<input style="display: block; float: inherit; height: 25px; width: 135px;" type="button" id="btnOk" value="Назад" onclick=" location.href='SplitArticle'">
</div>
</form>
</body>
</html>
<% System.out.println("advice_unit1.jsp"); %>
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
 <script>setTimeout(function() { document.getElementById('advice_unit_txt').focus(); }, 10);

 function scanf(){
	 document.getElementById('advice_unit_txt').focus();
 }
 </script>

 <link rel="stylesheet" type="text/css" href="tsd.css">
</head>
<body class="cebody">
<form action="${actionpage}" method="post" onclick="scanf()">
<p style="width: 110px">Приемка <small>${clientDocCode}</small></p>
<p style="width: 110px">Место приемки: <b>${adviceunitplace}</b></p>
<p style="width: 110px">Сканируйте номер <u>Поддона</u> с принимаемым товаром</p>


<input class="ceinput" type="text" id="advice_unit_txt" name="advice_unit_txt" onkeydown="if (event.keyCode == 13) submit" onblur="scanf()">
<div class="div">
<input class="ce21btn" type="button" id="btnOk" value="Назад" onclick=" location.href='${backpage}' ">
<input class="ce22btn" type="button" id="btnDok" value="Док" onclick=" location.href='${dokpage}' ">
</div>
</form>
</body>
<% System.out.println("from advice_unit.jsp ---- "+session.getAttribute("backpage")); %>
</html>
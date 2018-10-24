<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="advice_unit1.jsp"/>
<% } %>
<% System.out.println("advice_unit.jsp"); %>
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
</head>
<body>
<form style="zoom:200%" action="${actionpage}" method="post" onclick="scanf()">
<br>
<h3>Приемка <small>${clientDocCode}</small></h3>
<p><small>Место приемки: ${adviceunitplace}</small><br>
Сканируйте номер <u>Поддона</u> с принимаемым товаром</p>


<input style="border:none" type="text" id="advice_unit_txt" name="advice_unit_txt" onkeydown="if (event.keyCode == 13) submit" onblur="scanf()">
<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 52px; width: 100px;" type="button" id="btnOk" value="Назад" onclick=" location.href='${backpage}' ">
<input style="display: inline; float: inherit; height: 52px; width: 100px;" type="button" id="btnDok" value="Док" onclick=" location.href='${dokpage}' ">
</div>
</form>
</body>
</html>
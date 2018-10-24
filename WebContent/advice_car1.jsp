<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% System.out.println("advice_car1.jsp"); %>
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
 <script>setTimeout(function() { document.getElementById('advice_car_txt').focus(); }, 10);

 function scanf(){
	 document.getElementById('advice_car_txt').focus();
 }
 </script>
</head>
<body>
<form style="zoom:200%" action="AdviceCar" method="post" onclick="scanf()">
<br>
<h3>Приемка</h3>
<br>
<p style="width: 135px" onclick="scanf()">Введите номер <u>Машины</u></p>


<input style="border:none" type="text" id="advice_car_txt" name="advice_car_txt" onkeydown="if (event.keyCode == 13) submit" onblur="scanf()">
<div style="overflow: hidden">
<input style="display: block; float: inherit; height: 25px; width: 110px;" type="button" id="btnOk" value="Назад" onclick=" location.href='Mmenu' ">
</div>
</form>
</body>
</html>
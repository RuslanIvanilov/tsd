<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% System.out.println("userbc.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>ТСД</title>

</head>
<body>
<form action="UserBC" method="post" onclick="scanf()">
<h2 style="width: 135px">Печать Ш/К сотрудника</h2>
<p style="width: 135px"><small>сотрудник сразу и добавляется в БД</small><br>
Фамилия: <input type="text" id="surnametext" name="surnametext"><br>
Имя: <input type="text" id="firstnametext" name="firstnametext"><br>
Год рождения: <input type="text" id="yeartext" name="yeartext" onkeyup = 'this.value=parseInt(this.value) | 0'><br>
</p>
<p style="width: 135px">
<small>Принтер IP-адрес:</small> <input type="text" id="printeriptext" name="printeriptext"><br>
</p>
<div style="overflow: hidden">
<big>
<input style="display: inline; float: inherit; height: 25px; width: 60px;" type="button" id="btnOk" value="Назад" onclick="location.href='Mmenu'">
<input style="display: inline; float: inherit; height: 25px; width: 60px;" type="submit" id="btnPrint" value="Печать">
</big>
</div>
</form>
</body>
</html>
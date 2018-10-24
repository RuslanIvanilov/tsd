<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="userbc1.jsp"/>
<% } %>
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
<form style="zoom:200%" action="UserBC" method="post" onclick="scanf()">
<h3>Печать Ш/К сотрудника</h3>
<p><small>сотрудник сразу и добавляется в БД</small><br>
Фамилия: <input type="text" id="surnametext" name="surnametext"><br>
Имя: <input type="text" id="firstnametext" name="firstnametext"><br>
Год рождения: <input type="text" id="yeartext" name="yeartext" onkeyup = 'this.value=parseInt(this.value) | 0'><br>
</p>
<p>
<small>Принтер IP-адрес:</small> <input type="text" id="printeriptext" name="printeriptext"><br>
</p>
<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 52px; width: 100px;" type="button" id="btnOk" value="Назад" onclick="location.href='Mmenu'">
<input style="display: inline; float: inherit; height: 52px; width: 100px;" type="submit" id="btnPrint" value="Печать">
</div>
</form>
</body>
</html>
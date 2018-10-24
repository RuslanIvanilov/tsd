<%
	System.out.println("GUI/adv_create.jsp");
%>
<%@page import="java.util.*"%>
<%@page import="ru.defo.controllers.BinController"%>
<%@page import="ru.defo.controllers.AuthorizationController"%>
<%@page import="ru.defo.model.WmBin"%>
<%@page import="ru.defo.model.WmUser"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>WMS Дэфо Список приемок</title>
</head>
<body>
<p>
<input type="button" id="filter" value="Фильтр">
<input type="button" id="create_advice" value="Создать приемку">
</p>
<table>
<tr>
<td>Машина гос. номер</td>
<td>Дата создания</td>
<td>Статус</td>
<td>Кол-во документов приемки</td>
<td>Док номер</td>
<td>Премщик</td>
</tr>
<%  %>
</table>
</body>
</html>
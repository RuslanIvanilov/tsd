<% System.out.println("inv_bin_frn_qnt1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
</head>
<body>
<form action="InvBinFrnSku" method="post">

<p style="width: 135px;">
<small>
Упаковка: <%=session.getAttribute("skuname")%><br>

<br> </small><b><%=session.getAttribute("articlecode")%></b><br><br>
<small><%=session.getAttribute("articlename") %></small>
<br><b>Ввод <small>количества?</small></b>

</p>


<div style="overflow: hidden">
<small>
<input style="display: inline; float: inherit; height: 25px; width: 22%;" type="submit" id="btnManual"   value="Назад">

<input style="display: inline; float: inherit; height: 25px; width: 28%;" type="button" id="btnManual"   value="Вручную" onclick='document.location.href ="EnterQty"'>

<input style="display: inline; float: inherit; height: 25px; width: 30%;" type="button" id="btnScaning" value="Скани-рование" onclick='document.location.href ="ScanArticle"'>
</small>
</div>
</form>
</body>
</html>
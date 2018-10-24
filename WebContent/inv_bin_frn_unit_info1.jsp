<% System.out.println("inv_bin_frn_unit_info1.jsp"); %>
<% session.setAttribute("backpage", "inv_bin_frn_unit_info.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
</head>
<body>
<form action="InvStart" method="post">
<br>
<h3>Инвентаризация</h3>
<p><big>
Ячейка: <%=session.getAttribute("bincode")%><br>
Поддон: <%=session.getAttribute("unitcode")%><br>
Артикулов: <%=session.getAttribute("articlecount") == null ? 0 : session.getAttribute("articlecount") %>
</big></p>

<div style="overflow: hidden">

<input style="display: inline; float: inherit; height: 20%; width: 24%;" type="submit" id="btnBack"   value="Назад">
<input style="display: inline; float: inherit; height: 20%; width: 28%;" type="button" id="btnArtList"   value="Товары" onclick='document.location.href ="unit_article_list.jsp"'>

<input style="display: inline; float: inherit; height: 20%; width: 34%;" type="button" id="btnAddArticle" value="Добавить" onclick='document.location.href ="InvBinFrnSku"'>

</div>
</form>
</body>
</html>
<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="inv_bin_frn_unit_info1.jsp"/>
<% } %>
<% System.out.println("inv_bin_frn_unit_info.jsp"); %>
<% session.setAttribute("backpage", "inv_bin_frn_unit_info.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
</head>
<body>
<form style="zoom:200%" action="InvStart" method="post">
<br>
<h3>Инвентаризация</h3>
<p>
Ячейка: <%=session.getAttribute("bincode")%><br>
Поддон: <%=session.getAttribute("unitcode")%><br>
Артикулов: <%=session.getAttribute("articlecount") == null ? 0 : session.getAttribute("articlecount") %>
</p> 

<div style="overflow: hidden">
<small>
<input style="display: inline; float: inherit; height: 52px; width: 50px;" type="submit" id="btnBack"   value="Назад">
<input style="display: inline; float: inherit; height: 52px; width: 60px;" type="button" id="btnArtList"   value="Товары" onclick='document.location.href ="unit_article_list.jsp"'>
<input style="display: inline; float: inherit; height: 52px; width: 90px;" type="button" id="btnAddArticle" value="Добавить" onclick='document.location.href ="InvBinFrnSku"'> 	 	
</small>
</div>	
</form>
</body>
</html> 
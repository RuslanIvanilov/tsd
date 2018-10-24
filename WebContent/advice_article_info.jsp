<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="advice_article_info1.jsp"/>
<% } %>
<% System.out.println("advice_article_info.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
</head>
<body>
<form style="zoom:200%" action="advice_enter_qty.jsp" method="post">
<p>
Артикул: <%=session.getAttribute("articlecode") %><br>
Количество: <%=(session.getAttribute("quantity")==null?0:session.getAttribute("quantity")) %> <%=session.getAttribute("skuname")%> (<%=(session.getAttribute("qualityname")==null?0:session.getAttribute("qualityname")) %>)<br>
Наименование:<small><%=session.getAttribute("articlename") %></small>
</p>
<p><b>Способ ввода количества товара?</b></p>


<div style="overflow: hidden">
<small>
<input style="display: inline; float: inherit; height: 52px; width: 50px;" type="button" id="btnBack"   value="Назад" onclick='document.location.href="advice_article.jsp" '>
<!-- <input style="display: inline; float: inherit; height: 52px; width: 65px;" type="submit" id="btnManual"   value="Вручную">  -->
<input style="display: inline; float: inherit; height: 52px; width: 100px;" type="button" id="btnScaning" value="Сканирование" onclick='document.location.href="ScanArticle" '>
</small>
</div>
</form>
</body>
</html>
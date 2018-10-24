<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% System.out.println("advice_article_info1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>

<style>
 body {
	margin-right: -15px;
	margin-bottom: -15px;
	overflow-y: hidden;
	overflow-x: hidden;
}
 </style>
</head>
<body class="body">
<form action="advice_enter_qty.jsp" method="post">
<p style="width: 110px">
Артикул: <%=session.getAttribute("articlecode") %><br>
Количество: <%=(session.getAttribute("quantity")==null?0:session.getAttribute("quantity")) %> <%=session.getAttribute("skuname")%> (<%=(session.getAttribute("qualityname")==null?0:session.getAttribute("qualityname")) %>)<br>
Наименование:<small><%=session.getAttribute("articlename") %></small>
</p>
<p style="width: 110px"><b>Способ ввода количества товара?</b></p>


<div style="overflow: hidden">
<small>
<input style="display: inline; float: inherit; height: 25px; width: 30px;" type="button" id="btnBack"   value="Назад" onclick='document.location.href="advice_article.jsp" '>
<input style="display: inline; float: inherit; height: 25px; width: 30px;" type="submit" id="btnManual"   value="Вручную">
<input style="display: inline; float: inherit; height: 25px; width: 30px;" type="button" id="btnScaning" value="Сканиро-вание" onclick='document.location.href="ScanArticle" '>
</small>
</div>
</form>
</body>
</html>
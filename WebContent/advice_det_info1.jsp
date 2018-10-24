<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% System.out.println("advice_det_info1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
</head>
<body>
<form action="<%=session.getAttribute("savepage") %>" method="post">
<p style="width: 110px">
А/м: <%=session.getAttribute("carCode")%> <small><%=session.getAttribute("clientDocCode")%></small><br>
Артикул: <%=session.getAttribute("articlecode")%><br>
<small><%=session.getAttribute("articlename")%></small><br>
План. Кол-во: <%=session.getAttribute("expquantity")%><br>
Факт. Кол-во: <%=session.getAttribute("quantity")%> <%=session.getAttribute("skuname")%> (<%=session.getAttribute("qualityname")%>)<br>
</p>
<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 25px; width: 90px;" type="button" id="btnCancel" value="Назад" onclick="location.href='AdvicePosList' ">
<!-- <input style="display: inline; float: inherit; height: 52px; width: 100px;" type="button" id="btnUnits" value="Поддоны" onclick="location.href='AdvicePosList' ">-->
</div>
</form>
</body>
</html>
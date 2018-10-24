<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="advice_article_fnlinfo1.jsp"/>
<% } %>
<% System.out.println("advice_article_fnlinfo.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
</head>
<body>
<form style="zoom:200%" action="AdviceFinish?action=1" method="post">
<p>
<small>
${clientDocCode}<br>
Принять на:${unitcode}<br>
</small>
Артикул: <%=session.getAttribute("articlecode") %><br>
Количество: <%=(session.getAttribute("quantity")==null?0:session.getAttribute("quantity")) %> <%=session.getAttribute("skuname")%> (<%=(session.getAttribute("qualityname")==null?0:session.getAttribute("qualityname")) %>)<br>
Наименование:<small><%=session.getAttribute("articlename") %></small>
</p>

<div style="overflow: hidden">
<small>
<input style="display: inline; float: inherit; height: 52px; width: 50px;" type="button" id="btnBack"   value="Назад" onclick='javascript:history.back()'>
<input style="display: inline; float: inherit; height: 52px; width: 65px;" type="button" id="btnCancel"   value="Заново" onclick='document.location.href="AdviceResetUnit"'>
<input style="display: inline; float: inherit; height: 52px; width: 100px;" type="submit" id="btnSave" value="Сохранить">
</small>
</div>
</form>
</body>
</html>
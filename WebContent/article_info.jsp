<% System.out.println("article_info.jsp placescount "+session.getAttribute("placescount")); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="tsd.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<link rel="stylesheet" type="text/css" href="tsd.css">
<title>TSD</title>
</head>
<body>
<form class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>form" action="<%=session.getAttribute("savepage") %>" method="post">
<br>
<h3><%=session.getAttribute("articlecode") %></h3>
<p>
<small><%=session.getAttribute("articlename") %></small> <br>
Мест нахождения: <%=session.getAttribute("placescount") %>
</p>



<div class="div">
<% if(Integer.valueOf(session.getAttribute("placescount").toString()) > 0) { %>
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>21bin" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="InvStart"'>
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>22bin" type="submit" id="btnEdit" value="Адреса">
<% } else {  %>
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>1btn" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="InvStart"'> <!-- 'javascript:history.back()'> -->
<% } %>
</div>
</form>
</body>
</html>
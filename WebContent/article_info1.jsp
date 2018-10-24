<% System.out.println("article_info.jsp placescount "+session.getAttribute("placescount")); %>
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
<h3><%=session.getAttribute("articlecode") %></h3> 
<p style="width: 135px;">
<big><%=session.getAttribute("articlename") %></big> <br>
<b>Мест нахождения: <%=session.getAttribute("placescount") %></b> 
</p>



<div style="overflow: hidden">
<big>
<% if(Integer.valueOf(session.getAttribute("placescount").toString()) > 0) { %>	
<input style="display: inline; float: inherit; height: 25px; width: 65px;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="InvStart"'> 
<input style="display: inline; float: inherit; height: 25px; width: 65px;" type="submit" id="btnEdit" value="Адреса"> 	
<% } else {  %>
<input style="display: inline; float: inherit; height: 25px; width: 135px;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="InvStart"'> <!-- 'javascript:history.back()'> --> 
<% } %>
</big>
</div>	
</form>
</body>
</html> 
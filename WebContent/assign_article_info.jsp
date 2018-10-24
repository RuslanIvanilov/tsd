<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="assign_article_info1.jsp"/>
<% } %>
<% System.out.println("assign_article_info.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
</head>
<body>
<form style="zoom:200%" action="AssignSave" method="post">
<br>
<p>
Упаковке: <small><%=session.getAttribute("skuname")%></small><br>
Артикула: <%=session.getAttribute("articlecode") %><br>
Наименование:<small><%=session.getAttribute("articlename") %></small><br> 
<b>Присвоить штрих-код <%=session.getAttribute("barcode") %> ?</b></p> 


<div style="overflow: hidden">
<small>
<input style="display: inline; float: inherit; height: 52px; width: 90px;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="article_list.jsp"'> 	 	
<input style="display: inline; float: inherit; height: 52px; width: 130px;" type="submit" id="btnManual"   value="Присвоить">
</small>
</div>	
</form>
</body>
</html> 
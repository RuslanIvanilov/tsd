<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="splitqty1.jsp"/>
<% } %>
<% System.out.println("splitqty.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('enteredqty').focus(); }, 10);</script>
</head>
<body>
<form style="zoom:200%" action="SplitQty" method="post">
<br>
<p>Товар: <%=session.getAttribute("articlecode")%> <small><%=session.getAttribute("articlename")%></small><br>
Кол-во: <%=session.getAttribute("quantity") %> (<%=session.getAttribute("skuname")%>) - <%=session.getAttribute("qualityname") %></p>
<p>Введите количество</p>


<input type="text" id="enteredqty" name="enteredqty" onkeyup = 'this.value=parseInt(this.value) | 0'>
<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 52px; width: 230px;" type="button" id="btnCancel" value="Назад" onclick='document.location.href="splitarticle.jsp"'>
</div>
</form>
</body>
</html>
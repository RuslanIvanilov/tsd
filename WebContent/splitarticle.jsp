<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="splitarticle1.jsp"/>
<% } %>
<% System.out.println("splitarticle.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('scanedart').focus(); }, 10);</script>

</head>
<body>
<form style="zoom:200%" action="SplitArticle" method="post">
<br>
<h3>Перекладка</h3>
<br>
<p>
<% if(session.getAttribute("bincode") != null){ %>
<small>Ячейка <%=session.getAttribute("bincode")%> </small><br>
<% } else { %>
Без ячейки<br>
<% } %>
<small>поддон <%=session.getAttribute("unitsrc")%> </small><br>
<br>Сканируйте <u>Товар</u></p>


<input style="border:none" type="text" id="scanedart" name="scanedart" onkeydown="if (event.keyCode == 13) submit">
<div style="overflow: hidden">
<input style="display: block; float: inherit; height: 52px; width: 230px;" type="button" id="btnOk" value="Назад" onclick='document.location.href ="splitsrc.jsp"'>
</div>
</form>
</body>
</html>
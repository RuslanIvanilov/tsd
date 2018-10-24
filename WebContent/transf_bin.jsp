<% if(session.getAttribute("userid")==null){%>
<jsp:forward page="index.jsp"/>
<%} %>

<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="transf_bin1.jsp"/>
<% } %>
<% System.out.println("transf_bin.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('scanedbintext').focus(); }, 10);</script>
</head>
<body>
<form style="zoom:200%" action="${actionpage}" method="post">
<br>
<p>Перемещение поддона <%=session.getAttribute("unitcode")==null?"":session.getAttribute("unitcode") %>
<br>
Сканируйте<br>штрих-код <u>ячейки ${bintypename}</u></p>


<input type="text" id="scanedbintext" name="scanedbintext" onkeydown="if (event.keyCode == 13) submit">
<div style="overflow: hidden">
<input style="display: block; float: inherit; height: 52px; width: 220px;" type="button" id="btnOk" value="Назад" onclick='document.location.href ="${backpage}"'>
</div>
</form>
</body>
</html>
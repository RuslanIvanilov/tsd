<% System.out.println("splitarticle1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('scanedart').focus(); }, 10);

 function scanf(){
	 document.getElementById('scanedart').focus();
 }
 </script>

</head>
<body>
<form action="SplitArticle" method="post" onclick="scanf()">

<h3>Перекладка</h3>

<p style="width: 135px;">
<% if(session.getAttribute("bincode") != null){ %>
Ячейка <%=session.getAttribute("bincode")%> <br>
<% } else { %>
Без ячейки<br>
<% } %>
поддон <%=session.getAttribute("unitsrc")%><br>
<br>Сканируйте <u>Товар</u></p>


<input style="border:none" type="text" id="scanedart" name="scanedart" onkeydown="if (event.keyCode == 13) submit">
<div style="overflow: hidden">
<input style="display: block; float: inherit; height: 25px; width: 135px;" type="button" id="btnOk" value="Назад" onclick='document.location.href="splitsrc.jsp" '>
</div>
</form>
</body>
</html>
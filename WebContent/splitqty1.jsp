<% System.out.println("splitqty1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('enteredqty').focus(); }, 10);

 function scanf(){
	 document.getElementById('scanedart').focus();
 }
 </script>
</head>
<body>
<form action="SplitQty" method="post" onclick="scanf()">

<p style="width: 135px;">Товар: <%=session.getAttribute("articlecode")%> <small><%=session.getAttribute("articlename")%></small><br>
Кол-во: <%=session.getAttribute("quantity") %> (<%=session.getAttribute("skuname")%>) - <%=session.getAttribute("qualityname") %></p>
<p>Введите количество</p>


<input style="border:none" type="text" id="enteredqty" name="enteredqty" onkeyup = 'this.value=parseInt(this.value) | 0'>
<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 25px; width: 135px;" type="button" id="btnCancel" value="Назад" onclick='document.location.href="splitarticle.jsp"'>
</div>
</form>
</body>
</html>
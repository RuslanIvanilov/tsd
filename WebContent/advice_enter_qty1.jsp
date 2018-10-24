<% System.out.println("advice_enter_qty1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('enteredqty').focus(); }, 10);

 function scanf(){
	 document.getElementById('enteredqty').focus();
 }
 </script>

  <style>
 body {
	margin-right: -15px;
	margin-bottom: -15px;
	overflow-y: hidden;
	overflow-x: hidden;
}
 </style>
</head>
<body class="body" onclick="scanf()">
<form action="AdviceEnterQty" method="post">
<p onclick="scanf()" style="width: 110px">Товар: <%=session.getAttribute("articlecode")%> <small><%=session.getAttribute("articlename")%></small><br>
<small>Введите <b>количество</b> <big><%=session.getAttribute("skuname")%></big> принимаемого товара</small></p>


<input style="border:none" type="text" id="enteredqty" name="enteredqty" onkeyup = 'this.value=parseInt(this.value) | 0'>
<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 25px; width: 110px;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="advice_article_info.jsp"'>
</div>
</form>
</body>
</html>
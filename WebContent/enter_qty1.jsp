<% System.out.println("enter_qty1.jsp v2"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('enteredqty').focus(); }, 10);
 function scanf(){
	 init();
	 document.getElementById('enteredqty').focus();
 }

 function init(){
	 document.getElementById('enteredqty').value = '';
 }

 </script>
</head>
<body onclick="scanf()">
<form action="EnterQty" method="post">
<p style="width: 135px" onclick="scanf()">
<b><%=session.getAttribute("articlecode")%></b>
<small><br><br><%=session.getAttribute("articlename")%></small><br>
<br>
<b>Сколько <%=session.getAttribute("skuname")%>?</b>

</p>


<input style="border:none;" type="text" id="enteredqty" name="enteredqty" onkeyup = 'this.value=parseInt(this.value) | 0' onblur="scanf()">
<div style="overflow: hidden">
<big>
<input style="display: inline; float: inherit; height: 25px; width: 90%;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="InvBinFrnQnt"'>
</big>
</div>
</form>
</body>
</html>
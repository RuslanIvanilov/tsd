<% System.out.println("enter_qty_labels1.jsp"); %>
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
</head>
<body>
<form action="PrintEnd" method="post">
<p style="width: 135px" onclick="scanf()">
<big>
Товар: <%=session.getAttribute("articlecode")%> <small><%=session.getAttribute("articlename")%></small><br> 
ШК: <%=session.getAttribute("barcode")%>
</big>
</p>
<p onclick="scanf()" style="width: 135px">Введите количество этикеток для печати</p>


<input style="border:none;" type="text" id="enteredqty" name="enteredqty" onkeyup = 'this.value=parseInt(this.value) | 0'> 
<div style="overflow: hidden">
<big>
<input style="display: inline; float: inherit; height: 25px; width: 135px;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="EnterQtyLabels"'>  	
</big>
</div>	
</form>
</body>
</html> 
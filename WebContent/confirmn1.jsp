<%
System.out.println("confirmN1.jsp");
System.out.println("unitcode : "+session.getAttribute("unitcode")+" userid : "+session.getAttribute("userid"));
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>

</head>
<body>
<form style="zoom:200%" action="${action_page}" method="post">
<h3>Подтверждение</h3>
<p style="width: 135px;"><big>${question}</big></p>



<div style="overflow: hidden">
<%if(session.getAttribute("continue_page")==null){ %>
<input style="display: inline; float: inherit; height: 25px; width: 50%;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="${back_page}"'>
<input style="display: inline; float: inherit; height: 25px; width: 40%;" type="submit" id="btnOk" value="Да">
<%} else { %>
<input style="display: inline; float: inherit; height: 25px; width: 30%;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="${back_page}"'>
<input style="display: inline; float: inherit; height: 25px; width: 25%;" type="submit" id="btnOk" value="Да">
<input style="display: inline; float: inherit; height: 25px; width: 35%;" type="button" id="btnContinue" value="Продолжить" onclick='document.location.href ="${continue_page}"'>
<%}%>
</div>
</form>
</body>
</html>
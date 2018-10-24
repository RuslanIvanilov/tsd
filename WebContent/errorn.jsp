<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="errorn1.jsp"/>
<% } %>
<% System.out.println("errorn.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
<script>
	setTimeout(function() {
		document.getElementById('btnOk').focus();
	}, 10);
</script>
</head>
<body>	
	<form style="zoom:200%" action="${action}" method="post">
		<br>
		<h1><font color="red">Ошибка:</font></h1>
		<br>
		<p>${message}</p>
		<div style="overflow: hidden">
		<input style="display: block; float: inherit; height: 52px; width: 230px;" type="submit" id="btnOk" value="Ok"> 	
		</div>		
	</form>
</body>
</html>
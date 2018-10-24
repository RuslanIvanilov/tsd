<% System.out.println("errorn1.jsp"); %>
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

 <style>
 body {
	margin-right: -15px;
	margin-bottom: -15px;
	overflow-y: hidden;
	overflow-x: hidden;
}
 </style>
</head>
<body class="body">
	<form style="zoom:100%" action="${action}" method="post">

		<h2><font color="red">Ошибка:</font></h2>

		<p style="width: 135px"><big>${message}</big></p>
		<div style="overflow: hidden">
		<big>
		<input style="display: block; float: inherit; height: 25px; width: 135px;" type="submit" id="btnOk" value="Ok">
		</big>
		</div>
	</form>
</body>
</html>
<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% System.out.println("transf_bin1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('scanedbintext').focus(); }, 10);
 function scanf(){
	 document.getElementById('scanedbintext').focus();
 }
 </script>

 <style>
body {
	float: none;
	font-size: 13px;
	padding: 0px;
	border: hidden;
	margin-right: -15px;
	margin-bottom: -15px;
	overflow-y: hidden;
	overflow-x: hidden;
}
</style>
</head>
<body class="body">
<form action="${actionpage}" method="post" >

<p style="width: 135px;" onclick="scanf()">

Перемещение поддона <b><%=session.getAttribute("unitcode") %></b>
<br>
<br>
Сканируйте штрих-код <u>ячейки ${bintypename}</u>

</p>


<input style="border:none" type="text" id="scanedbintext" name="scanedbintext" onkeydown="if (event.keyCode == 13) submit">
<div style="overflow: hidden">
<big>
<input style="display: block; float: inherit; height: 25px; width: 135px;" type="button" id="btnOk" value="Назад" onclick='document.location.href ="${backpage}"'>
</big>
</div>
</form>
</body>
</html>
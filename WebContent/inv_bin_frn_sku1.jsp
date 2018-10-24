<% System.out.println("inv_bin_frn_sku1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('scanedsku').focus(); }, 10);</script>
 <script>
	function requestMainMenu(userbarcode) {
	    document.location.href = "Login?scanedsku="+userbarcode;
	}

	function scanf(){
		 document.getElementById('scanedsku').focus();
	 }
</script>
</head>
<body>
<form action="InvBinFrnSku" method="post" onclick="scanf()">

<p>
<big>
<b>Инвентаризация</b>

<br>Ячейка:<%=session.getAttribute("bincode")%>
<br>Поддон:<%=session.getAttribute("unitcode")%><br>
<br>Сканируйте <u>Товар</u>
</big>
</p>

<input style="border:none" type="text" id="scanedsku" name="scanedsku" onkeydown="if (event.keyCode == 13) submit" onblur="scanf()">
<div style="overflow: hidden">
<input style="display: block; float: inherit; height: 25px; width: 130px;" type="button" id="btnOk" value="Назад" onclick='document.location.href ="inv_bin_frn_unit_info.jsp"'>
</div>
</form>
</body>
</html>
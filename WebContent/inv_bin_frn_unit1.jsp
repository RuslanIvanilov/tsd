<% System.out.println("inv_bin_frn_unit1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('scanedunit').focus(); }, 10);</script>
 <script>
	function requestMainMenu(userbarcode) {
	    document.location.href = "Login?scanedunit="+userbarcode;
	}
	
	function scanf(){
		 document.getElementById('scanedunit').focus();
	 }
</script>
</head>
<body>
<form action="InvBinFrnUnit" method="post" onclick="scanf()"> 
<h3>Инвентаризация</h3> 
<br>
<p style="width: 135px;"><big>Ячейка <%=session.getAttribute("bincode")%><br>Сканируйте <u>Поддон</u></big></p>


<input style="border:none" type="text" id="scanedunit" name="scanedunit" onkeydown="if (event.keyCode == 13) submit" onblur="scanf()">
<div style="overflow: hidden">
<big>
<input style="display: block; float: inherit; height: 25px; width: 135px;" type="button" id="btnOk" value="Назад" onclick='document.location.href ="InvBinFrn"'> 
</big>	
</div>	
</form>
</body>
</html> 
<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% System.out.println("advice_bin1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">

<meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>

<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('advice_bin_txt').focus(); }, 10);

 function scanf(){
	 document.getElementById('advice_bin_txt').focus();
 }
 </script>
</head>
<body>
<form action="${actionpage}" method="post" onclick="scanf()">
<h3>Приемка</h3>
<p style="width: 110px"><small>${clientDocCode}</small><br>
Сканируйте штрих-код <u>Места приемки</u></p>


<input style="border:none" type="text" id="advice_bin_txt" name="advice_bin_txt" onkeydown="if (event.keyCode == 13) submit" onblur="scanf()">
<div style="overflow: hidden">
<input style="display: block; float: inherit; height: 25px; width: 110px;" type="button" id="btnOk" value="Назад" onclick=" location.href='${backpage}' ">
</div>
</form>
</body>
</html>
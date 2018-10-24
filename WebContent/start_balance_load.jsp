<%
	System.out.println("start_balance_load.jsp");
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf(); init();">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0 , maximum-scale=10, user-scalable=no">

<meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>

<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('scanedtext').focus(); }, 10);
 function scanf(){
	 document.getElementById('scanedtext').focus();
 }

 function screensize(){
	 document.getElementById('screen').value = window.screen.availWidth+' : '+window.screen.availHeight;
 }

 function init(){
	 document.getElementById('scanedtext').value = "";

	 var iever = 0;
		if((navigator.userAgent.indexOf('IEMobile 7')>0) || (+window.screen.availWidth == +"240")
				)
			iever = 1;

		document.getElementById('oldbrowser').value = iever;
 }

 function setZoom(){
	 if(document.getElementById('oldbrowser').value == "1"){
		 document.getElementById('loginform').style.zoom = "100%";
	 } else
	   document.getElementById('loginform').style.zoom = "200%";
 }

 </script>
</head>
<!-- onclick="alert(navigator.userAgent+' ::: '+navigator.userAgent.indexOf('IEMobile 7')+' / '+navigator.userAgent.indexOf('MSIE 4'));" -->
<body onload="scanf(); init(); setZoom();">
<form id="loginform" action="Login" method="post" onclick="scanf(); init();">
<br>
<h2>ТСД "Дэфо"</h2>
<br>
<p>Сканируйте<br>личный штрих-код</p>

<input size=18 style="border:none" type="password" id="scanedtext" name="scanedtext" onkeydown="if (event.keyCode == 13) submit" onblur="scanf()" value = "" onfocus="init();">

 <!-- <input style="border:none" type="button" id="btn_info" onclick="alert('W:'+window.screen.availWidth+' '+'H:'+window.screen.availHeight)"> -->

<input type="hidden" id="oldbrowser" name="oldbrowser">
<!-- <input type="text" style="border:none" id="screen"> -->
</form>
</body>
</html>
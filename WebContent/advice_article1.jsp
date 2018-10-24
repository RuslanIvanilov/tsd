<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% System.out.println("advice_article1.jsp"); %>
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
 <script>setTimeout(function() { document.getElementById('advice_art_txt').focus(); }, 10);

 function scanf(){
	 document.getElementById('advice_art_txt').focus();
 }
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
<form action="${actionpage}" method="post" onclick="scanf()">
<h3 style="width: 135px">Приемка <small>${clientDocCode}</small></h3>
<p  style="width: 135px"><small>Место приемки: ${adviceunitplace}</small><br>
Поддон: ${unitcode}<br>
артикулов: ${articlecount}<br>
Сканируйте штрих-код <u>Товара</u></p>


<input style="border:none" type="text" id="advice_art_txt" name="advice_art_txt" onkeydown="if (event.keyCode == 13) submit" onblur="scanf()">
<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 25px; width: 40px;" type="button" id="btnOk" value="Назад" onclick=" location.href='${backpage}'">
<input style="display: inline; float: inherit; height: 25px; width: 40px;" type="button" id="btnArtList" value="Товары" onclick=" location.href='${artlist}'">
<input style="display: inline; float: inherit; height: 25px; width: 40px;" type="button" id="btnPrintArticle" value="Печать" onclick=" location.href='${printpage}'">
</div>
</form>
</body>
</html>
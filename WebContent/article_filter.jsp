<% System.out.println("article_filter.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
<link rel="stylesheet" type="text/css" href="tsd.css">
 <script>setTimeout(function() { scanf(); }, 10);

 function scanf(){
	 document.getElementById('articlefilter').value = '';
	 document.getElementById('articlefilter').focus();
 }
 </script>
</head>
<body onload="scanf()">
<form class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>form" action="<%=session.getAttribute("savepage")%>" method="post" onclick="scanf()">

<h3>Поиск товара по артикулу</h3>
<p>
Введите номер <u>артикула</u>*<br>
<small>*можно указать часть кода артикула, чем больше символов тем меньше список выбора!</small>
</p>


<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>input" type="text" id="articlefilter" name="articlefilter" onkeypress="if (event.keyCode == 13) submit">
<div class="div">
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>1btn" type="button" id="btnCancel" value="Назад" onfocus="scanf()"  onclick='document.location.href ="<%=session.getAttribute("backpage")%>"'>
</div>
</form>
</body>
</html>
<% System.out.println("info.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
<link rel="stylesheet" type="text/css" href="tsd.css">
</head>
<body>
<form class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>form" action="${actionpage}" method="post">
<br>
<h3>Контроль</h3>
<p>
Поддон: <%=session.getAttribute("unitcode")%><br>
Ячейка: <%=session.getAttribute("bincode")==null ? "---" : session.getAttribute("bincode")%><br>
Артикулов: <%=session.getAttribute("articlecount") == null ? 0 : session.getAttribute("articlecount") %>
</p>

<div class="div">
<small>
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>21bin" type="button" id="btnBack" value="Назад" onclick='document.location.href ="${backpage}"'>
<!-- <input class="wm32bin" type="button" id="btnArtList"   value="Товары" onclick='document.location.href ="${articlelist}"'>  -->
<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>22bin" type="submit" id="btnAddArticle" value="Контроль">
</small>
</div>
</form>
</body>
</html>
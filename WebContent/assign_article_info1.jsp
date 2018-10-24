<% System.out.println("assign_article_info1.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
</head>
<body>
<form action="AssignSave" method="post">

<p style="width: 110px">
<big>
Упаковке: <%=session.getAttribute("skuname")%><br>
Артикула: <%=session.getAttribute("articlecode") %><br>
<%=session.getAttribute("articlename") %><br>
<b>Присвоить штрих-код <%=session.getAttribute("barcode") %> ?</b>
</big>
</p>


<div style="overflow: hidden">
<big>
<input style="display: inline; float: inherit; height: 25px; width: 40%;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="article_list.jsp"'>
<input style="display: inline; float: inherit; height: 25px; width: 50%;" type="submit" id="btnManual" value="Присвоить">
</big>
</div>
</form>
</body>
</html>
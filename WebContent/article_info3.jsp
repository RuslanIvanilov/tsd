<% System.out.println("article_info3.jsp"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
</head>
<body>
<form action="PrintEnd" method="post">
<p style="width: 135px;">
<big>
Упаковке: <%=session.getAttribute("skuname")%><br>
Артикула: <%=session.getAttribute("articlecode") %><br>
<%=session.getAttribute("articlename") %><br> 
<b><small>Штрих-код : <%=session.getAttribute("barcode") %> ?</small></b>
</big>
</p> 


<div style="overflow: hidden">
<big>
<input style="display: inline; float: inherit; height: 25px; width: 65px;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="article_list2.jsp"'> 	 	
<input style="display: inline; float: inherit; height: 25px; width: 65px;" type="submit" id="btnManual"   value="Печать">
</big>
</div>	
</form>
</body>
</html> 
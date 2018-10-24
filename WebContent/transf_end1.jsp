<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
</head>
<body>
<form action="TransfEnd" method="post">

<p style="width: 135px">
<big>
Поддон <b><%=session.getAttribute("unitcode")%></b><br>
успешно помещен в ячейку <b><%=session.getAttribute("bincode") %></b><br>
</big>
</p>


<div style="overflow: hidden">
<big>
<input style="display: inline; float: inherit; height: 25px; width: 135px;" type="submit" id="btnOk" value="Ок">
</big>
</div>
</form>
</body>

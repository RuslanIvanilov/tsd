<% System.out.println("shipment/info.jsp"); %>
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
<h3>Отгрузка <small>${orderid}</small> ${carcode}</h3>
<p>
<% if(session.getAttribute("unitcode") != null){ %>
Поддон:<%}%><%=session.getAttribute("unitcode")==null?"":session.getAttribute("unitcode")%><br>
<b><u>Поддонов</u></b><br>
Подготовлено: ${preparedcnt}<br>
Отгружено: ${shippedcnt}
</p>

<div class="div">

<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>1btn" type="submit" id="btnOk" value="Ок">

</div>
</form>
</body>
</html>
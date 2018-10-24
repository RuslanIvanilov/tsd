<% System.out.println("inv_blk_remunit.jsp v2"); %>
<% session.setAttribute("blkunit", 0); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() { document.getElementById('scanedtext').focus(); }, 10);</script>
</head>
<body>
<form style="zoom:200%" action="InvBinBlk" method="post">
<br> 
<h3>Инвентаризация</h3> 
<br>
<p>Сканируйте <u>Поддон</u> для <b>удаления</b> из набивной ячейки <%=session.getAttribute("bincode")%></p>


<input type="text" id="scanedtext" name="scanedtext" onkeydown="if (event.keyCode == 13) submit">
<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 52px; width: 100px;" type="button" id="btnOk" value="Назад" onclick="location.href='inv_bin_blk.jsp'"> 	
<input style="display: inline; float: inherit; height: 52px; width: 100px;" type="button" id="btnOk" value="Удалить все" onclick="location.href='InvBinBlkDelAll'"> 
</div>	
</form>
</body>
</html> 
<% System.out.println("inv_bin_blk1.jsp"); %>
<%
  if(session.getAttribute("skuname") !=""){session.setAttribute("sku_delim", ":");}else{session.setAttribute("sku_delim", "");}
  if(session.getAttribute("qualityname") !=""){
	                                             session.setAttribute("quality_delim_s", "(");
	                                             session.setAttribute("quality_delim_e", ")");
	                                          }else{
	                                        	  session.setAttribute("quality_delim_s", "");
		                                          session.setAttribute("quality_delim_e", "");
                                              }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
</head>
<body>
<form action="InvStart" method="post">
<br>
<h3>Инвентаризация*</h3>
<p style="width: 135px">
Набивная ячейка<br>
<%=session.getAttribute("bincode") %><br>
Поддонов в ячейке: <input type="button" id="btnUnitContent" value="<%=session.getAttribute("unitcount") %>" onclick="location.href='unit_list.jsp'"><br>
</p>



<div style="overflow: hidden">
<small>
<input style="display: inline; float: inherit; height: 25px; width: 22%;" type="submit" id="btnCancel" value="Назад">
<input style="display: inline; float: inherit; height: 25px; width: 30%;" type="button" id="btnEdit" value="Добавить" onclick="location.href='inv_blk_addunit.jsp' ">
<input style="display: inline; float: inherit; height: 25px; width: 30%;" type="button" id="btnEdit" value="Удалить"  onclick="location.href='inv_blk_remunit.jsp' ">
</small>
</div>
</form>
</body>
</html>
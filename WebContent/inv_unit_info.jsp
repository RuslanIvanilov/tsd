<% 
  session.setAttribute("question", "");
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
<form style="zoom:200%" action="InvUnit" method="post">
<h3>Инвентаризация</h3> 
<p>
Поддон: <%=session.getAttribute("unitcode") %><br>
в ячейке: <%=session.getAttribute("bincode") %><br>
<%=session.getAttribute("skuname")%> <%=session.getAttribute("sku_delim")%> <%=session.getAttribute("quantity") %>  <%=session.getAttribute("quality_delim_s")%><%=session.getAttribute("qualityname")%><%=session.getAttribute("quality_delim_e")%><br>
<%=session.getAttribute("articlecode") %> <small><%=session.getAttribute("articlename") %></small> 
</p>



<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 52px; width: 110px;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="invent_start.jsp"'> 	
<input style="display: inline; float: inherit; height: 52px; width: 110px;" type="submit" id="btnEdit" value="Изменить"> 	
</div>	
</form>
</body>
</html> 
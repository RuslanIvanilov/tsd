<% System.out.println("quant_info1.jsp"); %>
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
<form style="zoom:200%; overflow-x: hidden;" action="unit_article_list.jsp" method="post">

<p style="width: 135px;">
<big>
Ячейка: <%=session.getAttribute("bincode") %><br>
Поддон: <%=session.getAttribute("unitcode") %><br>
<%=session.getAttribute("skuname")%> <%=session.getAttribute("sku_delim")%> <%=session.getAttribute("quantity") %>  <%=session.getAttribute("quality_delim_s")%><%=session.getAttribute("qualityname")%><%=session.getAttribute("quality_delim_e")%><br>
<%=session.getAttribute("articlecode") %> <small><%=session.getAttribute("articlename") %></small>
</big>
<br><b>Удалить товар с поддона?</b>
</p>


<div style="overflow: hidden">
<big>
<input style="display: inline; float: inherit; height: 25px; width: 45px;" type="submit" id="btnBack"   value="Назад">
<input style="display: inline; float: inherit; height: 25px; width: 45px;" type="button" id="btnDel"   value="Удалить" onclick='document.location.href ="QuantDel"'>
</big>
</div>
</form>
</body>
</html>
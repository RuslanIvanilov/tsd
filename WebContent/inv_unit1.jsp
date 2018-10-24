<% System.out.println("inv_unit1.jsp v2"); %>
<%
  if(session.getAttribute("bin_comment")==null){ session.setAttribute("bin_comment","в ячейке:"); }

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
<form action="InvUnitAction" method="post">
<h3>Инвентаризация</h3>
<p style="width: 135px;"><big>
Поддон: <%=session.getAttribute("unitcode") %><br>
<small>
<%if(session.getAttribute("bincode") != null){ %>
<%=session.getAttribute("bin_comment")%> <%=session.getAttribute("bincode") %><br>
</small>
<% } else { %>
без ячейки<br>
<% } %>
<%if(session.getAttribute("articlecode") != null){ %>
<%=session.getAttribute("skuname")%> <%=session.getAttribute("sku_delim")%> <%=session.getAttribute("quantity") %>  <%=session.getAttribute("quality_delim_s")%><%=session.getAttribute("qualityname")%><%=session.getAttribute("quality_delim_e")%><br>
<%=session.getAttribute("articlecode") %> <small><%=session.getAttribute("articlename") %></small>
<% } else { if((session.getAttribute("quantity") != null) && (session.getAttribute("articlecode") == null)){%>
артикулов: <%=session.getAttribute("quantity") %>
<%} else {%>
пустой
<%} } %>
</big></p>



<div style="overflow: hidden">
<% if(session.getAttribute("bincode") != null){ %>
<small>
<input style="display: inline; float: inherit; height: 25px; width: 24px; " type="button" id="btnCancel" value="Назад" onclick='document.location.href ="${backpage}"'>
<input style="display: inline; float: inherit; height: 25px; width: 28px; " type="button" id="btnArtList"   value="Товары" onclick='document.location.href ="unit_article_list.jsp"'>
<input style="display: inline; float: inherit; height: 25px; width: 30px; " type="submit" id="btnEdit" value="Очистить">
</small>
<% } else { %>
<input style="display: inline; float: inherit; height: 25px; width: 35px;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="${backpage}"'>
<%	if(session.getAttribute("quantity") != null){ %>
		<input style="display: inline; float: inherit; height: 25px; width: 40px;" type="button" id="btnArtList"   value="Товары" onclick='document.location.href ="unit_article_list.jsp"'>
		<input style="display: inline; float: inherit; height: 25px; width: 50px;" type="submit" id="btnEdit" value="Очистить">
<%		}  %>
<% } %>
</div>
</form>
</body>
</html>
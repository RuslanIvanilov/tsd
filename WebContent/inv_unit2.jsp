<% System.out.println("inv_unit2.jsp"); %>
<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="inv_unit1.jsp"/>
<% } %>
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
<form style="zoom:200%" action="InvUnitAction" method="post">
<h3>Инвентаризация</h3>
<p>
Поддон: <%=session.getAttribute("unitcode") %><br>
<%if(session.getAttribute("bincode") != null){ %>
<%=session.getAttribute("bin_comment")%> <%=session.getAttribute("bincode") %><br>
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
</p>



<div style="overflow: hidden">
<small>
<% if(session.getAttribute("bincode") != null){ %>
<input style="display: inline; float: inherit; height: 52px; width: 45px;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="${backpage}"'>
<input style="display: inline; float: inherit; height: 52px; width: 45px;" type="button" id="btnArtList"   value="Товары" onclick='document.location.href ="unit_article_list.jsp"'>
<input style="display: inline; float: inherit; height: 52px; width: 45px;" type="submit" id="btnEdit" value="Очистить">
<% } else { %>
<input style="display: inline; float: inherit; height: 52px; width: 55px;" type="button" id="btnCancel" value="Назад" onclick='document.location.href ="${backpage}"'>
<%	if(session.getAttribute("quantity") != null){ %>
		<input style="display: inline; float: inherit; height: 52px; width: 65px;" type="button" id="btnArtList"   value="Товары" onclick='document.location.href ="unit_article_list.jsp"'>
		<input style="display: inline; float: inherit; height: 52px; width: 85px;" type="submit" id="btnEdit" value="Очистить">
<%		}  %>
<% } %>
</small>
</div>
</form>
</body>
</html>
<% if(session.getAttribute("oldbrowser") != null) {%>
 <jsp:forward page="scan_article1.jsp"/>
<% } %>
<% System.out.println("scan_article.jsp"); %>
<%
  if(session.getAttribute("skuname") !=null){session.setAttribute("sku_delim", ":");}else{session.setAttribute("sku_delim", "");}
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
 <script>setTimeout(function() {
	  //document.getElementById('scaned_art_text').focus();
	  //document.getElementById('btnOk').disabled = 0;
	  //document.getElementById('btnCancel').disabled = 0;
	  }, 10);

 function scanf(){
	 document.getElementById('scaned_art_text').focus();
 }
 </script>
</head>
<body onload="document.getElementById('scaned_art_text').value = ''; scanf()">
<form style="zoom:200%" action="ScanArticle" method="post" onclick="scanf()">
<br>
<p>
<small>Артикул <%=session.getAttribute("articlecode")%> </small><br>
<%=session.getAttribute("articlename")%><br>
<%=session.getAttribute("skuname")%> <%=session.getAttribute("sku_delim")%> <b><%=session.getAttribute("quantity") %></b><br>
<b>Сканируйте товар</b></p>


<input style="border:none" type="text" id="scaned_art_text" name="scaned_art_text" onkeypress="if (event.keyCode == 13) submit" onblur="scanf()">
<div style="overflow: hidden">
<input style="display: inline; float: inherit; height: 52px; width: 90px;" type="button" id="btnCancel" value="Назад" onfocus="scanf()"  onclick='document.location.href ="<%=session.getAttribute("backpage")%>"'>
<input style="display: inline; float: inherit; height: 52px; width: 130px;" type="button" id="btnOk" value="Хватит" onfocus="scanf()" onclick='document.location.href ="<%=session.getAttribute("savepage")%>"'>
</div>
</form>
</body>
</html>
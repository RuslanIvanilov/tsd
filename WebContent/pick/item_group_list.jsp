<% System.out.println("item_group_list.jsp"); %>
<% session.setAttribute("more", null); %>

<%@page import="java.util.*"%>
<%@page import="ru.defo.controllers.OrderController"%>
<%@page import="ru.defo.controllers.ArticleController"%>
<%@page import="ru.defo.model.WmVendorGroup"%>
<%@page import="ru.defo.util.DefaultValue"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<link rel="stylesheet" type="text/css" href="tsd.css">
<title>TSD</title>
<script>
	setTimeout(function() {
		document.getElementById('Select').focus();
	}, 10);
</script>
<script>
	function requestSelectedState()
	{
	  var e = document.getElementById('select');
	  document.location.href = '<%=session.getAttribute("actionpage")%>?vndgroup='+e.options[e.selectedIndex].value;

	}

</script>

</head>
<body>
	<form class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>form"
		action='javascript:requestSelectedState(0)'
		method="post">
		<p><b><small>На поддоне ${ordercode}</small></b></p>
		<div class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>selectdiv">
			<select id="select" size="11" class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>select">
			 	<%
			 	Long orderId = 0L;
			 	if(session.getAttribute("orderid")!=null) orderId = Long.valueOf(session.getAttribute("orderid").toString());

			 	Map<WmVendorGroup, Long> groupMap =  new OrderController().getOpenPickGroupListByOrderId(orderId);
	        	for(Map.Entry<WmVendorGroup, Long> entry : groupMap.entrySet())
	        	{
			 	%>
				<option value='<%=entry.getKey().getVendorGroupId()%>' class='option'><%=entry.getKey().getDescription()%> : <%=entry.getValue()%></option>
				<% } %>
			</select>
		</div>

		<div class="div">
			<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>21bin" type="button" id="btnCancel" name="btnCancel" value="Назад" onclick="document.location.href ='${backpage}'">

	        <input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>22bin" type="button" id="btnAction" name="btnAction" value="${actionname}" onclick="requestSelectedState()">

		</div>
	</form>
</body>
</html>
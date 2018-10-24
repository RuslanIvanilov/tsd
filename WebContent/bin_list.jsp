<% System.out.println("bin_list.jsp :: articlecode : "+session.getAttribute("articlecode")); %>
<%@page import="java.util.*"%>
<%@page import="ru.defo.controllers.QuantController"%>
<%@page import="ru.defo.model.views.VQuantInfoShort"%>
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
	function requestSelected() {
		var e = document.getElementById('Select');
	    document.location.href = "InvArticle?selectedquant="+e.options[e.selectedIndex].value;
	}
</script>

</head>
<body>
	<form class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>form"
		action='javascript:requestSelected()'
		method="post">
		<p>
			<small><b>Адреса:</b></small>
		</p>
		<div
			class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>selectdiv">
			<select id="Select" size="20" class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>select">
				<%
				    List<VQuantInfoShort> quantList = new QuantController().getVQuantInfoListByArticle(String.valueOf(session.getAttribute("articlecode")));
					Iterator<VQuantInfoShort> iterator = quantList.iterator();
					while (iterator.hasNext()) {
						VQuantInfoShort vQuantInfoShort = (VQuantInfoShort) iterator.next();
				%>
				<option value='<%=vQuantInfoShort.getQuantId() %>' class='option'><%=vQuantInfoShort.getBinCode()==null?"Без ячейки":vQuantInfoShort.getBinCode()%> : <%=vQuantInfoShort.getQuantity()%>(<%=vQuantInfoShort.getQualityName()%>)</option>
				<%} %>
			</select>
		</div>


		<div class="div">
			<input
				class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>21bin"
				type="button" id="btnCancel" name="btnCancel" value="Назад"
				onclick="javascript:history.back()"> <input
				class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>22bin"
				type="submit" id="btnOk" name="btnOk" value="Ок">
		</div>
	</form>
</body>
</html>
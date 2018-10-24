<%@page import="ru.defo.model.views.Vorderpos"%>
<% System.out.println("binlist.jsp"); %>
<% session.setAttribute("more", null); %>
<%@page import="java.util.*"%>
<%@page import="ru.defo.controllers.OrderController"%>
<%@page import="ru.defo.controllers.ArticleController"%>
<%@page import="ru.defo.controllers.BinController"%>
<%@page import="ru.defo.controllers.QuantController"%>
<%@page import="ru.defo.model.WmOrderPos"%>
<%@page import="ru.defo.model.WmArticle"%>
<%@page import="ru.defo.model.WmBin"%>
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
		var e = document.getElementById('Select');

		document.location.href = '<%=session.getAttribute("action")%>?bincode='+e.options[e.selectedIndex].value;
	}

</script>

</head>
<body>
	<form class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>form"
		action='javascript:requestSelectedState(0)'
		method="post">
		<p><b><small>Ячейки товара <%=session.getAttribute("articlecode") %></small></b></p>
		<div class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>selectdiv">
			<select id="Select" size="11" class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>select">
				<%
				 	Long articleId = Long.valueOf(session.getAttribute("articleid").toString());
					WmOrderPos orderPos = new OrderController().getOrderPosByOrderArticleQState(session.getAttribute("ordercode").toString(), articleId, DefaultValue.QUALITY_STATE);
					Map<WmBin, Long> binMap = new BinController().getBinMapByOrderPosArticle(orderPos.getOrderPosId().toString(), session.getAttribute("articlecode").toString());
					Map<WmBin, Long> sortBinMap = new BinController().getSortedBinMap(binMap);
					Long sumQty = 0L;

			 	    for(Map.Entry<WmBin, Long> entry : sortBinMap.entrySet())
				    {
			 	    	WmArticle article = new ArticleController().getArticleByCode(session.getAttribute("articlecode").toString());
			 	    	if(article != null)
			 	    		sumQty = new QuantController().getSumQtyByBinArticle(entry.getKey(), article);
				%>
							<option style="color:black; font-weight:bold"  value='<%=entry.getKey().getBinId() %>' class='option'><%=entry.getKey().getBinCode()%> : <%=sumQty%></option>
				<% 	} %>
			</select>
		</div>

		<div class="div">
			<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>21bin" type="button" id="btnCancel" name="btnCancel" value="Назад" onclick="document.location.href ='${backpage}'">
			<!-- <input class="wm22bin" type="submit" id="btnOk" name="btnAction" value="Изменить">  -->
			<% if(session.getAttribute("more") == null){	%>
					<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>22bin" type="button" id="btnAction" name="btnAction" value="${actionname}" onclick="requestSelectedState()">
			<% } %>
		</div>
	</form>
</body>
</html>
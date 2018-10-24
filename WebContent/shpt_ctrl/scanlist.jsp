<%@page import="ru.defo.model.views.Vorderpos"%>
<% System.out.println("scanlist.jsp"); %>
<% session.setAttribute("more", null); %>
<%@page import="java.util.*"%>
<%@page import="ru.defo.controllers.OrderController"%>
<%@page import="ru.defo.controllers.ArticleController"%>
<%@page import="ru.defo.model.views.VQuantInfoShort"%>
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
	function requestSelectedState(fordel)
	{
		var e = document.getElementById('Select');
		if(fordel == 1){
			document.location.href = '<%=session.getAttribute("action")%>?article='+e.options[e.selectedIndex].value;
		} else {
			document.location.href = '<%=session.getAttribute("action")%>';
		}
	}

</script>

</head>
<body>
	<form class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>form"
		action='javascript:requestSelectedState(0)'
		method="post">
		<p><b><small>На поддоне <%=session.getAttribute("unitcode") %></small></b></p>
		<div class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>selectdiv">
			<select id="Select" size="11" class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>select">
				<%
				    String scanqty;
				    Map<String, String> map = new HashMap<String, String>();
				    if(session.getAttribute("scanmap") !=null) map.putAll((HashMap<String, String>) session.getAttribute("scanmap"));

				    if(session.getAttribute("actiondel")==null){
				 	    List<Vorderpos> quantList = new OrderController().getVOrderPosList(session.getAttribute("ordercode").toString());
				 	    for(Vorderpos quantInfo :quantList){
				 	      	scanqty = map.get(quantInfo.getArticleCode());

				 	      	if(scanqty != null){
				 	    	   	map.remove(quantInfo.getArticleCode());
								Long balanceQty = (Long.decode(scanqty)+(quantInfo.getCtrlQuantity()==null?0:quantInfo.getCtrlQuantity()))-quantInfo.getExpectQuantity();
							    map.put(quantInfo.getArticleCode(), balanceQty==0?"":(balanceQty>0?String.valueOf(balanceQty):String.valueOf(balanceQty)));
				 	      	}/* else{
				 	      		map.put(quantInfo.getArticleCode(), String.valueOf(-(quantInfo.getFactQuantity()==null?0:quantInfo.getFactQuantity())));
				 	      	}*/

				 	    }

				 	    session.setAttribute("finalmap", map);
				    }

			 	    for(Map.Entry<String, String> entry : map.entrySet())
				    {
						if(Integer.decode(entry.getValue().trim().length()==0?"0":entry.getValue())>0  && session.getAttribute("actionname") == DefaultValue.TOSAVE){
							session.setAttribute("more", 1);
				%>
							<option style="color:red; font-weight:bold"  value='<%=new ArticleController().getArticleByCode(entry.getKey()).getArticleId() %>' class='option'><%=entry.getKey()%> : <%=entry.getValue()%></option>
				<% 		} else { %>
							<option value='<%=new ArticleController().getArticleByCode(entry.getKey()).getArticleId() %>' class='option'><%=entry.getKey()%> : <%=entry.getValue()%></option>
				<%		}
				    } %>
			</select>
		</div>

		<div class="div">
			<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>21bin" type="button" id="btnCancel" name="btnCancel" value="Назад" onclick="document.location.href ='${backpage}'">
			<!-- <input class="wm22bin" type="submit" id="btnOk" name="btnAction" value="Изменить">  -->
			<% if(session.getAttribute("more") == null){	%>
					<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>22bin" type="button" id="btnAction" name="btnAction" value="${actionname}" onclick="requestSelectedState(${actiondel})">
			<% } %>
		</div>
	</form>
</body>
</html>
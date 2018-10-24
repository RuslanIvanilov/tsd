<% System.out.println("bin_list1.jsp :: articlecode : "+session.getAttribute("articlecode")); %>
<%@page import="java.util.*"%>
<%@page import="ru.defo.controllers.QuantController"%>
<%@page import="ru.defo.controllers.HistoryController"%>
<%@page import="ru.defo.model.views.VQuantInfoShort"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
<script>
	setTimeout(function() {
		document.getElementById('Select').focus();
	}, 10);
</script>
<script>
	function requestSelected() {
		var e = document.getElementById('Select');
	    document.location.href = "InvArticle?selectedbin="+e.options[e.selectedIndex].value;
	}
</script>

<style>
option {
	padding: 0px; /* Поля вокруг текста */
	text-overflow: ellipsis; /* Добавляем многоточие */
}

select {
    font-family: "Arial Narrow", Arial, sans-serif;
	float: none;
	overflow-y: scroll;
	height: 80px;
	width: 110px;
	font-size: 14px;
}
</style>

</head>
<body>
	<form
		action='javascript:requestSelected()'
		method="post">
		<div
			style="width: 120px; overflow-x: none; overflow-y: none; height: 70px;">
			<select id="Select" size="20" class="select">
				<%
				    List<VQuantInfoShort> quantList = new QuantController().getVQuantInfoListByArticle(String.valueOf(session.getAttribute("articlecode")));
					Iterator<VQuantInfoShort> iterator = quantList.iterator();
					while (iterator.hasNext()) {
						VQuantInfoShort vQuantInfoShort = (VQuantInfoShort) iterator.next();
						String bincode = vQuantInfoShort.getBinCode();
						if(bincode == null){
						    /* Поднять из истории операций */
						    if(vQuantInfoShort.getUnitCode() != null)
						    	bincode = new HistoryController().getLastBinByUnit(vQuantInfoShort.getUnitCode())+"-->";
						}

						Long qty = vQuantInfoShort.getQuantity();
						String qsName = vQuantInfoShort.getQualityName();
				%>
				<option value='<%=bincode%>' class='option'><%=bincode%> : <%=qty%>(<%=qsName%>)</option>
				<%} %>
			</select>
		</div>


		<div style="overflow: hidden">
			<input
				style="display: inline; float: inherit; height: 25px; width: 40px;"
				type="button" id="btnCancel" name="btnCancel" value="Назад"
				onclick="javascript:history.back()"> <input
				style="display: inline; float: inherit; height: 25px; width: 50px;"
				type="submit" id="btnOk" name="btnOk" value="Ок">
		</div>
	</form>
</body>
</html>
<% System.out.println("userid : "+session.getAttribute("userid").toString()); %>
<% System.out.println("unit_list.jsp");
   session.setAttribute("backpage", "unit_list.jsp");
%>
<%@page import="java.util.*"%>
<%@page import="ru.defo.controllers.UnitController"%>
<%@page import="ru.defo.controllers.BinController"%>
<%@page import="ru.defo.model.WmUnit"%>
<%@page import="ru.defo.model.WmBin"%>
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
		document.getElementById('unitSelect').focus();
	}, 10);
</script>
<script>
	function requestSelectedState() {
		var e = document.getElementById('unitSelect');
	    document.location.href = "InvUnit2?unitcode="+e.options[e.selectedIndex].value;
	}
</script>

<style>
option {
	padding: 0px; /* Поля вокруг текста */
	text-overflow: ellipsis; /* Добавляем многоточие */
}

select {
	float: none;
	overflow-y: scroll;
	overflow-x: auto;
	height: 60px;
	width: 110px;
	font-size: 11px;
}
</style>

</head>
<body>
	<form style="zoom: 200%"
		action='javascript:requestSelectedState()'
		method="post">

		<p>
			<b><small>В ячейке <%=session.getAttribute("bincode") %></small></b>
		</p>
		<div
			style="width: 110px; overflow-x: none; overflow-y: none; height: 60px;">
			<select id="unitSelect" size="11" class="select">
				<%
				    WmBin bin = new BinController().getBin(String.valueOf(session.getAttribute("bincode")));
				    List<WmUnit> unitList = new UnitController().getUnitListByBinId(bin.getBinId());
					Iterator<WmUnit> iterator = unitList.iterator();
					while (iterator.hasNext()) {
						WmUnit unit = (WmUnit) iterator.next();
				%>
				<option value='<%=unit.getUnitCode() %>' class='option'><%=unit.getUnitCode()%></option>
				<%
					}
				%>
			</select>
		</div>


		<div style="overflow: hidden">
			<input
				style="display: inline; float: inherit; height: 25px; width: 40%;"
				type="button" id="btnCancel" name="btnCancel" value="Назад"
				onclick="document.location.href ='inv_bin_blk.jsp'">
				<input
				style="display: inline; float: inherit; height: 25px; width: 50%;"
				type="submit" id="btnOk" name="btnOk" value="Изменить">
		</div>
	</form>
</body>
</html>
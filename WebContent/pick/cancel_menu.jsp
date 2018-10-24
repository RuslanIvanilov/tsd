<% System.out.println("cancel_menu.jsp"); %>
<%@page import="java.util.*"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0 , maximum-scale=10, user-scalable=no">

<meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>

<title>TSD</title>
<script>
	setTimeout(function() {
		document.getElementById('itemselect').focus();
	}, 10);
</script>
<script>
	function requestSelectedItem() {
		var e = document.getElementById('itemselect');
	    document.location.href="CancelMenu?&cancel_item="+e.options[e.selectedIndex].value;
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
	height: 160px;
	width: 230px;
	font-size: 34px;
	padding: 0px;
}

body {
	zoom: 200%;
	margin-right: -15px;
	margin-bottom: -15px;
	overflow-y: hidden;
	overflow-x: hidden;
}

ooption {
	padding: 0px; /* Поля вокруг текста */
	text-overflow: ellipsis; /* Добавляем многоточие */
}

oselect {
	float: none;
	overflow-y: scroll;
	height: 73%;
	width: 100%;
	font-size: 13px;
	padding: 0px;
	border: hidden;
}

obody {

	margin-right: -15px;
	margin-bottom: -15px;
	overflow-y: hidden;
	overflow-x: hidden;
}
</style>
<link rel="stylesheet" type="text/css" href="tsd.css">
</head>
<body>
	<form class="<%=session.getAttribute("oldbrowser") != null?"o":"" %>body"
		action='javascript:requestSelectedItem()'
		method="post"
		>
		<p>
			<b>Меню подбора</b>
		</p>
		<div
			style="width: 230px; overflow-x: none; overflow-y: none; height: 160px;">
			<select id="itemselect" size="11" class="<%=session.getAttribute("oldbrowser") != null?"o":"" %>select">

				<option selected value='1' class='<%=session.getAttribute("oldbrowser") != null?"o":"" %>option'>Больше нету</option>
				<option          value='2' class='<%=session.getAttribute("oldbrowser") != null?"o":"" %>option'>Поддон->Док</option>
				<option          value='3' class='<%=session.getAttribute("oldbrowser") != null?"o":"" %>option'>Список ячеек</option>

			</select>
		</div>


		<div style="overflow: hidden">
			<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>21bin"
				type="button" id="btnCancel" name="btnCancel" value="Назад"
				onclick=" location.replace('${cancelAction}') ">
			<input class="<%=session.getAttribute("oldbrowser") != null?"ce":"wm" %>22bin"
				type="submit" id="btnOk" name="btnOk" value="Ок">
		</div>
	</form>
</body>
</html>
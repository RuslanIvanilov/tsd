
<%
	System.out.println("advice_gui_main.jsp");
%>
<%@page import="java.util.*"%>
<%@page import="ru.defo.controllers.BinController"%>
<%@page import="ru.defo.controllers.AuthorizationController"%>
<%@page import="ru.defo.model.WmBin"%>
<%@page import="ru.defo.model.WmUser"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>WMS Дэфо</title>


</head>
<body>
	<form style="zoom: 200%" action="AdviceGUIStart" method="post">
		<h3>Создание приемки</h3>


		<div style="overflow: hidden">
			<input
				style="display: inline; float: inherit; height: 25px; width: 60px;"
				type="button" id="btnOk" value="Назад"
				onclick="location.href='Mmenu'"> <input
				style="display: inline; float: inherit; height: 25px; width: 70px;"
				class='button' type="button" id="btnAddAdvice"
				value="Создать приемку" onClick='opendialog()'> <input
				style="display: inline; float: inherit; height: 25px; width: 85px;"
				type="submit" id="btnSave" value="Сохранить">
		</div>

		<!--  -->
		<div class="back-dialog" id="dialog">
			<!-- Блок с нашим контентом -->
			<div class="dialog-content">
				<!-- Заголовок и кнопка закрытия окна -->
				<div class="dialog-title">
					<span>Заголовок диалогового окна</span> <a class='close-dialog'
						href='javascript: closedialog()'></a>
				</div>
				Текст диалогового окна
				<table>
					<tr>
						<td>Трансп. средство гос.гос.номер:</td>
						<td><input type="text" id="carno" name="carno"></td>
					</tr>
					<tr>
						<td>Приемщик:</td>
						<td><select id="selectExecutor" class="select">
								<%
									List<WmUser> userList = new AuthorizationController().getUserForAdvice();
									for (WmUser user : userList) {
								%>
								<option value='<%=user.getUserId()%>' class='option'><%=user.getSurname() + " " + user.getFirstName().charAt(0) + "."%></option>
								<%
									}
								%>
						</select></td>
					</tr>
					<tr>
					<td>Док №:</td>
					<td>
					<select id="selectDok" class="select">
				<%
					List<WmBin> binList = new BinController().getDokList();
					for (WmBin bin : binList) {
				%>
				<option value='<%=bin.getBinId()%>' class='option'><%=bin.getBinCode().charAt(10)%></option>
				<%
					}
				%>
			</select>
					</td>
					</tr>
				</table>



			</div>
		</div>

		<!--  -->

	</form>
</body>
</html>
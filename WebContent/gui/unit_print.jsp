<%
	System.out.println("GUI/unit_print.jsp");
%>
<%@page import="java.util.*"%>
<%@page import="ru.defo.model.views.Vrouteordpos"%>
<%@page import="java.util.Date"%>
<%@page import="ru.defo.model.WmUnit"%>
<%@page import="ru.defo.managers.UnitManager"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
<title>Поддоны печать штрих-кодов</title>
<!--
<style>
@font-face
	{font-family:"Code 128";
	panose-1:5 0 0 0 0 0 0 0 0 0;
	mso-font-charset:2;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:0 268435456 0 0 -2147483648 0;}

.bc{
	font-size:16.0pt;line-height:107%;
	font-family:"Code 128";
	mso-ansi-language:EN-US
	}

</style>
-->
</head>
<!-- <body onload="window.print(); window.close();"> -->
<body>
<!-- <h3>Поддоны серия ${unitqty} листов от <%=new Date().toLocaleString()  %></h3>  -->

<form>
<%
int cnt = Integer.valueOf(session.getAttribute("unitqty").toString())*4;
if(session.getAttribute("unittype")==null) return;
for(int i=0; i<cnt; i++){
    WmUnit unit = new UnitManager().createUnit(session.getAttribute("unittype").toString());
  // WmUnit unit = new WmUnit().initUnitByCode("EU00012345");
%>

<!-- <p><span lang=EN-US style='padding-left:50px;font-size:60.0pt;line-height:150%;font-family:"Code 128";'>EU12345678</span></p>  -->

<!-- jsp { -->
<hr>
<table id="unittable1" border="0px">
<tr>
<!-- <td style="padding-left: 20px"><span style='font-size:80.0pt; font-family:"Code 128";'>D<%=unit.getUnitCode()%></span></td> -->
<td style="padding-left: 20px"><img style="height: 170%; width: 90%" src='https://barcode.tec-it.com/barcode.ashx?data=<%=unit.getUnitCode()%>&code=Code128&dpi=120&dataseparator='/></td>
<td style="padding-left: 20px; padding-right: 20px">|</td>
<td style="padding-left: 20px"><img style="height: 170%; width: 90%" src='https://barcode.tec-it.com/barcode.ashx?data=<%=unit.getUnitCode()%>&code=Code128&dpi=120&dataseparator='/></td>
<td>|</td>
</tr>
</table>
<!-- jsp } -->
<%

} %>

<hr>

</form>
</body>
</html>
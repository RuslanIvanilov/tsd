<%@page import="ru.defo.managers.PreOrderManager"%>
<%
	System.out.println("GUI/print_day_list2.jsp");
%>
<%@page import="java.util.*"%>
<%@page import="ru.defo.model.views.Vrouteordpos"%>
<%@page import="ru.defo.model.WmPreOrder"%>
<%@page import="ru.defo.model.WmOrder"%>
<%@page import="ru.defo.model.WmRoute"%>
<%@page import="ru.defo.model.WmCar"%>
<%@page import="ru.defo.managers.OrderManager"%>
<%@page import="ru.defo.managers.PreOrderManager"%>
<%@page import="ru.defo.managers.CarManager"%>
<%@page import="ru.defo.controllers.DeliveryController"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<meta http-equiv='cache-control' content='no-cache, no-store, must-revalidate'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
<title>ОТЧЕТ Доставка ${reportdate} <%=new java.sql.Time(System.currentTimeMillis()).toString() %> ${vgname}</title>

</head>
<body>
<h3>Доставка на ${reportdate} ${vgname}</h3>
<p><%=session.getAttribute("chCollapsedFlt").toString() %></p>
<form>
<table id="routetable" border="1px" style="border-collapse: collapse;">

<%
    int i = 0;
	List<WmRoute> routeList = new DeliveryController().getRouteListByDateFilter(session.getAttribute("dateflt").toString());

	for (WmRoute route : routeList) {
			i++;
			WmCar car = new CarManager().getCarById(route.getCarId());
			List<WmOrder> orderList = new ArrayList<WmOrder>();
			orderList.addAll(new OrderManager().getOrderListByRoute(route));
%>

<tr><td colspan="8">-</td></tr>
<tr>
	<td colspan="8" align="left" title="routeId : <%=route.getRouteId()%>"><b>Заявка на транспорт</b> <%=route.getRouteCode()%>
	 &nbsp;&nbsp;&nbsp;&nbsp;<b><%=car==null?"":car.getCarCode() %> <%=route.getRouteId() %></b></td>
</tr>

<% 			for(WmOrder order : orderList){
				List<WmPreOrder> preOrderList = new PreOrderManager().getPreOrderByWmOrderLink(order.getOrderId());
%>
<tr>
<td colspan="3" align="center"><small><b>Складская отгрузка Но.</b></small></td>
<td colspan="1" align="center" title="orderId : <%=order.getOrderId()%>">
<img class="img-responsive"
src="http://toolki.com/en/barcode/?foreground=000000&amp;code=<%=order.getOrderCode() %>&amp;font_size=0&amp;module_width=0.6&amp;module_height=10.0&amp;text_distance=5.0&amp;background=FFFFFF&amp;quiet_zone=6.5&amp;center_text=True&amp;type=ean13"
alt="">
</td>
<td colspan="4" align="center"><%=order.getOrderCode()==null?"": order.getOrderCode()%></td>
</tr>


<tr bgcolor="#DCDCDC">
	<td align="center">#</td>
	<td align="center"><b>Документ</b></td>
	<td align="center"><b>Статус</b></td>
</tr>

<% 				for(WmPreOrder pre : preOrderList){

%>

<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
<td align="center"><small><%=i %></small></td>
<td align="center" title="WmOrderId : <%=order.getOrderId()%> PreOrderId : <%=pre.getOrderId()%>"><%=pre.getOrderCode()%></td>
<td align="center" title="StatusId : <%=pre.getStatusId()%>"><small><%=pre.getStatus().getDescription()%></small></td>
</tr>

<% 				}
			}
	}  %>

</table>
</form>
</body>
</html>
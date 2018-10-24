<%
	System.out.println("GUI/print_day_list.jsp");
%>
<%@page import="java.util.*"%>
<%@page import="ru.defo.model.views.Vrouteordpos"%>
<%@page import="ru.defo.model.WmOrder"%>
<%@page import="ru.defo.managers.OrderManager"%>
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
<table id="routetable" border="1px">

<%
    int i = 0;
	Long routeId = 0L;
	Long groupId = 0L;
	Long orderId = 0L;
	String ordCode = null;

	List<Vrouteordpos> routeOrdPosList = Vrouteordpos.getVRouteOrdPosList(session.getAttribute("selectvg")==null?null:session.getAttribute("selectvg").toString(),
																		  session.getAttribute("dateflt").toString(),
																		  session.getAttribute("carcodeflt")==null?null:session.getAttribute("carcodeflt").toString(),
																		  session.getAttribute("routelist") ==null?null:session.getAttribute("routelist").toString()
																	      );

	if(routeOrdPosList != null){
		for (Vrouteordpos route : routeOrdPosList) {
			i++;


%>
<!-- Маршруты { -->
<% if(!routeId.equals(route.getRouteId()) || (session.getAttribute("carcodeflt")==null && !groupId.equals(route.getVendorGroupId()==null?0L:route.getVendorGroupId()))
		//&& session.getAttribute("chCollapsedFlt").toString().equals("true")
	  )
	{
	   orderId = 0L;
	   routeId = route.getRouteId();
	  // groupId = route.getVendorGroupId()==null?0L:route.getVendorGroupId();

%>
<% if(session.getAttribute("carcodeflt")==null){ %>
<tr><td colspan="8">-</td></tr>
<% } %>
<tr>
	<td colspan="8" align="left" title="routeId : <%=route.getRouteId()%>"><b>Заявка на транспорт</b> <%=route.getRouteCode()%>
	 &nbsp;&nbsp;&nbsp;&nbsp;<b><%=route.getCarCode()==null?"":route.getCarCode() %></b></td>
</tr>
<% } //else { if(route.getPreOrderCode().equals(ordCode) && session.getAttribute("chCollapsedFlt").toString().equals("true")) continue; }%>
<!-- Маршруты } -->

<!-- Складские отгрузки { -->
<% if(!orderId.equals(route.getOrderId()))
	{
	orderId = route.getOrderId();
	i=1;
	WmOrder order = null;
	String wmOrderCode = null;

	if(route.getOrderId()!= null)
		order = new OrderManager().getOrderById(route.getOrderId());

	if(order instanceof WmOrder) wmOrderCode = order.getOrderCode();

%>
<tr>
<td colspan="3" align="center"><small><b>Складская отгрузка Но.</b></small></td>
<td colspan="1" align="center" title="orderId : <%=route.getOrderId()%>">
<img class="img-responsive"
src="http://toolki.com/en/barcode/?foreground=000000&amp;code=<%=wmOrderCode %>&amp;font_size=0&amp;module_width=0.6&amp;module_height=10.0&amp;text_distance=5.0&amp;background=FFFFFF&amp;quiet_zone=6.5&amp;center_text=True&amp;type=ean13"
alt="">
</td>
<!-- <td colspan="5" align="center"><%=wmOrderCode==null?"": wmOrderCode%> <%=session.getAttribute("vgname")==null?(session.getAttribute("carcodeflt")==null?"":session.getAttribute("carcodeflt").toString()):session.getAttribute("vgname").toString() %></td> -->
<td colspan="4" align="center"><%=wmOrderCode==null?"": wmOrderCode%> <%=session.getAttribute("carcodeflt")!=null?"":(route.getVendorGroupName()==null?"<неустановлена>":route.getVendorGroupName())  %></td>
</tr>
<tr bgcolor="#DCDCDC">
	<td align="center">#</td>
	<td align="center"><b>Документ</b></td>
	<td align="center"><b>Товар</b></td>
	<td align="center"><small><b>Наименование</b></small></td>
	<td align="center"><b>Упаковка</b></td>
	<td align="center"><small><b>Ожид. кол-во</b></small></td>
	<td align="center"><small><b>Факт. кол-во</b></small></td>
	<td align="center"><b>Статус</b></td>

</tr>
<%  }  %>
<!-- Складские отгрузки } -->

<!-- Строки отгрузки { -->
<% if(i%2!=0){ %> <tr bgcolor="#F5F8DA"> <% } else { %> <tr> <% } %>
<td align="center"><small><%=i %></small></td>
<td align="center" title="WmOrderId : <%=route.getOrderId()%> WmOrderLine : <%=route.getOrderPosId()%>"><%=(route.getPreOrderCode().equals(ordCode) && groupId.equals(route.getVendorGroupId()==null?0L:route.getVendorGroupId())   )?"":route.getPreOrderCode()%></td>
<td align="center"><%=route.getArticleCode()==null?"":route.getArticleCode()%></td>
<td align="left"><small><%=route.getArticleName()==null?"":route.getArticleName()%></small></td>
<td align="center"><small><%=route.getSkuName()==null?"":route.getSkuName()%></small></td>
<td align="right"><small><%=route.getExpectQuantity()==null?"":route.getExpectQuantity()%></small></td>
   <%
   if(route.getFactQuantity()!=null)
   {   %>
   <td align="right" onclick="moveToFactQtyDetails(<%=route.getOrderId()%>, <%=route.getOrderPosId()%>)"><small><%=route.getFactQuantity()==0?"":route.getFactQuantity()%></small></td>
   <% } else { %>
   <td align="right"><small></small></td>
   <% } %>
   <td align="center"><small><%=route.getStatusName()==null?"":route.getStatusName()%></small></td>
   <!-- <td align="center"><%=route.getVendorGroupName()==null?"":route.getVendorGroupName() %></td> -->
</tr>

<!-- Строки отгрузки } -->



<% 	if(!route.getPreOrderCode().equals(ordCode)){ ordCode = route.getPreOrderCode(); }
	if(!groupId.equals(route.getVendorGroupId()==null?0L:route.getVendorGroupId())){groupId = route.getVendorGroupId()==null?0L:route.getVendorGroupId();}
     }
	}	%>


</table>
</form>
</body>
</html>
<% System.out.println("article_list1.jsp --> articlefilter : "+session.getAttribute("articlefilter")); %>
<%@page import="java.util.*"%>
<%@page import="ru.defo.model.WmArticle"%>
<%@page import="ru.defo.controllers.ArticleController"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html onclick="scanf()">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=10 , maximum-scale=10, user-scalable=no">
<title>TSD</title>
<script>
	setTimeout(function() {
		document.getElementById('articlelist').focus();
	}, 10);

	function requestSelected() {
		var e = document.getElementById('Select');
	    document.location.href = "AssignArticleInfo?selectedart="+e.options[e.selectedIndex].value;
	}

	function scanf(){
		 init();
		 document.getElementById('articlelist').focus();
	 }
</script>

<style>
option {
    font-family: "Arial Narrow", Arial, sans-serif;
	padding: 0px; /* Поля вокруг текста */
	text-overflow: ellipsis; /* Добавляем многоточие */
	style="font-size:10px;"
}

select {
    font-family: "Arial Narrow", Arial, sans-serif;
	float: none;
	overflow-y: auto;
	overflow-x: scroll;
	height: 60px;
	width: 110px;
	font-size: 11px;
	border: 0px;
}
</style>

</head>
<body onclick="scanf()">
	<form action='javascript:requestSelected()'
		method="post">
		<p>
			<small><b>Артикулы фильтр:<%=session.getAttribute("articlefilter") %></b></small>

		</p>
		<div id="articlelist"
			style="width: 110px; overflow-x: none; overflow-y: none; height: 60px; border: 0px;">
			<select id="Select" size="10" class="select">
				<%
			    	List<WmArticle> articleList = new ArticleController().getArticleListByArtCodeFilter(String.valueOf(session.getAttribute("articlefilter")));
	        	    Iterator<WmArticle> iterator = articleList.iterator();

					while (iterator.hasNext()) {
						WmArticle article = (WmArticle) iterator.next();
		        		//System.out.println(article.getArticleId()+" : "+article.getArticleCode()+" : "+article.getDescription()+" : "+article.getArticleKitId());
				%>
				<option value='<%=article.getArticleId() %>' class="option"><%=article.getDescription()%></option>
				<%} %>
			</select>
		</div>


		<div style="overflow: hidden">
			<input
				style="display: inline; float: inherit; height: 25px; width: 40%;"
				type="button" id="btnCancel" name="btnCancel" value="Назад"
				onclick='document.location.href ="<%=session.getAttribute("backpage")%>"'>
				<input style="display: inline; float: inherit; height: 25px; width: 50%;"
				type="submit" id="btnOk" name="btnOk" value="Ок">
		</div>
	</form>
</body>
</html>
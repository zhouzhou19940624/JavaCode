<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>" />
<title>Mystery</title>
<link rel="stylesheet" href="web-static/easy/css/bootstrap.css" />
<link rel="stylesheet" href="web-static/icon/css/font-awesome.min.css" />


</head>
<body class="bg-a">
		<!-- 表单 -->
		<div class="jumbotron">
			<h1 class="display-3">Detils</h1>
			<p class="lead">${content}</p>
			<hr class="my-4">
			<p class="lead">
			</p>
		</div>


		<!-- js -->
		<script src="web-static/easy/js/jquery.js"></script>
		<script src="web-static/layer/layer.js"></script>
</body>
</html>
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
	<div class="container-fluid">
		<!-- 空行 -->
		<div class="row mt-5"></div>
		<div class="row mt-5"></div>
		<div class="row mt-5"></div>
		<!-- 提示 -->
		<div class="row mt-5">
			<div class="col-sm-4 offset-sm-4">
				<div class="card">
					<div class="card-header text-center bg-primary text-white">
						<i class="fa fa-telegram"></i>&nbsp;&nbsp;操 作 成 功
					</div>
					<div class="card-body">
						<div>- ojbk---继续工作吧</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
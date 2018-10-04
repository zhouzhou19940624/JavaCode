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
<link rel="stylesheet" href="web-static/easy/css/boot.css" />
<link rel="stylesheet" href="web-static/icon/css/font-awesome.min.css" />
</head>
<body class="bg-a">
	<div class="container-fluid">
		<!-- 空行 -->
		<div class="row mt-5"></div>
		<div class="row mt-5"></div>
		<div class="row mt-5"></div>
		<div class="row mt-5"></div>
		<div class="row mt-5"></div>
		<div class="row mt-5"></div>
		<div class="row mt-5"></div>
		<!-- 提示 -->
		<div class="row mt-5">
			<div class="col-sm-4 offset-sm-4">
				<div class="card">
					<div class="card-header text-center bg-secondary text-white">
						<i class="fa fa-cog fa-spin fa-x fa-fw"></i>&nbsp;&nbsp;操 作 失 败
					</div>
					<div class="card-body">
						<div>- 提交信息不正确</div>
						<div>- 未登录 权限不足</div>
						<div>- 占用 冲突 其他因素</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
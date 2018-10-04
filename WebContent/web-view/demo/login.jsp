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
		<div class="row mt-5"></div>
		<div class="row mt-5"></div>
		<div class="row mt-5"></div>
		<div class="row mt-5"></div>
		<div class="row mt-5"></div>
		<!-- 表单 -->
		<div class="row mt-5">
			<div class="col-sm-4 offset-sm-4">
				<form class="card" action="mystery/SysUser/login" method="POST">
					<div class="card-header text-center bg-primary text-white">
						&nbsp;&nbsp;用 户 登 录</div>
					<div class="card-body">
						<div>用户名</div>
						<input type="text" name="account" class="form-control" />
						<div>密码</div>
						<input type="password" name="password" class="form-control" />
						<div class="mt-3">
							<div
								style="text-align: center; width: 100%; height: 100%; margin: 0px;">
								<button class="btn btn-sm btn-outline-info" type="submit">登录</button>
								<button class="btn btn-sm btn-outline-primary ml-2"
									type="button" onclick="register();">注册</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- js -->
	<script src="web-static/easy/js/jquery.js"></script>
	<script src="web-static/layer/layer.js"></script>

	<script>
		function register() {
			window.location.href = "home/register";
		};
	</script>
</body>
</html>
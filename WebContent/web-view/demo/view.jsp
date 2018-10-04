<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" href="web-static/menu/metisMenu.css" />
<link rel="stylesheet" href="web-static/menu/vertical.css" />
</head>
<body class="bg-a">
	<div class="container-fluid h-100">
		<!-- 空行 -->
		<div class="row"></div>
		<!-- 标题栏 -->
		<div class="row mt-1 bg-light shadow">
			<div class="col"></div>
			<div class="col">
				<h6 class="text-center text-secondary font-weight-bold">
					&nbsp;&nbsp;<i class="fa fa-h-square"></i>
				</h6>
			</div>
			<div class="col text-right">
				<i class="fa fa-user-o"></i>&nbsp;
				<c:if test="${not empty sessionScope.user}">
					<span>${sessionScope.user.account}</span>&nbsp;<a
						href="mystery/SysUser/logout">注销</a>
				</c:if>
			</div>
		</div>


		<!-- 主区域 -->
		<div class="row mt-1" style="height: 88%;">
			<!-- 导航 -->
			<div class="col-sm-2">
				<div class="sidebar-nav">
					<ul class="metismenu" id="menu">
						<li><a href="javascript:" class="has-arrow">站内信</a>
							<ul>
								<li><a href="javascript:" class="has-arrow" view="receiver"
									onclick="openWork(this);">收件箱</a></li>
								<li><a href="javascript:" class="has-arrow" view="sender"
									onclick="openWork(this);">发件箱</a></li>
							</ul></li>
						<li><a href="javascript:" class="has-arrow">网盘</a>
							<ul>
								<li><a href="javascript:" class="has-arrow" view="upload"
									onclick="openWork(this);">我的网盘</a></li>
								<!-- 								<li><a href="javascript:" class="has-arrow" view="sender"
									onclick="openWork(this);">下载</a></li> -->
							</ul></li>
						<li class="mt-5"></li>
						<li class="mt-5"></li>
						<li class="mt-5"></li>
						<li class="mt-5"></li>
						<li class="mt-5"></li>
						<li class="mt-5"></li>
						<li class="mt-5"></li>
						<li class="mt-5"></li>
						<li class="mt-5"></li>
						<li class="mt-5"></li>
						<li class="mt-5"></li>
						<li class="mt-5"></li>
						<li class="mt-5"></li>
					</ul>
				</div>
			</div>
			<!-- 工作区 -->
			<div class="col-sm-10" id="work">
				<iframe src="home/openWork/welcome" frameborder=0 width=100%
					height=100%></iframe>
			</div>
		</div>
	</div>
	<!-- js -->
	<script src="web-static/easy/js/jquery.js"></script>
	<script src="web-static/menu/metisMenu.js"></script>
	<script>
		//导航菜单
		$("#menu").metisMenu();

		//打开子页
		function openWork(obj) {
			var iframe = "<iframe src='home/openWork/" + $(obj).attr("view")
					+ "' frameborder=0 width=100% height=100%></iframe>";
			$("#work").html(iframe);
		}
	</script>
</body>
</html>
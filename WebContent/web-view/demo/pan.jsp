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
	<!-- 转发表单 -->
	<div class="jumbotron">
		<div>发送人:</div>
		<div>
			<h1 id="account">${account}</h1>
		</div>
		<div>标题:</div>
		<div>
			<input type="text" id="title" class="form-control"
				placeholder="__请输入标题" />
		</div>
		<div class="mt-2">内容:</div>
		<textarea class="form-control" id="content" rows="3"
			placeholder="__请输入内容"></textarea>
		<button class="btn btn-outline-primary mt-2" onclick="replyMsg();">
			<i class="fa fa-paper-plane-o"></i>
		</button>
		
	</div>
	<!-- js -->
	<script src="web-static/easy/js/jquery.js"></script>
	<script src="web-static/layer/layer.js"></script>
	<script>
		function replyMsg() {
			$.post("mystery/MsgInfo/insertMsg", {
				receiver : $("#account").html(),
				title : $("#title").val(),
				msg : $("#content").val()
			}, function(data) {
				if (data == "error")
					layer.msg("数据为空 发送失败");
				if (data == "success") {
					layer.msg("发送成功");
					$("#content").val('');
					$("#title").val('');
				}
			});
		}
	</script>
</body>
</html>
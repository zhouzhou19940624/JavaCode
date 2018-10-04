<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>" />
<title>Mystery</title>
<link rel="stylesheet" href="web-static/easy/css/boot.css" />
<link rel="stylesheet" href="web-static/icon/css/font-awesome.min.css" />

<style>
.top {
	border: 1px solid #ccc;
}

.text {
	border: 1px solid #ccc;
	height: 100px;
}
</style>
</head>
<body class="bg-b">
	<div class="container-fluid">
		<div class="row">
			<div class="col text-center bg-primary text-white">发 件 箱</div>
		</div>
	</div>
	<div class=" mt-5"></div>
	<!-- 添加好友 -->
	<div class="form-inline">
		<input type="text" style="width: 200px" id="friend"
			onblur="checkuser();" class="form-control" placeholder="_请输入账户" />
		<button class="btn btn-outline-primary ml-2" id="btn"
			onclick="addFriend();">
			<i class="fa fa-plus fa-pulse"></i>
		</button>
	</div>
	<div class=" mt-3"></div>
	<!-- 发送人 -->
	<div class="form-inline">
		<select style="width: 200px" class="form-control" id="select">
			<option value="伟哥牛逼">====only friend====</option>
		</select>
		<button class="btn btn-outline-primary ml-2" onclick="deleteFriend();">
			<i class="fa fa-minus"></i>
		</button>
	</div>
	<!-- 标题	 -->
	<input class="form-control mt-3" type="text" id="title"
		style="width: 250px" placeholder="__请输入标题" />

	<!-- 富文本div -->
	<div id="div1" class="top form-control mt-3"></div>
	<div id="div2" class="text form-control"></div>
	<button onclick="sendMsg();" class="btn btn-outline-primary mt-3">提交</button>

	<!-- js导包 -->
	<script src="web-static/easy/js/jquery.js"></script>
	<script src="web-static/page/jqpaginator.js"></script>
	<script src="web-static/layer/layer.js"></script>
	<script src="web-static/edit/wangEditor.js"></script>
	<!-- 删除好友 -->
	<script>
		function deleteFriend() {
			$.post("mystery/SysUser/deleteFriend", {
				friend_account : $("#select").val()
			}, function(data) {
				if (data == "success")
					alert("删除成功");
				window.location.reload();
				if (data == "error")
					layer.msg("删除失败");
			});
		}
	</script>

	<!-- 添加好友 -->
	<script>
		function addFriend() {
			$.post("mystery/SysUser/addFriend", {
				friend : $("#friend").val()
			}, function(data) {
				if (data == "success") {
					alert("添加成功");
					 window.location.reload();
				}
				if (data == "error")
					layer.msg("添加失败");
			});
		}
	</script>
	<!-- 检查好友 -->
	<script>
		function checkuser() {
			$.post("mystery/SysUser/checkuser", {
				account : $("#friend").val()
			}, function(data) {
				/* 如果存在这个账户的话,则进行好友验证 */
				if (data == "repeated") {
					$.post("mystery/SysUser/checkFriend", {
						friend : $("#friend").val()
					}, function(data) {
						if (data == "success")
							layer.msg("可以添加好友");
						if (data == "error") {
							layer.msg("你已经添加过" + $("#friend").val() + "了");
							$("#friend").val("");
						}
					});
				}
				if (data == "ojbk") {
					layer.msg("此id没有注册,请重新输入");
					$("#friend").val("");
				}
				if (data == "error")
					layer.msg("空的,请重新输入");
			});
		}
	</script>
	<!-- 生成富文本编辑器 -->
	<script>
		var E = window.wangEditor
		var editor = new E('#div1', '#div2')
		editor.create()
	</script>
	<!-- 发送信息 -->
	<script>
		function sendMsg() {
			$.post("mystery/MsgInfo/insertMsg", {
				msg : editor.txt.html(),
				receiver : $("#select").val(),
				title : $("#title").val()
			}, function(data) {
				if (data == "error")
					layer.msg("空 发送失败");
				if (data == "success") {
					layer.msg("发送成功");
					editor.txt.html('');
					$("#receiver").val('');
					$("#title").val('');
				}
			});
		}
	</script>
	<script>
		show();
		function show() {
			$.post("mystery/SysUser/selectFriends", {}, function(data) {
				if (data == null)
					layer.msg("去加个好友吧,你没有好友");
				var list = $.parseJSON(data);
				$.each(list, function(i, n) {
					$("#select").append(
							"<option value="+n+">" + n + "</option>");
				});
			});
		}
	</script>

</body>
</html>
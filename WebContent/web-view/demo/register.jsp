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
<script
	src="https://cdn.bootcss.com/jquery-validate/1.17.0/jquery.validate.min.js"></script>
</head>
<body class="bg-a">
	<div class="container-fluid">
		<!-- 空行 -->
		<div class="row"></div>
		<!-- 表单 -->
		<div class="row mt-5">
			<div class="col-sm-4 offset-sm-4">
				<form class="card" action="mystery/SysUser/add" method="POST"
					onsubmit="return check(this)" id="fm">
					<div class="card-header text-center bg-primary text-white">
						<i class="fa fa-cog fa-spin fa-x fa-fw"></i>&nbsp;&nbsp;用 户 注 册
					</div>
					<div class="card-body">

						<div id="feedback">账户</div>
						<div class="form-group has-success">
							<input type="text" class="form-control" name="account"
								onblur="checkuser();" id="a" class="form-control" minlength="5" required
								placeholder="___请输入账户" />
						</div>

						<div>密码</div>
						<input type="password" name="password" onblur="checkpsw();" id="p"
							class="form-control" placeholder="___请输入密码" required/>
						<div id="nicknametop">昵称</div>
						<input type="text" name="nickname" class="form-control"
							id="nickname" placeholder="___请输入昵称" onblur="checknickname();" required />
						<div>真实姓名</div>
						<input type="text" name="realname" class="form-control"
							placeholder="___请输入姓名" required/>
						<div>手机号码</div>
						<input type="text" name="mobile" class="form-control" id="m"
							onblur="checkphone();"  placeholder="___请输入手机号" required/>
						<div class="mt-1">
							邮箱
							<button type="button" class="btn btn-sm ml-5 btn-outline-danger"
								onclick="test();" required email="true">验证</button>
						</div>
						<input type="text" name="email" onblur="checkmail();" id="email"
							class="form-control mt-1" placeholder="_请输入真实的邮箱" />
						<div id="codetop">验证码</div>
						<input type="text" name="code" class="form-control" id="code"
							onblur="checkcode();" placeholder="_请输入验证码" />
						<div class="mt-3">
							<div style="text-align: center; width: 100%; height: 100%; margin: 0px;">
								<button class="btn btn-outline-primary btn-sm" type="submit">提交</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- js -->
	<script src="web-static/easy/js/jquery.js"></script>
	<script src="web-static/easy/js/avalon.js"></script>
	<script src="web-static/layer/layer.js"></script>
	<script>
		function test() {
			$.post("mystery/SysUser/test", {
				email : $("#email").val()
			}, function(data) {
				layer.msg(data);
			});
		}

		function checkuser() {
			if ($("#a").val()!="") {
				$.post("mystery/SysUser/checkuser", {
					account : $("#a").val()
				}, function(data) {
					if(data=="repeated"){
					document.getElementById("feedback").innerHTML = "账号重复请重新输入";
					$("#a").val("");
				}else{
				    document.getElementById("feedback").innerHTML = "可以使用";
				}
				});
			}				
		function checkpsw() {
				document.getElementById("feedback").innerHTML = "用户";
			if ($("#p").val() == "") {
				layer.msg("密码不能为空");
			}
		}

		 function checkphone() {
				document.getElementById("nicknametop").innerHTML = "昵称";
		} 

		function checkmail() {
			var reg = /^[0-9A-Za-z]{2,}@[0-9A-Za-z]{2,}\.(com|cn|com\.cn)$/;
			if (reg.test($("#email").val()) == false) {
				layer.msg("邮箱不对");
			}
		}
		
		function checkcode() {
			$.post("mystery/SysUser/checkcode", {
				email : $("#email").val(),
				code : $("#code").val()
			}, function(data) {
				if(data=="wrong"){
				document.getElementById("codetop").innerHTML = "验证码错误请重新输入";
				$("#code").val("");
				}
			});
		}
		
		function checknickname(){
			if ($("#nickname").val()=="") {
				layer.msg("昵称请不要为空");
			}else {
				$.post("mystery/SysUser/checknickname", {
					nickname : $("#nickname").val()
				}, function(data) {
					if(data=="repeated"){
					document.getElementById("nicknametop").innerHTML = "昵称重复请重新输入";
					$("#nickname").val("");
				}else{
				    document.getElementById("nicknametop").innerHTML = "可以使用";
				}
				});
			}
		}
	}
	    $("#fm").validate();
	</script>
</body>
</html>
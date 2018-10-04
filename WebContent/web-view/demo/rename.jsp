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
<link rel="stylesheet" href="web-static/upload/css/uploader.css" />
</head>
<body>
    <div id="myid" style="display:none">${id}</div>
	<div>
		<input id="rname" type="text" class="form-control" style="width: 200px" placeholder="__rename" onblur="checkRename();"/>
		<button class="btn btn-outline-primary" onclick="rename();" style="width: 200px"><i class="fa fa-arrow-circle-o-right fa-1x"></i></button>
	</div>
	<!-- js -->
	<script src="web-static/easy/js/jquery.js"></script>
	<script src="web-static/easy/js/avalon.js"></script>
	<script src="web-static/page/jqpaginator.js"></script>
	<script src="web-static/layer/layer.js"></script>
	<script src="web-static/upload/js/uploader.js"></script>
	<!-- 检查重命名 -->
	<script>
	function checkRename(){
		$.post("mystery/disk/checkRename",{
			rename:$("#rname").val(),
			id : $("#myid").text()
		},function(data){
			if(data=="error"){
				layer.msg("名字重复,请重新填写");
				$("#rname").val("");
			}
		});
	}
	</script>
	<script>
	<!-- 重命名 -->
		function rename(obj) {
	 		$.post("mystery/disk/renameFile", {
	 			rename:$("#rname").val(),
				id : $("#myid").text()
			}, function(data) {
				if(data=="success"){
                 layer.msg("修改成功");
				$("#rname").val('');
				}
			}); 
		}
	</script>
</body>
</html>
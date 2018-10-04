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
	<div class="mt-3"></div>
	<div id="menu">
		<ul class="nav nav-pills">
			<li class="nav-item"><input type="file" name="myFile"
				style="display: none" />
				<button class="btn btn-outline-primary btn-sm ml-3" id="button">
					<i class="fa fa-upload fa-2x">&nbsp;upload</i>
				</button></li>
			<li class="nav-item">
				<button class="btn btn-outline-primary btn-sm ml-2"
					onclick="newFile();">
					<i class="fa fa-file-text-o fa-2x">&nbsp;NewFile</i>
				</button>
			</li>
			<li class="nav-item">
				<button class="btn btn-outline-primary btn-sm ml-2"
					onclick="returnFile()">
					<i class="fa fa-reply fa-2x">&nbsp;return</i>
				</button>
			</li>
			<li class="nav-item">
				<div id="view"></div>
			</li>
		</ul>
	</div>
	<h1 class="page-header"></h1>
	<hr>
	<h1 class="page-header"></h1>

	<!-- 新建文件夹弹出层 -->
	<div class="col" id="newFile" style="display: none">
		<div class="row">
			<input type="text" id="inputfile" style="width: 250px"
				class="form-control" placeholder="__please enter filename">
		</div>
		<div class="row">
			<button class="btn btn-outline-primary" style="width: 250px"
				onclick="createFile();">
				<i class="fa fa-file-text-o fa-2x"></i>
			</button>
		</div>
	</div>

	<!-- 文件内容 -->
	<div id="content" class="row mt-3 h-75" ms-controller="vm">
		<div class="col">
			<div ms-for="data in list">
				<div class="m-2 float-left">
					<div
						style="width: 100px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">
						<span style="font-size: 18px; color: gray;">name:&nbsp;</span>{{data.filename}}
					</div>
					<table class="table table-hover">
						<tr>
							<td class="text-center" ms-if="data.type==0"><a
								href="script:" ms-attr="{url:data.urlA}" onclick="inFile(this);"><i
									class="fa fa-folder-o fa-5x"></i></a></td>
							<td class="text-center" ms-if="data.type==1"><i
								class="fa fa-file-text-o fa-5x"></i></td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td>
								<button class="btn btn-sm  btn-outline-primary"
									ms-attr="{id:data.id}" onclick="rename(this);" title="rename">
									<i class="fa fa-pencil-square-o"></i>
								</button>
								<button class="btn btn-sm  btn-outline-primary  ml-1"
									title="download" ms-attr="{url:data.urlA,type:data.type}"
									onclick="download(this);">
									<i class="fa fa-download"></i>
								</button>
								<button class="btn btn-sm  btn-outline-danger  ml-1"
									title="delete" ms-attr="{type:data.type,url:data.urlA}"
									onclick="deleteFile(this);">
									<i class="fa fa-trash-o"></i>
								</button>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>


</body>
<!-- js -->
<script src="web-static/easy/js/jquery.js"></script>
<script src="web-static/easy/js/avalon.js"></script>
<script src="web-static/layer/layer.js"></script>
<script src="web-static/upload/js/uploader.js"></script>
<script>
	function download(obj) {
		$.post("mystery/disk/download", {
			url : $(obj).attr("url"),
			type:$(obj).attr("type")
		}, function(data) {
			if (data = "success") {
				alert.mag("下载成功");
			}
			if (data == "error") {
				alert.mag("下载失败");
			}
		});
	}
</script>
<!-- 重命名弹窗 -->
<script>
	function rename(obj) {
		reView = layer.open({
			type : 2,
			area : [ "200px", "122px" ],
			content : "home/rename/" + $(obj).attr("id"),
			end : function() {
				layer.close(reView);
				showFileSon();
			}
		});
	}
</script>
<!-- 删除文档 -->
<script>
	function deleteFile(obj) {
		$.post("mystery/disk/deleteFile", {
			type : $(obj).attr("type"),
			url : $(obj).attr("url")
		}, function(data) {
			if (data == "success") {
				layer.msg("删除成功");
				showFile();
			} else {
				layer.msg("删除失败");
			}
		});
	}
</script>
<!-- 返回按钮 -->
<script>
	function returnFile(obj) {
		$.post("mystery/disk/returnFile", {
			url : $(obj).attr("url"),
		}, function(data) {
			vm.list = $.parseJSON(data);
		});
	}
</script>
<!-- 进入文件夹 -->
<script>
	function inFile(obj) {
		$.post("mystery/disk/input", {
			url : $(obj).attr("url"),
		}, function(data) {
			vm.list = $.parseJSON(data);
		});
	}
</script>
<!--  展示文件夹  -->
<script>
	var vm = avalon.define({
		$id : "vm",
		list : []
	});

	showFile();
	function showFile() {
		$.post("mystery/disk/showFile", {

		}, function(data) {
			vm.list = $.parseJSON(data);
		});
	}
</script>
<!-- 创建文件夹 -->
<script>
	function createFile() {
		$.post("mystery/disk/createFile", {
			filename : $("#inputfile").val()
		}, function(data) {
			if (data == "success") {
				layer.msg("创建文件夹成功");
				layer.close(view);
				$("#inputfile").val("");
				showFileSon();
			} else {
				layer.msg("文件重复无法创建");
				layer.close(view);
				$("#inputfile").val("");
				showFileSon();
			}
		});

	}
</script>

<!-- 展示子路径 -->
<script>
	function showFileSon() {
		$.post("mystery/disk/showFileSon", {}, function(data) {
			vm.list = $.parseJSON(data);
		});
	}
</script>
<!-- 弹出新建文件夹的layer框 -->
<script>
	var view = "";
	function newFile() {
		view = layer.open({
			type : 1,
			content : $("#newFile")
		});
	}
</script>
<!-- 上传 -->
<script>
	var upload = new Q.Uploader({
		url : "mystery/disk/upload",
		target : $("#button")[0], //上传按钮,可为数组 eg:[element1,element2]
		view : $("#view")[0], //上传任务视图(若自己实现UI接口，则无需指定此参数)
		upName : "myFile",
		dataType : "text",
		on : {
			complete : function(task) {
				if (task.response == "success") {
					layer.msg("上傳成功");
					showFileSon();
					setTimeout('showPan()', 4000);
				} else
					layer.msg("上传失敗");
			}
		}
	});

	function showPan() {
		$("#view").empty();
	}
</script>

<!-- 固定菜单栏 -->
<!-- <script>
	$("#menu").metisMenu();
</script> -->
</body>
</html>
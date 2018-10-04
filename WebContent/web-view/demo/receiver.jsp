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
<body class="bg-b">
	<div class="container-fluid" ms-controller="vm">
		<!-- 标题 -->
		<div class="row">
			<div class="col text-center bg-primary text-white">
				<i class="fa fa-database"></i>&nbsp;&nbsp;姚 瑜 伽 牛 逼
			</div>
		</div>

		<!-- 数据区 -->
		<div class="row mt-3">
			<div class="col">
				<div>
					<table class="table table-striped">
						<tr>
							<td>发件人</td>
							<td>标题</td>
							<td>发件时间</td>
							<td>状态</td>
							<td>change</td>
							<td>读信</td>
							<td>回信</td>
							<td>删除</td>
						</tr>
						<tr ms-for="s in list">
							<td>{{s.senderaccount}}</td>
							<td>{{s.msgtitle}}</a></td>
							<td>{{s.createtime|date("yyyy/MM/dd HH:mm:ss")}}</td>
							<td style="display: none" id="msgid">{{s.msgid}}</td>

							<td ms-if="s.readflag==0">未读</td>
							<td ms-if="s.readflag==1">已读</td>

							<td>
								<button class="btn btn-outline-primary"
									ms-attr="{msgid:s.msgid}" onclick="change(this);">
									<i class="fa fa-exchange"></i>
								</button>
							</td>

							<!-- 读取信息 -->
							<td ms-if="s.readflag==0">
								<button class="btn btn-outline-success"
									onclick="showDetils(this);" ms-attr="{msgid:s.msgid}">
									<i class="fa fa-envelope-o"></i>
								</button>
							</td>
							<td ms-if="s.readflag==1">
								<button class="btn btn-outline-success"
									onclick="showDetils(this);" ms-attr="{msgid:s.msgid}">
									<i class="fa fa-envelope-open-o"></i>
								</button>
							</td>


							<td><button class="btn btn-outline-primary"
									onclick="replyMsg(this);"
									ms-attr="{senderaccount:s.senderaccount}">
									<i class="fa fa-reply"></i>
								</button></td>
							<td><button class="btn btn-outline-danger"
									ms-attr="{msgid:s.msgid}" onclick="deleteMsg(this);">
									<i class="fa fa-trash-o"></i>
								</button></td>
						</tr>
					</table>
				</div>
				<!-- <!-- 分页条 -->
				<div class="mt-1">
					<ul class="pagination" id="pager"></ul>
				</div>
			</div>
		</div>
	</div>


	<!-- 分页条 -->
	<div class="mt-1">
		<ul class="pagination" id="pager"></ul>
	</div>

	-->
	<!-- js -->
	<script src="web-static/easy/js/jquery.js"></script>
	<script src="web-static/easy/js/avalon.js"></script>
	<script src="web-static/page/jqpaginator.js"></script>
	<script src="web-static/layer/layer.js"></script>

	<!-- 改变状态 -->
	<script>
		function change(obj) {
			$.post("mystery/MsgComm/changeStatus", {
				msgid : $(obj).attr("msgid")
			}, function(data) {

				readMsg();
			});
		}
	</script>
	<!-- 删除消息 -->
	<script>
		function deleteMsg(obj) {
			$.post("mystery/MsgInfo/deleteMsgDetailsById", {
				msgid : $(obj).attr("msgid")
			}, function(data) {
				if (data == "ok")
					layer.msg("删除成功");
				readMsg();
			});
		}
	</script>
	<!-- 抓取回复视图 -->
	<script>
		function replyMsg(obj) {
			layer.open({
				type : 2,
				area : [ "500px", "400px" ], 
				content : "home/reply/" + $(obj).attr("senderaccount"),
				end : function() {
					readMsg();
				}
			});
		}
	</script>
	<!-- 抓取content视图 -->
	<script>
		function showDetils(obj) {
			layer.open({
				type : 2,
				area : [ "400px", "350px" ],
				content : "mystery/MsgInfo/selectMsgDetails/"
						+ $(obj).attr("msgid"),
				end : function() {
					readMsg();
				}

			});
		}

		//vm
		var vm = avalon.define({
			$id : "vm",
			list : [],
			pageNum : 1
		});

		readMsg();

		//获取Msg
		function readMsg() {
			$.post("mystery/MsgComm/queryMsg", {
			 pageNum : vm.pageNum,
			 pageSize : 3 
			}, function(data) {
				var res = $.parseJSON(data);
				vm.list = res.list;
				$("#pager").jqPaginator("option", {
					totalPages : res.pagecount
				}); 
			});
		}

			//分页条
			$("#pager").jqPaginator({
				totalPages : 1,
				currentPage : vm.pageNum,
				onPageChange : function(n) {
					vm.pageNum = n;
					readMsg();
				}
			}); 
	</script>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="../js/html5shiv.js"></script>
<script type="text/javascript" src="../js/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="../static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="../static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="../js/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="../static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="../static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="../js/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>作息时间管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>
	基础数据管理
	<span class="c-gray en">&gt;</span>
	作息时间管理
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
		<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
		<a class="btn btn-primary radius" onclick="system_worktime_operation('添加作息时间','system-worktime-add.html')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加作息时间</a>
		</span>
	</div>
	<div class="mt-20">
		<table class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th width="25"><input type="checkbox" name="" value=""></th>
					<th width="50">序号</th>
					<th>上班时间</th>
					<th>下班时间</th>
					<th>班次名称</th>
					<th>状态</th>
					<th width="100">操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var ="worktime" items="${worktimeList}" varStatus="tag">
				<tr class="text-c">
					<td><input type="checkbox" <c:if test="${worktime.status == 1}">disabled="disabled"</c:if> name="ids" id="" value="${worktime.worktimeid}"></td>
					<td>${tag.count}</td>
					<td><fmt:formatDate value="${worktime.starttime}" pattern="HH:mm"/></td>
					<td><fmt:formatDate value="${worktime.endtime}" pattern="HH:mm"/></td>
					<td>${worktime.illustrate}</td>
					<td>${worktime.status == 0 ? "停用" : "启用"}</td>
					<td class="f-14">
					    <a title="编辑" href="javascript:;" onclick="system_worktime_operation('作息时间编辑','system-worktime-edit.html?id='+${worktime.worktimeid})" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
						<c:if test="${worktime.status == 0}">
							<a title="启用" href="javascript:;" onclick="system_worktime_setUp(this, '${worktime.worktimeid}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe615;</i></a>
							<a title="删除" href="javascript:;" onclick="system_worktime_del(this, '${worktime.worktimeid}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="../js/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="../js/layer/2.4/layer.js"></script>
<script type="text/javascript" src="../static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="../static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="../js/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../js/laypage/1.2/laypage.js"></script>
<script type="text/javascript">
$('.table-sort').dataTable({
	"aaSorting": [[ 1, "asc" ]],//默认第几个排序
	"bStateSave": false,//状态保存
	"aoColumnDefs": [
	  {"orderable":false,"aTargets":[0,2,3,4,5]}// 制定列不参与排序
	]
});
/*系统-栏目-操作*/
function system_worktime_operation(title,url,w,h){
	layer_show(title,url,w,300);
}
/*系统-栏目-启用*/
function system_worktime_setUp(obj,id){
	layer.confirm('确认要启用该作息时间吗？',function(index){
		$.ajax({
			type: 'GET',
			url: 'system-worktime-status-operation',
			data: {id:id},
			dataType: "json",
			success: function(data){
				if(data.head){
					layer.msg('已启用该作息时间!',{icon:1,time:2000},function(){
						 window.location.reload();
					});
				} else {
					if(data.body == 'UNLOGIN'){
						layer.msg("请先登录!",{icon:1,time:1000},function(){
							window.parent.location.href = "../login.html";
						});								
					} else if(data.body == 'PERMISSION_DENIED') {
						layer.msg("您无操作权限!",{icon:2,time:2000});
					} else {
						layer.msg(data.body,{icon:2,time:2000});
					}
				}
			},
			error: function(XmlHttpRequest, textStatus, errorThrown){
				layer.msg('系统错误!'+XMLHttpRequest.status+XMLHttpRequest.readyState,{icon:1,time:6000});
			},
		});
	});
}
/*系统-栏目-删除*/
function system_worktime_del(obj,id){
	layer.confirm('确认要删除该作息时间吗？',function(index){
		$.ajax({
			type: 'GET',
			url: 'system-worktime-del',
			data: {id:id},
			dataType: "json",
			success: function(data){
				if(data.head){
					layer.msg('该作息时间已删除!',{icon:1,time:2000},function(){
						 window.location.reload();
					});				
				} else {
					if(data.body == 'UNLOGIN'){
						layer.msg("请先登录!",{icon:1,time:1000},function(){
							window.parent.location.href = "../login.html";
						});								
					} else if(data.body == 'PERMISSION_DENIED') {
						layer.msg("您无操作权限!",{icon:2,time:2000});
					} else {
						layer.msg(data.body,{icon:2,time:2000});
					}
				}
			},
			error: function(XmlHttpRequest, textStatus, errorThrown){
				layer.msg('系统错误!'+XMLHttpRequest.status+XMLHttpRequest.readyState,{icon:1,time:6000});
			},
		});
	});
}
/*系统-栏目-批量删除*/
function datadel(){
	var ids = $("input[name='ids']:checked").serialize();
	if(ids == '') {
		layer.msg('请选择要删除的作息时间!',{icon:2,time:1000});
		return false;
	}	
	layer.confirm('确认要删除选中的作息时间吗？',function(index){		
		$.ajax({
			type: 'POST',
			url: 'system-worktime-del',
			data: ids,
			dataType: 'json',
			success: function(data){
				if(data.head){
					layer.msg('作息时间已删除!',{icon:1,time:2000},function(){
						 window.location.reload();
					});
				} else {
					if(data.body == 'UNLOGIN'){
						layer.msg("请先登录!",{icon:1,time:1000},function(){
							window.parent.location.href = "../login.html";
						});								
					} else if(data.body == 'PERMISSION_DENIED') {
						layer.msg("您无操作权限!",{icon:2,time:2000});
					} else {
						layer.msg(data.body,{icon:2,time:2000});
					}
				}
			},
			error: function(XmlHttpRequest, textStatus, errorThrown){
				layer.msg('系统错误!'+XMLHttpRequest.status+XMLHttpRequest.readyState,{icon:1,time:6000});
			},
		});
	});
}
</script>
</body>
</html>
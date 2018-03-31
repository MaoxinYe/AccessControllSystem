<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>${area}管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>
	基础数据管理
	<span class="c-gray en">&gt;</span>
	${area}管理
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
		<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
		<a class="btn btn-primary radius" onclick="system_area_operation('添加','system-area-add.html?flag=${flag}')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加${area}</a>
		</span>
	</div>
	<div class="mt-20">
		<table class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th width="25"><input type="checkbox" name="" value=""></th>
					<th width="50">序号</th>
					<c:if test="${flag == '1'}">
						<th>${area_1}</th>
					</c:if>
					<c:if test="${flag == '2'}">
						<th>${area_1}</th>
						<th>${area_2}</th>
					</c:if>
					<c:if test="${flag == '3'}">
						<th>${area_1}</th>
						<th>${area_2}</th>
						<th>${area_3}</th>
					</c:if>
					<th width="100">操作</th>
				</tr>
			</thead>
			<tbody>
			<c:if test="${flag == '1'}">
			<c:forEach var ="area" items="${area1List}" varStatus="tag">
				<tr class="text-c">
					<td><input type="checkbox" name="ids" id="" value="${area.areacode}"></td>
					<td>${tag.count}</td>
					<td>${area.areaname}</td>
					<td class="f-14">
						<a title="编辑" href="javascript:;" onclick="system_area_operation('编辑','system-area-edit.html?flag=${flag}&areacode=${area.areacode}')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
						<a title="删除" href="javascript:;" onclick="system_area_del(this, '${area.areacode}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
					</td>
				</tr>
			</c:forEach>																								
			</c:if>
			<c:if test="${flag == '2'}">
			<c:forEach var ="area" items="${area1List}">
			<c:forEach var ="area2" items="${area2List}" varStatus="tag">
			<c:if test="${area.areacode == area2.supercode}">
				<tr class="text-c">
					<td><input type="checkbox" name="ids" id="" value="${area2.areacode}"></td>
					<td>${tag.count}</td>
					<td>${area.areaname}</td>
					<td>${area2.areaname}</td>
					<td class="f-14">
						<a title="编辑" href="javascript:;" onclick="system_area_operation('编辑','system-area-edit.html?flag=${flag}&areacode=${area2.areacode}')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
						<a title="删除" href="javascript:;" onclick="system_area_del(this, '${area.areacode}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
					</td>
				</tr>
			</c:if>
			</c:forEach>
			</c:forEach>																								
			</c:if>
			<c:if test="${flag == '3'}">
			<c:forEach var ="area" items="${area1List}">
			<c:forEach var ="area2" items="${area2List}">
			<c:if test="${area.areacode == area2.supercode}">
			<c:forEach var ="area3" items="${area3List}"  varStatus="tag">
			<c:if test="${area3.supercode == area2.areacode}">
				<tr class="text-c">
					<td><input type="checkbox" name="ids" id="" value="${area3.areacode}"></td>
					<td>${tag.count}</td>
					<td>${area.areaname}</td>
					<td>${area2.areaname}</td>
					<td>${area3.areaname}</td>
					<td class="f-14">
					    <a title="编辑" href="javascript:;" onclick="system_area_operation('编辑','system-area-edit.html?flag=${flag}&areacode=${area3.areacode}')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
						<a title="删除" href="javascript:;" onclick="system_area_del(this, '${area.areacode}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
					</td>
				</tr>
			</c:if>
			</c:forEach>
			</c:if>
			</c:forEach>
			</c:forEach>																								
			</c:if>
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
	  {"orderable":false,"aTargets":[0]}// 制定列不参与排序
	]
});
/*系统-栏目-操作*/
function system_area_operation(title,url,w,h){
	layer_show(title,url,w,300);
}
/*系统-栏目-删除*/
function system_area_del(obj,areacode){
	layer.confirm('确认要删除该${area}信息吗？',function(index){
		$.ajax({
			type: 'GET',
			url: 'system-area-del',
			data: {areacode:areacode},
			dataType: "json",
			success: function(data){
				if(data.head){
					layer.msg('该${area}信息已删除!',{icon:1,time:2000},function(){
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
		layer.msg('请选择要删除的${area}信息!',{icon:2,time:1000});
		return false;
	}	
	layer.confirm('确认要删除选中的${area}信息吗？',function(index){		
		$.ajax({
			type: 'POST',
			url: 'system-area-del',
			data: ids,
			dataType: 'json',
			success: function(data){
				if(data.head){
					layer.msg('${area}信息已删除!',{icon:1,time:2000},function(){
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
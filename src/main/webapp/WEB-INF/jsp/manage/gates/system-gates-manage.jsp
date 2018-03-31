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
<script type="text/javascript" src="../js/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="../js/datatables/1.10.0/jquery.dataTables.min.js"></script>
<!--[if IE 6]>
<script type="text/javascript" src="../js/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>门禁闸机管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>
	门禁闸机列表
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container">
    
    <form id="gatesManage" action="system-gates-manage.html" method="post">
    <div class="text-c" style="text-align:left">
        所属${area}：
        <span class="select-box inline">
        <select class="select" id="areacode" name="areacode"  style="width:160px;">
			<option value="">-请选择-</option>
			<c:forEach var="area" items="${areaList}">
				<option value="${area.areacode}" <c:if test="${conditions.areacode==area.areacode}">selected="selected"</c:if> >${area.areaname}</option>
			</c:forEach>
		</select>
		</span>
		&nbsp;&nbsp;作用：
        <span class="select-box inline">
        <select class="select" id="inorout" name="inorout"  style="width:160px;">
			<option value="">-请选择-</option>
			<option value="1" <c:if test="${conditions.inorout == 1}">selected="selected"</c:if> >进</option>
			<option value="2" <c:if test="${conditions.inorout == 2}">selected="selected"</c:if> >出</option>
		</select>
		</span>
       <button type="submit" class="btn btn-success" id="serach" name="serach"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
    </div>
    </form>

    <form name="searchresult" id="searchresult" action="" method="post">
    <input type="hidden" id="areacode" name="areacode" value="${conditions.areacode}">
	<input type="hidden" id="inorout" name="inorout" value="${conditions.inorout}">
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
		<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
		<a class="btn btn-primary radius" onclick="system_gates_operation('添加门禁闸机','system-gates-add.html')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加门禁闸机</a>
		</span> <span class="r">共有数据：<strong>${pageMap.page.totalRows}</strong> 条</span>
	</div>
	<div class="mt-20">
	<div class="dataTables_wrapper no-footer" id="DataTables_Table_0_wrapper">
		<table class="table table-border table-bordered table-hover table-bg ">
			<thead>
				<tr class="text-c">
					<th width="25"><input type="checkbox" name="" value=""></th>
					<th width="50">序号</th>
					<th>硬件代码</th>
					<th>所属${area}</th>
					<th>硬件说明</th>
					<th>终端类型</th>
					<th>作用</th>
					<th>位置信息</th>
					<th>添加时间</th>
					<th width="100">操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var ="gates" items="${pageMap.info}" varStatus="tag">
				<tr class="text-c">
					<td><input type="checkbox" name="ids" id="" value="${gates.gatesid}"></td>
					<td>${(pageMap.page.currentPage-1)*(pageMap.page.pageSize)+tag.count}</td>
					<td>${gates.uniqueid}</td>
					<td>${gates.area.areaname}</td>
					<td>${gates.gatesname}</td>
					<td>${gates.type == 1 ? "门禁" : "闸机" }</td>
					<td>${gates.inorout == 1 ? "进" : "出"}</td>
					<td>${gates.address}</td>
					<td><fmt:formatDate value="${gates.addtime}" pattern="yyyy-MM-dd"/></td>
					<td class="f-14"><a title="编辑" href="javascript:;" onclick="system_gates_operation('门禁闸机修改','system-gates-edit.html?id='+${gates.gatesid})" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
						<a title="删除" href="javascript:;" onclick="gates_manage_del(this, ${gates.gatesid})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
					</td>
				</tr>
			</c:forEach>																								
			</tbody>
		</table>
		<%@include file="../inc/page.jsp"%>
        <input type="hidden" name="pageUrl" value="system-gates-manage.html" />
	</div>
	</div>
	</form>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="../js/layer/2.4/layer.js"></script>
<script type="text/javascript" src="../static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="../static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="../js/laypage/1.2/laypage.js"></script>
<script type="text/javascript">
$('.table-sort').dataTable({
	"aaSorting": [[ 1, "asc" ]],//默认第几个排序
	"bStateSave": false,//状态保存
	"iDisplayLength": 50,
	"aoColumnDefs": [
	  {"orderable":false,"aTargets":[0,2,3,4,5,6,7,8]}// 制定列不参与排序
	]
});
/*系统-栏目-操作*/
function system_gates_operation(title,url,w,h){
	layer_show(title,url,w,500);
}
/*系统-栏目-删除*/
function gates_manage_del(obj,id){
	layer.confirm('确认要删除该门禁闸机吗？',function(index){
		$.ajax({
			type: 'GET',
			url: 'system-gates-del',
			data: {id:id},
			dataType: 'json',
			cache: false,
			success: function(data){
				if(data.head){
					layer.msg('门禁闸机已删除!',{icon:1,time:2000},function(){
						query();
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
		layer.msg('请选择要删除的门禁闸机!',{icon:2,time:1000});
		return false;
	}	
	layer.confirm('确定要删除选定的门禁闸机吗？',function(index){		
		$.ajax({
			type: 'POST',
			url: 'system-gates-del',
			data: ids,
			dataType: 'json',
			success: function(data){
				if(data.head){
					layer.msg('门禁闸机删除成功!',{icon:1,time:2000},function(){
						query();
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
function query() {
    $("#searchresult").submit();
}
</script>
</body>
</html>
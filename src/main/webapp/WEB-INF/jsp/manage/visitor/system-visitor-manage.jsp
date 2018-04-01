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
<title>访客信息</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>
	访客列表
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container">
    
    <form id="visitorManage" action="system-visitor-manage.html" method="post">
    <div class="text-c" style="text-align:left">
        访客姓名：
        <input type="text" class="input-text" style="width:160px" placeholder="输入访客姓名" id="name" name="name" value="${conditions.name}">
        访客电话：
        <input type="text" class="input-text" style="width:160px" placeholder="输入访客电话" id="tel" name="tel" value="${conditions.tel}">
       	受访人员姓名：
        <input type="text" class="input-text" style="width:160px" placeholder="输入受访人员姓名" id="personnel_name" name="personnel_name" value="${conditions.personnel_name}">
        受访人员电话：
        <input type="text" class="input-text" style="width:160px" placeholder="输入受访人员电话" id="personnel_tel" name="personnel_tel" value="${conditions.personnel_tel}">
       <button type="submit" class="btn btn-success" id="serach" name="serach"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
    </div>
    </form>

    <form name="searchresult" id="searchresult" action="" method="post">
    <input type="hidden" id="name" name="name" value="${conditions.name}">
	<input type="hidden" id="tel" name="tel" value="${conditions.tel}">
	<input type="hidden" id="personnel_name" name="personnel_name" value="${conditions.personnel_name}">
	<input type="hidden" id="personnel_tel" name="personnel_tel" value="${conditions.personnel_tel}">
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
		<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
		<a class="btn btn-primary radius" onclick="system_visitor_operation('添加访客','system-visitor-add.html')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加访客</a>
		</span> <span class="r">共有数据：<strong>${pageMap.page.totalRows}</strong> 条</span>
	</div>
	<div class="mt-20">
	<div class="dataTables_wrapper no-footer" id="DataTables_Table_0_wrapper">
		<table class="table table-border table-bordered table-hover table-bg ">
			<thead>
				<tr class="text-c">
					<th width="25"><input type="checkbox" name="" value=""></th>
					<th width="50">序号</th>
					<th>访客姓名</th>
					<th>访客电话</th>
					<th>受访人员姓名</th>
					<th>受访人员电话</th>
					<th>访问时间</th>
					<th width="100">操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var ="visitor" items="${pageMap.info}" varStatus="tag">
				<tr class="text-c">
					<td><input type="checkbox" name="ids" id="" value="${visitor.visitorid}"></td>
					<td>${(pageMap.page.currentPage-1)*(pageMap.page.pageSize)+tag.count}</td>
					<td><a title="查看" href="javascript:;" onclick="system_visitor_operation('人员详情','system-visitor-show.html?id='+${visitor.visitorid})" class="ml-5" style="text-decoration:none"><span style="text-decoration:underline">${visitor.name}</span></a></td>
					<td>${visitor.tel } </td>
					<td>${visitor.personnel_name}</td>
					<td>${visitor.personnel_tel}</td>
					<td>
						<fmt:formatDate value="${visitor.accesstime}" pattern="yyyy-MM-dd"/>
					</td>
					<td class="f-14">
						<a title="编辑" href="javascript:;" onclick="system_visitor_operation('访客修改','system-visitor-edit.html?id='+${visitor.visitorid})" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
						<a title="删除" href="javascript:;" onclick="visitor_manage_del(this, ${visitor.visitorid})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
					</td>
				</tr>
			</c:forEach>																								
			</tbody>
		</table>
		<%@include file="../inc/page.jsp"%>
        <input type="hidden" name="pageUrl" value="system-visitor-manage.html" />
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
function system_visitor_operation(title,url,w,h){
	layer_show(title,url,w,500);
}
/*系统-栏目-删除*/
function visitor_manage_del(obj,id){
	layer.confirm('确认要删除该访客访客吗？',function(index){
		$.ajax({
			type: 'GET',
			url: 'system-visitor-del',
			data: {id:id},
			dataType: 'json',
			cache: false,
			success: function(data){
				if(data.head){
					layer.msg('访客删除成功!',{icon:1,time:2000},function(){
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
		layer.msg('请选择要删除的访客!',{icon:2,time:1000});
		return false;
	}	
	layer.confirm('确认要删除选定的访客吗？',function(index){		
		$.ajax({
			type: 'POST',
			url: 'system-visitor-del',
			data: ids,
			dataType: 'json',
			success: function(data){
				if(data.head){
					layer.msg('访客删除成功!',{icon:1,time:2000},function(){
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
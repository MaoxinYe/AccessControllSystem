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
<title>用户管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>
	用户列表
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container">
    
    <form id="userManage" action="system-user-manage.html" method="post">
    <div class="text-c" style="text-align:left">
         登录名：
        <input type="text" class="input-text" style="width:160px" placeholder="输入登录名" id="username" name="username" value="${conditions.username}">
        角色类型：
        <span class="select-box inline">
        <select class="select" id="roleid" name="roleid"  style="width:160px;">
			<option value="">-请选择-</option>
			<c:forEach var="role" items="${roleList}">
				<option value="${role.roleid}" <c:if test="${conditions.roleid==role.roleid}">selected="selected"</c:if> >${role.rolename}</option>
			</c:forEach>
		</select>
		</span>
       <button type="submit" class="btn btn-success" id="serach" name="serach"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
    </div>
    </form>

    <form name="searchresult" id="searchresult" action="" method="post">
    <input type="hidden" id="username" name="username" value="${conditions.username}">
	<input type="hidden" id="roleid" name="roleid" value="${conditions.roleid}">
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
		<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
		<a class="btn btn-primary radius" onclick="system_user_operation('添加用户','system-user-add.html')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加用户</a>
		</span> <span class="r">共有数据：<strong>${pageMap.page.totalRows}</strong> 条</span>
	</div>
	<div class="mt-20">
	<div class="dataTables_wrapper no-footer" id="DataTables_Table_0_wrapper">
		<table class="table table-border table-bordered table-hover table-bg ">
			<thead>
				<tr class="text-c">
					<th width="25"><input type="checkbox" name="" value=""></th>
					<th width="50">序号</th>
					<th>登录名</th>
					<th>角色类型</th>
					<th>添加时间</th>
					<th width="100">操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var ="user" items="${pageMap.info}" varStatus="tag">
				<tr class="text-c">
					<td><input type="checkbox" name="ids" id="" value="${user.userid}"></td>
					<td>${(pageMap.page.currentPage-1)*(pageMap.page.pageSize)+tag.count}</td>
					<td>${user.username}</td>
					<td>${user.xt_role.rolename}</td>
					<td><fmt:formatDate value="${user.addtime}" pattern="yyyy-MM-dd"/></td>
					<td class="f-14"><a title="编辑" href="javascript:;" onclick="system_user_operation('用户修改','system-user-edit.html?id='+${user.userid})" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
						<a title="修改密码" href="javascript:;" onclick="system_user_operation('修改密码','user-password-edit.html?userid='+${user.userid})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe63f;</i></a>
						<a title="删除" href="javascript:;" onclick="user_manage_del(this, ${user.userid})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
					</td>
				</tr>
			</c:forEach>																								
			</tbody>
		</table>
		<%@include file="../inc/page.jsp"%>
        <input type="hidden" name="pageUrl" value="system-user-manage.html" />
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
	  {"orderable":false,"aTargets":[0,2,3,4,5,6]}// 制定列不参与排序
	]
});
/*系统-栏目-操作*/
function system_user_operation(title,url,w,h){
	layer_show(title,url,w,500);
}
/*系统-栏目-状态*/
function user_manage_lock(obj,id){
	layer.confirm('确认要切换该用户状态吗？',function(index){
		$.ajax({
			type: 'GET',
			url: 'user-manage-lock',
			data: {id:id},
			dataType: 'json',
			cache: false,
			success: function(data){
				if(data.head){
					layer.msg('用户状态已切换!',{icon:1,time:2000},function(){
						query();
                    });
					
				}else {
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
function user_manage_del(obj,id){
	layer.confirm('确认要删除该用户用户吗？',function(index){
		$.ajax({
			type: 'GET',
			url: 'system-user-del',
			data: {id:id},
			dataType: 'json',
			cache: false,
			success: function(data){
				if(data.head){
					layer.msg('用户删除成功!',{icon:1,time:2000},function(){
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
		layer.msg('请选择要删除的用户!',{icon:2,time:1000});
		return false;
	}	
	layer.confirm('确认要删除选定的用户吗？',function(index){		
		$.ajax({
			type: 'POST',
			url: 'system-user-del',
			data: ids,
			dataType: 'json',
			success: function(data){
				if(data.head){
					layer.msg('用户删除成功!',{icon:1,time:2000},function(){
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
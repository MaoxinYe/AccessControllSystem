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
<title>人员信息</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>
	人员列表
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container">
    
    <form id="personnelManage" action="personnel-manage.html" method="post">
    <div class="text-c" style="text-align:left">
        人员姓名：
        <input type="text" class="input-text" style="width:160px" placeholder="输入人员姓名" id="name" name="name" value="${conditions.name}">
        证件号码：
        <input type="text" class="input-text" style="width:160px" placeholder="输入证件号码" id="credentials" name="credentials" value="${conditions.credentials}">
      所属${area}：
		<select class="select" id="areacode" name="areacode" style="width: 200px;height: 31px">
			<option value="">请选择所属${area}</option>
		 	<c:forEach var="area" items="${areaList}">
		 		<option value="${area.areacode}" <c:if test="${area.areacode==conditions.areacode}">selected="selected"</c:if> >${area.areaname}</option>
			</c:forEach>
		</select>
		
       	<button type="submit" class="btn btn-success" id="serach" name="serach"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
    </div>
    </form>
    <form id="messageAction" action="importPhotoResult.html" method="post">
	   <input type="hidden" id="falseMessage" name="falseMessage" value=""/>
	</form>
    <form name="searchresult" id="searchresult" action="" method="post">
    <input type="hidden" id="name" name="name" value="${conditions.name}">
	<input type="hidden" id="tel" name="tel" value="${conditions.tel}">
	<input type="hidden" id="credentials" name="credentials" value="${conditions.credentials}">
	<input type="hidden" id="cardid" name="cardid" value="${conditions.cardid}">
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
		<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
		<a class="btn btn-primary radius" onclick="personnel_operation('添加人员','personnel-add.html')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加人员</a>
		<!-- <a title="Excel模板下载" href="personnel-excel-export.html" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe640;</i>Excel模板下载</a>
		<a href="javascript:;" onclick="personnel_operation('人员Excel导入','personnel-excel-import.html','','300')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe645;</i> 人员Excel信息导入</a>
		<a href="javascript:;" onclick="personnel_operation('人员照片导入','personnel-photo-import.html','','300')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe645;</i> 人员照片导入</a>
		 --></span> <span class="r">共有数据：<strong>${pageMap.page.totalRows}</strong> 条</span>
	</div>
	<div class="mt-20">
	<div class="dataTables_wrapper no-footer" id="DataTables_Table_0_wrapper">
		<table class="table table-border table-bordered table-hover table-bg ">
			<thead>
				<tr class="text-c">
					<th width="25"><input type="checkbox" name="" value=""></th>
					<th width="50">序号</th>
					<th>人员姓名</th>
					<th>性别</th>
					<th>证件号</th>
					<th>联系电话</th>
					<th>所属${area}</th>
					<!-- <th>人员状态</th> -->
					<th width="100">操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var ="personnel" items="${pageMap.info}" varStatus="tag">
				<tr class="text-c">
					<td><input type="checkbox" name="ids" id="" value="${personnel.personnelid}"></td>
					<td>${(pageMap.page.currentPage-1)*(pageMap.page.pageSize)+tag.count}</td>
					<td><a title="查看" href="javascript:;" onclick="personnel_operation('人员详情','personnel-show.html?id='+${personnel.personnelid})" class="ml-5" style="text-decoration:none"><span style="text-decoration:underline">${personnel.name}</span></a></td>
					<td>
					<c:if test="${personnel.sex==1}">男</c:if>
					<c:if test="${personnel.sex==2}">女</c:if>
					</td>
					<td>${personnel.credentials}</td>
					<td>${personnel.tel}</td>
					<td>${personnel.area.areaname}</td>
					<%-- <td>${personnel.status == 0 ? "无效" : "有效"}</td> --%>
					<td class="f-14">
					<a title="编辑" href="javascript:;" onclick="personnel_operation('人员信息修改','personnel-edit.html?id='+${personnel.personnelid})" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
					<a title="删除" href="javascript:;" onclick="personnel_del(this, ${personnel.personnelid})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
					</td>
				</tr>
			</c:forEach>																								
			</tbody>
		</table>
		<%@include file="../inc/page.jsp"%>
        <input type="hidden" name="pageUrl" value="personnel-manage.html" />
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
function personnel_operation(title,url,w,h){
	layer_show(title,url,w,500);
};
/*系统-栏目-恢复*/
function personnel_return(obj,id){
	layer.confirm('确认要恢复该人员吗？',function(index){
		$.ajax({
			type: 'GET',
			url: 'personnel-return',
			data: {id:id},
			dataType: 'json',
			cache: false,
			success: function(data){
				if(data.head){
					layer.msg('人员已恢复成功!',{icon:1,time:2000},function(){
						query();
                    });					
				} else {
					if(data.body == 'UNLOGIN'){
						layer.msg("请先登录!",{icon:1,time:1000},function(){
							window.parent.location.href = "../login.html";
						});								
					}else {
						layer.msg(data.body,{icon:2,time:2000});
					}
				}
			},		
			error: function(XmlHttpRequest, textStatus, errorThrown){
				layer.msg('系统错误!'+XMLHttpRequest.status+XMLHttpRequest.readyState,{icon:1,time:6000});
			},
		});
	});
};
/*系统-栏目-删除*/
function personnel_del(obj,id){
	layer.confirm('确认要删除该人员吗？',function(index){
		$.ajax({
			type: 'GET',
			url: 'personnel-del',
			data: {id:id},
			dataType: 'json',
			cache: false,
			success: function(data){
				if(data.head){
					layer.msg('人员信息已删除!',{icon:1,time:2000},function(){
						query();
                    });					
				} else {
					if(data.body == 'UNLOGIN'){
						layer.msg("请先登录!",{icon:1,time:1000},function(){
							window.parent.location.href = "../login.html";
						});								
					}else {
						layer.msg(data.body,{icon:2,time:2000});
					}
				}
			},		
			error: function(XmlHttpRequest, textStatus, errorThrown){
				layer.msg('系统错误!'+XMLHttpRequest.status+XMLHttpRequest.readyState,{icon:1,time:6000});
			},
		});
	});
};
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
			url: 'personnel-del',
			data: ids,
			dataType: 'json',
			success: function(data){
				if(data.head){
					layer.msg('人员信息删除成功!',{icon:1,time:2000},function(){
						query();
                    });
				} else {
					if(data.body == 'UNLOGIN'){
						layer.msg("请先登录!",{icon:1,time:1000},function(){
							window.parent.location.href = "../login.html";
						});								
					}else {
						layer.msg(data.body,{icon:2,time:2000});
					}
				}
			},
			error: function(XmlHttpRequest, textStatus, errorThrown){
				layer.msg('系统错误!'+XMLHttpRequest.status+XMLHttpRequest.readyState,{icon:1,time:6000});
			}
		});
	});
};
function query() {
    $("#searchresult").submit();
};
function messageFunction(message){
    $("#falseMessage").val(message);
    $("#messageAction").submit();
};
</script>
</body>
</html>
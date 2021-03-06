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
<title>出入记录管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>
	出入记录管理
	<span class="c-gray en">&gt;</span>
	未出记录
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container">

	<form id="recordManage" action="system-record-notout.html" method="post">
    <div class="text-c" style="text-align:left">
    	所属${area}：
        <span class="select-box inline">
        <select class="select" id="areacode" name="areacode"  style="width:160px;">
			<option value="">-请选择-</option>
			<c:forEach var="area"	items="${areaList}">
				<option value="${area.areacode }" <c:if test="${conditions.areacode == area.areacode}">selected="selected"</c:if>>${area.areaname}</option>
			</c:forEach>
		</select>
		</span>
		<c:if test="${recordType == 1}">
			<input hidden="hidden" name="type" id="type" value="1">
		</c:if>
		<c:if test="${recordType == 2}">
		 触发类型：
        <span class="select-box inline">
        <select class="select" id="type" name="type"  style="width:160px;">
			<option value="">-请选择-</option>
			<option value="1" <c:if test="${conditions.type==1}">selected="selected"</c:if>>-人脸识别-</option>
			<option value="2" <c:if test="${conditions.type==2}">selected="selected"</c:if>>-刷卡-</option>
			<option value="3" <c:if test="${conditions.type==3}">selected="selected"</c:if>>-刷卡加人脸识别-</option>
		</select>
		</span>
		</c:if>
		 人员类型：
        <span class="select-box inline">
        <select class="select" id="personneltype" name="personneltype"  style="width:160px;">
			<option value="1" <c:if test="${conditions.personneltype==1}">selected="selected"</c:if>>-人员-</option>
			<option value="2" <c:if test="${conditions.personneltype==2}">selected="selected"</c:if>>-访客-</option>
		</select>
		</span>
		<input hidden="hidden" name="result" id="result" value="1">
        时间：
        <input type="text" style="width:160px" placeholder="选择时间" id="time" name="time" value="${conditions.time}" onfocus="WdatePicker({ dateFmt:'yyyy-MM-dd' })" class="input-text Wdate">
        <button type="submit" class="btn btn-success" id="serach" name="serach"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
   
    </div>
    </form>
    <form name="searchresult" id="searchresult" action="" method="post">
	<input type="hidden" id="areacode" name="areacode" value="${conditions.areacode}">
	<input type="hidden" id="result" name="result" value="${conditions.result}">
	<input type="hidden" id="type" name="type" value="${conditions.type}">
	<input type="hidden" id="personneltype" name="personneltype" value="${conditions.personneltype}">
	<input type="hidden" id="time" name="time" value="${conditions.time}">
    <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l">
    </span> <span class="r">共有数据：<strong>${pageMap.page.totalRows}</strong> 条</span> </div>
	<div class="mt-20">
	<div class="dataTables_wrapper no-footer" id="DataTables_Table_0_wrapper">
		<table class="table table-border table-bordered table-hover table-bg table-sort">
			 <thead>
                <tr class="text-c">
                    <th width="50">序号</th>
                    <th width="80">${area}名称</th>
                    <th>进出门</th>
                    <th>触发类型</th>
                    <th>人员姓名</th>
                    <th>人员类型</th>
                    <th>出入时间</th>
                    <th>操作</th>
                </tr>
            </thead>
			<tbody>
            <c:forEach var ="record" items="${pageMap.info}" varStatus="tag">
                <tr class="text-c">
                    <td>${(pageMap.page.currentPage-1)*(pageMap.page.pageSize)+tag.count}</td>
                    <td>${record.areaname}</td>
                    <td>${record.inorout == 1 ? "进门" : "出门"}</td>
                    <td>${record.inorout == 1 ? "人脸识别" : record.inorout == 2 ? "刷卡" : "刷卡加人脸识别"}</td>
                    <td>${record.name}</td>
                    <td>${record.personneltype == 1 ? "人员" : "访客"}</td>
                    <td><fmt:formatDate value="${record.createtime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                	<td>
                		<a title="查看" href="javascript:;" onclick="record_show('出入记录查看','record-info.html?id=${record.id}')" style="text-decoration:none"><i class="Hui-iconfont">&#xe695;</i></a>
                	</td>
                </tr>
            </c:forEach>                                                                                                
            </tbody>
		</table>
		<%@include file="../inc/page.jsp"%>
        <input type="hidden" name="pageUrl" value="system-record-notout.html" />
	</div>
	</div>
	</form>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="../js/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="../js/layer/2.4/layer.js"></script>
<script type="text/javascript" src="../static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="../static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="../js/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="../js/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../js/laypage/1.2/laypage.js"></script>
</body>
</html>
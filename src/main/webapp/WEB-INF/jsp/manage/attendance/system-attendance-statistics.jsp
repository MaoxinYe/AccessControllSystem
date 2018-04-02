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
<!--[if IE 6]>
<script type="text/javascript" src="../js/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>考勤统计</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>
	考勤统计
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container">
	
	<form id="attendancestatistics" action="system-attendance-statistics.html" method="post">
    <div class="text-c" style="text-align:left">
    	人员姓名：
        <input type="text" class="input-text" style="width:160px" placeholder="输入人员姓名" id="personnel_name" name="personnel_name" value="${conditions.personnel_name}">
         &nbsp;&nbsp;开始时间：
        <input type="text" style="width:160px" placeholder="选择开始时间" id="starttime" name="starttime" value="${conditions.starttime}" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'endtime\')||\'%y-%M-%d\'}'})" class="input-text Wdate">
         &nbsp;&nbsp;结束时间：
        <input type="text" style="width:160px" placeholder="选择结束时间" id="endtime" name="endtime" value="${conditions.endtime}" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'starttime\')}',maxDate:'%y-%M-%d'})" class="input-text Wdate">
        <button type="submit" class="btn btn-success" id="serach" name="serach"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
   
    </div>
    </form>
	
	<form name="searchresult" id="searchresult" action="" method="post">
	<input type="hidden" id="personnel_name" name="personnel_name" value="${conditions.personnel_name}">
	<input type="hidden" id="starttime" name="starttime" value="${conditions.starttime}">
	<input type="hidden" id="endtime" name="endtime" value="${conditions.endtime}">
    <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l">
    </span> <span class="r">共有数据：<strong>${pageMap.page.totalRows}</strong> 条</span> </div>
    <div class="mt-20">
    <div class="dataTables_wrapper no-footer" id="DataTables_Table_0_wrapper">
    <table class="table table-border table-bordered table-hover table-bg ">
            <thead>
                <tr class="text-c">
                    <th width="50">序号</th>
                    <th>人员姓名</th>
                    <th>总记录数</th>
                    <th>正常记录数</th>
                    <th>异常记录数</th>
                    <th>迟到早退数</th>
                    <th>缺勤记录数</th>
                    <th>迟到记录数</th>
                    <th>早退记录数</th>
                    <th>出差记录数</th>
                    <th>请假记录数</th>
                    <th>调休记录数</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var ="attendance" items="${pageMap.info}" varStatus="tag">
                <tr class="text-c">
                 	<td>${(pageMap.page.currentPage-1)*(pageMap.page.pageSize)+tag.count}</td>
                    <td>${attendance.personnel_name}</td>
                    <td><a href="javascript:;" onclick="system_attendance_operation('${attendance.personnel_name}','100')">${attendance.totalNum}</a></td>
                    <td><a href="javascript:;" onclick="system_attendance_operation('${attendance.personnel_name}','0')">${attendance.normalNum}</a></td>
                    <td><a href="javascript:;" onclick="system_attendance_operation('${attendance.personnel_name}','-3')">${attendance.abnormalNum}</a></td>
                    <td><a href="javascript:;" onclick="system_attendance_operation('${attendance.personnel_name}','-2')">${attendance.earlyLateNum}</a></td>
                    <td><a href="javascript:;" onclick="system_attendance_operation('${attendance.personnel_name}','-1')">${attendance.absenceNum}</a></td>
                    <td><a href="javascript:;" onclick="system_attendance_operation('${attendance.personnel_name}','1')">${attendance.lateNum}</a></td>
                    <td><a href="javascript:;" onclick="system_attendance_operation('${attendance.personnel_name}','2')">${attendance.earlyNum}</a></td>
                    <td><a href="javascript:;" onclick="system_attendance_operation('${attendance.personnel_name}','3')">${attendance.outNum}</a></td>
                    <td><a href="javascript:;" onclick="system_attendance_operation('${attendance.personnel_name}','4')">${attendance.leaveNum}</a></td>
                    <td><a href="javascript:;" onclick="system_attendance_operation('${attendance.personnel_name}','5')">${attendance.offNum}</a></td>
                </tr>
            </c:forEach>                                                                                                
            </tbody>
        </table>
        <%@include file="../inc/page.jsp"%>
        <input type="hidden" name="pageUrl" value="system-attendance-statistics.html" />
    </div>
    </div>
   </form>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="../js/layer/2.4/layer.js"></script>
<script type="text/javascript" src="../static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="../static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="../js/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="../js/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../js/laypage/1.2/laypage.js"></script>
<script type="text/javascript">
/*系统-栏目-操作*/
function system_attendance_operation(name,status){
	if(name != null && name != "" && status != null && status != ""){
		var href = "system-attendance-manage.html?personnel_name="+name+"&status="+status;
		var starttime = $("#starttime").val();
		var endtime = $("#endtime").val();
		if(starttime != null && starttime != ""){
			href = href + "&starttime="+starttime;
		}
		if(endtime != null && endtime != ""){
			href = href + "&endtime="+endtime;
		}
		window.location.href = href;
	}
}
</script>
</body>
</html>
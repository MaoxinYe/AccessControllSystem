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

<title>人员管理</title>
</head>
<body>
<article class="page-container">
		<div class="tabBar cl">
			<span>详细信息</span>
		</div>
		
	<div class="dataTables_wrapper no-footer" id="DataTables_Table_0_wrapper">
        <table class="table table-border table-bordered table-hover table-bg table-sort dataTable no-footer" id="DataTables_Table_0" role="grid" aria-describedby="DataTables_Table_0_info">
         <thead>
            <tr class="text-c">
                <th colspan='2'>访客照片</th>
                <th colspan='2'>受访人员照片</th>
            </tr>
        </thead>
        <tbody>
            <tr class="text-c">
                <td colspan='2' >
                <img width="179" height="221" alt="照片" src="${session_photoVirtualPath}${visitor.path}">
                </td>
                <td colspan='2' >
                <img width="179" height="221" alt="照片" src="${session_photoVirtualPath}${face.path}">
                </td>
            </tr>
        </tbody>
        <thead>
            <tr class="text-c">
                <th>访客姓名</th>
                <th>访客性别</th>
               	<th>访问对象姓名</th>
                <th>访问对象电话</th>
            </tr>
        </thead>
        <tbody>
            <tr class="text-c">
                <td>
                ${visitor.name }
                </td>
                <td>
                <c:if test="${visitor.sex == 1 }">男</c:if>
                <c:if test="${visitor.sex == 2 }">女</c:if>
                <c:if test="${visitor.sex == 3 }">其他</c:if>
                </td>
                 <td>
                ${visitor.personnel_name}
                </td>
                <td>
                ${visitor.personnel_tel}
                </td>
            </tr>
        </tbody>
        <thead>
            <tr class="text-c">
                <th>访客证件号</th>
                <th>访客电话</th>
                <th>访问日期</th>
                <th>备注</th>
            </tr>
        </thead>
        <tbody>
            <tr class="text-c">
           	 	<td>
                ${visitor.credentials}
                </td>
                <td>
                ${visitor.tel }
                </td>
                <td>
                <fmt:formatDate value="${visitor.accesstime }" pattern="yyyy-MM-dd"/>
                </td>
                <td colspan = '2'>
                ${visitor.remarks}
                </td>
            </tr>
        </tbody>
        
    </table>
    </div>
		
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="../js/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="../js/layer/2.4/layer.js"></script>
<script type="text/javascript" src="../static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="../static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="../js/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="../js/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="../js/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="../js/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript">
$(function(){	
	
});
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
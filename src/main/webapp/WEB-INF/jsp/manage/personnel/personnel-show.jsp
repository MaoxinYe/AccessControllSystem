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
        <%-- <thead>
            <tr class="text-c">
                <th colspan='2'>证件照</th>
                <th colspan='3'>现场照</th>
            </tr>
        </thead>
        <tbody>
            <tr class="text-c">
                <td colspan='2' >
                 <img  width="258" height="168" id="img1" alt="证件照" src="data:image/jpg;base64,${cardBase64 }">
                </td>
                <td colspan="3">
                 <img  width="148" height="168" id="img2" alt="现场照" src="data:image/jpg;base64,${personimage.photoString }">
                </td>
            </tr>
        </tbody> --%>
         <thead>
            <tr class="text-c">
                <th colspan='4'>照片</th>
            </tr>
        </thead>
        <tbody>
            <tr class="text-c">
                <td colspan='4' >
                <img width="179" height="221" alt="照片" src="${session_photoVirtualPath}${face.path}">
                </td>
            </tr>
        </tbody>
        <thead>
            <tr class="text-c">
                <th>姓名</th>
                <th>性别</th>
                <th>证件号</th>
                <th>联系电话</th>
            </tr>
        </thead>
        <tbody>
            <tr class="text-c">
                <td>
                ${personnel.name }
                </td>
                <td>
                <c:if test="${personnel.sex == 1 }">男</c:if>
                <c:if test="${personnel.sex == 2 }">女</c:if>
                </td>
                <td>
                ${personnel.credentials}
                </td>
                <td>
                ${personnel.tel }
                </td>
            </tr>
        </tbody>
        <thead>
            <tr class="text-c">
                <th>人员类型</th>
                <th>所属${area}</th>
                <th>门禁卡号</th>
                <th>详细地址</th>
            </tr>
        </thead>
        <tbody>
            <tr class="text-c">
                <td>
              		<c:forEach var="type" items="${personnelTypeList}">
		 				<c:if test="${type.id == personnel.type}">${type.name}</c:if>
					</c:forEach>
                </td>
                <td>
                ${personnel.area.areaname}
                </td>
                <td>
                ${personnel.cardid}
                </td>
                <td>
                ${personnel.address}
                </td>
            </tr>
        </tbody>
        <thead>
            <tr class="text-c">
                <th>添加时间</th>
                <th>截止日期</th>
                <th colspan = '2'>备注</th>
            </tr>
        </thead>
         <tbody>
            <tr class="text-c">
                <td>
                <fmt:formatDate value="${personnel.addtime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>
                <fmt:formatDate value="${personnel.expirationdate }" pattern="yyyy-MM-dd"/>
                </td>
                <td colspan = '2'>
                ${personnel.remarks}
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
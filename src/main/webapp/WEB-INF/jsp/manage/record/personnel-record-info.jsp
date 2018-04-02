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
<title>人员出入记录详情</title>
<meta name="keywords" content="">
<meta name="description" content="">
</head>
<body>
<form name="searchresult" id="searchresult" action="" method="post">
    <div class="mt-20">
    <div class="dataTables_wrapper no-footer" id="DataTables_Table_0_wrapper">
    <p style="font-size: 30px;font-weight: bold;" align="center">人员出入记录详情</p>
    <table class="table table-border table-bordered table-hover table-bg ">
		<tbody>
			<tr class="text-c">
				<td style="font-size: 20px">所属${areaname}：${area.areaname}</td>
				<td style="font-size: 20px">闸机说明：${gates.gatesname}</td>
			</tr>
			<tr class="text-c">
				<td style="font-size: 20px">人员姓名：${personnel.name}</td>
				<td style="font-size: 20px">人员证件号：${personnel.credentials}</td>
			</tr>
			<tr class="text-c">
				<td style="font-size: 20px">进出门：${record.inorout == 1 ? "进门" : "出门"}</td>
				<td style="font-size: 20px">记录时间：<fmt:formatDate value="${record.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
				<td>人员模板照：
              		<img id="img1" src="${session_photoVirtualPath}${faceid.path}" width="171px" height="210px" alt="无照片"/>
           		</td>
           		<td>人员现场照：
              		<img id="img2" src="${session_photoVirtualPath}${record.path}" width="171px" height="210px" alt="无照片"/>
           		</td>
			</tr>
		</tbody>
    </table>
    </div>
    </div>
   </form>
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
</script>
</body>
</html>

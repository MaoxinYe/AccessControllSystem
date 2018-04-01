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
<script type="text/javascript" src="../js/jquery/1.9.1/jquery.min.js"></script> 
<!--[if IE 6]>
<script type="text/javascript" src="../js/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>人员Excel导入</title>
<meta name="keywords" content="">
<meta name="description" content="">
</head>
<body>
<article class="page-container" style="margin-left:100px;width:700px;">
	<form action="personnel-excel-import" method="post" class="form form-horizontal" id="form-admin-add">
    
	<div class="row cl">
    <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>选择Excel：</label>
    <div class="formControls col-xs-8 col-sm-9">
        <span class="btn-upload form-group" style="width:500px;">
             <input class="input-text upload-url radius" style="width:200px" type="text" name="uploadfile-1" id="uploadfile-1" readonly><a href="javascript:void();" class="btn btn-primary radius"><i class="Hui-iconfont">&#xf0020;</i> 浏览文件</a>
             <input type="file" multiple name="excelFile" id="excelFile" class="input-file">
        </span>
    </div>
    </div>
	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
		</div>
	</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去-->
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
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$("#form-admin-add").validate({
		rules:{
			excelFile:{
				required:true,
				checkXlsx:true,
			}
		},
		messages:{
			
			excelFile:{
				required:"请选择excel文件",
				checkXlsx:"文件后缀名必须为xlsx",
			}
		},
		errorPlacement: function (error, element) {
			if (element.is("#excelFile")) {
				error.insertAfter(element.parent());
			} else {
				error.insertAfter(element);
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
				dataType : "json",
				success: function(data){
					if(data.head){
						layer.msg("人员批量添加成功!",{icon:1,time:2000},function(){
							parent.query();
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
						});
					} else {
						if(data.body == 'UNLOGIN'){
	                           layer.msg("请先登录!",{icon:1,time:1000},function(){
	                               window.parent.location.href = "../login.html";
	                               var index = parent.layer.getFrameIndex(window.name);
	                               parent.layer.close(index);
	                           });                             
	                       } /* else if(data.body == 'PERMISSION_DENIED') {
	   						layer.msg("您无操作权限!",{icon:2,time:2000});
	   					} */ else {
	                           layer.msg(data.body,{icon:2,time:2000});
	                       }
					}
				},
				error: function(XmlHttpRequest, textStatus, errorThrown){
					layer.msg("提交出现异常，请重试！",{icon:2,time:1000});
				}
			});
		}
	});
});
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>

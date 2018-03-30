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
<!--[if IE 6]>
<script type="text/javascript" src="../js/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->

<title>模块管理</title>
</head>
<body>
<article class="page-container">
	<form action="system-module-edit" method="post" class="form form-horizontal" id="form-system-module-edit">
	<input type="hidden" id="id" name="id" value="${module.modulecode}"/>
		<div class="tabBar cl">
			<span>修改模块信息</span>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>模块编号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${module.modulecode}" placeholder="" id="modulecode" name="modulecode" readonly="readonly" style="width:200px">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>模块名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${module.modulename}" placeholder="" id="modulename" name="modulename" style="width:200px">
			</div>
		</div>		
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<button type="submit" class="btn btn-success radius" id="admin-role-save" name="admin-role-save"><i class="icon-ok"></i> 确定</button>
			</div>
		</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="../js/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="../js/layer/2.4/layer.js"></script>
<script type="text/javascript" src="../static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="../static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="../js/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="../js/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="../js/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript">
$(function(){	
	$("#form-system-module-edit").validate({
		debug:true,
		rules:{
			modulecode:{ required:true, number:true, rangelength: [4,4], min:1000 },
			modulename:{ required:true, maxlength: 10,
				remote:{
                    depends : function(element) {
                        return element.value !== "${module.modulename}";
                    },
                    param : {
                        url : "check-modulename.html",
                        cache :false
                    },
                },
            },
		},
		messages: {
			modulecode:{ required: "请输入模块编号", number: "请输入4位数字", rangelength: "请输入4位数字", min:"从1000开始" },
			modulename:{ required: "请输入模块名称", maxlength: "最多输入10个字符", remote: "模块名称已被使用" },
		},
		onkeyup:false,
		focusCleanup:false,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
					dataType : "json",
					success: function(data){
						if(data.head){
							layer.msg("功能模块修改成功!",{icon:1,time:1000},function(){
								window.parent.location.reload();
								var index = parent.layer.getFrameIndex(window.name);
								parent.layer.close(index);
							});
						}  else {
							if(data.body == 'UNLOGIN'){
								layer.msg("请先登录!",{icon:1,time:1000},function(){
									window.parent.location.href = "../login.html";
								});								
							}  /* else if(data.body == 'PERMISSION_DENIED') {
								layer.msg("您无操作权限!",{icon:2,time:2000});
							} */  else {
								layer.msg(data.body,{icon:2,time:2000});
							}
						} 
					},
					error: function(XmlHttpRequest, textStatus, errorThrown){
						layer.msg('系统错误!'+XMLHttpRequest.status,{icon:1,time:1000});
					}
				});
		}
	});
});
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
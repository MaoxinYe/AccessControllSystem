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
<script type="text/javascript" src="js/html5shiv.js"></script>
<script type="text/javascript" src="js/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="static/Wopop_files/style.css">
<link href="static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
<link href="js/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="js/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>人脸识别门禁系统</title>
</head>
<body style="background:url(static/Wopop_files/bg.jpg) ;background-size:cover;" >
<div>
 
  <div id="loginform" class="loginBox">
  	<div align="center"><h4>人脸识别门禁系统</h4></div>
    <form class="form form-horizontal" action="login" method="post" id="form-login">
      <div class="row cl">
        <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-xs-8">
          <input id="username" name="username" type="text" placeholder="账号" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-xs-8">
          <input id="psw" name="psw" type="password" placeholder="密码" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-xs-8 col-xs-offset-3">
          <input name="" type="submit" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
        </div>
      </div>
    </form>
  </div>
</div>
<script type="text/javascript" src="js/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="js/layer/2.4/layer.js"></script>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="js/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="js/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="js/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript">
jQuery.validator.addMethod("stringMaxLength", function(value,element, param) {
	var length = value.length;
	for ( var i = 0; i < value.length; i++) {
		if (value.charCodeAt(i) > 127) {
			length++;
		}
	}
return this.optional(element) || (length <=param);
}, $.validator.format("长度不能大于{0}!"));
$(function(){
	$("#form-login").validate({
		debug:false,
		rules:{
			username:{ required:true, stringMaxLength: 20, },
			psw:{ required:true, },
		},
		messages: {
			username:{ required: "请输入登录名", stringMaxLength: "长度不能大于20",},
			psw:{ required: "请输入密码", },
		},
		onkeyup:false,
		focusCleanup:false,
		success:"valid",
		showErrors: function (errorMap, errorList) {
			$.each(errorList, function (i, v) {
				layer.tips(v.message, v.element, { time: 2000 });
				return false;
			});  
		},		
		submitHandler:function(form){
			$(form).ajaxSubmit({
					dataType : "json",
					success: function(data){
						if(data.head){
							location.href="manage/index.html";
						} else {
							layer.msg(data.body,{icon:2,time:2000});
						}
					},
					error: function(XmlHttpRequest, textStatus, errorThrown){
						layer.msg('系统错误!',{icon:1,time:1000});
					}
				});
		}
	});
});
if (window != window.top) {
	top.location.href = location.href;
}
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
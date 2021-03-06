﻿<%@ page contentType="text/html; charset=UTF-8" language="java"%>
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

<title>用户管理</title>
</head>
<body>
<article class="page-container">
	<form action="system-user-add" method="post" class="form form-horizontal" id="form-user-manage-add">
		<div class="tabBar cl">
			<span>添加用户信息</span>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色类型：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="select" size="1" id="roleid" name="roleid" style="width: 200px;height: 31px">
					<option value="">--请选择--</option>
					<c:forEach var="role" items="${roleList}">
						<option value="${role.roleid}">${role.rolename}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>登录名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="请输入登录名" id="username" name="username" style="width:200px">
			</div>
		</div>
		
		<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>登录密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" autocomplete="off" value="" placeholder="请输入密码" id="password" name="password" style="width:200px">
		</div>
		</div>
		<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>确认密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" autocomplete="off"  placeholder="请再次输入密码" id="mm" name="mm" style="width:200px">
		</div>
		</div>
		<!-- <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="请输入姓名" id="username" name="username" style="width:200px">
			</div>
		</div> -->
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
	$("#form-user-manage-add").validate({
		debug:true,
		rules:{
			roleid:{ required:true, },
			username:{ required:true, maxLengthInCN: 30, remote: "check-loginname.html", checkENGNUM:true },
			password:{required:true, maxLengthInCN: 30,isPwd:true,},
			mm:{required:true,equalTo: "#password",isPwd:true,},
		},
		messages: {
			roleid:{ required: "角色类型不能为空！", },
			username:{ required: "登录名不能为空！", maxLengthInCN: "登录名不能超过30个字符！", remote:"该登录名已存在", checkENGNUM:"请输入正确的英文字母或数字" },
			password:{ required: "登录密码不能为空！", maxLengthInCN: "登录密码不能超过30个字符！", isPwd:"以字母开头，长度在6-12之间，只能包含字符、数字和下划线" },
			mm:{ required: "请确认登录密码", maxLengthInCN: "登录密码不能超过30个字符！",equalTo:"两次输入的密码不一致", isPwd:"以字母开头，长度在6-12之间，只能包含字符、数字和下划线" },
		},
		onkeyup:false,
		focusCleanup:false,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
				    dataType : "json",
					success: function(data){
						if(data.head){
							layer.msg("用户添加成功!",{icon:1,time:1000},function(){
								parent.query();
								var index = parent.layer.getFrameIndex(window.name);
								parent.layer.close(index);
							});
						} else {
							if(data.body == 'UNLOGIN'){
								layer.msg("请先登录!",{icon:1,time:1000},function(){
									window.parent.location.href = "../login.html";
								});								
							}else if(data.body == 'PERMISSION_DENIED') {
								layer.msg("您无操作权限!",{icon:2,time:2000});
							} else {
								layer.msg(data.body,{icon:2,time:2000});
							}
						}
					},
					error: function(XmlHttpRequest, textStatus, errorThrown){
						layer.msg('系统错误!',{icon:1,time:1000});
					}
				});
		}
	});
});
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
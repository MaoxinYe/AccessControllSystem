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

<title>添加作息时间</title>
</head>
<body>
<article class="page-container" style="padding-left: 200px"> 
	<form action="system-worktime-add" method="post" class="form form-horizontal" id="form-system-worktime-add">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>上班时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" style="width:160px" placeholder="选择上班时间" id="startTime" name="startTime" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'HH:mm'})" class="input-text Wdate">
			</div>
		</div>	
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>下班时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" style="width:160px" placeholder="选择下班时间" id="endTime" name="endTime" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'HH:mm'})" class="input-text Wdate">
			</div>
		</div>	
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>班次名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="" id="illustrate" name="illustrate" style="width:200px">
			</div>
		</div>	
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>使用状态：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="select" size="1" id="status" name="status" style="width: 200px;height: 31px">
					<option value="0">--停用--</option>
					<option value="1">--启用--</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<button type="submit" class="btn btn-success radius" id="admin-worktime-save" name="admin-worktime-save"><i class="icon-ok"></i> 确定</button>
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
<script type="text/javascript" src="../js/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="../js/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="../js/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="../js/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript">
$(function(){	
	$("#form-system-worktime-add").validate({
		debug:true,
		rules:{
			starttime:{ required:true},
			illustrate:{ required:true,maxLengthInCN:200,remote: "check-illustrate.html"},
			endtime:{ required:true},
		},
		messages: {
			starttime:{ required: "上班时间不能为空"},
			illustrate:{ required: "班次名称内容不能为空",maxLengthInCN:"班次名称字段不能超过200个字符",remote: "班次名称内容不能重复"},
			endtime:{ required:"下班时间不能为空"},
		},
		onkeyup:false,
		focusCleanup:false,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
					dataType : "json",
					success: function(data){
						if(data.head){
							layer.msg("作息时间添加成功!",{icon:1,time:1000},function(){
								window.parent.location.reload();
								var index = parent.layer.getFrameIndex(window.name);
								parent.layer.close(index);
							});
						} else {
							if(data.body == 'UNLOGIN'){
								layer.msg("请先登录!",{icon:1,time:1000},function(){
									window.parent.location.href = "../login.html";
								});								
							} else if(data.body == 'PERMISSION_DENIED') {
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
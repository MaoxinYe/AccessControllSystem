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

<title>修改${area}信息</title>
</head>
<body>
<article class="page-container" style="padding-left: 200px">
	<form action="system-area-edit" method="post" class="form form-horizontal" id="form-system-area-edit">
		<c:if test="${flag != 1}">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>上级${area_1}：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="select" size="1" id="supercode" name="supercode" style="width: 200px;height: 31px">
					<option value="0">--请选择--</option>
					<c:forEach var="area1" items="${areaList}">
						<option value="${area1.areacode}" <c:if test="${area1.areacode == yw_area.supercode}">selected="selected"</c:if>>${area1.areaname}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		</c:if>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>${area}编号：</label>
			<div class="formControls col-xs-8 col-sm-3">
				<input type="text" class="input-text" value="${yw_area.areacode}"  id="areacode" name="areacode"placeholder="" readonly="readonly" style="width:200px">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>${area}名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${yw_area.areaname}" placeholder="" id="areaname" name="areaname" style="width:200px">
			</div>
		</div>		
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<button type="submit" class="btn btn-success radius" id="admin-area-save" name="admin-area-save"><i class="icon-ok"></i> 确定</button>
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
	$("#form-system-area-edit").validate({
		debug:true,
		rules:{
			areaname:{ required:true, maxLengthInCN: 20,  
				remote:{
				    depends : function(element) {
				        return element.value !== "${yw_area.areaname}";
				        },
				        param : {
				            url : "check-areaname.html",
				            cache :false
				        },
				   },
			    },
		},
		messages: {
			areaname:{ required: "请输入${area}信息名称", maxLengthInCN: "最多输入20个字符,一个中文两个字符", remote: "${area}名称已被使用"},
		},
		onkeyup:false,
		focusCleanup:false,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
					dataType : "json",
					success: function(data){
						if(data.head){
							layer.msg("${area}信息修改成功!",{icon:1,time:1000},function(){
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
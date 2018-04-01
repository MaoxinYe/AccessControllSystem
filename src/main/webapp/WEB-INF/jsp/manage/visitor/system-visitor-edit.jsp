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

<title>访客信息</title>
</head>
<body>
<article class="page-container">
	<form action="system-visitor-edit" method="post" class="form form-horizontal" id="form-visitor-manage-edit" enctype="multipart/form-data">
		<div class="tabBar cl">
			<span>修改访客信息</span>
		</div>		
		<input type="hidden" value="${visitor.visitorid}" id="visitorid" name="visitorid">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>访客姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${visitor.name}" placeholder="" id="name" name="name" style="width:200px">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">访客性别：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="select" size="1" id="sex" name="sex" style="width: 200px;height: 31px">
					<option value="3" <c:if test="${visitor.sex == 3}">selected="selected"</c:if>>--请选择--</option>
					<option value="1" <c:if test="${visitor.sex == 1}">selected="selected"</c:if>>--男--</option>
					<option value="2" <c:if test="${visitor.sex == 2}">selected="selected"</c:if>>--女--</option>
				</select>
			</div>
		</div>
		<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>访客证件号：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" value="${visitor.credentials}" placeholder="" id="credentials" name="credentials" style="width:200px">
		</div>
		</div>
		<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>访客电话：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off"  value="${visitor.tel}" placeholder="" id="tel" name="tel" style="width:200px">
		</div>
		</div>
		
		<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">访问时间：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" style="width:160px" placeholder="选择访问开始时间" id="startTime" name="startTime" value="${startTime}" onfocus="WdatePicker()" class="input-text Wdate">
         	<%-- --
        	<input type="text" style="width:160px" placeholder="选择访问结束时间" id="endTime" name="endTime" value="${endTime}" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'startTime\')}'})" class="input-text Wdate"> --%>
		</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>受访人员姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${visitor.personnel_name}" placeholder="" id="personnel_name" name="personnel_name" style="width:200px">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>受访人员电话：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${visitor.personnel_tel}" placeholder="" id="personnel_tel" name="personnel_tel" style="width:200px">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">备注：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${visitor.remarks}" placeholder="" id="remarks" name="remarks" style="width:200px">
			</div>
		</div>
		<div class="row cl" >
        <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>访客标准照：</label>
        <div class="formControls col-xs-8 col-sm-5">
            <span class="btn-upload form-group" style="width:520px;">
            <input class="input-text upload-url radius" style="width:200px" type="text" name="uploadfile-1" id="uploadfile-1" readonly><a href="javascript:void();" class="btn btn-primary radius"><i class="iconfont">&#xf0020;</i> 浏览文件</a>
            <input type="file" multiple name="file" id="file" accept="image/jpeg" class="input-file" onchange="PreviewImage(this);">
            </span>
        <div class="c-red">目前本系统仅支持.jpg格式的照片</div>
        </div>
        </div>
        <div class="row cl">
        <label class="form-label col-xs-4 col-sm-3">照片预览：</label>
        <div class="formControls col-xs-8 col-sm-9">
            <div id="imgPreview" style="width:171px;height:210px">
            <img id="img1" src="${session_photoVirtualPath}${visitor.path}" width="171px" height="210px" alt="预览"/>
            </div>
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
<script type="text/javascript" src="../js/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="../js/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="../js/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="../js/jquery.validation/1.14.0/additional-methods.js"></script>
<script type="text/javascript" src="../js/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript">
function PreviewImage(imgFile){
	var path;
	if(document.all){//IE
	    imgFile.select();
	    $("#admin-role-save").focus();
	    path = document.selection.createRange().text;
	    document.getElementById("imgPreview").innerHTML="";
	    document.getElementById("imgPreview").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src=\"" + path + "\")";//使用滤镜效果
	} else {//FF
	    path=window.URL.createObjectURL(imgFile.files[0]);// FF 7.0以上
	    document.getElementById("imgPreview").innerHTML = "<img id='img1' width='171px' height='210px' src='"+path+"'/>";
	}
}

$(function(){
	$("#form-visitor-manage-edit").validate({
		debug:true,
		rules:{
			name:{required:true,maxLengthInCN:50},
			credentials:{maxLengthInCN:30,checkENGNUM:true},
			tel:{required:true,isTel:true},
			company:{maxLengthInCN:200},
			personnel_name:{required:true,maxLengthInCN:50},
			personnel_tel:{required:true,isTel:true},
			remarks:{maxLengthInCN:200},
            file:{extension:"jpe?g"},
		},
		messages: {
			name:{required:"访客姓名不能为空",maxLengthInCN:"访客姓名长度不能大于50个字符"},
			credentials:{maxLengthInCN:"证件号长度不能大于30个字符",checkENGNUM:"证件号只能是字母和数字"},
			tel:{required:"访客电话不能为空",isTel:"请输入正确的电话号码"},
			company:{maxLengthInCN:"访客单位长度不能大于200个字符"},
			personnel_name:{required:"受访人员姓名不能为空",maxLengthInCN:"受访人员姓名长度不能大于50个字符"},
			personnel_tel:{required:"受访人员电话不能为空",isTel:"请输入正确的电话号码"},
			remarks:{maxLengthInCN:"备注长度不能大于200个字符"},
            file:{extension:"只能上传.jpg格式的照片"},
		},
		onkeyup:false,
		focusCleanup:false,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
				    dataType : "json",
					success: function(data){
						if(data.head){
							layer.msg("访客修改成功!",{icon:1,time:1000},function(){
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
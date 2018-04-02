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
<style type="text/css">
        #line-chart {
            height:300px;
            width:800px;
            margin: 0px auto;
            margin-top: 1em;
        }
        .brand { font-family: georgia, serif; }
        .brand .first {
            color: #ccc;
            font-style: italic;
        }
        .brand .second {
            color: #fff;
            font-weight: bold;
        }
    </style>
<title>人员管理</title>
</head>
<body>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-personnel-edit" enctype="multipart/form-data">
		<div class="tabBar cl">
			<span>更新人员信息</span>
		</div>
		<input type="hidden" class="input-text" value="${personnel.personnelid }" id="personnelid" name="personnelid" >
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${personnel.name }" style="width: 200px" placeholder="" id="name" name="name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>性别：</label>
			<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input name="sex" value="1" type="radio" id="sex-1" <c:if test="${personnel.sex == 1 }">checked</c:if> >
				<label for="sex-1">男</label>
			</div>
			<div class="radio-box">
				<input type="radio" id="sex-2" name="sex" value="2" <c:if test="${personnel.sex == 2 }">checked</c:if> >
				<label for="sex-2">女</label>
			</div>
		</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>证件号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${personnel.credentials }" style="width: 200px" placeholder="" id="credentials" name="credentials">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>联系电话：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${personnel.tel }" placeholder="" style="width: 200px" id="tel" name="tel">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>所属${area}：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="select" id="areacode" name="areacode" style="width: 200px;height: 31px">
					<option value="">请选择${area}</option>
					<c:forEach var="area" items="${areaList}">
						<option value="${area.areacode}" <c:if test="${personnel.areacode == area.areacode }">selected</c:if> >${area.areaname}</option>
					</c:forEach>
				</select>
			</div>
		</div>
<%-- 		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>门禁卡编号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${personnel.cardid }" style="width: 200px" placeholder="" id="cardid" name="cardid">
			</div>
		</div> --%>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">详细地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${personnel.address }" style="width: 200px" placeholder="" id="address" name="address">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">备注：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${personnel.remarks }" style="width: 200px" placeholder="" id="remarks" name="remarks">
			</div>
		</div>
		
		<div class="row cl" id="disimg1" style="display:none;">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>照片预览：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<img  width="148" height="168" id="img1" alt="" src="">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>人员照片：</label>
			<div class="formControls col-xs-8 col-sm-9">
			<input type="file" multiple name="file" id="file" style="width:150px" onclick="clear()" onchange="PreviewImage(this);">
			</div>
			<div class="c-red">目前本系统仅支持.jpg格式的照片</div>
		</div>
		<div class="row cl">
        <label class="form-label col-xs-4 col-sm-3">照片预览：</label>
        <div class="formControls col-xs-8 col-sm-9">
            <div id="imgPreview" style="width:171px;height:210px">
            <img id="img1" src="${session_photoVirtualPath}${face.path}" width="171px" height="210px" alt="预览"/>
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
<script type="text/javascript" src="../js/jquery.validation/1.14.0/additional-methods.js"></script>
<script type="text/javascript" src="../js/jquery.validation/1.14.0/validate-methods.js"></script>
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

/*清空拍摄的照片*/
function clear(flag) {
	//清除拍摄的人员照片
	$("#picpath").val("");
}
	/*外聘人员需填写有效日期*/
	function opear() {
		var vs = $("#type").val();
		if(vs != null && vs != ""){
			if (vs==1) {
				document.getElementById("disexpiration").style.display = "none";//隐藏
			} else if (vs==2) {
				document.getElementById("disexpiration").style.display = "none";//隐藏
			} else if (vs==3) {
				document.getElementById("disexpiration").style.display = "";//显示
			}
		} else {
			document.getElementById("disexpiration").style.display = "none";//隐藏
		}
	}
	/*拍照*/
	function personnel_modeling(title, url, w, h) {
		url = 'personnel-modeling.html';
		layer_show(title, url, 800, 650);
	}
	
	
	$(function() {
		$("#form-personnel-edit").validate(
					{
						debug : true,
						rules : {
							name : {
								required : true,
								maxLengthInCN : 20
								
							},
							credentials : {
								required : true,
								maxLengthInCN : 30,
								checkENGNUM : true
							},
							tel : {
								required : true,
								maxLengthInCN : 22,
								isTel : true
							},
							areacode : {
								required : true,
							},
							type : {
								required : true,
							},
							expirationdate : {
								required : true,
							},
						},
						messages : {
							name : {
								required : "请输入姓名",
								maxLengthInCN : "最多输入20个字符"
							},
							credentials : {
								required : "请输入证件号",
								maxLengthInCN : "最多输入30个字符",
								checkENGNUM : "请输入正确的英文字母和数字"
							},
							tel : {
								required : "请输入联系电话",
								maxLengthInCN : "最多输入22个字符",
								isTel : "请输入正确手机号码或电话号码"
							},
							areacode : {
								required : "所属${area}不能为空",
							},
							type : {
								required : "人员类型不能为空",
							},
							expirationdate : {
								required : "截止日期不能为空",
							},
								
						},
						onkeyup : false,
						focusCleanup : false,
						success : "valid",

						submitHandler : function(form) {
							$(form).ajaxSubmit(
								{
									url : 'personnel-edit',
									dataType : "json",
									success : function(data) {
									if (data.head) {
										layer.msg("更新成功!",{icon : 1,time : 1000},
										function() {parent.query();
										var index = parent.layer.getFrameIndex(window.name);
										parent.layer.close(index);
										});
									} else {
									if (data.body == 'UNLOGIN') {
									layer.msg("请先登录!",{icon : 1,time : 1000},
										function() {
											window.parent.location.href = "../login.html";
										});
								} /* else if (data.body == 'PERMISSION_DENIED') {
									layer.msg("您无操作权限!",{icon : 2,time : 2000});
									} */ else {
									layer.msg(data.body,{icon : 2,time : 2000});
								}
							}
						},
						error : function(XmlHttpRequest,textStatus,errorThrown) {
							layer.msg('系统错误!', {icon : 1,time : 1000});
						}
				});
			}
		});
	});
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
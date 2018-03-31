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
<script >
	
	var list=[];
	var i=0;
	//var tmp="${mytmp}"
	<c:forEach var="user" items="${departmentList}">
		list[i]={"supercode":"${user.supercode}","code":"${user.areacode}","name":"${user.areaname}"}
		i++
	</c:forEach>
	/* <c:forEach var="user" items="${departmentList}">
	list[i].username=${user.areacode};
    i++;
	</c:forEach> */
	//alert(tmp)
</script>
<title>门禁闸机管理</title>
</head>
<body>
<article class="page-container">
	<form action="system-gates-add" method="post" class="form form-horizontal" id="form-gates-manage-add">
		<div class="tabBar cl">
			<span>添加门禁闸机信息</span>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>所属${area}：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="select" size="1" id="areacode" name="areacode" style="width: 200px;height: 31px" onchange="myChange(this.id)">
					<option value="">--请选择--</option>
					<c:forEach var="area" items="${areaList}">
						<option value="${area.areacode}">${area.areaname}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>所属部门：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="select" size="1" id="areacode2" name="areacode2" style="width: 200px;height: 31px">
					<option value="">--请选择--</option>
					<c:forEach var="area" items="${departmentList}">
						<c:if test="${area.supercode eq param.areacode }">
							<option value="${area.areacode}">${area.areaname}</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>硬件说明：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="请输入硬件说明信息" id="gatesname" name="gatesname" style="width:200px">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>终端类型：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="select" size="1" id="type" name="type" style="width: 200px;height: 31px">
					<option value="">--请选择--</option>
					<option value="1">门禁</option>
					<option value="2">闸机</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>作用：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="select" size="1" id="inorout" name="inorout" style="width: 200px;height: 31px">
					<option value="">--请选择--</option>
					<option value="1">进</option>
					<option value="2">出</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>详细位置信息：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="请输入详细位置信息" id="address" name="address" style="width:200px">
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
function myChange(x)
{
var y=document.getElementById(x).value
var sid = document.getElementById("areacode2");
sid.innerHTML = ""
for(j = 0; j < list.length; j++) {
	if(parseInt(y)==parseInt(list[j].supercode))
	{
		var tmp=list[j].name
		var val=list[j].code
		//alert(tmp)
		sid.add(new Option(tmp,val)) 
	}
	   //sid.option[j] = new Option(tmp,j);
	   
} 

}

$(function(){
	$("#form-gates-manage-add").validate({
		debug:true,
		rules:{
			areacode:{ required:true, },
			type:{ required:true, },
			inorout:{ required:true, },
			gatesname:{ required:true, maxLengthInCN: 50,
				remote:{
                    url: "check-gatesname.html",
                    type: "get",
                    cache: false,
                    data: {
                    	areacode: function () { return $("#areacode").val();},
                    	gatesname: function () { return $("#gatesname").val();}
                     },
                	},
                },
			address:{ required:true, maxLengthInCN: 200},
		},
		messages: {
			areacode:{ required: "所属${area}不能为空！", },
			type:{ required: "终端类型不能为空"},
			inorout:{ required: "作用不能为空"},
			gatesname:{ required: "硬件说明不能为空！", maxLengthInCN: "硬件说明不能超过50个字符！",remote:"同${area}下硬件说明不能相同"},
			address:{ required: "位置信息不能为空！", maxLengthInCN: "位置信息不能超过200个字符！" },
		},
		onkeyup:false,
		focusCleanup:false,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
				    dataType : "json",
					success: function(data){
						if(data.head){
							layer.msg("门禁闸机添加成功!",{icon:1,time:1000},function(){
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
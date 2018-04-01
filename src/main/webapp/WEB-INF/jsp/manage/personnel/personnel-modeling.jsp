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
<body >
<article class="page-container">
		<div class="tabBar cl">
			<span>拍摄标准照片</span>
		</div>
		
		<div class="row c1" align="center">
		  
				<div class="row c1" align="">
					<object classid="CLSID:3CE7A2FD-1AE9-40B0-83DB-4A32CEB2CF30" height="400px" id="CANONCAM"> 
					<PARAM NAME="_Version" VALUE="65536">
						<PARAM NAME="_ExtentX" VALUE="19045">
						<PARAM NAME="_ExtentY" VALUE="10022">
						<PARAM NAME="_StockProps" VALUE="0">
					</object>							  
				  </div>
				
					<div class="row c1">
					<p id="cam_connect" class="btn btn-primary"> 连接摄像头</p>
					<p id="cam_takepic" class="btn btn-primary"><i class="icon-camera"></i> 拍摄标准照</p>
					<p id="cam_croppicture" class="btn btn-primary"><i class="icon-cut"></i> 裁剪照片</p>
					<p id="cam_upload" class="btn btn-primary"><i class="icon-cut"></i> 上传标准照</p>	
					<input id="bbh" name="bbh" type="hidden" value="1">
					</div>
			
           </div>
		
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

$().ready(function() {
	
	if($("#CANONCAM")[0].object == null)alert("未安装控件，请到人员列表中下载并安装海量照片质量检测控件。");
	 $(window).unload(function(){
		var result = $("#CANONCAM")[0].CAMNON_IFCONNECT();
		if(result==1){
			$("#CANONCAM")[0].CAM_CONNECT();
		}				
	});	 	
	$("#cam_connect").click(function (){
		$("#CANONCAM")[0].CAM_CONNECT();
		var result = $("#CANONCAM")[0].CAMNON_IFCONNECT();
		if(result==1){
			$(this).text(" 断开相机");
		}else{
			$(this).text(" 连接相机");
		}
	});
	$("#cam_takepic").click(function (){
		var result = $("#CANONCAM")[0].CAMNON_IFCONNECT();
		if(result==1){
			$("#CANONCAM")[0].CAM_TakePic();					 
		};
	});
	$("#cam_croppicture").click(function (){
		$("#CANONCAM")[0].CAM_CROP_PICTURE();
	});
	
	$("#cam_upload").click(function(){
		var base64;
		try{
			var bbh = $("#bbh").val();
			var version = $("#CANONCAM")[0].GetOCXVersion();
			if(version == bbh){
				var str = $("#CANONCAM")[0].GetQuaDetect();
				if(str != 0){
					alert('照片质量检测不合格,不能上传。'); 
					return false;
				}else{
					base64 = $("#CANONCAM")[0].ImageToString();
				}
			}else{
				alert("请下载最新版海量质量检测控件");
				return false;
			}
		}catch(err){
			alert("请下载并安装新版海量照片质量检测控件");
			return false;
		}
		if(base64!=null){
			$(window.parent.$("#picpath1").val(base64));
			$(window.parent.$("#img1").attr("src","data:image/jpg;base64,"+base64));
			$(window.parent.$("#file1").val(""));
			$(window.parent.$("#disimg1").attr("style","display:'';")); 
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		}else{
			alert("请重新拍照或选择照片！");
		}
	});
});
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
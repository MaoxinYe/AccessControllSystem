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
<title>技术人员工作任务统计系统</title>
</head>
<body>
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"> <p class="logo navbar-slogan f-l mr-10 hidden-xs" style="font-size: 18px">技术人员工作任务统计系统</p>
		<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
			<ul class="cl">
				<%--这里不登录的  <li class="dropDown dropDown_hover">
					<a href="#" class="dropDown_A">${session_username} <i class="Hui-iconfont">&#xe6d5;</i></a>
					<ul class="dropDown-menu menu radius box-shadow">
						<li><a href="javascript:;" onClick="myselfinfo()">个人信息</a></li>
						<li><a href="javascript:;" onClick="mypassword(${session_userid})">修改密码</a></li>
						<li><a href="../sign-out.html">退出</a></li>
					</ul>
				</li> --%>
				<li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
					<ul class="dropDown-menu menu radius box-shadow">
						<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
						<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
						<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
						<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
						<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
						<li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
					</ul>
				</li>
			</ul>
		</nav>
	</div>
</div>
</header>
<aside class="Hui-aside">
	<div class="menu_dropdown bk_2">
	<c:forEach var ="module" items="${menuList}">
		<dl id="menu-admin">
			<dt><i class="Hui-iconfont">&#xe62d;</i> ${module.modulename}<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<c:forEach var ="menu" items="${module.module}">
						<li><a data-href="${menu.modulepage}" data-title="${menu.modulename}" href="javascript:void(0)">${menu.modulename}</a></li>
						<%--  <c:choose>
							<c:when test="${session_role == 1001}">
								<li><a data-href="${menu.modulepage}" data-title="${menu.modulename}" href="javascript:void(0)">${menu.modulename}</a></li>
							</c:when>
							<c:otherwise>
								<c:if test="${module.modulecode != 100101 || module.modulecode != 100102}">
									<li><a data-href="${menu.modulepage}" data-title="${menu.modulename}" href="javascript:void(0)">${menu.modulename}</a></li>
								</c:if>
							</c:otherwise>
						</c:choose> --%>
					</c:forEach>					
				</ul>
			</dd>
		</dl>
	</c:forEach>
</div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active">
					<span title="我的首页" data-href="manage/welcome.html">我的首页</span>
					<em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="welcome.html"></iframe>
		</div>
	</div>
</section>

<div class="contextMenu" id="Huiadminmenu">
	<ul>
		<li id="closethis">关闭当前 </li>
		<li id="closeall">关闭全部 </li>
	</ul>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="../js/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="../js/layer/2.4/layer.js"></script>
<script type="text/javascript" src="../static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="../static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="../js/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
<script type="text/javascript">
$(function(){
	$("#min_title_list li").contextMenu('Huiadminmenu', {
		bindings: {
			'closethis': function(t) {
				console.log(t);
				if(t.find("i")){
					t.find("i").trigger("click");
				}		
			},
		}
	});
});
/*个人信息*/
function myselfinfo(id){
	layer.open({
		type: 2,
		area: ['620px','400px'],
		fix: false, //不固定
		maxmin: true,
		shade:0.4,
		title: '修改信息',
		content: 'system-user-edit.html'
	});
}
/*修改密码*/
function mypassword(id){
	layer.open({
		type: 2,
		area: ['620px','400px'],
		fix: false, //不固定
		maxmin: true,
		shade:0.4,
		title: '修改密码',
		content: 'user-password-edit.html?userid='+id
	});
}
function query() {
    window.location.reload;
}
</script>
</body>
</html>
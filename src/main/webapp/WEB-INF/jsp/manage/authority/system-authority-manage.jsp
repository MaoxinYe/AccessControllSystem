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

<title>权限管理</title>
</head>
<body>
<article class="page-container">
		<form action="system-authority-edit" method="post" class="form form-horizontal" id="form-system-authority-edit">
		<div class="tabBar cl">
			<span>权限设置</span>
		</div>
		<table border="0" width="800">
		<%-- <% Integer cnt=(Integer)request.getAttribute("size");
		int[] count=new int[cnt];
		int index=0;%> --%>
		<input type="hidden" name="roleid" value="${roleid }">
	 	<c:forEach var ="module" items="${moduleList}" varStatus="tag">
	 	
	 	<%-- <%count[index]=0; %> --%>
	 		
	 		<tr>
	 			<td width="800" height="50">
	 			<font size="5" face="arial" color="red">模块名称：${module.modulename }</font>
	 			<input type="hidden" name="module" value="${module.moduleid }">
	 			</td>
	 		</tr>
	 		<tr>
	 			<td width="800" height="50">
		 			<c:forEach var ="menu" items="${menuList}" varStatus="menutag">
					<c:if test="${module.modulecode eq menu.supercode }">
						<%-- <c:if test="${menu.modulelevel eq 0 }">
							<% ++count[index]; %>
						</c:if> --%>
		         		<input type="checkbox" name="menu"  value="${menu.moduleid }" <c:if test="${menu.modulelevel eq 0 }"> checked=1</c:if> ><font size="3" face="arial" color="red">${menu.modulename }</font>
		         	</c:if>
		         	</c:forEach>
	 			</td>
	 		</tr>
	 		<%-- <%index++; %> --%>
	 	
	 	<%-- <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">模块名称：</label>
			 模块名称：${module.modulename }
			 
			 <div class="formControls col-xs-4 col-sm-3">
				${module.modulename } 
			</div> 
		</div>
			<div class="row cl">
			<c:forEach var ="menu" items="${menuList}" varStatus="menutag">
				<c:if test="${module.modulecode eq menu.supercode }">
	         		<input type="checkbox" name="menu${tag.count }"  value="${menu.modulecode }">${menu.modulename }
	         </c:if>
	         </c:forEach>
	         </div> --%>
        </c:forEach>
        
      <%--  <%for(int i=0;i<index;i++){ %>
       		<input type="hidden" name="count" value="<%= count[i]%>">
       <%} %> --%>
       <%--  <%session.setAttribute("count", count); %> --%>
        <tr>
	 			<td width="800" height="50">
	 			<button type="submit" class="btn btn-success radius" id="admin-role-save" name="admin-role-save"><i class="icon-ok"></i> 确定</button>
	 			</td>
	 	</tr>
        </table>
        
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
	$("#form-system-authority-edit").validate({
		debug:true,
		rules:{
			
		},
		messages: {
			
		},
		onkeyup:false,
		focusCleanup:false,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
					dataType : "json",
					success: function(data){
						if(data.head){
							layer.msg("权限修改成功!",{icon:1,time:1000},function(){
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
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="static/Wopop_files/style_log.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="static/Wopop_files/style.css">
<link rel="stylesheet" type="text/css" href="static/Wopop_files/userpanel.css">
<link rel="stylesheet" type="text/css" href="static/Wopop_files/jquery.ui.all.css">

</head>
<body class="login" mycollectionplug="bind"> 
<div class="login_m">
<div class="login_logo" align="center"><h3>人脸识别门禁系统</h3></div>
<div class="login_boder">

<div class="login_padding" id="login_model">

  <h2>用户名</h2>
  <label>
    <input type="text" id="username" class="txt_input txt_input2" onfocus="if (value ==&#39;输入您的用户名&#39;){value =&#39;&#39;}" onblur="if (value ==&#39;&#39;){value=&#39;输入您的用户名&#39;}" value="输入您的用户名">
  </label>
  <h2>密码</h2>
  <label>
    <input type="password" name="textfield2" id="userpwd" class="txt_input" onfocus="if (value ==&#39;******&#39;){value =&#39;&#39;}" onblur="if (value ==&#39;&#39;){value=&#39;******&#39;}" value="******">
  </label>
 
 

 
  <div class="rem_sub">
  <div class="rem_sub_l">
  
   </div>
    <label>
      <input type="submit" class="sub_button" name="button" id="button" value="登陆" style="opacity: 0.7;">
    </label>
  </div>
</div>



<!--login_padding  Sign up end-->
</div><!--login_boder end-->
</div><!--login_m end-->
 <br> <br>


</body>
</html>
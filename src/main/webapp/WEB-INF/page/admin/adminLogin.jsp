<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>/welcome">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>系统后台管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<c:import url="public/p-css.jsp"></c:import>
<style type="text/css">
.modal-body{max-height: 580px;}
body{background: url('../images/geometry2.png') repeat}
</style>
</head>

<body>

	<div class="container-fluid-full">
		<div class="row-fluid">

			<div class="login-box">
					<div class="box-header" style="padding:20px 25px 10px 25px;margin-bottom:20px;">
						<a href="#">后台登录</a>
					</div>
					<div style="padding:10px 50px;"><img src="../images/getmore_login.png" style="width:100%;"/></div>
					<form class="form-horizontal" action="../login.htmls" method="post" id="loginForm">
							<!-- 错误提示 -->
							<div class="alert alert-error" style="margin:10px 10px;${info==null?"display:none":""}">
								<button type="button" class="close" data-dismiss="alert">×</button>
								<span class="content">${info }</span>
							</div>
							<div class="input-prepend" title="Username">
								<span class="add-on"><i class="halflings-icon user"></i> </span>
								<input name="orignUrl" type="hidden" value="${param.orignUrl }"/>
								<input class="input-large span10" required="required" name="loginname" id="loginname" type="text" placeholder="请输入用户名" maxlength="30"/>
							</div>
							<div class="clearfix"></div>

							<div class="input-prepend" title="Password">
								<span class="add-on"><i class="halflings-icon lock"></i> </span>
								<input class="input-large span10" required="required" name="password" id="password" type="password" placeholder="请输入密码" maxlength="30"/>
							</div>
							<div class="clearfix"></div>
							<div class="button-login">	
								<button type="submit" class="btn btn-primary btn_submit" id="btn_submit">登录</button>
							</div>
							<div class="clearfix"></div>
					</form>
					<hr>
					<!-- <h3>找回密码</h3> -->
				</div><!--/span-->
			
		</div>
	</div>
	
	<div class="clearfix"></div>
<c:import url="public/p-javascript.jsp"></c:import>
	<!-- start: JavaScript-->
	<script type="text/javascript">
		$("#loginForm").on("submit",function(){
			var loginname=$("#loginname").val();
			var password=$("#password").val();
			if(loginname&&loginname.length>5&&password&&password.length>5){
				$("#loginForm").submit();
			}else{
				$(".alert .content").text("请输入正确的密码以及账号！");
				$(".alert").fadeIn("fast");
			}
		});
	</script>
</body>
</html>

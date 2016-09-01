<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="zh">
<head>
<base href="<%=basePath%>">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/style.css"></link>
	<script src="js/jquery.min.js"></script>
	<title>后台管理系统</title>
	
</head>
<body id="landing"> 
	<div class="loginform">
		<div class="logo">
			<img src="images/logo-login.png">
		</div>
		
		<form action="adminLogin/login.htmls" id="loginForm" method="post">
		<div class="formarea formarea-pad">
			<c:if test="${not empty  message}">
				<!-- 错误提示 -->
					<div class="notice error closable">
							<span class="info">${message }</span>
							<div class="close"></div>
						</div>
					<!--  -->
				</c:if>
			<div class="form-row withicon">
			<input type="text" name="username_login" id="username" placeholder="登录名" maxlength="20">
				<label for="username"><i class="icon-person"></i></label>
			</div>
			
			<div class="form-row withicon">
				<input type="password" name="password_login" id="password" placeholder="密码"><label for="password">
				<i class="icon-vpn_key"></i></label>
				<div class="forget" title="找回密码">
				<i class="icon-help"></i>
				</div>	
			</div>
			
			<c:if test="${not empty sessionScope['userLoginTime']&&sessionScope['userLoginTime']>=3}">
			<div class='form-row withicon option'>
				<input type='text' name='loginCode' id='verifycode' maxlength="4" placeholder='验证码'>
				<label for='verifycode'><i class='icon-security'></i></label>
					<div class='codeimg'>
					<img src='adminLogin/codeimg_100_26_loginCode.htmls' onClick="this.src='adminLogin/codeimg_100_26_loginCode.htmls?'+new Date()"/>
					</div>
				</div>
			</c:if>
			</div>
			<div class="submitarea">
				<input type="checkbox" name="remember" value="yes" id="remember"><label for="remember">保存登陆状态</label>
				<div class="btnarea">
					<button class="btn-nor btn">登陆</button>
				</div>
			</div>
		</form>

	</div>
	
</body>
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#loginForm").on("submit",function(e){
		var username=$("#username").val();
		var password=$("#password").val();
		var verifycode=$("#verifycode").val();
		if(!username){
			showError("请输入登录名!");$("#username").addClass("error").focus();
			return false;
		}else if(!password){
			showError("请输入密码！");$("#password").addClass("error").focus();
			return false;
		}else if($("#verifycode")[0]&&!verifycode){
			showError("请输入验证码");$("#verifycode").addClass("error").focus();
			return false;
		}else if(!verifycode.match(/^[\d\w]{4}$/)){
			showError("请输入正确格式的验证码");$("#verifycode").addClass("error").focus();
			return false;
		}
		return true;
	});
	
	$('.formarea').on('keydown', 'input.error',function(event) {
		$(this).removeClass('error');
	});

	$('.formarea').one('click','.notice.closable .close', function(event) {
		$(this).parent('.notice.closable').hide();
	});
	
	
	
	
	
	function showError(message){
		var $errDiv=$(".notice.closable")[0]?$(".notice.closable"):
			$("<div class='notice error closable'><span class='info'></span><div class='close'></div></div>");
		$errDiv.find(".info").text(message);
		$(".formarea").prepend($errDiv);
		$errDiv.show();
	}
	
});

</script>
</html>
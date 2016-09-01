<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="sf" uri="/sf" %>
<!DOCTYPE html>
<html lang="en">
<!-- start: Header -->
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- start: Header -->
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<%=basePath%>css/style.css"></link>
<title>系统后台管理 </title>


</head>
<!-- start: Header -->
<body>
	<div id="main-container" class="menu-collapsed">
		<div class="left-container dynmic-menu" >
			</div>
			<div class="right-container anim">
			<h1>这是欢迎页面</h1>
		</div>
	</div>
	
	<div class="overlay ol-dark" style="display:none;" id="modelContainer">
		
	</div>
		
	<div class="overlay ol-dark" style="display: none;" id="batch-alert">
		<div class="alert">
			<div class="icon-animation">
					<div class="loading"></div>
			
			<div class="alerttxt">
				数据上传中...可能需要较多时间，请耐心等待！
			</div>
			
		</div>
</div>
</div>

	<div class="overlay ol-dark hide"  id="ajaxInfo">
		<div class="alert">
			<div class="icon-animation">
					<div class="loading"></div>
			
			<div class="alerttxt">
				查询中！
			</div>
			
		</div>
</div>
</div>
	
	<c:import url="public/p-javascript.jsp"></c:import>
	<script type="text/javascript" src="js/call/uc-2.0.1.js"></script>
	<script src="js/jquery.datetimepicker.full.js"></script>
</body>
</html>

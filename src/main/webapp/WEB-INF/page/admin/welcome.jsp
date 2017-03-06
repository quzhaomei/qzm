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
<link rel="stylesheet" href="/static/css/style.css"></link>
<title>系统后台管理 </title>


</head>
<!-- start: Header -->
<body>
<input type="hidden" id="basePath" value="<%=basePath%>"/>
	<div id="main-container" class="menu-collapsed">
	<div class="left-container dynmic-menu" >
			</div>
			<div class="right-container anim">
			<c:if test="${empty real_child_id}">
			<header>
				<h1 class="t-rd">控制台</h1>
				<div class="notification">
					<span class="help">
						 <a href="/static/help.html" target="_blank">操作帮助</a>
					</span>
				</div>
				</header>
				
				<div id="maincontents">
					<div class="row dailycounts">
						
						<c:forEach items="${notices }" var="notice">
							<div class="col-03">
								<div class="panel">
									<div class="panel-content">
									<a href="${notice.url }">
										${notice.title }
										<div class="counts">
											${notice.message }
										</div>
									</a>
									</div>
								</div>
							</div>
						</c:forEach>
						</div>
						
						<div class="row pendinglist">
					
					<c:if test="${needDo!=null}">
						<div class="col-04">
							<div class="panel">
								<div class="panel-title">
									<span>共<em>${needDo.count }</em>个待处理客户</span>
									<div class="btnarea-right">
											<button class="btn btn-sm" href="myWork/myWork-customer-index.htmls?ajaxTag_=1">
												去回访 <i class="13"></i>
											</button>
										</div>
								</div>
								<div class="panel-content">
										<table>
											<thead>
												<tr>
													<th><span>客户手机</span></th>
													<th><span>客户姓名</span></th>
													<th><span>录入时间</span></th>
												</tr>
											</thead>
											
											
											<tbody class="strip">
											<c:forEach items="${needDo.param }" var="need">
												<tr>
													<td>${need.phone }</td>
													<td>${need.name }</td>
													<td><sf:longtime data="${need.createDate }" pattern="yyyy-MM-dd HH:mm"></sf:longtime> </td>
												</tr>
											</c:forEach>
											</tbody>
	
										</table>
								</div>
							</div>
						</div>
					
					</c:if>	
					
					
					<c:if test="${needHouse!=null }">
					
					<div class="col-04">
						<div class="panel">
							<div class="panel-title">
								<span>共<em>${needHouse.count }</em>个待发布需求的房产</span>
								<div class="btnarea-right">
										<button class="btn btn-sm" href="myWork/myWork-customer-index.htmls?ajaxTag_=1">
											去发布需求 <i class="13"></i>
										</button>
									</div>
							</div>
							<div class="panel-content">
									<table>
										<thead>
											<tr>
												<th><span>房型</span></th>
												<th><span>房产地址</span></th>
												<th><span>发布时间</span></th>
											</tr>
										</thead>
										
										
										<tbody class="strip">
										<c:forEach items="${needHouse.param }" var="house">
											<tr>
												<td>${house.houseStyle.name }</td>
												<td>${house.houseLocation }</td>
												<td><sf:longtime data="${house.createDate }" pattern="yyyy-MM-dd HH:mm"></sf:longtime> </td>
											</tr>
											</c:forEach>
										</tbody>

									</table>
							</div>
						</div>
					</div>
				</c:if>

	<c:if test="${needRequired!=null }">
					<div class="col-04">
						<div class="panel">
							<div class="panel-title">
								<span>共<em>${needRequired.count }</em>待派单需求</span>
								<div class="btnarea-right">
										<button class="btn btn-sm" href="myWork/hard-index.htmls?ajaxTag_=1">
											硬装 <i class="13"></i>
										</button>
										<button class="btn btn-sm" href="myWork/soft-index.htmls?ajaxTag_=1">
											软装 <i class="13"></i>
										</button>
										
									</div>
							</div>
							<div class="panel-content">
									<table>
										<thead>
											<tr>
												<th><span>房产地址</span></th>
												<th class="td-label"><span>类型</span></th>
												<th><span>发布时间</span></th>
											</tr>
										</thead>
										
										
										<tbody class="strip">
										<c:forEach items="${needRequired.param }" var="house">
											<tr>
												<td>${house.houseLocation }</td>
												<td class="td-label">
												<c:choose>
													<c:when test="${empty house.houseId }">
														<span class="label">硬装</span>
													</c:when>
													<c:otherwise>
														<span class="label label-green">软装</span>
													</c:otherwise>
												</c:choose>
												</td>
												<td><sf:longtime data="${house.createDate }" pattern="yyyy-MM-dd HH:mm"></sf:longtime> </td>
											</tr>
										</c:forEach>
											
										</tbody>


									</table>
							</div>
						</div>
					</div>
					
					</c:if>
					
				</div>
						</div>
						
						</c:if>
						
				</div>
		</div>
		
<div class="sf-modal modal modal-sm modal-profile" style="display:none;" id="updatePriModal">
			<div class="panel">
				<div class="panel-title">
					<span>账号信息修改</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="关闭窗口">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					<div class="formarea formarea-pad">

								<div class="formsection">
									<div class="userpic">
										<input type="file" name="userimg" id="welcome_userimg" accept="image/png, image/jpeg, image/gif">

										<label for="userimg">
											<img id="userimg_ref" src="${empty loginUser.avatar?'/static/images/avatar.jpg':loginUser.avatar }" alt="">
											<i class="icon-pencil">
												
											</i>
										</label>
										
									</div>

									<div class="notice error closable" style="display:none;" id="error-div">
										<span>昵称必填</span>
									<div class="close"></div>
								</div>
									
				<div class="form-row withicon">
				<input type="text" name="username" id="welcome_username" 
				value="${loginUser.loginname }" disabled><label for="welcome_username"><i class="icon-person"></i></label><span></span>
			</div>

			<div class="form-row withicon">
										<input type="text" placeholder="昵称" value="${loginUser.nickname }" id="welcome_nickname" autocomplete="off" required><label for="welcome_nickname"><i class="icon-bubble_chart"></i></label>
										<span></span>
									</div>

			<div class="form-row withicon">
				<input type="text" name="phone" id="welcome_cellphone" value="${loginUser.phone }" placeholder="电话号码" required maxlength="11"><label for="welcome_cellphone"><i class="icon-phone_android"></i></label><span></span>
			</div>
			<div class="form-row withicon">
				<input type="password" id="new_password" name="password" value="" placeholder="新密码/不改则置空" required><label for="new_password"><i class="icon-vpn_key"></i></label><span></span>
				
			</div>
									
								</div>
								
					</div>
					
				</div>
				<div class="panel-foot">
					<div class="btnarea-center">
						<button class="btn btn-nor btn-iconleft" id="send_user_edit">
									<i class="icon-check">
									</i>
									确认修改
						</button>
					</div>
				</div>
			</div>
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
	<script type="text/javascript">
	var real_child_id="${real_child_id}";
	var real_parent_id="${real_parent_id}";
	
	$(function(){
		
		$("#welcome_userimg").on("change",function(){
			var _this=this;
			var formData = new FormData(); 
			formData.append("file",this.files[0]);
			$.ajax({
				   url: 'welcome/uploadSource/user_photo.htmls',
				   type: 'POST',
				   cache: false,
				   data: formData,
				   dataType:"json",
				   processData: false,
				   contentType: false ,
				   success:function(json){
					   if(json.error==0){
						   $("#userimg_ref").attr("src",json.url);
						   $("#userimg_ref")[0].url=json.url;
						  _this.value="";
					   }else{
						   alert(json.message);
					   }
					   _this.value="";
				  }
			});
		});
		
		function myalert(msg,$aim){
			$("#error-div span").text(msg);
			$aim.addClass("error");
			$("#error-div").show();
		}
		$("#send_user_edit").on("click",function(){
			var avatar=$("#userimg_ref")[0].url;
			var nickname=$("#welcome_nickname").val();
			var phone=$("#welcome_cellphone").val();
			var password=$("#new_password").val();
			if(!nickname){myalert("请输入昵称！",$("#welcome_nickname"));return;}
			if(!phone){myalert("请输电话号码！",$("#welcome_cellphone"));return;}
			var param={};
			param.nickname=nickname;
			param.phone=phone;
			if(avatar){param.avatar=avatar;}
			if(password){param.password=password;}
			$.post("welcome/update_self.htmls",param,function(json){
				alert(json.message);
				if(json.status==1){
					window.location.reload();
				}
			},"json")
			
		});
		
		$("body").on('mouseenter','[title]', function(event) {
			event.preventDefault();
			var tooltipsTxt = $(this).attr('title');
			$(this).removeAttr('title');
			if($(this).css('position') === "static") {
				$(this).css('position','relative');
			}
			$(this).append("<div class='tooltips'>"+tooltipsTxt+"</div>");
			var tooltips = $('.tooltips'),
				tooltipsWidth = tooltips.width();
				
			$(this).mouseleave(function(event) {
				event.preventDefault();
				$(this).attr("title",tooltipsTxt).children('.tooltips');
				$(this).children('.tooltips').remove();
				
			});
		});
		
	});
	</script>
</body>
</html>

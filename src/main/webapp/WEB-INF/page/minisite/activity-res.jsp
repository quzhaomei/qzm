<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="initial-scale=1, width=device-width, 
	height=device-height, maximum-scale=1, minimum-scale=1, user-scalable=no">
	<title>${activity.name }</title>

	<script type="text/javascript" src="/static/js/jquery.min.js"></script>
  	<script type="text/javascript" src="/static/js/jquery.fullPage.min.js"></script>
  	<link href='/static/css/signup.css' rel='stylesheet' type='text/css'>
	<link href='/static/css/jquery.fullPage.css' rel='stylesheet' type='text/css'>
	<script type="text/javascript">
			window.onload = function(){
			   $('body').addClass('loaded');
			   $('#fullpage').fullpage();
		    	$('.arrow').on('click', '', function(){
				  $.fn.fullpage.moveSectionDown();
				});
		    };		
	</script>
</head>

<body>

<!-- 预加载文件 -->
	<div class="imgpreload hide">
    <!-- 这里是所有背景图片，有多少上多少 -->
     <c:forEach items="${activity.images }" var="image" varStatus="status">
		<img src="${image.path}">
     </c:forEach>
	</div>

	<!-- 预加载页面开始 -->
	<div id="loader-wrapper">
      <div id="loader">
        <div class="spinner">
          <div class="dot1"></div>
          <div class="dot2"></div>
        </div>
      </div>
    
      <div class="loader-section section-left"></div>
      <div class="loader-section section-right"></div>
   	</div>
   	<!-- 预加载页面结束 -->

<div id="fullpage">
    <!-- 这里是页面背景图 -->
    <c:forEach items="${activity.images }" var="image" varStatus="status">
    	<c:if test="${status.index+1!=fn:length(activity.images) }">
		    <div class="section" id="section${status.index }">
		        <!-- 这里是每页背景图 -->
		    	<div class="page" style="background-image: url('${image.path}');"> 
		    	</div>
		    </div>
    	</c:if>
    	<c:if test="${status.index+1==fn:length(activity.images) }">
		   <div class="section" id="section3">
      <div class="page" style="background-image: url('${image.path}');">
          <div class="signform">
            <input type="text" id="name" name="name" placeholder="您的姓名" maxlength="20">
            <input type="text" id="phone" name="phone" placeholder="您的联系手机" maxlength="20">
            <input type="hidden" id="activityId" value="${activityId }">
            <input type="hidden" id="channelId" value="${channelId }">
            <button type="button" id="btnSend">预约参加</button>
          </div>
        </div>
    </div>
    	</c:if>
    </c:forEach>
   
</div>
 

<div class="arrow animated bounce">
	
</div>


<script type="text/javascript">
$(function(){
	$("#btnSend").on("click",function(){
		var name=$("#name").val();
		var phone=$("#phone").val();
		var activityId=$("#activityId").val();
		var channelId=$("#channelId").val();
		if($("#name")[0]&&!name){alert("请输入您的姓名！");return;}
		if($("#phone")[0]&&!phone){alert("请输入您的电话号码！");return;}
		if(phone&&!phone.match(/^\d+$/)){alert("请输入正确的电话号码！");return;}
		var param={};
		if(name){param.name=name;}
		if(phone){param.phone=phone;}
		if(activityId){param.activityId=activityId;}
		if(channelId){param.channelId=channelId;}
		var _this=this;
		$(_this).attr("disabled","disabled");
		$.post("../doRes.htmls",param,function(json){
			$(_this).removeAttr("disabled");
			if(json.status==0){
				alert(json.message)
			}else{
				alert(json.message)
			}
		},"json")
	})
})
</script>	

</body>
</html>
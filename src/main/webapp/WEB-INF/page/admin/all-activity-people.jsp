<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
				<h1 class="t-rd">活动报名信息</h1>
			</header>
			<div id="maincontents">
			</div>
<script type="text/javascript">


var props={
			title:"活动报名列表",
			url:"activity/all-activity-people.htmls?operator=list",
			whole:false,
			head:[{name:"name",title:"客户名称",search:true},
			      {name:"phone",title:"客户手机",className:"td-mobile",search:true},
			    
			      {name:"createDate",title:"报名时间",className:"td-datetime",
						style:"date",format:"yyyy-MM-dd HH:mm",sort:true,remove:true,sort_default:true},
				  {name:"activityDTO",title:"活动",style:"label",className:"td-label",value:"activityId",key:"activityId",
						    	  group:[
											<c:forEach items="${activitys }" var="temp" varStatus="status">
											<c:if test="${status.index!=0}">,</c:if>
											{className:"label",title:"${temp.name }",value:"${temp.activityId }"}
											</c:forEach>
						    	  		],search:true},
			      {name:"channelDTO",title:"渠道",style:"define",content:"{channelDTO['name']}",className:"td-label"}
			      ],
			normalOperator:[]
			};
			
			
var reactData=$("#maincontents").render("Table_data",props,function(dom){
	$(dom).find('.datetimepicker').datetimepicker({
		lang:'ch',
		timepicker:false,
		format:'Y-m-d',
		formatDate:'Y-m-d',
		yearStart: '2016',
		minDate:'-2016/08/02', // yesterday is minimum date
	});
});
</script>

	
	
<script type="text/javascript">

$(function(){
	//转换select控件
	$(".sf-select").each(function(){//设置select
		$(this).smartSelect()
	});
	
});

</script>
	

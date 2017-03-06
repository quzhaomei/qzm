<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
				<h1 class="t-rd">所有活动</h1>
			</header>
			<div id="maincontents">
			</div>
<script type="text/javascript">
function parseImages(images){
	if(images&&images.length){
		return images.length+" 页";
	}
	return "0 页";
}


var props={
			title:"活动列表",
			url:"activity/all-activity.htmls?operator=list",
			whole:true,
			addBtn:{powerCode:"",callback:function(){
						$("#addModal").modal("show");$("#addModal").reset();
						}
					},
			head:[{name:"name",title:"活动名称"},
			      {name:"startDate",title:"开始时间",className:"td-date",
				style:"date",format:"yyyy-MM-dd",sort:true,remove:true,},
					{name:"endDate",title:"结束时间",className:"td-date",
					style:"date",format:"yyyy-MM-dd",sort:true,remove:true,},
			      {name:"createDate",title:"录入时间",className:"td-datetime",
						style:"date",format:"yyyy-MM-dd HH:mm",sort:true,remove:true,sort_default:true},
			      {name:"images",title:"页面图片",style:"define",content:"{parseImages(images)}",className:"td-label"},
			      {name:"info",title:"活动备注",remove:true},
			      {name:"status",title:"状态",style:"label",
			    	  group:[{className:"label label-gray",title:"未开始",value:"0"}
			    	  		,{className:"label",title:"进行中",value:"1"}
			    	  		,{className:"label label-gray label-line",title:"已结束",value:"2"}]}
			      ],
			normalOperator:[{icon:"icon-pencil",title:"修改",powerCode:"constant-age-edit",
								callback:function(data){
									toEdit(data);
									}}]
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

		<div class="sf-modal modal modal-sm" style="display:none;"  id="addModal">
			<div class="panel">
				<div class="panel-title">
					<span>新增活动</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="关闭">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					<div class="formarea formarea-pad">
								 <div class="notice info closable"  style="display: none;">
									<span class="text">请按要求填写</span>
									<div class="close"></div>
								</div>
								<div class="formsection">
									<div class="form-row withicon">
									<input type="text" placeholder="名称" id="name" name="name" autocomplete="off" maxlength="50" required><label for="name"><i class="icon-person"></i></label>
									<span></span>
									</div>
									<div class="form-row withicon">
									<input type="text" placeholder="开始时间" id="startDate" name="startDate" class="datetimepicker" autocomplete="off" maxlength="50" required><label for="start"><i class="icon-event_note"></i></label>
									<span></span>
									</div>
									<div class="form-row withicon">
									<input type="text" placeholder="结束时间" id="endDate" name="endDate" class="datetimepicker" autocomplete="off" maxlength="50" required><label for="end"><i class="icon-event_note"></i></label>
									<span></span>
									</div>
									<div class="form-row withicon">
									<input type="text" placeholder="备注" id="info" name="info" autocomplete="off" maxlength="50" required><label for="end"><i class="icon-person"></i></label>
									<span></span>
									</div>
								</div>
								
					</div>
					
				</div>
				<div class="panel-foot">
					<div class="btnarea-center">
						<button class="btn btn-nor btn-iconleft" id="saveBtn">
									<i class="icon-check">
									</i>
									确认添加
						</button>
					</div>
				</div>
			</div>
		</div>
	
<script type="text/javascript">

$(function(){
	//转换select控件
	$(".sf-select").each(function(){//设置select
		$(this).smartSelect()
	});
	
	$("#saveBtn").on("click",function(){
		var name=$("#name").val();
		var startDate=$("#startDate").val();
		var endDate=$("#endDate").val();
		var info=$("#info").val();
		if(!name){alert("请输入活动名称！");return;}
		var param={};
		param.name=name;
		if(startDate){
			param.startDate=startDate;
		}
		if(endDate){
			param.endDate=endDate;
		}
		param.info=info;
		$(this).attr("disabled","disabled");
		var _this=this;
		$.post("activity/add-activity.htmls",param,function(json){
			$(_this).removeAttr("disabled");
			alert(json.message);
			if(json.status==1){
				$("#addModal").modal("hide");
				reactData.reload("last");
			}
		},"json")
	});
	

	$("#pageimg").on("change",function(){
		var _this=this;
		var formData = new FormData(); 
		formData.append("file",this.files[0]);
		$.ajax({
			   url: 'welcome/uploadSource/activityImage.htmls',
			   type: 'POST',
			   cache: false,
			   data: formData,
			   dataType:"json",
			   processData: false,
			   contentType: false ,
			   success:function(json){
				   if(json.error==0){
					   var $li=$("<li>").addClass("activityImage");
					  $li.data("url",json.url);
					  $li.css({"background-image":"url('"+json.url+"')"})
					   $(".imglist").append($li);
				   }else{
					   alert(json.message);
				   }
				   _this.value="";
			  }
		});
	});
	
	
	
	
	$('#editModal').on('mouseenter', ".imglist li:not('.addnew')", function(event) {
		event.preventDefault();
		var index=$(this).index();
		$(this).append("<span class='moveup'><i class='icon-arrow-left2'></i></span><span class='movedown'><i class='icon-arrow-right2'></i></span><div class='toolbtn'><input type='file' name='itemid1' id='itemid1' class='tempFile' accept='image/png, image/jpeg, image/gif' onchange='setImg(event,"+index+")'><label for='itemid1'><i class='icon-mode_edit img-edit'></i></label><i class='icon-delete_forever img-del'></i></div>");
		$(this).on('mouseleave', '', function(event) {
			event.preventDefault();
			$(this).children('.toolbtn').remove();
			$(this).children('span').remove();
		});
	});

	
	
	$("#editModal").on("click",".moveup",function(){
		var $image=$(this).parents(".activityImage");
		var $prev=$image.prev(".activityImage");
		if($prev[0]){
			$prev.before($image);
		}
	});
	
	$("#editModal").on("click",".movedown",function(){
		var $image=$(this).parents(".activityImage");
		var $next=$image.next(".activityImage");
		if($next[0]){
			$next.after($image);
		}
	});
	
	$("#editModal").on("click",".img-del",function(){
		var $image=$(this).parents(".activityImage");
		if(confirm("确定删除这张活动图吗？")){
			$image.remove();
		}
	});
	
	//保存
	$("#editBtn").on("click",function(){
		var activity=$("#editModal").data("data");
		var name=$("#name_edit").val();
		var startDate=$("#startDate_edit").val();
		var endDate=$("#endDate_edit").val();
		var info=$("#info_edit").val();
		var hasName=$("#hasName_edit:checked").val();
		var hasPhone=$("#hasPhone_edit:checked").val();
		
		var param={};
		if(!name){alert("请输入活动名称！");return;}
		//处理图片
		var images=[];
		$("#editModal .imglist .activityImage").each(function(index){
			var url=$(this).data("url");
			var image={};
			image.sequence=index;
			image.path=url;
			image.activityId=activity.activityId;
			images.push(image);
		});
		if(images.length==0){alert("请至少上传一张活动图片！");return;}
		
		if(hasName==1){
			param.hasName=1;
		}else{
			param.hasName=0;
		}
		
		if(hasPhone==1){
			param.hasPhone=1;
		}else{
			param.hasPhone=0;
		}
		
	 	param.activityId=activity.activityId;
	 	param.name=name;
	 	param.startDate;
	 	param.endDate=endDate;
	 	param.info=info;
	 	param.images=images;
	 	var _this=this;
	 	$(_this).attr("disabled","disabled");
	 	$.ajax({type:"post",
			url:"activity/update-activity.htmls",
			dataType:"json",
			contentType:"application/json",               
            data:JSON.stringify(param), 
			success:function(json){
				$(_this).removeAttr("disabled");
				if(json.status==1){//成功，刷新数据;
					$("#editModal").modal("hide");
					alert(json.message);
					reactData.reload("last");
				}else{
					alert(json.message);
				}
			}
		});
	});
	
	
	$("#linkModalBtn").on("click",function(){
		$("#linkModal").reset();
		$("#linkModal").modal("show");
	});
	
	//生成url
	$("#activity_url_get").on("click",function(){
		var activity=$("#editModal").data("data");
		var channelId=$("#activity_channel").val();
		if(!channelId){alert("请选择渠道！");return;}
			
		var url=$("#basePath").val()+"/"+activity.activityId+"/res_"+channelId+".htmls";
		$("#activity_url").val(url);
	});
	
});

function toEdit(data){
	$("#editModal").reset();
	$("#editModal").initForm(data);	
	$("#editModal").data("data",data);
	
	$("#editModal .panel-title .label[status]").hide();
	$("#editModal .panel-title .label[status='"+data.status+"']").show();
	if(data.hasName=="1"){
		$("#editModal #hasName_edit")[0].checked="checked";
	}
	if(data.hasPhone=="1"){
		$("#editModal #hasPhone_edit")[0].checked="checked";
	}
	$("#editModal .imglist .activityImage").remove();
	$(data.images).each(function(){
	 var $li=$("<li>").addClass("activityImage");
		 $li.data("url",this.path);
		 $li.css({"background-image":"url('"+this.path+"')"})
		 $(".imglist").append($li);
	});
	$("#editModal").modal("show");
}

function setImg(event,index){
	var $images=$("#editModal .imglist li:eq("+index+")");
	var _this=event.target;
	var formData = new FormData(); 
	formData.append("file",_this.files[0]);
	$.ajax({
		   url: 'welcome/uploadSource/activityImage.htmls',
		   type: 'POST',
		   cache: false,
		   data: formData,
		   dataType:"json",
		   processData: false,
		   contentType: false ,
		   success:function(json){
			   if(json.error==0){
				   $images.data("url",json.url);
				   $images.css({"background-image":"url('"+json.url+"')"})
			   }else{
				   alert(json.message);
			   }
			   _this.value="";
		  }
	});
}

</script>
	<div class="sf-modal modal-full item-detail modal-toorder" id="editModal" style="display:none;">
			<div class="panel">
				<div class="panel-title">
					<span> <strong data-ref="name">活动标题</strong>
					<div class="label-right">
						
						<div class="label" status="1">
							进行中
						</div>
						
						<div class="label label-gray label-line" status="2">
							已结束
						</div>
						<div class="label label-gray" status="0">
							未开始
						</div>
					</div>
					</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="返回我的硬装需求">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					<div class="formarea formarea-pad form-info form-lg">
								
								<div class="formsection">
									<div>

									<div class="formitem-2 withicon">
									<input type="text" id="name_edit" data-ref="name" value=""><label for="usercode"><i class="icon-toys"></i></label>
										<span></span>
										<div class="placeholder">
											活动名称
										</div>
									</div>
									<div class="formitem-1 withicon">
									<input type="text" id="startDate_edit" data-ref="startDate" data-fmt="yyyy-MM-dd" class="datetimepicker" value=""><label><i class="icon-event_note"></i></label>
										<span></span>
										<div class="placeholder">
											活动开始时间
										</div>
									</div>
									<div class="formitem-1 withicon no-right-line">
									<input type="text" id="endDate_edit" data-ref="endDate" data-fmt="yyyy-MM-dd" class="datetimepicker" value=""><label><i class="icon-event_note"></i></label>
										<span></span>
										<div class="placeholder">
											活动结束时间
										</div>
									</div>
									<div class="formitem-2 withicon no-bottom-line">
									<input type="text" id="info_edit" data-ref="info" value="35"><label><i class="icon-comment"></i></label>
										<span></span>
										<div class="placeholder">
											活动备注
										</div>
									</div>
										
									</div>
									
									<fieldset class="withicon">
										<div class="field-title">
												<i class="icon-panorama"></i> 活动页面添加
										</div>
										<div class="field-content">
											
												<ul class="imglist">
													<li class="addnew">
													<input type="file" name="pageimg" id="pageimg" accept="image/png, image/jpeg, image/gif">
													<label for="pageimg">
													<i class="icon-plus">
													</i></label>
													</li>
													
												<!-- 
												<li class="activityImage" 
												style="background-image: url('/static/images/avatar.jpg');"></li>
												 -->
												 	
												</ul>
										</div>
									</fieldset>
									
									<div class="form-row withicon">
											<div class="checkarea-title"><i class="icon-anchor-point"></i>报名表单项</div>
											<div class="checkarea">
												<input type="checkbox"  value="1" id="hasName_edit"><label for="hasName_edit">姓名</label>
												<input type="checkbox"  value="1" id="hasPhone_edit"><label for="hasPhone_edit">电话</label>
											</div>
									</div>
									

									<div class="form-footer">
										<div class="btnarea-center">
											<button class="btn btn-nor btn-iconleft" id="editBtn">
														<i class="icon-check">
														</i>
														保存修改
											</button>
											
											<button class="btn btn-nor btn-gray genurl" id="linkModalBtn"><i class="icon-link">
														</i> 生成渠道链接</button>
										</div>
									</div>
									</div>
									</div>
								</div>
					</div>
					
				</div>
	
				
	<div class="sf-modal modal modal-sm sourceurl" style="display:none;" id="linkModal">
			<div class="panel">
				<div class="panel-title">
					<span>渠道链接生成</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="关闭窗口">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					<div class="formarea formarea-pad">
								<div class="formsection">
																		

									<div class="form-row withicon">
										<select class="sf-select select dropdown-container menu-down sourcetype" id="activity_channel">
											<option value="">选择渠道来源</option>
											<c:forEach items="${channels }" var="channel">
												<option value="${channel.channelId }">${channel.name }</option>
											</c:forEach>
										</select>
										
									</div>
									
									
									<div class="form-row withicon">
										<textarea name="url" placeholder="链接信息" id="activity_url"></textarea>
										<label for="activity_url"><i class="icon-link"></i></label>
									</div>
								</div>
								
					</div>
					
				</div>
				<div class="panel-foot">
					<div class="btnarea-center">
						<button class="btn btn-nor btn-iconleft" id="activity_url_get">
									<i class="icon-check">
									</i>
									生成链接
						</button>
					</div>
				</div>
			</div>
		</div>

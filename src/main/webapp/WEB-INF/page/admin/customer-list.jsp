<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
				<h1 class="t-rd">客户管理</h1>
			</header>
			<div id="maincontents" class="customerPage">
			</div>
<script type="text/javascript">
function parseHouse(houses){
	if(houses){
		var arr=[];
		for(var index=0;index<houses.length;index++){
			var temp=houses[index];
			if(temp.status==1){
				arr.push(temp.houseLocation+'<span><i class="icon-check_circle icon-green"></i></span>');
			}else{
				arr.push(temp.houseLocation+'<span><i class="icon-schedule icon-red"></i></span>');
			}
		}
		return arr.join("<br/>")
	}else{
		return "";
	}
}

//批量分单
function batchToService(datas){
	if(datas&&datas instanceof Array){
		if(datas.length>0){
			var arr=[];
			$(datas).each(function(){
				arr.push(this.customerId);
			});
			$("#serviceBatchSave").data("ids",arr);
			$("#toBatchService").modal("show");
		}else{
			alert("至少选择一条来进行操作！");
		}
	}else{
			alert("至少选择一条来进行操作！");
	}
}

var props={
			title:"客户列表",
			url:"sysCustomer/customer-index.htmls?operator=list",
			whole:false,//真分页
			batch:[
			       {title:"分单",icon:"icon-record_voice_over",powerCode:"sysCustomer-customer-batch-service",callback:batchToService},
			       {title:"发送回访确认短信",icon:"icon-mail_outline",powerCode:"sysCustomer-customer-batch-msg",callback:function(data){batch_customerMsg(data)}}
			],
			addBtn:{powerCode:"sysCustomer-customer-save",callback:function(){$("#addModal").modal("show");$("#addModal").reset()}},
			batchAddBtn:{powerCode:"sysCustomer-batchUpload-customer-save",callback:function(){$("#batchAdd").modal("show")}},
			head:[
			      {name:"customerCode",title:"客户编号",remove:false,search:true},
			      {name:"name",title:"客户姓名",search:true},
			      {name:"houses",title:"房产地址",style:"define",
			    	  content:"{parseHouse(houses)}",remove:true,search:true,key:"houseLocation"},
			      {name:"phone",title:"手机",className:"td-mobile",search:true},
			      {name:"channel",title:"来源",className:"label label-gray",style:"label",content:"{channel['name']}",
					remove:true,search:true},
			      {name:"createDate",title:"录入时间",className:"td-datetime",
				style:"date",format:"yyyy-MM-dd HH:mm",sort:true,remove:true,sort_default:true,search:true},
			      {name:"createUser",title:"创建者",style:"define",
					content:"{createUser['nickname']}",remove:true,search:true},
			      {name:"serviceDate",title:"分配时间",className:"td-datetime",
				style:"date",format:"yyyy-MM-dd HH:mm",sort:true,remove:false},
			      {name:"servicer",title:"跟进业务员",style:"define",content:"{servicer['nickname']}",remove:false},
			      {name:"status",title:"状态",style:"label",className:"td-label",
			    	  group:[{className:"label label-gray label-line",title:"待分配",value:"1"}
			    	  		,{className:"label label-gray",title:"等待回访",value:"2"}
			    	  		,{className:"label label-blue",title:"待跟进库",value:"3"}
			    	  		,{className:"label label-red",title:"无效客户",value:"4"}
			    	  		,{className:"label",title:"已回访",value:"5"}],search:true}
			      , {name:"nextcallTime",title:"下次回访",className:"td-date",
						style:"date",format:"yyyy-MM-dd",sort:true,remove:false}
			      ],
			normalOperator:[{icon:"icon-eye",title:"详细",powerCode:"sysCustomer-customer-update",
				callback:function(data){
					toEdit(data);
					}}],
			specialOperator:[{type:1,icon:"icon-mail_outline",title:"发送回访确认短信",powerCode:"myWork-customer-msg",
				callback:function(data){
					customerMsg(data);
				}},
			                 {type:1,icon:"icon-phone",title:"呼叫",powerCode:"sysCustomer-customer-call", callback:function(data){
				callCustomer(data);
				}}
			,{type:1,icon:"icon-record_voice_over",powerCode:"sysCustomer-customer-service",title:"分单",callback:function(data){serviceTemp=data;$("#toService").modal("show")}}
			,{type:1,icon:"icon-loop changestatus",title:"状态切换",powerCode:"sysCustomer-customer-status",
				callback:function(data){
						changeCustomerStatus(data);
					}}
			]
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

		<div class="modal modal-sm sf-modal" style="display:none;" id="addModal">
			<div class="panel">
				<div class="panel-title">
					<span>新增客户</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="添加新用户">
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
									<input type="text" placeholder="客户名字" id="name" autocomplete="off" maxlength="20" required><label for="name"><i class="icon-person"></i></label>
									<span></span>
									</div>
									<div class="form-row withicon">
									<input type="text" placeholder="客户电话" id="phone" autocomplete="off" maxlength="11" required><label for="name"><i class="icon-phone_android"></i></label>
									<span></span>
									</div>
									
								<div class="form-row withicon">
										<select class="sf-select select dropdown-container sourcetype menu-down" id="channelId">
											<c:forEach	items="${channels }" var="channel" varStatus="status">
												<option value="${channel.channelId }" ${status.index==0?"selected='seleceted'":"" }>${channel.name }</option>
											</c:forEach>
										</select>
									</div>
									
									<div class="form-row withicon">
										<textarea name="comment" placeholder="客户备注" id="info" maxlength="200"></textarea>
										<label for="info"><i class="icon-comment"></i></label>
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
		
		<div class="modal modal-sm modal-tocaller sf-modal"  style="display:none;" id="toService" >
			<div class="panel">
				<div class="panel-title">
					<span>分配客户给相关客服</span>
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
										<select class="select dropdown-container menu-down caller sf-select" id="serviceSelect">
											<c:forEach items="${services }" var="service" >
												<option value="${service.adminUserId }">${service.nickname }</option>
											</c:forEach>
										</select>
									</div>
									
								</div>
								
					</div>
					
				</div>
				<div class="panel-foot">
					<div class="btnarea-center">
						<button class="btn btn-nor btn-iconleft" id="serviceSave">
									<i class="icon-check">
									</i>
									确认分配给该客服
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal modal-sm modal-tocaller sf-modal"  style="display:none;" id="toBatchService" >
			<div class="panel">
				<div class="panel-title">
					<span>批量分配客户给相关客服</span>
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
										<select class="select dropdown-container menu-down caller sf-select" id="serviceBatchSelect">
											<c:forEach items="${services }" var="service" >
												<option value="${service.adminUserId }">${service.nickname }</option>
											</c:forEach>
										</select>
									</div>
									
								</div>
								
					</div>
					
				</div>
				<div class="panel-foot">
					<div class="btnarea-center">
						<button class="btn btn-nor btn-iconleft" id="serviceBatchSave">
									<i class="icon-check">
									</i>
									确认分配给该客服
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 切换状态 -->
		<div class="modal modal-sm modal-changestatus sf-modal" id="changeStatus" style="display:none;">
			<div class="panel">
				<div class="panel-title">
					<span>修改客户状态</span>
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
										<select name="status" class="sf-select select dropdown-container menu-down status">
										<!--  	<option value="1">待分配</option>
											<option value="2">等待回访</option>-->
											<option value="3">待跟进库</option>
											<option value="4">无效客户</option>
											<option value="5">已回访</option>
										</select>
									</div>

									<div class="form-row withicon nextcallTimeDiv" style="display:none;">
										<input type="text"  name="nextcallTime" placeholder="下次回访时间"  class="datetimepicker">
										<label for=""><i class="icon-event_note"></i></label>
									</div>
									<div class="form-row withicon">
										<textarea name="info" placeholder="添加备注" id="user-comment"></textarea>
										<label for="user-comment"><i class="icon-comment"></i></label>
									</div>
									
								</div>
								
					</div>
					
				</div>
				<div class="panel-foot">
					<div class="btnarea-center">
						<button class="btn btn-nor btn-iconleft" id="changeStatusSave">
									<i class="icon-check">
									</i>
									确认切换客户状态
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 发送短信 -->
		<div class="modal modal-sm modal-changestatus sf-modal" id="sendMsgModal" style="display:none;">
			<div class="panel">
				<div class="panel-title">
					<span>发送短信</span>
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
										<select name="template" class="sf-select select dropdown-container menu-down status">
											<option value="">请选择短信内容</option>
											<c:forEach items="${msgTemplate }" var="temp">
												<option value="${temp.template}">${temp.value }</option>
											</c:forEach>
										</select>
									</div>

								</div>
								
					</div>
					
				</div>
				<div class="panel-foot">
					<div class="btnarea-center">
						<button class="btn btn-nor btn-iconleft" id="sendMsgBtn">
									<i class="icon-check">
									</i>
									确认发送
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 批量发送短信 -->
		<div class="modal modal-sm modal-changestatus sf-modal" id="batch_sendMsgModal" style="display:none;">
			<div class="panel">
				<div class="panel-title">
					<span>批量发送短信</span>
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
										<select name="template" class="sf-select select dropdown-container menu-down status">
											<option value="">请选择短信内容</option>
											<c:forEach items="${msgTemplate }" var="temp">
												<option value="${temp.template}">${temp.value }</option>
											</c:forEach>
										</select>
									</div>

								</div>
								
					</div>
					
				</div>
				<div class="panel-foot">
					<div class="btnarea-center">
						<button class="btn btn-nor btn-iconleft" id="batch_sendMsgBtn">
									<i class="icon-check">
									</i>
									确认发送
						</button>
					</div>
				</div>
			</div>
		</div>
	
<script type="text/javascript">
//切换状态
function changeCustomerStatus(data){
	$("#changeStatus").modal("show");
	$("#changeStatus").data("customer",data);
	$("#changeStatus select[name='status'] option").removeAttr("selected");
	$("#changeStatus select[name='status'] option[value='"+data.status+"']").attr("selected","selected");
	$("#changeStatus select[name='status']").change();
	
	$("#changeStatus [name='info']").val(data.nextcallInfo);
	if(data.nextcallTime&&data.nextcallTime.match(/^\d+$/)){
		$("#changeStatus [name='nextcallTime']").val(new Date(parseInt(data.nextcallTime)).format("yyyy-MM-dd"));
	}else{
		$("#changeStatus [name='nextcallTime']").val("");
	}
}
var callphone = new UCObj(window, document);
//外呼
function callCustomer(data){
	var param={};
	param.customerId=data.customerId;
	$.post("sysCustomer/customer-call.htmls",param,function(json){
		if(json.status==1){
			var phone=json.data;
			//电话外呼
			var dn = "Tel:"+phone;
			var ani = "4000000000";
			callphone.doCallOut(dn, ani);
		}else{
			alert("查无此人！");
		}
	},"json")
}
//发送短信
function customerMsg(data){
	$("#sendMsgModal").modal("show");
	$("#sendMsgModal").data("data",data);
}
//发送短信
function batch_customerMsg(datas){
	if(datas&&datas instanceof Array){
		if(datas.length>200){
			alert("最多一次只能发送200个客户,请重新确认号码！");
			return;
		}
		if(datas.length>0){
			var arr=[];
			$(datas).each(function(){
				arr.push(this.customerId);
			});
			$("#batch_sendMsgModal").data("ids",arr);
			$("#batch_sendMsgModal").modal("show");
		}else{
			alert("至少选择一条来进行操作！");
		}
	}else{
			alert("至少选择一条来进行操作！");
	}
}

var serviceTemp;
$(function(){
	
	$.datetimepicker.setLocale('ch');
	$('.datetimepicker').datetimepicker({
		lang:'ch',
		timepicker:false,
		format:'Y-m-d',
		formatDate:'Y-m-d',
		yearStart: '2016',
		minDate:'-2016/08/02', // yesterday is minimum date
	});
	
	
	$("#changeStatusSave").on("click",function(){
		var customer=$("#changeStatus").data("customer");
		var customerId=customer.customerId;
		var status=$("#changeStatus select[name='status']").val();
		var nextcallTime=$("#changeStatus [name='nextcallTime']").val();
		var info=$("#changeStatus [name='info']").val();
		if(status==3&&!nextcallTime){
			alert("请选择下次回访时间！");
			return;
		}
		var param={};
		param.customerId=customerId;
		param.status=status;
		if(status==3){
			param.nextcallTime=nextcallTime;
		}
		param.nextcallInfo=info;//回访备注
		$.post("sysCustomer/customer-status.htmls",param,function(json){
			alert(json.message);
			if(json.status==1){
				$("#changeStatus").modal("hide");
				reactData.reload("last");//重新加载数据
			}
		},"json")
	});
	
	$("#changeStatus select[name='status']").on("change",function(){
		var value=this.value;
		if(value==3){
			$(".nextcallTimeDiv").show();
		}else{
			$(".nextcallTimeDiv").hide();
		}
	});
	//转换select控件
	$(".sf-select").each(function(){
		$(this).smartSelect();
	});
	
	$("#addModal").on("click",".close",function(){
		$("#addModal .notice").hide();
	});
	
	//显示错误信息
	function showMsg(msg,error){
		$("#addModal .notice").show();
		$("#addModal .notice .text").text(msg);
		if(error){
			$("#addModal .notice").removeClass("info").addClass("error");
		}else{
			$("#addModal .notice").removeClass("error").addClass("info");
		}
	}
	
	var telephone_reg=/^1[3|4|5|6|7|8]\d{9}$/;
	
	
	$("#name").validator({require:true,callback:function(){
		showMsg("请输入客户名字",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	
	$("#phone").validator({require:true,callback:function(){
		showMsg("请输入客户手机",true);
	},regExp:telephone_reg,callback1:function(){
		showMsg("请输入正确的手机号码",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});

	//分单
	$("#serviceSave").on("click",function(){
		var data=serviceTemp;
		var serviceId=$("#serviceSelect").val();
		if(serviceId&&data){
			var param={};
			param.serviceId=serviceId;
			param.customerId=data.customerId;
			$.post("sysCustomer/customer-service.htmls",param,function(json){
				alert(json.message);
				if(json.status==1){
					$("#toService").modal("hide");
					var pageIndex=reactData.state.pageIndex;
					reactData.reload(pageIndex);//重新加载数据,并跳转当页
				}
			},"json");
		}
	});
	//批量分单
	$("#serviceBatchSave").on("click",function(){
		var ids=$(this).data("ids");//获取数据
		var serviceId=$("#serviceBatchSelect").val();
		if(serviceId&&ids){
			var param={};
			param.serviceId=serviceId;
			param.customerIds=ids;
			$.post("sysCustomer/customer-batch-service.htmls",param,function(json){
				alert(json.message);
				if(json.status==1){
					$("#toBatchService").modal("hide");
					var pageIndex=reactData.state.pageIndex;
					reactData.reload(pageIndex);//重新加载数据,并跳转当页
				}
			},"json")
			
		}
	});
	
	//发送短信
	$("#sendMsgBtn").on("click",function(){
		var template=$("#sendMsgModal select[name='template']").val();
		if(template==""){
			alert("请选择短信内容");return;
		}
		var data=$("#sendMsgModal").data("data");
		if(confirm("确认向客户 " +data.name+" 发送短信吗？")){
			var param={};
			param.customerId=data.customerId;
			param.template=template;
			$.post("sysCustomer/customer-msg.htmls",param,function(json){
					alert(json.message);
					if(json.status==1){
						$("#sendMsgModal").modal("hide");
					}
			},"json");
		}	
	});
	
	//批量发送短信
	$("#batch_sendMsgBtn").on("click",function(){
		var template=$("#batch_sendMsgModal select[name='template']").val();
		if(template==""){
			alert("请选择短信内容");return;
		}
		var ids=$("#batch_sendMsgModal").data("ids");
		if(confirm("确认发送短信吗？")){
			var param={};
			param.ids=ids;
			param.template=template;
			$.post("sysCustomer/batch-customer-msg.htmls",param,function(json){
				alert(json.message);
					if(json.status==1){
						$("#batch_sendMsgModal").modal("hide");
					}
			},"json");
		}	
	});
	

	$("#saveBtn").on("click",function(){
		var name=$("#name").val();
		var code=$("#phone").val();
		var info=$("#info").val();
		var phone=$("#phone").val();
		var channelId=$("#channelId").val();
		var param={};
		
		if(!$("#name").valide()){
			return;
		}else if(!$("#phone").valide()){
			return;
		}
		
		param.name=name;
		param.code=code;
		param.phone=phone;
		param.info=info;
		param.channelId=channelId;
		$.post("sysCustomer/customer-save.htmls",param,function(json){
			if(json.status==1){//保存成功
				alert(json.message)
				$("#addModal").modal("hide");
				reactData.reload("last");//重新加载数据
			}else{
				showMsg(json.message,true)
			}
		},"json");
	});
	
	

	//保存修改
	$("#editSave").on("click",function(){
		var name=$("#editModal #name_edit").val();
//		var code=$("#editModal #customerCode_edit").val();
		var info=$("#editModal #info_edit").val();
		var ageId=$("#editModal #ageId_edit").val();
		var carCode=$("#editModal #carCode_edit").val();
		var gender=$("#editModal #gender_edit").val();
		var integration=$("#editModal #integration_edit").val();
		var phone=$("#editModal #phone_edit").val();
		
		if(!name){
			alert("请输入用户名称");
			return;
		}
		
		var param={};
		param.customerId=editTemp.customerId;
		
		param.name=name;
//		param.code=code;
		param.info=info;
		param.phone=phone;
		if(ageId!="null"){
		param.ageId=ageId;
		}
		
		param.carCode=carCode;
		if(gender!="null"){
		param.gender=gender;
		}
		param.integration=integration;
		
		var houses=[];
		var error=false;
		//查看是否有新增的房产
		$("#editModal .data-form").each(function(){
			var house={};
			var houseId=$(this).find("[name='houseId']").val();
			if(houseId){//为修改
				house.houseId=houseId;
			}
			
			var zoneId=$(this).find("[name='zoneId']").val();
			house.zoneId=zoneId;
			
			var houseInfo=$(this).find("[name='houseInfo']").val();
			house.houseInfo=houseInfo;
			
			var houseLocation=$(this).find("[name='houseLocation']").val();
			house.houseLocation=houseLocation;
			
			var houseTypeId=$(this).find("[name='houseTypeId']").val();
			house.houseTypeId=houseTypeId;
			
			var isNew=$(this).find("[name='isNew']").val();
			house.isNew=isNew;
			
			var area=$(this).find("[name='area']").val();
			house.area=area;
			
			var designType=$(this).find("[name='designType']").val();
			house.designType=designType;
			
			var budgetId=$(this).find("[name='budgetId']").val();
			house.budgetId=budgetId;
			
			var hasSoft=$(this).find("[name='hasSoft']").val();
			house.hasSoft=hasSoft;
			
			var comment=$(this).find("[name='comment']").val();
			house.comment=comment;
			
			var callbackTips=$(this).find("[name='callbackTips']").val();
			house.callbackTips=callbackTips;
			
			var gift=$(this).find("[name='gift']").val();
			house.gift=gift;
			var giftAddress=$(this).find("[name='giftAddress']").val();
			house.giftAddress=giftAddress;
//
//			if(!zoneId){
//				alert("请选择区域");error=true;
//				return;
//			}
//			if(!houseInfo){
//				alert("请填写楼盘信息");error=true;
//				return;
//			}
//			if(!houseLocation){
//				alert("请填写房屋地址");error=true;
//				return;
//			}
//			if(houseTypeId==""){
//				alert("请选择房型");error=true;
//				return;
//			}
//			if(isNew==""){
//				alert("请选择房屋类别");error=true;
//				return;
//			}
//			if(area==""){
//				alert("请填写装修面积");error=true;
//				return;
//			}
//			if(!area.match(/\d+/)){
//				alert("装修面积为数字");error=true;
//				return;
//			}
//			if(designType==""){
//				alert("请选择专修类别");error=true;
//				return;
//			}
//			if(budgetId==""){
//				alert("请选择装修预算");error=true;
//				return;
//			}
//			if(hasSoft==""){
//				alert("请选择软装需求");error=true;
//				return;
//			}
//			if(gift==""){
//				alert("请选择是否派送了礼物");error=true;
//				return;
//			}
			
			houses.push(house);
		});
		param.houses=houses;
		if(error){return;}
		$.ajax({type:"post",
				url:"sysCustomer/customer-update.htmls",
				dataType:"json",
				contentType:"application/json",               
	            data:JSON.stringify(param), 
				success:function(json){
					if(json.status==1){//成功，刷新数据;
						alert(json.message);
						$("#editModal").modal("hide");
						var pageIndex=reactData.state.pageIndex;
						reactData.reload(pageIndex);//重新加载数据,并跳转当页
					}else{
						alert(json.message);
					}
				}
			});
	});
	
	//重置
	$("#editReset").on("click",function(){
		editReset();
	})
	
	//添加房产信息
	$("#addCustomerHouse").on("click",function(){
		addHouse();
	});
	
	
	//删除房产
	$("#editModal").on("click",".data-delete",function(){
		if(confirm("确定删除吗？")){
			$(this).parent(".data-form").remove();
		}
	});
	
	//发布房产
	$("#editModal").on("click",".publish_house",function(){
		var _this=this;
		var house=$(this).parents(".data-form").data("house");
		if(house&&confirm("确定发布吗？")){
			var param={};
			param.houseId=house.houseId;
			param.operator="publishHouse";
			
			$.post("sysCustomer/customer-index.htmls",param,function(json){
				alert(json.message)
				if(json.status==1){
					$(_this).parent().empty().append('<button class="btn btn-sm btn-green" readonly>'+
							'已发布</button>')
				}
			},"json")
		}
	});
});



function addHouse(){
	$("#editModal .form-footer").before($("#addCustomerHouseTemplate").html());
	$("#editModal select").each(function(){
		$(this).smartSelect();
	});
}	

function editHouse(data){
	$("#editModal .form-footer").before($("#editCustomerHouseTemplate").html());
	var $dataForm=$("#editModal .form-footer").prev(".data-form");
	$dataForm.data("house",data);
	//初始化区域
	$dataForm.find("#zoneId_1").attr("default_",data.zoneRootId);
	$dataForm.find("#zoneId_2").attr("default_",data.zoneParentId);
	$dataForm.find("#zoneId").attr("default_",data.zoneId);
	var $statusDiv=$dataForm.find(".house_status_div");
	if(data.status==1){
		$statusDiv.append('<button class="btn btn-sm btn-green" readonly>已发布</button>');
	}else{
		$statusDiv.append('<button class="btn btn-sm publish_house">发布</button>');
	}
	$dataForm.initForm(data);
	$("#editModal select").each(function(){
		$(this).smartSelect();
	});
}

var editTemp;

//修改
function toEdit(data){
	//查找房产信息
	if(data.houses&&data.houses.length>0){
		var customerId=data.customerId;
		var param={};param.customerId=customerId;param.operator="getHouse";
		$.post("sysCustomer/customer-index.htmls",param,function(json){
			if(json){
				data.houses=json;
				editTemp=data;
				$("#editModal").modal('show');
				initEditForm(editTemp);
				//初始化区域
			}else{
				alert("数据异常，请刷新页面！");
			}
		},"json");
	}else{//无房产信息
		editTemp=data;
		$("#editModal").modal('show');
		initEditForm(editTemp);
	}
}


function editReset(){
	var data=editTemp;
	initEditForm(data);
}

function initEditForm(data){
//初始化状态
$("#editModal #editStatus .label").hide();
$("#editModal #editStatus .label[value='"+data.status+"']").show();
$("#editModal .waitForCallDiv").remove();
//初始化带跟进信息
if(data.status==3){
	$("#info_edit_div").after($("#waitForCallDivTempalte").html());
	var $waitForCall=$("#editModal .waitForCallDiv");
	if(data.fileDate.match(/^\d+$/)){
		$waitForCall.find("td[name='fileDate']").text(new Date(parseInt(data.fileDate)).format("yyyy-MM-dd"))
	}else{
		$waitForCall.find("td[name='fileDate']").text("");
	}
	if(data.nextcallTime.match(/^\d+$/)){
		$waitForCall.find("td[name='nextcallTime']").text(new Date(parseInt(data.nextcallTime)).format("yyyy-MM-dd"))
	}else{
		$waitForCall.find("td[name='nextcallTime']").text("");
	}
	$waitForCall.find("td[name='info']").text(data.nextcallInfo);
}


	$("#editModal .dynmic").remove();
	
	$("#editModal #customerCode_edit").val(data.customerCode);
	$("#editModal #name_edit").val(data.name);
	$("#editModal #code_edit").val(data.code);
	$("#editModal #phone_edit").val(data.phone);
	
	var channelName="";
	if(data.channel){channelName=data.channel.name};
	$("#editModal #channel_edit").val(channelName);
	$("#editModal #info_edit").text(data.info);
	$("#editModal #carCode_edit").val(data.carCode);
	$("#editModal #integration_edit").val(data.integration);
	
	//初始化性别
	$("#gender_edit option:eq(0)")[0].selected="selected";
	$("#gender_edit  option").each(function(){
		$(this).removeAttr("selected");
		var value=this.value;
			if(value==data.gender){
				this.selected="selected";
			}
	});
	$("#gender_edit").change();
	
	//初始化年龄
	$("#ageId_edit option:eq(0)")[0].selected="selected";
	$("#ageId_edit option").each(function(){
		$(this).removeAttr("selected");
		var value=this.value;
			if(data.age&&value==data.age.ageId){
				this.selected="selected";
				$(this).change();
			}
	});
	$("#ageId_edit").change();
	
	//初始化房产
	if(data.houses&&data.houses.length>0){
		$(data.houses).each(function(){
			editHouse(this);
		});
	}
}




function toStatus(data){
	var channelId=data.channelId;
	var url="source/channel/"+channelId+"/status.htmls";
	var message="";
	var aim=0;
	if(data.status==0){
		message="确定激活 "+data.name+" 吗？";
		aim=1;
	}else{
		message="确定冻结 "+data.name+" 吗？";
		aim=0;
	}
	if(confirm(message)){
		$.post(url,{channelId:channelId},function(json){
			alert(json.message);
			if(json.status==1){
				data.status=aim;
				reactData.fresh();//刷新
			}
		},"json");
	}
}


</script>

		<div class="modal-full sf-modal"  id="editModal" style="display: none;">
			<div class="panel">
				<div class="panel-title">
					<span>修改客户
					
					<div class="label-right" id="editStatus">
						<div class="label label-line label-gray" value="1">
							待分配
						</div>
						<div class="label" value="5">
							已回访
						</div>
						<div class="label label-blue" value="3">
							待跟进
						</div>
						<div class="label label-red" value="4">
							无效客户
						</div>
						<div class="label label-gray" value="2">
							等待回访
						</div>
					</div>
					
					
					</span>
					
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="关闭">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					<div class="formarea formarea-pad form-info form-lg">
								
								<div class="formsection">
									<div>

									<div class="formitem withicon">
									<input type="text"  id="customerCode_edit" disabled><label for="customerCode_edit"><i class="icon-label_outline"></i></label>
										<span></span>
										<div class="placeholder">
											客户编码
										</div>
									</div>
									<div class="formitem withicon">
									<input type="text" id="name_edit" value="suninverse" maxlength="20"><label for="edit_name"><i class="icon-person"></i></label>
										<span></span>
										<div class="placeholder">
											客户姓名
										</div>
									</div>
									<div class="formitem withicon">
										<input type="tel" id="phone_edit" value="189****837" ><label for="edit_phone"><i class="icon-phone_android" required></i></label>
										<span></span>
										<div class="placeholder">
											联系手机
										</div>
									</div>
									<div class="formitem withicon">
										<input type="text" value="数据源1" id="channel_edit" disabled><label for="channel_edit"><i class="icon-spin"></i></label>
										<span></span>
										<div class="placeholder">
											来源渠道
										</div>
									</div>
									<div class="formitem withicon">
										<select id="gender_edit" class="sf-select dropdown-container menu-down select">
											<option value="null">未选择</option>
											<option value="1">男</option>
											<option value="0">女</option>
										</select>
										<label for="gender_edit"><i class="icon-people"></i></label>
										<div class="placeholder">
											性别
										</div>
									</div>

									<div class="formitem withicon">
										<select id="ageId_edit" class="sf-select dropdown-container menu-down select">
											<option value="null">未选择</option>
											<c:forEach items="${ages }" var="age">
												<option value="${age.ageId }">${age.name }</option>
											</c:forEach>
										</select>
										<label for="ageId_edit"><i class="icon-cake"></i></label>
										<div class="placeholder">
											年龄
										</div>
									</div>
									
									<div class="formitem withicon">
										<input type="text" value="沪203928" id="carCode_edit"><label for="carCode_edit"><i class="icon-time_to_leave"></i></label>
										<span></span>
										<div class="placeholder">
											车牌号
										</div>
									</div>
									
									<div class="formitem withicon">
										<input type="text" value="200" id="integration_edit"><label for="integration_edit"><i class="icon-coin-yen"></i></label>
										<div class="placeholder">
											用户积分
										</div>
									</div>
										
									</div>

									<div class="form-row withicon" id="info_edit_div">
										<textarea name="comment" id="info_edit"></textarea>
										<label for="edit-info"><i class="icon-comment"></i></label>
										<div class="placeholder">
											客户备注
										</div>
									</div>
									
									
									
									<div class="form-footer">
										<div class="btnarea-center">
											<button class="btn btn-nor btn-iconleft" id="editSave">
														<i class="icon-check">
														</i>
														保存修改
											</button>

											<button class="btn btn-nor btn-iconleft btn-gray" id="editReset">
														<i class="icon-reload">
														</i>
														重置数据
											</button>
										</div>
									</div>
									
									<!-- 增加房产 -->
									<div class="form-btn-fix">
										<button class="btn btn-round" title="添加房产" id="addCustomerHouse">
											<i class="icon-home2"></i>
										</button>
									</div>
									</div>
								</div>
					</div>
			</div>
		</div>
		
		<div id="editCustomerHouseTemplate" style="display:none;">
			<fieldset class="withicon data-form dynmic" >
										<div class="field-title">
												<i class="icon-home3"></i>房产信息
										</div>
										<div class="field-content">
											<div>
												<div class="formitem">
													<input type="text" name="houseId"  disabled>
													<span></span>
													<div class="placeholder">
														需求ID
													</div>
												</div>
												<div class="formitem">
													<select id="zoneId_1" class="dropdown-container menu-down select"
													 aimId="$(this).parent('.formitem').next('.formitem').find('select')"
													  uri="welcome/getZone.htmls" value_key="zoneId" text_key="name">
														<option value="">请选择</option>
														<c:forEach items="${zones }" var="zone">
														<option value="${zone.zoneId }">${zone.name }</option>
														</c:forEach>
													</select>
												<div class="placeholder">
													城市
												</div>
												</div>

												<div class="formitem">
												<select id="zoneId_2" class="dropdown-container menu-down select" 
												 aimId="$(this).parent('.formitem').next('.formitem').find('select')"
												  uri="welcome/getZone.htmls"  value_key="zoneId" text_key="name">
														<option value="">请选择</option>
													</select>
													<div class="placeholder">
														区域
													</div>
												</div>

												<div class="formitem">
													<select id="zoneId" name="zoneId" class="dropdown-container menu-down select">
														<option value="">请选择</option>
														</select>
													<div class="placeholder">
														区域细分
													</div>
												</div>

												<div class="formitem">
													<input type="text" name="houseInfo" placeholder="请输入楼盘信息">
													
													<div class="placeholder">
														楼盘信息
													</div>
												</div>

												<div class="formitem">
													<input type="text" name="houseLocation" placeholder="请输入房屋地址">
													<div class="placeholder">
														房屋地址
													</div>
												</div>


												<div class="formitem">
														<select  name="houseTypeId" class="dropdown-container menu-down select">
															<option value="">请选择</option>
															<c:forEach items="${styles }" var="style">
																<option value="${style.styleId }">${style.name }</option>
															</c:forEach>
														</select>
													<div class="placeholder">
														房型
													</div>
												</div>

												<div class="formitem">
													<select name="isNew" class="dropdown-container menu-down select">
															<option value="">请选择</option>
															<option value="1">新房</option>
															<option value="0">二手房</option>
														</select>
													<div class="placeholder">
														房屋类别
													</div>
												</div>


												<div class="formitem">
													<input type="text" name="area" placeholder="请输入装修面积">
													<span>m²</span>
													<div class="placeholder">
														装修面积
													</div>
												</div>

												<div class="formitem">
												
												<select name="designType" class="dropdown-container menu-down select">
														<option value="">请选择</option>
														<option value="0">半包</option>
														<option value="1">全包</option>
													</select>
													
													<div class="placeholder">
														装修类别
													</div>
												</div>
						

												<div class="formitem">
													<select name="budgetId" class="dropdown-container menu-down select">
														<option value="">请选择</option>
														<c:forEach items="${budgets }" var="budget">
														<option value="${budget.budgetId }">${budget.name }</option>
														</c:forEach>
													</select>
													<div class="placeholder">
														装修预算
													</div>
												</div>


												<div class="formitem">
												<select name="hasSoft" class="dropdown-container menu-down select">
														<option value="">请选择</option>
														<option value="1">是</option>
														<option value="0">否</option>
													</select>
													
													<div class="placeholder">
														软装需求
													</div>
												</div>
												
											</div>



											<div class="form-row">
												<textarea name="comment" id="user-comment"></textarea>
												<div class="placeholder">
													需求备注
												</div>
											</div>
											<div class="form-row">
												<textarea name="callbackTips" id="user-comment"></textarea>
												<div class="placeholder">
													回访备注
												</div>
											</div>

											<div class="form-row-special">
												<div class="formitem-1 no-bottom-line">
												<select class="dropdown-container menu-down select" name="gift">
														<option value="">请选择</option>
														<option value="1">是</option>
														<option value="0">否</option>
													</select>
													
													
													<div class="placeholder">
														礼包是否已配送
													</div>
												</div>
												<div class="formitem-3 no-bottom-line">
													<input type="text" name="giftAddress">
													<div class="placeholder">
														礼包配送地址
													</div>
												</div>
												<div class="form-row">
											<textarea name="log" disabled readonly>日志内容</textarea>
												<div class="placeholder">
													操作日志
												</div>
											</div>
									</div>
							</div>
							
							<div class="btnarea-right house_status_div">
											
							</div>
										
					</fieldset>
					
		</div>
		
		<div id="addCustomerHouseTemplate" style="display:none;">
			<fieldset class="withicon data-form dynmic addCustomerHouseTemplateClass" >
										<div class="field-title">
												<i class="icon-home3"></i>新建房产信息
										</div>
										<div class="field-content">
											<div>
											<!-- 
												<div class="formitem">
													<input type="text" value="ND129837" disabled>
													<span></span>
													<div class="placeholder">
														需求ID
													</div>
												</div>
												 -->
												<div class="formitem">
													<select id="zoneId_1" class="dropdown-container menu-down select"
													 aimId="$(this).parent('.formitem').next('.formitem').find('select')"
													  uri="welcome/getZone.htmls" value_key="zoneId" text_key="name">
														<option value="">请选择</option>
														<c:forEach items="${zones }" var="zone">
														<option value="${zone.zoneId }">${zone.name }</option>
														</c:forEach>
													</select>
												<div class="placeholder">
													城市
												</div>
												</div>

												<div class="formitem">
												<select id="zoneId_2" class="dropdown-container menu-down select" 
												 aimId="$(this).parent('.formitem').next('.formitem').find('select')"
												  uri="welcome/getZone.htmls"  value_key="zoneId" text_key="name">
														<option value="">请选择</option>
													</select>
													<div class="placeholder">
														区域
													</div>
												</div>

												<div class="formitem">
													<select id="zoneId" name="zoneId" class="dropdown-container menu-down select">
														<option value="">请选择</option>
														</select>
													<div class="placeholder">
														区域细分
													</div>
												</div>

												<div class="formitem">
													<input type="text" name="houseInfo" placeholder="请输入楼盘信息">
													
													<div class="placeholder">
														楼盘信息
													</div>
												</div>

												<div class="formitem">
													<input type="text" name="houseLocation" placeholder="请输入房屋地址">
													<div class="placeholder">
														房屋地址
													</div>
												</div>


												<div class="formitem">
														<select  name="houseTypeId" class="dropdown-container menu-down select">
															<option value="">请选择</option>
															<c:forEach items="${styles }" var="style">
																<option value="${style.styleId }">${style.name }</option>
															</c:forEach>
														</select>
													<div class="placeholder">
														房型
													</div>
												</div>

												<div class="formitem">
													<select name="isNew" class="dropdown-container menu-down select">
															<option value="">请选择</option>
															<option value="1">新房</option>
															<option value="0">二手房</option>
														</select>
													<div class="placeholder">
														房屋类别
													</div>
												</div>


												<div class="formitem">
													<input type="text" name="area" placeholder="请输入装修面积">
													<span>m²</span>
													<div class="placeholder">
														装修面积
													</div>
												</div>

												<div class="formitem">
												
												<select name="designType" class="dropdown-container menu-down select">
														<option value="">请选择</option>
														<option value="0">半包</option>
														<option value="1">全包</option>
													</select>
													
													<div class="placeholder">
														装修类别
													</div>
												</div>
						

												<div class="formitem">
													<select name="budgetId" class="dropdown-container menu-down select">
														<option value="">请选择</option>
														<c:forEach items="${budgets }" var="budget">
														<option value="${budget.budgetId }">${budget.name }</option>
														</c:forEach>
													</select>
													<div class="placeholder">
														装修预算
													</div>
												</div>


												<div class="formitem">
												<select name="hasSoft" class="dropdown-container menu-down select">
														<option value="">请选择</option>
														<option value="1">是</option>
														<option value="0">否</option>
													</select>
													
													<div class="placeholder">
														软装需求
													</div>
												</div>
												
											</div>



											<div class="form-row">
												<textarea name="comment" id="user-comment"></textarea>
												<div class="placeholder">
													需求备注
												</div>
											</div>

											<div class="form-row-special">
												<div class="formitem-1 no-bottom-line">
												<select class="dropdown-container menu-down select" name="gift">
														<option value="">请选择</option>
														<option value="1">是</option>
														<option value="0">否</option>
													</select>
													
													
													<div class="placeholder">
														礼包是否已配送
													</div>
												</div>
												<div class="formitem-3 no-bottom-line">
													<input type="text" name="giftAddress">
													<div class="placeholder">
														礼包配送地址
													</div>
												</div>
									</div>
							</div>
							<div class="btnarea-right house_status_div">
							<button class="btn btn-md data-delete">
												<i class="icon-trash-o"></i> 删除
										</button>
								</div>
					</fieldset>
		</div>
		
		<div id="waitForCallDivTempalte" style="display:none;">
			<!-- 待跟进记录 -->
			<div class="form-row withicon waitForCallDiv">
				<div class="tablearea-title">
					<i class="icon-event_available"></i>
						待跟进记录
					</div>
				<div class="tablearea">
					<table>
						<thead>
							<tr>
								<th class="td-date"><span>归档时间</span></th>
								<th class="td-date"><span>下次回访时间</span></th>
								<th><span>备注</span></th>
							</tr>
						</thead>
							<tbody class="strip">
								<tr>
								<td name="fileDate"></td>
								<td name="nextcallTime">2016-09-25</td>
								<td name="info"></td>
								</tr>
								</tbody>
							</table>
							</div>
					</div>
			<!-- 待跟进结束 -->
		</div>

<script type="text/javascript">
//批量上传
$(function(){
	var batchChannelId;
	var batchChannelName;
	$("#batch-label").on("click",function(){
		var channelId=$("#batchAddChannel").val();
		if(channelId==""){alert("请先选择渠道");return false;}
	});
	
	$("#uploadexcel").on("change",function(){
		var _this=this;
		batchChannelId=$("#batchAddChannel").val();
		batchChannelName=$("#batchAddChannel option:selected").text();
		$("#batch-alert").show();
		var formData = new FormData(); 
		formData.append("file",this.files[0]);
		$.ajax({
		    url: 'sysCustomer/customer-batchUpload-save.htmls?operator=upload',
		    type: 'POST',
		    cache: false,
		    data: formData,
		    dataType:"json",
		    processData: false,
		    contentType: false ,
		    success:function(json){
		    	//
		    	$("#batch-alert").hide();
		    	_this.files.length=0;
		    	_this.value="";
		    	initUploadItem(json);
		    	setTotalStatu();
		    }
		});
		
	});
	function initUploadItem(datas){
		var repeat=0;
		$("#uploadItem").empty();
		$(datas).each(function(){
			var className="icon-check_box";
			if(this.exist){
				className="icon-check_box_outline_blank";
			}
			var temp=$("<tr>");
			if(this.exist){
				repeat++;
				temp.addClass("error");
			}
			var td1=$('<td class="checkbox"><span><i class="'+className+'"></i></span></td>');
			var name=$("<td>").text(this.username);
			var phone=$("<td>").text(this.phone);
			var houseLocation=$("<td>").text(this.houseLocation);
			var channel=$("<td>").text(batchChannelName);
			temp.append(td1).append(name).append(phone).append(houseLocation)
			.append(channel);
			temp.data("datas",this);//绑定数据
			$("#uploadItem").append(temp);
		});
		if(datas.length>0){
			$("#upload_detail").show();
			$("#upload_detail").html('一共<span class="total">'+datas.length+
					'</span>条数据，<span class="repeat">'+repeat+'</span>条重复');
			
			$("#sendUploadResult").show();
		}else{
			$("#upload_detail").html("无数据");
			
			$("#sendUploadResult").hide();
		}
	}
	//设置选中状态
	$("#uploadItem").on("click",".checkbox i",function(){
		$(this).toggleClass("icon-check_box_outline_blank icon-check_box");
		setTotalStatu();
	});
	//全选
	$("#totalTag").on("click",function(){
		var check=$("#uploadItem .checkbox i.icon-check_box").length;
		 var total=$("#uploadItem .checkbox i").length;
		 if(check==total){//全部取消
			$("#totalTag").removeClass("icon-indeterminate_check_box icon-check_box")
				.addClass("icon-check_box_outline_blank");
		 	$("#uploadItem .checkbox i").removeClass("icon-check_box icon-indeterminate_check_box")
		 	.addClass("icon-check_box_outline_blank");
		 }else{
			 $("#totalTag").removeClass("icon-check_box_outline_blank icon-indeterminate_check_box")
			 	.addClass("icon-check_box");
			 $("#uploadItem .checkbox i").removeClass("icon-check_box_outline_blank icon-indeterminate_check_box")
			 	.addClass("icon-check_box");
		 }
	});
	
	//设置全选状态
	function setTotalStatu(){
		 var check=$("#uploadItem .checkbox i.icon-check_box").length;
		 var total=$("#uploadItem .checkbox i").length;
		 if(check==total){
			$("#totalTag").removeClass("icon-indeterminate_check_box icon-check_box_outline_blank")
				.addClass("icon-check_box");
		 }else if(check==0){
			 $("#totalTag").removeClass("icon-check_box icon-indeterminate_check_box")
			 	.addClass("icon-check_box_outline_blank");
		 }else{
			 $("#totalTag").removeClass("icon-check_box_outline_blank icon-check_box")
			 	.addClass("icon-indeterminate_check_box");
		 }
	}
	
	
	//上传数据
	$("#uploadDatas").on("click",function(){
		var channelId=batchChannelId;
		if(channelId==""){
			alert("请先选择渠道来源！");return;
		}
		$(this).attr("disabled","disabled");
		var param=getCheckedDates();
		var formdata=new FormData();
		if(param.length==0){
			alert("没有数据");return;
		}
		$(param).each(function(){
			formdata.append("username",this["username"]);
			formdata.append("phone",this["phone"]);
			formdata.append("houseLocation",this["houseLocation"]);
		});
		if(channelId!=""){
			formdata.append("channelId",channelId);
		}
		$("#batch-alert").show();
		$.ajax({
		    url:"sysCustomer/customer-batchUpload-save.htmls?operator=uploadDate" ,
		    type: 'POST',
		    processData: false,
		    contentType:false,               
            data: formdata, 
		    dataType:"json",
		    success:function(json){
		    	$("#batch-alert").hide();
		    	if(json.status==1){
		    		alert(json.message);
		    		$("#batchAdd").modal("hide")
		    		$(".right-container").reload();
		    	}else{
		    		alert(json.message);
		    	}
		    }
		});
	});
	
	//下载数据
	$("#downloadRepeat").on("click",function(){
		var $form=$("<form>").attr("action","sysCustomer/customer-batchUpload-save.htmls?operator=downloadRepeat")
		.attr("method","post").attr("enctype","multipart/form-data");
		var param=getRepeatDates();
		if(param.length==0){
			alert("没有重复数据");return;
		}
		$(param).each(function(){
			$form.append($("<input>").attr("name","username").val(this["username"]));
			$form.append($("<input>").attr("name","phone").val(this["phone"]));
			$form.append($("<input>").attr("name","houseLocation").val(this["houseLocation"]));
		});
		$form.submit();
	});
	
	//获取数据
	function getCheckedDates(){
		var param=[];
		$("#uploadItem .checkbox i.icon-check_box").each(function(){
			var data=$(this).parents("tr").data("datas");//获取绑定的数据
			if(data){
				param.push(data);
			}
		});
		return param;
	}
	//重复数据
	function getRepeatDates(){
		var param=[];
		$("#uploadItem tr.error").each(function(){
			var data=$(this).data("datas");//获取绑定的数据
			if(data){
				param.push(data);
			}
		});
		return param;
	}
});
</script>	

		<!-- 批量上传 -->
		<div class="modal-full modal-batch sf-modal" style="display:none" id="batchAdd">
			<div class="panel">
				<div class="panel-title">
					<span>批量添加新客户</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="关闭窗口">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					<div class="form-area formarea-pad form-info form-lg">
						<div class="notice info closable" id="upload_detail">
							 请先<a href="template/template.xls">下载数据模版</a>，把数据复制进模版相关字段中保存，选择准确<strong>数据来源</strong>，再将文件导入。一张数据表只支持一个数据源。
							<div class="close"></div>
						</div>
						<div class="formsection download-stencil">
							<div class="table-title">
								<span>导入数据预览</span>
								<div class="toolbar">

									<input type="file" name="excel" id="uploadexcel" accept="application/vnd.ms-excel">
						  			<label id="batch-label" for="uploadexcel"><i class="icon-cloud_upload"></i>上传数据</label>
						  			<span><a href="template/template.xls"><i class="icon-file-text"></i>下载数据模版</a></span>
						  			
						  			<select class="sf-select dropdown-container menu-down select" id="batchAddChannel">
												<option value="">无渠道</option>
						  				<c:forEach	items="${channels }" var="channel" varStatus="status">
												<option value="${channel.channelId }" >${channel.name }</option>
											</c:forEach>
						  			</select>
								</div>
								
							</div>
							<table>
										<thead>
											<tr>
												<th class="checkbox">
												<span>
													<i id="totalTag" class="icon-indeterminate_check_box"></i>
												</span>
												</th>
												<th class="td-label"><span>客户姓名</span></th>
												<th class="td-mobile"><span>联系手机</span></th>
												<th><span>房产需求详细地址</span></th>
												<th ><span>渠道来源</span></th>
											</tr>
										</thead>
										
										
										<tbody class="strip" id="uploadItem">
											
										</tbody>


									</table>


								 <div class="form-footer" style="display:none;" id="sendUploadResult">
										<div class="btnarea-center">
											<button class="btn btn-nor btn-iconleft" id="uploadDatas" >
														<i class="icon-check">
														</i>
														录入勾选的用户信息
											</button>

											<button class="btn btn-nor btn-iconleft btn-gray" id="downloadRepeat">
														<i class="icon-cloud-download">
														</i>
														下载重复数据信息
											</button>
										</div>
									</div>

						  </div>
					</div>
				</div>
			</div>
		</div>
	
		
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
				<h1 class="t-rd">客户管理</h1>
			</header>
			<div id="maincontents">
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

var props={
			title:"客户列表",
			url:"myWork/myWork-customer-index.htmls?operator=list",
//			whole:true,
			head:[{name:"name",title:"客户姓名",search:true},
			      {name:"houses",title:"房产地址",style:"define",key:"houseLocation",
				content:"{parseHouse(houses)}",remove:true,search:true},
			      {name:"phone",title:"手机",className:"td-mobile"},
			      {name:"channel",title:"来源",className:"label label-gray",
			    	  style:"label",content:"{channel['name']}",remove:true,search:true},
			      {name:"createDate",title:"录入时间",className:"td-date",
				style:"date",format:"yyyy-MM-dd",sort:true,remove:true,sort_default:true,search:true},
			      {name:"createUser",title:"创建者",style:"define",content:"{createUser['nickname']}",remove:true},
			      {name:"status",title:"状态",style:"label",className:"td-label",
			    	  group:[{className:"label label-gray label-line",title:"待分配",value:"1"}
			    	  		,{className:"label label-gray",title:"等待回访",value:"2"}
			    	  		,{className:"label label-blue",title:"待跟进",value:"3"}
			    	  		,{className:"label label-red",title:"无效客户",value:"4"}
			    	  		,{className:"label",title:"已回访",value:"5"}],search:true}
			      ,  {name:"nextcallTime",title:"下次回访",className:"td-date",
						style:"date",format:"yyyy-MM-dd",sort:true,remove:false}],
			normalOperator:[{icon:"icon-eye",title:"查看",powerCode:"myWork-customer-update",
				callback:function(data){
					toEdit(data);
					}}],
			specialOperator:[{type:1,icon:"icon-mail_outline",title:"发送回访确认短信",powerCode:"myWork-customer-msg",
				callback:function(data){
					customerMsg(data);
				}}
			,{type:1,icon:"icon-phone",title:"呼叫",powerCode:"myWork-customer-call", callback:function(data){
				callCustomer(data);
			}}
			,{type:1,icon:"icon-loop changestatus",title:"状态切换",powerCode:"myWork-customer-status",
				callback:function(data){
					changeCustomerStatus(data);
				}}
			]
			};
var reactData=$("#maincontents").render("Table_data",props,function(react_obj,dom){});
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
										<!-- 	<option value="1">待分配</option> -->
										<!-- 	<option value="2">等待回访</option> -->
											<option value="5">已回访</option>
											<option value="3">待跟进</option>
											<option value="4">无效客户</option>
										</select>
									</div>

									<div class="form-row withicon nextcallTimeDiv" style="display:none;">
										<input type="text" name="nextcallTime" placeholder="下次回访时间" class="datetimepicker">
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
	
	
<script type="text/javascript">
var callphone = new UCObj(window, document);
//外呼
function callCustomer(data){
	var param={};
	param.customerId=data.customerId;
	$.post("myWork/customer-call.htmls",param,function(json){
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
//切换状态
function changeCustomerStatus(data){
	$("#changeStatus").modal("show");
	$("#changeStatus").data("customer",data);
	$("#changeStatus select[name='status'] option").removeAttr("selected");
	$("#changeStatus select[name='status'] option[value='"+data.status+"']").attr("selected","selected");
	$("#changeStatus select[name='status']").change();
	
	$("#changeStatus [name='info']").val(data.nextcallInfo);
	if(data.nextcallTime){
		$("#changeStatus [name='nextcallTime']").val(new Date(parseInt(data.nextcallTime)).format("yyyy-MM-dd"));
	}else{
		$("#changeStatus [name='nextcallTime']").val("");
	}
}
//发送短信
function customerMsg(data){
	$("#sendMsgModal").modal("show");
	$("#sendMsgModal").data("data",data);
}

$(function(){
	
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
		param.nextcallInfo=info;
		$.post("myWork/customer-status.htmls",param,function(json){
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
			$.post("myWork/customer-msg.htmls",param,function(json){
					alert(json.message);
					if(json.status==1){
						$("#sendMsgModal").modal("close");
					}
			},"json");
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
			
			var gift=$(this).find("[name='gift']").val();
			house.gift=gift;
			var giftAddress=$(this).find("[name='giftAddress']").val();
			house.giftAddress=giftAddress;
			
			if(!zoneId){
				alert("请选择区域");error=true;
				return;
			}
			if(!houseInfo){
				alert("请填写楼盘信息");error=true;
				return;
			}
			if(!houseLocation){
				alert("请填写房屋地址");error=true;
				return;
			}
			if(houseTypeId==""){
				alert("请选择房型");error=true;
				return;
			}
			if(isNew==""){
				alert("请选择房屋类别");error=true;
				return;
			}
			if(area==""){
				alert("请填写装修面积");error=true;
				return;
			}
			if(!area.match(/\d+/)){
				alert("装修面积为数字");error=true;
				return;
			}
			if(designType==""){
				alert("请选择专修类别");error=true;
				return;
			}
			if(budgetId==""){
				alert("请选择装修预算");error=true;
				return;
			}
			if(hasSoft==""){
				alert("请选择软装需求");error=true;
				return;
			}
			if(gift==""){
				alert("请选择是否派送了礼物");error=true;
				return;
			}
			
			houses.push(house);
		});
		param.houses=houses;
		if(error){return;}
		$.ajax({type:"post",
				url:"myWork/customer-update.htmls",
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
	$(".editMyCustomer").on("click",".data-delete",function(){
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
			
			$.post("myWork/myWork-customer-index.htmls",param,function(json){
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
		$.post("myWork/myWork-customer-index.htmls",param,function(json){
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
	$("#editModal .dynmic").remove();
	
	$("#editModal #editStatus .label").hide();
	$("#editModal #editStatus .label[value='"+data.status+"']").show();
	
	
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
	
	$("#editModal #customerCode_edit").val(data.customerCode);
	$("#editModal #name_edit").val(data.name);
	$("#editModal #code_edit").val(data.code);
	$("#editModal #phone_edit").val(data.phone);
	
	var channelName="";
	if(data.channel){channelName=data.channel.name};
	$("#editModal #channel_edit").val(channelName);
	$("#editModal #info_edit").val(data.info);
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






</script>

		<div class="modal-full sf-modal editMyCustomer"  id="editModal" style="display: none;">
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
										<input type="tel" id="phone_edit" value="189****837" disabled><label for="edit_phone"><i class="icon-phone_android" required></i></label>
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
												
											<textarea name="log"  disabled readonly>日志内容</textarea>
												<div class="placeholder">
													操作日志
												</div>
											</div>
									</div>
							</div>
							<!-- 发布 -->
							<div class="btnarea-right house_status_div">
											
							</div>
							
					</fieldset>
					
		</div>
		
		<div id="addCustomerHouseTemplate" style="display:none;">
			<fieldset class="withicon data-form dynmic" >
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
	
	function initUploadItem(datas){
		$("#uploadItem").empty();
		$(datas).each(function(){
			var className="icon-check_box";
			if(this.exist){
				className="icon-check_box_outline_blank";
			}
			var temp=$("<tr>");
			if(this.exist){
				temp.addClass("error");
			}
			var td1=$('<td class="checkbox"><span><i class="'+className+'"></i></span></td>');
			var name=$("<td>").text(this.username);
			var phone=$("<td>").text(this.phone);
			var houseLocation=$("<td>").text(this.houseLocation);
			temp.append(td1).append(name).append(phone).append(houseLocation);
			temp.data("datas",this);//绑定数据
			$("#uploadItem").append(temp);
		});
		if(datas.length>0){
			$("#sendUploadResult").show();
		}else{
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
						<div class="notice info closable">
							 请先<a href="template/template.xls">下载数据模版</a>，把数据复制进模版相关字段中保存，选择准确<strong>数据来源</strong>，再将文件导入。一张数据表只支持一个数据源。
							<div class="close"></div>
						</div>
						<div class="formsection download-stencil">
							<div class="table-title">
								<span>导入数据预览</span>
								<div class="toolbar">

									<input type="file" name="excel" id="uploadexcel" accept="application/vnd.ms-excel">
						  			<label for="uploadexcel"><i class="icon-cloud_upload"></i>上传数据</label>
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
														上传选中数据
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
		
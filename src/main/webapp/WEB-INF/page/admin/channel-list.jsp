<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
				<h1 class="t-rd">来源管理</h1>
			</header>
			<div id="maincontents">
			</div>
<script type="text/javascript">
function parseRoles(roles){
	return roles.map(function(temp){return temp.roleName});
}

var props={
			title:"当前渠道列表",
			url:"sysCustomer/channel.htmls?operator=list",
			whole:true,
//			batch:true,
			addBtn:{powerCode:"source-channel-save",callback:function(){$("#addModal").modal("show");$("#addModal").reset();}},
			head:[{name:"name",title:"渠道名字",sort:true},{name:"code",title:"渠道编码"},
			      {name:"channelType",title:"渠道类别",style:"define",content:"{channelType.name}",
					className:"td-labels",remove:true},
					
			      {name:"createUser",title:"创建者",style:"define",content:"{createUser.nickname}",
					className:"td-labels",remove:true},
			      {name:"createDate",title:"创建时间",className:"td-datetime",
				style:"date",format:"yyyy-MM-dd HH:mm",sort:true,remove:true,sort_default:true},
			      {name:"info",title:"备注",remove:true},
			      {name:"status",title:"状态",style:"icon",
			    	  group:[{icon:"icon-remove_circle_outline icon-gray",title:"冻结",value:"0"}
			    	  		,{icon:"icon-check icon-green",title:"激活",value:"1"}]}
			      ],
			normalOperator:[{icon:"icon-pencil",title:"修改",powerCode:"source-channel-update",
				callback:function(data){
					toEdit(data);
					}},
			 			          {icon:"icon-trash-o",title:"删除",powerCode:"source-channel-delete",callback:function(data){
			 			        	  toDelete(data);
			 			          }},
			 			          {icon:"icon-lock_outline",powerCode:"source-channel-status", title:"333",callback:function(data){
			 			        	  toStatus(data);
			 			          }}]
			,table_panel:{title:"渠道分类",url:"sysCustomer/channel.htmls?operator=type_list",content:"{name}",
					editCall:{powerCode:"source-channel-type-update",callback:
							function(data){
								toTypeEdit(data)
							}
						},
					operator:[{icon:"icon-control_point",callback:"",title:"新增分类",powerCode:"source-channel-type-save",
						callback:function(){
							$("#addTypeModal").reset();
							$("#addTypeModal").modal("show");
						}}]
					}
//			,specialOperator:[{icon:"icon-phone",title:"111",callback:function(data){}},
//			          {icon:"icon-chat",title:"222",callback:function(data){}},
//			          {icon:"icon-attachment",title:"333",callback:function(data){}}]
			
			};
var reactData=$("#maincontents").render("Table_data",props,function(react_obj,dom){});
</script>

		<div class="sf-modal modal modal-sm" style="display:none;"  id="addModal">
			<div class="panel">
				<div class="panel-title">
					<span>新增渠道</span>
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
									<input type="text" placeholder="渠道名字" id="name" autocomplete="off" maxlength="50" required><label for="name"><i class="icon-person"></i></label>
									<span></span>
									</div>
									
								<div class="form-row withicon">
										<select class="sf-select select dropdown-container roles menu-down" id="typeId">
											<c:forEach	items="${channelTypes }" var="type" varStatus="status">
												<option value="${type.typeId }" ${status.index==0?"selected='seleceted'":"" }>${type.name }</option>
											</c:forEach>
										</select>
									</div>
									
									<div class="form-row withicon">
										<textarea name="comment" placeholder="渠道备注" id="info" maxlength="200"></textarea>
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
	
	<div class="sf-modal modal modal-sm"  style="display:none;" id="addTypeModal">
			<div class="panel">
				<div class="panel-title">
					<span>新增渠道类型</span>
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
									<input type="text" placeholder="渠道类型" id="typeName" autocomplete="off" maxlength="50" required><label for="name"><i class="icon-person"></i></label>
									<span></span>
									</div>
								</div>
								
					</div>
					
				</div>
				<div class="panel-foot">
					<div class="btnarea-center">
						<button class="btn btn-nor btn-iconleft" id="saveTypeBtn">
									<i class="icon-check">
									</i>
									确认添加
						</button>
					</div>
				</div>
			</div>
		</div>
	
	
		<div class="sf-modal modal modal-sm"  style="display:none;" id="updateTypeModal">
			<div class="panel">
				<div class="panel-title">
					<span>修改渠道类型</span>
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
									<input type="text" placeholder="渠道类型" id="edit_typeName" autocomplete="off" maxlength="50" required><label for="name"><i class="icon-person"></i></label>
									<span></span>
									</div>
								</div>
								
					</div>
					
				</div>
				<div class="panel-foot">
					<div class="btnarea-center">
						<button class="btn btn-nor btn-iconleft" id="editTypeBtn">
									<i class="icon-check">
									</i>
									确认修改
						</button>
					</div>
				</div>
			</div>
		</div>
	
<script type="text/javascript">

$(function(){
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
	
	function showTypeMsg(msg,error){
		$("#addTypeModal .notice").show();
		$("#addTypeModal .notice .text").text(msg);
		if(error){
			$("#addTypeModal .notice").removeClass("info").addClass("error");
		}else{
			$("#addTypeModal .notice").removeClass("error").addClass("info");
		}
	}
	
	
	
	$("#name").validator({require:true,callback:function(){
		showMsg("请输入渠道名字",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	
	$("#typeName").validator({require:true,callback:function(){
		showTypeMsg("请输入渠道类型名字",true);
	},success:function(){
		$("#addTypeModal .notice").hide();
	}});


	$("#saveBtn").on("click",function(){
		var name=$("#name").val();
		var code=$("#code").val();
		var info=$("#info").val();
		var typeId=$("#typeId").val();
		var param={};
		
		if(!$("#name").valide()){
			return;
		}
		
		param.name=name;
		param.code=code;
		param.info=info;
		param.typeId=typeId;
		var _this=this;
		$(_this).attr("disabled","disabled");
		$.post("sysCustomer/channel-save.htmls",param,function(json){
			$(_this).removeAttr("disabled");
			if(json.status==1){//保存成功
				alert(json.message)
				$("#addModal").modal("hide");
				reactData.reload("last");//重新加载数据
			}else{
				showMsg(json.message,true)
			}
		},"json");
	});
	
	
	$("#saveTypeBtn").on("click",function(){//新增类型
		var name=$("#typeName").val();
		var param={};
		
		if(!$("#typeName").valide()){
			return;
		}
		param.name=name;
		var _this=this;
		$(_this).attr("disabled","disabled");
		$.post("sysCustomer/channel-type-save.htmls",param,function(json){
			$(_this).removeAttr("disabled");
			if(json.status==1){//保存成功
				alert(json.message)
				$(".right-container").reload();
				$("#addTypeModal").modal("hide");
			}else{
				showTypeMsg(json.message,true)
			}
		},"json");
	});
	//保存修改
	$("#editSave").on("click",function(){
		var name=$("#editModal #name_edit").val();
		var code=$("#editModal #code_edit").val();
		var info=$("#editModal #info_edit").val();
		
		if(!name){
			alert("请输入渠道名称");
			return;
		}
		if(!code){
			alert("请输入渠道编码");
			return;
		}
		
		var status=$("#status_edit input[name='edit_status']:checked").val();
		var typeId=$("#type_edit input[name='type_edit']:checked").val();
		
		var param={};
		param.channelId=editTemp.channelId;
		param.name=name;
		param.code=code;
		param.info=info;
		param.typeId=typeId;
		param.status=status;
		var _this=this;
		$(_this).attr("disabled","disabled");
		$.post("sysCustomer/channel-update.htmls",param,function(json){
			$(_this).removeAttr("disabled");
			if(json.status==1){//成功，刷新数据
				$("#editModal").modal("hide");
				var pageIndex=reactData.state.pageIndex;
				reactData.reload(pageIndex);//重新加载数据,并跳转当页
			}
		},"json");
		
	});
	
	//重置
	$("#editReset").on("click",function(){
		editReset();
	})
	
	//保存修改
	$("#editTypeBtn").on("click",function(){
		var name=$("#edit_typeName").val();
		if(!name){alert("类型名字不能为空！");return;};
		var param={};
		param.name=name;
		param.typeId=editTemp.typeId;
		var _this=this;
		$(_this).attr("disabled","disabled");
		$.post("sysCustomer/channel-type-update.htmls",param,function(json){
			$(_this).removeAttr("disabled");
				if(json.status==1){//保存成功
					alert(json.message)
					$(".right-container").reload();
					$("#updateTypeModal").modal("hide");
				}else{
					showTypeMsg(json.message,true)
				}
		},"json");
	});
});
var editTemp;
//修改类型
function toTypeEdit(data){
	editTemp=data;
	$("#updateTypeModal").reset();
	$("#updateTypeModal").modal("show");
	$("#updateTypeModal #edit_typeName").val(data.name);
}
//修改
function toEdit(data){
	editTemp=data;
	$("#editModal").modal("show");
	initEditForm(editTemp);
}



function editReset(){
	var data=editTemp;
	initEditForm(data);
}

function initEditForm(data){
	$("#editModal #name_edit").val(data.name);
	$("#editModal #code_edit").val(data.code);
	
	$("#editModal #info_edit").val(data.info);
	
	//初始化状态
	$("#status_edit input[name='edit_status']").each(function(){
		$(this).removeAttr("checked");
		var value=this.value;
			if(value==data.status){
				this.checked="checked";
			}
	});
	//初始化状态
	$("#type_edit input[name='type_edit']").each(function(){
		$(this).removeAttr("checked");
		var value=this.value;
			if(value==data.channelType.typeId){
				this.checked="checked";
			}
	});
}

function toDelete(data){
	var channelId=data.channelId;
	var url="sysCustomer/"+channelId+"/channel-delete.htmls";
	if(confirm("确定删除吗？")){
		$.post(url,{channelId:channelId},function(json){
			alert(json.message);
			if(json.status==1){
				var pageIndex=reactData.state.pageIndex;
				reactData.reload(pageIndex);//重新加载数据,并跳转当页
			}
		},"json");
	}
}

function toStatus(data){
	var channelId=data.channelId;
	var url="sysCustomer/channel/"+channelId+"/status.htmls";
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
		<div class="sf-modal modal modal-lg" id="editModal" style="display: none;">
			<div class="panel">
				<div class="panel-title">
					<span>修改渠道</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="关闭">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					<div class="formarea formarea-pad form-info form-md">
								<div class="formsection">
									<div class="formitem withicon">
									<input type="text" id="name_edit" value="suninverse"><label for="name"><i class="icon-person"></i></label>
										<span></span>
										<div class="placeholder">
											渠道名字
										</div>
									</div>
									<div class="formitem withicon">
										<input type="text" id="code_edit" maxlength="10"  disabled value="克利夫兰"><label for="code"><i class="icon-bubble_chart"></i></label>
										<span></span>
										<div class="placeholder">
											编码
										</div>
									</div>
									
									<div class="form-row withicon">
										<div class="checkarea-title">
											<i class="icon-report"></i> 渠道类别
										</div>
										<div class="checkarea radio" id="type_edit">
											<c:forEach	items="${channelTypes }" var="type" varStatus="status">
												<input type="radio" id="type${status.index }" value="${type.typeId }" name="type_edit">
												<label for="type${status.index }">${type.name }</label>
											</c:forEach>
										</div>
									</div>
									
									<div class="form-row withicon">
										<div class="checkarea-title">
											<i class="icon-report"></i> 渠道状态
										</div>
										<div class="checkarea radio" id="status_edit">
											<input type="radio" id="status1" value="1" name="edit_status"><label for="status1">激活</label>
											<input type="radio" id="status2" value="0" name="edit_status"><label for="status2">冻结</label>
										</div>
									</div>
									
									<div class="form-row withicon">
										<textarea name="comment_edit" id="info_edit" maxlength="200"></textarea>
										<label for="info_edit"><i class="icon-comment"></i></label>
										<div class="placeholder">
											渠道备注
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
									
								</div>
								
					</div>
					
				</div>
				
			</div>
		</div>

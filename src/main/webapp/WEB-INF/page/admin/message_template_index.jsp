<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
				<h1 class="t-rd">短信模板管理</h1>
			</header>
			<div id="maincontents">
			</div>
<script type="text/javascript">
function parseRoles(roles){
	return roles.map(function(temp){return temp.roleName});
}

var props={
			title:"短信模板列表",
			url:"system/message-template-index.htmls?operator=list",
			whole:true,
			addBtn:{powerCode:"message-template-save",callback:function(){$("#addModal").modal("show");$("#addModal").reset()}},
			head:[{name:"sign",title:"签名"}
			      ,{name:"code",title:"短信编号",remove:true}
			      ,{name:"info",title:"内容",remove:true}
			      ,{name:"createDate",title:"录入时间",className:"td-datetime",
						style:"date",format:"yyyy-MM-dd HH:mm",sort:true,remove:true,sort_default:true,search:true}
			      ,{name:"createUser",title:"创建人",style:"define",content:"{createUser['nickname']}",remove:true}
			      ,{name:"comment",title:"备注",remove:false}
			      ,{name:"status",title:"状态",style:"label",className:"td-label",
			    	  group:[{className:"label label-gray label-line",title:"冻结",value:"0"}
			    	  		,{className:"label label-blue",title:"使用中",value:"1"}
			    	  		]} 
				],
			normalOperator:[{icon:"icon-pencil",title:"修改",powerCode:"message-template-update",
								callback:function(data){
									toEdit(data);
									}},
							{icon:"icon-lock_outline",title:"状态",powerCode:"message-template-status",
										callback:function(data){
											toStatus(data);
											}},
			 			{icon:"icon-trash-o",title:"删除",powerCode:"message-template-delete",
							callback:function(data){
			 			       toDelete(data);
			 			}}]
			};
var reactData=$("#maincontents").render("Table_data",props,function(react_obj,dom){});
</script>

		<div class="modal modal-md sf-modal" style="display:none;" id="addModal">
			<div class="panel">
				<div class="panel-title">
					<span>新增短信模板</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="添加短信模板">
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
								<div class="form-row ">
									<input type="text" placeholder="短信签名" name="sign" id="addSign" autocomplete="off" maxlength="20" required>
									<label for="addSign">签名</label>
									<span></span>
									</div>
									<div class="form-row">
									<input type="text" placeholder="短信编号" name="code" id="addCode" autocomplete="off" maxlength="50" required>
									<label for="addCode">编号</label>
									<span></span>
									</div>
									
									
									<div class="form-row">
										<textarea name="info" placeholder="短信内容"  id="addInfo" maxlength="200"></textarea>
										<label for="addInfo">短信内容</label>
									</div>
									
									<div class="form-row">
										<textarea name="comment" placeholder="备注" id="addComment" maxlength="200"></textarea>
										<label for="addComment">备注</label>
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
	
	
	
	$("#addSign").validator({require:true,callback:function(){
		showMsg("请输入短信签名",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	$("#addInfo").validator({require:true,callback:function(){
		showMsg("请输入短信内容",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	$("#addCode").validator({require:true,callback:function(){
		showMsg("请输入短信编号",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	


	$("#saveBtn").on("click",function(){
		var sign=$("#addSign").val();
		var code=$("#addCode").val();
		var info=$("#addInfo").val();
		var comment=$("#addComment").val();
		var param={};
		
		if(!$("#addSign").valide()){
			return;
		}
		if(!$("#addCode").valide()){
			return;
		}
		if(!$("#addInfo").valide()){
			return;
		}
		
		
		param.sign=sign;
		param.code=code;
		param.info=info;
		param.comment=comment;
		var _this=this;
		$(_this).attr("disabled","disabled");
		$.post("system/message-template-save.htmls",param,function(json){
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
	
	

	//保存修改
	$("#editSave").on("click",function(){
		var data=$("#editModal").data("data");
		
		var code=$("#editModal #editCode").val();
		var sign=$("#editModal #editSign").val();
		var info=$("#editModal #editInfo").val();
		var comment=$("#editModal #editComment").val();
		var param={};
		param.templateId=data.templateId;
		param.code=code;
		param.sign=sign;
		param.info=info;
		param.comment=comment;
		var _this=this;
		$(_this).attr("disabled","disabled");
		$.post("system/message-template-update.htmls",param,function(json){
			$(_this).removeAttr("disabled");
				alert(json.message);
			if(json.status==1){//成功，刷新数据
				$("#editModal").modal("hide");
				var pageIndex=reactData.state.pageIndex;
				reactData.reload(pageIndex);//重新加载数据,并跳转当页
			}
			
		},"json");
		
	});
	
	
});

//修改
function toEdit(data){
	
	$("#editModal").modal("show");
	$("#editModal").data("data",data);
	initEditForm(data);
}

function initEditForm(data){
	$("#editModal").initForm(data);
}


function toDelete(data){
	var templateId=data.templateId;
	var url="system/message/"+templateId+"/delete.htmls";
	if(confirm("确定删除吗？")){
		$.post(url,function(json){
			alert(json.message);
			if(json.status==1){
				var pageIndex=reactData.state.pageIndex;
				reactData.reload(pageIndex);//重新加载数据,并跳转当页
			}
		},"json");
	}
}

function toStatus(data){
	var templateId=data.templateId;
	var url="system/message/"+templateId+"/status.htmls";
	var message="";
	var aim=0;
	if(data.status==0){
		message="确定激活吗？";
		aim=1;
	}else{
		message="确定冻结吗？";
		aim=0;
	}
	if(confirm(message)){
		$.post(url,{templateId:templateId},function(json){
			alert(json.message);
			if(json.status==1){
				data.status=aim;
				reactData.fresh();//刷新
			}
		},"json");
	}
}

</script>
<div class="modal modal-md sf-modal" style="display:none;" id="editModal">
			<div class="panel">
				<div class="panel-title">
					<span>修改短信模板</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="修改短信模板">
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
								<div class="form-row">
									<input type="text" placeholder="短信签名" name="sign" id="editSign"
									 autocomplete="off" maxlength="20" required><label for="editSign">短信签名</label>
									<span></span>
									</div>
									<div class="form-row">
									<input type="text" placeholder="短信编号" name="code" id="editCode" autocomplete="off" maxlength="50" required>
									<label for="editCode">短信编号</label>
									<span></span>
									</div>
									
									
									<div class="form-row">
										<textarea name="info" placeholder="短信内容"  id="editInfo" maxlength="200"></textarea>
										<label for="editInfo">短信内容</label>
									</div>
									
									<div class="form-row">
										<textarea name="comment" placeholder="备注" id="editComment" maxlength="200"></textarea>
										<label for="editComment">备注</label>
									</div>
								</div>
					</div>
				</div>
				<div class="panel-foot">
					<div class="btnarea-center">
						<button class="btn btn-nor btn-iconleft" id="editSave">
									<i class="icon-check">
									</i>
									确认修改
						</button>
					</div>
				</div>
			</div>
		</div>

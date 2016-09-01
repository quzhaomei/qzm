<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
				<h1 class="t-rd">年龄段管理</h1>
			</header>
			<div id="maincontents" class="agePage">
			</div>
<script type="text/javascript">
function parseRoles(roles){
	return roles.map(function(temp){return temp.roleName});
}

var props={
			title:"预算列表",
			url:"constant/budget-index.htmls?operator=list",
			whole:true,
//			batch:true,
			addBtn:{powerCode:"constant-budget-save",callback:function(){$("#addModal").modal("show");$("#addModal").reset()}},
			head:[{name:"name",title:"预算"},
			      {name:"start",title:"开始",remove:true},
			      {name:"end",title:"结束",remove:true}],
			normalOperator:[{icon:"icon-pencil",title:"修改",powerCode:"constant-budget-edit",
								callback:function(data){
									toEdit(data);
									}},
			 			{icon:"icon-trash-o",title:"删除",powerCode:"constant-budget-delete",
							callback:function(data){
			 			       toDelete(data);
			 			}}]
			};
var reactData=$("#maincontents").render("Table_data",props,function(react_obj,dom){});
</script>

		<div class="sf-modal modal modal-sm" style="display:none;"  id="addModal">
			<div class="panel">
				<div class="panel-title">
					<span>新增预算</span>
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
									<input type="text" placeholder="预算" id="name" autocomplete="off" maxlength="50" required><label for="name"><i class="icon-person"></i></label>
									<span></span>
									</div>
									<div class="form-row withicon">
									<input type="text" placeholder="开始预算" id="start" autocomplete="off" maxlength="50" required><label for="start"><i class="icon-person"></i></label>
									<span></span>
									</div>
									<div class="form-row withicon">
									<input type="text" placeholder="结束预算" id="end" autocomplete="off" maxlength="50" required><label for="end"><i class="icon-person"></i></label>
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
	
	
	var number_reg=/^\d+$/;
	
	$("#name").validator({require:true,callback:function(){
		showMsg("请输入年龄段名字",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	$("#start").validator({regExp:number_reg,callback1:function(){
		showMsg("请输入数字",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	$("#end").validator({regExp:number_reg,callback1:function(){
		showMsg("请输入数字",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	


	$("#saveBtn").on("click",function(){
		var name=$("#name").val();
		var start=$("#start").val();
		var end=$("#end").val();
		
		var param={};
		
		if(!$("#name").valide()){
			return;
		}
		if(!$("#start").valide()){
			return;
		}
		if(!$("#end").valide()){
			return;
		}
		
		param.name=name;
		param.start=start;
		param.end=end;
		
		$.post("constant/budget-save.htmls",param,function(json){
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
		var start=$("#editModal #start_edit").val();
		var end=$("#editModal #end_edit").val();
		
		if(!name){
			alert("请输入预算名称");
			return;
		}
		if(start&&!start.match(number_reg)){
			alert("预算起始为数字！");
			return;
		}
		if(end&&!end.match(number_reg)){
			alert("预算结束为数字！");
			return;
		}
		
		
		var param={};
		param.budgetId=editTemp.budgetId;
		param.name=name;
		param.start=start;
		param.end=end;
		
		$.post("constant/budget-edit.htmls",param,function(json){
			if(json.status==1){//成功，刷新数据
				$("#editModal").modal("hide");
				var pageIndex=reactData.state.pageIndex;
				reactData.reload(pageIndex);//重新加载数据,并跳转当页
			}else{
				alert(json.message);
			}
		},"json");
		
	});
	
	//重置
	$("#editReset").on("click",function(){
		editReset();
	})
	
});
var editTemp;

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
	$("#editModal #start_edit").val(data.start);
	
	$("#editModal #end_edit").val(data.end);
}


function toDelete(data){
	var budgetId=data.budgetId;
	var url="constant/"+budgetId+"/budget-delete.htmls";
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

</script>
<div class="sf-modal modal modal-sm" style="display:none;"  id="editModal">
			<div class="panel">
				<div class="panel-title">
					<span>修改预算</span>
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
									<input type="text" placeholder="预算" id="name_edit" autocomplete="off" maxlength="50" required><label for="name_edit"><i class="icon-person"></i></label>
									<span></span>
									</div>
									<div class="form-row withicon">
									<input type="text" placeholder="开始预算" id="start_edit" autocomplete="off" maxlength="50" required><label for="start_edit"><i class="icon-person"></i></label>
									<span></span>
									</div>
									<div class="form-row withicon">
									<input type="text" placeholder="结束预算" id="end_edit" autocomplete="off" maxlength="50" required><label for="end_edit"><i class="icon-person"></i></label>
									<span></span>
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

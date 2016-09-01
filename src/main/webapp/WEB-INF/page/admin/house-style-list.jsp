<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
				<h1 class="t-rd">房型管理</h1>
			</header>
			<div id="maincontents">
			</div>
<script type="text/javascript">
function parseRoles(roles){
	return roles.map(function(temp){return temp.roleName});
}

var props={
			title:"房型列表",
			url:"constant/style-index.htmls?operator=list",
			whole:true,
//			batch:true,
			addBtn:{powerCode:"constant-style-save",callback:function(){$("#addModal").modal("show");$("#addModal").reset()}},
			head:[{name:"name",title:"预算"},
			      {name:"price",title:"价格",remove:true}],
			normalOperator:[{icon:"icon-pencil",title:"修改",powerCode:"constant-style-edit",
								callback:function(data){
									toEdit(data);
									}},
			 			{icon:"icon-trash-o",title:"删除",powerCode:"constant-style-delete",
							callback:function(data){
			 			       toDelete(data);
			 			}}]
			};
var reactData=$("#maincontents").render("Table_data",props,function(react_obj,dom){});
</script>

		<div class="sf-modal modal modal-sm" style="display:none;"  id="addModal">
			<div class="panel">
				<div class="panel-title">
					<span>新增房型</span>
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
									<input type="text" placeholder="房型" id="name" autocomplete="off" maxlength="50" required><label for="name"><i class="icon-person"></i></label>
									<span></span>
									</div>
									<div class="form-row withicon">
									<input type="text" placeholder="价格" id="price" autocomplete="off" maxlength="50" required><label for="price"><i class="icon-person"></i></label>
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
		showMsg("请输入房型名字",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	$("#price").validator({require:true,callback:function(){
		showMsg("请输入价格",true);
	},regExp:number_reg,callback1:function(){
		showMsg("价格为数字",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	


	$("#saveBtn").on("click",function(){
		var name=$("#name").val();
		var price=$("#price").val();
		var param={};
		
		if(!$("#name").valide()){
			return;
		}
		if(!$("#price").valide()){
			return;
		}
		
		param.name=name;
		param.price=price;
		
		$.post("constant/style-save.htmls",param,function(json){
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
		var price=$("#editModal #price_edit").val();
		
		if(price&&!price.match(number_reg)){
			alert("价格为数字");
			return;
		}
		
		var param={};
		param.styleId=editTemp.styleId;
		param.name=name;
		param.price=price;
		
		$.post("constant/style-edit.htmls",param,function(json){
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
	$("#editModal #price_edit").val(data.price);
	
}


function toDelete(data){
	var styleId=data.styleId;
	var url="constant/"+styleId+"/style-delete.htmls";
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
					<span>修改房型</span>
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
									<input type="text" placeholder="房型" id="name_edit" autocomplete="off" maxlength="50" required><label for="name_edit"><i class="icon-person"></i></label>
									<span></span>
									</div>
									<div class="form-row withicon">
									<input type="text" placeholder="价格" id="price_edit" autocomplete="off" maxlength="50" required><label for="price_edit"><i class="icon-person"></i></label>
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

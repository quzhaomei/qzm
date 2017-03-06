<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
		<h1 class="t-rd">房型管理</h1>
			</header>
			<div id="maincontents">
				<div id="roomtypelist"></div>
				
					<div id="pricelist" class="panel">
						
					</div>
			</div>
			
			
<script type="text/javascript">
var props= {
		index:false,
		url:"system/style-index.htmls?operator=list",
		title:"房型列表",
		heads:[
		       {name:"name",title:"房型"}
		       
		       <c:forEach items="${types }" varStatus="status" var="type">
				,{name:"${type.typeId }",title:"${type.name }"}
				</c:forEach>
		       ],
		 saveBtn:{powerCode:"constant-style-save",save:function(){$("#addModal").modal("show");$("#addModal").reset()}},
		 btn:[{title:"修改房型",icon:"icon-pencil",powerCode:"constant-style-edit",btnClass:"btn-square",
			 onClick:function(event,data,orignl,react){toEdit(data)}
		 },{title:"删除房型",icon:"icon-lock_outline",powerCode:"constant-style-delete",btnClass:"btn-square",
			 onClick:function(event,data,orignl,react){
				 toDelete(data);
		 }} ],
		};
		
var reactData=$("#roomtypelist").render("Table_li",props,function(dom){
	
	$("#roomtypelist").on("click",".tbody ul",function(){
		var data=$(this).data("data");
		$.post("system/style-index.htmls?operator=styleId",{styleId:data.styleId},function(json){
			$("#pricelist").data("data",data);
			getCompanyHtml(data,json);
		},"json");
	});
	
	var task=setInterval(function(){
		if($(dom).find(".tbody ul:eq(0)")[0]){
			$(dom).find(".tbody ul:eq(0)").click();
			clearInterval(task);
		}
	}, 200);
	
});
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
									<div class="form-row">
									<input type="text" placeholder="房型描述" id="name" autocomplete="off" maxlength="50" required>
									<label for="name">描述</label>
									<span></span>
									</div>
									 <c:forEach items="${types }" varStatus="status" var="type">
									<div class="form-row">
									<input type="text" class="number" placeholder="${type.name }默认价格" 
									typeId="${type.typeId }"  autocomplete="off" maxlength="50" required>
									<label for="">${type.name }价格</label>
									<span></span>
									</div>
									</c:forEach>
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
	$(".number").each(function(){
		$(this).validator({require:true,callback:function(){
			showMsg("请输入价格",true);
		},regExp:number_reg,callback1:function(){
			showMsg("价格为数字",true);
		},success:function(){
			$("#addModal .notice").hide();
		}});
	});
	
	


	$("#saveBtn").on("click",function(){
		var param={};
		var name=$("#name").val();
		
		if(!$("#name").valide()){
			return;
		}
		var set=[];
		for(var index=0;index<$("#addModal .number").length;index++){
			var temp=$("#addModal .number")[index];
			if(!$(temp).valide()){
				return;
			}
			var obj={};
			obj.typeId=$(temp).attr("typeId");
			obj.price=temp.value;
			set.push(obj);
		}
		param.defaultSet=set;
		param.name=name;
		var _this=this;
		$(_this).attr("disabled","disabled");
		$.ajax({type:"post",
			url:"system/style-save.htmls",
			dataType:"json",
			contentType:"application/json",
			data:JSON.stringify(param),
			success:function(json){
				$(_this).removeAttr("disabled");
				$("#addModal").modal("hide");
				alert(json.message);
				if(json.status==1){//保存成功
					reactData.reload(
							function(){
								$("#roomtypelist .table-area ul:last").click();	
								
							});//重新加载数据,并跳转当页
				}
			}
		})
		
		
	});
	
	

	//保存修改
	$("#editSave").on("click",function(){
		var data=$("#editModal").data("data");
		var name=$("#editModal #name_edit").val();
		if(!name){alert("请输入房型描述");return};
		var set=[];
		for(var index=0;index<$("#editModal .number").length;index++){
			var temp=$("#editModal .number")[index];
			if(!temp.value){alert("请输入价格");return;}
			if(!temp.value.match(/^\d+$/)){alert("价格为整数;");return;}
			var obj={};
			obj.typeId=$(temp).attr("typeId");
			obj.price=temp.value;
			set.push(obj);
		}
		var param={};
		param.styleId=data.styleId;
		param.defaultSet=set;
		param.name=name;
		var _this=this;
		$(_this).attr("disabled","disabled");
		$.ajax({type:"post",
			url:"system/style-edit.htmls",
			dataType:"json",
			contentType:"application/json",
			data:JSON.stringify(param),
			success:function(json){
				$(_this).removeAttr("disabled");
				$("#editModal").modal("hide");
				alert(json.message);
				if(json.status==1){//保存成功
					reactData.reload();//重新加载数据,并跳转当页
				}
			}
		})
		
		
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
	$("#editModal").data("data",data);
	initEditForm(editTemp);
}




function initEditForm(data){
	$("#editModal").initForm(data);
}


function toDelete(data){
	var styleId=data.styleId;
	var url="system/"+styleId+"/style-delete.htmls";
	if(confirm("确定删除吗？")){
		$.post(url,function(json){
			alert(json.message);
			if(json.status==1){
				reactData.reload();//重新加载数据,并跳转当页
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
									<div class="form-row">
									<input type="text" name="name" placeholder="房型描述" id="name_edit" 
									autocomplete="off" maxlength="50" required><label for="name_edit">房型</label>
									<span></span>
									</div>
									<c:forEach items="${types }" varStatus="status" var="type">
									<div class="form-row">
									<input type="text" class="number" typeId="${type.typeId }" placeholder="${type.name }默认价格" 
									name="${type.typeId }" autocomplete="off" maxlength="50" required>
									<label for="">${type.name }价格</label>
									<span></span>
									</div>
									</c:forEach>
									
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
		
<script type="text/javascript">
function getCompanyHtml(style,datas){
	$("#pricelist").empty().append( $("#companyStyleTemplate").html());
	
	$("#pricelist").find(".styleName").text(style.name);
	var template=$("#companyStyleTemplate");
	var head=[];
	$("#pricelist .pro-head th").each(function(){
		head.push($(this).attr("name"))
	});
	$("#pricelist .pro-body").empty();
	
	$(datas).each(function(){
		var $tr=$("<tr>").data("data",this);
		for(var index=0;index<head.length;index++){
			var name=head[index];
			var className="";
			if(index!=0){className="td-label"};
			var value=this[name];
			if(value==-1){value='<span class="label label-gray">无软装业务</span>'}
			else if(!value){value="未设置"}
			var $td=$("<td>").addClass(className).html(value);
			
			$tr.append($td);
		}
		$("#pricelist .pro-body").append($tr);
	});
	
}
$(function(){
	
	//先去查询有没有全权限
	$("#pricelist").on('mouseenter', 'tr', function(event) {
		$(this).children("td:eq(0)").append("<div class='operation'><span class='btn-edit edit hardprice'><i class='icon-pencil'></i></span></div>");
	}).on('mouseleave', 'tr', function(event) {
		event.preventDefault();
		$('#pricelist .operation').remove();
	});
	
	$("#pricelist").on("click",".operation .edit",function(){
		//初始化companyType
		$("#companyPriceModal .typeModal").empty();
		var data=$(this).parents("tr").data("data");
		$("#companyPriceModal").find(".typeDate").removeClass("hide").find("input").val("");//重置
		
		for(var name in data){
			var value=data[name];
			if(value&&value=="-1"){
			$("#companyPriceModal").find(".typeDate[typeId='"+name+"']").addClass("hide");
			}else{
			$("#companyPriceModal").find(".typeDate[typeId='"+name+"'] input").val(value);//初始化有值的
			}
		}	
		var style=$("#pricelist").data("data");
		$("#companyPriceModal .storeName").text(data.storeName+" "+style.name+" 佣金设置")
		$("#companyPriceModal").modal("show");
		$("#companyPriceModal").data("data",data);
	});
	
	//提交更新
	$("#companyPriceModalBtn").on("click",function(){
		var param={};
		var data=$("#companyPriceModal").data("data");//价格
		var style=$("#pricelist").data("data");//房型
		var prices=[];
		
		var $aim=$("#companyPriceModal").find(".typeDate:not(.hide) input");
		for(var index=0;index<$aim.length;index++){
			var _this=$aim[index];
			var typeId=$(_this).parent(".typeDate").attr("typeId");
			var value=_this.value;
			var info=$(_this).next(".styleName").text();
			if(!value){
				alert("请输入"+info); return;
			}
			if(!value.match(/^\d+$/)){
				alert(info+"为整数！");return;
			}
			var temp={};
			temp.typeId=typeId;
			temp.price=value;
			temp.companyId=data.companyId
			prices.push(temp);
		}
		
		param.styleId=style.styleId;
		param.companySet=prices;
		
		$.ajax({type:"post",
			url:"system/company-style-edit.htmls",
			dataType:"json",
			contentType:"application/json",
			data:JSON.stringify(param),
			success:function(json){
				$("#companyPriceModal").modal("hide");
				alert(json.message);
				if(json.status==1){//保存成功
					$("#roomtypelist .table-area ul.active").click();					
				}
			}
		})
		
	});
	
});
</script>

<div style="display:none;" id="companyStyleTemplate">
	<div class="panel-title">
		<span>
			相关商家默认佣金 <strong class="styleName">一房一厅</strong>
		</span>
			</div>
			<table>
			<thead>
				<tr class="head pro-head">
					<th name="storeName" class="td-label"><span>商户名</span></th>
						<c:forEach items="${types }" varStatus="status" var="type">
							<th name="${type.typeId }" class="td-label"><span>${type.name }默认佣金</span></th>
						</c:forEach>
					</tr>
					</thead>
				<tbody class="pro-body">
					<tr>
					<td>亦凡装饰</td>
					<td class="td-label">400</td>
					<td>王五</td>
					<td>2016-06-20 17:30</td>
					</tr>
				</tbody>
		</table>
</div>	

<div class="sf-modal modal modal-sm modal-softprice hide" id="companyPriceModal">
	<div class="panel">
		<div class="panel-title">
			<span class="storeName">商户 亦凡装饰软装佣金</span>
			<div class="btnarea-right full">
				<button class="btn-square modal-close" title="取消">
						<i class='icon-close icon-gray'></i>
				</button>
			</div>
		</div>
		<div class="panel-content" class="typeModal">
			<div class="formarea formarea-pad">
				
				<c:forEach items="${types }" varStatus="status" var="type">
					<div typeId="${type.typeId }" class="typeDate form-row form-row-full">
						<input type="text" placeholder="修改${type.name }佣金" value="400">
						<label class="styleName">${type.name }佣金</label>
					</div>
				</c:forEach>
				
			</div>
		</div>
		<div class="panel-foot">
			<div class="btnarea-center">
				<button class="btn btn-nor btn-iconleft" id="companyPriceModalBtn">
					<i class="icon-check">
						</i>确认修改
				</button>
			</div>
			</div>
		</div>
</div>

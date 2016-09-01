<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
				<h1 class="t-rd">设计师管理</h1>
			</header>
			<div id="maincontents" class="companyPage">
			</div>
<script type="text/javascript">
function parseRoles(roles){
	return roles.map(function(temp){return temp.roleName});
}

var props={
			title:"设计师列表",
			url:"company/designer.htmls?operator=list",
			whole:true,
//			batch:true,
			addBtn:{powerCode:"designer-save",callback:function(){$("#addModal").modal("show");}},
			head:[
			      {name:"name",title:"设计师姓名"},
			      {name:"companyName",title:"公司名称",sort:true},
			      {name:"phone",title:"接单手机",className:"td-phone",remove:true},
			      {name:"createDate",title:"入驻时间",className:"td-date",
						style:"date",format:"yyyy-MM-dd",sort:true,remove:true,sort_default:true},
			      {name:"accept",title:"接单状态",style:"label",
			    	  group:[{title:"关闭",value:"0"}
			    	  		,{title:"开启",value:"1"}]}
			      ],
			normalOperator:[{icon:"icon-pencil",title:"修改",powerCode:"source-channel-update",
				callback:function(data){
					toEdit(data);
					}}
			 		]
//			,table_panel:{title:"公司业务类别",url:"company/company-company.htmls?operator=typeList",content:"{name}",
//					editCall:{powerCode:"company-company-type-update",callback:
//							function(data){
//								toTypeEdit(data)
//							}
//						},
//					operator:[{icon:"icon-control_point",callback:"",title:"新增分类",powerCode:"company-company-type-save",
//						callback:function(){
//							$("#addTypeModal").modal("show");
//						}}]
//					}
//			,specialOperator:[{icon:"icon-phone",title:"111",callback:function(data){}},
//			          {icon:"icon-chat",title:"222",callback:function(data){}},
//			          {icon:"icon-attachment",title:"333",callback:function(data){}}]
			};
var reactData=$("#maincontents").render("Table_data",props,function(react_obj,dom){});
</script>

		<div class="sf-modal modal modal-md" style="display:none;"  id="addModal">
			<div class="panel">
				<div class="panel-title">
					<span>添加店铺</span>
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
									<input type="text" placeholder="设计师姓名" name="name" required><label for="name"><i class="icon-shop"></i></label>
									<span></span>
									</div>
									<div class="form-row withicon">
									<input type="text" placeholder="所属设计公司" name="companyName" ><label for="companyName"><i class="icon-business_center"></i></label>
									<span></span>
									</div>
									<div class="form-row withicon">
										<input type="tel" placeholder="接单手机" name="phone" required><label for="phone"><i class="icon-phone" required></i></label>
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
	
	
	
	$("#addModal input[name='name']").validator({require:true,callback:function(){
		showMsg("请输入设计师名字",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	
	$("#addModal input[name='phone']").validator({require:true,callback:function(){
		showMsg("请输入接单手机",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	


	$("#saveBtn").on("click",function(){
		var name=$("#addModal input[name='name']").val();
		var companyName=$("#addModal input[name='companyName']").val();
		var phone=$("#addModal input[name='phone']").val();
		
		if(!$("#addModal input[name='name']").valide()){
			return;
		}
		if(!$("#addModal input[name='phone']").valide()){
			return;
		}
		var param={};
		param.name=name;
		param.companyName=companyName;
		param.phone=phone;
		
		$.post("company/designerSave.htmls",param,function(json){
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
		var designerId=$("#editModal [name='designerId']").val();
		var name=$("#editModal [name='name']").val();
		var phone=$("#editModal [name='phone']").val();
		var managerId=$("#editModal [name='manager.adminUserId']").val();
		var companyName=$("#editModal [name='companyName']").val();
		
		var accept=$("#editModal [name='accept']:checked").val();
		
		
		if(!phone){
			alert("请输入接单手机");
			return;
		}
		if(!managerId){
			alert("请选择接单帐号");
			return;
		}
		var param={};
		param.designerId=designerId;
		param.name=name;
		param.phone=phone;
		param.managerId=managerId;
		param.companyName=companyName;
		param.accept=accept;
		//处理区域
		param.zoneIds=[];
		getCheckedZone(zoneChooseTemp,param.zoneIds);
		$.ajax({type:"post",
			url:"company/designer-update.htmls",
			dataType:"json",
			contentType:"application/json",               
            data:JSON.stringify(param), 
			success:function(json){
				if(json.status==1){//保存成功
					alert(json.message)
					$(".right-container").reload();
					$("#editModal").modal("hide");
				}else{
					showTypeMsg(json.message,true)
				}
			}
		});
	
	});
	

	
	
	//重置
	$("#editReset").on("click",function(){
		editReset();
	})
	
	//保存修改
	$("#editTypeBtn").on("click",function(){
		var name=$("#edit_typeName").val();
		if(!name){alert("类别名字不能为空！");return;};
		var param={};
		param.name=name;
		param.typeId=editTemp.typeId;
		$.post("company/company-type-update.htmls",param,function(json){
				if(json.status==1){//保存成功
					alert(json.message)
					$(".right-container").reload();
					$("#updateTypeModal").modal("hide");
				}else{
					showTypeMsg(json.message,true)
				}
		},"json");
	});
	
	
	//设置区域
	$(".editCompanyModel").on("click","#addZone",function(){
		$("#company_choose_zone").addClass("hide");
		$("#company_choose_zone_all").removeClass("hide");
		
		$("#company_choose_zone_root li:eq(0)").click();//默认看第一个
		
		$("#zone_operator").html(
		'<button id="company_choose_save" class="btn btn-sm save">确定</button>'+
		'<button class="btn btn-sm btn-gray" id="company_choose_reset">重置</button>'
		);
		
	});
	
	//保存选择
	$(".editCompanyModel").on("click","#company_choose_save",function(){
		$("#company_choose_zone").removeClass("hide");
		$("#company_choose_zone_all").addClass("hide");
		
		$("#company_choose_zone").empty();//清空
		//初始化页面显示
		initCompanyZone(zoneChooseTemp[0],$("#company_choose_zone"));
		$("#zone_operator").html(
				'<button id="addZone" class="btn btn-sm save">添加区域</button>'
				)
	});
	
	//重置区域
	$(".editCompanyModel").on("click","#company_choose_reset",function(){
		zoneReset();
	});
	
	//区域级联
	$(".editCompanyModel").on("click","#company_choose_zone_root li",function(){
		$(this).siblings("li").removeClass("current");
		$(this).addClass("current");
		var zone=$(this).data("zone");
		$("#company_choose_zone_children label").remove();
		$("#company_choose_zone_children input").remove();
		for(var index=0;index<zone.children.length;index++){
			var tempZone=zone.children[index];
			var $input=$("<input>").attr("type","checkBox").attr("id","zone_temp_child"+index);
			var $label=$("<label>").attr("for","zone_temp_child"+index).text(tempZone.name);
			$("#company_choose_zone_children").append($input).append($label);
			$input.data("zone",tempZone);//绑定数据
			if(tempZone.tempId){
				 $input[0].checked="checked";
			}
			tempZone.parent=zone;
		}
	});
	
	//选择区域－全选
	$(".editCompanyModel").on("click",".checkall button",function(){
		$("#company_choose_zone_children input").each(function(){
			this.checked="checked";
			var zone=$(this).data("zone");
			zone.tempId=1;//选中
		});
			initRootZone($("#company_choose_zone_root li.current"));
	});
	
	//选择区域－单选
	$(".editCompanyModel").on("click","#company_choose_zone_children input",function(){
			var zone=$(this).data("zone");
			if(this.checked){
				zone.tempId=1;//选中
			}else{
				zone.tempId=0;//取消
			}
			
			zone.parent.tempId=0;//取消全选状态，重新判断
			initRootZone($("#company_choose_zone_root li.current"));
	});
	//删除区域
	$(".editCompanyModel").on("click","#company_choose_zone span",function(){
		var zone=$(this).data("zone");
		if(zone&&confirm("确定要删除 "+zone.name+" 吗？")){
			$(this).remove();
			deleteZone(zone);
			initAllCompanyZone(zoneChooseTemp[0].children);
		}		
	});
	
});
var editTemp;

var zoneChooseTemp;
//修改
function toEdit(data){
	editTemp=data;
	initEditForm(editTemp);
	//查找所有的接单区域
	var param={};
	param.designerId=data.designerId;
	$.post("company/designZone.htmls",param,function(json){
		zoneChooseTemp=json;
		$("#editModal").modal("show");
		$("#company_choose_zone").empty();
		if(zoneChooseTemp.length>0){//初始化分单区域,root默认为第一条，上海
			initCompanyZone(zoneChooseTemp[0],$("#company_choose_zone"));
			initAllCompanyZone(zoneChooseTemp[0].children);
		}
	},"json")
}

function initCompanyZone(root,$obj){
	if(root.tempId){
		var clazz="level-2";
		if(root.children&&root.children.length>0){
			clazz="level-1";
		}
		var $span=$("<span>").text(root.name).addClass('label label-sm withcross').addClass(clazz);
		$span.data("zone",root);
		$obj.append($span);
	}else{
		if(root.children&&root.children.length>0){
			for(var index=0;index<root.children.length;index++){
				initCompanyZone(root.children[index],$obj);
			}	
		}
	}
}

//初始化所有的备选区域
function initAllCompanyZone(zoneArr){
	$("#company_choose_zone_root").empty();
	if(zoneArr){
		for(var index=0;index<zoneArr.length;index++){
			var tempZone=zoneArr[index];
			var rootZone=$("<li>").text(tempZone.name);
			rootZone.data("zone",tempZone);
			$("#company_choose_zone_root").append(rootZone);
			
			initRootZone(rootZone);
		}
	}
}
//初始化选择状态
function initRootZone($root){
	var tempZone=$root.data("zone");
	$root.removeClass("checked partially-checked");
	if(tempZone.tempId){
		$root.addClass("checked")
	}else{
		var selectCount=0;
		for(var inner=0;inner<tempZone.children.length;inner++){
			var innerZone=tempZone.children[inner];
			if(innerZone.tempId){
				selectCount++;
			}
		}
		if(selectCount==tempZone.children.length){
			tempZone.tempId=1;//全选标志
			$root.addClass("checked")
		}else if(selectCount>0){//部分选择
			tempZone.tempId="";
			$root.addClass("partially-checked")
		}
	}
}

function deleteZone(zone){
	if(zone.tempId){
		zone.tempId=0;//删除标志
		if(zone.children){
			for(var index=0;index<zone.children.length;index++){
				var temp=zone.children[index];
				deleteZone(temp);
			}
		}
	}
}

function editReset(){
	var data=editTemp;
	initEditForm(data);
	zoneReset();
}
function zoneReset(){
	var param={};
	param.designerId=editTemp.designerId;
	$.post("company/designZone.htmls",param,function(json){
		zoneChooseTemp=json;
		$("#company_choose_zone").removeClass("hide");
		$("#company_choose_zone_all").addClass("hide");
		
		$("#company_choose_zone").empty();//清空
		//初始化页面显示
		initCompanyZone(zoneChooseTemp[0],$("#company_choose_zone"));
		initAllCompanyZone(zoneChooseTemp[0].children);
		$("#zone_operator").html(
				'<button id="addZone" class="btn btn-sm save">添加区域</button>'
				)
	},"json")
}

//初始化
function initEditForm(data){
	$("#editModal").initForm(data);
	$("#editModal .shopName").text("设计师 "+data.name+" 信息")
}
//获取所有区域
function getCheckedZone(zones,zonesArr){
	if(zones&&zones.length>0){
		for(var index=0;index<zones.length;index++){
			var temp=zones[index];
			if(temp.tempId){
				zonesArr.push(temp.zoneId);
			}
			if(temp.children&&temp.children.length>0){
				getCheckedZone(temp.children,zonesArr);
			}
		}
	}

}




</script>
		<div class="sf-modal modal-full item-detail editCompanyModel" id="editModal" style="display: none;">
			<div class="panel">
				<div class="panel-title">
					<span class="shopName">某某店铺信息</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="返回公司列表">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					<div class="formarea formarea-pad form-info form-lg">
								
								<div class="formsection">
									<div>

									<div class="formitem withicon">
									<input type="text" value="" id="edit_i"name="designerId" disabled><label for="edit_i"><i class="icon-label_outline"></i></label>
										<span></span>
										<div class="placeholder">
											设计师编号
										</div>
									</div>
									<div class="formitem withicon">
									<input type="text" name="name" id="edit_sn" maxlength="20" value=""><label for="edit_sn"><i class="icon-shop"></i></label>
										<div class="placeholder">
											设计师姓名
										</div>
									</div>

									<div class="formitem withicon">
										<input type="tel" id="phone" name="phone" value=""><label for="phone"><i class="icon-phone_android" required></i></label>
										<span></span>
										<div class="placeholder">
											接单手机
										</div>
									</div>
									
									<div class="formitem withicon no-bd-right">
										<select class="sf-select dropdown-container menu-down select" name="manager.adminUserId">
											<option value="">请选择</option>
											<c:forEach items="${keepers }" var="keeper">
												<option value="${keeper.adminUserId }">${keeper.nickname }</option>
											</c:forEach>
										</select>
										<label for=""><i class="icon-account_circle"></i></label>
										
										<div class="placeholder">
											接单账号
										</div>
									</div>
									



									<div class="formitem-2 withicon no-bd-bottom">
									<input type="text" value="" id="companyName" name="companyName"><label for="companyName"><i class="icon-business_center"></i></label>
										<div class="placeholder">
											所属公司
										</div>
									</div>
									


									<div class="form-row withicon areachoose">
										<div class="labelarea" id="company_choose_zone">
											<span class="label label-sm level-1 withcross">长宁</span>
											<span class="label label-sm level-1 withcross">徐汇</span>
											<span class="label label-sm level-2 withcross">静安寺</span>
										</div>
										<div class="arealist tabarea hide" id="company_choose_zone_all">
											<ul class="tabs withcheck" id="company_choose_zone_root">
												<li class="checked">静安</li>
												<li class="partially-checked current">长宁</li>
												<li class="checked">卢湾</li>
											</ul>
											<div class="checklables tabcontent" id="company_choose_zone_children">
												<div class="checkall checked">
													<button class="btn btn-sm">全选</button>
												</div>
												<input type="checkbox" id="role1" checked><label for="role1">花木</label>
												<input type="checkbox" id="role2"><label for="role2">陆家嘴</label>
											
											</div>
										</div>
										<label for="shop-comment"><i class="icon-map3"></i></label>
										<div class="placeholder">
											接单区域
										</div>
										<div class="btnarea-right" id="zone_operator">
											<button class="btn btn-sm addarea" id="addZone">
											 添加区域
											</button>
										</div>
										
									</div>


									<div class="form-row withicon">
										<div class="checkarea-title">
											<i class="icon-report"></i> 接单状态
										</div>
										<div class="checkarea radio">
											<input type="radio" id="status1" value="1" name="accept" ><label for="status1">接单中</label>
											<input type="radio" id="status2" value="0" name="accept"><label for="status2">停止接单</label>
										</div>
									</div>

									
									<!--  <div class="form-row withicon">
										<textarea name="comment" id="shop-comment">备注内容</textarea>
										<label for="shop-comment"><i class="icon-comment"></i></label>
										<div class="placeholder">
											店铺备注
										</div>
									</div>-->
								

									
									
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
			</div>

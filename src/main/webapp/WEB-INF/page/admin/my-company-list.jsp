<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
				<h1 class="t-rd">公司店铺管理</h1>
			</header>
			<div id="maincontents" class="companyPage">
			</div>
<script type="text/javascript">
function parseTypes(types){
	return types.map(function(temp){return temp.name});
}

var props={
			title:"我的店铺",
			url:"company/company-my-company.htmls?operator=list",
			whole:true,
			head:[
			      {name:"companyName",title:"公司全称"},
			      {name:"storeName",title:"店铺名字",sort:true},
			      {name:"storePhone",title:"店铺电话",className:"td-phone",remove:true},
			      {name:"storeAddress",title:"店铺地址",remove:true},
			      {name:"companyTypes",title:"业务类别",style:"labels",content:"{parseTypes(companyTypes)}",remove:true},
			      {name:"createDate",title:"入驻时间",className:"td-date",
					style:"date",format:"yyyy-MM-dd",sort:true,remove:true,sort_default:true},
			      {name:"keeperName",title:"负责人"},
			      {name:"accept",title:"接单状态",style:"label",
			    	  group:[{title:"关闭",value:"0"}
			    	  		,{title:"开启",value:"1"},{title:"注销",value:"2"}]}
			      ],
			normalOperator:[{icon:"icon-pencil",title:"修改",powerCode:"my-company-company-update",
				callback:function(data){
						toEdit(data);
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
	
<script type="text/javascript">

$(function(){
	//转换select控件
	$(".sf-select").each(function(){
		$(this).smartSelect();
	});
	

	
	//logo设置
	$("#companylogo").on("change",function(){
		var _this=this;
		var formData = new FormData(); 
		formData.append("file",this.files[0]);
		$.ajax({
			   url: 'welcome/uploadSource/companyLogo.htmls',
			   type: 'POST',
			   cache: false,
			   data: formData,
			   dataType:"json",
			   processData: false,
			   contentType: false ,
			   success:function(json){
				   if(json.error==0){
					   $("#logo_path").attr("src",json.url);
					   $("#editModal input[name='logo']").val(json.url);
				   }else{
					   alert(json.message);
				   }
			  }
		});
	});
	
	//保存修改
	$("#editSave").on("click",function(){
		var companyId=$("#editModal [name='companyId']").val();
		var logo=$("#editModal [name='logo']").val();
		var storeType=$("#editModal [name='storeType']").val();
		var companyType=[];
		$("#editModal [name='companyTypeIds']:checked").each(function(){
			companyType.push(this.value);
		});
		var accept=$("#editModal [name='accept']:checked").val();
		
		if(!storeType){
			alert("请选择店铺类型");
			return;
		}
		var param={};
		param.companyId=companyId;
		param.logo=logo;
		param.storeType=storeType;
		param.companyTypeIds=companyType;
		param.accept=accept;
		//处理区域
		param.zoneIds=[];
		getCheckedZone(zoneChooseTemp,param.zoneIds);
		var _this=this;
		$(_this).attr("disabled","disabled");
		$.ajax({type:"post",
			url:"company/my-company-update.htmls",
			dataType:"json",
			contentType:"application/json",               
            data:JSON.stringify(param), 
			success:function(json){
				$(_this).removeAttr("disabled");
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

function toEdit(data){
	editTemp=data;
	initEditForm(editTemp);
	//查找所有的接单区域
	var param={};
	param.companyId=data.companyId;
	$.post("company/companyZone.htmls",param,function(json){
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
	param.companyId=editTemp.companyId;
	$.post("company/companyZone.htmls",param,function(json){
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
	$("#editModal .shopName").text(data.storeName+" 店铺信息")
	$("#companylogo").val("");
	if(data.logo){
		$("#logo_path").attr("src",data.logo);//设置logo
	}else{
		$("#logo_path").attr("src","/static/images/shoplogo.png");//设置logo
	}
	//初始化
	$("#editModal input[name='companyTypeIds']:checkbox").each(function(){
		var _this=this;
		this.checked=false;
		var value=this.value;
		$(data.companyTypes).each(function(){
			if(this.typeId==value){
				_this.checked="checked";
			}
		});
	});
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
									<input type="text" value="" id="edit_i"name="companyId" disabled><label for="edit_i"><i class="icon-label_outline"></i></label>
										<span></span>
										<div class="placeholder">
											店铺编号
										</div>
									</div>
									<div class="formitem withicon">
									<input type="text" name="storeName" id="edit_sn" disabled="disabled" maxlength="20" value=""><label for=""edit_sn""><i class="icon-shop"></i></label>
										<div class="placeholder">
											店铺名
										</div>
									</div>

									<div class="formitem uploadimg">
										<span class="logoimg" >
											<img src="" id="logo_path">
										</span>
										<input type="hidden"   name="logo"/>
										<input type="file" id="companylogo" accept="image/*" />
										<label for="companylogo">设置店铺logo</label>
										
									</div>

									<div class="formitem withicon no-bd-right">
										<select name="storeType" id="edit_st"
										 class="sf-select dropdown-container menu-down select">
											<option value="0">普通店铺</option>
											<option value="1">旗舰店</option>
										</select>
										<label for="edit_st"><i class="icon-bubble_chart"></i></label>
										
										<div class="placeholder">
											店铺类型
										</div>
									</div>


									<div class="formitem withicon">
									<input type="text" name="companyName" disabled="disabled" id="edit_cn" value="" maxlength="50"><label for="edit_cn"><i class="icon-business_center"></i></label>
										<div class="placeholder">
											公司全称
										</div>
									</div>
									
									<div class="formitem-2 withicon no-bd-right">
									<input type="text" name="storeAddress" disabled="disabled" id="edit_sa" value="" maxlength="200"><label for="edit_sa"><i class="icon-location-pin"></i></label>
										<div class="placeholder">
											店铺地址
										</div>
									</div>
									
									<div class="formitem withicon">
									<input type="text" name="account" disabled="disabled" id="edit_account" value="" maxlength="50"><label for="edit_account"><i class="icon-dollor"></i></label>
										<div class="placeholder">
											余额
										</div>
									</div>

									
									<div class="formitem withicon no-bd-bottom">
										<input type="tel" name="storePhone" disabled="disabled" id="edit_sp" value=""><label for="edit_sp"><i class="icon-phone" required></i></label>
										<div class="placeholder">
											店铺电话
										</div>
									</div>
									<div class="formitem withicon no-bd-bottom bd-right">
									<input type="text" name="keeperName" disabled="disabled" id="edit_kn" value=""><label for="edit_kn"><i class="icon-person"></i></label>
										<div class="placeholder">
											店铺负责人
										</div>
									</div>
									<div class="formitem withicon">
										<input type="tel" id="edit_kp" disabled="disabled" name="keeperPhone" value=""><label for="keeperPhone"><i class="icon-phone_android" required></i></label>
										<span></span>
										<div class="placeholder">
											负责人手机
										</div>
									</div>


										
									</div>

									<div class="form-row withicon">
											<div class="checkarea-title">
												<i class="icon-anchor-point"></i>店铺业务类别
											</div>
											<div class="checkarea radio" >
												<c:forEach items="${types }" var="type" varStatus="status">
													<input type="checkbox" name="companyTypeIds" id="edit_ctc${status.index }" value="${type.typeId }">
													<label for="edit_ctc${status.index }">${type.name }</label>
												</c:forEach>
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

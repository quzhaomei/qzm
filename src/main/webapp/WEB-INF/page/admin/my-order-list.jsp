<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
				<h1 class="t-rd">我的订单</h1>
			</header>
			<div id="maincontents" class="customerPage">
			</div>
<script type="text/javascript">


var props={
			title:"订单列表",
			url:"myCompanyOrder/company-my-order.htmls?operator=list",
			whole:false,//真分页
			head:[
			      {name:"orderCode",title:"订单编号",search:true},
			      {name:"company",title:"接单店铺",style:"define",content:"{company.storeName}"},
			      {name:"companyType",className:"td-label",title:"类型",style:"define",
			    	  content:"{companyType.name}",remove:true,search:true,key:"companyTypeName"},
			      {name:"house",title:"房型",search:true,style:"define",content:"{house['houseStyle']['name']}",key:"houseStyleName"},
			      {name:"price",title:"订单单价"},
			      {name:"info",title:"订单备注",remove:false},
			      {name:"createDate",title:"生成时间",className:"td-datetime",search:true,remove:true,
			    	  style:"date",format:"yyyy-MM-dd HH:mm",sort:true,sort_default:true},
			      {name:"acceptStatus",title:"订单状态",style:"label",className:"td-label",
			    	  group:[{className:"label",title:"已接单",value:"1"}
			    	  		,{className:"label label-gray",title:"未接单",value:"0"}],search:true},
			    {name:"isSuccess",title:"结果",style:"label",className:"td-label",
					 group:[{className:"label label-red",title:"失败",value:"2"}
						    ,{className:"label label-gray",title:"未确认",value:"0"}
						    ,{className:"label label-green",title:"成功",value:"1"}],search:true}  
			      ],
			normalOperator:[{icon:"icon-eye",title:"详细",powerCode:"company-my-order-get",
				callback:function(data){
					show_detail(data);
					}}
			],
			specialOperator:[{type:1,icon:"icon-insert_drive_file",filter:{column:"acceptStatus",values:[0]},
				title:"接单",powerCode:"company-my-order-get",
				callback:function(data){
					getOrder(data);
				}},
			        {type:1,icon:"icon-check-square-o",
					filter:[{column:"acceptStatus",values:[1]},{column:"isSuccess",values:[0]}],
					title:"接单结果反馈",powerCode:"company-my-order-get-result",
					callback:function(data){
				getOrderResult(data);
				}}
				]};
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
function show_detail(order){
	var param={};
	param.operator="one";
	param.orderId=order.orderId;
	$.post("myCompanyOrder/company-my-order.htmls",param,function(json){
		initDetail(order,json.house,json.zone_parent,json.zone_root);
		$("#detail_modal").modal("show");
		$("#detail_modal").data("data",order)
		if(order.acceptStatus==0){
			$("#getOrderBtn").parent().show();
		}else{
			$("#getOrderBtn").parent().hide();
		}
	},"json");
}

function getOrder(order){
	if(confirm("确认接单吗？")){
		var param={};
		param.orderId=order.orderId;
		$.post("myCompanyOrder/company-my-order-get.htmls",param,function(json){
				alert(json.message);
				if(json.status==1){
					reactData.reload(reactData.state.pageIndex);//重新加载数据,并跳转当页
				}
		},"json");
	}
}
function getOrderResult(order){
	$("#orderResult").reset();
	$("#orderResult").modal("show");
	$("#orderResult").data("data",order);
}

function initDetail(order,house,zone_parent,zone_root,zone_detail){
	$("#detail_modal .order_title").text("订单"+order.orderCode+"详情");
	//初始化订单状态
	$("#detail_modal .order_status div[acceptStatus]").hide();
	$("#detail_modal .order_status div[acceptStatus='"+order.acceptStatus+"']").show();
	$("#detail_modal .order_price").text("¥"+order.price);
	$("#detail_modal").initForm(order);
	if(order.isSuccess==0){
		$("#isSuccess_detail").val("未确认");
		}else if(order.isSuccess==1){
			$("#isSuccess_detail").val("成功");
			}else{
			$("#isSuccess_detail").val("失败");
			}
	$("#house_detail").initForm(house);
	
	if(house.designType==0){
		$("#designType_detail").val("半包");
	}else if(house.designType==1){
		$("#designType_detail").val("全包");
	}else{
		$("#designType_detail").val("");
	}

	if(house.hasSoft==0){
		$("#hasSoft_detail").val("否");
	}else if(house.hasSoft==1){
		$("#hasSoft_detail").val("是");
	}else{
		$("#hasSoft_detail").val("");
	}
	
	if(house.isNew==0){
		$("#isNew_detail").val("旧房");
	}else if(house.isNew==1){
		$("#isNew_detail").val("新房");
	}else{
		$("#isNew_detail").val("");
	}
	
	if(house.customer.gender==0){
		$("#gender_detail").val("女");
	}else if(house.customer.gender==1){
		$("#gender_detail").val("男");
	}else{
		$("#gender_detail").val("不详");
	}
	$("#zone_parent").val(zone_parent);
	$("#zone_root").val(zone_root);
}


$(function(){
	$(".sf-select").each(function(){
		$(this).smartSelect();
	});
	$.datetimepicker.setLocale('ch');
	$('.datetimepicker').datetimepicker({
		lang:'ch',
		timepicker:false,
		format:'Y-m-d',
		formatDate:'Y-m-d',
		yearStart: '2016',
		minDate:'-2016/08/02', // yesterday is minimum date
	});
	$("#btn_result").on("click",function(){
		var order=$("#orderResult").data("data");
		var info_result=$("#info_result").val();
		var isSuccess_result=$("#isSuccess_result").val();
		if(isSuccess_result==""){alert("请选择结果！");return;}
		var param={};
		param.orderId=order.orderId;
		param.info=info_result;
		param.isSuccess=isSuccess_result;
		var _this=this;
		$(_this).attr("disabled","disabled");
		$.post("myCompanyOrder/company-my-order-get-result.htmls",param,function(json){
			$(_this).removeAttr("disabled");
			alert(json.message);
			if(json.status==1){
				$("#orderResult").modal("hide");
				reactData.reload(reactData.state.pageIndex);
			}
		},"json")
	});
	
	$("#getOrderBtn").on("click",function(){
		var order=$("#detail_modal").data("data");
		if(confirm("确认接单吗？")){
			var param={};
			param.orderId=order.orderId;
			var _this=this;
			$(_this).attr("disabled","disabled");
			$.post("myCompanyOrder/company-my-order-get.htmls",param,function(json){
				$(_this).removeAttr("disabled");
					alert(json.message);
					if(json.status==1){
						reactData.reload(reactData.state.pageIndex);//重新加载数据,并跳转当页
					}
					$("#detail_modal").modal("hide");
			},"json");
		}
	});
});
</script>
<div class="sf-modal modal modal-sm modal-changestatus" style="display:none;" id="orderResult">
			<div class="panel">
				<div class="panel-title">
					<span>接单结果反馈</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="关闭窗口">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					
					<div class="formarea formarea-pad">
					<div class="notice info closable">
							 结果确认后不可修改，请谨慎操作
							<div class="close"></div>
					</div>
								<div class="formsection">
									
									<div class="form-row withicon">
									
										<select class="sf-select select dropdown-container menu-down status" id="isSuccess_result">
											<option value="">请选择接单结果</option>
											<option value="1">接单成功</option>
											<option value="2">接单失败</option>
										</select>
									</div>

									<div class="form-row withicon">
										<textarea name="result" placeholder="接单结果备注" id="info_result"></textarea>
										<label for="info_result"><i class="icon-comment"></i></label>
									</div>
									
								</div>
								
					</div>
					
				</div>
				<div class="panel-foot">
					<div class="btnarea-center">
						<button class="btn btn-nor btn-iconleft" id="btn_result">
								<i class="icon-check">
								</i>
								确认反馈结果
						</button>
					</div>
				</div>
			</div>
		</div>
<div class="sf-modal modal-full item-detail modal-toorder"  style="display:none;" id="detail_modal">
			<div class="panel">
				<div class="panel-title">
					<span><strong class="order_title">订单0192382详情</strong>
					<div class="label-right order_status">
						<div class="label" acceptStatus="1">
							已接单
						</div>
						
						<div class="label label-red" acceptStatus="2">
							据单
						</div>
						<div class="label label-gray"  acceptStatus="0">
							未接单
						</div>
					</div>
					</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="返回全部订单">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					<div class="formarea formarea-pad form-info form-lg">

						<div class="notice info closable">
							 	本单费用<span class="label label-red label-lg order_price">¥200</span>，
							 	确认接单后，系统会即时从您的虚拟财务帐户中扣除本订单金额，我们稍后会根据您的财务记录与您进行线下结算。
							<div class="close"></div>
						</div>
								
							<div class="formsection">
									<div class="readonly">

									<div class="formitem withicon">
									<input type="text" name="orderCode" disabled><label for=""><i class="icon-label_outline"></i></label>
										<span></span>
										<div class="placeholder">
											订单编号
										</div>
									</div>
									<div class="formitem withicon">
									<input type="text" name="company.storeName" value="" disabled><label><i class="icon-shop"></i></label>
										<div class="placeholder">
											接单商户
										</div>
									</div>
									
									<div class="formitem withicon">
									<input type="text" name="price" value="" disabled><label><i class="icon-coin-yen"></i></label>
										<div class="placeholder">
											订单金额
										</div>
									</div>

									<div class="formitem withicon">
									<input type="text" name="createUser.nickname" value="" disabled><label><i class="icon-person"></i></label>
										
										<div class="placeholder">
											派单业务员
										</div>
									</div>

									</div>

									<div class="form-row-special readonly">
												<div class="formitem-1 no-bottom-line withicon">
													<input type="text" value="成功" id="isSuccess_detail" disabled>
													<label><i class="icon-star"></i></label>
													<div class="placeholder">
														业务是否成功
													</div>
												</div>
												<div class="formitem-3 withicon no-bottom-line no-right-line">
													<input type="text" name="info" value="info" disabled>
													<label><i class="icon-comment"></i></label>
													<div class="placeholder">
														业务情况备注
													</div>
												</div>
											</div>	


									

								</div>

								
								<div class="formsection" id="house_detail">
									<div class="readonly">

									<div class="formitem withicon">
									<input type="text" value="019238" name="customer.customerCode" disabled><label for="usercode"><i class="icon-label_outline"></i></label>
										<span></span>
										<div class="placeholder">
											客户编码
										</div>
									</div>
									<div class="formitem withicon">
									<input type="text" value="suninverse" name="customer.name" disabled><label for="username"><i class="icon-person"></i></label>
										<div class="placeholder">
											客户姓名
										</div>
									</div>
									
									<div class="formitem withicon">
									<input type="text" value="" id="gender_detail"  disabled><label for="gender"><i class="icon-people"></i></label>
										<div class="placeholder">
											性别
										</div>
									</div>

									<div class="formitem withicon">
									<input type="text" value="1872637238" name="customer.phone" disabled><label for="username"><i class="icon-phone"></i></label>
										
										<div class="placeholder">
											客户手机
										</div>
									</div>

									
										
									</div>

								
									
									<fieldset class="withicon readonly">
										<div class="field-title">
												<i class="icon-home3"></i> 需求信息
										</div>
										<div class="field-content">
											<div>
												<div class="formitem">
													<input type="text" name="houseId" value="ND129837" disabled>
													<span></span>
													<div class="placeholder">
														需求ID
													</div>
												</div>
												<div class="formitem">
													<input type="text" id="zone_root" value="上海市" disabled>
														<div class="placeholder">
															城市
														</div>
												</div>
												<div class="formitem">
													<input type="text" id="zone_parent" value="静安区" disabled>
														<div class="placeholder">
															区域
														</div>
												</div>
												

											<div class="formitem">
													<input type="text" name="zone.name" value="静安寺" disabled>
														<div class="placeholder">
															区域细分
														</div>
												</div>

											

												<div class="formitem">
													<input type="text" name="houseInfo" value="静安枫景" disabled>
													
													<div class="placeholder">
														楼盘信息
													</div>
												</div>

												<div class="formitem">
													<input type="text" name="houseLocation" value="南京西路1877号3栋5楼" disabled>
												
													<div class="placeholder">
														房屋地址
													</div>
												</div>

										


												<div class="formitem">
													<input type="text" name="isNew" id="isNew_detail" value="" disabled>
												
													<div class="placeholder">
														房屋类别
													</div>
												</div>


												<div class="formitem">
													<input type="text" name="houseStyle.name" value="" disabled>
												
													<div class="placeholder">
														房型
													</div>
												</div>
												<div class="formitem">
													<input type="text" name="area" value="" disabled>
													<span>m²</span>
													<div class="placeholder">
														装修面积
													</div>
												</div>

												<div class="formitem">
													<input type="text" name="designType" id="designType_detail" value="" disabled>
													
													<div class="placeholder">
														装修类别
													</div>
												</div>
												<div class="formitem">
													<input type="text"  name="budget.name" value="" disabled>
													<div class="placeholder">
														装修预算
													</div>
												</div>
												<div class="formitem">
												<input type="text" id="hasSoft_detail" value="" disabled>
													
													<div class="placeholder">
														软装需求
													</div>
												</div>
											</div>

											<div class="form-row">
												<textarea name="comment"  id="user-comment" disabled>备注内容</textarea>
												<div class="placeholder">
													需求备注
												</div>
											</div>
											
										</div>
									</fieldset>

									<div class="form-footer">
										<div class="btnarea-center">
											<button class="btn btn-nor btn-iconleft" id="getOrderBtn">
														<i class="icon-check">
														</i>
														立刻接单
											</button>
										</div>
									</div>
								</div>
						</div>
					</div>
				</div>
				</div>
	
		
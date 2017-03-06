<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
				<h1 class="t-rd">硬装需求管理</h1>
			</header>
			<div id="maincontents">
			</div>
<script type="text/javascript">

var props={
			title:"硬装需求列表",
			url:"requireTotal/hard-index.htmls?operator=list",
//			whole:true,
			head:[ 
				{name:"houseLocation",title:"房产信息",search:true},
				{name:"customerCode",style:"define",title:"客户编号",content:"{customer.customerCode}",search:true},
				{name:"customerName",style:"define",title:"客户姓名",content:"{customer.name}",className:"td-name",search:true},
				{name:"comment",title:"需求备注"},
				{name:"createUser",title:"创建者",style:"define",
						content:"{createUser['nickname']}",remove:true,search:true},
				{name:"createDate",title:"生成时间",className:"td-datetime",
					  style:"date",format:"yyyy-MM-dd HH:mm",sort:true,remove:true,sort_default:true,search:true},
				{name:"comment",title:"已派数 : 已接数",className:"td-label",style:"define",
					   content:'<span class="label">{orderNum}</span><span class="label label-gray">{orderUnAccept}</span>'}
					  ,
				{name:"status",title:"状态",style:"label",className:"td-label",search:true,
						  group:[
				 	         {className:"label label-gray label-line",title:"待派单",value:"1"}
				 	  		,{className:"label label-gray",title:"已派单",value:"2"}
				 	  		,{className:"label label-red",title:"关闭",value:"3"}]}
			      ],
			normalOperator:[{icon:"icon-eye",title:"详细",powerCode:"requireTotal-soft-set-index",
				callback:function(data){
					toEdit(data);
					}}],
			 specialOperator:[{type:1,icon:"icon-insert_drive_file toorder",powerCode:"requireTotal-hard-set-order",title:"派单",
				 filter:{column:"status",values:[1,2]},
				 callback:function(data){
					toOrder(data);
					 }
			 },{type:1,icon:"icon-lock_outline close-needs",title:"关闭",powerCode:"requireTotal-hard-set-close",
				 filter:{column:"status",values:[1]},
					callback:function(data){
							closeRequire(data);
						}}]
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

		<div class="sf-modal modal modal-sm" style="display:none;"  id="addModal">
			
		</div>
	
<script type="text/javascript">
//关闭订单
function closeRequire(data){
	if(data.status==3){alert("需求已经关闭，无法操作！");return;}
	
	if(confirm("确定关闭该需求吗？")){
		$.post("requireTotal/hard-close.htmls",{houseId:data.houseId},function(json){
			alert(json.message);
			if(json.status==1){
				var pageIndex=reactData.state.pageIndex;
				reactData.reload(pageIndex);//重新加载数据,并跳转当页
			}
		},"json")
	}
}

//详细
function toEdit(data){
	$.post("requireTotal/soft-index.htmls?operator=one",{houseId:data.houseId},function(json){
		$.extend(data,json);
		$("#orderDetailModal").initForm(data);
		$("#orderDetailModal").data("data",data);
		$("#orderDetailModal").modal("show");
		//初始化订单状态
		$("#orderStatus_detail .label[status]").hide();
		$("#orderStatus_detail .label[status='"+data.status+"']").show();
	},"json");
}

//派单
function toOrder(data){
	
	$.post("requireTotal/hard-index.htmls?operator=one",{houseId:data.houseId},function(json){
		$.extend(data,json);
		
		$("#toOrderModal").initForm(data);
		$("#searchStoreNameBtn").val("");
		$("#toOrderModal").data("data",data);
		$("#toOrderModal").modal("show");
		//初始化订单状态
		$("#orderStatus .label[status]").hide();
		$("#orderStatus .label[status='"+data.status+"']").show();
		//初始化店铺
		initCompany(json.companyPage.param);
	},"json");
}

function initCompany(arr){
	$("#companyServiceList").empty();
	$(arr).each(function(){
		var $tr=$("<tr>").data("data",this);
		$tr.append($("<td>").text(this.storeName))
		$tr.append($("<td>").text(this.storeFee).addClass("td-label"))
		$tr.append($("<td>").text(this.price).addClass("td-label"))
		$tr.append($("<td>").text(this.monthCount).addClass("td-label"))
		if(this.tempId){
			$tr.append($("<td>").text("符合").addClass("td-label"));
		}else{
			$tr.append($("<td class='warning'>").text("已超出").addClass("td-label"))
		}
		$tr.append($("<td>").text(this.companyTypes.map(function(temp){return temp.name}).join(",")).addClass("td-label"));
		if(this.orderId){
			$tr.append($("<td class='td-label'>").html('<span class="label">已派</span>'));
		}else{
			$tr.append($("<td>").html('<span><i class="icon-add_box"></i></span>').addClass("td-icon ops"));
		}
		$("#companyServiceList").append($tr);
	});
}

$(function(){
	//转换select控件
	$(".sf-select").each(function(){//设置select
		$(this).smartSelect();
	});
	
	$("#companyServiceList").on('mouseenter', 'td.ops', function(event) {
		$(this).children('span').children('i').hide();
		$(this).children('span').append("<div class='toolicons'><i class='doCompanyOrder icon-insert_drive_file confirmorder' title='派单'></i>"+
		"<i class='icon-gift freeCompanyOrder'  title='赠送'></i></div>");
	}).on('mouseleave', '.ops', function(event) {
		$(this).find('.toolicons').remove();
		$(this).children('span').children('i').show();
	});
	
	$("#searchStoreNameBtn").on("click",function(){//店铺搜索
		var data= $("#toOrderModal").data("data");
		var storeName=$("#searchStoreName").val();
		var param={};
		param.houseId=data.houseId
		if(storeName){
			param.storeName=storeName;
		}
		$.post("requireTotal/hard-index.htmls?operator=company",param,function(json){
			//初始化店铺
			initCompany(json.param);
		},"json");
	});
	
	//派单
	$("#toOrderModal").on("click",".doCompanyOrder",function(){
		var company=$(this).parents("tr").data("data");
		$("#doOrderModal").data("data",company);
		$("#doOrderModal .storeName").text("派单给"+company.storeName);
		$("#orderPrice").val(company.price).removeAttr("disabled");
		$("#doOrderModal").modal("show");
	})
	
	//赠送派单
	$("#toOrderModal").on("click",".freeCompanyOrder",function(){
		var company=$(this).parents("tr").data("data");
		$("#doOrderModal").data("data",company);
		$("#doOrderModal .storeName").text("派单给"+company.storeName);
		$("#orderPrice").val(1).attr("disabled","disabled");
		$("#doOrderModal").modal("show");
	})
	
	//派单操作
	$("#doOrderModalBtn").on("click",function(){
		var house= $("#toOrderModal").data("data");
		if(house.status==3){alert("需求已经关闭，无法派单！");return;}
		
		
		var company=$("#doOrderModal").data("data");
		var price=$("#orderPrice").val();
		if(!price){alert("请添入订单价格！");return}else if(!price.match(/^\d+$/)){alert("订单价格为整数");return;}
		if(!company.tempId){
			var con=confirm("该需求已超出店铺接单范围，确定继续派单吗？");
			if(!con){return};
		}
		
		var param={};
		param.houseId=house.houseId;
		param.companyId=company.companyId;
		param.price=$.trim(price);
		var _this=this;
		$(_this).attr("disabled","disabled");
		$.post("requireTotal/hard-order.htmls",param,function(json){
			$(_this).removeAttr("disabled");
			alert(json.message);
			var pageIndex=reactData.state.pageIndex;
			reactData.reload(pageIndex);//重新加载数据,并跳转当页
			
			if(json.status==1){
				$("#orderStatus .label").hide();
				$("#orderStatus .label[status=2]").show();
				$("#toOrderModal textarea[name='log']").html(json.data.log)
				$("#doOrderModal").modal("hide");
				$("#searchStoreNameBtn").click();
			}
		},"json")
	})
	
});

</script>
<div class="sf-modal modal-full item-detail modal-toorder" style="display:none;"  id="orderDetailModal">
			<div class="panel">
				<div class="panel-title">
					<span>硬装需求详细
					
					<div class="label-right" id="orderStatus_detail">
						
						<div class="label" status="2" style="display:none;">
							已派单
						</div>
						
						<div class="label label-red" style="display:none;" status="3">
							关闭
						</div>
						<div class="label label-gray" style="display:none;" status="1">
							待派单
						</div>
					</div>
					
					</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="返回我的硬装需求">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					<div class="formarea formarea-pad form-info form-lg">
								
								<div class="formsection">
									<div class="readonly">

									<div class="formitem withicon">
									<input type="text" name="customer.customerCode" value="019238" disabled><label for="usercode"><i class="icon-label_outline"></i></label>
										<span></span>
										<div class="placeholder">
											客户编码
										</div>
									</div>
									<div class="formitem withicon">
									<input type="text" name="customer.name" value="suninverse" disabled><label for="username"><i class="icon-person"></i></label>
										<span></span>
										<div class="placeholder">
											客户姓名
										</div>
									</div>
									<div class="formitem withicon">
									<input type="text" name="customerAge" disabled><label for="username"><i class="icon-cake"></i></label>
										<span></span>
										<div class="placeholder">
											客户年龄
										</div>
									</div>
									<div class="formitem withicon">
									<input type="text" name="customerGender" disabled><label for="gender"><i class="icon-people"></i></label>
										<span></span>
										<div class="placeholder">
											性别
										</div>
									</div>
									
										
									</div>

								
									
									<fieldset class="withicon readonly">
										<div class="field-title">
												<i class="icon-home3"></i> 房产信息
										</div>
										<div class="field-content">
											<div>
												<div class="formitem">
													<input type="text" value="" name="houseId" disabled>
													<span></span>
													<div class="placeholder">
														需求ID
													</div>
												</div>
												<div class="formitem">
													<input type="text" value="" name="zone1" disabled>
														<div class="placeholder">
															城市
														</div>
												</div>
												<div class="formitem">
													<input type="text" value="" name="zone2" disabled>
														<div class="placeholder">
															区域
														</div>
												</div>
												

											<div class="formitem">
													<input type="text" value="" name="zone3" disabled>
														<div class="placeholder">
															区域细分
														</div>
												</div>

											

												<div class="formitem">
													<input type="text" value="" name="houseInfo" disabled>
													
													<div class="placeholder">
														楼盘信息
													</div>
												</div>

												<div class="formitem">
													<input type="text" value="" name="houseLocation" disabled>
												
													<div class="placeholder">
														房屋地址
													</div>
												</div>

												<div class="formitem">
													<input type="text" value="" name="houseStyle.name" disabled>
												
													<div class="placeholder">
														房型
													</div>
												</div>


												<div class="formitem">
													<input type="text"  name="isNewInfo" disabled>
												
													<div class="placeholder">
														房屋类别
													</div>
												</div>




												<div class="formitem">
													<input type="text" name="area" disabled>
													<span>m²</span>
													<div class="placeholder">
														装修面积
													</div>
												</div>

												<div class="formitem">
													<input type="text" name="designTypeInfo" disabled>
													
													<div class="placeholder">
														装修类别
													</div>
												</div>
						

												<div class="formitem">
													<input type="text" value="" name="budget.name" disabled>
													
													<div class="placeholder">
														装修预算
													</div>
												</div>


												<div class="formitem">
												<input type="text" value="" name="hasSoftInfo" disabled>
													
													<div class="placeholder">
														软装需求
													</div>
												</div>
												
											</div>



											<div class="form-row">
												<textarea name="comment" id="user-comment" disabled></textarea>
												<div class="placeholder">
													需求备注
												</div>
											</div>

											<div class="form-row-special">
												<div class="formitem-1 no-bottom-line">
													<input type="text" value="" name="giftInfo" disabled>
													
													<div class="placeholder">
														礼包是否已配送
													</div>
												</div>
												<div class="formitem-3 no-bottom-line">
													<input type="text" value="" name="giftAddress" disabled>
													<div class="placeholder">
														礼包配送地址
													</div>
												</div>
											</div>
											<div class="form-row">
												<textarea name="log"  disabled></textarea>
												<div class="placeholder">
													操作日志
												</div>
											</div>


										</div>
									</fieldset>

									</div>
									</div>
								</div>
				</div>
		</div>


<!-- 派单 -->
<div class="sf-modal modal-full item-detail modal-toorder" style="display:none;"  id="toOrderModal">
			<div class="panel">
				<div class="panel-title">
					<span>需求派单
					
					<div class="label-right" id="orderStatus">
						
						<div class="label" status="2" style="display:none;">
							已派单
						</div>
						
						<div class="label label-red" style="display:none;" status="3">
							关闭
						</div>
						<div class="label label-gray" style="display:none;" status="1">
							待派单
						</div>
					</div>
					
					</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="返回我的硬装需求">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					<div class="formarea formarea-pad form-info form-lg">
								
								<div class="formsection">
									<div class="readonly">

									<div class="formitem withicon">
									<input type="text" name="customer.customerCode" value="019238" disabled><label for="usercode"><i class="icon-label_outline"></i></label>
										<span></span>
										<div class="placeholder">
											客户编码
										</div>
									</div>
									<div class="formitem withicon">
									<input type="text" name="customer.name" value="suninverse" disabled><label for="username"><i class="icon-person"></i></label>
										<span></span>
										<div class="placeholder">
											客户姓名
										</div>
									</div>
									<div class="formitem withicon">
									<input type="text" name="customerAge" disabled><label for="username"><i class="icon-cake"></i></label>
										<span></span>
										<div class="placeholder">
											客户年龄
										</div>
									</div>
									<div class="formitem withicon">
									<input type="text" name="customerGender" disabled><label for="gender"><i class="icon-people"></i></label>
										<span></span>
										<div class="placeholder">
											性别
										</div>
									</div>
									
										
									</div>

								
									
									<fieldset class="withicon readonly">
										<div class="field-title">
												<i class="icon-home3"></i> 房产信息
										</div>
										<div class="field-content">
											<div>
												<div class="formitem">
													<input type="text" value="" name="houseId" disabled>
													<span></span>
													<div class="placeholder">
														需求ID
													</div>
												</div>
												<div class="formitem">
													<input type="text" value="" name="zone1" disabled>
														<div class="placeholder">
															城市
														</div>
												</div>
												<div class="formitem">
													<input type="text" value="" name="zone2" disabled>
														<div class="placeholder">
															区域
														</div>
												</div>
												

											<div class="formitem">
													<input type="text" value="" name="zone3" disabled>
														<div class="placeholder">
															区域细分
														</div>
												</div>

											

												<div class="formitem">
													<input type="text" value="" name="houseInfo" disabled>
													
													<div class="placeholder">
														楼盘信息
													</div>
												</div>

												<div class="formitem">
													<input type="text" value="" name="houseLocation" disabled>
												
													<div class="placeholder">
														房屋地址
													</div>
												</div>

												<div class="formitem">
													<input type="text" value="" name="houseStyle.name" disabled>
												
													<div class="placeholder">
														房型
													</div>
												</div>


												<div class="formitem">
													<input type="text"  name="isNewInfo" disabled>
												
													<div class="placeholder">
														房屋类别
													</div>
												</div>




												<div class="formitem">
													<input type="text" name="area" disabled>
													<span>m²</span>
													<div class="placeholder">
														装修面积
													</div>
												</div>

												<div class="formitem">
													<input type="text" name="designTypeInfo" disabled>
													
													<div class="placeholder">
														装修类别
													</div>
												</div>
						

												<div class="formitem">
													<input type="text" value="" name="budget.name" disabled>
													
													<div class="placeholder">
														装修预算
													</div>
												</div>


												<div class="formitem">
												<input type="text" value="" name="hasSoftInfo" disabled>
													
													<div class="placeholder">
														软装需求
													</div>
												</div>
												
											</div>



											<div class="form-row">
												<textarea name="comment" id="user-comment" disabled></textarea>
												<div class="placeholder">
													需求备注
												</div>
											</div>

											<div class="form-row-special">
												<div class="formitem-1 no-bottom-line">
													<input type="text" value="" name="giftInfo" disabled>
													
													<div class="placeholder">
														礼包是否已配送
													</div>
												</div>
												<div class="formitem-3 no-bottom-line">
													<input type="text" value="" name="giftAddress" disabled>
													<div class="placeholder">
														礼包配送地址
													</div>
												</div>
											</div>
											<div class="form-row">
												<textarea name="log"  disabled></textarea>
												<div class="placeholder">
													操作日志
												</div>
											</div>


										</div>


									</fieldset>



									<div class="form-row withicon">
										<div class="tablearea-title">
											<i class="icon-shop"></i>
											店铺推荐搜索
										</div>
										<div class="tablearea">
										
										<div class="search">
										<form action="#" onsubmit="return false;">
											<input type="search" placeholder="输入店铺名" id="searchStoreName">
											<button type="submit" class="btn btn-nor btn-md btn-adjunctleft" id="searchStoreNameBtn" title="置空搜索可返回推荐店铺列表">搜索</button>
										</form>
										</div>
										
											<table>
														<thead>
															<tr>
																
																<th class="td-name"><span>商户名</span></th>
																<th class="td-label"><span>账户余额</span></th>
																<th class="td-label"><span>商户订单单价</span></th>
																<th class="td-label"><span>本月已派</span></th>
																<th class="td-label"><span>派单区域</span></th>
																<th><span>店铺业务类型</span></th>
																<th class="td-icons"><span>操作</span></th>
															</tr>
														</thead>
														
														
														<tbody class="strip" id="companyServiceList">
															
														</tbody>


											</table>
										</div>
										
									</div>
									</div>
									</div>
								</div>
				</div>
		</div>
		
		<div class="sf-modal modal modal-sm modal-confirmorder" style="display:none;" id="doOrderModal">
			<div class="panel">
				<div class="panel-title">
					<span class="storeName">派单给亦凡装饰(店铺名)
					</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="取消">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					<div class="formarea formarea-pad">
								<div class="formsection">
									<div class="form-row">
									<input type="text" required id="orderPrice" value="400"><label for="orderPrice">订单价格</label>

									<span></span>
									</div>
									
								
								</div>
								
					</div>
					
				</div>

				<div class="panel-foot">
					<div class="btnarea-center">
						<button class="btn btn-nor btn-iconleft" id="doOrderModalBtn">
							<i class="icon-check">
							</i>
									确认派单
						</button>
					</div>
				</div>
			</div>
		</div>

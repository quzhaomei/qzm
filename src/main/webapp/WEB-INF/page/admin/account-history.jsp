<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
				<h1 class="t-rd">账户结算管理</h1>
			</header>
			<div id="maincontents">
			</div>
<script type="text/javascript">

var props={
			title:"账户列表",
			url:"order/account-history.htmls?operator=list",
			whole:true,
			head:[{name:"storeName",title:"账户名"},
			      {name:"createDate",title:"入驻时间",className:"td-datetime",
				style:"date",format:"yyyy-MM-dd HH:mm",sort:true,sort_default:true},
			      {name:"accept",title:"接单状态",style:"label",
			    	  group:[{title:"停止接单",value:"0",className:"label label-gray"}
			    	  		,{title:"接单中",value:"1",className:"label"}]},
			      {name:"status",title:"店铺状态",style:"label",
			    	  group:[{title:"关闭",value:"0",className:"label label-gray"}
			    	  		,{title:"开启",value:"1",className:"label"},
			    	  		{title:"注销",value:"2",className:"label label-red"}]},
			      {name:"account",title:"账户余额",remove:true}
			      ],
			normalOperator:[],
			specialOperator:[{type:1,icon:"icon-account_balance_wallet",title:"充值",powerCode:"account-history-add",
						callback:function(data){
							toAccount(data);
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

	
<script type="text/javascript">

$(function(){
	//转换select控件
	$(".sf-select").each(function(){//设置select
		$(this).smartSelect()
	});
	

	$("#cur_btn").on("click",function(){
		var company=$("#accountModal").data("data");
		var cur_type=$("#cur_type").val();//1充值，2扣除
		var cur_price=$("#cur_price").val();
		var cur_comment=$("#cur_comment").val();
		if(!cur_price){alert("请输入金额;");return;}
		if(!cur_price.match(/^\d+$/)){alert("金额为整数;");return;}
		if(cur_type==1){
			cur_price=parseInt(cur_price);
		}else{
			cur_price=0-parseInt(cur_price);
		}
		var param={};
		param.companyId=company.companyId;
		param.price=cur_price;
		param.comment=cur_comment;
		var _this=this;
		$(_this).attr("disabled","disabled");
		$.post("order/account-history-add.htmls",param,function(json){
			$(_this).removeAttr("disabled");
			alert(json.message);
			if(json.status==1){
			$("#accountModal").modal("hide");
			reactData.reload(reactData.state.pageIndex);
			}
		},"json")
	});
	
});	

function toAccount(data){
	$("#accountModal").initForm(data);
	initModal(data);
	$("#accountModal").data("data",data);
	$("#accountModal").modal("show");
}

function initModal(data){
	$.post("order/account-history.htmls?operator=history",{companyId:data.companyId},function(json){
		$("#cur_price").val("");
		$("#cur_comment").val("");
		$("#accountModal .storeName").text(data.storeName+"账户信息");
		if(data.storeType==0){$("#storeType").val("普通店铺")}else{$("#storeType").val("旗舰店")}
		if(data.accept==0){$("#accept").val("关闭")}else{$("#accept").val("开启")}
		if(data.status==0){$("#status").val("关闭")}else if(data.status==1){$("#status").val("开启")}else{$("#status").val("注销")}
		$("#companyTypes").val(data.companyTypes.map(function(temp){return temp.name}).join(","))
		if(data.account&&parseInt(data.account)<0){$("#account_c").parent().addClass("warning")}
	//初始化消费／充值记录
	var $body=$("#store_account_detail").empty();
	$(json.param).each(function(){
		var $tr=$("<tr>");
		var createDate=$("<td>").html(new Date(parseInt(this.createDate)).format("yyyy-MM-dd HH:mm"));
		var orderId=$("<td>").html(this.orderId);
		var tag=$("<td>").html(this.tag==1?'<span class="label label-green">消费</span>':'<span class="label">金额调整</span>');
		var price=$("<td>").html(this.price);
		var account=$("<td>").html(this.account);
		var createUser=$("<td>").html(this.createUser.nickname);
		var comment=$("<td>").html(this.comment);
		$tr.append(createDate).append(orderId).append(tag).append(price).append(account).append(createUser).append(comment);
		$body.append($tr);
	});
	},"json")
	
	
}
</script>
<div class="sf-modal modal-full item-detail" style="display:none;" id="accountModal">
			<div class="panel">
				<div class="panel-title">
					<span class="storeName">账户信息
					</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="返回账户列表">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					<div class="formarea formarea-pad form-info form-lg">

								<div class="formsection">
									<div>
										<div class="formitem withicon">
											<select class="sf-select dropdown-container menu-down select" id="cur_type">
												<option value="1">充值</option>
												<option value="2">扣除</option>
											</select>
											<label for=""><i class="icon-bubble_chart"></i></label>
											
											<div class="placeholder">
												金额调整类型
											</div>
										</div>

										<div class="formitem withicon">
										<input type="text" id="cur_price" placeholder="请输入要调整的金额"><label><i class="icon-coins"></i></label>
											<span></span>
											<div class="placeholder">
												调整金额
											</div>
										</div>

										<div class="formitem-2 no-bd-bottom no-bd-right withicon">
										<input type="text" id="cur_comment"><label><i class="icon-comment"></i></label>
											<span></span>
											<div class="placeholder">
												操作备注
											</div>
										</div>


									</div>
									<div class="form-footer">
										<div class="btnarea-center">
											<button class="btn btn-md btn-iconleft" id="cur_btn">
														<i class="icon-check">
														</i>
														确认调整金额
											</button>

										</div>
									</div>
								</div>
								
								<div class="formsection">
									<div class="readonly">

									<div class="formitem withicon"> <!-- 负值请加 warning class -->
									<input type="text" name="account"  id="account_c" value="" disabled><label><i class="icon-account_balance_wallet"></i></label>
										<span></span>
										<div class="placeholder">
											店铺余额
										</div>
									</div>
									<div class="formitem withicon">
									<input type="text" name="storeName" value="" disabled><label for="shopname"><i class="icon-shop"></i></label>
										<div class="placeholder">
											店铺名
										</div>
									</div>

									<div class="formitem-2 withicon">
									<input type="text" value="" name="companyName" disabled><label><i class="icon-business_center"></i></label>
										<div class="placeholder">
											公司全称
										</div>
									</div>

									<div class="formitem-2 withicon">
									<input type="text" name="storeAddress" value="" disabled><label><i class="icon-location-pin"></i></label>
										<div class="placeholder">
											店铺地址
										</div>
									</div>

									<div class="formitem-1 withicon bd-bottom">
										<input type="text" value="" id="storeType" disabled>
										<label for=""><i class="icon-bubble_chart"></i></label>
										<div class="placeholder">
											店铺类型
										</div>
									</div>


									<div class="formitem-1 withicon bd-bottom no-bd-right">
										<input type="tel" name="storePhone" value="" disabled><label for="shopphone"><i class="icon-phone" required></i></label>
										<div class="placeholder">
											店铺电话
										</div>
									</div>

									</div>

									<div class="readonly">
										<div class="formitem withicon">
										<input type="text" name="keeperName" value="" disabled><label for="representative"><i class="icon-person"></i></label>
										<div class="placeholder">
											店铺负责人
										</div>
									</div>
									 
									<div class="formitem withicon">
										<input type="tel" id="phone" value="" name="keeperPhone" disabled><label for="phone"><i class="icon-phone_android" required></i></label>
										<span></span>
										<div class="placeholder">
											负责人手机
										</div>
									</div>

									<div class="formitem withicon">
										<input type="text" value="装修，软装，家具" id="companyTypes" disabled><label><i class="icon-anchor-point" required></i></label>
										<div class="placeholder">
											店铺业务类别
										</div>
									</div>
									

									<div class="formitem withicon">
										<input type="text" value="" id="accept" disabled><label><i class="icon-report" required></i></label>
										<div class="placeholder">
											接单状态
										</div>
									</div>


									<div class="formitem withicon">
										<input type="text" value="开启" id="status" disabled><label><i class="icon-report" required></i></label>
										<div class="placeholder">
											店铺状态
										</div>
									</div>
									</div>


									<div class="form-row withicon">
										<div class="tablearea-title">
											<i class="icon-event_available"></i>
											帐户交易记录
										</div>
										<div class="tablearea">
											<table>
														<thead>
															<tr>
																
																<th class="td-datetime"><span>时间</span></th>
																<th class="td-label"><span>交易订单号</span></th>
																<th class="td-label"><span>交易类型</span></th>
																<th class="td-label"><span>变更金额</span></th>
																<th class="td-label"><span>帐户余额</span></th>
																<th class="td-name"><span>操作人</span></th>
																<th><span>操作备注</span></th>
															</tr>
														</thead>
														
														
														<tbody class="strip" id="store_account_detail">
															<tr>
																<td>2016-08-25 16:30</td>
																<td>M02918372</td>
																<td class="td-label"><span class="label label-green">金额调整</span></td>
																<td>1300</td>
																<td>8000</td>
																<td>王大海</td>
																<td>备注内容</td>

															</tr>
															<tr>
																<td>2016-08-25 16:30</td>
																<td>M02918372</td>
																<td class="td-label"><span class="label">订单消费</span></td>
																<td>-300</td>
																<td>-300</td>
																<td>王大海</td>
																<td>备注内容</td>

															</tr>
															
														</tbody>
											</table>
										</div>
										

										
									</div>

								
			
									</div>


									
								</div>


					
								
				</div>
					
				</div>
			</div>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
				<h1 class="t-rd">硬装需求管理</h1>
			</header>
			<div id="maincontents">
			</div>
<script type="text/javascript">
function parseRoles(roles){
	return roles.map(function(temp){return temp.roleName});
}

var props={
			title:"硬装需求列表",
			url:"myWork/hard-index.htmls?operator=list",
			whole:true,
			head:[ 
			       {name:"houseLocation",title:"房产信息"},
			       {name:"customerCode",style:"define",title:"客户编号",content:"{customer.customerCode}"},
			      {name:"customer",style:"define",title:"客户姓名",content:"{customer.name}",className:"td-name"},
			       {name:"comment",title:"需求备注"},
			      {name:"createDate",title:"需求生成时间",className:"td-datetime",
			    	  style:"date",format:"yyyy-MM-dd HH:mm",sort:true,remove:true,sort_default:true},
			       {name:"comment",title:"已派数 : 已接数",className:"td-label",style:"define",
			    	   content:'<span class="label">2</span><span class="label label-gray">0</span>'},
			      {name:"status",title:"状态",style:"label",className:"td-label",
			    	  group:[{className:"label label-gray label-line",title:"待派单",value:"1"}
			    	  		,{className:"label label-gray",title:"已派单",value:"2"}
			    	  		,{className:"label label-red",title:"关闭",value:"3"}]}
			      ],
			      
			normalOperator:[{icon:"icon-pencil",title:"修改",powerCode:"constant-age-edit",
								callback:function(data){
									toEdit(data);
									}},
			 			{icon:"icon-trash-o",title:"删除",powerCode:"constant-age-delete",
							callback:function(data){
			 			       toDelete(data);
			 			}}],
			 specialOperator:[{type:1,icon:"icon-insert_drive_file toorder",powerCode:"sysCustomer-customer-service",title:"派单",callback:function(data){serviceTemp=data;$("#toService").modal("show")}}
						,{type:1,icon:"icon-lock_outline close-needs",title:"关闭",powerCode:"myWork-hard-close",
							callback:function(data){
									closeRequire(data);
								}}
						]
			};
var reactData=$("#maincontents").render("Table_data",props,function(dom){});
</script>

		<div class="sf-modal modal modal-sm" style="display:none;"  id="addModal">
			
		</div>
	
<script type="text/javascript">
//关闭订单
function closeRequire(data){
	if(data.status==3){alert("订单已经关闭，无法操作！");return;}
	
	if(confirm("确定关闭该订单吗？")){
		$.post("myWork/hard-close.htmls",{houseId:data.houseId},function(json){
			alert(json.message);
			if(json.status==1){
				var pageIndex=reactData.state.pageIndex;
				reactData.reload(pageIndex);//重新加载数据,并跳转当页
			}
		},"json")
	}
}
$(function(){
	//转换select控件
	$(".sf-select").each(function(){//设置select
		$(this).smartSelect();
	});
});

</script>
<div class="sf-modal modal modal-sm" style="display:none;"  id="editModal">
			
		</div>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="ad" uri="/sf" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>系统后台管理</title>
<c:import url="public/p-css.jsp"></c:import>
<style type="text/css">
.modal-body{max-height: 580px;}
</style>
</head>

<body>
	<!-- start: Header -->
		<c:import url="public/p-header.jsp"></c:import>
	<!-- start: Header -->

	<div class="container-fluid-full">
		<div class="row-fluid">

			<!-- start: Main Menu -->
			<c:import url="/welcome/menus.htmls"></c:import>
			<!-- end: Main Menu -->

			<!-- start: Content -->
			<div id="content" class="span10">
				<ul class="breadcrumb">
					<li><i class="icon-home"></i> <a href="index.html">首页</a></li>
				<li><i class="icon-angle-right"></i> <a href="#">合作企业管理</a> </li>
				</ul>

				<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2>
								<i class="halflings-icon user"></i><span class="break"></span>合作企业管理
							</h2>
							<div class="box-icon">
							
							<ad:power uri="../cooperation/add.htmls">
								<a href="#" class="addCooperation"><i
									class="halflings-icon plus"></i>
									 </a> 
								</ad:power>
									 
									 <a href="#"
									class="btn-minimize"><i class="halflings-icon chevron-up"></i>
								</a> <a href="#" class="btn-close"><i
									class="halflings-icon remove"></i> </a>
							</div>
					</div>
					<div class="box-content">
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
						  <thead>
							  <tr>
								  <th width=5%>序列</th>
								  <th width=10%>code</th>
								  <th width=25%>公司名字</th>
								  <th width=20%>描述</th>
								  <th width=15%>创建时间</th>
								  <th width=15%>状态</th>
								  <th width=10%>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  <c:forEach items="${cooperations }" var="temp" varStatus="status">
						  	<tr>
								<td>${status.count }</td>
								<td class="center">${fn:escapeXml(temp.code) }</td>
								<td class="center">
									${fn:escapeXml(temp.name) }
								</td>
								<td class="center">${fn:escapeXml(temp.description) }</td>
								<td class="center">
									<fmt:formatDate value="${temp.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td class="center">
									<c:choose>
										<c:when test="${temp.status==0 }">
											<span class="label">冻结</span>
										</c:when>
										<c:when test="${temp.status==1 }">
											<span class="label label-success">启用</span>
										</c:when>
									</c:choose>
								</td>
								<td class="center">
									<ad:power uri="../cooperation/update.htmls">
									<a class="btn btn-info btn-mini editCooperation" href="#" tempId="${temp.cooperationId }">
										<i class="halflings-icon white edit "></i>  修改
									</a>
									</ad:power>
								</td>
							</tr>
						  </c:forEach>
						  </tbody>
					  </table>            
					</div>
				</div><!--/span-->
			
			</div><!--/row-->
			</div>
			
		</div>
	</div>
	
	<div class="clearfix"></div>
	<c:import url="public/p-footer.jsp"></c:import>
	<c:import url="public/p-javascript.jsp"></c:import>
</body>
<!-- 新增modal -->
	<div class="modal hide fade" id="addModal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>合作企业管理</h3>
		</div>
		<div class="modal-body">
			<p><small class="text-success">新建企业</small></p>			
					<div class="alert alert-error alert-dismissable" style="display:none">
						 <span class="content" ></span>
					</div>
					<table>
					<tr>
					<td>
						<span class="add-on"><i class="halflings-icon user"></i> code </span>
					</td>
					<td>
						<input class="input-large span5" id="addCode"  type="text" placeholder="请输入code" maxlength="50" >
					</td>
					</tr>
					
					<tr>
					<td>
						<span class="add-on"><i class="halflings-icon user"></i> 企业名字 </span>
					</td>
					<td id="addFileTd">
						<input class="input-large span5" id="addName"  type="text" placeholder="请输入企业名字" maxlength="200" >
					</td>
					</tr>
					
					
					<tr>
					<td>
						<span class="add-on"><i class="halflings-icon user"></i> 描 述 </span>
					</td>
					<td>
						<textarea id="addDescription" style="resize:none;width:100%;height:80px;"></textarea>
					</td>
					</tr>
					
					<tr>
						<td>
							<span class="add-on"><i class="halflings-icon user"></i> 状态 </span>
						</td>
						<td>
						 <label class="radio" style="padding:0 0;"> <input type="radio"
							name="status" class="type_radio addCooperatorStatus" value="1"> <span class="label label-success">激活 </span>
						</label>		
						<label class="radio" style="padding:0 0;"> <input type="radio" name="status"
							class="type_radio addCooperatorStatus" value="0" checked="checked"> <span class="label label-important">冻结 </span>
						</label>
						</td>
					</tr>
					
					</table>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">返回</a> <a href="#"
				class="btn btn-primary addSubmit">保存</a>
		</div>
	</div>
	
	<!-- 修改modal -->
	<div class="modal hide fade" id="updateModal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>合作企业管理</h3>
		</div>
		<div class="modal-body">
			<p><small class="text-success">修改企业</small></p>			
					<div class="alert alert-error alert-dismissable" style="display:none">
						 <span class="content" ></span>
					</div>
					<table>
					<tr>
					<td>
						<span class="add-on"><i class="halflings-icon user"></i> code </span>
					</td>
					<td>
						<input class="input-large span5" id="updateCode"  type="text" placeholder="请输入code" maxlength="50" >
						<input class="input-large span5" id="updateCooperationId"  type="hidden" >
					</td>
					</tr>
					
					<tr>
					<td>
						<span class="add-on"><i class="halflings-icon user"></i> 企业名字 </span>
					</td>
					<td id="addFileTd">
						<input class="input-large span5" id="updateName"  type="text" placeholder="请输入企业名字" maxlength="200" >
					</td>
					</tr>
					
					
					<tr>
					<td>
						<span class="add-on"><i class="halflings-icon user"></i> 描 述 </span>
					</td>
					<td>
						<textarea id="updateDescription" style="resize:none;width:100%;height:80px;"></textarea>
					</td>
					</tr>
					
					<tr>
						<td>
							<span class="add-on"><i class="halflings-icon user"></i> 状态 </span>
						</td>
						<td>
						 <label class="radio" style="padding:0 0;"> <input type="radio"
							name="status" class="type_radio updateStatus"  value="1"> <span class="label label-success">激活 </span>
						</label>		
						<label class="radio" style="padding:0 0;"> <input type="radio" name="status"
							class="type_radio updateStatus" value="0"> <span class="label label-important">冻结 </span>
						</label>
						</td>
					</tr>
					
					</table>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">返回</a> <a href="#"
				class="btn btn-primary updateSubmit">保存</a>
		</div>
	</div>
	
<script type="text/javascript">
	$(".addCooperation").on("click",function(){
		$("#addModal").modal("show");
	});
	
	
	$(".addSubmit").on("click",function(){
		var param={};
		var code=$("#addCode").val();
		var name=$("#addName").val();
		var status=$(".addCooperatorStatus:checked").val();
		var description=$("#addDescription").val();
		
		if(!code){layer.msg("请输入code");return;}
		if(!name){layer.msg("请输入企业名字");return;}
			param.code=code;
			param.name=name;
			param.status=status;
			param.description=description;
			$.ajax({
				url:"add.htmls",
				method:"post",
				data:param,
				cache:false,
				dataType:"json",
				success:function(json){
					if(json.status==1){
						layer.msg(json.message);
						window.location.reload();
					}else{
						layer.msg(json.message);
					}
				}
			});
	});
	
	
	
	
	$(".editCooperation").on("click",function(){
		var tempId=$(this).attr("tempId");
		if(!tempId){return;}
		var param={};
		param.cooperationId=tempId;
		param.operator="findById";
		$.ajax({
			url:"update.htmls",
			data:param,
			method:"post",
			dataType:"json",
			success:function(json){
				if(json.status==1){
					$("#updateCooperationId").val(json.data.cooperationId);
					$("#updateCode").val(json.data.code);
					$("#updateName").val(json.data.name);
					$("#updateDescription").val(json.data.description);
					$(".updateStatus[value='"+json.data.status+"']").parent("span").removeClass("checked");
					$(".updateStatus[value='"+json.data.status+"']").attr("checked","checked")
					.parent("span").addClass("checked");
					$("#updateModal").modal("show");
				}else{
					layer.msg(json.message);
				}
			}
		});
		
	});
	
	$(".updateSubmit").on("click",function(){
		var param={};
		var cooperationId=$("#updateCooperationId").val();
		var name=$("#updateName").val();
		var status=$(".updateStatus:checked").val();
		var code=$("#updateCode").val();
		var description=$("#updateDescription").val();
			param.cooperationId=cooperationId;
			param.name=name;
			param.status=status;
			param.code=code;
			param.description=description;
			param.operator="edit";
			$.ajax({
				url:"update.htmls",
				method:"post",
				data:param,
				cache:false,
				dataType:"json",
				success:function(json){
					if(json.status==1){
						layer.msg(json.message);
						window.location.reload();
					}else{
						layer.msg(json.message);
					}
				}
			});
	});
	
</script>
</html>

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
#addValueFile{display:none;}
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
				<li><i class="icon-angle-right"></i> <a href="#">系统资源管理</a> </li>
				</ul>

				<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2>
								<i class="halflings-icon user"></i><span class="break"></span>系统资源管理
							</h2>
							<div class="box-icon">
							
							<ad:power uri="../source/add.htmls">
								<a href="#" class="addSource"><i
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
								  <th width=10%>key</th>
								  <th width=25%>value</th>
								  <th width=10%>类型</th>
								  <th width=20%>描述</th>
								  <th width=15%>创建时间</th>
								  <th width=10%>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  <c:forEach items="${sources }" var="source" varStatus="status">
						  	<tr>
								<td>${status.count }</td>
								<td class="center">${fn:escapeXml(source.key) }</td>
								<td class="center">
									<c:choose>
										<c:when test="${source.type==0 }">
										<img src="${source.value }" style="max-width:80%;">
										</c:when>
										<c:when test="${source.type==1 }">
										${source.value }
										</c:when>
									</c:choose>
								</td>
								<td class="center">
									<c:choose>
										<c:when test="${source.type==0 }">
										<span class="label label-important">图片</span>
										</c:when>
										<c:when test="${source.type==1 }">
										<span class="label">字符</span>
										</c:when>
									</c:choose>
								</td>
								<td class="center">${fn:escapeXml(source.description) }</td>
								<td class="center">
									<fmt:formatDate value="${source.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td class="center">
									<ad:power uri="../source/update.htmls">
									<a class="btn btn-info btn-mini editSource" href="#" sourceId="${source.sourceId }">
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
			<h3>系统资源管理</h3>
		</div>
		<div class="modal-body">
			<p><small class="text-success">新建资源</small></p>			
					<div class="alert alert-error alert-dismissable" style="display:none">
						 <span class="content" ></span>
					</div>
					<table>
					<tr>
					<td>
						<span class="add-on"><i class="halflings-icon user"></i> key </span>
					</td>
					<td>
						<input class="input-large span5" id="addKey"  type="text" placeholder="请输入key" maxlength="50" >
					</td>
					</tr>
					<tr>
						<td>
							<span class="add-on"><i class="halflings-icon user"></i> 类型 </span>
						</td>
						<td>
						 <select rel="chosen" id="addType">
						 	<option value="1" selected="selected"> 字符</option>
						 	<option value="0"> 图片</option>
						 </select>
						</td>
					</tr>
					<tr>
					<td>
						<span class="add-on"><i class="halflings-icon user"></i> value </span>
					</td>
					<td id="addFileTd">
						<input class="input-large span5" id="addValue"  type="text" placeholder="请输入value" maxlength="200" >
						<input type="file" id="addValueFile" name="imgFile" >
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
			<h3>系统资源管理</h3>
		</div>
		<div class="modal-body">
			<p><small class="text-success">修改资源</small></p>			
					<div class="alert alert-error alert-dismissable" style="display:none">
						 <span class="content" ></span>
					</div>
					<table>
					<tr>
					<td>
						<span class="add-on"><i class="halflings-icon user"></i> key </span>
					</td>
					<td>
						<input class="input-large span5" id="updateKey"  readonly="readonly" type="text" placeholder="请输入key" maxlength="50" >
						<input type="hidden" id="updateSourceId"/>
					</td>
					</tr>
					<tr>
					<td>
						<span class="add-on"><i class="halflings-icon user"></i> value </span>
					</td>
					<td id="updateFileTd">
						<input class="input-large span5" id="updateValue"  type="text" placeholder="请输入value" maxlength="200" >
					
						<label for="updateValueFile">
						<img style="width:100px;" id="updateImgPre"/>
						<input type="file" id="updateValueFile" name="imgFile" >
						</label>
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
					</table>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">返回</a> <a href="#"
				class="btn btn-primary updateSubmit">保存</a>
		</div>
	</div>
	
<script type="text/javascript" src="../js/ajaxfileupload.js" charset="utf-8"></script>
<script type="text/javascript">
	$(".addSource").on("click",function(){
		$("#addModal").modal("show");
	});
	
	$(".addSubmit").on("click",function(){
		var param={};
		var key=$("#addKey").val();
		var value=$("#addValue").val();
		var type=$("#addType").val();
		var description=$("#addDescription").val();
		if(!key){layer.msg("请输入key");return;}
		if(!value){layer.msg("请输入value");return;}
			param.key=key;
			param.value=value;
			param.type=type;
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
	
	$("#addType").on("change",function(){
		$("#addValue").val("");
		if($(this).val()=="0"){
			$("#addValueFile").show();
			$("#uniform-addValueFile").show();
			$("#addValue").hide();
		}else{
			$("#addValueFile").hide();
			$("#uniform-addValueFile").hide();
			$("#addValue").show();
		}
	});
	
	$("#addFileTd").on("change", "#addValueFile", function() {
		var file = this.files[0];
		if (file.type && !file.type.match(/image.*/)) {// 如果支持type，验证图片
			layer.msg("请选择图片文件");
			return;
		}
		if (!(file.type)) {// 不支持type，则进行后缀名匹配
			if (!checkName(_this.value)) {
				layer.msg("请选择图片文件");
				return;
			}
		}
		$.ajaxFileUpload({
			url : '../welcome/uploadImg.htmls',// 处理图片脚本
			secureuri : false,
			fileElementId : 'addValueFile',// file控件id
			dataType : 'json',
			success : function(data, status) {
					$("#addValue").val(data.url);
				if(data.error==0){
					if(!$("#imgPre")[0]){
					$("#addValueFile").after(
							$("<img>").attr("id","imgPre").attr("src",data.url).css(
									{width:"80px",height:"80px"}))
					}else{
						$("#imgPre").attr("src",data.url);
					}
				}else{
					layer.msg(data.message);
				}
			},
			error : function(data, status, e) {
				alert(e);
			}
		});
	});
	
	$(".editSource").on("click",function(){
		var sourceId=$(this).attr("sourceId");
		if(!sourceId){return;}
		var param={};
		param.sourceId=sourceId;
		param.operator="findById";
		$.ajax({
			url:"update.htmls",
			data:param,
			method:"post",
			dataType:"json",
			success:function(json){
				if(json.status==1){
					$("#updateSourceId").val(json.data.sourceId);
					$("#updateKey").val(json.data.key);
					$("#updateValue").val(json.data.value);
					$("#updateDescription").val(json.data.description);
					if(json.data.type==0){//图片
						$("#updateValue").hide();
						$("#updateValueFile").parents("label").show();
						$("#uniform-updateValueFile").hide();
						$("#updateImgPre").attr("src",json.data.value);
					}else if(json.data.type==1){//文字
						$("#updateValue").show();
						$("#updateValueFile").parents("label").hide();
						
					}
					$("#updateModal").modal("show");
				}else{
					layer.msg(json.message);
				}
			}
		});
		
	});
	
	$("#updateFileTd").on("change", "#updateValueFile", function() {
		var file = this.files[0];
		if (file.type && !file.type.match(/image.*/)) {// 如果支持type，验证图片
			layer.msg("请选择图片文件");
			return;
		}
		if (!(file.type)) {// 不支持type，则进行后缀名匹配
			if (!checkName(_this.value)) {
				layer.msg("请选择图片文件");
				return;
			}
		}
		$.ajaxFileUpload({
			url : '../welcome/uploadImg.htmls',// 处理图片脚本
			secureuri : false,
			fileElementId : 'updateValueFile',// file控件id
			dataType : 'json',
			success : function(data, status) {
					$("#updateValue").val(data.url);
				if(data.error==0){
						$("#updateImgPre").attr("src",data.url);
				}else{
					layer.msg(data.message);
				}
			},
			error : function(data, status, e) {
				alert(e);
			}
		});
	});
	
	$(".updateSubmit").on("click",function(){
		var param={};
		var sourceId=$("#updateSourceId").val();
		var value=$("#updateValue").val();
		var description=$("#updateDescription").val();
		if(!value){layer.msg("请输入value");return;}
			param.sourceId=sourceId;
			param.value=value;
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

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
				<!-- 	<li><a href="#">系统设置<i class="icon-angle-right"></i> </a> -->
					</li>
				</ul>

				<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header" data-original-title>
						<h2>
								<i class="halflings-icon user"></i><span class="break"></span>系统角色管理
							</h2>
							<div class="box-icon">
							
							<ad:power uri="../role/add.htmls">
								<a href="#" class="addRole"><i
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
								  <th width=10%>序列</th>
								  <th width=20%>角色名</th>
								  <th width=30%>创建时间</th>
								  <th width=10%>状态</th>
								  <th width=30%>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  <c:forEach items="${roles }" var="role" varStatus="status">
						  	<tr>
								<td>${status.count }</td>
								<td class="center">${fn:escapeXml(role.roleName) }</td>
								<td class="center">
									<fmt:formatDate value="${role.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td class="center">
								<c:if test="${role.status==0 }">
									<span class="label label-important">冻结 0</span>
								</c:if>
								<c:if test="${role.status==1 }">
									<span class="label label-success">激活 1</span>
								</c:if>
								</td>
								
								<td class="center">
									<%--<a class="btn btn-success" href="#">
										<i class="halflings-icon white zoom-in"></i>  
									</a>--%>
									<ad:power uri="../role/update.htmls">
									<a class="btn btn-info btn-mini editRole" href="#" roleId="${role.roleId }">
										<i class="halflings-icon white edit "></i>  修改
									</a>
									</ad:power>
									
									<ad:power uri="../role/status.htmls">
									<a class="btn btn-danger btn-mini changeStatu" href="#" roleid="${role.roleId }" status="${role.status }" >
										<i class="halflings-icon white refresh"></i> 切换状态
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
	<script type="text/javascript" src="../js/admin/roleManager.js" charset="utf-8"></script>
</body>
<!-- 新增modal -->
	<div class="modal hide fade" id="addModal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>系统角色管理</h3>
		</div>
		<div class="modal-body">
			<p><small class="text-success">新建管理员</small></p>
				<div class="input-prepend" title="菜单名字">
				<div class="alert alert-error alert-dismissable" style="display:none">
						 <span class="content" ></span>
				</div>
				<span class="add-on"><i class="halflings-icon user"></i> 名 称 </span>
				<input class="input-large span5" id="add_menuName"  type="text" placeholder="请输入角色名称" maxlength="20" >
				</div>		
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
			<h3>系统角色管理</h3>
		</div>
		<div class="modal-body">
			<p><small class="text-success">修改管理员</small></p>			
					<div class="alert alert-error alert-dismissable" style="display:none">
						 <span class="content" ></span>
					</div>
					<table>
					<tr>
					<td>
						<span class="add-on"><i class="halflings-icon user"></i> 名 称 </span>
						<input type="hidden" id="roleId" >
					</td>
					<td>
						<input class="input-large span5" id="roleName"  type="text" placeholder="请输入角色名称" maxlength="20" >
					</td>
					</tr>
					<tr>
						<td>
							<span class="add-on"><i class="halflings-icon user"></i> 状态 </span>
						</td>
						<td>
						 <label class="radio" style="padding:0 0;"> <input type="radio"
							name="status" class="type_radio roleStatus" value="1"> <span class="label label-success">激活 </span>
						</label>		
						<label class="radio" style="padding:0 0;"> <input type="radio" name="status"
							class="type_radio roleStatus" value="0" checked=""> <span class="label label-important">冻结 </span>
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
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>后台管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<c:import url="public/p-css.jsp"></c:import>
<link href="../css/admin/menuManager.css" rel="stylesheet">

</head>
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
			<div class="tree well">
				<h2>
					菜单管理 <small style="font-size:12px;color:#888;"> <i
						class="icon-folder-open"></i> -菜单组 <i class="icon-globe"></i>
						-页面菜单 <i class="icon-leaf"></i> -操作菜单
					</small>
				</h2>
			</div>
		</div>
	</div>
</div>

<div class="clearfix"></div>
<c:import url="public/p-footer.jsp"></c:import>
<c:import url="public/p-javascript.jsp"></c:import>

<script type="text/javascript" src="../js/admin/menuManager.js" charset="utf-8"></script>

</body>
<div class="modal hide fade" id="myModal">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>设置菜单</h3>
	</div>
	<div class="modal-body">
		<input id="operator" type="hidden"/>
		<ul id="myTab" class="nav nav-tabs">
			<li id="li_edit" class="active"><a class="edit_mod" href="#edit"
				data-toggle="tab"> <i class="icon-pencil"></i> 修改
			</a></li>
			<li id="li_del"><a class="del_mod" href="#del" data-toggle="tab"><i
					class="icon-file"></i> 删除</a></li>
			<li id="li_add"><a class="add_mod" href="#add" data-toggle="tab"><i
					class="icon-leaf"></i> 增加子菜单</a></li>
		</ul>
		<div id="myTabContent" class="tab-content">
			<!-- 修改tab -->
			<div class="tab-pane fade  active text-left" id="edit">
				<p>
					<small class="text-success">修改本菜单</small>
				</p>
				<div class="input-prepend" title="菜单名字">
					<span class="add-on"><i class="halflings-icon pencil"></i> 名
						称 </span> <input class="input-large span5" id="edit_menuName" type="text"
						placeholder="请输入菜单名字" maxlength="10"> <input
						type="hidden" id="menuId" />
				</div>
				<div class="input-prepend" title="链接">
					<span class="add-on"><i class="halflings-icon globe"></i> 链
						接 </span> <input class="input-large span5" id="edit_uri" type="text"
						style="ime-mode:disabled" placeholder="请输入链接" maxlength="50" />
				</div>
			</div>
			<!-- 删除tab -->
			<div class="tab-pane fade text-left" id="del">
				<p class="text-success">
					<small>请输入：<span id="repeat" class="text-warn">我确认删除</span></small>
				</p>
				<input class="input-large span5" type="text" id="del_repeat"
					placeholder="请输入上面提示的内容" />
			</div>
			<!-- 增加tab -->
			<div class="tab-pane fade text-left" id="add">
				<p>
					<small class="text-success">新建子菜单</small>
				</p>
				<div class="input-prepend" title="菜单名字">
					<span class="add-on"><i class="icon-leaf"></i> 名 称 </span> <input
						class="input-large span5" id="add_menuName" type="text"
						placeholder="请输入菜单名字" maxlength="10">
				</div>
				<div class="input-prepend" title="菜单类型">
					<span class="add-on"><i class="icon-leaf"></i> 菜单类型 </span> <label
						class="radio"> <input type="radio" name="add_type"
						class="type_0 type_radio" value="0" checked=""><i
						class="icon-folder-open"></i> 菜单组
					</label> <label class="radio"> <input type="radio"
						name="add_type" class="type_1 type_radio" value="1"><i
						 class="icon-globe"></i> 页面菜单
					</label> <label class="radio"> <input type="radio"
						name="add_type" class="type_2 type_radio" value="2"><i
						 class="icon-leaf"></i> 页面操作
					</label>
				</div>
				<div class="input-prepend" title="链接">
					<span class="add-on"><i class="halflings-icon globe"></i> 链
						接 </span> <input class="input-large span5" id="add_uri" type="text"
						style="ime-mode:disabled" placeholder="请输入链接" maxlength="50" />
				</div>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn" data-dismiss="modal">关闭</a> <a href="#"
			class="btn btn-primary saveOrUpdate">确认</a>
	</div>
</div>

</html>


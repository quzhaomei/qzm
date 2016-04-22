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
<c:import url="public/p-css.jsp"></c:import>
<link href="../css/admin/menuManager.css" rel="stylesheet">

<style type="text/css">
.icon-leaf{color:green;}
</style>
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
					<li><i class="icon-home"></i> <a href="index.html">首页</a> <i
						class="icon-angle-right"></i></li>
					<li><a href="#">系统设置<i class="icon-angle-right"></i> </a>
					</li>
					<li><a href="#">权限管理</a>
					</li>
				</ul>
				<p>
					<small class="text-error">-角色权限分配 <br />-请先选择需要更改权限的角色，再进行角色权限操作！
					</small>
				</p>
				<div id="searchDiv">
				<table>
				<tr>
				<td class="text-center text-success"><select id="pageSelect" data-rel="chosen" style='width:180px;'>
							<option value="-1">- 请 选 择 -</option>
						<c:forEach items="${allRoles}" var="role">
							<option value="${role.roleId }">${role.roleName }</option>
						</c:forEach>
						</select>						
				</td>
				<td><button class="btn btn-primary btn-small" id="searchBtn">
				<i class="halflings-icon search white"></i></button></td>
				</tr>
				</table>
				</div>									
			</div>
		
	</div>
</div>

<div class="clearfix"></div>
<c:import url="public/p-footer.jsp"></c:import>
<c:import url="public/p-javascript.jsp"></c:import>
<script type="text/javascript"  src="../js/admin/power.js" charset="utf-8">

</script>

</body>
</html>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
				<h1 class="t-rd">用户管理</h1>
			</header>
			<div id="maincontents">
			</div>
<script type="text/javascript">
function parseRoles(roles){
	return roles.map(function(temp){return temp.roleName});
}

var props={
			title:"当前用户列表",
			url:"system/userIndex.htmls?operator=list",
			whole:true,
//			batch:true,
			addBtn:{powerCode:"userManager-save",callback:function(){ toAdd();}},
			head:[{name:"loginname",title:"登录名",sort:true},{name:"nickname",title:"昵称"},
		      {name:"roles",title:"角色",style:"labels",content:"{parseRoles(roles)}",remove:true},
			      {name:"phone",title:"手机",className:"td-mobile",remove:true},
			      {name:"createDate",title:"创建时间",className:"td-date",
				style:"date",format:"yyyy-MM-dd",sort:true,sort_default:true,remove:true},
			      {name:"status",title:"状态",style:"icon",
			    	  group:[{icon:"icon-remove_circle_outline icon-gray",title:"冻结",value:"0"}
			    	  		,{icon:"icon-check icon-green",title:"激活",value:"1"}]}
			      ],
			normalOperator:[{icon:"icon-pencil",title:"修改",powerCode:"userManager-update",
				callback:function(data){
					toEdit(data);
					}},
			 			          {icon:"icon-trash-o",title:"删除",powerCode:"userManager-delete",callback:function(data){
			 			        	  toDelete(data);
			 			          }},
			 			          {icon:"icon-lock_outline",powerCode:"userManager-status", title:"333",callback:function(data){
			 			        	  toStatus(data);
			 			          }}]
//			,specialOperator:[{icon:"icon-phone",title:"111",callback:function(data){}}]
			
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

		<div class="sf-modal modal  modal-md" style="display: none;"  id="addModal">
			<div class="panel">
				<div class="panel-title">
					<span>添加新用户</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="添加新用户">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					<div class="formarea formarea-pad">
								<div class="notice info closable">
									<span class="text">如不填写用户密码，则初始密码默认为手机号</span>
									<div class="close"></div>
								</div>
								<div class="formsection">
									<div class="form-row withicon">
									<input type="text" placeholder="用户名" id="username" value="" autocomplete="off" maxlength="50" required><label for="username"><i class="icon-person"></i></label>
									<span></span>
									</div>
									<div class="form-row withicon">
										<input type="text" placeholder="昵称" id="nickname"  autocomplete="off" maxlength="10" required><label for="nickname"><i class="icon-bubble_chart"></i></label>
										<span></span>
									</div>
									<div class="form-row withicon">
										<input type="password" name="password" id="password"  autocomplete="off" maxlength="50" class="withicon" placeholder="登录密码"><label for="password"><i class="icon-vpn_key"></i></label>
										<span></span>
									</div>
									<div class="form-row withicon">
										<input type="tel" placeholder="手机" id="telephone" autocomplete="off" maxlength="11" id="telephone" required><label for="telephone"><i class="icon-mobile" required></i></label>
										<span></span>
									</div>
									<div class="form-row withicon">
										<input type="text" placeholder="邮件" autocomplete="off" id="email" maxlength="50"><label for="email"><i class="icon-mail"></i></label>
										<span></span>
									</div>
									<div class="form-row withicon">
										<select class="sf-select select dropdown-container roles menu-down" id="role">
											<c:forEach	items="${roles }" var="temp" varStatus="status">
												<option value="${temp.roleId }" ${status.index==0?"selected='seleceted'":"" }>${temp.roleName }</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-row withicon">
										<textarea name="comment" placeholder="添加用户备注" id="comment" maxlength="200"></textarea>
										<label for="user-comment"><i class="icon-comment"></i></label>
									</div>
								</div>
								
					</div>
					
				</div>
				<div class="panel-foot">
					<div class="btnarea-center">
						<button class="btn btn-nor btn-iconleft" id="saveBtn">
									<i class="icon-check">
									</i>
									确认添加
						</button>
					</div>
				</div>
			</div>
		</div>
	
<script type="text/javascript">
function toAdd(){
	$("#addModal").modal("show");
	$("#addModal").reset();
}
$(function(){
	//转换select控件
	$(".sf-select").each(function(){//设置select
		$(this).smartSelect();
	});
	
	$("#addModal").on("click",".close",function(){
		$("#addModal .notice").hide();
	});
	
	//显示错误信息
	function showMsg(msg,error){
		$("#addModal .notice").show();
		$("#addModal .notice .text").text(msg);
		if(error){
			$("#addModal .notice").removeClass("info").addClass("error");
		}else{
			$("#addModal .notice").removeClass("error").addClass("info");
		}
	}
	
	
	var username_reg=/^[a-zA-Z]+\w{5,49}$/;
	var telephone_reg=/^1[3|4|5|6|7|8]\d{9}$/;
	var password=/^.{6,50}$/;
	var email_reg=/^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
	
	$("#username").validator({require:true,regExp:username_reg,callback:function(){
		showMsg("请输入用户名",true);
	},callback1:function(){
		showMsg("请输入正确格式的用户名，以字母开头字母数字混合且至少6位",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	
	$("#nickname").validator({require:true,callback:function(){
		showMsg("请输入用户昵称，将用于消息提示",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	
	$("#password").validator({require:true,callback:function(){
		showMsg("请输入密码",true);
	},regExp:password,callback1:function(){
		showMsg("密码至少为六位",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	
	$("#telephone").validator({require:true,callback:function(){
		showMsg("请输入用户手机",true);
	},regExp:telephone_reg,callback1:function(){
		showMsg("请输入正确的手机号码",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	
	$("#email").validator({require:false,regExp:email_reg,callback1:function(){
		showMsg("请输入正确格式的邮箱",true);
	},success:function(){
		$("#addModal .notice").hide();
	}});
	
	$("#saveBtn").on("click",function(){
		var username=$("#username").val();
		var nickname=$("#nickname").val();
		var password=$("#password").val();
		var telephone=$("#telephone").val();
		var email=$("#email").val();
		var role=$("#role").val();
		var comment=$("#comment").val();
		var param={};
		
		if(!$("#username").valide()){
			return;
		}
		if(!$("#nickname").valide()){
			return;
		}
		
		if(!$("#password").valide()){
			return;
		}
		
		if(!$("#telephone").valide()){
			return;
		}
		
		if(!$("#email").valide()){
			return;
		}
		param.loginname=username;
		param.nickname=nickname;
		param.phone=telephone;
		param.password=password;
		param.email=email;
		param.roleId=role;
		param.description=comment;
		$.post("system/saveUser.htmls",param,function(json){
			if(json.status==1){//保存成功
				alert(json.message)
				$("#addModal").modal("hide");
				reactData.reload("last");//重新加载数据
			}else{
				showMsg(json.message,true)
			}
		},"json");
	});
	
	
	
	//保存修改
	$("#editSave").on("click",function(){
		var nickname=$("#editModal #nickname_edit").val();
		var password=$("#editModal #password_edit").val();
		var phone=$("#editModal #phone_edit").val();
		var email=$("#editModal #email_edit").val();
		var description=$("#editModal #comment_edit").val();
		
		var password=$("#editModal #edit_password").val();
		
		
		if(!nickname){
			alert("请输入用户昵称，将用于消息提示");
			return;
		}
		if(password&&password.length<6){
			alert("密码至少为六位");
			return;
		}
		if(!phone){
			alert("请输入用户手机");
			return;
		}
		if(!telephone_reg.test(phone)){
			alert("请输入正确的手机号码");
			return;
		}
		if(email&&!email_reg.test(email)){
			alert("请输入正确格式的邮箱");
			return;
		}
		//角色
		var roleIds=[];
		$("#roles_edit input[name='role_edit']:checked").each(function(){
			roleIds.push(this.value);
		});
		if(roleIds.length==0){
			alert("至少选择一个角色");
			return;
		}
		var status=$("#status_edit input[name='edit_status']:checked").val();
		
		var param={};
		param.adminUserId=editTemp.adminUserId;
		param.nickname=nickname;
		param.password=password;
		param.status=status;
		param.phone=phone;
		param.email=email;
		param.description=description;
		param.newRoleIds=roleIds;
		$.post("system/updateUser.htmls",param,function(json){
			alert(json.message);
			if(json.status==1){//成功，刷新数据
				$("#editModal").modal("hide");
				var pageIndex=reactData.state.pageIndex;
				reactData.reload(pageIndex);//重新加载数据,并跳转当页
			}
		},"json");
		
	});
	
	//重置
	$("#editReset").on("click",function(){
		editReset();
	})
	//修改密码
	$("#editModal").on("click","#password_edit",function(){
		event.preventDefault();
		$(this).parents(".formitem").html("<input type='password' name='password' class='withicon' id='edit_password' maxlength='50'>"+
				"<label for='password'><i class='icon-vpn_key'></i></label><span></span><div class='placeholder'>新密码</div>").children('input').focus();
	});
});
var editTemp;
//修改
function toEdit(data){
	editTemp=data;
	$("#editModal").modal("show");
	initEditForm(editTemp);
	//初始化密码框
	$("#password_edit_div").html('<label for="password"><i class="icon-vpn_key"></i></label>'+
					'<div class="btnarea-right">'+
				'<button class="btn btn-nor btn-md"  id="password_edit">修改密码</button>'+
		'</div>');
}
function editReset(){
	var data=editTemp;
	initEditForm(data);
}

function initEditForm(data){
	$("#editModal .user-title").text(data.nickname);
	$("#editModal #loginname_edit").val(data.loginname);
	$("#editModal #nickname_edit").val(data.nickname);
	$("#editModal #phone_edit").val(data.phone);
	$("#editModal #email_edit").val(data.email);
	
	$("#editModal #comment_edit").val(data.description);
	
	
	//初始化角色
	$("#roles_edit input[name='role_edit']").each(function(){
		$(this).removeAttr("checked");
		var value=this.value;
		for(var index=0;index<data.roles.length;index++){
			var temp=data.roles[index];
			if(value==temp.roleId){
				this.checked="checked";
				break;
			}
		}
	});
	
	//初始化状态
	$("#status_edit input[name='edit_status']").each(function(){
		$(this).removeAttr("checked");
		var value=this.value;
			if(value==data.status){
				this.checked="checked";
			}
	});
}

function toDelete(data){
	var userId=data.adminUserId;
	var url="system/"+userId+"/deleteUser.htmls";
	if(confirm("确定删除吗？")){
		$.post(url,{userId:userId},function(json){
			alert(json.message);
			if(json.status==1){
				var pageIndex=reactData.state.pageIndex;
				reactData.reload(pageIndex);//重新加载数据,并跳转当页
			}
		},"json");
	}
}

function toStatus(data){
	var userId=data.adminUserId;
	var url="system/"+userId+"/statusUser.htmls";
	var message="";
	var aim=0;
	if(data.status==0){
		message="确定激活吗？";
		aim=1;
	}else{
		message="确定冻结吗？";
		aim=0;
	}
	if(confirm(message)){
		$.post(url,{userId:userId},function(json){
			alert(json.message);
			if(json.status==1){
				data.status=aim;
				reactData.fresh();//刷新
			}
		},"json");
	}
}

</script>
		<div class="sf-modal modal-full" style="display: none;" id="editModal">
			<div class="panel">
				<div class="panel-title">
					<span>修改用户<strong  class="user-title">suninverse</strong>的信息</span>
					<div class="btnarea-right full">
						<button class="btn-square modal-close" title="关闭">
								<i class='icon-close icon-gray'></i>
						</button>
					</div>
				</div>
				<div class="panel-content">
					<div class="formarea formarea-pad form-info form-lg">
								<div class="notice info closable">
									如不想更换密码，请置空
									<div class="close"></div>
								</div>
								<div class="formsection">
									<div>
									<div class="formitem withicon">
									<input type="text" id="loginname_edit" value="suninverse" disabled><label for="username"><i class="icon-person"></i></label>
										<span></span>
										<div class="placeholder">
											用户名
										</div>
									</div>
									<div class="formitem withicon">
										<input type="text" id="nickname_edit" maxlength="10" value="克利夫兰"><label for="nickname"><i class="icon-bubble_chart"></i></label>
										<span></span>
										<div class="placeholder">
											昵称
										</div>
									</div>
									<div class="formitem withicon" id="password_edit_div">
										<label for="password"><i class="icon-vpn_key"></i></label>
										<div class="btnarea-right">
											<button class="btn btn-nor btn-md"  id="password_edit">修改密码</button>
										</div>
									</div>
									<div class="formitem withicon">
										<input type="tel" id="phone_edit" maxlength="11" value="18920392837"><label for="phone"><i class="icon-phone_android" required></i></label>
										<span></span>
										<div class="placeholder">
											手机
										</div>
									</div>
									<div class="formitem withicon">
										<input type="text" id="email_edit" value="example@sunphun.com" maxlength="50" ><label for="username"><i class="icon-mail"></i></label>
										<span></span>
										<div class="placeholder">
											邮件
										</div>
									</div>
									</div>
									
									
									<div class="form-row withicon">
											<div class="checkarea-title">
												<i class="icon-account_circle"></i> 用户角色
											</div>
											<div class="checkarea" id="roles_edit">
											<c:forEach	items="${roles }" var="role" varStatus="status">
												<input type="checkbox" name="role_edit" value="${role.roleId }" id="role${status.index }">
												<label for="role${status.index }">${role.roleName }</label>
											</c:forEach>
											</div>
									</div>
									<div class="form-row withicon">
										<div class="checkarea-title">
											<i class="icon-report"></i> 用户状态
										</div>
										<div class="checkarea radio" id="status_edit">
											<input type="radio" id="status1" value="1" name="edit_status"><label for="status1">激活</label>
											<input type="radio" id="status2" value="0" name="edit_status"><label for="status2">冻结</label>
										</div>
									</div>
									<div class="form-row withicon">
										<textarea name="comment_edit" id="comment_edit" maxlength="200"></textarea>
										<label for="user-comment"><i class="icon-comment"></i></label>
										<div class="placeholder">
											用户备注
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
		


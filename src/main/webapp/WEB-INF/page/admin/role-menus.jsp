<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header>
	<h1 class="t-rd">角色权限分配</h1>
</header>

<script type="text/javascript">
	$(function(){
		$("#rolelist").render("Table_li", {
				index:true,
				url:"system/power.htmls?operator=json",
				title:"角色权限分配",
				heads:[
				       {name:"roleName",title:"角色"},
				       {name:"count",title:"用户数",style:"define",content:"<a href='#'>{count}</a>"},
//				       {name:"status",title:"状态",style:"define",content:"<a href='#'>{count+status}</a>"}
				       {name:"status",title:"状态",style:"status",alive:1}
				       ],
				 saveBtn:{powerCode:"system-role-add",save:function(react){
					 
					 $.SF.prompt({
							title:"新增角色",
							placeholder:"请输入角色名字",
							default_:"",
							callback:function(name){
									if(!name||!$.trim(name)){
										alert("角色名字不能为空");
									}
									if(name==""){return;}
									var url="system/roleAdd.htmls";
									$.post(url,{roleName:name},function(json){
											alert(json.message);
										if(json.status==1){
											react.reload(function(){
												$(react.getDOMNode()).find(".tbody ul:last").click();
											});
										}
									},"json");
								}
						});
					 
				 }},
				 btn:[{title:"更改角色名",icon:"icon-pencil",powerCode:"system-role-update",btnClass:"btn-square",
					 onClick:function(event,data,orignl,react){
					$.SF.prompt({
						title:"更改角色名字",
						placeholder:"请输入角色名字",
						default_:data.roleName,
						callback:function(name){
								if(!name||!$.trim(name)){
									alert("角色名字不能为空");
								}
								if(name==data.roleName){return;}
								var url="system/roleUpdate/"+data.roleId+".htmls";
								$.post(url,{roleName:name},function(json){
										alert(json.message);
									if(json.status==1){
										orignl.roleName=name;
										react.fresh();
									}
								},"json");
							}
					});
					
					 
				 }},{title:"角色状态切换",icon:"icon-lock_outline",powerCode:"system-role-status",btnClass:"btn-square",
					 onClick:function(event,data,orignl,react){
					 var info="";
					 if(data.status==0){
						 info="确定激活 "+data.roleName+" 角色吗";
					 }else{
						 info="确定冻结 "+data.roleName+" 角色吗";
					 }
					if(confirm(info)){
						var url="system/roleStatus.htmls?roleId="+data.roleId;
						$.post(url,function(json){
							alert(json.message);
							if(json.status==1){
								orignl.status=orignl.status==0?1:0;
								react.fresh();
							}
							
						},"json")
					}
				 
				 }} ],
				 
				},function(dom){
					$(dom).on("click",".tbody ul",function(){
						if($(this).hasClass("active")){return;};
						var datas=$(this).data("data");
						if(datas){
							
							loadPowerTree(datas.roleId,datas.roleName);
						}
					});
					var task=setInterval(function(){
						if($(dom).find(".tbody ul:eq(0)")[0]){
							$(dom).find(".tbody ul:eq(0)").click();
							clearInterval(task);
						}
					}, 200)
				});
		
		function loadPowerTree(roleId,roleName){
			$("#caplist").render("Power_set",{
				url:"system/power.htmls?operator=power&roleId="+roleId,
				role:{name:roleName,id:roleId},
				saveBtn:{color:"red",powerCode:"system-power-update",icon:"icon-mode_edit",text:"修改",
					handleClick:function(json,react_obj){
						var url="system/powerUpdate/"+roleId+".htmls";
						$.ajax({
		                url: url,
		                type:"post",
		                data:JSON.stringify(processMenu(processMenu(json))),
		                dataType:"json",
		                contentType:"application/json",
		                success:function(data){
		                	alert("更新成功！");
		                	loadPowerTree(roleId);
		                },
		                error:function(data){
		                }
		            });
				}}},function(react_obj,dom){//callback
					
				});
		}
		
		
		
		function processMenu(menus,arr){
			if(!arr){arr=[]};
			if(menus.length&&menus.length>0){
				$(menus).each(function(){
					if(this.status==1){
						arr.push({menuId:this.menuId,menuName:this.name,status:this.status});
					}
					if(this.childMenu&&this.childMenu.length>0){
						processMenu(this.childMenu,arr)
					}
				});
			}
			return arr;
		}
		
	});
</script>
			<div id="maincontents">
			
					<div id="rolelist">
					</div>
					
					<div id="caplist">
					
					</div>
			</div>

		
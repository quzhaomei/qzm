/**
 * jquery-react 
 * @since 2016-06
 * @author quzhaomei
 * **/
(function(){
	
	var MENU_TEMPLATE=['<li>',
	                   '<a href="{url}" title="{title}" data-submenu="x">',
	                   	'<i class="{icon}"></i><span class="line"></span>',
	                   '</a>',
	                   '</li>'];
	
	var MENU_LOGO=['<div id="mainlogo">',
	               '<a href="{url}" title="控制台"><img src="{image}" alt="{title}"></a>',
	               '</div>'];
	
	var USER=['<div id="account">',
				'<div class="showsub offstage">',
				'<i class="icon-playlist_play"></i>',
			 '</div>',
			 '<div href="#" title="个人账号" class="avatar">',
					'<img src="{avatar}" alt="{name}">',
				'</div>',
				'<div href="#" title="退出" id="logout">',
					'<i class="icon-switch"></i>',
				'</div>',
				'</div>'];
	var CHILD_HEAD=['<div id="pagetitle">{title}</div>'];
	var CHILD_TEMPLATE=['<li>','<a href="{url}">{title}</a>','</li>',];//#{url}
	function parseTemplate(template,obj){
		var result=template;
		if(template instanceof Array){
			result=template.join("");
		}
		var strArr=result.match(/\{\s*\w[\w\d]*[^\{\}]*\}*/g);
		if(strArr){
			$(strArr).each(function(){
				var value="";
				var key=$.trim(this.substring(1,this.length-1));
				if(this.match(/\{\s*\w[\w\d]*\s*\}/)){//直接值
					value=obj[key];
					result=result.replace(this,value);
				}else{//处理运算
					var exp=key;
					var reg="";
					for(varible in obj){
						var value_=obj[varible];
						if(typeof value_=="string"){
							value_='"'+value_+'"';
						}else if(typeof value_=="object"){
							value_="eval('("+JSON.stringify(value_)+")')";
						}
						 reg+="var "+varible+"="+value_+"; ";
					}
					reg=reg.replace(/[\r\n]/g,"");
					try {
						eval(reg);
						value=eval(key);
					} catch (e) {
						value="";
					}
					result=value;
				}
				
			});
		}
		return result;
	}
	
	var DEFAULT_={LOGO:"images/logo.svg",ICON:"icon-cog",AVATAR:"images/avatar.jpg",NAME:"游客"};
	var SF={setConfig:function(key,value){this[key]=value;}
		};
	$.extend(SF,DEFAULT_);
	$.SF=SF
	/**自定义方法开始*/
	/**
	 * 菜单生成
	 * 
	 */
	function menu($obj,menus,callback){
		$obj.empty();
		var $nav=$("<nav>").attr("id","main-nav").attr("role","menu-ver-full");
			$nav.append(parseTemplate(MENU_LOGO,menus.logo));
		
		var $menu_ul=$("<ul>");
			$(menus.menu).each(function(){
				var menu_li=$(parseTemplate(MENU_TEMPLATE,this)).on("click");
				menu_li.data("menu",this);
				menu_li.attr("id","menu"+this.menuId);
				$menu_ul.append(menu_li)
			});
			
			if(menus.menu_){
				if(menus.menu&&menus.menu.length>0){
					$menu_ul.append('<div class="divider"></div>');
				}
				$(menus.menu_).each(function(){
					var menu_li=$(parseTemplate(MENU_TEMPLATE,this));
					menu_li.data("menu",this);
					menu_li.attr("id","menu"+this.menuId);
					$menu_ul.append(menu_li)
				});
			}
			$nav.append($menu_ul);
			$nav.append(parseTemplate(USER,menus.user))
			$obj.append($nav);
			
			
		$(".dynmic-menu").on("click","#main-nav li a",function(e){
			e.preventDefault();
			$(this).parents("li").addClass("active").siblings().removeClass("active")
			
			var menu=$(this).parents("li").data("menu");
			
			if(menu==$("#main-subnav").data("menu")){
				menu_toggle(menu.childMenu&&menu.childMenu.length>0);
			}else if(menu.childMenu&&menu.childMenu.length==1){//默认打开
				if($("#main-subnav")[0]){$("#main-subnav").remove()}
				var default_menu=menu.childMenu[0];
				callback(default_menu.url,default_menu);
				menu_close();
				
			}else if(menu.childMenu&&menu.childMenu.length>0){
				if($("#main-subnav")[0]){$("#main-subnav").remove()}
				var $child_nav=$("<nav>").attr("id","main-subnav").addClass("anim");
				$child_nav.append(parseTemplate(CHILD_HEAD,{title:menu.title}));
				var $child_ul=$("<ul>");
				$(menu.childMenu).each(function(){
					this.parent=menu;
					var $chile_li=$(parseTemplate(CHILD_TEMPLATE,this));
					$chile_li.data("menu",this).attr("id","menu"+this.menuId);
					$child_ul.append($chile_li);
				});
				$child_nav.append($child_ul);
				$child_nav.append('<span class="btn_hide"><i class="icon-drag_handle"></i></span>');
				$obj.append($child_nav);
				$child_nav.data("menu",menu);
				menu_open();
			}else{
				callback(menu.url);
				menu_close();
			}
		});
		
		$(".dynmic-menu").on("click","#main-subnav li a",function(e){
			e.preventDefault();
			$(this).parent('li').addClass('active').siblings().removeClass('active');
			var menu=$(this).parents("li").data("menu");
				callback(menu.url,menu);
		});
		$(".dynmic-menu").on("click",'#main-nav .showsub',function(){menu_open()})
		$(".dynmic-menu").on("click",'#main-subnav .btn_hide',function(){menu_close(true)})
		
		
		//处理ajax页面刷新问题
		if(real_parent_id!=""){
			$("#menu"+real_parent_id+" a").click();
			if(real_child_id!=""){
				setTimeout(function(){
					$("#menu"+real_child_id+" a").click();
				}, 100)
				
			}
		}
	}
	
	function menu_open(){
		$("#main-container").removeClass("menu-collapsed");
		$('#main-nav .showsub').addClass('offstage');
	}
	function menu_close(hasChild){
		$("#main-container").addClass("menu-collapsed");
		if(hasChild){
			$('#main-nav .showsub').removeClass('offstage');
		}else{
			$('#main-nav .showsub').addClass('offstage');
		}
	}
	function menu_toggle(hasChild){
		if($("#main-container").hasClass("menu-collapsed")){
			menu_open();
		}else{
			menu_close(hasChild);
		}
	}
	
	function createMarkup(html){
	 	 return {__html:html}
	 	}
	

	//权限设置
	var Power_set=React.createClass({displayName: "Power_set",
		getRootDom:function(){
			return this.refs.main.getDOMNode();
		},
		
		getInitialState: function() {	
		 $.post(this.props.url,function(json){
		 		this.setState({datas:json,status:"readonly",datas_bak:$.extend(true,[],json)});
		 		$(this.getRootDom()).fadeIn(300);
		 	}.bind(this),"json");	
	     	return {
	     		datas:[]
	     	};
		 },
		 componentDidMount: function() {
	 	 },
	 	  componentDidUpdate:function(){
		 	var $delay_class_add=$(this.getDOMNode()).find("[data-class-delay-add]");
		 	$delay_class_add.each(function(){
		 		var json=$(this).attr("data-class-delay-add");
		 		if(json){
		 		json=eval("("+json+")");
		 		var _this=this;
		 		setTimeout(function(){$(_this).addClass(json.className)},json.time);
		 		}
		 	});

		 	var $delay_class_remove=$(this.getDOMNode()).find("[data-class-delay-remove]");
		 	$delay_class_remove.each(function(){
		 		var json=$(this).attr("data-class-delay-remove");
		 		if(json){
		 			json=eval("("+json+")");
		 		var _this=this;
		 		$(_this).removeClass(json.className);
		 		}	 		
		 	});
		 },
	 	 fresh:function(){
	 	 	this.setState(this.state);
	 	 },
	 	 doOperatorClick:function(menu,parent,root){
	 	 	if(this.state.status=="updating"){return;}  
	 	 	if(parent.status==0)return;
	 	 	if(menu.status==1){
	 	 		menu.status=0;
	 	 	}else{
	 	 		menu.status=1;
	 	 	}
	 	 	this.fresh(); 
	 	 },
	 	 doParentClick:function(parent,root){
	 	 	if(this.state.status!="edit"){return;}
	 	 	var count=0;
	 	 	if(parent.status==0)return;
			 $(parent.childMenu).each(function(){
			 	if(this.status==1){count++};
				});
			 if(count==parent.childMenu.length){
			 	$(parent.childMenu).each(function(){
			 		this.status=0;
				});
			 }else{
			 	$(parent.childMenu).each(function(){
			 		this.status=1;
				});
			 } 
	 	 	this.fresh(); 
	 	 },
	 	 doRootClick:function(parent,root){
	 	 	if(this.state.status!="edit"){return;}
	 	 	var count=0;
			if(parent.status==1){
	 	 		parent.status=0;
	 	 		//root.status=0;
	 	 	}else{
	 	 		parent.status=1;
	 	 		//root.status=1;
	 	 	}
			this.doRootStatus(root)
	 	 	this.fresh(); 
	 	 },
	 	 doRootStatus:function(root){
	 		 var count=0;
	 		 $(root.childMenu).each(function(){
	 			 if(this.status==1){
	 				 count++;
	 			 }
	 		 });
	 		 if(count==0){
	 			root.status=0;
	 		 }else{
	 			 root.status=1;
	 		 }
	 	 },
	 	 doSave:function(datas,obj){
	 	 	if(this.state.status=="readonly"){
	 	 		this.state.status="edit";
	 	 		this.fresh();
	 	 	}else{
	 	 		this.state.status="updating";
	 	 		this.props.saveBtn.handleClick(datas,obj)
	 	 	}
	 	 },
	 	 doCancel:function(datas,obj){
	 	 	this.state.status="readonly";
	 	 	this.state.datas=$.extend(true,[],this.state.datas_bak)
	 	 	this.fresh();
	 	 },
		render:function(){
			var hide={"display":"none"}
			var _this=this;
			var perMenu=this.state.datas.map(function(temp,index){
				var chile_menus=(temp.childMenu&&temp.childMenu.length>0)?temp.childMenu.map(function(temp_){
					var status_="unchecked";
					var count=0;
			 	 	$(temp_.childMenu).each(function(){
			 	 		if(this.status==1){count++};
			 	 	});
			 	 	if(temp_.childMenu.length==0){status_=""}else if(count==0){status_="unchecked"}else if(count!=0&&count<temp_.childMenu.length){status_="partially-checked"}else if(count==temp_.childMenu.length){status_="checked"}
					
					var chile_menus_child=(temp_.childMenu&&temp_.childMenu.length>0)?temp_.childMenu.map(function(temp__){
						var status__="unchecked";
						if(temp__.status&&temp__.status==1){status__="checked"};
						//操作状态
							return React.createElement("div", {"data-role": "checkgroup-child", "data-form-status": status__, onClick: function(){_this.doOperatorClick(temp__,temp_,temp)}}, temp__.name);
							}):null;
						return	React.createElement("ul", {"data-page-status": temp_.status==1?"show":"hide"}, 
										React.createElement("li", {title: temp_.status==1?"当前可见":"当前隐藏"}, React.createElement("div", {onClick: function(){_this.doRootClick(temp_,temp)}})), 
										React.createElement("li", null, temp_.name), 
										React.createElement("li", null, React.createElement("div", {"data-form-status": status_, "data-role": "checkgroup-parent", onClick: function(){_this.doParentClick(temp_,temp)}, 
										"data-target": "#contents"})), 
										React.createElement("li", {id: "general", className: "cap checklables"}, chile_menus_child)									
									);
								}):null;

				return React.createElement("div", {className: "table"}, React.createElement("div", {className: "thead"}, 
									React.createElement("ul", null, 
										React.createElement("li", null, React.createElement("span", null, temp.name)), 
										React.createElement("li", null, React.createElement("span", null, "模块可用权限"))
									)
								), 
								React.createElement("div", {className: "tbody tbody-group"}, chile_menus));
	 
		
			});
			var  btn=null;
			if(this.state.status=="readonly"){
				 btn=[React.createElement(Button_Part, {key: "aaa",  powerCode: _this.props.saveBtn.powerCode, icon: this.props.saveBtn.icon, text: this.props.saveBtn.text, 
									handClick: function(){_this.doSave(_this.state.datas,_this)}})]	
			}else
			if(this.state.status=="edit"){
				btn=[
					React.createElement(Button_Part, {powerCode: _this.props.saveBtn.powerCode, key: "ccc", delayAdd: "200", className: "btn-square", icon: "icon-check icon-green", 
					title: "保存", handClick: function(){_this.doSave(_this.state.datas,_this)}}),
					React.createElement(Button_Part, {powerCode: _this.props.saveBtn.powerCode, key: "ddd", delayAdd: "200", className: "btn-square", icon: "icon-close icon-red", 
					title: "取消", handClick: function(){_this.doCancel(_this.state.datas,_this)}}),
					React.createElement(Button_Part, {key: "bbb", delayMiss: _this.state.status=="edit"?200:"", 
						icon: this.props.saveBtn.icon, text: this.props.saveBtn.text})
				]
			}

			return React.createElement("div", {className: "panel", ref: "main", style: hide}, 
							React.createElement("div", {className: "panel-title "}, 
								React.createElement("span", null, 
									this.props.role.name
								), 
								
								React.createElement("div", {"data-class-delay-add": this.state.status=="edit"?"{time:200,className:'full'}":"", 
									"data-class-delay-remove": this.state.status!="edit"?"{time:200,className:'full'}":"", 
								className: "btnarea-right"}, 
								btn
																
								)
							), 
							React.createElement("div", {className: "table-area", "data-form-status": this.state.status=="readonly"?"uneditable":"editable"}, 
								perMenu
							)
							);
		}

	});




	var Button_Part=React.createClass({displayName: "Button_Part",
		getRootDom:function(){
			return this.refs.main.getDOMNode();
		},
		getInitialState: function() {
			var _this=this;
			var powerCode=this.props.powerCode;			
			var url=$.SF["powerCheckUrl"];
			if(url&&powerCode){
				$.post(url,{powerCode:powerCode},function(json){
					if(!json||json.status==0){
						_this.setState({"status":0});
					}else{
						_this.setState({"status":1});
					}
				},"json");
			}
			if(powerCode){
	     		return {status:0};
			}else{
				return {status:1};
	     	}
		 },
		 componentDidMount: function() {
		 	var code=this.props.code; 


		 	var aim=this.getDOMNode();
		 	var data_miss=$(aim).attr("data-missing-time");
		 	if(data_miss){
		 		$(aim).addClass("hide");
		 		setTimeout(function(){$(aim).hide()},data_miss);
		 	}
		 	var delay_add=$(aim).attr("data-add-time");
	 	  	if(delay_add){
	 	  		$(aim).hide();
	 	  		setTimeout(function(){$(aim).show()},delay_add);
	 	  	}	 	 	
	 	 },
	 	 componentDidUpdate:function(){
	 	 	var aim=this.getDOMNode();
		 	var data_miss=$(aim).attr("data-missing-time");
		 	if(data_miss){
		 		setTimeout(function(){$(aim).hide()},data_miss);
		 	}
		 	var delay_add=$(aim).attr("data-add-time");
	 	  	if(delay_add){
	 	  		setTimeout(function(){$(aim).show()},delay_add);
	 	  	}
	 	 },
		 render:function(){
		 	var none={"display":"none"}
		 	if(this.state.status==0){
		 		return null;
		 	}
		 	var className;
		 	if(this.props.className){
		 		className=this.props.className;
		 		
		 	}else{
		 		className=this.props.color?("btn btn-md btn-iconleft "+this.props.color):"btn btn-md btn-iconleft";
		 	} 	
		 	if(this.props.delayAdd){
		 		return React.createElement("button", {style: this.props.style, style: none, "data-add-time": this.props.delayAdd, className: className, onClick: this.props.handClick, title: this.props.title}, 
										React.createElement("i", {className: this.props.icon}), React.createElement("text", null, this.props.text)
										)
		 	}
		 	if(this.props.delayMiss){
		 		return React.createElement("button", {style: this.props.style, "data-missing-time": this.props.delayMiss, className: className, onClick: this.props.handClick, title: this.props.title}, 
										React.createElement("i", {className: this.props.icon}), React.createElement("text", null, this.props.text)
										)
		 	}
		 	return React.createElement("button", {style: this.props.style, className: className, onClick: this.props.handClick, title: this.props.title}, 
										React.createElement("i", {className: this.props.icon}), React.createElement("text", null, this.props.text)
										);
		 }
		});



	var Icon_Btn=React.createClass({displayName: "Icon_Btn",
		getRootDom:function(){
			return this.refs.main.getDOMNode();
		},
		getInitialState: function() {
			var _this=this;
			var powerCode=this.props.powerCode;			
			var url=$.SF["powerCheckUrl"];
			if(url&&powerCode){
				$.post(url,{powerCode:powerCode},function(json){
					if(!json||json.status==0){
						_this.setState({"status":0});
					}else{
						_this.setState({"status":1});
					}
				},"json");
			}
			if(powerCode){
	     		return {status:0};
			}else{
				return {status:1};
	     	}
		 },
		 componentDidMount: function() {
	 	 	
	 	 },
	 	 componentDidUpdate:function(){
	 	 
	 	 },
		 render:function(){
		 	if(this.state.status==0){
		 		return null;
		 	}
		 	if(this.props.type==1){
		 		return React.createElement("i", {className: this.props.icon, onClick: this.props.handClick});
		 	}
		 	if(this.props.type==2){
		 		return React.createElement("button", {title: this.props.title, onClick: this.props.handClick}, React.createElement("i", {className: this.props.icon}));
		 	}
		 	return React.createElement("span", {title: this.props.title, onClick: this.props.handClick}, React.createElement("i", {className: this.props.icon}));
		 }
		});


	//li table 不带分页
	var Table_li=React.createClass({displayName: "Table_li",
		getRootDom:function(){
			return this.refs.main.getDOMNode();
		},
		reload:function(callback){
	 	 	this.initData(callback);
	 	 },
		getInitialState: function() {	
			this.initData();
	     	return {datas:[],current:{}};
		 },
		 initData:function(callback){
		 	var _this=this;
			$.post(this.props.url,function(json){
		 		this.setState({datas:json,current:{}});  	
		 			$(this.getDOMNode()).find("div.dataContainer ul").each(function(index){
		 			$(this).data("data",_this.state.datas[index]);
		 		}); 		
		 			if(callback){callback()};
		 	}.bind(this),"json");
		 },
		 componentDidMount: function() {
		 	
	 	 },

	 	 onLiClick:function(event,temp){
	 	 	var node=event.target;
	 	 	if(node.tagName!="UL"){
	 	 		node=$(node).parents("ul")[0];
	 	 	}
	 	 	$(node).addClass("active").siblings().removeClass("active");
	 	 	this.state.current=temp;
	 	 	this.fresh();
	 	 },
	 	 fresh:function(){
	 	 	this.setState(this.state);
	 	 },
		render:function(){
			var _this=this;
			var head_index=this.props.index?React.createElement("li", null, React.createElement("span", null, "No.")):null;
			var head_li=this.props.heads.map(function(temp){
				return React.createElement("li", null, React.createElement("span", null, temp.title));
			});

			var body_ul=this.state.datas.map(function(temp,index){
				var data_li=[];
				var temp_status=true;
				if(_this.props.index){
					data_li.push(React.createElement("li", {className: "index"}, index+1));
				};
				for(var i=0;i<_this.props.heads.length;i++){
					var pros_temp=_this.props.heads[i];
					var style=pros_temp.style;
					var key=pros_temp.name;
					var live=pros_temp.alive||1;  
					switch (style) {
					case "define":
						var content=pros_temp.content;
						content=parseTemplate(content,temp);
						data_li.push(React.createElement("li", {dangerouslySetInnerHTML: createMarkup(content)}))
						break;
					case "status":
						data_li.push(temp[key]==live?React.createElement("li", {className: "status"}, React.createElement("i", {className: "icon-check"}))
								:React.createElement("li", {className: "status"}, React.createElement("i", {className: "icon-remove_circle_outline"})));
						temp_status=temp[key]==live;
						break;
					default:
						data_li.push(React.createElement("li", null, temp[key]))
						break;
					}		
				}
				return React.createElement("ul", {onClick: function(){_this.onLiClick(event,temp);}, className: temp_status?null:"suspend"}, 
								data_li
									);			
			});
			var btn=this.props.btn.map(function(temp){
				return React.createElement(Button_Part, {powerCode: temp.powerCode, className: temp.btnClass, icon: temp.icon, 
				title: temp.title, handClick: function(event){temp.onClick(event,$.extend(true,{},_this.state.current),_this.state.current,_this)}});
			});
			return React.createElement("div", {className: "table panel", ref: "main"}, 
							React.createElement("div", {className: "panel-title"}, 
								React.createElement("span", null, 
									this.props.title
								), 
								React.createElement("div", {className: "btnarea-right full"}, 
									React.createElement(Button_Part, {powerCode: _this.props.saveBtn.powerCode, className: "btn-square", icon: "icon-add", 
									title: "添加角色", 
									handClick: function(){_this.props.saveBtn.save(_this)}})
								)

							), 
							
							React.createElement("div", {className: "table-area"}, 
								React.createElement("div", {className: "thead"}, 
									React.createElement("ul", null, 
										head_index, 
										head_li
									)
								), 
								React.createElement("div", {className: "tbody clickable strip dataContainer"}, 
									body_ul
								)
							), 
							React.createElement("div", {className: "panel-foot"}, 
									React.createElement("div", {className: "btnarea-right full"}, 
									btn	
									)								
							)

							);
		}
	});

	

	var Prompt=React.createClass({displayName: "Prompt",
		getRootDom:function(){
			return this.getDOMNode();
		},
		getInitialState: function() {				
	     	return {datas:[]};
		 },
		componentDidMount: function() {
		 		
	 	 },
		render:function(){
			var _this=this;
		 	return React.createElement("div", {className: "modal modal-sm"}, 
				React.createElement("div", {className: "panel"}, 
					React.createElement("div", {className: "panel-title"}, 
						React.createElement("span", null, this.props.title), 
						React.createElement("div", {className: "btnarea-right full"}, 
							React.createElement("button", {className: "btn-square", title: "关闭", onClick: this.props.close}, 
									React.createElement("i", {className: "icon-close icon-gray"})
							)
						)
					), 
					React.createElement("div", {className: "panel-content"}, 
							React.createElement("div", {className: "formarea formarea-pad"}, 
								React.createElement("div", {className: "form-row withicon"}, 
									React.createElement("input", {type: "text", ref: "input", placeholder: this.props.placeholder, defaultValue: this.props.default_})
								)
								
							)
							
						), 
					React.createElement("div", {className: "panel-foot"}, 
						React.createElement("div", {className: "btnarea-center"}, 
							React.createElement("button", {className: "btn btn-nor btn-iconleft", onClick: function(){_this.props.save(_this.refs["input"].getDOMNode().value);}}, 
										React.createElement("i", {className: "icon-check"}										
										), React.createElement("text", null, "确认")
							)
						)
					)
				)
			);
		 }
		});


	var DropDown=React.createClass({displayName: "DropDown",
		getRootDom:function(){
			return this.getDOMNode();
		},
		getInitialState: function() {
			return {current:this.props.current}
		 },
		 componentDidMount: function() {
		 		
	 	 },
	 	 toggle:function(){
	 	 	var main=this.getDOMNode();
	 	 	$(main).toggleClass("shown");
	 	 },
	 	 select:function(e){
	 	 	$(this.getDOMNode()).removeClass("shown");
	 	 	var target=e.target;
	 	 	var index=$(target).data("index");
	 	 	var data=this.props.options[parseInt(index)];
	 	 	if(this.props.select){
	 	 		this.props.select(data,this);
	 	 		this.state.current=data.value;
	 	 		this.setState(this.state);
	 	 	}
	 	 },
	 	 selectAll:function(){
	 	 	$(this.getDOMNode()).removeClass("shown");
	 	 	if(this.props.select){
	 	 		this.props.select(null,this);
	 	 		this.state.current=null;
	 	 		this.setState(this.state);
	 	 	}
	 	 },
		render:function(){
			var _this=this;
			var title="全部"+_this.props.title;

			var options=this.props.options.map(function(temp,index){
				var className="";
				if(temp.value==_this.state.current){
					className="active";
					title=temp.title;
				}
				return React.createElement("li", {onClick:  _this.select, "data-index": index, key: temp.value, className: className}, temp.title);
			});
			return React.createElement("div", {className: "dropdown-container menu-down"}, 
						React.createElement("div", {className: "dropdown-title", onClick: _this.toggle}, title), 
								React.createElement("ul", {className: "dropdown-menu"}, 
									React.createElement("li", {className: _this.state.current?null:"active", onClick: _this.selectAll}, "全部"+_this.props.title), 
												options
										)													
								);
		}
	});

	var Table_panel=React.createClass({displayName: "Table_panel",
		getRootDom:function(){
			return this.getDOMNode();
		},
		getInitialState: function() {
			var _this=this;

			$.post(this.props.url,function(json){
				_this.state.datas=json;
				_this.setState(_this.state);
			},"json");

	     	return {datas:[]};
		 },
		componentDidMount: function() {
		 		
	 	 },
	 	 callEditback:function(data){ 	
	 		this.props.editCall.callback(data);
	  	 },
	 	render:function(){
	 		var _this=this;
	 		var spans=null;
	 		var mouseenter_normal=function(e){var src=e.target;if(src.tagName!="SPAN"){src=$(src).parents("SPAN")[0]};
	 			$(src).find(".float-opt").show()};
	 		var mouseleave_normal=function(e){var src=e.target;if(src.tagName!="SPAN"){src=$(src).parents("SPAN")[0]};
	 			$(src).find(".float-opt").hide()};

	 		if(this.state.datas.length>0){
	 			spans=this.state.datas.map(function(obj,index){
	 				result =parseTemplate(_this.props.content,obj);
	 				return React.createElement("span", {onMouseLeave: mouseleave_normal, onMouseEnter: mouseenter_normal}, React.createElement("text", null, result), 
	 						React.createElement("div", {className: "float-opt", style: {display: "none"}}, 
	 							React.createElement(Icon_Btn, {title: "修改", icon: "icon-pencil", powerCode: _this.props.editCall.powerCode, handClick: function(){_this.callEditback(obj)}})
	 						)
	 				)
	 				});
	 		}

	 		var operator=this.props.operator.map(function(temp){
	 			return 	React.createElement(Button_Part, {key: "addData", powerCode: temp.powerCode, 
	 							 className: "btn-square", icon: temp.icon, 
	 							title: temp.title, handClick: temp.callback});
	 		});

	 	 	return React.createElement("div", {className: "category"}, 
	 					React.createElement("div", {className: "title"}, 
	 							React.createElement("span", null, this.props.title)
	 							), 
	 							React.createElement("div", {className: "contents grids grid-2"}, 
	 							spans
	 							), 
	 				React.createElement("div", {className: "operationbar"}, 
	 					React.createElement("div", {className: "btnarea-right full"}, 
	 							operator
	 					)	
	 					)
	 				);
	 	 }
		});

	var Table_data=React.createClass({displayName: "Table_data",
		getRootDom:function(){
			return this.getDOMNode();
		},
		getInitialState: function() {
			var _this=this;
			var state={datas:[],pageIndex:1,pageSize:10,total:0,totalPage:0,
				showPage:false,pageGroup:[10,20,25,50,100,200],
				batch:_this.props.batch,filter:{type:0},hide:{},noData:true};
				//设置默认排序
			for(var i=0;i<this.props.head.length;i++){
				var temp=this.props.head[i];
				if(temp.sort&&temp.sort_default){
					state.sort={"name":temp.name,"direction":1}
				}
			}
			this.initState();
			return state;
		 },
		 reload:function(pagePosition){
		 	this.initState(pagePosition); 
		 },
		 initState:function(pagePosition){
		 	if(this.state&&this.state.select){
				this.state.select=[];
				}

		 	var _this=this;
			var whole=this.props.whole;//是否已经加载了全部数据		
			var url=this.props.url;
			if(whole){//假分页
				$.post(url,function(jsons){
					_this.state.datas=jsons;
					if(!jsons||jsons.length>0){
						_this.state.noData=false;
					}
					if(pagePosition=="last"){
						var totalPage=Math.ceil(_this.state.datas.length/_this.state.pageSize);
						_this.state.pageIndex=totalPage;
					}else if(new RegExp("\\d+").test(pagePosition)){
						var position=parseInt(pagePosition,"10");
						var totalPage=Math.ceil(_this.state.datas.length/_this.state.pageSize);
						position=position>totalPage?totalPage:position;
						_this.state.pageIndex=position;
					}
					_this.state.total=jsons.length;
					_this.state.totalPage=Math.ceil(jsons.length/_this.state.pageSize);
					_this.fresh();
				},"json");
			}else{//真分页
				var pageIndex=1;
				var pageSize=10;
				var param={};
				if(pagePosition=="last"){
					_this.state.pageIndex=this.state.totalPage;
				}else if(new RegExp("\\d+").test(pagePosition)){
					var position=parseInt(pagePosition,"10");
					_this.state.pageIndex=position;
				}

				if(this.state&&this.state.pageIndex){
					pageIndex=this.state.pageIndex;
				}
				if(this.state&&this.state.pageSize){
					pageSize=this.state.pageSize;
				}
				if(this.state&&this.state.sort){
					param.sort=this.state.sort.name;
					if(this.state.sort.direction==1){
						param.direction="asc";
					}else{
						param.direction="desc";
					}
				}

				param.pageIndex=pageIndex;
				param.pageSize=pageSize;
				//初始化查询参数
				if(this.state&&this.state.filter&&this.state.filter.type==0&&this.state.filter.key){//关键词查询
					param.key=this.state.filter.key;
				}else if(this.state&&this.state.filter&&this.state.filter.type==1&&this.state.filter.column){
					for(var name in this.state.filter.column){
						var value=this.state.filter.column[name];

						for(var inner=0;inner<this.props.head.length;inner++){
							var column=this.props.head[inner];
							if(name==column.name&&column.key){
								name=column.key;
							}
						}

						
						if(value){
							if(value.start){//时间
								param[name+"_start"]=value.start;
							}
							if(value.end){
								param[name+"_end"]=value.end;
							}
							if(!value.hasOwnProperty("start")&&!value.hasOwnProperty("end")){
								param[name]=value;
							}
						}
						
					}
				}

				$.post(url,param,function(page){
					_this.state.datas=page.param;
					if(page.totalPage>0){
						_this.state.noData=false;
					}
					_this.state.total=page.count;
					_this.state.totalPage=page.totalPage;
					_this.state.statistics=page.statistics;//统计消息
					_this.fresh();
				},"json")
			}
		 },
		 download:function(){
			 var url=this.props.download.url;
			 var param={};
			//初始化查询参数
			if(this.state&&this.state.filter&&this.state.filter.type==0&&this.state.filter.key){//关键词查询
					param.key=this.state.filter.key;
				}else if(this.state&&this.state.filter&&this.state.filter.type==1&&this.state.filter.column){
					for(var name in this.state.filter.column){
						var value=this.state.filter.column[name];

						for(var inner=0;inner<this.props.head.length;inner++){
							var column=this.props.head[inner];
							if(name==column.name&&column.key){
								name=column.key;
							}
						}
						
						if(value){
							if(value.start){//时间
								param[name+"_start"]=value.start;
							}else if(value.end){
								param[name+"_end"]=value.end;
							}else {
								param[name]=value;
							}
						}
					}
				}
			
			var $form=$("<form>").attr("method","post");
			$form.attr("action",url);
			for(var temp in param){
				$form.append($("<input>").attr("name",temp).attr("value",param[temp]));
			}
			$form.submit();
			 
		 },
		 turnToPage:function(e,index){
		 		var totalPage=this.state.totalPage;
			 	index=typeof index=='number'?index:parseInt($(e.target).data("page"),10);
			 	if(index<1){return;}else if(index>totalPage){return;}else if(index==this.state.pageIndex){return;}
		 	if(this.props.whole){
			 	this.state.pageIndex=index;
			 	this.state.select=null;
			 	this.fresh();
			 }else{
			 	this.state.pageIndex=index;
			 	this.state.select=null;
			 	this.initState();
			 }
		 },
		 showPage:function(){
		 	if(this.state.showPage){
		 		this.state.showPage=false;
		 	}else{
		 		this.state.showPage=true;
		 	}
		 	if(this.props.whole){
		 		this.state.totalPage=Math.ceil(this.state.datas.length/this.state.pageSize);//初始化总共页数
		 		this.fresh();
		 	}else{
		 		this.initState();
		 	}
		 	
		 },
		 changeSize:function(e){
		 	var size=$(e.target).data("size");
		 	if(size){
		 		this.state.pageSize=parseInt(size,10);
		 		this.state.pageIndex=1;
		 		this.state.select=null;
		 		this.showPage();
		 	}
		 },
		 batchSelect:function(event){
		 	var index=$(event.target).data("index");
		 	var data=this.state.datas[index];
		 	var _this=this;
		 	if(this.state.select&&(this.state.select instanceof Array)){
		 		var remove=false;
		 		for(var inner=0;inner<this.state.select.length;inner++){
		 			if(this.state.select[inner]==data){
		 				remove=true;
		 				_this.state.select.splice(inner,1);
		 				break;
		 			}
		 		}
		 		if(!remove){
		 			this.state.select.push(data);
		 		}
		 	}else{
		 		this.state.select=[data];
		 	}
		 	this.fresh();
		 },
		 hasSelect:function(index){
		 	var data=this.state.datas[index];
		 	var has=false;	
		 	if(this.state.select&&(this.state.select instanceof Array)){
		 		$(this.state.select).each(function(index){
		 			if(this==data){
		 				has=true;	 			
		 			}
		 		});
		 	}
		 	return has;
		 },
		 selectAll:function(){//全选

		 	if(!this.state.select||this.state.select.length<this.state.pageSize){
		 		if(this.props.whole){
				 	var select=[];
				 	var start=(this.state.pageIndex-1)*this.state.pageSize;
					var end=this.state.pageIndex*this.state.pageSize;
					for(;start<end;start++){
						select.push(this.state.datas[start]);
					}
					this.state.select=select;
				}else{//真分页全选
					this.state.select=$.extend([],this.state.datas);
				}
				
			}else{
				this.state.select=null;
			}
			this.fresh();
		 },
		 batchStatu:function(){
		 	if(!this.state.select||this.state.select.length==0){
		 		return "icon-check_box_outline_blank";
		 	}else if(this.state.select.length<this.state.pageSize){
		 		return "icon-indeterminate_check_box";
		 	}else{
		 		return "icon-check_box";
		 	}
		 },
		 sort:function(event){
		 	var src=event.target;
		 	if(src.tagName!="TH"){
		 		src=$(src).parent("th")[0];
		 	}
		 	var key=$(src).data("sort");

		 	var sort=this.state.sort;
		 	if(!sort){
		 		this.state.sort={"name":key,"direction":1};
		 	}else if(sort.name==key){
		 		var direction=this.state.sort.direction*(-1);
		 		this.state.sort={"name":key,"direction":direction};
		 	}else{
		 		this.state.sort={"name":key,"direction":1};
		 	}

		 	var direction=this.state.sort.direction;
		 	this.state.pageIndex=1;
		 	this.state.select=null;

		 	if(this.props.whole){
			 	this.state.datas.sort(function(a,b){
			 		if (a[key]>b[key]){
			 			return direction;
			 		}else{
			 			return 0-direction;
			 		}
			 	});
			 	
		 		this.fresh();
			 }else{
			 	//真分页
			 	this.initState();
			 }
		 	$(src).data("direction",direction);

		 	
		 },
		 fresh:function(){ 	
		 	this.setState(this.state);	 	
		 },
		componentDidMount: function() {
		 	
	 	 },
	 	  componentDidUpdate: function() {
				 if(this.props.initcallback){
					 		this.props.initcallback(this.getDOMNode());
					 	}	
			 },
	 	 toggleFilter:function(){
			this.state.filter.show=!this.state.filter.show;
	 	 	this.fresh();
	 	 },
	 	 toggleNormal:function(){
	 	 	this.state.filter.show=false;	 	
	 	 	this.state.filter.type=0;	
	 	 	delete this.state.filter.column;
	 	 	delete this.state.filter.columnBak;
	 	 	var aim=this.refs.searchKey.getDOMNode();
	 	 	$(aim).val("").removeAttr("readonly","readonly").focus();
	 	 	this.fresh();
	 	 },
	 	  addSearchLabel:function(e){
	 	 	var target=e.target;
	 	 	var name=$(target).data("search");
	 	 	if(this.state.filter.columnBak){
	 	 		if(this.state.filter.columnBak.hasOwnProperty(name)){
	 	 			delete this.state.filter.columnBak[name];
	 	 		}else{
	 	 			this.state.filter.columnBak[name]="";
	 	 		}	 		
	 	 	}else{
	 	 		this.state.filter.columnBak={};
	 	 		this.state.filter.columnBak[name]="";
	 	 	}
	 	 	this.fresh();
	 	 },
	 	 inputFocus:function(){
	 	 	var searchDom=this.refs.searchKey.getDOMNode();
	 	 	if(this.state.filter.type==0){
	 	 		this.state.filter.show=false;
	 	 		this.fresh();
	 	 	}

	 	 },
	 	 deleteSearch:function(){
	 	 	delete this.state.filter.column;
	 	 	delete this.state.filter.columnBak;
	 	 	this.state.filter.type=0;
	 	 	this.state.filter.show=false;
	 	 	this.state.filter.key="";
	 	 	var searchDom=this.refs.searchKey.getDOMNode();
		 	$(searchDom).val("").removeAttr("readonly");
	 	 	this.state.pageIndex=1;
	 	 	this.state.select=null;
	 	 	if(!this.props.whole){
	 	 		this.initState();
	 	 	}else{
	 	 		this.fresh();
	 	 	}
	 	 },
	 	 hasSearch:function(name){
	 	 	var has=false;
	 	 	if(this.state.filter.columnBak&&
	 	 		this.state.filter.columnBak.hasOwnProperty(name)){
	 	 			has=true;
	 	 	}
	 	 	return has;
	 	 },
	 	 search:function(){
	 	 	var searchDom=this.refs.searchKey.getDOMNode();
	 	 	if(this.state.filter.columnBak){
	 	 		this.state.filter.column=$.extend({},this.state.filter.columnBak);
	 	 		var val=[];
	 	 		for(var element in this.state.filter.column){
	 	 			var text=this.state.filter.column[element];
	 	 			for(var inner=0;inner<this.props.head.length;inner++){
	 	 				var temp=this.props.head[inner];
	 	 				if(temp.name==element){
	 	 					sw:{switch (temp.style) {
								case "icon":
									$(temp.group).each(function(){
										if(this.value==text){
											text=this.title;
										}
									});
									break sw;
								case "date":
									var time=text;
									var text="";
									var start=time.start;
									var end=time.end;
									if(start){
										text+=start+"->";
									}
									if(end){
										text+="<-"+end;
									}
								default:
									break sw;
								}
							}
	 	 				}
	 	 			}
	 	 			if(text){
	 	 				val.push(text);
	 	 			}
	 	 			
	 	 		}
	 	 		for(var index=0;index<val.length;index++){
	 	 			if(!val[index]){
	 	 				val.splice(index,1);
	 	 				index--;
	 	 			}
	 	 		}
	 	 		if(val.length>0){//高级搜索
	 	 			searchDom.value=val.join(",");
	 	 		}else{
	 	 			searchDom.value="";
	 	 		}
	 	 		this.state.filter.type=1;
	 	 		this.state.filter.key=" ";
	 	 		$(searchDom).attr("readonly","readonly");
	 	 	}else{
	 	 		this.state.filter.type=0;
		 	 	key=searchDom.value;
		 	 	if(this.state.filter.key&&this.state.filter.key==key){return;}
		 	 	this.state.filter.key=$.trim(key);	 	 	
	 		 }
	 		 this.state.pageIndex=1;
			 this.state.select=null;
			 this.state.filter.show=false;
			 if(!this.props.whole){//真分页 后台查询
			 	this.initState();
			 }else{//重新计算总页数
				 
			 	 this.fresh();
			 }
			
	 	 },
	 	 doRemoteFilter:function(){
	 	 	this.initState();
	 	 },
	 	 doFilter:function(){
	 	 	if(!this.props.whole){//不作过滤
	 	 		return this.state.datas;
	 	 	}
	 	 	var datas=this.state.datas;
	 	 	var filter=this.state.filter;
	 	 	var key=filter.key;
	 	 	var aims=[];
	 	 	if(filter.type==0&&key){//普通搜索
	 	 		for(var index=0;index<datas.length;index++){
	 	 			var temp=datas[index];

	 	 			for(var inner=0;inner<this.props.head.length;inner++){
	 	 				var content=this.parseContent(this.props.head[inner],temp);
	 	 				if(typeof(content) != "undefined"&&content.match(new RegExp(key))){
	 	 					aims.push(temp);
	 	 					break;
	 	 				}
	 	 			}
		
	 	 		}
	 	 	}else if(filter.type==1){//精确搜索
	 	 		for(var index=0;index<datas.length;index++){
	 	 			var temp=datas[index];
	 	 			var match=true;

	 	 			for(var inner=0;inner<this.props.head.length;inner++){	 			
	 	 				var column=this.props.head[inner];

	 	 				if(filter.column&&filter.column[column.name]){//进行过滤
	 	 					var value=filter.column[column.name];
	 	 					if(column.value&&value){value=value[column.value]};
	 	 					if(column.group){
	 	 						if(temp[column.name]!=value){
	 	 							match=false;
	 	 							break;
	 	 						}
	 	 					}else if(column.style=="date"){//处理时间
	 	 						var dataTime=this.parseContent(this.props.head[inner],temp);
	 	 						var start=value.start;
	 	 						var end=value.end;
	 	 						if(dataTime&&start&& new Date(dataTime)< new Date(start)){
	 	 							match=false;
	 	 							break;
	 	 						}
	 	 						if(dataTime&&end&& new Date(end)< new Date(dataTime)){
	 	 							match=false;
	 	 							break;
	 	 						}

	 	 					}else{
	 	 						var content=this.parseContent(this.props.head[inner],temp);
				 	 				if(typeof(content) == "undefined"||!content.match(new RegExp(value))){
				 	 					match=false;
				 	 					break;
				 	 				}
	 	 					}

	 	 				}	 				
	 	 			}
	 	 			if(match){
	 	 					aims.push(temp);
	 	 				}
		
	 	 		}

	 	 	}else{
	 	 		aims=datas;
	 	 	}
	 	 	//重新计算页数
	 	 	this.state.total=aims.length;
	 	 	this.state.totalPage=Math.ceil(aims.length/this.state.pageSize);//初始化总共页数
	 	 	return aims;
	 	 },
	 	 parseResult:function(column,data,index){
	 	 	var tempData=data;
	 	 	var normalOperatorArr=this.props.normalOperator.map(function(temp,index){
	 	 		return React.createElement(Icon_Btn, {title: temp.title, icon: temp.icon, powerCode: temp.powerCode, handClick: function(){temp.callback(tempData)}});
	 	 	});
	 	 	
	 	 	var normalOperator=null;
				if(index==0){
						normalOperator=React.createElement("div", {className: "operation", style: {display:"none"}}, 
						normalOperatorArr
						
						);
					}
			

	 	 	var result;
	 	 	var className=column.className?column.className:null;
					switch (column.style) {
					case "define":
						var content=column.content;
						content=parseTemplate(content,data);
						result=React.createElement("td", {className: className, dangerouslySetInnerHTML: createMarkup(content)})
						break;
					case "label":	
						var group;
						var statusTag=data[column.name];
						if(column.value&&statusTag){statusTag=statusTag[column.value]};
						if(column.group){
							for(var i=0;i<column.group.length;i++){
								if(statusTag==column.group[i].value){
									group=column.group[i];
									break;
								}
							}
							if(group){
								result=React.createElement("td", {className: "td-label"}, React.createElement("span", {className: group.className}, group.title));
							}else{
								result=React.createElement("td", {className: "td-label"}, React.createElement("span", null, ""));
							}
						}else{
							result=React.createElement("td", null, React.createElement("span", {className: className}, parseTemplate(column.content,data)));
						}

						break;
					case "labels":	 
						var content=column.content;
						if(!className){
							className="label label-line label-gray";
						}
						content=parseTemplate(content,data);
						var body;
						if(content instanceof Array){
							body=content.map(function(temp){
								return React.createElement("span", {className: className}, temp);
							});
						}else{
							body= React.createElement("span", {className: className}, content);
						}
						result=React.createElement("td", null, body);

						break;
					case "icon":
						var group;
						var statusTag=data[column.name];
						if(column.value&&statusTag){statusTag=statusTag[column.value]};
						for(var i=0;i<column.group.length;i++){
							if(statusTag==column.group[i].value){
								group=column.group[i];
								break;
							}
						}
						if(group){
							result=React.createElement("td", {className: "td-icon"}, React.createElement("i", {title: group.title, className: group.icon}));
						}else{
							result=React.createElement("td", null, React.createElement("span", null, data[column.name]));
						}				
						break;
					case "date":
					var content=data[column.name];
					var format=column.format?column.format:"yyyy-MM-dd";
					if(content&&content.match(/^\d+$/)){
						var date=new Date(parseInt(content));
						result=React.createElement("td", {className: className}, React.createElement("span", null, date.format(format)));		
					}else{
						result=React.createElement("td", {className: className}, React.createElement("span", null));		
					}
						break;
					default:
					result=React.createElement("td", {className: className}, React.createElement("span", null, data[column.name]), normalOperator);
						break;
					}
				return result;
	 	 },
	 	 parseContent:function(column,data){
	 	 	var content="";
	 	 	switch (column.style) {
					case "define":
						var content=column.content;
						content=parseTemplate(content,data);
						break;
					case "label":	
						var group;
						if(column.group){
							var statusTag=data[column.name];
							if(column.value&&statusTag){statusTag=statusTag[column.value]};
							for(var i=0;i<column.group.length;i++){
								if(statusTag==column.group[i].value){
									group=column.group[i];
									break;
								}
							}
							if(group){
								content=group.title;
							}else{
								content=data[column.name];
							}
						}else{
							content=parseTemplate(content,data);
						}
						break;
					case "labels":	
						var value=column.content;
							value=parseTemplate(value,data);
						if(value instanceof Array){
							content=value.map(function(temp){
								return temp;
							}).join(" ");
						}else{
							content=value;
						}
						break;
					case "icon":
						var group;
						var statusTag=data[column.name];
						if(column.value&&statusTag){statusTag=statusTag[column.value]};
						for(var i=0;i<column.group.length;i++){
							if(statusTag==column.group[i].value){
								group=column.group[i];
								break;
							}
						}
						if(group){
							content=group.title;						
						}else{
							content=undefined;
						}				
						break;
					case "date":
						var value=data[column.name];
						var format=column.format?column.format:"yyyy-MM-dd";
						if(value&&value.match(/^\d+$/)){
							var date=new Date(parseInt(value));
							content=date.format(format)	
						}else{
							content="";
						}
						
						
						break;
					default:
					content=data[column.name];
						break;
					}
				return  content;
	 	 },
	 	 toggleHead:function(e){
	 	 	var name=$(e.target).data("name");
	 	 	if(this.state.hide.hasOwnProperty(name)){
	 	 		this.state.hide[name]=!this.state.hide[name];
	 	 	}else{
	 	 		var shown=$(e.target).parent("ul").hasClass("shown");
	 	 		if(shown){
	 	 			this.state.hide[name]=true;
	 	 		}else{
	 	 			this.state.hide[name]=false;
	 	 		}
	 	 		
	 	 	}
	 	 	this.fresh();
	 	 },
	 	 filterHead:function(){
	 	 	var heads=$.extend([],this.props.head);
	 	 	for(var index=0;index<heads.length;index++){
	 	 		if(this.state.hide.hasOwnProperty(heads[index].name)){ 
	 	 			if(this.state.hide[heads[index].name]){
	 	 				heads.splice(index,1);
	 	 				index--;
	 	 			}	 			
	 	 		}else if(heads[index].hasOwnProperty("remove")&&!heads[index].remove){
	 	 				heads.splice(index,1);
	 	 				index--;
	 	 		}
	 	 	}
	 	 	return heads;

	 	 },
	 	 initTableByDatas:function(datas){
	 	 	var _this=this;
	 	 	var head=[];
			var headColumn=_this.filterHead();

			if(this.state.batch){
				head.push(React.createElement("th", {className: "checkbox"}, React.createElement("span", null, React.createElement("i", {onClick: _this.selectAll, 
					className: _this.batchStatu()})
								)));
			}
			for(var i=0;i<headColumn.length;i++){
				var column=headColumn[i];
				var className=column.className?column.className:null;
				var sort=column.sort?"sortable":null;

				var data_sort=this.state.sort;
				if(data_sort&&sort){
					if(data_sort.name==column.name){
						if(data_sort.direction==1){
							sort=sort+" asc";
						}else if(data_sort.direction==-1){
							sort=sort+" desc";
						}
					}
				}

				if(className&&sort){
					className=className+" "+sort;
				}else if(!className){
					className=sort;
				}
				var dom_sort=column.name;
				var dom_sort_fn=sort?_this.sort:null;		

				switch (column.style) {
					case "define":
						head.push(React.createElement("th", {className: className, "data-sort": dom_sort, onClick: dom_sort_fn}, React.createElement("span", null, column.title)));
						break;
					case "label":
						className="td-label";
						head.push(React.createElement("th", {className: className, "data-sort": dom_sort, onClick: dom_sort_fn}, React.createElement("span", null, column.title)));
						break;
					case "labels":
						className=className?className+" td-labels":"td-labels";
						head.push(React.createElement("th", {className: className, "data-sort": dom_sort, onClick: dom_sort_fn}, React.createElement("span", null, column.title)));
						break;
					case "icon":	
						className=className?className+" td-icon":"td-icon";
						head.push(React.createElement("th", {className: className, "data-sort": dom_sort, onClick: dom_sort_fn}, React.createElement("span", null, column.title)));							
						break;
					default:
						head.push(React.createElement("th", {"data-sort": dom_sort, onClick: dom_sort_fn, className: className}, React.createElement("span", null, column.title)));
						break;
					}

			}
			

			if(_this.props.specialOperator){
				head.push(React.createElement("th", {className: "td-icon"}, React.createElement("span", null, "操作")))
			}


			var body=[];
			var start=(this.state.pageIndex-1)*this.state.pageSize;
			var end=this.state.pageIndex*this.state.pageSize;
			if(!this.props.whole){
				start=0;
				end=this.state.pageSize;
			}
			var temp_param={};
			for(var index=start;index<end&&index<datas.length;index++){
				var td=[];
				var data=datas[index];
				if(this.state.batch){
					td.push(React.createElement("td", {className: "checkbox"}, React.createElement("span", null, React.createElement("i", {"data-index": index, key: "data"+index, onClick: _this.batchSelect, 
						className: _this.hasSelect(index)?"icon-check_box":"icon-check_box_outline_blank"}))));			
				}
				headColumn.map(function(column,index){
					td.push(_this.parseResult(column,data,index));
				});

				var mouseenter=function(e){var src=e.target;if(src.tagName!="TD"){src=$(src).parents(".ops")[0]};
				 $(src).find(".toolicons").show();
				 $(src).find(".icon-add_box").hide();};
				var mouseleave=function(e){var src=e.target;if(src.tagName!="TD"){src=$(src).parents(".ops")[0]};
				$(src).find(".toolicons").hide();
				$(src).find(".icon-add_box").show();};


				if(_this.props.specialOperator){
					var operaArr=[];
					
					var operaArrReal=_this.props.specialOperator.map(function(temp){
						if(temp.filter){//按内容显示
							var match=false;
							if(temp.filter instanceof Array){//数组
								match=true;
									for(var index_fl=0;index_fl<temp.filter.length;index_fl++){
										var temp_match=false;
										var column_filter=temp.filter[index_fl].column;
										var values=temp.filter[index_fl].values;
										var current_value=data[column_filter];
										
										for(var index_f=0;index_f<values.length;index_f++){
											if(values[index_f]==current_value){
												temp_match=true;
												break;
											}else{
												temp_match=false;
											}
										}
										match=match&&temp_match;
									}
								}else{
									var column_filter=temp.filter.column;
									var values=temp.filter.values;
									var current_value=data[column_filter];
									for(var index_f=0;index_f<values.length;index_f++){
										if(values[index_f]==current_value){
											match=true;
											break;
										}
									}
								}
							if(match){
								return React.createElement(Icon_Btn, React.__spread({},  temp, {handClick: function(e){var tempDate=datas[$(e.target).parents(".toolicons").data("index")];temp.callback(tempDate)}}));
							}else{
								return null;
							}	
							
							
						}else{
							return React.createElement(Icon_Btn, React.__spread({},  temp, {handClick: function(e){var tempDate=datas[$(e.target).parents(".toolicons").data("index")];temp.callback(tempDate)}}));
						}
					});
					
					for(var inner=0;inner<_this.props.specialOperator.length;inner++){
						var temp=_this.props.specialOperator[inner];
						if(temp.filter){//按内容显示
							var match=false;
							if(temp.filter instanceof Array){//数组
								match=true;
								for(var index_fl=0;index_fl<temp.filter.length;index_fl++){
									var temp_match=false;
									var column_filter=temp.filter[index_fl].column;
									var values=temp.filter[index_fl].values;
									var current_value=data[column_filter];
									
									for(var index_f=0;index_f<values.length;index_f++){
										if(values[index_f]==current_value){
											temp_match=true;
											break;
										}else{
											temp_match=false;
										}
									}
									match=match&&temp_match;
								}
							}else{
								var column_filter=temp.filter.column;
								var values=temp.filter.values;
								var current_value=data[column_filter];
								for(var index_f=0;index_f<values.length;index_f++){
									if(values[index_f]==current_value){
										match=true;
										break;
									}
								}
							}
							
							if(match){
								operaArr.push(React.createElement(Icon_Btn, React.__spread({},  temp, {handClick: function(e){var tempDate=datas[$(e.target).parents(".toolicons").data("index")];temp.callback(tempDate)}})));
							}	
						}else{
							operaArr.push(React.createElement(Icon_Btn, React.__spread({},  temp, {handClick: function(e){var tempDate=datas[$(e.target).parents(".toolicons").data("index")];temp.callback(tempDate)}})));
						}
					}
					
					
					var operaArrSpan=null;
					if(operaArr&&operaArr.length&&operaArr.length>0){
						operaArrSpan=React.createElement("span", null, 
								React.createElement("i", {className: "icon-add_box"}), 
								React.createElement("div", {style: {display:"none"}, className: "toolicons", "data-index": index}, 
									operaArrReal
								));
					}
					td.push(React.createElement("td", {className: "td-icon ops", onMouseEnter: mouseenter, onMouseLeave: mouseleave}, 
							operaArrSpan
						));
				}
				var mouseenter_normal=function(e){var src=e.target;if(src.tagName!="TR"){src=$(src).parents("TR")[0]};
				 $(src).find(".operation").show()};
				var mouseleave_normal=function(e){var src=e.target;if(src.tagName!="TR"){src=$(src).parents("TR")[0]};
				$(src).find(".operation").hide()};

				body.push(React.createElement("tr", {onMouseLeave: mouseleave_normal, onMouseEnter: mouseenter_normal}, td));

			}
			var table_content=null;
			if(_this.state.noData){
				table_content=React.createElement("div", {key: "noDate", className: "noresult"}, React.createElement("div", {className: "info-sho info-nothing"}, 
													React.createElement("text", null, "当前没有数据")));
			}else if(datas.length==0){
				table_content=React.createElement("div", {key: "noresult", className: "noresult"}, 
				React.createElement("div", {className: "info-sho info-nothing info-withbtn"}, 
						React.createElement("text", null, "无搜索结果"), " ", React.createElement("span", {className: "goback", onClick: _this.deleteSearch}, React.createElement("i", {className: "icon-cross"}))
					)										
				)
			}else{
				table_content=React.createElement("table", {key: "datas"}, React.createElement("thead", null, React.createElement("tr", null, head)), 										
											React.createElement("tbody", {className: "strip"}, 
												body
											));
			}
			return table_content;

	 	 },
	 	 initPageLi:function(datas){
	 	 	var _this=this;
	 	 	var pagination=[];
			var totalPage=this.state.totalPage;
			var viewNum=9;//只显示9个
			if(totalPage<2){
				pagination=[];
			}else if(totalPage<=viewNum){

				pagination.push(React.createElement("div", {className: "prev", onClick: function(e){_this.turnToPage(e,_this.state.pageIndex-1)}}, React.createElement("i", {className: "icon-navigate_before"})));
				for(var index=1;index<=totalPage;index++){
					var temp=index;
					var className=index==this.state.pageIndex?"active page":"page";
					pagination.push(React.createElement("div", {key: index, "data-page": index, onClick: function(e){_this.turnToPage(e)}, className: className}, index))
				}
				pagination.push(React.createElement("div", {className: "next", onClick: function(e){_this.turnToPage(e,_this.state.pageIndex+1)}}, React.createElement("i", {className: "icon-navigate_next"})));
			
			}else{
				pagination.push(React.createElement("div", {className: "prev", onClick: function(e){_this.turnToPage(e,_this.state.pageIndex-1)}}, React.createElement("i", {className: "icon-navigate_before"})));
				pagination.push(React.createElement("div", {onClick: function(e){_this.turnToPage(e,1)}, className: _this.state.pageIndex==1?'page active':'page'}, "1"))

				if(this.state.pageIndex>Math.ceil(viewNum/2)){
					pagination.push(React.createElement("div", {className: "eclipse"}, React.createElement("i", {className: "icon-more_horiz"})));
				}else{
					pagination.push(React.createElement("div", {className: "page", className: this.state.pageIndex==2?'page active':'page', 
						onClick: function(e){_this.turnToPage(e,2)}}, "2"));
				}

				var inner_start;

				if(this.state.pageIndex<viewNum/2){
					inner_start=3;
				}else if(this.state.pageIndex>viewNum/2&&(this.state.pageIndex+viewNum/2-2)<totalPage){
					var start1=totalPage-viewNum+3;
					var start2=this.state.pageIndex-viewNum/2+2;
					inner_start=Math.ceil(Math.min(start1,start2));
					inner_start=inner_start>=3?inner_start:3;
				}else{
					inner_start=totalPage-(viewNum-3);
				}

				for(var i=inner_start;i<inner_start+viewNum-4;i++){
					pagination.push(React.createElement("div", {"data-page": i, onClick: _this.turnToPage, key: i, 
						className: this.state.pageIndex==i?'page active':'page'}, i));
				}

				if(this.state.pageIndex+Math.ceil(viewNum/2-1)<totalPage){
					pagination.push(React.createElement("div", {className: "eclipse"}, React.createElement("i", {className: "icon-more_horiz"})));
				}else {
					pagination.push(React.createElement("div", {onClick: _this.turnToPage, "data-page": totalPage-1,  key: totalPage-1, 
						className: this.state.pageIndex==totalPage-1?'page active':'page'}, totalPage-1));
				}

				pagination.push(React.createElement("div", {onClick: _this.turnToPage, "data-page": totalPage,key: totalPage,  className: this.state.pageIndex==totalPage?'page active':'page'}, totalPage));
				pagination.push(React.createElement("div", {className: "next", onClick: function(e){_this.turnToPage(e,_this.state.pageIndex+1)}}, React.createElement("i", {className: "icon-navigate_next"})));
			}

			var pageDiv=null;
			var pageGroup=this.state.pageGroup.map(function(temp){
				var content=temp+"条每页";
				return React.createElement("li", {dangerouslySetInnerHTML: createMarkup(content), key: temp+"page", "data-size": temp, onClick: _this.changeSize, className: temp==_this.state.pageSize?"active":null});
			});
			var className="pagination pagecenter";

			if(_this.props.whole&&_this.state.pageIndex==totalPage&&
				(datas.length<=_this.state.pageSize*(totalPage-1)+2)){
				className+=" bottom-fix"
			};
			if(!_this.props.whole&&datas.length<=2){
				className+=" bottom-fix";
			}

			if(datas.length>=10||_this.state.totalPage>1){
				pageDiv=React.createElement("div", {className: className}, 
											React.createElement("div", {className: "pages-container"}, 
												pagination
											), 
											React.createElement("div", {className: this.state.showPage?"pagesize dropdown-container menu-up shown":"pagesize dropdown-container menu-up"}, 
													React.createElement("div", {className: "dropdown-title", onClick: _this.showPage, dangerouslySetInnerHTML: createMarkup(_this.state.pageSize+"条每页")}
														
													), 
													React.createElement("ul", {className: "dropdown-menu"}, 
														pageGroup
													)
													
												)
										);
			}
			return pageDiv;
	 	 },
		render:function(){
			var _this=this;

			var datas=_this.doFilter();

			var pageDiv=_this.initPageLi(datas);

			var table_content=_this.initTableByDatas(datas);
		
			var searchLabel=[];
			for(var index=0;index<this.props.head.length;index++){
				var temp=this.props.head[index];
				if(_this.props.whole||temp.search){
					searchLabel.push(React.createElement("div", {"data-search": temp.name, onClick: _this.addSearchLabel, 
				className: _this.hasSearch(temp.name)?"addlabel added":"addlabel"}, temp.title));
				}

			}

			var searchSelect=null;
			var searchText=null;
			var searchDate=null;

			var searchSelect_child=null;
			var searchText_child=null;
			var searchDate_child=null;

			for(var index=0;index<this.props.head.length;index++){
				var column=this.props.head[index];
				var filter_column=this.state.filter.columnBak;
				if(filter_column&&filter_column.hasOwnProperty(column.name)){
							switch (column.style) {
								case "date":
								var columnFilter=filter_column[column.name];
								var start=column&&columnFilter.start?columnFilter.start:"";
								var end=column&&columnFilter.end?columnFilter.end:"";

								var setDateStartFilter=function(e){
									if(!filter_column[e.target.name]){filter_column[e.target.name]={};}
									filter_column[e.target.name]["start"]=$.trim(e.target.value)};
								var setDateEndFilter=function(e){
									if(!filter_column[e.target.name]){filter_column[e.target.name]={};}
									filter_column[e.target.name]["end"]=$.trim(e.target.value)};
					
								var arr=React.createElement("div", {key: column.name+"_search", className: "formitem"}, 
											React.createElement("label", {htmlFor: ""}, column.title), 

											React.createElement("input", {type: "text", onChange: setDateStartFilter, onBlur: setDateStartFilter, 
											defaultValue: start, 
											name: column.name, name: column.name, className: "datetimestart datetimepicker"}), 
											React.createElement("i", {className: "icon-remove"}), 

											React.createElement("input", {type: "text", name: column.name, onChange: setDateEndFilter, onBlur: setDateEndFilter, 
											 className: "datetimeend datetimepicker", defaultValue: end})
												);
									if(searchDate_child){
										searchDate_child.push(arr);
									}else{
										searchDate_child=[arr];
									}
									break;
								default:
									if(column.group){//select
										var select=function(data,obj){
											if(data){
												filter_column[obj.props.name]=data.value
												}else{
													filter_column[obj.props.name]=""
														}
											};
										var arr=React.createElement("div", {key: column.name+"_search", className: "formitem"}, 
											React.createElement(DropDown, {title: column.title, name: column.name, current: filter_column[column.name], 
											select: select, options: column.group}));
										if(searchSelect_child){
											searchSelect_child.push([arr]);
										}else{
											searchSelect_child=[arr];
										}
									}else{//
										var setInputFilter=function(e){filter_column[e.target.name]=$.trim(e.target.value)};
										var arr=React.createElement("div", {key: column.name+"_search", className: "formitem"}, 
													React.createElement("label", {htmlFor: ""}, column.title), 
													React.createElement("input", {onChange: setInputFilter, name: column.name, type: "text", 
													defaultValue: filter_column[column.name], name: column.name})
													);
										if(searchText_child){
											searchText_child.push(arr);
										}else{
											searchText_child=[arr];
										}
									}
									break;
								}
								
				}

			}
			
			if(searchSelect_child){
				searchSelect=React.createElement("div", {className: "selectarea"}, searchSelect_child);
			}		
			if(searchText_child){
				searchText=React.createElement("div", {className: "textarea"}, searchText_child);
			}	
			if(searchDate_child){
				searchDate=React.createElement("div", {className: "datetimearea"}, searchDate_child);
			}

			var searchDiv=null;
			if(searchSelect||searchText||searchDate){
				searchDiv=React.createElement("div", {className: "search-condition"}, searchSelect, searchText, searchDate);
				};

			var searchBtn=null;
			if(searchDiv){
				searchBtn=React.createElement("div", {className: "btnbar"}, 
								React.createElement("button", {className: "btn btn-nor btn-iconleft", onClick: _this.search}, 
												React.createElement("i", {className: "icon-search2"}), React.createElement("text", null, "搜索")), 

												React.createElement("button", {className: "btn btn-nor btn-iconleft btn-gray", onClick: _this.deleteSearch}, 
												React.createElement("i", {className: "icon-trash"}), React.createElement("text", null, "删除所有搜索条件"))
											)
			}

			var head_show=[];
			for(var index=0;index<_this.props.head.length;index++){

				var className="shown";
				var temp=_this.props.head[index];
				if(!temp.hasOwnProperty("remove")){continue;}
				if(_this.state.hide.hasOwnProperty([temp.name])){
					if(_this.state.hide[temp.name]){
						className="hide";
					}
				}else if(!temp.remove){
					className="hide";
				}
				head_show.push(React.createElement("ul", {className: className}, React.createElement("li", {"data-name": temp.name, onClick: _this.toggleHead}), React.createElement("li", null, temp.title)));
			}

			var searchClass="formsearch";
			if(_this.state.filter.show){
				searchClass+=" advanced";
			}
			if(_this.state.filter.type==1){
				searchClass+=" withresult";
			}

			var hideLeftBar=function(e){
				var src=e.target;
				if(src.tagName!="BUTTON"){
					src=$(src).parent("button");
				}
				$('.column-container').toggleClass('sidehide');
				if($(src).children('i').hasClass('icon-web_asset')) {
					$(src).html("<i class='icon-web'></i>").attr('title','隐藏右栏');
				} else {
					$(src).html("<i class='icon-web_asset'></i>").attr('title','显示右栏');
				}
			}
			var table_panel=null;

			if(this.props.table_panel){
				table_panel=React.createElement(Table_panel, React.__spread({},   this.props.table_panel));
			}
			var batchDiv=null;
			if(this.props.batch&&this.props.batch instanceof Array){
				var batchOperator=this.props.batch.map(function(temp){
					return React.createElement(Icon_Btn, {title: temp.title, icon: temp.icon, type: "2", 
					powerCode: temp.powerCode, handClick: function(){temp.callback(_this.state.select)}});
				});
				batchDiv=React.createElement("div", {className: "batcharea"}, 
									React.createElement("span", null, "批操作"), 
									React.createElement("div", {className: "btngroup"}, 									
										batchOperator
									)																								
								);
			}
			var download=null;
			if(_this.props.download){
				download=React.createElement("div", {className: "operationbar"}, 
						React.createElement("div", {className: "btnarea-right full"}, 
							React.createElement(Icon_Btn, {title: "数据下载", icon: "icon-cloud-download",type:"2",
								powerCode: _this.props.download.powerCode,
								handClick: _this.download})
						)	
					)
			}
			var statistics=[React.createElement("ul", null, 
							React.createElement("li", null, "总数"), 
							React.createElement("li", null, _this.state.total))];
			if(_this.props.statistics&&_this.state.statistics){
				for(var index=0;index<_this.props.statistics.length;index++){
					var temp_=_this.props.statistics[index];
					statistics.push(React.createElement("ul", null, 
							React.createElement("li", null, temp_.title), 
							React.createElement("li", null, _this.state.statistics[temp_.column])));
				}
			}
		 	return React.createElement("div", {className: "panel with-table-side"}, 
							React.createElement("div", {className: "panel-title"}, 
								React.createElement("span", null, 
									this.props.title
								), 		
								batchDiv, 
								React.createElement("div", {className: "btnarea-right full"}, 


								this.props.addBtn?React.createElement(Button_Part, {key: "addData", powerCode: _this.props.addBtn.powerCode, 
								 className: "btn-square", icon: "icon-add", 
								title: "保存", handClick: this.props.addBtn.callback}):null, 
								this.props.batchAddBtn?React.createElement(Button_Part, {key: "batchAddData", powerCode: _this.props.batchAddBtn.powerCode, 
								 className: "btn-square", icon: "icon-playlist_add", 
								title: "批量保存", handClick: this.props.batchAddBtn.callback}):null, 

								React.createElement("button", {className: "btn-square hide-right-col", title: "隐藏右栏", onClick: hideLeftBar}, React.createElement("i", {className: "icon-web"}))
								), 

							

								React.createElement("div", {className: "toolset"}, 
									React.createElement("div", {className: searchClass}, 
									 _this.state.filter.type==1?React.createElement("button", {key: "1-search", className: "btn-search searchreset", 
								 		 title: "返回模糊搜索", onClick: _this.toggleNormal}, React.createElement("i", {className: "icon-undo"})):
								 		 React.createElement("button", {className: "btn-search", key: "0-search", onClick: _this.search}, 
											React.createElement("i", {className: "icon-search2"})
										), 		

										React.createElement("button", {className: "btn-advanced", title: "更多搜索条件", onClick: _this.toggleFilter}, 
											React.createElement("i", {className: "icon-arrow_downward"})
										), 
										
										React.createElement("input", {type: "search", onFocus: _this.inputFocus, className: "mainsearch input-sm", ref: "searchKey", placeholder: "输入关键词"}), 


										React.createElement("div", {className: "advanced-area"}, 
											React.createElement("div", {className: "title"}, 
												"自定义搜索条件"
											), 
											React.createElement("div", {className: "addlabels"}, 
												searchLabel
											), 
												searchDiv, 
												searchBtn
								 		 )

								 		 
									)
								)
							), 
							React.createElement("div", {className: "panel-content"}, 
								React.createElement("div", {className: "column-container"}, 
									React.createElement("div", {className: "column-fluid"}, 
										table_content, 
										pageDiv																	
									), 
									
									React.createElement("div", {className: "column-fixed"}, 
										React.createElement("div", {className: "table-side-panel"}, 

											React.createElement("div", {className: "statistic"}, 
												React.createElement("div", {className: "title"}, 
													React.createElement("span", null, "数据统计")
												), 
												React.createElement("div", {className: "contents"}, 
														statistics
												), 
												download
											), 


											table_panel, 


											React.createElement("div", {className: "field-shown"}, 
												React.createElement("div", {className: "title"}, 
													React.createElement("span", null, "表单显示列设置")
												), 
												React.createElement("div", {className: "contents"}, 
													head_show
												)
											)
											
										)
									)
								)
							)
						)
		 }
		});























	/**react 组件结束*/
	







	function SetCookie(name,value){  
	    document.cookie = name + "="+ escape (value);  
	} 
	function getCookie(name){  
	    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));  
	    if(arr != null){  
	     return unescape(arr[2]);   
	    }else{  
	     return null;  
	    }  
	} 
	
	$.fn.extend({
		render:function(tagName,pros,callback){
			if(callback){
				
				$.extend(pros,{initcallback:callback});
			}	
			
			$(this).empty();
			var aim=ReactDOM.render(React.createElement(eval(tagName),pros),this[0]);
			if(callback){
				callback(aim.getRootDom());
			}
			return aim;
			},
		menu:function(pros,fn){//设置菜单位置
			var _this=this;
			var type=typeof pros
			switch (type) {
			case "string"://url
				$.post(pros,function(json){
					menu(_this,json,fn);
				},"json");
				break;
			case "object":
				menu(_this,pros,fn);
				break;

			default:
				break;
			}
		},
		open:function(url,callback){
			//保存访问记录
			SetCookie("current-url",url);
			this.empty().load(url,callback);
		},
		reload:function(){
			var url=getCookie("current-url");
			this.empty().load(url);
		},
		reset:function(){
			this.find("input[type='text']").val("");
			
			this.find("textarea").val("");
			this.find("select").each(function(){
				$(this).find("option").removeAttr("selected");
				$(this).find("option:eq(0)").attr("selected","selected");
				$(this).change();
			});
			this.find("input[type='radio']").removeAttr("checked");
			this.find("input[type='checkbox']").removeAttr("checked");
		},
		validator:function(param){//验证框架
			var _this=this;
			this.data("param",param);
			var require=param.require;
			var regExp=param.regExp;
			var callback=param.callback;//非空回调
			var callback1=param.callback1;//正则回调
			var success=param.success;
			
			
		
			
			this.on("blur",function(){
				var value=_this.val();
				if(!value&&require){
					callback();
					this.addClass("error");
					return;
				}else if(value&&regExp){
					if(!regExp.test(value)){
						callback1();
						this.addClass("error");
						return;
					}
				}
				this.removeClass("error");
				if(success){success()};
			}.bind(this));
			
		},
		valide:function(){//进行验证
			var param=this.data("param");
			var require=param.require;
			var regExp=param.regExp;
			var callback=param.callback;//非空回调
			var callback1=param.callback1;//正则回调
			
			var success=param.success;
			var value=this.val();
			if(!value&&require){
				callback();
				this.addClass("error");
				return false;
			}else if(value&&regExp){
				if(!regExp.test(value)){
					callback1();
					this.addClass("error");
					return false;
				}else{
					this.removeClass("error");
				}
			}
			if(success){success()};
			return true;
		},
		modal:function(param){
			if(param=="show"){
				$(this).data("parent",$(this).parent());
				var aim=$(this).data("parent");
				$(this).show();
				var $containner=$('<div class="overlay ol-dark" style="display:none;"></div>');
				$containner.append(this);
				$containner.removeClass("offstage");
				$containner.show();
				$("body").append($containner);
			}else if(param=="hide"){
				$(this).hide();
				var aim=$(this).data("parent");
				var $containner=$(this).parent(".overlay");
				$(this).data("parent").append($(this));
				
				$containner.addClass("offstage");
				$containner.remove();
			}
		},
		initForm:function(data){
			//按属性初始化
			this.find("[data-ref]").each(function(){
				var varible=$(this).attr("data-ref");
				if(varible&&data[varible]){
					var fmt=$(this).attr("data-fmt");
					if(fmt){
						var date=new Date(parseInt(data[varible], 10));
						var value=date.format(fmt);
						$(this).text(value);
						$(this).val(value);
					}else{
						$(this).text(data[varible]);
						$(this).val(data[varible]);
					}
				}
			});
			
			this.find("[name]").each(function(){
				var name=$(this).attr("name");
				var value;
				if(name.indexOf(".")!=-1){
					var result=data;
					var sub=name.split(".");
					for(var index=0;index<sub.length;index++){
						if(result){
							result=result[sub[index]];
						}else{
							result="";
							break;
						}
					}
					value=result;
				}else{
					value=data[name];
				}
				if(!value){
					value="";
				}
				
				if(this.tagName=="INPUT"){
					if(this.type=="radio"){
						if(this.value==value){
							$(this)[0].checked="checked";
						}else{
							$(this).removeAttr("checked");
						}
					}else if(this.type=="checkbox"){
						//TODO
					}else{	
						this.value=value;
					}
				}else if(this.tagName=="TEXTAREA"){
					$(this).html(value);
				}else if(this.tagName=="SELECT"){
					$(this).find("option").removeAttr("selected");
					if($(this).find("option[value='"+value+"']")[0]){
						$(this).find("option[value='"+value+"']")[0].selected="selected";
					}
					$(this).change();//触发change事件
				}
			});
		},
		smartSelect:function(){
			var _this=this;
			if(this[0].tagName!="SELECT"){
				return;
			}
			
			if(this.data("changeSelect")){
				return;
			}else{
				this.data("changeSelect","true");
			}
			
			if(this.attr("disabled")){
				this.hide();
				var  value=$(this).find("option:selected").text();
				this.parent().append($('<input type="text">').val(value).attr("disabled","disabled"));
				return;
			}
			
			var selectClass="active";
			if(this.hasClass("multi")){
				this[0].multiple="multiple";//设置为多选
				selectClass="selected";
			}
			
			var _this=this;
			var clazz=$(this).attr("class");
			
			var $activeOption=$(this).find("option:selected");
			var $contain=$("<div>").addClass(clazz);
			
			var $show=$("<div>").addClass("dropdown-title").text($activeOption.text());
			var $body=$("<ul>").addClass("dropdown-menu");
			$(this).find("option").each(function(index){
				if(index==0&&this.value==""){//第一个作为提示，不显示
					return;
				}
				var $li=$("<li>").text($(this).text());
				if($(this).attr("selected")){$li.addClass(selectClass)};
				$body.append($li);		
				$li.data("data-option",this);
			});
			$contain.append($show).append($body);
			$(this).hide().parent().append($contain);
			//双向绑定
			$show.on("click",function(){
				$contain.toggleClass('shown');
			});
			
			$body.find("li").on("click",function(){
				var index=$(this).index();
				if($(_this).find("option:eq(0)")[0].value==""){
					index=index+1;
				}
				if(!_this.hasClass("multi")){
					$(this).addClass(selectClass).siblings().removeClass(selectClass);
					$show.text($(this).text());
					$(_this).find("option:eq("+index+")").siblings().each(function(){
						this.selected=false;
					});
					$(_this).find("option:eq("+index+")")[0].selected="selected";
					$(_this).data("self_event","true");
					$(_this).change();
				}else{//多选
					var option=$(this).data("data-option");
					if(option.selected){
						//取消
						option.selected=false;
						$(this).removeClass(selectClass);
						$show.find("span[index='"+index+"']").remove();
						if($show.find("span").length==0){
							$show.text(_this.find("option:eq(0)").text());
						}
					}else{
						//增加
						if($show.find("span").length==0){
							_this.find("option:eq(0)")[0].selected=false;
							$show.empty();
						}
						option.selected="selected";
						$(this).addClass(selectClass);
						var $span=$("<span>").addClass("label").text($(this).text())
						.attr("index",index);
						$show.append($span);
					}
				}
				$contain.removeClass('shown');
			});
			
			//双向绑定
			$(this).on("change",function(){
				if($(this).data("self_event")){$(_this).data("self_event","");return;};
				
				if(!_this.hasClass("multi")){
					var $options=$(this).find("option:selected");
					var index=$options.index();
					//判断第一个
					var f_value=$(this).find("option:eq(0)").val();
					if(f_value==""){index--;}
					
					var li=$body.find("li:eq("+index+")");
					$(li).addClass(selectClass).siblings().removeClass(selectClass);
					$show.text($options.text());
				}else{//多选
					$show.empty();
					var $options=$(this).find("option:selected");
					var index=$(this).index();
					if($options.length==1&&$options[0].value==""){
						$show.text($options.text());
						index=index-1;
						$body.find("li").removeClass(selectClass);
					}else{
						$options.each(function(){
							var $span=$("<span>").addClass("label").text($(this).text())
							.attr("index",index);
							$show.append($span);
							$body.find("li:eq("+index+")").addClass(selectClass);
						});
					}
				}
			});
			
			//初始化默认值
			if(this.attr("default_")){
				var default_=this.attr("default_");
				this.find("option").removeAttr("selected");
				this.find("option[value='"+default_+"']").attr("selected","selected");
				this.change();
			}
			
		}
		
	});
	
	$.SF.setConfig("prompt",function(param){
		var $model=$("<div id='tempprompt'>").addClass("overlay ol-dark");
		var config={close:function(){
			$('#tempprompt').addClass('offstage');
			setTimeout(function(){
				$model.remove();
			},200);
		},save:function(input){
			param.callback(input);
			$model.remove();
		}};
		$.extend(param,config);
		$model.render("Prompt",param);
		$("body").append($model);
	});
	
	Date.prototype.format = function(formatStr)   
	{   
	    var str = formatStr;   
	    var Week = ['日','一','二','三','四','五','六'];  
	  
	    str=str.replace(/yyyy|YYYY/,this.getFullYear());   
	    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));   
	    str=str.replace(/MM/,this.getMonth()>8?(this.getMonth()+1).toString():'0' + (this.getMonth()+1));   
	    str=str.replace(/M/g,(this.getMonth()+1));   
	  
	    str=str.replace(/w|W/g,Week[this.getDay()]);   
	  
	    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());   
	    str=str.replace(/d|D/g,this.getDate());   
	  
	    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());   
	    str=str.replace(/h|H/g,this.getHours());   
	    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());   
	    str=str.replace(/m/g,this.getMinutes());   
	  
	    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());   
	    str=str.replace(/s|S/g,this.getSeconds());   
	  
	    return str;   
	}
	
})();

function openPage(menu){
	document.title=menu.title;//替换标题
	$(".right-container").open("./"+menu.url,function(){
		$(this).find('.datetimepicker').datetimepicker({
			lang:'ch',
			timepicker:false,
			format:'Y-m-d',
			formatDate:'Y-m-d',
			yearStart: '2016',
			minDate:'-2016/08/02', // yesterday is minimum date
		});
		
		$("header .t-rd").text(menu.title);
		var url="./"+menu.url;
		if(url.indexOf("?")==-1){
			url=url+"?ajaxTag_=1";
		}else{
			url=url+"&ajaxTag_=1";
		}
		var state={title:"测试",url:+menu.url,param:menu};
		window.history.pushState(state, document.title, url);
		
		$.post("welcome/dailyMessage.htmls",function(json){
			var $div=$("<div>").addClass("notification");
			if($("header .notification")[0]){$("header .notification").remove();}
			var $span_1=$("<span>");
			var $span_2=$("<span>").addClass("help").html("<a href='/static/help.html' target='_blank'>操作帮助</a>");
			$("header").append($div);
			$div.append($span_1);
			$div.append($span_2);
			var $i=$("<i>").addClass("icon-notifications_none");
			if(json&&json.length&&json.length>0){
				$i.append('<div class="dot"></div>')
			}
			$span_1.append($i);
			
			var $ul_message=$("<ul>").addClass("noti_list");
			$(json).each(function(){
				var $li=$("<li>").text(this.message);
				$li.data("data",this);
				$ul_message.append($li);
				$span_1.append($ul_message);
			});
		
		},"json");
		
	});
}


$(function(){
	//
	$("#main-container").on("click",".notification .noti_list li",function(){
		var notice=$(this).data("data");
		if(notice&&notice.url){
			window.location.href=notice.url;
		}
	});
	
	//退出
	$("body").on("click","#logout",function(){
		if(confirm("确定退出吗（将清空所有登录信息） ？")){
			window.location.href="adminLogin/loginOut.htmls";
		}
	});
	//检测登录状态
	var check=setInterval(function(){
		$.post("welcome/sss_check.htmls",function(json){
			if(json){
				if(json.type!=1){
					clearInterval(check);
					alert(json.message);
					window.location.href="adminLogin/loginOut.htmls";
				}
			}else{
				clearInterval(check);
				alert("登录已失效，请重新登录！");
				window.location.href="adminLogin/loginOut.htmls";
			}
		},"json")
	}, 10000)
	
	
	$.SF.setConfig("powerCheckUrl","welcome/checkPower.htmls");
	
	window.addEventListener('popstate', function(e){
		  if (history.state){
		    var menu=history.state.param;
		    var parent=menu.parent;
		    if(parent){
		    	if($("#menu"+parent.menuId).hasClass("active")){
		    		$("#menu"+menu.menuId+" a").click();
		    	}else{
		    		$("#menu"+parent.menuId+" a").click();
		    		setTimeout(function(){$("#menu"+menu.menuId+" a").click()}, 100);
		    	}
		    }
		  }
		}, false);
	
	
	
	$("#main-container .left-container").menu("welcome/menu-info.htmls",function(url,menu){
		
		openPage(menu);
		
	});
	$("body").on("click",".closable .close",function(){
		$(this).parents(".closable").hide();
	})
	$("body").on('click', 'button', function(event) {
		var _this=this;
		$(this).addClass('focus')
		setTimeout(function(){$(_this).removeClass('focus');},200);		
	})

	//	.on('mouseup', 'button', function(event) {var _this = $(this);});
	
//	$("body").on('click', '#maincontents,.overlay button,.btn', function(event) {
//		$(this).addClass('focus');
//	}).on('mouseup', 'button,.btn', function(event) {
//		var _this = $(this);
//		setTimeout(function(){_this.removeClass('focus');},200);
//	});
	
	
	//关闭modal
	$("body").on("click",".modal-close",function(){
		var parent=$(this).parents(".sf-modal");
		parent.modal("hide");
	});
	
	//关闭select
	$("body").on("click",function(e){
		var src=e.target;
		if($(src).hasClass(".dropdown-container")){//
			
		}else{
			src=$(src).parents(".dropdown-container")[0];
		}
		
		$(".dropdown-container").each(function(){
			if(this!=src){
				$(this).removeClass("shown");
			}
		});
	})
	
	//地区级连查询
	$("body").on("change","select[aimId]",function(){
		var _this=this;
		var url=$(this).attr("uri"); 
		var aimEl=$(this).attr("aimId");
		var value=this.value;
		var textKey=$(this).attr("text_key");
		var valueKey=$(this).attr("value_key");
		var $aim=eval(aimEl);
		if(value=="0"||value){
			$aim[0].options.length=0;
			$aim[0].options.add(new Option("请选择",""));
			$.post(url,{id:value},function(json){
				if(json){
					$(json).each(function(){
						$aim[0].options.add(new Option(this[textKey], this[valueKey]));
					});
					//修改smartSelect
					if($aim.data("changeSelect")){
						$aim.parent().find("div.dropdown-container").remove();
						$aim.data("changeSelect","");
						$aim.smartSelect();
					}
				}
			},"json")
		}
	});
	function ajaxAlertOpen(){
	}
	
	function ajaxAlertHide(){
	}
	
	
	$(document).ajaxStart(ajaxAlertOpen);
	$(document).ajaxStop(ajaxAlertHide);
	
	$("body").on("click","button[href]",function(){
		var href_url=$(this).attr("href");
		if(href_url){
			window.location.href=href_url;
		}
	});
	
	//点击头像
	$("body").on("click",".avatar",function(){
		$("#updatePriModal").modal("show");
	});
	
});




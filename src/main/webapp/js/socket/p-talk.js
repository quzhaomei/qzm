//格式化时间输出
Date.prototype.toString=function(){
	var date=new Date();
	date.setHours(0, 0, 0, 0);
	var year=this.getFullYear();
	var mon=this.getMonth()+1;
	var day=this.getDate();
	var hour=this.getHours();
	var min=this.getMinutes();
	if(min<10){
		min="0"+min;
	}
	var sec=this.getSeconds();
	if(this>date){
		return "今天 "+hour+":"+min+":"+sec;
	}else{
		return year+"-"+mon+"-"+day+" "+hour+":"+min;
	}
}

jQuery(".onlinechat").on("click",function(){
	var toId=jQuery(this).attr("toId");
	if(toId){
		if(jQuery("#talkContainner.offstage")[0]){
			jQuery("#talkContainner").removeClass("offstage");
			SF.startTalk(toId);
		}else{
			jQuery("#talkContainner").addClass("offstage");
		}
	}else{
		if(jQuery("#chatselect ul li[toId]:eq(0)")[0]){
			if(jQuery("#talkContainner.offstage")[0]){
				jQuery("#talkContainner").removeClass("offstage");
			}else{
				jQuery("#talkContainner").addClass("offstage");
			}
			jQuery("#chatselect ul li[toId]:eq(0)").click();
		}
	}
});
jQuery("body").on("click",".popup .tools button",function(){
	var message=jQuery(this).prev("input[name='words']").val();
	if(!message){alert("发送消息不能为空!");return;}
	SF.sendMsg(message);
	jQuery(this).prev("input[name='words']").val("");
});

var SF={
		sfUser:"",
		sfRemoteUrl:"",
		socketServer:"",
		toId:"",
		sfStatusok:false,
		sfStart:false,
		deafultImg:"/images/default_avatar.jpg",
		talkContainner_1:[
		                  '<div class="popup chat offstage" id="talkContainner">',
		                  '</div>'
		                  ],
		talkUserUlTemplate_1:[
						'<div id="chatselect"><ul>',
						'</ul></div>'
		                     ],                 
		talkUserLiTemplate_1:[
						'<li toId="#{id}"><img src="#{headImg}">',
						'<span class="digi">#{count}</span>',
						'</li>'
		                      ],    
		formTemplate_1:[
			'<div id="chatapp">',
				'<ul class="chatarea">',
				'</ul>',
				'<div class="tools">',
					'<div class="input">',
						'<input type="text" name="words">',
						'<button type="button">发送</button>',
						'</div>',
					'</div>',
				'</div>' ],
		sendMessageTempLate_1:[
			'<li class="alt">',
			'<span class="bubble b_right">#{message}</span>',
				'<span class="avatar"><img src="#{headImg}"></span>',
				'<div class="datetime">#{createTime}  #{name}</div>',
			'</li>'
				],
		getMessageTempLate_1:[
           			'<li>',
           			'<span class="avatar"><img src="#{headImg}"></span>',
           			'<span class="bubble b_left">#{message}</span>',
           				'<div class="datetime">#{name} #{createTime} </div>',
           			'</li>'
           				]
			}

/**
 * socket 公共js  
 */
//登录
SF.login=function(id,name,headImg,code,sign){
	var task=setInterval((function(){
		if(SF.sfStatusok){
			SF.sfUser=new SF.TalkingUser(id,name, headImg,code, sign, SF.socketServer);
			SF.sfUser.login();
			clearInterval(task);
			if(!jQuery("#talkContainner")[0]){
				jQuery("body").append(SF.talkContainner_1.join(""));
				jQuery("#talkContainner").on("click"," #chatselect li[toId]",function(){
						jQuery("#talkContainner #chatselect li").removeClass("active");
						jQuery(this).addClass("active");
						var delcount=jQuery(this).find(".digi").text();
						jQuery(this).find(".digi").remove();
						if(delcount){
							var tocount=jQuery(".onlinechat .digi").text();
							if(tocount){
								tocount=parseInt(tocount,"10");
								delcount=parseInt(delcount,"10");
								if(tocount>delcount){
									jQuery(".onlinechat .digi").text(tocount-delcount);
								}
							}
							
						}
						
						var toId=jQuery(this).attr("toId");//开始聊天
						SF.startTalk(toId);
				});
			}
		}
	}), 200);
}
//开始聊天
SF.startTalk=function(toId){
	var tasks
		if(SF.sfStart){
			SF.toId=toId;
			jQuery("#talkContainner #chatapp").remove();
			jQuery(".popup").append(SF.formTemplate_1.join(""));
			//加载聊天记录
			SF.sfUser.loadHis(toId);
			if(tasks){
				clearTimeout(stask);
			}
			}else{
				 tasks=setTimeout(function(){
					SF.startTalk(toId);
				}, 200);
			}
	
}
//发送信息
SF.sendMsg=function(message){
	SF.sfUser.speak(SF.toId,message);
}

SF.setCallBack=function(callBack){
	//设置消息处理
	var stask=setInterval(function(){
	if(SF.sfStart){
		SF.sfUser.callback=callBack;
		clearInterval(stask);
			}
	}, 500);
}




//聊天对象
SF.TalkingUser=(function($){
	$(function(){
		$("script").each(function(){
			if(this.src.indexOf("p-talk.js")!=-1){
				SF.sfRemoteUrl=this.src;
				for(var i=3;i>0;i--){
					if(SF.sfRemoteUrl.lastIndexOf("/")!=-1){
						SF.sfRemoteUrl=SF.sfRemoteUrl.substring(0, SF.sfRemoteUrl.lastIndexOf("/"));
					}
				}
			}
		});
		if(SF.sfRemoteUrl){
			//加载js
			$.getScript(SF.sfRemoteUrl+"/js/socket/socket.io-1.3.4.js",function(){
				if(SF.sfRemoteUrl){
					$.ajax({
						url: SF.sfRemoteUrl+"/talking/socketServer.htmls",
						type: "post",
						dataType: 'jsonp',
						jsonp: 'jsoncallback',
						success: function(json){  
							SF.socketServer=json.data;
							SF.sfStatusok=true;
				        },  
				        error: function(){ 
				        } 
					});
				}
			});
			
			
		}
	});
//聊天用户对象,在首页创建
function talkingUser(id,name,headImg,code,sign,server){
	this.id=id;//id
	this.name=name;//姓名
	if(headImg){
		this.headImg=headImg;
	}else{
		this.headImg=SF.sfRemoteUrl+SF.deafultImg;
	}
	this.code=code;
	this.sign=sign;
	this.socket=null;//链接的socket
	this.server=server;
	var _this=this;
	this.callback=null;//默认的回调
	this.login=function(){//登录聊天系统方法,并初始化事件
		
		socket = io.connect(server,{
			'reconnection delay' : 5000,
			'force new connection' : true
		});
		
		socket.on("connect success",function(data){
			data=JSON.parse(data);
			if(data.status=="ok"){//链接成功，推送登录信息
				setTimeout(function(){
					socket.emit("login", {//向服务器注册用户
						'id':_this.id,
						'name':_this.name,
						'headImg':_this.headImg,
						'code':code,
						'sign':sign
					});
					
				}, 500);
			}
		});
		
		//登录成功，初始化未接受信息条数
		socket.on("unaccpet msg",function(data){
			data=JSON.parse(data);
			SF.sfStart=true;
			if(data&&data.length&&data.length>0){
				var uncheck=0;
				$(data).each(function(){
					if(this.unCheckCount>0){
						uncheck++;
					}
				});
				if(uncheck>0){
					$(".onlinechat .digi").text(uncheck);
				}
				if(!$("#talkContainner").hasClass("service")&&!$(".onlinechat").attr("toid")){
					$("#talkContainner").addClass("service");
					$("#talkContainner").prepend(SF.talkUserUlTemplate_1.join(""))
				}
				$(data).each(function(){
					var $html=$(SF.talkUserLiTemplate_1.join("").replace("#{id}",this.fromId)
							.replace("#{headImg}",this.fromHeadImg).replace("#{count}",this.unCheckCount));
					if(this.unCheckCount==0){
						$html.find(".digi").remove();
					}
					$("#chatselect ul").prepend($html);
				});
			}else{
				$(".onlinechat .digi").remove();
			}
			//处理未接受信息
			//TODO
		});
		
		//接受其他人信息
		socket.on("per2perTalking",function(data){
			data=JSON.parse(data);
			if(_this.callback){
				_this.callback(data);
			}else{
				if(!data.fromHeadImg){//设置默认头像
					data.fromHeadImg=SF.sfRemoteUrl+SF.deafultImg;
				}
				if(data.fromId==_this.id){//如果是本人发出的，用于信息回显
					$(".popup #chatapp .chatarea").append(SF.sendMessageTempLate_1.join("")
							.replace("#{headImg}",data.fromHeadImg).replace("#{message}",data.message)
							.replace("#{createTime}",new Date(parseInt(data.createDate,10)))
							.replace("#{name}",data.fromName)
							);
					
					//滚动到底部
					$(".popup #chatapp .chatarea").scrollTop($(".popup #chatapp .chatarea")[0].scrollHeight);
				}else if(data.toId=_this.id){//如果是接受信息
					$(".popup #chatapp .chatarea").append(SF.getMessageTempLate_1.join("")
							.replace("#{headImg}",data.fromHeadImg).replace("#{message}",data.message)
							.replace("#{createTime}",new Date(parseInt(data.createDate,10)))
							.replace("#{name}",data.fromName)
							);
					$(".popup #chatapp .chatarea").scrollTop($(".popup #chatapp .chatarea")[0].scrollHeight);
				}else{//接受的是其他人发的消息
					if(!$("#talkContainner").hasClass("service")){
						$("#talkContainner").addClass("service");
						$("#talkContainner").prepend(SF.talkUserUlTemplate_1.join(""))
					}
					if($("#chatselect li[toId='"+data.toId+"']")[0]){//如果存在
						var count=$("#chatselect li[toId='"+data.toId+"'] .digi").text();
						if(count){
							$("#chatselect li[toId='"+data.toId+"'] .digi").text(parseInt("count",10)+1);//数量＋1
						}else{
							$("#chatselect li[toId='"+data.toId+"']").append('<span class="digi">1</span>');//数量初始化为1
						}
						
					}else{//如果不存在
						$("#chatselect ul").prepend(
								SF.talkUserLiTemplate_1.join("").replace("#{id}",data.toId)
								.replace("#{headImg}",data.fromHeadImg).replace("#{count}",1)
						);
					}
					//全局消息显示
					var count=$(".onlinechat .digi").text();
					if(count){
						count=parseInt("count",10)+1;
						$(".onlinechat .digi").text(count);
					}else{
						$(".onlinechat").append('<span class="digi">1</span>');
					}
				}
			}
		});
		socket.on("load history",function(data){
			data=JSON.parse(data);
			if(data){
				$(data).each(function(){
					if(this.fromId==_this.id&&this.toId==SF.toId){//如果是本人发出的历史消息
						$(".popup #chatapp .chatarea").append(SF.sendMessageTempLate_1.join("")
								.replace("#{headImg}",this.fromHeadImg).replace("#{message}",this.message)
								.replace("#{createTime}",new Date(parseInt(this.createDate,10)))
								.replace("#{name}",this.fromName)
						);
					}else if(this.fromId==SF.toId&&this.toId==_this.id){
						$(".popup #chatapp .chatarea").append(SF.getMessageTempLate_1.join("")
								.replace("#{headImg}",this.fromHeadImg).replace("#{message}",this.message)
								.replace("#{createTime}",new Date(parseInt(this.createDate,10)))
								.replace("#{name}",this.fromName)
						);
					}
					$(".popup #chatapp .chatarea").scrollTop($(".popup #chatapp .chatarea")[0].scrollHeight);
				});
			}
		});
	};
	this.speak=function(toId,message){
		var data={};
		data.toId=toId;
		data.message=message;
		//说话
		socket.emit('say to user',data);
	};
	this.loadHis=function(toId){
		var data={};
		data.toId=toId;
		//说话
		socket.emit('load history',data);
	};
}
return talkingUser;
})(window.jQuery);
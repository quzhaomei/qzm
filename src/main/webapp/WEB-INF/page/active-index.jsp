<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath%>">
<title>active</title>
<style type="text/css">
table{width:80%;}

</style>
</head>
<body>
<h1>表格字段</h1>
<table>
<c:forEach items="${cols }" var="col">
<tr>
<td>${col.name }</td>
<td>${col.property }</td>
<td>${col.type }</td>
<td>${col.require }</td>
<td>${col.resource }</td>
</tr>
</c:forEach>
</table>
<hr/>
<h2>活动数据表格自动生成</h2>
<div id="formContainer">

</div>
<hr/>
<h2>活动数据显示</h2>
<button class="clearDats" type="button">清空数据</button>
<div id="example">
</div>
<a href="active/download.htmls" id="download" >下载数据</a>
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/react/browser.min.js"></script>

<script type="text/javascript" src="js/react/react.min.js"></script>
<!---->
<script type="text/javascript" src="js/react/react-with-addons.min.js"></script>

<script type="text/javascript" src="js/react/react-dom.min.js"></script>


<script type="text/javascript">

</script>

<script type="text/babel">
var telReg="^1\\d{10}$";
var emailReg="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
var Input=React.createClass({
		handelChange:function(){
			var element=React.findDOMNode(this.refs.element);
			if(element.sfValid){
				var result=element.sfValid();
				if(result.status==0){
					alert(result.message);
					
				}
			}
		},
		componentDidMount:function(){
			var require=this.props.require;
			var type=this.props.type;
			var element=React.findDOMNode(this.refs.element);
			var resource=this.props.resource;//正则
			var cname=this.props.cname;
			 element.sfValid=function(){

			 	var status=true;
			 	var message="";
				var value=element.value;
			 	if(require==1&&!value){
			 		status=false;
			 		message=cname+"不能为空";
			 	}else if(resource&&require==1){
			 		var reg=new RegExp(resource) ;
			 		if(!reg.test(value)){
			 			status=false;
			 			message="请输入正确格式的"+cname;
			 		}
			 	}
			 	return {"status":status,"message":message,"value":value};
			 };
		},
		render:function(){
			return <div><span>{this.props.cname}</span> 
				<input className="formDate" ref="element" onBlur={this.handelChange} name={this.props.property}/>
			</div>;
		}
	});

var Radio=React.createClass({
		componentDidMount:function(){
			var require=this.props.require;
			var type=this.props.type;
			var element=React.findDOMNode(this.refs.element);
			var resource=this.props.resource;//正则
			var property=this.props.property;
			var cname=this.props.cname;
			if(element){
			 element.sfValid=function(){
			 	var value=$("input.formDate[type='radio'][name='"+property+"']:checked").val();
			 	var status=true;
			 	var message="";
			 	if(require==1&&!value){
			 		status=false;
			 		message="请选择"+cname;
			 	}
			 	return {"status":status,"message":message,"value":value};
			 };
			 }
		},
		render:function(){
			var _this=this;
			var radioArr=this.props.resource.split(",").map(function(temp,i){				
				return <span>
					<span>{temp}</span> 
					<input className="formDate"  ref={i==0?"element":""} type="radio" value={temp}   name={_this.props.property}/>
					</span>;
			});
		
			return <div>{this.props.cname}: {radioArr}</div>;
		}
	});

var CheckBox=React.createClass({
		componentDidMount:function(){
			var require=this.props.require;
			var type=this.props.type;
			var element=React.findDOMNode(this.refs.element);
			var resource=this.props.resource;//正则
			var property=this.props.property;
			var cname=this.props.cname;
			if(element){
			 element.sfValid=function(){
			 	var $value=$("input.formDate[type='checkBox'][name='"+property+"']:checked");
			 	var arr=[];
			 	
			 	var status=true;
			 	var message="";
			 	if(require==1&&!$value[0]){
			 		status=false;
			 		message="请选择"+cname;
			 	}
			 	$value.each(function(){
			 		arr.push(this.value);
			 	});
			 	return {"status":status,"message":message,"value":arr.join(",")};
			 };
			 }
		},
		render:function(){
			var _this=this;
			var radioArr=this.props.resource.split(",").map(function(temp,i){				
				return <span>
					<span>{temp}</span> 
					<input className="formDate"  ref={i==0?"element":""} type="checkBox" value={temp}   name={_this.props.property}/>
					</span>;
			});
		
			return <div>{this.props.cname}: {radioArr}</div>;
		}
	});

var Select=React.createClass({
	getInitialState: function() {
   		return {data:""};
  	},
	componentDidMount:function(){
		var _this=this;
		var resource=this.props.resource;//初始化选项
		$.post(resource,function(json){
			if(json){
				_this.setState({data:json})
			}
		},"json");

		var require=this.props.require;
		var type=this.props.type;
		var element=React.findDOMNode(this.refs.element);
		var property=this.props.property;
		var cname=this.props.cname;
			if(element){
			 element.sfValid=function(){
			 	var value=$("select.formDate[name='"+property+"']").val();
			 	
			 	var status=true;
			 	var message="";
			 	if(require==1&&!value){
			 		status=false;
			 		message="请选择"+cname;
			 	}
			 	return {"status":status,"message":message,"value":value};
			 };
			 }
		},
		render:function(){
			var _this=this;
			var options=null;
			if(this.state.data.map){
				options=this.state.data.map(function(temp){
				return <option value={temp.key}>{temp.value}</option>
				});
			}
			return <div>{this.props.cname}：
					<select ref="element" className="formDate" name={_this.props.property}>
						<option value="">-请选择-</option>
						{options}
					</select>				
				</div>;
		}
	});

var Select=React.createClass({
	getInitialState: function() {
   		return {data:""};
  	},
	componentDidMount:function(){
		var _this=this;
		var resource=this.props.resource;//初始化选项
		$.post(resource,function(json){
			if(json){
				_this.setState({data:json})
			}
		},"json");

		var require=this.props.require;
		var type=this.props.type;
		var element=React.findDOMNode(this.refs.element);
		var property=this.props.property;
		var cname=this.props.cname;
			if(element){
			 element.sfValid=function(){
			 	var value=$("select.formDate[name='"+property+"']").val();
			 	
			 	var status=true;
			 	var message="";
			 	if(require==1&&!value){
			 		status=false;
			 		message="请选择"+cname;
			 	}
			 	return {"status":status,"message":message,"value":value};
			 };
			 }
		},
		render:function(){
			var _this=this;
			var options=null;
			if(this.state.data.map){
				options=this.state.data.map(function(temp){
				return <option value={temp.key}>{temp.value}</option>
				});
			}
			return <div>{this.props.cname}：
					<select ref="element" className="formDate" name={_this.props.property}>
						<option value="">-请选择-</option>
						{options}
					</select>				
				</div>;
		}
	});

var Pselect=React.createClass({
	getInitialState: function() {
   		return {data:""};
  	},
	componentDidMount:function(){
		var _this=this;
		var resource=this.props.resource[0];//初始化选项
		$.post(resource,function(json){
			if(json){
				_this.setState({data:json})
			}
		},"json");

		var require=this.props.require;
		var type=this.props.type;
		var element=React.findDOMNode(this.refs["element0"]);//选第一个控件
		var property=this.props.property;
		var cname=this.props.cname;
	
			if(element){
			 element.sfValid=function(){
			 	var value=React.findDOMNode(_this.refs["element"+(_this.props.resource.length-1)]).value;

			 	var status=true;
			 	var message="";
			 	if(require==1&&!value){
			 		status=false;
			 		message="请选择"+cname;
			 	}
			 	return {"status":status,"message":message,"value":value};
			 };
			 }
		},
		handelChange:function(e){
			var _this=this;
			var elem=e.target;
			var index=$(elem).attr("data-index");
			index=parseInt(index,"10");
			for(var i=index+1;i<this.props.resource.length;i++){
				$("select.formDate[name='"+_this.props.property+(i)+"']")[0].options.length=1;
			}
			if(index<this.props.resource.length-1){
				$.post(_this.props.resource[index+1],{key:$(elem).val()},function(json){
					var $aimSelect=$("select.formDate[name='"+_this.props.property+(index+1)+"']");
					$aimSelect[0].options.length=1;
					$(json).each(function(){
						$aimSelect[0].options.add(new Option(this.value,this.key));
					});
					
				},"json");
			}
		},
		render:function(){
			var _this=this;
			var options=null;
			var pselect=null;
			pselect=this.props.resource.map(function(temp,index){
				var options=null;
				if(index==0&&_this.state.data.map){
					options=_this.state.data.map(function(temp){
					return <option value={temp.key}>{temp.value}</option>
					});
				}
				return <select className="formDate" ref={"element"+index} data-index={index} onChange={_this.handelChange} name={index==0?_this.props.property:(_this.props.property+index)}>
				<option value="">-请选择-</option>
				{options}
				</select>;
			});
			
			return <div>{this.props.cname}：
					{pselect}			
				</div>;
		}
	});

var Element = React.createClass({
	getInitialState: function() {
	var datas={};
  		datas["require"]=this.props.require;
  		datas["resource"]=this.props.resource;
  		datas["type"]=this.props.type;
   		return {data:datas};
  	},
  	componentDidMount:function(){

	  	},
	render:function(){
			
		if(this.state.data.type==0){//普通文本输入框
			 return <Input cname={this.props.name}  property={this.props.property} require={this.props.require} resource={this.props.resource}/>;			
		}else if(this.state.data.type==1){//电话号码
			return <Input cname={this.props.name} property={this.props.property} require={this.props.require} resource={telReg}/>;	
		}else if(this.state.data.type==2){//邮箱
			return <Input cname={this.props.name} property={this.props.property} require={this.props.require} resource={emailReg}/>;	
		}else if(this.state.data.type==3){//性别

		}else if(this.state.data.type==4){//单选框
			return <Radio cname={this.props.name} property={this.props.property} require={this.props.require} resource={this.props.resource}/>
		}else if(this.state.data.type==5){//多选框
			return <CheckBox cname={this.props.name} property={this.props.property} require={this.props.require} resource={this.props.resource}/>
		}else if(this.state.data.type==6){//下拉框
			return <Select cname={this.props.name} property={this.props.property} require={this.props.require} resource={this.props.resource}/>
		}else if(this.state.data.type==7){//级联下拉框
			return <Pselect cname={this.props.name} property={this.props.property} require={this.props.require} resource={this.props.resource.split(",")}/>
		}

		return null;		
	}

	});

var Form=React.createClass({
	handleSubmit:function(e){
		e.preventDefault();

		var target=e.target;
		var action=target.action;
		var data={};
		var $element=$(target).find("[name]");	
		for(var index=0;index<$element.length;index++){
			var temp=$element[index];
			var name=temp.name;
				if(temp.sfValid){
					var result=temp.sfValid();
					if(result.status==0){
						alert(result.message);
						
						$(temp).focus();
						return;
					}
				data[name]=result.value;
				}
			
		}
			
		$.post(action,data,function(json){
			alert(json);
		},"json");
		
		
	},
	render:function(){
		return <form action={this.props.action} onSubmit={this.handleSubmit} id="activeForm">{this.props.children}
			<button type="submit"> 提交 </button>
		</form>
	}
});

  ReactDOM.render(
		 <Form action="active/save.htmls?activeId=${activeId}" >
	<c:forEach items="${cols }" var="col">
	<Element name="${col.name}"  property="${col.property}" type="${col.type}" require="${col.require}" resource="${col.resource}"/>		
	</c:forEach>
		</Form>,
        document.getElementById('formContainer')
      );






	var Index=React.createClass({
			render:function(){
				return <td>{this.props.name}</td>
			}
			
		});
	var TbleTd=React.createClass({
			render:function(){
				return (this.state&&this.state.data)?<td>{this.state.data[this.props.prop]}</td>:<td>{this.props.name}</td>
			}
			
		});
	var Operator=React.createClass({
			render:function(){
			return <td>{this.props.name}</td>
			}
			
		});
	var BookTable=React.createClass({
		getInitialState : function(){return {data:[]};},
		componentDidMount : function(){
			this.loadDateFromServer();
		//	setInterval(this.loadDateFromServer,3000);		
		},
		loadDateFromServer:function(){
			$.ajax({
			url:this.props.url,
			type:"POST",
			cache:false,
			dataType:"json",
			success:function(json){
				this.setState({data:json});
			}.bind(this)	
			});
		},
		render:function(){
			var _this=this;
			var thead=<tr>{_this.props.index?<td>序列</td>:""}{
			React.Children.map(this.props.children,function(child){
				return child;
			})}{_this.props.operator?<td>操作</td>:""}</tr>;
			var index=0;
			var tbody=this.state.data.map(function(temp){
					var primary=temp[_this.props.primary];
					index++;
					var operator="";
					if(_this.props.operator){
					operator=<td>{_this.props.operator.map(function(temp){
								if(temp=="edit"){
										return <Edit primary={primary}/>;
									}else if(temp=="status"){
										return <Status primary={primary}/>;
									}
						})}</td>;
					}
					var datas= <tr>{_this.props.index?<td>{index}</td>:""}
								{React.Children.map(_this.props.children,function(child){return <td>{temp[child.props.prop]}</td>;})}
								{operator}
							</tr>;
					
					return datas;
				});
			return <table>{thead}{tbody}</table>;
			}
		});




	var Edit=React.createClass({
		getInitialState : function(){return {data:["ss"]};},
		componentDidMount : function(){
					
		},
		doClick:function(){
			alert(this.props.primary);
		},
		render:function(){return <button type="button" onClick={this.doClick}>修改</button>}
	});

	var Status=React.createClass({
		render:function(){return <button type="button">状态</button>}
	});
	
var doEdit=function(pKey){alert(pKey)};
var doStatus=function(pKey){};


   ReactDOM.render(
		 <BookTable url="active/getDatas.htmls?activeId=${activeId}" index="true" operator={["edit","status"]} primary="id" >

		<c:forEach items="${cols }" var="col">
			<TbleTd name="${col.name }" prop="${col.property}" type="${col.type}" require="${col.require}" resource="${col.resource}"/>
		</c:forEach>
		
		</BookTable> 
		,
        document.getElementById('example')
      );

    </script>
</body>
</html>

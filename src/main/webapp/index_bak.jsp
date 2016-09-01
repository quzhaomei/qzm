<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>react.js</title>
</head>
<body>
<h2>Hello World!</h2>
<div id="example"></div>
<div id="subform"></div>
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/react/react.min.js"></script>
<!---->
<script type="text/javascript" src="js/react/react-dom.min.js"></script>

<script src="js/react/browser.min.js"></script>
<script type="text/babel">
	var BookSaveForm=React.createClass({
		handelSubmit:function(e){
			e.preventDefault();
		var name=this.refs.name.value.trim();
		var price=this.refs.price.value.trim();
		if(!name){alert("请输入书本名字!");return};
		if(!price){alert("请输入书本价格!");return};
		if(!/^\d+$/.test(price)){alert("书本价格为整数!");return};
		this.props.onBookSubmit({name:name,price:price});
		return false;
		},
		render:function(){
			return <form method="post" className="myForm" onSubmit={this.handelSubmit}>
					<input ref="name" placeholder="请输入书本名字"/>
					<br/><input ref="price" placeholder="请输入价格"/><br/>
						 <input type="submit" value="保存"  />
				</form>;
		}
	});
	
	 ReactDOM.render(
		 <BookSaveForm onBookSubmit={function(json){
				$.post("auto/saveBook.htmls",json,function(json){
					if(json.status==1){
						alert(json.message);
					}
				},"json")
		}} /> 
		,
        $("#subform")[0]
      );

	var TableBar=React.createClass({
			render:function(){
				var tdArr=[];
			if(this.props.order){
				tdArr.push(<td>{this.props.order}</td>);
			}
			for(var i in this.props.bar){
				tdArr.push(<td>{this.props.bar[i]}</td>);
			}
			if(this.props.operator){
				tdArr.push(<td>操作</td>);
			}
			return <tr>{tdArr}</tr>
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
			var index=1;
			var datas=this.state.data.map(function(temp){
				var tdTemp=[];
				if(_this.props.order){
					tdTemp.push(<td>{index++}</td>);
				}
				for(var i in _this.props.bar){
					tdTemp.push(<td>{temp[i]}</td>);
				}
				if(_this.props.operator.btn){
					var btns=
					_this.props.operator.btn.map(function(btn){
							var pKey=null;
							if(_this.props.operator.pKey){
							 pKey=temp[_this.props.operator.pKey];
							}else{
							var count=0;
							for(var i in temp){
									if(count==0)pKey=temp[i];
									count++;
								}
							}
							if(btn.name=="EditBtn"){
								return <EditBtn pkey={pKey} handle={btn.handle} />;
							}else if(btn.name=="StatusBtn"){
								return <StatusBtn pkey={pKey} handle={btn.handle}/>;
							}
					});
					tdTemp.push(<td>{btns}</td>);
				}
				return <tr>{tdTemp}</tr>;
			});
			return <table>
				<TableBar bar={this.props.bar} order={this.props.order} operator={this.props.operator}/>
					{datas}
					</table>;
			}
		});
	var EditBtn=React.createClass({
		getInitialState : function(){return {data:["ss"]};},
		componentDidMount : function(){
					
		},
		doClick:function(){
			this.props.handle(this.props.pkey);
		},
		render:function(){return <button type="button" onClick={this.doClick}>修改</button>}
	});
	var StatusBtn=React.createClass({
		render:function(){return <button type="button">状态</button>}
	});
	
var doEdit=function(pKey){alert(pKey)};
var doStatus=function(pKey){};
   ReactDOM.render(
	
		 <BookTable url="auto/getQuestion.htmls" 
			bar={{"name":"书本名字","price":"价格"}}  order="序列" 
			operator={ {
				pKey:"name",
				btn:[
						{name:"EditBtn",handle:doEdit},
						{name:"StatusBtn",handle:doStatus}
					]
				}  }/> 
		,
        document.getElementById('example')
      );
   
    </script>
</body>
</html>

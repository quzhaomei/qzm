<html>
<body>
<h2>Hello World!</h2>
<button id="btn">sd</button>
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
$("#btn").on("click",function(){
	alert("a");
	return;
})

$("#btn").unbind("click").on("click",function(){
	alert("b");
	return;
})
</script>
</body>
</html>

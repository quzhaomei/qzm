<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<!doctype html>
<html>
<head>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<title>test</title>
</head>
<body>
<h2>Hello World!</h2>
<form action="test.htmls" method="post" id="myform">
<input name="code" value="" placeholder="请输入code"/>
<input name="name" value="" placeholder="请输入name"/>
<button type="submit">提交</button>
</form>
<table>
<tr><td>名字</td><td>code</td></tr>
<c:forEach items="${datas }" var="temp">
<tr><td>${temp.name }</td><td>${temp.code }</td></tr>
</c:forEach>
</table>
<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
$("#myform").on("submit",function(){
	
});
</script>
<script type="text/javascript"> 

</script> 
</body>
</html>

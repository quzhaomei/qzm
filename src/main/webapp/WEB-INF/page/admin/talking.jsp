<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<style>
* {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box; }
.popup.chat {
  position: fixed;
  top: 93px;
  width: 400px;
  height: 500px;
  left: 50%;
  margin-left: -200px;
  z-index: 1000;
  background-color: white;
  -webkit-box-shadow: 0px -1px 5px 0px rgba(51, 49, 47, 0.2);
  -moz-box-shadow: 0px -1px 5px 0px rgba(51, 49, 47, 0.2);
  box-shadow: 0px -1px 5px 0px rgba(51, 49, 47, 0.2);
  border-radius: 3px; }

#chatapp {
  position: absolute;
  top: 0px;
  bottom: 0px;
  left: 0px;
  right: 0px; }
  #chatapp .chatarea {
    list-style-type: none;
    list-style-position: inside;
    position: absolute;
    top: 0px;
    bottom: 50px;
    left: 0px;
    right: 0px;
    overflow-y: auto;
    -webkit-overflow-scrolling: touch;
    padding: 10px 0px; }
    #chatapp .chatarea li {
      position: relative;
      min-height: 40px;
      padding: 10px;
      width: 100%;
      font-size: 0px; }
      #chatapp .chatarea li .avatar {
        display: inline-block;
        z-index: 3;
        position: relative;
        padding: 4px;
        background-color: white;
        width: 58px;
        height: 58px;
        vertical-align: bottom;
        border-radius: 50%; }
        #chatapp .chatarea li .avatar img {
          width: 50px;
          height: 50px;
          height: auto;
          border-radius: 50%;
          border: 2px solid #FAFAFA; }
      #chatapp .chatarea li.alt {
        text-align: right; }
        #chatapp .chatarea li.alt .bubble {
          text-align: left; }
      #chatapp .chatarea li .datetime {
        font-size: 13px;
        padding: 0px 60px;
        color: #6A6A6A; }
    #chatapp .chatarea .bubble {
      display: inline-block;
      vertical-align: bottom;
      color: white;
      font-size: 16px;
      line-height: 22px;
      padding: 10px;
      max-width: 75%;
      border-radius: 5px;
      position: relative;
      margin-bottom: 4px;
      background-size: cover;
      z-index: 2; }
      #chatapp .chatarea .bubble img {
        width: 100%;
        height: auto;
        display: block; }
      #chatapp .chatarea .bubble.b_left {
        background-color: #73d022; }
        #chatapp .chatarea .bubble.b_left::before {
          display: inline-block;
          content: ' ';
          background-color: #73d022;
          width: 35px;
          height: 25px;
          position: absolute;
          left: -25px;
          bottom: 0px;
          z-index: 1; }
      #chatapp .chatarea .bubble.b_right {
        background-color: #ee7700; }
        #chatapp .chatarea .bubble.b_right::after {
          display: inline-block;
          content: ' ';
          background-color: #ee7700;
          width: 35px;
          height: 25px;
          position: absolute;
          right: -25px;
          bottom: 0px;
          z-index: 1; }
  #chatapp .tools {
    position: absolute;
    bottom: 0px;
    border-bottom-right-radius: 3px;
    border-bottom-left-radius: 3px;
    left: 0px;
    right: 0px;
    background-color: #FAFAFA;
    z-index: 10; }
    #chatapp .tools .input {
      padding: 15px; }
    #chatapp .tools input {
      display: inline-block !important;
      width: 78%;
      border: 1px solid #EEEBEC;
      font-size: 16px;
      line-height: 16px;
      background-color: white !important;
      margin: 0px;
      margin-right: 1.5%;
      height: 40px;
      padding: 5px;
      -webkit-box-shadow: inset 0px 1px 1px 0px rgba(51, 49, 47, 0.1);
      -moz-box-shadow: inset 0px 1px 1px 0px rgba(51, 49, 47, 0.1);
      box-shadow: inset 0px 1px 1px 0px rgba(51, 49, 47, 0.1); }
    #chatapp .tools button {
      width: 18%;
      font-size: 16px;
      font-weight: normal;
      padding: 2px;
      height: 32px;
      margin-top: 5px;
      margin-left: 5px;
      float: right;
      background-color: #EE7700;
      border: 0px;
      border-radius: 3px;
      color: white; }
    #chatapp .tools i {
      color: white;
      display: inline-block;
      width: 10%;
      font-size: 36px !important;
      text-align: right;
      vertical-align: middle; }


.popup.service #chatselect {
  position: absolute;
  left: 0px;
  top: 0px;
  bottom: 0px;
  width: 70px;
  background-color: #FAFAFA;
  overflow-y: auto;
  -webkit-overflow-scrolling: touch; }
  .popup.service #chatselect ul {
    padding: 0px;
    margin: 0px; }
  .popup.service #chatselect ul li {
    list-style: none;
    list-style-position: outside;
    text-align: center;
    padding: 10px;
    position: relative;
    cursor: pointer; }
    .popup.service #chatselect ul li.active {
      background-color: #EEEEEE; }
    .popup.service #chatselect ul li .digi {
      position: absolute;
      top: 3px;
      right: 3px;
      display: inline-block;
      min-width: 20px;
      height: 20px;
      background-color: #64BC00;
      color: white;
      font-size: 12px;
      text-align: center;
      padding: 3px;
      border-radius: 10px; }
  .popup.service #chatselect img {
    width: 100%;
    height: 100%;
    border-radius: 50%; }
.popup.service #chatapp {
  left: 70px; }
  .offstage{
  display:none;}
      </style>
</head>
<body>
<div class="onlinechat" toid="itooth2016customer">
	<i class="fa-comments-o fa"></i>
	<span>在线咨询</span>
	<span class="digi"></span>
</div>
<!--  
<div class="popup chat service offstage" id="talkContainner">
  <div id="chatselect">
    <ul>
      <li class="active">
        <img src="images/default_avatar.jpg">
        
      </li>
      
      <li><img src="images/default_avatar.jpg">
        <span class="digi">
          10
        </span>
        </li>
      <li><img src="images/default_avatar.jpg"></li>
    </ul>
  </div>
	<div id="chatapp">
		<ul class="chatarea">
			<li>
				<span class="avatar">
					<img src="images/default_avatar.jpg">
				</span>
					
				<span class="bubble b_left">
					请问有没有可以。<br>
					You can manage your dropdown position with the z-index property to bring it to front. Remember that the z-index property works with elements positioned relatively or absolutely. Also, is not correct semantically speaking to use a table to achieve layout results.
				</span>
				<div class="datetime">2016-01-03 23:30</div>
			</li>

			<li class="alt">
				
					
				<span class="bubble b_right">
					<img src="images/image.jpg" alt="">
				</span>
				<span class="avatar">
					<img src="images/logosquare.png">
				</span>
				<div class="datetime">今天 9:32</div>
			</li>


			<li>
				<span class="avatar">
					<img src="images/default_avatar.jpg">
				</span>
					
				<span class="bubble b_left">
					最近有没有优惠呢？
				</span>
				<div class="datetime">今天 10:32</div>

			</li>

			<li class="alt">
				
					
				<span class="bubble b_right">
					15号－16号我们有一场活动，会有比较有力的折扣呢
				</span>
				<span class="avatar">
					<img src="images/logosquare.png">
				</span>
				<div class="datetime">今天 9:32</div>
				
			</li>

			<li>
				<span class="avatar">
					<img src="images/default_avatar.jpg">
				</span>
					
				<span class="bubble b_left">
					能不能再多给些同类产品的建议？
				</span>
				<div class="datetime">今天 9:33</div>

			</li>

			<li class="alt">
				
					
				<span class="bubble b_right">
					您可以到我们门店进一步挑选。
				</span>
				<span class="avatar">
					<img src="images/logosquare.png">
				</span>
				<div class="datetime">今天 9:33</div>
				
			</li>

		</ul>
		<div class="tools">
			<div class="input">
				<form>
					<input type="text" name="words">
					
					
					<button>
						发送
					</button>
					
				</form>
				

			</div>
			
		</div>
	</div>
</div>
	-->
</body>
<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../js/socket/p-talk.js"></script>
<script type="text/javascript">
	SF.login("${id}","${name}","","${code}","${sign}");
</script>
</html>
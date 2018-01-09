<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试WebSocket</title>

<script src= "<%=path %>/js/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/sockjs-client/1.1.4/sockjs.min.js"></script>

<script type="text/javascript">
	var path = '';
	var speakMessage = '';
	$(function(){
		path = $('#path').val();
	});
	var websocket = "";
	var wsUrl = "ws://localhost:8080/barrageTools/websocket";
	function closeWebSocket(){
		websocket.close();
	}
	function look(){
		alert('WebSocket' in window);
	}
	
	function showBarrage(){
		var roomId = $('#roomId').val();
		$.ajax({
			url : path + '/roomMessage',
			type : 'post',
			data : {roomId : roomId},
			dataType : 'json',
			async : false,
			success : function(data){
				$('#roomName').val(data.room_name);
				$('#ownerName').val(data.owner_name);
				roomId = data.room_id;
			}
		});
		websocket = new WebSocket(wsUrl);
		websocket.onopen = function(){
			console.log("open");
			websocket.send(roomId);
		}
		websocket.onmessage = function(evt){
			if($('#message *').length >= 20){
				$('#message').children().first().remove();
			}
			//console.log(evt.data);
			//$('#message').append('<p>'+evt.data+'</p>');
			//$('#message').scrollTop($('#message')[0].scrollHeight);
			//console.log($('#message *').length);
			var msg = JSON.parse(evt.data);
			if(msg.info){
				var serverInfo = msg.info;
				for(var i = 0; i < serverInfo.length; i++){
					var showInfo = '<p style="color:red;">'+serverInfo[i]+'</p>';
					$('#message').append(showInfo);
				}
			}else{
				if(msg.type == 'chatmsg'){
					var nn = '<span style="color:blue;">'+msg.nn+' : </span>';
					var txt = '<span style="color:black;">'+msg.txt+'</span>';
					$('#message').append('<p>'+nn+txt+'</p>');
					if(speakMessage == ''){
						speakMessage = msg.txt;
					}
				}
			}
			$('#message').scrollTop($('#message')[0].scrollHeight);
		}
		websocket.onclose = function(evt){
			console.log("WebSocket closed");
		}
		websocket.onerror = function(evt){
			console.log("error");
		}
		var t = window.setInterval("speakText(speakMessage)",5000);
	}
	
	function test(){
		speakText("我很帅！");
	}
	function speakText(str){
		if(str){
		   	var url = "http://tts.baidu.com/text2audio?lan=zh&ie=UTF-8&per=3&text=" + encodeURI(str);        // baidu
		   	var n = new Audio(url);
		  	n.src = url;
		  	n.play();
		  	speakMessage = '';
		}
	}
	
</script>
</head>
<body>
	<!-- 隐藏参数 -->
	<div id="hiddenParam">
		<input id="path" value="<%= path%>" type="hidden">
	</div>
	<input id="connect" type="button" value="连接" onclick="connect()">
	<input id="close" type="button" value="关闭" onclick="closeWebSocket();">
	<input id="chick" type="button" value="检测" onclick="look();">
	<input id="speak" type="button" value="开始" onclick="test();"> 
	<div>
		<input id="roomId">
		<input id="connect" type="button" value="连接" onclick="showBarrage();">
		<input id="break" type="button" value="断开" onclick="closeWebSocket();">
	</div>
	<div>
		房间名:<input id="roomName" style="width:200px;">
		主播名:<input id="ownerName">
	</div>
	<div id="message" style="width:500px;height:200px;border:1px solid #00F;overflow:hidden;word-break: break-all;word-wrap: break-word;overflow-y:auto;"></div>
</body>
</html>
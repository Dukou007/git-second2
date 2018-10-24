<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
<script type="text/javascript">    
	var ws = null;
	function startWebSocket() {
		if ('WebSocket' in window)
			ws = new WebSocket("ws://localhost:8080/websocket/attr1");
		else if ('MozWebSocket' in window)
			ws = new MozWebSocket("ws://localhost:8080/websocket/attr2");
		else
			alert("not support");


		ws.onmessage = function(evt) {
			console.log("接受到数据---->" + evt.data);
			document.getElementById('dialog').appendChild(document.createElement("br"))
			document.getElementById('dialog').append("新消息-->" + evt.data);
		};

		ws.onclose = function(evt) {
			console.log("前端ws监测到---close");
		};

		ws.onopen = function(evt) {
			console.log("ws.open is open");
		};
	}

	function sendMsg() {
		ws.send(document.getElementById('writeMsg').value);
	}
</script>
</head>
<body onload="startWebSocket();">
	<input type="text" id="writeMsg"></input>
	<input type="button" value="发送" onclick="sendMsg()"></input>

	<div id="dialog"></div>
</body>
</html>

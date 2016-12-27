<html>
<head>
    <title>WebSoket Demo</title>
    <script type="text/JavaScript">
        //验证浏览器是否支持WebSocket协议
        if (!window.WebSocket) {
            alert("WebSocket not supported by this browser!");
        }
        var ws;
        function display() {
            //var valueLabel = document.getElementById("valueLabel");
            //valueLabel.innerHTML = "";
            ws=new WebSocket("ws://localhost:8080/ws/chat/xyz");
            //监听消息
            ws.onmessage = function(event) {
                //valueLabel.innerHTML+ = event.data;
                log(event.data);
            };
            // 打开WebSocket
            ws.onclose = function(event) {

                //WebSocket Status:: Socket Closed
            };
            // 打开WebSocket
            ws.onopen = function(event) {
                //WebSocket Status:: Socket Open
                //// 发送一个初始化消息
                ws.send("Hello, Server!");
            };
            ws.onerror =function(event){
                //WebSocket Status:: Error was reported
            };
        }
        var log = function(s) {
            if (document.readyState !== "complete") {
                log.buffer.push(s);
            } else {
                document.getElementById("contentId").innerHTML += (s + "\n");
            }
        }
        function sendMsg(){
            var msg=document.getElementById("messageId");
            //alert(msg.value);
            ws.send(msg.value);
        }
    </script>
</head>
<body onload="display();">
<div id="valueLabel"></div>
<textarea rows="20" cols="30" id="contentId"></textarea>
<br/>
<input name="message" id="messageId"/>
<button id="sendButton" onClick="javascript:sendMsg()" >Send</button>
</body>
</html>
<script>
//    var webSocket = null;
//    var tryTime = 0;
//    $(function () {
//        initSocket();
//
//        window.onbeforeunload = function () {
//            //离开页面时的其他操作
//        };
//    });
//
//    /**
//     * 初始化websocket，建立连接
//     */
//    function initSocket() {
//        if (!window.WebSocket) {
//            alert("您的浏览器不支持websocket！");
//            return false;
//        }
//
//        webSocket = new WebSocket("ws://localhost:8080/websocket.ws/"+ relationId +"/"+ userCode);
//
//        // 收到服务端消息
//        webSocket.onmessage = function (msg) {
//            console.log(msg);
//        };
//
//        // 异常
//        webSocket.onerror = function (event) {
//            console.log(event);
//        };
//
//        // 建立连接
//        webSocket.onopen = function (event) {
//            console.log(event);
//        };
//
//        // 断线重连
//        webSocket.onclose = function () {
//            // 重试10次，每次之间间隔10秒
//            if (tryTime < 10) {
//                setTimeout(function () {
//                    webSocket = null;
//                    tryTime++;
//                    initSocket();
//                }, 500);
//            } else {
//                tryTime = 0;
//            }
//        };
//
//    }
</script>
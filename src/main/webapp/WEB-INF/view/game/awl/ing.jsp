<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/23
  Time: 19:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>阿！瓦！隆！</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="${basePath}/lib/js/jquery-1.12.2.js"></script>
    <script type="text/javascript" src="${basePath}/lib/bootstrap-3.3.5/js/bootstrap.js"></script>
    <script type="text/javascript" src="${basePath}/lib/js/validateKit.js"></script>
    <%--<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"--%>
    <%--rel="stylesheet" type="text/css">--%>
    <link rel="stylesheet" type="text/css" href="${basePath}/lib/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${basePath}/lib/bootstrap-3.3.5/css/pingendo-default-bootstrap.css">
    <%--<link href="http://pingendo.github.io/pingendo-bootstrap/themes/default/bootstrap.css"--%>
    <%--rel="stylesheet" type="text/css">--%>
</head>

<body>
<div class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-ex-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${basePath}/"><span>huangshihe</span></a>
        </div>
        <div class="collapse navbar-collapse" id="navbar-ex-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="active">
                    <a href="${basePath}/"><i class="fa fa-fw fa-home fa-lg"></i>Home</a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-at fa-fw fa-lg"></i>Contacts</a>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <%--玩家列表/角色列表--%>
            <div id="awlUserList" class="col-md-4">
                <%--游戏等待中--%>

                <c:if test="${awl.status == 0}">
                    游戏等待中，快去呼唤你的小伙伴来加入你创建的游戏吧...<br>
                    等待中的玩家们：
                    <c:forEach items="${userList}" var="user">
                        <li id="awlUser_${user.username}">${user.username}
                            <img src="${basePath}/lib/photos/user_placeholder.png" class="img-circle img-responsive col-md-2">
                            <c:choose>
                                <c:when test="${user.sex}">男</c:when>
                                <c:otherwise>女</c:otherwise>
                            </c:choose>
                        </li>
                    </c:forEach>
                </c:if>
                <div class="row">特殊信息：</div>
            </div>
            <div class="col-md-8">
                <textarea rows="20" cols="30" id="contentId"></textarea>
                <%--TODO 游戏进程信息（红色）及聊天信息（黑色），坏人间的私密信息（蓝色）--%>
            </div>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-6">选择组员或投票</div>
            <div class="col-md-6">
                <input name="message" id="messageId"/>
                <button id="sendButton" onClick="sendMsg()" >提交</button>
                <input>
                <input type="button" value="私密">
            </div>
        </div>
    </div>
</div>
</body>

</html>
<script type="text/JavaScript">
    //验证浏览器是否支持WebSocket协议
    if (!window.WebSocket) {
        // TODO 当前浏览器不支持时，应该从玩家列表（等待、游戏中）中去掉当前用户，可视为意外退出游戏。
        alert("对不起，当前浏览器不支持该游戏！建议使用firefox、chrome等浏览器。");
        window.location.href = "${basePath}/game";
    }else{
        init();
    }
    var chatWs;
    var coreWs;
    function init() {
        // 聊天socket
        chatWs=new WebSocket("ws://"+document.location.host+"${basePath}/ws/awl/chat/${loginUser.id}");
        //监听消息
        chatWs.onmessage = function(event) {
            log(event.data);
        };
        // 关闭WebSocket
        chatWs.onclose = function(event) {

            //WebSocket Status:: Socket Closed
        };
        // 打开WebSocket
        chatWs.onopen = function(event) {
            //WebSocket Status:: Socket Open
            // 发送一个初始化消息
//            chatWs.send("Hello, Server!");
        };
        chatWs.onerror =function(event){
            //WebSocket Status:: Error was reported
        };

        /////////////////////////////////////////////////////////////
        // 核心socket
        coreWs = new WebSocket("ws://"+document.location.host+"${basePath}/ws/awl/core/${awl.creatorId}/${loginUser.id}");

        coreWs.onopen = function (event) {
//            log("open:"+event.data);
        };

        coreWs.onmessage = function (event) {
            console.info(event.data);
            // 如果游戏状态为等待中，则检查玩家信息是否正确；当游戏状态为进行中，则检查当前界面是否处于进行中
            // 将string数据转为json。
            var $message = $.parseJSON(event.data);
            if($message.status == 0){
                // 重新生成等待中的玩家列表，并提示用户。
                renderAwlUserList($message.userPackets);
            }else if($message.status == 1){
                // TODO 进入正式游戏界面，并提示用户。
                console.info("进入正式游戏，修改界面！");
            }else if($message.status == 2){
                // TODO 游戏结束，提示用户
                console.info("游戏结束！");
            }else if($message.status == -1){
                // 删除已退出玩家
                deleteAwlUser($message.userPackets);
            }
        };

        coreWs.onclose = function (event) {
//            console.info(event.data);
        };

//        window.onbeforeunload=function(){
//            return "游戏还在进行中，确定继续该操作？";
//        };
//        window.onunload = function(){
//            coreWs.close();
//        }

    }
    var log = function(s) {
        if (document.readyState !== "complete") {
            log.buffer.push(s);
        } else {
            document.getElementById("contentId").innerHTML += (s + "\n");
        }
    };
    function sendMsg(){
        var $message = $("#messageId");
        chatWs.send($message.val());
        $message.val("");
    }

    function renderAwlUserList(userPackets) {
        var $awlUserList = $("#awlUserList");
        // 记录需要添加的下标
        var readyToAdd = "";

        $.each(userPackets, function(i){
            // 如果当前li标签中不存在该玩家，则准备新增该玩家
            if(containAwlUser($awlUserList.children("li"), userPackets[i].username) == null){
                readyToAdd += i+",";
            }
        });
        ////////////////////////////修改界面////////////////////////
        var readyToAddList = readyToAdd.split(",");
        for(var i = 0; i < readyToAddList.length; i++){
            // 忽略“空字符串”
            if(isNum(readyToAddList[i])){
                var userPacket = userPackets[readyToAddList[i]];
                if(userPacket.sex){
                    $awlUserList.append("<li id='awlUser_"+userPacket.username+"'>"+userPacket.username+"<img src='"+userPacket.photo+"' " +
                            "class='img-circle img-responsive col-md-2'>男</li>");
                }else{
                    $awlUserList.append("<li id='awlUser_"+userPacket.username+"'>"+userPacket.username+"<img src='"+userPacket.photo+"' " +
                            "class='img-circle img-responsive col-md-2'>女</li>");
                }
            }
        }
    }

    /**
     * 判断是否存在该玩家，如果存在则返回当前li
     * @param awlUserLi 已展现在li标签中的玩家列表
     * @param username
     */
    function containAwlUser(awlUserLi, username) {
        // 是否找到玩家的标记
        var awlUser = null;
        $.each(awlUserLi, function (i) {
            if(awlUserLi[i].id == "awlUser_"+username){
                awlUser = awlUserLi[i];
                return;
            }
        });
        return awlUser;
    }

    /**
     * 删除该玩家
     * @param userPackets
     */
    function deleteAwlUser(userPackets){
        var $awlUserList = $("#awlUserList");
        containAwlUser($awlUserList.children("li"), userPackets[0].username).remove();
    }

    window.onbeforeunload=function(){
        return "游戏还在进行中，确定继续该操作？";
    };
    window.onunload = function(){
        coreWs.close();
    }

</script>
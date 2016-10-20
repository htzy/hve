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
        <c:if test="${loginUser.id == awl.creatorId}">
            <a class="btn btn-danger" href="javascript:void(0);" onclick="quitGame()">结束这一局</a>
        </c:if>
        <c:if test="${loginUser.id != awl.creatorId}">
            <a class="btn btn-danger" href="javascript:void(0);" onclick="quitGame()">退出这一局</a>
        </c:if>
    </div>

    <div class="container dl-horizontal">
        <%--玩家列表/角色列表--%>
        <div class="col-sm-4">
            <div id="introduceInfo">
                <%--游戏等待中--%>
                <c:if test="${awl.status == 0 || awl.status == -1}">
                    游戏等待中，快去呼唤你的小伙伴来加入你创建的游戏吧...<br>
                    等待中的玩家们：
                </c:if>
            </div>
            <ul id="awlUserList" class="list-group">
            </ul>
            <div id="awlUserInfo" class="row"></div>
        </div>
        <div class="col-sm-8">
            <textarea rows="20" cols="30" id="contentId"></textarea>
            <%--TODO 游戏进程信息（红色）及聊天信息（黑色），坏人间的私密信息（蓝色）--%>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-6" id="teamDiv">选择组员或投票</div>
            <div class="col-md-6">
                <input name="message" id="messageId"/>
                <button id="sendButton" onClick="sendMsg()">提交</button>
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
    } else {
        init();
    }
    var chatWs;
    var coreWs;
    function init() {
        // 聊天socket
        chatWs = new WebSocket("ws://" + document.location.host + "${basePath}/ws/awl/chat/${loginUser.id}");
        //监听消息
        chatWs.onmessage = function (event) {
            log(event.data);
        };
        // 关闭WebSocket
        chatWs.onclose = function (event) {

            //WebSocket Status:: Socket Closed
        };
        // 打开WebSocket
        chatWs.onopen = function (event) {
            //WebSocket Status:: Socket Open
            // 发送一个初始化消息
//            chatWs.send("Hello, Server!");
        };
        chatWs.onerror = function (event) {
            //WebSocket Status:: Error was reported
        };

        /////////////////////////////////////////////////////////////
        // 核心socket
        coreWs = new WebSocket("ws://" + document.location.host + "${basePath}/ws/awl/core/${awl.creatorId}/${loginUser.id}");

        coreWs.onopen = function (event) {
//            log("open:"+event.data);
        };

        coreWs.onmessage = function (event) {
            console.info(event.data);
            // 如果游戏状态为等待中，则检查玩家信息是否正确；当游戏状态为进行中，则检查当前界面是否处于进行中
            // 将string数据转为json。
            var $message = $.parseJSON(event.data);
            var $introduceInfo = $("#introduceInfo");
            if ($message.status == 0) {
                // 重新生成等待中的玩家列表，并提示用户。
                renderWaitUserList($message.userPackets);
                $introduceInfo.innerHTML = "游戏等待中，快去呼唤你的小伙伴来加入你创建的游戏吧...<br>等待中的玩家们：";
            } else if ($message.status == 1) {
                console.info("进入正式游戏，修改界面！");
                // 生成玩家列表
                renderAwlUserList($message);
                // TODO now 生成任务？组队？

            } else if ($message.status == 2) {
                console.info("游戏结束！");
                alert("欢迎再来...see you~");
                quitGame();
            } else if ($message.status == -1) {
                // 删除已退出玩家
                deleteAwlUser($message.userPackets);
                $introduceInfo.innerHTML = "有人退出了！游戏进入等待中，快去呼唤你的小伙伴来加入你创建的游戏吧...<br>等待中的玩家们：";
            }
        };

        coreWs.onclose = function (event) {
//            console.info(event.data);
        };
    }
    var log = function (s) {
        if (document.readyState !== "complete") {
            log.buffer.push(s);
        } else {
            document.getElementById("contentId").innerHTML += (s + "\n");
        }
    };
    function sendMsg() {
        var $message = $("#messageId");
        chatWs.send($message.val());
        $message.val("");
    }

    function renderWaitUserList(userPackets) {
        var $awlUserList = $("#awlUserList");
        // 记录需要添加的下标
        var readyToAdd = "";

        $.each(userPackets, function (i) {
            // 如果当前li标签中不存在该玩家，则准备新增该玩家
            if (containAwlUser($awlUserList.children("li"), userPackets[i].username) == null) {
                readyToAdd += i + ",";
            }
        });
        ////////////////////////////修改界面////////////////////////
        var readyToAddList = readyToAdd.split(",");
        for (var i = 0; i < readyToAddList.length; i++) {
            // 忽略“空字符串”
            if (isNum(readyToAddList[i])) {
                var userPacket = userPackets[readyToAddList[i]];
                if (userPacket.sex) {
                    $awlUserList.append("<li class='list-group-item' id='awlUser_" + userPacket.username + "'>" + userPacket.username + "<img src='" + userPacket.photo + "' " +
                            "class='img-circle img-responsive col-md-2'>男</li>");
                } else {
                    $awlUserList.append("<li class='list-group-item' id='awlUser_" + userPacket.username + "'>" + userPacket.username + "<img src='" + userPacket.photo + "' " +
                            "class='img-circle img-responsive col-md-2'>女</li>");
                }
            }
        }
    }

    function renderAwlUserList(packet) {
        var $awlUserInfo = $("#awlUserInfo");
        if ($awlUserInfo.innerHTML != null && $awlUserInfo.innerHTML != "") {
            return;
        }
        var $awlUserList = $("#awlUserList");
        var $introduceInfo = $("#introduceInfo");
        var $teamDiv = $("#teamDiv");
        var awlUserId = packet.awlUserId;
        var userPackets = packet.userPackets;
        $introduceInfo.innerHTML = "游戏进行中，享受游戏吧...<br>玩家们：";
        $awlUserList.empty();
        for (var i = 0; i < userPackets.length; i++) {
            var userPacket = userPackets[i];
            // 当显示自己身份时
            if (userPacket.userId == awlUserId) {
                $awlUserInfo.innerHTML = "特殊信息：" + userPacket.identityInfo;
                // 好人1，坏人2
                if (userPacket.identityType == 1) {
                    $awlUserList.append("<li class='list-group-item alert-success' id='awlUser_" + userPacket.identityNum + "'>" + userPacket.identityNum + "<img src='" + userPacket.photo + "' " +
                            "class='img-circle img-responsive col-md-2'>userPacket.identityName</li>");
                } else {
                    $awlUserList.append("<li class='list-group-item alert-danger' id='awlUser_" + userPacket.identityNum + "'>" + userPacket.identityNum + "<img src='" + userPacket.photo + "' " +
                            "class='img-circle img-responsive col-md-2'>userPacket.identityName</li>");
                }
            } else {
                $awlUserList.append("<li class='list-group-item alert-danger' id='awlUser_" + userPacket.identityNum + "'>" + userPacket.identityNum + "<img src='" + userPacket.photo + "' " +
                        "class='img-circle img-responsive col-md-2'>未知身份</li>");
            }
            // 0当组长，开始组队，队伍人数：2人。
            if(userPacket.identityNum == 0){
                $teamDiv.empty();
                // TODO now
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
            if (awlUserLi[i].id == "awlUser_" + username) {
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
    function deleteAwlUser(userPackets) {
        var $awlUserList = $("#awlUserList");
        var $awlUser = containAwlUser($awlUserList.children("li"), userPackets[0].username);
        if ($awlUser != null) {
            $awlUser.remove();
        }
    }

    window.onbeforeunload = function () {
        return "游戏还在进行中，确定继续该操作？";
    };
    window.onunload = function () {
        // run correct on firefox, but chrome can't execute this code?
        coreWs.close();
        chatWs.close();
    };

    function quitGame() {
        chatWs.close();
        coreWs.close();
        window.location.href = "${basePath}/game_awl/quit";
    }

</script>
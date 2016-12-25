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
        hello, ${loginUser.username} &nbsp;
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
            <div class="col-md-6" id="teamDiv"></div>
            <div class="col-md-6">
                <input name="message" id="messageId"/>
                <button id="sendButton" onClick="sendMsg()">提交</button>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6" id="voteDiv">

            </div>
            <div class="col-md-6" id="taskDiv">

            </div>
        </div>
        <div class="row">
            <div class="col-sm-4" id="totalSuccessTimesDiv">
                累计任务成功次数：<span></span>
            </div>
            <div class="col-sm-4" id="totalFailTimesDiv">
                累计任务失败次数：<span></span>
            </div>
            <div class="col-sm-4" id="delayTimesDiv">
                累计延迟次数：<span></span>
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
    //    var chatWs;
    var coreWs;
    var selfNum;
    var selfType;
    function init() {
        <%--// 聊天socket--%>
        <%--chatWs = new WebSocket("ws://" + document.location.host + "${basePath}/ws/awl/chat/${loginUser.id}");--%>
        <%--//监听消息--%>
        <%--chatWs.onmessage = function (event) {--%>
        <%--log(event.data);--%>
        <%--};--%>
        <%--// 关闭WebSocket--%>
        <%--chatWs.onclose = function (event) {--%>

        <%--//WebSocket Status:: Socket Closed--%>
        <%--};--%>
        <%--// 打开WebSocket--%>
        <%--chatWs.onopen = function (event) {--%>
        <%--//WebSocket Status:: Socket Open--%>
        <%--// 发送一个初始化消息--%>
        <%--//            chatWs.send("Hello, Server!");--%>
        <%--};--%>
        <%--chatWs.onerror = function (event) {--%>
        <%--//WebSocket Status:: Error was reported--%>
        <%--};--%>

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
                $introduceInfo.html("游戏等待中，快去呼唤你的小伙伴来加入你创建的游戏吧...<br>等待中的玩家们：");
            } else if ($message.status == 1) {
                // 生成玩家列表
                renderAwlUserList($message);
                renderTeam($message);
                renderVote($message);
                renderTask($message);
                updateResult($message);
            } else if ($message.status == 2) {
                renderTask($message);
                updateResult($message);
                dealGameResult($message);
            } else if ($message.status == -1) {
                // 删除已退出玩家
                deleteAwlUser($message.userPackets);
                $introduceInfo.html("有人退出了！游戏进入等待中，快去呼唤你的小伙伴来加入你创建的游戏吧...<br>等待中的玩家们：");
            } else {
                log($message.data);
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
        var data;
        // TODO now 当selfNum == 0 时有错，错误显示为用户名？？
        if (selfNum == null || selfNum == "") {
            data = "${loginUser.username}";
        } else {
            data = selfNum;
        }
        data += " > " + $message.val();
        coreWs.send("{\"operate\":\"chat\", \"data\":\"" + data + "\"}");
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
        if ($awlUserInfo.html() != null && $awlUserInfo.html() != "") {
            return;
        }
        var $awlUserList = $("#awlUserList");
        var $introduceInfo = $("#introduceInfo");
        var awlUserId = packet.awlUserId;
        var userPackets = packet.userPackets;
        $introduceInfo.html("游戏进行中，享受游戏吧...<br>玩家们：");
        // 清空之前的聊天记录
        $("#contentId").html("系统提示 > 游戏开始了，别bibi了！");

        $awlUserList.empty();
        for (var i = 0; i < userPackets.length; i++) {
            var userPacket = userPackets[i];
            // 当显示自己身份时
            if (userPacket.userId == awlUserId) {
                selfNum = userPacket.identityNum;
                selfType = userPacket.identityType;
                $awlUserInfo.html(userPacket.identityInfo);
                // 好人1，坏人2
                if (userPacket.identityType == 1) {
                    $awlUserList.append("<li class='list-group-item alert-success' id='awlUser_" + userPacket.identityNum + "'>" + userPacket.identityNum + "<img src='" + userPacket.photo + "' " +
                            "class='img-circle img-responsive col-md-2'>" + userPacket.identityName + "</li>");
                } else {
                    $awlUserList.append("<li class='list-group-item alert-danger' id='awlUser_" + userPacket.identityNum + "'>" + userPacket.identityNum + "<img src='" + userPacket.photo + "' " +
                            "class='img-circle img-responsive col-md-2'>" + userPacket.identityName + "</li>");
                }
            } else {
                $awlUserList.append("<li class='list-group-item alert-warning' id='awlUser_" + userPacket.identityNum + "'>" + userPacket.identityNum + "<img src='" + userPacket.photo + "' " +
                        "class='img-circle img-responsive col-md-2'>未知身份</li>");
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
    };

    function quitGame() {
        coreWs.close();
        window.location.href = "${basePath}/game_awl/quit";
    }

    function renderTeam(basePacket) {
        var teamPacket = basePacket.teamPacket;
        if (teamPacket == null || teamPacket == "") {
            return;
        }
        // 当队伍信息包中的成员为空时，则需要创建队伍。
        if (teamPacket.members.length == 0) {
            var leaderPacket = basePacket.userPackets[teamPacket.creatorNum];
            if (basePacket.awlUserId == leaderPacket.userId) {
                createTeam(basePacket);
            }
        }
        // 当出现队伍结果时，说明该队伍组建完成，删除该组的显示
        if (teamPacket.info != null && teamPacket.info != "" && (teamPacket.status == 2 || teamPacket.status == -1)) {
            deleteAll();
            log("系统提示 > " + teamPacket.info);
        }
    }

    function createTeam(basePacket) {
        var $teamDiv = $("#teamDiv");
        if ($teamDiv.find("tr").length > 0) {
            return;
        }
        var teamPacket = basePacket.teamPacket;
        var userPackets = basePacket.userPackets;
        var title = $("<tr><td>现请您勾选如下玩家，组建队伍！</td></tr>");
        var teamTable = $("<table id='newTeam'></table>").append(title);
        for (var i = 0; i < userPackets.length; i++) {
            teamTable.append($("<label><input type='checkbox' name='team_member_num' value='" + userPackets[i].identityNum + "'/>" + userPackets[i].identityNum + "</label>"))
        }
        var postBtn = $("<input type='button' value='提交'>").bind("click", {
            currentLeaderNum: teamPacket.creatorNum,
            teamMemberCount: teamPacket.memberCount
        }, postTeam);
        teamTable.append(postBtn);
        $teamDiv.append(teamTable);
    }

    function postTeam(event) {
        // check checkbox size must equal currentTeamNum
        var currentLeaderNum = event.data.currentLeaderNum;
        var teamMemberCount = event.data.teamMemberCount;
        var $teamMemberNumCheck = $("input[name='team_member_num']:checked");
        if ($teamMemberNumCheck.length != teamMemberCount) {
            alert("你只能勾选" + teamMemberCount + "个成员！");
        } else {
            var teamMemberNums = "";
            $teamMemberNumCheck.each(function () {
                teamMemberNums += ($(this).val() + ',');
            });
            teamMemberNums = teamMemberNums.substring(0, teamMemberNums.length - 1);
            coreWs.send("{\"operate\":\"postTeam\"," +
                    "\"teamPacket\":{\"creatorNum\":" + currentLeaderNum + ",\"members\":[" + teamMemberNums + "]}}");
            $("#newTeam").find("input").attr("disabled", "disabled");
            alert("您组建的队伍信息已提交！");
        }
    }

    function renderVote(basePacket) {
        var $voteDiv = $("#voteDiv");
        if ($voteDiv.find("tr").length > 0) {
            return;
        }
        var teamPacket = basePacket.teamPacket;
        if (teamPacket.members.length > 0 && teamPacket.status == 0) {
            var title = $("<tr><td>组长为：" + teamPacket.creatorNum + "，队伍成员为：" + teamPacket.members + "</td></tr>");
            var voteTable = $("<table id='newVote'></table>").append(title);
            var postBtn = $("<input type='button' onclick='postVote(true)' value='同意'>" +
                    "<input type='button' onclick='postVote(false)' value='反对'>");
            voteTable.append(postBtn);
            $voteDiv.append(voteTable);
        }
    }

    function postVote(answer) {
        coreWs.send("{\"operate\":\"postVote\"," +
                "\"votePacket\":{\"awlUserNum\":" + selfNum + ",\"agree\":\"" + answer + "\"}}");
        $("#newVote").find("input").attr("disabled", "disabled");
        alert("您的投票信息已提交！");
    }

    function renderTask(basePacket) {
        var teamPacket = basePacket.teamPacket;
        if (teamPacket.status == 2) {
            // 如果队伍组建成功，给队员创建任务，好人只能创建1个选项，坏人可以创建两个选项
            createTask(basePacket);
        } else if (teamPacket.status == -1) {
            // 如果队伍组建失败，则取消创建任务，并向服务器发送信息，要求进行下一轮队长选队员。
            if (teamPacket.creatorNum == selfNum) {
                coreWs.send("{\"operate\":\"nextTeam\", \"data\":" + selfNum + "}");
            }
        } else if (teamPacket.status == 1) {
            // 如果接收到任务结果（即组队成功，但是已过时），则显示任务结果，并要求进行下一轮队长选队员。
            var taskPacket = teamPacket.taskPacket;
            if (taskPacket.result) {
                log("系统提示 > 由" + taskPacket.members + "所做的任务成功！");
            } else {
                log("系统提示 > 由" + taskPacket.members + "所做的任务失败！");
            }
            // 当游戏结束后，不能再请求
            if (basePacket.status == 1 && teamPacket.creatorNum == selfNum) {
                coreWs.send("{\"operate\":\"nextTeam\", \"data\":" + selfNum + "}");
            }
        }
    }

    function createTask(basePacket) {
        var $taskDiv = $("#taskDiv");
        if ($taskDiv.find("tr").length > 0) {
            return;
        }
        var teamPacket = basePacket.teamPacket;
        var members = teamPacket.members;
        // 找到所有的组员进行任务
        $.each(members, function (i) {
            if (members[i] == selfNum) {
                var title = $("<tr><td>您有一个任务需要执行</td><tr>");
                var taskTable = $("<table id='newTask'></table>").append(title);
                // 如果是好人1，则只能执行√；坏人2可以执行√和×
                if (selfType == 1) {
                    taskTable.append($("<input type='button' onclick='postTask(" + true + ")' value='√'>"));
                } else if (selfType == 2) {
                    taskTable.append($("<input type='button' onclick='postTask(" + true + ")' value='√'>" +
                            "<input type='button' onclick='postTask(" + false + ")' value='×'>"))
                }
                $taskDiv.append(taskTable);
            }
        });
    }

    function postTask(answer) {
        coreWs.send("{\"operate\":\"postTask\"," +
                "\"votePacket\":{\"awlUserNum\":" + selfNum + ",\"agree\":\"" + answer + "\"}}");
//        $("#newTask").find("input").attr("disabled", "disabled");
        alert("您做的任务已提交！");
        $("#taskDiv").empty();
    }

    function deleteAll() {
        var $teamDiv = $("#teamDiv");
        var $voteDiv = $("#voteDiv");
        $teamDiv.empty();
        $voteDiv.empty();
    }

    function updateResult(basePacket) {
        var $totalSuccessTimes = $("#totalSuccessTimesDiv span");
        var $totalFailTimes = $("#totalFailTimesDiv span");
        var $delayTimes = $("#delayTimesDiv span");
        $totalSuccessTimes.html(basePacket.successTimes);
        $totalFailTimes.html(basePacket.failTimes);
        $delayTimes.html(basePacket.delayTimes);
        if (basePacket.delayTimes >= 4) {
            // 延迟次数已达到上限，由新队长选人，可跳过投票环节
            alert("延迟次数已达到上限，新队长选人时，请直接同意！");
        }
    }

    function dealKillTask(event) {
        var killedIdentityNum = $("input[name='member_num']:checked").val();
        var userPackets = event.data.userPackets;
        var mehring;
        var info = "好人：";
        for (var i = 1; i < userPackets.length; i++) {
            info += " 编号：" + userPackets[i].identityNum + " 身份：" + userPackets[i].identityName + "；";
            if (userPackets[i].identityName == "梅林") {
                mehring = userPackets[i].identityNum;
            }
        }
        if (killedIdentityNum == mehring) {
            // 刺杀成功！坏人赢
            info = "刺杀成功！" + info + "失败！";
        } else {
            // 刺杀失败！好人赢
            info = "刺杀失败！" + info + "获得胜利！"
        }
        alert(info);
        log("系统提示 > " + info);
        alert("欢迎再来...see you~");
//        quitGame();
    }

    function dealGameResult(basePacket) {
        var userPackets = basePacket.userPackets;
        if (basePacket.successTimes >= 3) {
            // 坏人开始杀人，由刺客执行，开启私密讨论，必须杀预言家才赢
            var assassin = userPackets[0];
            if (assassin.identityNum == selfNum) {
                var $taskDiv = $("#taskDiv");
                var title = $("<tr><td>您需要指定刺杀对象</td></tr>");
                var taskTable = $("<table id='killTask'></table>").append(title);
                for (var i = 1; i < userPackets.length; i++) {
                    taskTable.append($("<label><input type='radio' name='member_num' value='" + userPackets[i].identityNum + "'/>" + userPackets[i].identityNum + "</label>"))
                }
                var postBtn = $("<input type='button' value='刺杀'>").bind("click", {userPackets: userPackets}, dealKillTask);
                taskTable.append(postBtn);
                $taskDiv.append(taskTable);
            }
        }
        if (basePacket.failTimes >= 3) {
            // 坏人直接赢
            var info = "坏人：";
            for (var i = 0; i < userPackets.length; i++) {
                info += " 编号：" + userPackets[i].identityNum + " 身份：" + userPackets[i].identityName + "；";
            }
            info += "获得胜利！";
            alert(info);
            log("系统提示 > " + info);
            alert("欢迎再来...see you~");
//            quitGame();
        }
    }

</script>
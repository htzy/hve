<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/21
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>阿瓦隆游戏大厅</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="${basePath}/lib/js/jquery-1.12.2.js"></script>
    <script type="text/javascript" src="${basePath}/lib/bootstrap-3.3.5/js/bootstrap.js"></script>
    <%--<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"--%>
    <%--rel="stylesheet" type="text/css">--%>
    <link rel="stylesheet" type="text/css" href="${basePath}/lib/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${basePath}/lib/bootstrap-3.3.5/css/pingendo-default-bootstrap.css">
    <%--<link href="http://pingendo.github.io/pingendo-bootstrap/themes/default/bootstrap.css"--%>
    <%--rel="stylesheet" type="text/css">--%>
</head>
<body data-spy="scroll">
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
            <div class="col-md-6">
                <img src="${basePath}/lib/photos/photo_awl_index.jpg" class="img-responsive">
            </div>
            <div class="col-md-6">
                <p class="text-center">
                    <strong>阿瓦隆——游戏进程</strong>
                </p>
                <p>
                    <br>
                    <strong>天黑阶段：</strong>
                    <br>1、 梅林睁眼，所有坏人举手（除了莫德雷德）。
                    <br>2、 奥伯伦和兰斯洛特*坏以外的坏人睁眼，互相辨认同伴，兰斯洛特*坏只举手不睁眼。
                    <br>3、 派西维尔睁眼，梅林和莫甘娜举手
                    <br>
                    <strong>天亮阶段：</strong>
                    <br>1、 随机（如非第一次游戏，则以上局最后一个队长的左手玩家为首任队长）选择一个玩家担任队长，队长从5个任务中选择一个，再选定足够人数的队员，进行发言和投票，决定任务执行或延迟。
                    <br>2、 队长左手边的玩家为下一个队长，不断重复，直到出现三次任务成功或失败为游戏结束。
                    <br>
                    <strong>胜负判定阶段：</strong>
                    <br>1、 出现三次任务失败时坏人直接胜利。
                    <br>2、出现三次任务成功时所有玩家禁言，所有坏人举手声明自己是坏人，讨论梅林人选，并确定一个玩家刺杀，如被刺杀的是梅林，则坏人胜利，否则好人胜利。</p>
            </div>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h1 class="text-center">阿瓦隆游戏大厅</h1>
                <p class="text-center">选择一个等待中的游戏，点击“加入”按钮，就可以享受游戏了！</p>
            </div>
        </div>
        <div class="row">
            <c:forEach items="${awlList}" var="awl">
                <c:choose>
                    <c:when test="${awl.status == 0 || awl.status == -1}">
                        <div class="col-md-4">
                                <%--TODO 增加头像 --%>
                                <%--<img src="http://pingendo.github.io/pingendo-bootstrap/assets/user_placeholder.png"--%>
                                <%--class="center-block img-circle img-responsive">--%>
                            <img src="http://oeerwig1e.bkt.clouddn.com/user_placeholder.png"
                                 class="center-block img-circle img-responsive">
                            <h3 class="text-center">创建人：${awl.creatorName}</h3>
                            <div class="row">
                                <div class="col-md-12">
                                    <i class="fa fa-3x fa-bed fa-fw"></i>
                                    <span>${awl.createTime}</span>
                                    <div class="col-md-3 pull-right">
                                        <a class="btn btn-primary" href="${basePath}/game_awl/ing/${awl.id}">加入<span
                                                class="badge">${fn:length(awl.gamers)}</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${awl.status == 1}">
                        <div class="col-md-4">
                            <img src="http://oeerwig1e.bkt.clouddn.com/user_placeholder.png"
                                 class="center-block img-circle img-responsive">
                            <h3 class="text-center">创建人：${awl.creatorName}</h3>
                            <div class="row">
                                <div class="col-md-12">
                                    <i class="fa fa-3x fa-fw fa-paper-plane"></i>
                                    <span>创建时间：${awl.createTime}</span>
                                    <div class="col-md-3 pull-right">
                                        <a class="btn btn-primary disabled">进行中</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <%--TODO 异常情况？--%>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <div class="col-md-4">
                <img src="http://oeerwig1e.bkt.clouddn.com/user_placeholder.png" class="center-block img-circle img-responsive">
                <h3 class="text-center"><a href="${basePath}/game_awl/create">点击创建游戏</a></h3>
            </div>
        </div>
    </div>
</div>
<footer class="section section-primary">
    <div class="container">
        <div class="row">
            <div class="col-sm-6">
                <h1>about huangshihe</h1>
                <p>thanks bootstrap and pingendo support
                    <br>if you have any question or suggest, &nbsp;please contact me.
                    <br>contact@huangshihe.com</p>
            </div>
            <div class="col-sm-6">
                <p class="text-info text-right">
                    <br>
                    <br>
                </p>
                <div class="row">
                    <div class="col-md-12 hidden-lg hidden-md hidden-sm text-left">
                        <a href="#"><i class="fa fa-3x fa-fw fa-inverse fa-mortar-board"></i></a>
                        <a href="#"><i class="fa fa-3x fa-fw fa-inverse fa-wechat"></i></a>
                        <a href="#"><i class="fa fa-3x fa-fw fa-inverse fa-android"></i></a>
                        <a href="#"><i class="fa fa-3x fa-fw hub fa-inverse fa-html5"></i></a>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 hidden-xs text-right">
                        <a href="#"><i class="fa fa-3x fa-fw fa-inverse fa-mortar-board"></i></a>
                        <a href="#"><i class="fa fa-3x fa-fw fa-inverse fa-wechat"></i></a>
                        <a href="#"><i class="fa fa-3x fa-fw fa-inverse fa-android"></i></a>
                        <a href="#"><i class="fa fa-3x fa-fw hub fa-inverse fa-html5"></i></a>
                    </div>
                </div>
            </div>
        </div>
        <div class="text-center">
            <a href="http://www.miitbeian.gov.cn/" class="alert-info">测试中</a>
        </div>
    </div>
</footer>
</body>
<script type="text/javascript">
    var info = "${info}";
    if (info != "") {
        alert(info);
    }
</script>
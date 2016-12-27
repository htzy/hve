<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/22
  Time: 17:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>娱乐中心</title>
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
<div id="carousel-next" data-interval="5000" class="carousel slide"
     data-ride="carousel">
    <div class="carousel-inner">
        <div class="item active">
            <img src="${basePath}/lib/photos/photo_awl_index.jpg">
            <div class="carousel-caption">
                <h2>阿瓦隆</h2>
                <p>最好玩的狼人杀游戏</p>
            </div>
        </div>
    </div>
    <a class="left carousel-control" href="#carousel-next" data-slide="prev"><i class="icon-prev  fa fa-angle-left"></i></a>
    <a class="right carousel-control" href="#carousel-next" data-slide="next"><i class="icon-next fa fa-angle-right"></i></a>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <img src="${basePath}/lib/photos/photo_awl_index.jpg"
                     class="img-responsive">
            </div>
            <div class="col-md-6">
                <h1>阿瓦隆</h1>
                <h3>最好玩的狼人杀游戏</h3>
                <p>两方对抗性桌游，适合5-10人游戏。
                    <br>1、5人：梅林、派西维尔、忠臣 vs 莫甘娜、刺客
                    <br>2、6人：梅林、派西维尔、忠臣*2 vs 莫甘娜、刺客
                    <br>3、7人：梅林、派西维尔、忠臣*2 vs 莫甘娜、奥伯伦、刺客
                    <br>4、8人：梅林、派西维尔、忠臣*3 vs 莫甘娜、刺客、爪牙
                    <br>5、9人：梅林、派西维尔、忠臣*4 vs 莫德雷德、莫甘娜、刺客
                    <br>6、10人：梅林、派西维尔、忠臣*4 vs 莫德雷德、莫甘娜、奥伯伦、刺客</p>
                <div class="row">
                    <div class="col-md-6"></div>
                    <div class="col-md-6">
                        <a class="btn btn-primary" href="${basePath}/game_awl/index">进入</a>
                    </div>
                </div>
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
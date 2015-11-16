<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>管理员登录</title>
    <script type="text/javascript" src="./static/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="./static/js/jquery.validate.js"></script>
    <link rel="stylesheet" type="text/css" href="./static/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="./static/css/login.css"/>
    <style type="text/css">
        body{
            font-family: Microsoft YaHei, sans-serif;
        }
        label.error{
            color: red;
            font-size: 14px;
            font-weight: normal;
        }
    </style>
</head>
<body>
<div class="box">
    <div class="login-box">
        <div class="login-title text-center">
            <h1>
                <small>管理员登录</small>
            </h1>
        </div>
        <div class="login-content ">
            <div class="form">
                <form id="login">
                    <div class="form-group">
                        <div class="col-xs-12  ">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                                <input type="text" id="username" name="username" class="form-control" placeholder="用户名">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                <input type="password" id="password" name="password" class="form-control"
                                       placeholder="密码">
                            </div>
                        </div>
                    </div>
                    <div id="errorPlace" class="form-group">
                        <div class="col-xs-12">
                        </div>
                    </div>
                    <div class="form-group form-actions">
                        <div class="col-xs-4 col-xs-offset-4 ">
                            <a href="javascript:;" onclick="login()" id="loginBtn" class="btn btn-sm btn-info"><span
                                    class="glyphicon glyphicon-off"></span> 登录</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div style="text-align: center;margin-top:320px;">
            <span style="font-size: 13px;">
                Copyright © 2015-2015 茂欣管理 All Rights Reserved. 鄂ICP备15019898号-1
            </span>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(document).keypress(function(e){
        if(e.which == 13){
            $('#loginBtn').click();
        }
    });

    var loginValidator;
    $(document).ready(function(){
        loginValidator = $("#login").validate({
            errorPlacement : function(error,element){
                error.appendTo($("#errorPlace").find("div"));
            },
            rules : {
                username : {
                  required : true,
                },
                password : {
                    required : true,
                },
            },
            messages : {
                username : {
                    required : "*请填写用户名&nbsp;&nbsp;",
                },
                password : {
                    required : "*请填写密码&nbsp;&nbsp;",
                },
            }
        });
    });

    //将form表单的字符串转为json
    function string2json(str) {
        var res = {};
        var kvString = str.split("&");
        $.each(kvString, function (key, value) {
            var kv = value.split("=");
            res[kv[0]] = kv[1];
        })
        return JSON.stringify(res);
    }

    function login() {
        if(!loginValidator.form()) return;
        var params = decodeURIComponent($("#login").serialize());
        var data = string2json(params);
        var url = "./login";

        $.ajax({
            url: url,
            data: data,
            type: "post",
            contentType: "application/json"
        }).success(function (res) {
            if (res.isok) {
                window.location.href = "./home";
            } else {
                var label = document.createElement("label");
                label.setAttribute("class","error");
                label.innerHTML = "用户名或密码错误";
                $("#errorPlace").find("div").append(label);
            }
        }).error(function () {
            alert("登录失败，请联系管理员！");
        });
    }
</script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/11/4
  Time: 19:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <style>
        body {
            background: url("../../image/1.jpg") no-repeat;
            background-size: cover;
        }
    </style>
    <link rel="stylesheet" href="../../layui/css/layui.css">
</head>
<body>

<h1 style="color:#11aeff;margin-top:30px;text-align:center;">Welcome to pets！</h1>
<div style="margin:auto;margin-top:100px;width:450px;height:250px;background-color:#EEEEEE;">
    <div style="margin : auto;height: 200px;padding-top : 50px;text-align:center;">
        <div style="margin-left: 30px;">
            <div class="layui-form-item">
                <label class="layui-form-label">username：</label>
                <div class="layui-input-inline">
                    <input type="text" id="loginUserName" name="username" lay-verify="username" placeholder="Please input user name"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">password：</label>
                <div class="layui-input-inline">
                    <input type="password" id="loginPassWord" name="password" lay-verify="pass" placeholder=" Please enter your password"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>
        <div style="margin-top:30px;">
            <button class="layui-btn" id="login">login</button>
            <button class="layui-btn layui-btn-primary">register</button>
        </div>
    </div>
</div>

<%--下面是弹框所需元素--%>
<div id="registerWindow" style="display:none;">
    <div class="layui-form-item">
        <label class="layui-form-label">userName：</label>
        <div class="layui-input-inline">
            <input type="text" name="userName" lay-verify="userName" placeholder=" Please input user name"
                   autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">password：</label>
        <div class="layui-input-inline">
            <input type="password" name="password" lay-verify="pass" placeholder=" Please enter your password"
                   autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">firstName：</label>
        <div class="layui-input-inline">
            <input type="text" name="firstName" lay-verify="pass" placeholder=" Please input your firstName"
                   autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">lastName：</label>
        <div class="layui-input-inline">
            <input type="text" name="lastName" lay-verify="lastName" placeholder=" Please input your lastName"
                   autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">email</label>
            <div class="layui-input-inline">
                <input type="email" name="email" lay-verify="email" placeholder=" Please input your email"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">phone</label>
            <div class="layui-input-inline">
                <input type="tel" name="phone" lay-verify="required|phone" placeholder=" Please input your phone"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
</div>
<script src="../../js/jquery-1.11.3.js"></script>
<script src="../../layui/layui.all.js"></script>
<script>
    $(function () {
        //ajax失败统一处理
        $(document).ajaxError(function(jqXHR,textStatus,errorThrown){
            var error=JSON.parse(textStatus.responseText);
            if(textStatus.status == 500){
                layer.msg("服务器出现了错误，请重试");
            }else{
                layer.msg(error.message);
            }[7]
        });
        //注册弹框
        $(".layui-btn.layui-btn-primary").click(function () {
            var registerWindow = $("#registerWindow");
            layer.open({
                title: "register",
                content: registerWindow,
                btn: ['register', 'cancel'],
                //注册
                btn1: function () {
                    $.ajax({
                        url: "/user",
                        type: "post",
                        data: {
                            userName : $("#registerWindow input[name=userName]").val(),
                            firstName : $("#registerWindow input[name=firstName]").val(),
                            lastName : $("#registerWindow input[name=lastName]").val(),
                            email : $("#registerWindow input[name=email]").val(),
                            password : $("#registerWindow input[name=password]").val(),
                            phone : $("#registerWindow input[name=phone]").val()
                        },
                        dataType: "application/json;charset=utf-8",
                        success : function(data){
                            console.log(data);
                        }
                    });
                },
                end: function () {
                    $("body").append(registerWindow);
                    $("#registerWindow").hide();
                }
            })
        });
        //登录
        $("#login").click(function(){
            $.ajax({
                url:"/user/login",
                type:"get",
                dataType:"application/json;charset=utf-8",
                data:{
                    userName:$("#loginUserName").val(),
                    password:$("#loginPassWord").val()
                },
                success:function(data){
                    console.log(data);
                }
            });
        });
    });
</script>
</body>

</html>

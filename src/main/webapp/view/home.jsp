<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style type="text/css">
      .welcome{
        width: 800px;
        height: 200px;
        margin: 200px auto auto auto;
        text-align: center;
      }
      .w1{font-size: 54px;}
      .w2{font-size: 44px;}
      .w3{font-size: 34px;color: #aaa;}
    </style>
</head>
<body>
<div class="welcome">
  <span class="w1">欢迎使用茂欣薪资管理系统</span><br/>
  <span class="w2">当前登录用户:</span>
  <span class="w3">${username}</span>
</div>
</body>
</html>

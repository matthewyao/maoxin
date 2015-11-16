<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title></title>
    <style type="text/css">
      .form-control{
        display: inline;
        width: 300px;
      }
      #addmanager{
        width: 650px;
        margin: auto;
      }
      .td_title{
        width: 100px;
        text-align: right;
      }
      label.error{
        margin-left:12px;
        font-weight: normal;
      }
    </style>
</head>
<body>
<div class="row wrapper border-bottom white-bg page-heading">
  <div class="col-lg-12">
    <h3 class="bgtitle">添加系统管理员</h3>
  </div>
</div>
<div class="wrapper-content animated fadeInRight">
  <div class="col-lg-12">
    <div class="ibox float-e-margins">
      <div class="ibox-content">
        <form id="addManager">
          <table class="table table-hover table-striped table-bordered" style="height:280px;margin-bottom: 0;">
            <tr>
              <td class="td_title">用户名:</td>
              <td>
                <input id="username" name="username" type="text" class="form-control" placeholder="请输入用户名" />
              </td>
            </tr>
            <tr>
              <td class="td_title">对应员工:</td>
              <td>
                <select name="staffId" class="form-control">
                  <option></option>
                  <c:forEach items="${staffList}" var="staff">
                    <option value="${staff.id}">${staff.staffName}</option>
                  </c:forEach>
                </select>
              </td>
            </tr>
            <tr>
              <td class="td_title">密码:</td>
              <td>
                <input id="password" name="password" type="password" class="form-control" placeholder="请输入密码" />
              </td>
            </tr>
            <tr>
              <td class="td_title">确认密码:</td>
              <td>
                <input name="confirmPwd" type="password" class="form-control" placeholder="请确认密码" />
              </td>
            </tr>
            <tr>
              <td colspan="2" style="text-align: center">
                <a href="javascript:;" class="btn btn-primary" onclick="addManager()">添加管理员</a>
              </td>
            </tr>
          </table>
        </form>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">

  var managerValidator ;

  $(document).ready(function(){
    managerValidator = $("#addManager").validate({
      rules : {
        username : {
          maxlength : 18,
          required : true,
        },
        password : {
          required : true,
          minlength : 6,
          maxlength : 18,
        },
        confirmPwd : {
          required : true,
          minlength : 6,
          maxlength : 18,
          equalTo : "#password",
        },
        staffId : {
          required : true,
          minlength : 1,
        },
      },
      messages : {
        username : {
          required : " *请输入用户名",
          maxlength : $.validator.format("用户名长度不能多于{0}位"),
        },
        password : {
          required : " *请输入密码",
          minlength : $.validator.format("密码长度不能少于{0}位"),
          maxlength : $.validator.format("密码长度不能大于{0}位"),
        },
        confirmPwd : {
          required : " *请确认密码",
          minlength : $.validator.format("密码长度不能少于{0}位"),
          maxlength : $.validator.format("密码长度不能大于{0}位"),
          equalTo : "两次密码不一致",
        },
        staffId : {
          required : "*请选择对应员工",
          minlength : "请选择对应员工",
        },
      }
    });
  });

  function addManager(){
    if(!managerValidator.form()) return;
    var manager = new Object();
    var username = $("input[name='username']").val();
    var password = $("input[name='password']").val();
    var staffId = $("select[name='staffId']").val();
    manager.username = username;
    manager.password = password;
    manager.staffId = staffId;
    var data = JSON.stringify(manager);
    $.ajax({
      url : './add',
      type : 'post',
      contentType : 'application/json',
      data : data
    }).success(function(res){
      if(res.isok) {
        window.location.href = './all';
      }
      else {
        alert("用户名已存在！");
      }
    });
  }
</script>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
  <title></title>
  <style type="text/css">
    .form-control{
      display: inline;
      width: 300px;
    }
    #addCompany{
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
    <h3 class="bgtitle">
      <c:choose>
        <c:when test="${act eq 'new'}">添加分公司</c:when>
        <c:when test="${act eq 'modify'}">修改分公司信息</c:when>
      </c:choose>
    </h3>
  </div>
</div>
<div class="wrapper-content animated fadeInRight">
  <div class="col-lg-12">
    <div class="ibox float-e-margins">
      <div class="ibox-content">
        <form id="addCompany">
          <c:choose>
            <c:when test="${act eq 'new'}">
              <table class="table table-hover table-striped table-bordered" style="height:280px;margin-bottom: 0;">
                <tr>
                  <td class="td_title">分公司名称:</td>
                  <td>
                    <input name="companyName" type="text" class="form-control" placeholder="请输入分公司名" />
                  </td>
                </tr>
                <tr>
                  <td class="td_title">分公司责任人:</td>
                  <td>
                    <select name="inChargeName" class="form-control">
                      <option></option>
                      <c:forEach items="${managerList}" var="manager">
                        <option value="${manager.id}">${manager.staffName}  (${manager.idCardNo})</option>
                      </c:forEach>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td colspan="2" style="text-align: center">
                    <a href="javascript:;" class="btn btn-primary" onclick="addCompany(1)">创建分公司</a>
                  </td>
                </tr>
              </table>
            </c:when>
            <c:when test="${act eq 'modify'}">
              <input id="companyId" type="hidden" value="${company.id}"/>
              <table class="table table-hover table-striped table-bordered" style="height:280px;margin-bottom: 0;">
                <tr>
                  <td class="td_title">分公司名称:</td>
                  <td>
                    <input name="companyName" type="text" value="${company.companyName}" class="form-control" placeholder="请输入分公司名" />
                  </td>
                </tr>
                <tr>
                  <td class="td_title">分公司责任人:</td>
                  <td>
                    <select name="inChargeName" class="form-control">
                      <option></option>
                      <c:forEach items="${managerList}" var="manager">
                        <option <c:if test="${company.inChargeId == manager.id}">selected</c:if> value="${manager.id}">${manager.staffName}</option>
                      </c:forEach>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td colspan="2" style="text-align: center">
                    <a href="javascript:;" class="btn btn-primary" onclick="addCompany(2)">确认修改</a>
                  </td>
                </tr>
              </table>
            </c:when>
          </c:choose>
        </form>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">

  var companyValidator ;

  $(document).ready(function(){
    companyValidator = $("#addCompany").validate({
      rules : {
        companyName : {
          maxlength : 18,
          required : true,
        },
      },
      messages : {
        companyName : {
          required : " *请输入分公司名",
          maxlength : $.validator.format("公司名长度不能多于{0}位"),
        },
      }
    });
  });

  function addCompany(act){
    if(!companyValidator.form()) return;
    var company = new Object();
    var companyName = $("input[name='companyName']").val();
    var inChargeId = $("select[name='inChargeName']").val();
    company.companyName = companyName;
    company.inChargeId = inChargeId;
    var url,type,backUrl;
    if(act == 1){
      url = './add';
      type = 'post';
      backUrl = './all';
    } else {
      var companyId = $("#companyId").val();
      company.id = companyId;
      url = '../modify';
      type = 'put';
      backUrl = '../all';
    }
    var data = JSON.stringify(company);
    $.ajax({
      url : url,
      type : type,
      contentType : 'application/json',
      data : data
    }).success(function(res){
      if(res.isok) {
        window.location.href = backUrl;
      }
      else {
        alert("添加失败，请联系管理员");
      }
    });
  }
</script>
</body>
</html>

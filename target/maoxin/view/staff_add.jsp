<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
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
        #addStaff{
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
                <c:when test="${act eq 'new'}">添加员工</c:when>
                <c:when test="${act eq 'modify'}">修改员工信息</c:when>
            </c:choose>
        </h3>
    </div>
</div>
<div class="wrapper-content animated fadeInRight">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <form id="addStaff">
                    <c:choose>
                        <c:when test="${act eq 'new'}">
                            <table class="table table-hover table-striped table-bordered" style="height:280px;margin-bottom: 0;">
                                <tr>
                                    <td class="td_title">员工名:</td>
                                    <td>
                                        <input name="staffName" type="text" class="form-control" placeholder="请输入员工名" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_title">身份证号:</td>
                                    <td>
                                        <input name="idCardNo" type="text" class="form-control" placeholder="请输入身份证号" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_title">联系电话:</td>
                                    <td>
                                        <input name="tel" type="text" class="form-control" placeholder="请输入联系电话" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_title">推荐人:</td>
                                    <td>
                                        <select name="reCommendId" class="form-control">
                                            <option value="0">无推荐人</option>
                                            <c:forEach items="${staffList}" var="staff">
                                                <option value="${staff.id}">${staff.staffName}   (${staff.idCardNo})</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_title">所属分公司:</td>
                                    <td>
                                        <select name="companyId" class="form-control">
                                            <option></option>
                                            <c:forEach items="${companyList}" var="company">
                                                <option value="${company.id}">${company.companyName}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="text-align: center">
                                        <a href="javascript:;" class="btn btn-primary" onclick="addStaff(1)">添加员工</a>
                                    </td>
                                </tr>
                            </table>
                        </c:when>
                        <c:when test="${act eq 'modify'}">
                            <input id="staffId" type="hidden" value="${staff.id}"/>
                            <table class="table table-hover table-striped table-bordered" style="height:280px;margin-bottom: 0;">
                                <tr>
                                    <td class="td_title">员工名:</td>
                                    <td>
                                        <input name="staffName" value="${staff.staffName}" type="text" class="form-control" placeholder="请输入员工名" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_title">身份证号:</td>
                                    <td>
                                        <input name="idCardNo" value="${staff.idCardNo}" type="text" class="form-control" placeholder="请输入身份证号" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_title">联系电话:</td>
                                    <td>
                                        <input name="tel" value="${staff.tel}" type="text" class="form-control" placeholder="请输入联系电话" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_title">推荐人:</td>
                                    <td>
                                        <select name="reCommendId" class="form-control">
                                            <option <c:if test="${staff.reCommendId eq 0}">selected</c:if> value="0">无推荐人</option>
                                            <c:forEach items="${staffList}" var="s">
                                                <option <c:if test="${staff.reCommendId eq s.id}">selected</c:if> value="${s.id}">${s.staffName}   (${s.idCardNo})</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_title">所属分公司:</td>
                                    <td>
                                        <select name="companyId" class="form-control">
                                            <c:forEach items="${companyList}" var="company">
                                                <option <c:if test="${staff.companyId eq company.id}">selected</c:if> value="${company.id}">${company.companyName}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="text-align: center">
                                        <a href="javascript:;" class="btn btn-primary" onclick="addStaff(2)">修改信息</a>
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

    var staffValidator ;
    $(document).ready(function(){
        staffValidator = $("#addStaff").validate({
            rules : {
                staffName : {
                    maxlength : 18,
                    required : true,
                },
                idCardNo : {
                    required : true,
                    rangelength : [18,18],
                },
                tel : {
                    required : true,
                    maxlength : 18,
                },
                companyId : {
                    required : true,
                    minlength : 1,
                },
            },
            messages : {
                staffName : {
                    required : " *请输入员工名",
                    maxlength : $.validator.format("用户名长度不能多于{0}位"),
                },
                idCardNo : {
                    required : " *请输入身份证号",
                    rangelength : "请输入18位身份证号",
                },
                tel : {
                    required : " *请输入联系电话",
                    maxlength : $.validator.format("联系电话长度不能多于{0}位"),
                },
                companyId : {
                    required : "*请选择所属分公司",
                    minlength : "请选择所属分公司",
                },
            }
        });
    });

    function addStaff(act){
        if(!staffValidator.form()) return;
        var params = decodeURIComponent($("#addStaff").serialize());
        var data = string2json(params);
        var url,type,backUrl;
        if(act == 1){
            url = './add';
            type = 'post';
            backUrl = './all';
        } else {
            var dataJson = jQuery.parseJSON(data);
            var staffId = $("#staffId").val();
            dataJson['id'] = staffId;
            data = JSON.stringify(dataJson);
            url = '../modify';
            type = 'put';
            backUrl = '../all';
        }
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
                alert("员工已存在或身份证号重复！");
            }
        });
    }
</script>
</body>
</html>

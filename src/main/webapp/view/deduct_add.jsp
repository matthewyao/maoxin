<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title></title>
    <style type="text/css">
        .form-control {
            display: inline;
            width: 300px;
        }

        #addDeduct {
            width: 670px;
            margin: auto;
        }

        .td_title {
            width: 100px;
            text-align: right;
        }

        label.error {
            margin-left: 12px;
            font-weight: normal;
        }
    </style>
</head>
<body>
<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-12">
        <h3 class="bgtitle">
            <c:choose>
                <c:when test="${act eq 'new'}">添加提成</c:when>
                <c:when test="${act eq 'modify'}">修改提成</c:when>
            </c:choose>
        </h3>
    </div>
</div>
<div class="wrapper-content animated fadeInRight">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <form id="addDeduct">
                    <c:choose>
                        <c:when test="${act eq 'new'}">
                            <table class="table table-hover table-striped table-bordered"
                                   style="height:280px;margin-bottom: 0;">
                                <tr>
                                    <td class="td_title">提成人:</td>
                                    <td>
                                        <select name="staffId" class="form-control">
                                            <c:forEach items="${staffList}" var="staff">
                                                <option value="${staff.id}">${staff.staffName}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_title">提成金额:</td>
                                    <td>
                                        <input name="money" type="text" class="form-control" placeholder="请输入提成金额"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_title">提成时间:</td>
                                    <td>
                                        <input name="createTime" type="text" value="${curDate}" class="form-control"
                                               placeholder="请输入提成时间"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_title">备注:</td>
                                    <td>
                                        <input name="comment" type="textarea" class="form-control" placeholder="请输入备注"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="text-align: center">
                                        <a href="javascript:;" class="btn btn-primary" onclick="addStaff(1)">添加提成</a>
                                    </td>
                                </tr>
                            </table>
                        </c:when>
                        <c:when test="${act eq 'modify'}">
                            <input id="deductId" type="hidden" value="${deduct.id}"/>
                            <table class="table table-hover table-striped table-bordered"
                                   style="height:280px;margin-bottom: 0;">
                                <tr>
                                    <td class="td_title">提成人:</td>
                                    <td>
                                        <select name="staffId" class="form-control">
                                            <c:forEach items="${staffList}" var="staff">
                                                <option <c:if test="${deduct.staffId eq staff.id}">selected</c:if> value="${staff.id}">${staff.staffName}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_title">提成金额:</td>
                                    <td>
                                        <input name="money" type="text" value="${deduct.money}" class="form-control" placeholder="请输入提成金额"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_title">提成时间:</td>
                                    <td>
                                        <input name="createTime" type="text" value="${deduct.createTime}" class="form-control"
                                               placeholder="请输入提成时间"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_title">备注:</td>
                                    <td>
                                        <input name="comment" type="textarea" value="${deduct.comment}" class="form-control" placeholder="请输入备注"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="text-align: center">
                                        <a href="javascript:;" class="btn btn-primary" onclick="addStaff(2)">修改提成</a>
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

    //add money validator method
    jQuery.validator.addMethod("money", function(value, element) {
        var reg = /^([1-9]\d*(\.\d)?)$/;
        return reg.test(value);
    }, "请输入最多一位小数的提成金额");

    //add createTime validator method
    jQuery.validator.addMethod("createTime", function(value, element) {
        var reg = /^20\d\d-[0-1]\d-[0-3]\d$/;
        return reg.test(value);
    }, "请输入正确的日期格式，如2015-01-01");

    var deductValidator;
    $(document).ready(function () {
        deductValidator = $("#addDeduct").validate({
            rules: {
                money: {
                    money: true,
                    required: true,
                },
                createTime: {
                    required: true,
                    createTime: true,
                },
                staffId: {
                    required: true,
                }
            },
            messages: {
                money: {
                    money: "请输入最多一位小数的正数",
                    required: "请输入提成金额",
                },
                createTime: {
                    required: "请输入提成时间",
                    createTime: "请输入正确的日期格式，如2015-01-01",
                },
                staffId: {
                    required: "请选择提成人",
                }
            }
        });
    });
    function addStaff(act) {
    if(!deductValidator.form()) return;
        var params = decodeURIComponent($("#addDeduct").serialize());
        var data = string2json(params);
        var url, type, backUrl;
        if (act == 1) {
            url = './add';
            type = 'post';
            backUrl = './all';
        } else {
            var dataJson = jQuery.parseJSON(data);
            var duductId = $("#deductId").val();
            dataJson['id'] = duductId;
            data = JSON.stringify(dataJson);
            url = '../modify';
            type = 'put';
            backUrl = '../all';
        }
        $.ajax({
            url: url,
            type: type,
            contentType: 'application/json',
            data: data
        }).success(function (res) {
            if (res.isok) {
                window.location.href = backUrl;
            }
            else {
                alert("操作出现错误，请联系管理员！");
            }
        });
    }
</script>
</body>
</html>

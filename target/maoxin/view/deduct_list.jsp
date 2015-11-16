<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title></title>
    <style type="text/css">
        .control-bar{
            width: 100%;
        }
        .querybtn{
            width: 80px;
            text-align: center;
        }
        .newbtn,.querybtn{
            margin: auto;
        }
    </style>
</head>
<body>
<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-12">
        <h3 class="bgtitle">提成管理</h3>
    </div>
</div>
<div class="wrapper-content animated fadeInRight">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <form id="searchForm" action="${ctx}/deduct/search" method="post">
                    <table class="control-bar">
                        <tr>
                            <td width="*">
                                <a href="${ctx}/deduct/add" class="newbtn button-blue">添加提成</a>
                            </td>
                            <td width="10%" style="text-align: right;font-weight: bold;"><span style="margin-right: 20px">查询条件:</span></td>
                            <td width="220px">
                                <select name="companyId" class="form-control" style="width: 200px">
                                    <option <c:if test="${companyId eq 0}">selected</c:if> value="0">全公司</option>
                                    <c:forEach items="${companyList}" var="company">
                                        <option <c:if test="${companyId eq company.id}">selected</c:if> value="${company.id}">${company.companyName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td width="130px">
                                <select name="year" class="form-control" style="width: 110px">
                                    <option value="0">所有年份</option>
                                    <c:forEach var="y" begin="2015" end="2025">
                                        <option <c:if test="${year eq y}">selected</c:if> value="${y}">${y}年</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td width="130px" style="font-weight: normal">
                                <select name="month" class="form-control" style="width: 110px">
                                    <option value="0">所有月份</option>
                                    <c:forEach var="m" begin="1" end="12">
                                        <option <c:if test="${month eq m}">selected</c:if> value="${m}">${m}月</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td width="10%" style="font-weight: normal">
                                <input name="staffName" type="text" value="${staffName}" class="form-control" placeholder="输入员工名" />
                            </td>
                            <td width="100px" style="text-align: right">
                                <a href="javascript:;" class="querybtn button-blue" onclick="doSearch()">查询</a>
                            </td>
                        </tr>
                    </table>
                    <br/>
                </form>
                <table class="table table-hover table-striped table-bordered">
                    <thead>
                    <tr>
                        <th width="10%">提成人</th>
                        <th width="10%">身份证号</th>
                        <th width="10%">提成时间</th>
                        <th width="10%">提成金额</th>
                        <th width="6%">提成等级</th>
                        <th width="*">备注</th>
                        <th width="8%">操作人</th>
                        <th width="6%"><span><i class="glyphicon glyphicon-edit"></i></span></th>
                        <th width="6%"><span><i class="glyphicon glyphicon-trash"></i></span></th>
                    </tr>
                    </thead>
                    <c:forEach items="${deductList }" var="deduct">
                        <tr>
                            <td class="variable text-center">${deduct.staffName}</td>
                            <td class="variable text-center">${deduct.idCardNo}</td>
                            <td class="text-center">${deduct.createTime}</td>
                            <td class="text-center"><fmt:formatNumber value="${deduct.money}" pattern="#0.0"></fmt:formatNumber></td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${deduct.deduct_level eq 1}"><span style="color: #0000DD;">1级</span></c:when>
                                    <c:when test="${deduct.deduct_level eq 2}"><span style="color: #00DD00;">2级</span></c:when>
                                    <c:when test="${deduct.deduct_level eq 3}"><span style="color: #DD0000;">3级</span></c:when>
                                </c:choose>
                            </td>
                            <td class="variable text-center">${deduct.comment}</td>
                            <td class="text-center">${deduct.managerName}</td>
                            <td class="text-center op-edit"><a href="${ctx}/deduct/add/${deduct.id}" class="tbtn editbtn">修改</a></td>
                            <td class="text-center op-delete"><a href="javascript:;" onclick="deleteDeduct(${deduct.id})" class="tbtn deletebtn">删除</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    function doSearch(){
        var searchForm = $("#searchForm")[0];
        searchForm.submit();
    }

    function deleteDeduct(id){
        if(confirm("确认删除该提成?")){
            var url = './delete/' + id;
            $.ajax({
                url : url,
                type : 'delete',
            }).success(function(res){
                if(res.isok){
                    window.location.href = './all';
                } else {
                    alert("删除失败，请联系管理员");
                }
            });
        }
    }
</script>
</body>
</html>

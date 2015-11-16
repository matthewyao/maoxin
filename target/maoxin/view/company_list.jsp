<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title></title>
</head>
<body>
<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-12">
        <h3 class="bgtitle">分公司管理</h3>
    </div>
</div>
<div class="wrapper-content animated fadeInRight">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <a href="${ctx}/company/add" class="button-blue newbtn">添加分公司</a>
                <table class="table table-hover table-striped table-bordered">
                    <thead>
                    <tr>
                        <th width="*">分公司名称</th>
                        <th width="20%">分公司责任人</th>
                        <th width="20%">创建时间</th>
                        <th width="15%"><span><i class="glyphicon glyphicon-edit"></i></span></th>
                    </tr>
                    </thead>
                    <c:forEach items="${companyList }" var="company">
                        <tr>
                            <td class="text-center">${company.companyName}</td>
                            <td class="variable text-center">${company.inChargeName}</td>
                            <td class="variable text-center">${company.createTime}</td>
                            <td class="text-center op-edit"><a href="${ctx}/company/add/${company.id}" class="tbtn editbtn">修改</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>

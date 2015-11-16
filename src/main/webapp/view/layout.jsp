<%@ page import="com.maoxin.util.Constant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>茂欣薪资管理系统</title>
    <script type="text/javascript" src="${ctx}/static/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx}/static/css/animate.css">
    <link rel="stylesheet" href="${ctx}/static/css/style.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/layout.css">
    <sitemesh:write property="head"/>
</head>
<body>
<div class="wrapper">
    <div class="header">
        <h1 class="maoxintitle">茂欣薪资管理系统</h1>

        <div class="pinfo c_b">
            <a href="###" title="" class="name" style="margin-right: 20px;">
                <i class="glyphicon glyphicon-user"></i>&nbsp;<%= session.getAttribute("username") %>
            </a>
            <a href="${ctx }/logout" id="logout" title="">
                <i class="glyphicon glyphicon-log-out"></i>&nbsp;退出登录
            </a>
        </div>
    </div>
    <div class="container">
        <div class="nav">
            <ul>
                <%--<c:if test="${sessionScope.pageNavType == 'dspHome' }"> class="now" </c:if>--%>
                <li <c:if test="${sessionScope.pageNavType == 'company'}"> class="now" </c:if> >
                    <a href="${ctx}/company/all" title="" class="a1"><i></i> 分公司管理 </a>
                </li>
                <li <c:if test="${sessionScope.pageNavType == 'staff'}"> class="now" </c:if>>
                    <a href="${ctx}/staff/all" title="" class="a2"><i></i> 员工管理 </a>
                </li>
                <li <c:if test="${sessionScope.pageNavType == 'deduct'}"> class="now" </c:if>>
                    <a href="${ctx}/deduct/all" title="" class="a3"><i></i> 提成管理 </a>
                </li>
                <li <c:if test="${sessionScope.pageNavType == 'report'}"> class="now" </c:if>>
                    <a href="${ctx}/report/all" title="" class="a4"><i></i>报表中心</a>
                </li>
                <% if (session.getAttribute("username").toString().equals(Constant.SUPER_MANAGER_NAME)) {%>
                    <li <c:if test="${sessionScope.pageNavType == 'manage'}"> class="now" </c:if>>
                        <a href="${ctx}/manage/all" title="" class="a5"><i></i> 超级管理员 </a>
                    </li>
                <%}%>
            </ul>
        </div>
        <div class="main">
            <sitemesh:write property="body"/>
        </div>
    </div>
</div>
</body>
</html>

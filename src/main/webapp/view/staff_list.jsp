<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
        <h3 class="bgtitle">员工管理</h3>
    </div>
</div>
<div class="wrapper-content animated fadeInRight">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <form id="searchForm" action="${ctx}/staff/search" method="post">
                    <table class="control-bar">
                        <tr>
                            <td width="*">
                                <a href="${ctx}/staff/add" class="newbtn button-blue">添加员工</a>
                            </td>
                            <td width="10%" style="text-align: right;font-weight: bold;"><span style="margin-right: 20px">查询员工:</span></td>
                            <td width="220px">
                                <select name="companyId" class="form-control" style="width: 200px">
                                    <option <c:if test="${companyId eq 0}">selected</c:if> value="0">全公司</option>
                                    <c:forEach items="${companyList}" var="company">
                                        <option <c:if test="${companyId eq company.id}">selected</c:if> value="${company.id}">${company.companyName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td width="120px">
                                <input name="staffName" style="width: 100px" value="${staffName}" class="form-control" placeholder="员工姓名" />
                            </td>
                            <td width="10%" style="font-weight: normal">
                                <input <c:if test="${showDelete}">checked</c:if> name="showDelete" id="showDelete" type="checkbox" value="1">
                                <label for="showDelete" style="font-weight: normal">显示离职员工</label>
                            </td>
                            <td width="90px" style="text-align: right">
                                <a href="javascript:;" class="querybtn button-blue" onclick="doSearch()">查询</a>
                            </td>
                        </tr>
                    </table>
                </form>
                <br/>
                <table class="table table-hover table-striped table-bordered">
                    <thead>
                    <tr>
                        <th width="6%">员工姓名</th>
                        <th width="10%">身份证号</th>
                        <th width="8%">联系电话</th>
                        <th width="8%">推荐人</th>
                        <th width="*">分公司</th>
                        <th width="10%">入职时间</th>
                        <th width="8%">是否已离职</th>
                        <th width="10%">离职时间</th>
                        <th width="4%"><span><i class="glyphicon glyphicon-edit"></i></span></th>
                        <th width="4%"><span><i class="glyphicon glyphicon-trash"></i></span></th>
                    </tr>
                    </thead>
                    <tr style="display: none;">
                        <td class="text-center"></td>
                        <td class="variable text-center"></td>
                        <td class="text-center"></td>
                        <td class="text-center roletd"></td>
                        <td class="text-center op-edit"><a href="javascript:" class="tbtn editbtn">修改</a></td>
                        <td class="text-center op-delete"><a href="javascript:" class="tbtn deletebtn">删除</a></td>
                    </tr>
                    <c:forEach items="${staffList }" var="staff">
                        <tr>
                            <td class="text-center">${staff.staffName}</td>
                            <td class="variable text-center">${staff.idCardNo}</td>
                            <td class="variable text-center">${staff.tel}</td>
                            <td class="text-center">${staff.recommendName}</td>
                            <td class="text-center">${staff.companyName}</td>
                            <td class="text-center">${staff.createTime}</td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${staff.isDelete}">
                                        <span style="color: red;">已离职</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: green;">未离职</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center">${staff.leftTime}</td>
                            <td class="text-center op-edit"><a href="${ctx}/staff/add/${staff.id}" class="tbtn editbtn">修改</a></td>
                            <td class="text-center op-delete">
                                <c:choose>
                                    <c:when test="${staff.isDelete}">
                                        <a href="javascript:" onclick="turnover(${staff.id},0)" class="tbtn deletebtn">复职</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="javascript:" onclick="turnover(${staff.id},1)" class="tbtn deletebtn">离职</a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function turnover(id,status){
        var msg = status == 0 ? "确认复职该员工?" : "确认该员工已离职?";
        if(confirm(msg)){
            $.ajax({
                url : './status/'+id+"/"+status,
                type : 'put',
            }).success(function(res){
                if(res.isok){
                    window.location.href = './all';
                }else{
                    alert("该员工身份证号与现在职员工身份证号重复！");
                }
            });
        }
    }
    function doSearch(){
        var searchForm = $("#searchForm")[0];
        searchForm.submit();
    }
</script>
</body>
</html>

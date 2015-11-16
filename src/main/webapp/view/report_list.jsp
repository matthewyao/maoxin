<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title></title>
    <script type="text/javascript" src="../static/js/bootstrap-multiselect.js"></script>
    <link rel="stylesheet" href="../static/css/bootstrap-multiselect.css" type="text/css"/>
    <style type="text/css">
        .querybtn{
            width: 80px;
            text-align: center;
        }
        .newbtn,.querybtn{
            margin: auto;
        }
        .ibox .ibox-content{
            min-height: 500px;
        }
    </style>
</head>
<body>
<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-12">
        <h3 class="bgtitle">报表中心</h3>
    </div>
</div>
<div class="wrapper-content animated fadeInRight">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <form id="searchForm" action="${ctx}/report/search" method="post">
                    <table class="control-bar">
                        <tr>
                            <%--<td width="10%" style="text-align: right;font-weight: bold;"><span style="margin-right: 20px">查询条件:</span></td>--%>
                            <td >
                                <span style="margin:0 5px 0 0;font-weight: bold;">公司:</span>
                                <select name="companyId" multiple="multiple" class="multiselect">
                                    <%--<option <c:if test="${companyId eq 0}">selected</c:if> value="0">全公司</option>--%>
                                    <c:forEach items="${companyList}" var="company">
                                        <option value="${company.id}">${company.companyName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td >
                                <span style="margin:0 5px 0 20px;font-weight: bold;">年份:</span>
                                <select multiple="multiple" name="year" class="multiselect">
                                    <%--<option value="0">所有年份</option>--%>
                                    <c:forEach var="y" begin="2015" end="2025">
                                        <option <c:if test="${year eq y}">selected</c:if> value="${y}">${y}年</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td >
                                <span style="margin:0 5px 0 20px;font-weight: bold;">月份:</span>
                                <select multiple="multiple" name="month" class="multiselect">
                                    <%--<option value="0">所有月份</option>--%>
                                    <c:forEach var="m" begin="1" end="12">
                                        <option <c:if test="${month eq m}">selected</c:if> value="${m}">${m}月</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td width="140px" style="font-weight: normal;">
                                <input style="width: 100px;margin-left: 20px;" name="staffName" type="text" class="form-control" placeholder="输入员工名" />
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
                        <th width="15%">公司</th>
                        <th width="8%">提成人</th>
                        <th width="10%">身份证号</th>
                        <th width="8%">提成年份</th>
                        <th width="8%">提成月份</th>
                        <th width="8%">提成金额</th>
                    </tr>
                    </thead>
                    <c:forEach items="${reportList }" var="report">
                        <tr>
                            <td class="text-center">${report.companyName}</td>
                            <td class="text-center">${report.staffName}</td>
                            <td class="text-center">${report.idCardNo}</td>
                            <td class="text-center">${report.year}</td>
                            <td class="text-center">${report.month}</td>
                            <td class="text-center">
                                <span style="color:#F00">
                                    ${report.deduct}
                                    <%--<fmt:formatNumber value="${report.deduct}" pattern="#0.0"></fmt:formatNumber>--%>
                                </span>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    $(document).ready(function() {
        $('.multiselect').multiselect({
            buttonWidth: '160px',
            /*buttonText: function(options, select) {
                if (options.length == 0) {
                    if(select.attr('name') == 'companyId')
                        return '全公司';
                    if(select.attr('name') == 'year')
                        return '所有年份';
                    if(select.attr('name') == 'month')
                        return '所有月份';
                }
            },*/
        });
    });

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

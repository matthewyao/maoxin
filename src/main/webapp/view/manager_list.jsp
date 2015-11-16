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
        <h3 class="bgtitle">系统管理员管理</h3>
    </div>
</div>
<div class="wrapper-content animated fadeInRight">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <a href="${ctx}/manage/add" class="button-blue newbtn">添加系统管理员</a>
                <table class="table table-hover table-striped table-bordered">
                    <thead>
                    <tr>
                        <th width="15%">管理员用户名</th>
                        <th width="25%">管理员密码</th>
                        <th width="15%">创建时间</th>
                        <th width="15%">对应员工</th>
                        <%--<th width="20%">是否已删除</th>--%>
                        <%--<th width="15%"><span><i class="glyphicon glyphicon-edit"></i></span></th>--%>
                        <th width="15%"><span><i class="glyphicon glyphicon-trash"></i></span></th>
                    </tr>
                    </thead>
                    <tr style="display: none;">
                        <td class="text-center"></td>
                        <td class="variable text-center"></td>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                        <td class="text-center roletd"></td>
                        <%--<td class="text-center op-edit"><a href="javascript:" class="tbtn editbtn">修改</a></td>--%>
                        <td class="text-center op-delete"><a href="javascript:" class="tbtn deletebtn">删除</a></td>
                    </tr>
                    <c:forEach items="${managerList }" var="manager">
                        <tr>
                            <td class="text-center">${manager.username}</td>
                            <td class="variable text-center">${manager.password}</td>
                            <td class="variable text-center">${manager.createTime}</td>
                            <td class="variable text-center">${manager.staffName}</td>
                            <%--<td class="text-center op-edit"><a href="${ctx}/manage/update/${manager.id}" class="tbtn editbtn">修改</a></td>--%>
                            <td class="text-center op-delete"><a href="javascript:;" onclick="deleteManager(${manager.id})" class="tbtn deletebtn">删除</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function deleteManager(id){
        console.info("id:"+id);
        if(confirm("确定要删除该管理员吗?")){
            var url = './delete/' + id;
            $.ajax({
                url : url,
                type : 'delete'
            }).success(function(res){
                if(res.isok){
                    window.location.href = './all';
                } else {
                    alert("删除失败！");
                }
            });
        }
    }
</script>
</body>
</html>

<%-- 
    Document   : addGroupExpense
    Created on : Aug 9, 2023, 4:00:25 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>

<c:url value="/group/add" var="action"/>
<form:form method="post" action="${action}" modelAttribute="group" >
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <sec:authentication property="principal" var="loggedInUser" />
        <span>${loggedInUser.id}</span>
        <form:hidden path="ownerId" value="${loggedInUser.id}"/>    
    </c:if>

    <div class="wrapper wrapper--w680 addGroup">
        <div class="card card-4">
            <div class="card-body">
                <h1>Thêm nhóm thu chi</h1>
                <div class="input-group">
                    <label for="purpose" class="form-label">Tên nhóm: </label>
                    <form:input type="text" class="form-control" path="name" id="name" placeholder="Nhập tên nhóm ..." name="name"/>
                </div>

                <div class="input-group">
                    <label for="purpose" class="form-label">Mục đích thu chi: </label>
                    <form:input type="text" class="form-control" path="title" id="title" placeholder="Nhập mục đích ..." name="title"/>
                </div>

                <div class="input-group">
                    <label for="purpose" class="form-label">Mô tả chi tiết: </label>
                    <form:input type="text" class="form-control" path="description" id="description" placeholder="Mô tả chi tiết ..." name="description"/>
                </div>

                <div class="row row-space">
                    <div class="col-2">
                        <div class="input-group">
                            <label class="label">Ngày bắt đầu</label>
                            <form:input type="date" class="form-control" pattern="yyyy-MM-dd" path="startDate" />
                        </div>
                    </div>
                    <div class="col-2">
                        <div class="input-group">
                            <label class="label">Ngày kết thúc</label>
                            <form:input type="date" class="form-control" pattern="yyyy-MM-dd" path="endDate" />
                        </div>
                    </div>
                </div>

                <button class="btn btn-info mt-4">Thêm nhóm</button>

            </div>
        </div>
    </div>
</form:form>
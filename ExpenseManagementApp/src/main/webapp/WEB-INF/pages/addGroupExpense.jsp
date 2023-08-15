<%-- 
    Document   : addGroupExpense
    Created on : Aug 9, 2023, 4:00:25 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/addGroupExpense" var="action"/>
<form:form method="post" action="${action}" modelAttribute="transaction">
    <form:hidden path="userId" value="1"/>    

    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control" path="purpose" id="purpose" placeholder="Nhập mục đích ..." name="purpose"/>
        <label for="purpose">Mục đích: </label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control" path="description" id="description" placeholder="Nhập mô tả ..." name="description"/>
        <label for="description">Mô tả chi tiết (tùy chọn): </label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <form:input type="number" class="form-control" path="amount" id="amount" placeholder="Nhập số tiền ..." name="amount"/>
        <label for="amount">Số tiền: </label>
    </div>

    <div class="form-floating">
        <form:select class="form-select" id="type" name="type" path="typeId">
            <c:forEach items="${type}" var="t">
                <option value="${t.id}">${t.name}</option>
            </c:forEach>
        </form:select>
        <label for="type" class="form-label">Select list (select one):</label>
    </div>

    <button class="btn btn-info mt-4">Thêm sản phẩm</button>
</form:form>
<%-- 
    Document   : addTransaction
    Created on : Aug 8, 2023, 2:59:08 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>

<c:url value="/transactions/add" var="action"/>
<form:form method="post" action="${action}" modelAttribute="transaction">
    <form:hidden path="id"/>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <sec:authentication property="principal" var="loggedInUser" />
        <span>${loggedInUser.id}</span>
        <form:hidden path="userId" value="${loggedInUser.id}"/>    
    </c:if>

    <div class="wrapper wrapper--w680 addTransaction">
        <div class="card card-4">
            <div class="card-body">
                <h1>Quản lý thu chi</h1>
                <form:errors path="*" element="div" cssClass="text-danger"/>

                <div class="input-group">
                    <label for="purpose" class="form-label">Mục đích: </label>
                    <form:input  type="text" class="form-control" path="purpose" id="purpose" placeholder="Nhập mục đích ..." name="purpose"/>
                    <form:errors path="purpose" element="div" cssClass="text-danger"/>
                </div>
                <div class="input-group">
                    <label for="description" class="form-label">Mô tả chi tiết (tùy chọn): </label>
                    <form:input type="text" class="form-control" path="description" id="description" placeholder="Nhập mô tả ..." name="description"/>
                </div>


                <div class="input-group">
                    <label for="amount" class="form-label">Số tiền: </label>
                    <form:input oninput="limitNumberLength()"  type="number" class="form-control" path="amount" id="amountInput" placeholder="Nhập số tiền ..." name="amount"/>
                </div>

                <div class="input-group">
                    <label for="type" class="form-label">Select list (select one):</label>

                    <form:select class="form-select" id="type" name="type" path="typeId">
                        <c:forEach items="${type}" var="t">
                            <c:choose>
                                <c:when test="${t.id == transaction.typeId.id}">
                                    <option value="${t.id}" selected>${t.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${t.id}">${t.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>

                </div>


                <button class="btn btn-info mt-4" type="submit">
                    <c:choose>
                        <c:when test="${transaction.id == null}">Thêm thu chi</c:when>
                        <c:otherwise>Cập nhật</c:otherwise>
                    </c:choose>    
                </button>
            </div>
        </div>
    </div>


</form:form>

<script>
    function limitNumberLength() {
        var input = document.getElementById("amountInput");
        var maxLength = 10; // Số lượng ký tự tối đa bạn muốn cho phép
        if (input.value.length > maxLength) {
            input.value = input.value.slice(0, maxLength);
        }
    }
</script>
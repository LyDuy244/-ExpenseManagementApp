<%-- 
    Document   : addTransactionGroup
    Created on : Aug 19, 2023, 1:36:37 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>   

<c:url value="/group/details/add-transaction" var="action"/>
<form:form method="post" action="${action}" modelAttribute="groupTransaction">
    <form:hidden path="id"/>
    <form:hidden path="groupId" value="${gr.id}"/>
    <c:if test="${memberGroup != null}">
        <form:hidden path="groupMemberId" value="${memberGroup.id}"/>
    </c:if>

    <div class="wrapper wrapper--w680 addTransaction">
        <div class="card card-4">
            <div class="card-body">
                <h1>
                    <spring:message code="transaction.add.title"/>
                </h1>

                <c:if test="${msg != null}">
                    <div class="text-danger">${msg}</div>
                </c:if>

                <div class="input-group">
                    <label for="purpose" class="form-label">
                        <spring:message code="transaction.add.purpose"/>

                    </label>
                    <spring:message code="transaction.placeholder.purpose" var="placeholder_purpose"/>
                    <form:input type="text" class="form-control" path="purpose" id="purpose" placeholder="${placeholder_purpose}" name="purpose"/>
                    <form:errors path="purpose" element="div" cssClass="text-danger"/>

                </div>
                <div class="input-group">
                    <label for="description" class="form-label">
                        <spring:message code="transaction.add.desc"/>

                    </label>
                    <spring:message code="transaction.placeholder.desc" var="placeholder_desc"/>
                    <form:input type="text" class="form-control" path="description" id="description" placeholder="${placeholder_desc}" name="description"/>
                </div>

                <div class="input-group">
                    <label for="amount" class="form-label">
                        <spring:message code="transaction.add.date"/>

                    </label>
                    <form:input type="date" class="form-control" path="createdDate"/>
                    <form:errors path="createdDate" element="div" cssClass="text-danger"/>
                </div>

                <div class="input-group">
                    <label for="amount" class="form-label">
                        <spring:message code="transaction.add.amount"/>
                    </label>
                    <form:input oninput="limitNumberLength()" type="number" class="form-control" path="amount" id="amountInput" placeholder="Nhập số tiền ..." name="amount"/>
                    <form:errors path="amount" element="div" cssClass="text-danger"/>

                </div>

                <div class="input-group">
                    <label for="type" class="form-label">

                        <spring:message code="transaction.add.type"/>
                    </label>

                    <form:select class="form-select" id="type" name="type" path="typeId">
                        <c:forEach items="${type}" var="t">
                            <c:choose>
                                <c:when test="${t.id == groupTransaction.typeId.id}">
                                    <option value="${t.id}" selected>${t.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${t.id}">${t.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>

                </div>

                <c:choose>
                    <c:when test="${currentDate > gr.endDate}">
                        <button class="btn btn-danger disabled mt-4">
                            <spring:message code="groupTransaction.add.block"/>
                        </button>
                        <div class="text-danger">
                            <spring:message code="groupTransaction.add.block.desc"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-info mt-4" type="submit">
                            <c:choose>
                                <c:when test="${groupTransaction.id == null}">
                                    <spring:message code="transaction.add.btn.add"/>
                                </c:when>
                                <c:otherwise>
                                    <spring:message code="transaction.add.btn.update"/>
                                </c:otherwise>
                            </c:choose>    
                        </button>
                    </c:otherwise>
                </c:choose>

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
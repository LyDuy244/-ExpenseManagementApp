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
                <h1>Thêm giao dịch</h1>
                
                <c:if test="${msg != null}">
                    <div class="text-danger">${msg}</div>
                </c:if>

                <div class="input-group">
                    <label for="purpose" class="form-label">Mục đích: </label>
                    <form:input type="text" class="form-control" path="purpose" id="purpose" placeholder="Nhập mục đích ..." name="purpose"/>
                    <form:errors path="purpose" element="div" cssClass="text-danger"/>

                </div>
                <div class="input-group">
                    <label for="description" class="form-label">Mô tả chi tiết (tùy chọn): </label>
                    <form:input type="text" class="form-control" path="description" id="description" placeholder="Nhập mô tả ..." name="description"/>
                </div>


                <div class="input-group">
                    <label for="amount" class="form-label">Số tiền: </label>
                    <form:input oninput="limitNumberLength()" type="number" class="form-control" path="amount" id="amountInput" placeholder="Nhập số tiền ..." name="amount"/>

                    <c:if test="${errorMsg != null}">
                        <div class="text-danger">
                            ${errorMsg}
                        </div>
                    </c:if>

                </div>

                <div class="input-group">
                    <label for="type" class="form-label">Loại thu chi:</label>

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
                        <button class="btn btn-danger disabled mt-4">Khóa</button>
                        <div class="text-danger">Kế hoạch nhóm đã kết thúc (không thể thêm thu chi mới)</div>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-info mt-4">Thêm thu chi</button>
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
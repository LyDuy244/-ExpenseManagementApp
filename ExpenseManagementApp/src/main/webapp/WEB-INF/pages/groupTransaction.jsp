<%-- 
    Document   : groupTransaction
    Created on : Aug 20, 2023, 2:45:08 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>   

<sec:authentication property="principal" var="loggedInUser" />
<section class="mt-4">
    <c:if test="${msg != null}">
        <div class="text-danger">${msg}</div>
    </c:if>
    <div>
        <button onclick="showTransactionAccept()" class="btn btn-info btn_transaction-accept btn_transaction success">
            <spring:message code="groupTransaction.btn.transaction.accept"/>
        </button>
        <button onclick="showTransaction()" class="btn btn-info btn_transaction_user btn_transaction ">
            <spring:message code="groupTransaction.btn.transaction.noAccept.user"/>
        </button>

        <c:if test="${loggedInUser.id == gr.ownerId.id}">
            <button onclick="showTransactionNoAccept()" class="btn btn-info btn_transaction-noAccept btn_transaction ">
                <spring:message code="groupTransaction.btn.transaction.noAccept.leader"/>
            </button>
            <div class="text-danger mt-1">
                <spring:message code="groupTransaction.alert"/>
            </div>
        </c:if>

    </div>


    <div class="transaction__accept">
        <table class="mt-4 table table-striped  ">
            <thead>
                <tr>
                    <th><spring:message code="transaction.purpose"/></th>
                    <th><spring:message code="transaction.desc"/></th>                    
                    <th><spring:message code="transaction.amount"/></th>
                    <th><spring:message code="transaction.type"/></th>
                    <th><spring:message code="transaction.user"/></th>
                    <th><spring:message code="transaction.date"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${trGroupAccept}" var="trGroupAccept">
                    <tr>
                        <td>${trGroupAccept.purpose}</td>
                        <td>${trGroupAccept.description}</td>
                        <td class="formattedAmount">${trGroupAccept.amount}</td>
                        <td>${trGroupAccept.typeId.name}</td>
                        <td>${trGroupAccept.groupMemberId.userId.username}</td>
                        <td>${trGroupAccept.createdDate}</td>
                    </tr>
                </c:forEach>
            </tbody>

        </table>
        <c:if test="${counterAccept > 1}">
            <ul class="pagination mt-1">
                <c:url value="/group/details/${gr.id}/group-transaction" var="pageTransactionGroupExpense"/>
                <li class="page-item"><a class="page-link" href="${pageTransactionGroupExpense}"><spring:message code="base.btn.all"/></a></li>
                    <c:forEach begin="1" end="${counterAccept}" var="i">
                        <c:url value="/group/details/${gr.id}/group-transaction" var="pageUrl">
                            <c:param name="pageAccept" value="${i}"></c:param>
                        </c:url>
                    <li class="page-item"><a class="page-link" href="${pageUrl}">${i}</a></li>
                    </c:forEach>
            </ul>
        </c:if>
    </div>

    <div class="transaction hide">
        <table class="mt-4 table table-striped  ">
            <thead>
                <tr>
                    <th><spring:message code="transaction.purpose"/></th>
                    <th><spring:message code="transaction.desc"/></th>                    
                    <th><spring:message code="transaction.amount"/></th>
                    <th><spring:message code="transaction.type"/></th>
                    <th><spring:message code="transaction.user"/></th>
                    <th><spring:message code="transaction.date"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${trGroup}" var="trGroup">
                    <tr>
                        <td>${trGroup.purpose}</td>
                        <td>${trGroup.description}</td>
                        <td class="formattedAmount">${trGroup.amount}</td>
                        <td>${trGroup.typeId.name}</td>
                        <td>${trGroup.groupMemberId.userId.username}</td>
                        <td>${trGroup.createdDate}</td>
                        <td style="display: flex; justify-content: space-around;">
                            <c:choose>
                                <c:when test="${currentDate > gr.endDate}">
                                    <a href="<c:url value="/group/details/${gr.id}/update/${trGroup.id}" />" class="btn btn-danger disabled" ><spring:message code="transaction.btn.block"/></a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value="/group/details/${gr.id}/update/${trGroup.id}" />" class="btn btn-success" ><spring:message code="transaction.btn.update"/></a>
                                </c:otherwise>
                            </c:choose>
                            <c:url value="/api/group-transaction/${trGroup.id}" var="apiDel"/>
                            <button class="btn btn-danger" onclick="delTran('${apiDel}',${trGroup.id})"><spring:message code="transaction.btn.delete"/></button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>

        </table>
        <c:if test="${counterTran > 1}">
            <ul class="pagination mt-1">
                <c:url value="/group/details/${gr.id}/group-transaction" var="pageTransactionGroupExpense"/>
                <li class="page-item">
                    <a class="page-link" href="${pageTransactionGroupExpense}">
                        <spring:message code="base.btn.all"/>
                    </a>
                </li>
                <c:forEach begin="1" end="${counterTran}" var="i">
                    <c:url value="/group/details/${gr.id}/group-transaction" var="pageUrl">
                        <c:param name="page" value="${i}"></c:param>
                    </c:url>
                    <li class="page-item"><a class="page-link" href="${pageUrl}">${i}</a></li>
                    </c:forEach>
            </ul>
        </c:if>
    </div>


    <c:if test="${loggedInUser.id == gr.ownerId.id}">
        <div class="transaction__noAccept hide">
            <table class="mt-4 table table-striped ">
                <thead>
                    <tr>
                        <th><spring:message code="transaction.purpose"/></th>
                        <th><spring:message code="transaction.desc"/></th>                    
                        <th><spring:message code="transaction.amount"/></th>
                        <th><spring:message code="transaction.type"/></th>
                        <th><spring:message code="transaction.user"/></th>
                        <th><spring:message code="transaction.date"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${trGroupNoAccept}" var="trGroup">
                        <c:url value="/group/details/${gr.id}/group-transaction" var="action"/>
                        <form:form method="post" action="${action}" modelAttribute="groupTransaction">         
                            <form:hidden path="id" value="${trGroup.id}"/>  
                            <form:hidden path="id" value="${trGroup.groupId.id}"/>  

                            <tr>
                                <td>${trGroup.purpose}</td>
                                <td>${trGroup.description}</td>
                                <td class="formattedAmount">${trGroup.amount}</td>
                                <td>${trGroup.typeId.name}</td>
                                <td>${trGroup.groupMemberId.userId.username}</td>
                                <td>${trGroup.createdDate}</td>
                                <td style="display: flex; justify-content: space-around;">
                                    <c:choose>
                                        <c:when test="${currentDate > gr.endDate}">
                                            <button class="btn btn-danger" disabled><spring:message code="transaction.btn.block"/></button>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn btn-success" ><spring:message code="transaction.btn.confirm"/></button>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </form:form>

                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${counterNoAccept > 1}">
                <ul class="pagination mt-1">
                    <c:url value="/group/details/${gr.id}/group-transaction" var="pageTransactionGroupExpense"/>
                    <li class="page-item"><a class="page-link" href="${pageTransactionGroupExpense}">                            
                            <spring:message code="base.btn.all"/>
                        </a></li>
                        <c:forEach begin="1" end="${counterNoAccept}" var="i">
                            <c:url value="/group/details/${gr.id}/group-transaction" var="pageUrl">
                                <c:param name="pageNoAccept" value="${i}"></c:param>
                            </c:url>
                        <li class="page-item"><a class="page-link" href="${pageUrl}">${i}</a></li>
                        </c:forEach>
                </ul>
            </c:if>
        </div>
    </c:if>
</section>
<script src="<c:url value="/js/main.js"/>"></script>
<script>
                                const trNoAccept = document.querySelector(".btn_transaction-noAccept");
                                const tableNoAccept = document.querySelector(".transaction__noAccept");

                                const trUser = document.querySelector(".btn_transaction_user");
                                const tableTransaction = document.querySelector(".transaction");

                                const trAccept = document.querySelector(".btn_transaction-accept");
                                const tableAccept = document.querySelector(".transaction__accept");

                                const btnList = document.querySelectorAll(".btn_transaction");

                                // Lấy trạng thái đã lưu trong LocalStorage (nếu có)
                                const selectedButton = localStorage.getItem('selectedButton');

    <c:if test="${loggedInUser.id == gr.ownerId.id}">

                                function showTransactionNoAccept() {
                                    tableNoAccept.classList.remove("hide");
                                    tableAccept.classList.add("hide");
                                    tableTransaction.classList.add("hide");
                                    btnList.forEach(item => item.classList.remove("success"));
                                    trNoAccept.classList.add("success");

                                    // Lưu trạng thái nút đã chọn vào LocalStorage
                                    localStorage.setItem('selectedButton', 'noAccept');
                                }
    </c:if>

                                function showTransactionAccept() {
                                    if (tableNoAccept != null) {
                                        tableNoAccept.classList.add("hide");
                                    }
                                    tableTransaction.classList.add("hide");
                                    tableAccept.classList.remove("hide");
                                    btnList.forEach(item => item.classList.remove("success"));
                                    trAccept.classList.add("success");

                                    // Lưu trạng thái nút đã chọn vào LocalStorage
                                    localStorage.setItem('selectedButton', 'accept');
                                }

                                function showTransaction() {
                                    if (tableNoAccept != null) {
                                        tableNoAccept.classList.add("hide");
                                    }
                                    tableTransaction.classList.remove("hide");
                                    tableAccept.classList.add("hide");
                                    btnList.forEach(item => item.classList.remove("success"));
                                    trUser.classList.add("success");

                                    // Lưu trạng thái nút đã chọn vào LocalStorage
                                    localStorage.setItem('selectedButton', 'transaction');
                                }

                                // Khôi phục trạng thái nút sau khi tải lại trang
                                if (selectedButton === 'noAccept') {
    <c:if test="${loggedInUser.id == gr.ownerId.id}">

                                    showTransactionNoAccept();
    </c:if>
                                } else if (selectedButton === 'accept') {
                                    showTransactionAccept();
                                } else {
                                    showTransaction();
                                }

                                // Tạo một đối tượng Intl.NumberFormat để định dạng tiền tệ
                                var formatter = new Intl.NumberFormat('vi-VN', {
                                    style: 'currency',
                                    currency: 'VND'
                                });

                                var elements = document.getElementsByClassName('formattedAmount');
                                for (var i = 0; i < elements.length; i++) {
                                    var text = elements[i].textContent;
                                    var formattedAmount = formatter.format(text);
                                    elements[i].textContent = formattedAmount;
                                }
</script>
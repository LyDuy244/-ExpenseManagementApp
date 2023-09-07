<%-- 
    Document   : transactions
    Created on : Aug 8, 2023, 2:16:53 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>   

<div class="nav__transaction">

    <a class="btn btn-info" href="<c:url value="/transactions/add"/>">
        <spring:message code="transaction.btn.add"/>
    </a>

    <div class="dropdown">
        <div class="dropdown__select">
            <span class="dropdown__text">
                <spring:message code="transaction.text.amount.expense"/>
            </span>
            <i class="fa-solid fa-angle-down dropdown__caret"></i>
        </div>
        <ul class="dropdown__list">
            <li class="dropdown__item">
                <a class="dropdown__link" href="<c:url value="/transactions?all=true"/>">
                    <spring:message code="base.btn.all"/>
                </a>
            </li>
            <li class="dropdown__item">
                <a class="dropdown__link"  href="<c:url value="/transactions?ps=10" />">10</a>
            </li>
            <li class="dropdown__item">
                <a class="dropdown__link"  href="<c:url value="/transactions?ps=20" />">20</a>
            </li>
            <li class="dropdown__item">
                <a class="dropdown__link"  href="<c:url value="/transactions?ps=30" />">30</a>

            </li>
            <li class="dropdown__item">
                <a class="dropdown__link"  href="<c:url value="/transactions?ps=50" />">50</a>
            </li>
            <li class="dropdown__item">
                <a class="dropdown__link"  href="<c:url value="/transactions?ps=100" />">100</a>
            </li>
        </ul>
    </div>
</div>

<div class="mb-3">
    <c:url value="/transactions" var="action"/>
    <form class="d-flex" action="${action}">

        <select class="form-select me-2" name="typeId">
            <c:forEach items="${type}" var="t">
                <option value="${t.id}">${t.name}</option>
            </c:forEach>
        </select>

        <input class="form-control me-2" type="date" name="fromDate">
        <input class="form-control me-2" type="date" name="toDate">

        <input class="form-control me-2" type="text" name="kw" placeholder="<spring:message code="base.input.keyword"/>">
        <button class="btn btn-primary" type="submit"><spring:message code="base.text.find"/></button>
    </form>
</div>



<section class="container">
    <table class="table table-striped">
        <thead>
            <tr>
                <th><spring:message code="transaction.purpose"/></th>
                <th><spring:message code="transaction.desc"/></th>                    
                <th><spring:message code="transaction.amount"/></th>
                <th><spring:message code="transaction.type"/></th>
                <th><spring:message code="transaction.date"/></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${transactions}" var="transaction">
                <tr>
                    <td>${transaction.purpose}</td>
                    <td>${transaction.description}</td>
                    <td class="formattedAmount">${transaction.amount}</td>
                    <td>${transaction.typeId.name}</td>
                    <td>${transaction.createdDate}</td>
                    <td style="display: flex; justify-content: space-around;">
                        <c:url value="/api/transactions/${transaction.id}" var="apiDel"/>
                        <a href="<c:url value="/transactions/${transaction.id}" />" class="btn btn-success" ><spring:message code="transaction.btn.update"/></a>
                        <button class ="btn btn-danger" onclick="delTran('${apiDel}',${transaction.id})"><spring:message code="transaction.btn.delete"/></button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>


    <c:if test="${counter > 1}">
        <ul class="pagination mt-1">
            <c:url value="/transactions" var="pageTransaction"/>
            <li class="page-item"><a class="page-link" href="${pageTransaction}"><spring:message code="base.btn.all"/></a></li>
                <c:forEach begin="1" end="${counter}" var="i">
                    <c:url value="/transactions" var="pageUrl">
                        <c:param name="page" value="${i}"></c:param>
                    </c:url>
                <li class="page-item"><a class="page-link" href="${pageUrl}">${i}</a></li>
                </c:forEach>
        </ul>
    </c:if>

</section>

<script>
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
<script src="<c:url value="/js/main.js"/>"></script>

<%-- 
    Document   : groupExpense
    Created on : Aug 9, 2023, 3:50:21 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>   

<div class="wrapper wrapper--w960 group">

    <div class="card card-4">
        <div class="card-body">
            <h1>
                <spring:message code="groupExpense.title"/>
            </h1>
            <a class="btn btn-info mb-3" href="<c:url value="/group/add"/>">
                <spring:message code="groupExpense.btn"/>
            </a>
            <c:choose>
                <c:when test="${groups.size() == 0}">
                    <h1 class="text-info">
                        <spring:message code="groupExpense.noti.error"/>
                    </h1>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>
                                    <spring:message code="groupExpense.group.name"/>
                                </th>
                                <th>
                                    <spring:message code="groupExpense.group.leader"/>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${groups}" var="group">
                                <tr>
                                    <td>${group.name}</td>
                                    <td>${group.ownerId.firstName} ${group.ownerId.lastName}</td>
                                    <td>
                                        <a href="<c:url value="/group/details/${group.id}" />" class="btn btn-success" >
                                            <spring:message code="groupExpense.group.detail"/>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>

            <c:if test="${counter > 1}">
                <ul class="pagination mt-1">
                    <c:url value="/group" var="pageGroupExpense"/>
                    <li class="page-item">
                        <a class="page-link" href="${pageGroupExpense}">
                            <spring:message code="base.btn.all"/>
                        </a>
                    </li>
                    <c:forEach begin="1" end="${counter}" var="i">
                        <c:url value="/group" var="pageUrl">
                            <c:param name="page" value="${i}"></c:param>
                        </c:url>
                        <li class="page-item"><a class="page-link" href="${pageUrl}">${i}</a></li>
                        </c:forEach>
                </ul>
            </c:if>

        </div>
    </div>
</div>
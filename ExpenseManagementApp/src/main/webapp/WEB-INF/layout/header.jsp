<%-- 
    Document   : header
    Created on : Aug 8, 2023, 1:48:57 PM
    Author     : ADMIN
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>   
<sec:authentication property="principal" var="loggedInUser" />
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <div class="container-fluid">
        <c:url value="/" var="action"/>
        <a class="navbar-brand" href="${action}"><spring:message code="header.web.name"/></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>   
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="${action}"><spring:message code="header.home"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${action}"><spring:message code="header.about"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${action}"><spring:message code="header.career"/></a>
                </li>
                <c:if test="${pageContext.request.userPrincipal.name == null}">
                    <li class="nav-item" style="margin-left: auto">
                        <a class="nav-link" href="<c:url value="/login"/>"><spring:message code="header.login"/></a>
                    </li>

                </c:if>
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <c:if test="${loggedInUser.userRole != 'ROLE_ADMIN'}">
                        <c:url value="/transactions" var="transaction"/>
                        <li class="nav-item">
                            <a class="nav-link" href="${transaction}"><spring:message code="header.expense.management.personal"/></a>
                        </li>
                        <li class="nav-item">   
                            <a class="nav-link" href="<c:url value="/group"/>"><spring:message code="header.expense.management.group"/></a>
                        </li>
                        <li class="nav-item">   
                            <a class="nav-link" href="<c:url value="/stats/transaction"/>"><spring:message code="header.stats.personal"/></a>
                        </li>
                        <li class="nav-item">   
                            <a class="nav-link" href="<c:url value="/stats/group-transaction"/>"><spring:message code="header.stats.group"/></a>
                        </li>
                    </c:if>

                    <c:if test="${loggedInUser.userRole == 'ROLE_ADMIN'}">
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/manage-user"/>"><spring:message code="header.manage.user"/></a>
                        </li>
                    </c:if>


                    <li class="nav-item" style="margin-left: auto">
                        <a class="nav-link text-info" href="<c:url value="/user-details"/>">
                            ${loggedInUser.fullname}
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link text-danger" href="<c:url value="/logout"/>"><spring:message code="header.signout"/></a>
                    </li>


                </c:if>
            </ul>

        </div>
    </div>
</nav>
<div class="language">
    <div class="language-item">
        <a href="?lang=en" class="language-link">
            <div class="language-cover">
                <img src="https://moneylover.me/vi/img/en.svg" alt="alt"/>
            </div>
            English
        </a>
    </div>
    <div class="language-item">
        <a href="?lang=vi" class="language-link">
            <div class="language-cover">
                <img src="https://moneylover.me/vi/img/vi.svg" alt="alt"/>
            </div>
            Vietnamese
        </a> 
    </div>
</div>



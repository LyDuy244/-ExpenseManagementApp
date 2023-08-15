<%-- 
    Document   : header
    Created on : Aug 8, 2023, 1:48:57 PM
    Author     : ADMIN
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %> 
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <div class="container-fluid">
        <c:url value="/" var="action"/>
        <a class="navbar-brand" href="${action}">Expense Manage App</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#">Trang chủ</a>
                </li>

                <c:url value="/transactions" var="transaction"/>
                <li class="nav-item">
                    <a class="nav-link" href="${transaction}">Quản lý thu chi</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/group"/>">Quản lý nhóm thu chi</a>
                </li>

                <c:if test="${pageContext.request.userPrincipal.name == null}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/login"/>">Đăng nhâp</a>
                    </li>

                </c:if>
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/"/>">${pageContext.request.userPrincipal.name}</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/logout"/>">Đăng xuất</a>
                    </li>
                    
                </c:if>
            </ul>
            <form class="d-flex" action="${action}">
                <input class="form-control me-2" type="text" name="kw" placeholder="Nhập từ khóa...">
                <button class="btn btn-primary" type="subumit">Tìm</button>
            </form> 
        </div>
    </div>
</nav>


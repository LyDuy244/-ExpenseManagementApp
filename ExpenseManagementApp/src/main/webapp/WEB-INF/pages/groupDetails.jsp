<%-- 
    Document   : groupDetails
    Created on : Aug 16, 2023, 5:54:45 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>

<c:url value="/group/details" var="action"/>
<form class="d-flex" action="${action}">
    <input class="form-control me-2" type="text" name="username" placeholder="Nhập từ khóa...">
    <button class="btn btn-primary" type="subumit">Tìm</button>
</form> 

<c:choose>
    <c:when test="${user != null}">    
        <h1>${user.username}</h1>
    </c:when>
    <c:otherwise>
        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTVVzFIs00C1WVmivQSlqsGgRu2ouRc4slMmQ&usqp=CAU" style="width: 100px; height: 100px; margin: 50px auto" alt="alt"/>
    </c:otherwise>
</c:choose>   
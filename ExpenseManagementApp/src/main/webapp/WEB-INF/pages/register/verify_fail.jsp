<%-- 
    Document   : register_success
    Created on : Aug 27, 2023, 10:43:10 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>   

<div class="container">
    <div class="text-center mt-5">
        <h2 >
            <spring:message code="verify.fail.title"/>
           
        </h2>
        <h4 class="text-danger">
            <spring:message code="verify.fail.desc"/>
           
        </h4>
    </div>
</div>  
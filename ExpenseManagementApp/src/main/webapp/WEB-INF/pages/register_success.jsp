<%-- 
    Document   : register_success
    Created on : Aug 27, 2023, 10:43:10 AM
    Author     : ADMIN
--%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>   

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container">
    <div class="text-center mt-5">
        <h2 class="text-success">
             <spring:message code="register.success.title"/>
        </h2>
        <h4 class="text-success">
             <spring:message code="register.success.desc"/>
        </h4>
        <p>
             <spring:message code="register.success.activated"/>
        </p>
    </div>
</div>
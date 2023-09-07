<%-- 
    Document   : index
    Created on : Jul 29, 2023, 3:04:38 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>   


<c:if test="${msg != null}">
    <div class="text-danger">${msg}</div>
</c:if>

<h1 class="home-title"><spring:message code="home.title.1"/> <br> <span><spring:message code="home.title.span"/></span> <spring:message code="home.title.2"/></h1>

<div class="home-info">
    <div class="home-info-item">
        <div class="home-info-cover">
            <img src="https://moneylover.me/vi/img/introfeature/1.svg" alt="alt"/>
        </div>
        <h4><spring:message code="home.info.title.1"/></h4>
    </div>

    <div class="home-info-item">
        <div class="home-info-cover">
            <img src="https://moneylover.me/vi/img/introfeature/2.svg" alt="alt"/>
        </div>
        <h4><spring:message code="home.info.title.2"/></h4>
    </div>

    <div class="home-info-item">
        <div class="home-info-cover">
            <img src="https://moneylover.me/vi/img/introfeature/3.svg" alt="alt"/>
        </div>
        <h4><spring:message code="home.info.title.3"/></h4>
    </div>

    <div class="home-info-item">
        <div class="home-info-cover">
            <img src="https://moneylover.me/vi/img/introfeature/4.svg" alt="alt"/>
        </div>
        <h4><spring:message code="home.info.title.4"/></h4>
    </div>
</div>

<div class="feature">
    <div class="feature-cover">
        <img src="https://moneylover.me/vi/img/details/Transaction@4x.png" alt="alt"/>
    </div>
    <div class="feature-content">
        <h5 class="feature-title"><spring:message code="home.feature.title.1"/></h5>
        <p class="feature-desc"><spring:message code="home.feature.desc.1"/></p>
    </div>
</div>

<div class="feature reverse">
    <div class="feature-cover">
        <img src="https://moneylover.me/vi/img/details/budget@4x.png" alt="alt"/>
    </div>
    <div class="feature-content">
        <h5 class="feature-title"><spring:message code="home.feature.title.2"/></h5>
        <p class="feature-desc"><spring:message code="home.feature.desc.2"/></p>
    </div>
</div>

<div class="feature">
    <div class="feature-cover">
        <img src="https://moneylover.me/vi/img/details/REPORT@4x.png" alt="alt"/>
    </div>
    <div class="feature-content">
        <h5 class="feature-title"><spring:message code="home.feature.title.3"/></h5>
        <p class="feature-desc"><spring:message code="home.feature.desc.3"/></p>
    </div>
</div>

<div class="feedback-user">
    <h4 class="feedback-title"><spring:message code="home.feedback.title"/></h4>
    <p class="feedback-rate">
        <span>
            <i class="fa-sharp fa-solid fa-star feedback-icon"></i>
            <i class="fa-sharp fa-solid fa-star feedback-icon"></i>
            <i class="fa-sharp fa-solid fa-star feedback-icon"></i>
            <i class="fa-sharp fa-solid fa-star feedback-icon"></i>
            <i class="fa-sharp fa-solid fa-star feedback-icon"></i>
        </span>
        <span class="feedback-count">4.6</span>
        <spring:message code="home.feedback.byUser"/>
        Đánh giá từ người dùng
    </p>
    <div class="feedback-list">
        <div class="feedback-item">
            <p class="feedback-comment">
                <spring:message code="home.feedback.comment.1"/>
            </p>
            <h5 class="feedback-author">                
                <spring:message code="home.feedback.author.1"/>
            </h5>
        </div>

        <div class="feedback-item">
            <p class="feedback-comment">
                <spring:message code="home.feedback.comment.2"/>
            </p>
            <h5 class="feedback-author">
                <spring:message code="home.feedback.author.2"/>
            </h5>
        </div>

        <div class="feedback-item">
            <p class="feedback-comment">
                <spring:message code="home.feedback.comment.3"/>
            </p>
            <h5 class="feedback-author">
                <spring:message code="home.feedback.author.3"/>
            </h5>
        </div>  
    </div>
</div>


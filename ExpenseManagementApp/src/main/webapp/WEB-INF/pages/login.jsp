<%-- 
    Document   : login
    Created on : Aug 9, 2023, 12:21:44 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/login" var="action"/>
<c:if test="${param.error!= null}">
    <div class="alert alert-danger">
        Da co loi xay ra vui long quay lai sau  
    </div>
</c:if>

<c:if test="${param.accessDenied != null}">
    <div class="alert alert-danger">
        Bạn không có quyền truy cập
    </div>
</c:if>

<form action="${action}" method="POST">
    <div class="signin">
        <div class="signin__content">
            <h2 class="signin__heading">Đăng nhập</h2>
            <h3 class="signin__caption">Đăng nhập với</h3>
            <div class="signin-social">
                <div class="signin-social__item">
                    <i class="fa-brands fa-google signin-social__icon"></i>
                    <span class="signin-social__text">Đăng nhập với Google</span>
                </div>
                <div class="signin-social__item">
                    <i class="fab fa-facebook signin-social__icon"></i>
                    <span class="signin-social__text">Đăng nhập với Facebook</span>
                </div>
            </div>

            <form action="" class="signin-form">
                <div class="signin-form__group">
                    <label for="username" class="signin-form__label">Tên đăng nhập</label>
                    <input type="text" name="username" id="username" class="signin-form__input">
                </div>
                <div class="signin-form__group">
                    <label for="password" class="signin-form__label">Mật khẩu</label>
                    <input type="password" name="password" id="password" class="signin-form__input">
                </div>
                <button class="signin-form__submit" type="submit">
                    <i class="fa fa-long-arrow-right"></i>
                </button>
            </form>

            <p class="register">Bạn chưa có tài khoản? <a href="<c:url value="/register"/>">Đăng ký</a></p>
        </div>
        <div class="signin__image">
            <img  src="<c:url value='/img/rocket.jpg'/>" alt="">
        </div>
    </div>
</form>


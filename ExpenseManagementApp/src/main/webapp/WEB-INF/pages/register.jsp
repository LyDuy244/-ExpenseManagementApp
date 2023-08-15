<%-- 
    Document   : register
    Created on : Aug 10, 2023, 3:15:14 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${errorMsg != null}">
    <div class="alert alert-danger">
        ${errorMsg}
    </div>
</c:if>
<c:url value="/register" var="action"/>
<form:form method="post" action="${action}" modelAttribute="user" enctype="multipart/form-data">
    <form:hidden path="id" />
    <div class="wrapper wrapper--w680">
        <div class="card card-4">
            <div class="card-body">
                <h2 class="title">Đăng ký</h2>
                
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Họ và tên lót</label>
                                <form:input type="text" class="form-control" path="firstName" />
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Tên</label>
                                <form:input type="text" class="form-control" path="lastName" />

                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Ngày sinh nhật</label>
                                <div class="input-group-icon">
                                    <form:input type="date" class="form-control" pattern="yyyy-MM-dd" placeholder="Enter Date of birth" path="birthday" />
                                    <i class="zmdi zmdi-calendar-note input-icon js-btn-calendar"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Giới tính</label>
                                <div class="p-t-10">
                                    <label class="radio-container m-r-45">Nam
                                        <form:radiobutton path="gender" value="0"/>
                                        <span class="checkmark"></span>
                                    </label>
                                    <label class="radio-container">Nữ
                                        <form:radiobutton path="gender" value="1"/>
                                        <span class="checkmark"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Email</label>
                                <form:input type="email" class="form-control" path="email" />
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Kiểu khách hàng</label>
                                <div class="rs-select2 js-select-simple select--no-search">
                                    <form:select name="subject" path="userRole">
                                        <option value="ROLE_USER" disabled="disabled" selected="selected">Người dùng</option>
                                        <option value="ROLE_BUSINESS">Doanh nghiệp</option>
                                        <option value="ROLE_REPRESENTATIVE">Đại diện nhóm</option>
                                    </form:select>
                                    <div class="select-dropdown"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label" for="username">Tên đăng nhập</label>
                                <form:input type="text" class="form-control" path="username" id="username" />
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label" for="password">Mật khẩu</label>
                                <form:input type="password" class="form-control" path="password" id="password"/>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label" for="confirmPassword">Nhập lại mật khẩu</label>
                                <form:input type="password" class="form-control" path="confirmPassword" id="confirmPassword"/>
                            </div>
                        </div>
                    </div>

                    <div class="input-group">
                        <label class="label" for="file">Avartar</label>
                        <form:input type="file" class="form-control" path="file" id="file" />
                    </div>

                    <div class="p-t-15">
                        <button class="btn btn--radius-2 btn--blue" type="submit">Submit</button>
                    </div>
                
            </div>
        </div>
    </div>
</form:form>







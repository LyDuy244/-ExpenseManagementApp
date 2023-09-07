<%-- 
    Document   : userDetails
    Created on : Aug 21, 2023, 4:14:34 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<sec:authentication property="principal" var="loggedInUser" />
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>   

<c:url value="/user-details" var="action"/>
<form:form method="post" action="${action}" modelAttribute="user" enctype="multipart/form-data">
    <form:hidden path="id"/>
    <form:hidden path="avartar"/>


    <div class="rounded bg-white mt-5 wrapper wrapper--w960" >
        <div class="profile">
            <div class="col-md-4 border-right">
                <div class="d-flex flex-column align-items-center text-center p-3 py-5" >

                    <div class="profile-cover">
                        <img id="avatarImage" class="rounded-circle mt-5" src="${user.avartar}" style="width: 120px; height: 120px;">
                        <div class="file">
                            <form:input type="file" id="file__input" class="file__input" path="file" accept=".png, .jpg, .jpeg"/>
                            <label for="file__input" class="file__label">
                                <i class="fa-solid fa-camera"></i>
                            </label>
                        </div>
                    </div>


                    <span class="font-weight-bold mt-3">${user.firstName} ${user.lastName}</span>
                    <span class="text-black-50 mt-1">${user.email}</span>
                </div>
            </div>
            <div class="col-md-8">
                <div class="p-3 py-5">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <a class="d-flex flex-row align-items-center back" href="<c:url value="/"/>">
                            <i class="fa fa-long-arrow-left mr-1 mb-1 back-icon"></i>
                            <h6>                            
                                <spring:message code="user.detail.btn.back"/>
                            </h6>
                        </a>
                        <h6 class="text-right">                
                            <spring:message code="user.detail.title"/>
                        </h6>
                    </div>  


                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label" for="firstName">
                                    <spring:message code="user.detail.firstName"/>
                                </label>
                                <form:input type="text" class="form-control" id="firstName" path="firstName"/>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label" for="lastName">
                                    <spring:message code="user.detail.lastName"/>
                                </label>
                                <form:input type="text" class="form-control" id="lastName" path="lastName"/>
                            </div>
                        </div>
                    </div>

                    <div class="input-group">
                        <label class="label">
                            <spring:message code="user.detail.gender"/>
                        </label>
                        <div class="p-t-10">
                            <label class="radio-container m-r-45">
                                <spring:message code="user.detail.gender.male"/>
                                <form:radiobutton path="gender" value="0"/>
                                <span class="checkmark"></span>
                            </label>
                            <label class="radio-container">
                                <spring:message code="user.detail.gender.female"/>
                                <form:radiobutton path="gender" value="1"/>
                                <span class="checkmark"></span>
                            </label>
                        </div>
                    </div>

                    <div class="input-group">
                        <label class="label" for="email">
                            Email
                        </label>
                        <form:input type="email" class="form-control" placeholder="Email" path="email" readonly="true" />
                    </div>
                    <div class="input-group">
                        <label class="label" for="birthday">
                            <spring:message code="user.detail.birthday"/>
                        </label>
                        <form:input type="date" class="form-control" pattern="yyyy-MM-dd" path="birthday" />
                    </div>
                    <button class="btn btn-primary profile-button" type="submit">
                        <spring:message code="user.detail.btn.submit"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</form:form>
<script>
    const fileInput = document.getElementById("file__input");
    const avatarImage = document.getElementById("avatarImage");

    fileInput.addEventListener("change", function (event) {
        const selectedFile = event.target.files[0];

        if (selectedFile) {
            const imageURL = URL.createObjectURL(selectedFile);
            avatarImage.src = imageURL;
        }
    });
</script>
<%-- 
    Document   : addGroupExpense
    Created on : Aug 9, 2023, 4:00:25 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>   


<c:url value="/group/add" var="action"/>
<form:form method="post" action="${action}" modelAttribute="group" >
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <sec:authentication property="principal" var="loggedInUser" />
        <form:hidden path="ownerId" value="${loggedInUser.id}"/>    
    </c:if>

    <div class="wrapper wrapper--w680 addGroup">
        <div class="card card-4">
            <div class="card-body">
                <h1>
                    <spring:message code="groupExpense.add.title"/>
                </h1>
                <div class="input-group">
                    <label for="purpose" class="form-label">
                        <spring:message code="groupExpense.add.name"/>
                    </label>
                    <spring:message code="groupExpense.placeholder.name" var="placeholder_name"/>
                    <form:input type="text" class="form-control" path="name" id="name" placeholder="${placeholder_name}" name="name"/>
                    <form:errors path="name" element="div" cssClass="text-danger"/>
                </div>

                <div class="input-group">
                    <label for="purpose" class="form-label">
                        <spring:message code="groupExpense.add.purpose"/>
                    </label>
                    <spring:message code="groupExpense.placeholder.purpose" var="placeholder_purpose"/>
                    <form:input type="text" class="form-control" path="title" id="title" placeholder="${placeholder_purpose}" name="title"/>
                    <form:errors path="title" element="div" cssClass="text-danger"/>
                </div>

                <div class="input-group">
                    <label for="purpose" class="form-label">
                        <spring:message code="groupExpense.add.desc"/>
                    </label>
                    <spring:message code="groupExpense.placeholder.desc" var="placeholder_desc"/>
                    <form:input type="text" class="form-control" path="description" id="description" placeholder="${placeholder_desc}" name="description"/>
                </div>

                <div class="row row-space">
                    <div class="col-2">
                        <div class="input-group">
                            <label class="label">
                                <spring:message code="groupExpense.add.startDate"/>
                            </label>
                            <form:input type="date" class="form-control" pattern="yyyy-MM-dd" path="startDate" />
                            <form:errors path="startDate" element="div" cssClass="text-danger"/>

                        </div>
                    </div>
                    <div class="col-2">
                        <div class="input-group">
                            <label class="label">
                                <spring:message code="groupExpense.add.endDate"/>
                            </label>
                            <form:input type="date" class="form-control" pattern="yyyy-MM-dd" path="endDate" />
                            <form:errors path="endDate" element="div" cssClass="text-danger"/>

                        </div>
                    </div>
                </div>

                <button class="btn btn-info mt-4">
                    <spring:message code="groupExpense.add.btn"/>
                </button>

            </div>
        </div>
    </div>
</form:form>
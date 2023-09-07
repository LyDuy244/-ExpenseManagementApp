<%-- 
    Document   : manageUser
    Created on : Aug 28, 2023, 10:52:09 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>   


<section class="container mt-5">

    <div class="mb-3">
        <c:url value="/manage-user" var="action"/>
        <form class="d-flex" action="${action}">
            <select class="select-dropdown me-2" name="userRole" style="width: 200px; margin-right: 10px; padding: 0 10px;">
                <option value="ROLE_USER">                                
                    <spring:message code="manage.user.role.user"/>
                </option>
                <option value="ROLE_BUSINESS">
                    <spring:message code="manage.user.role.business"/>
                </option>
                <option value="ROLE_REPRESENTATIVE">
                    <spring:message code="manage.user.role.representative"/>
                </option>
            </select>
            <input class="form-control me-2" type="text" name="kw" style="width: 300px" placeholder="<spring:message code="base.input.keyword"/>">
            <button class="btn btn-primary" type="submit"><spring:message code="base.text.find"/></button>
        </form>
    </div>

    <table class="table table-striped">
        <thead> 
            <tr>
                <th>
                    <spring:message code="user.detail.firstName"/>
                </th>
                <th>
                    <spring:message code="user.detail.lastName"/>
                </th>                    
                <th>Username</th>
                <th>Email</th>
                <th>
                    <spring:message code="user.detail.gender"/>
                </th>
                <th>
                    <spring:message code="user.detail.birthday"/>
                </th>
                <th>User Role</th>
                <th>Active</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user">
                <form:form method="post" action="${action}" modelAttribute="u">
                    <form:hidden path="id" value="${user.id}"/>
                    <tr>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.gender == false}"><spring:message code="user.detail.gender.male"/></c:when>
                                <c:otherwise><spring:message code="user.detail.gender.female"/></c:otherwise>
                            </c:choose>
                        </td>
                        <td>${user.birthday}</td>
                        <td>${user.userRole}</td>
                        <td>${user.isActive}</td>

                        <td style="display: flex; justify-content: space-around;">
                            <c:choose>
                                <c:when test="${user.isActive == false}">
                                    <button class ="btn btn-success" style="width: 100%">
                                        <spring:message code="manage.user.btn.unblock"/>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button class ="btn btn-danger" style="width: 100%">
                                        <spring:message code="manage.user.btn.block"/>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </form:form>

                </tr>
            </c:forEach>
        </tbody>
    </table>


    <c:if test="${counter > 1}">
        <ul class="pagination mt-1">
            <c:url value="/manage-user" var="pageManageUser"/>
            <li class="page-item"><a class="page-link" href="${pageManageUser}"><spring:message code="base.btn.all"/></a></li>
                <c:forEach begin="1" end="${counter}" var="i">
                    <c:url value="/manage-user" var="pageUrl">
                        <c:param name="page" value="${i}"></c:param>
                    </c:url>
                <li class="page-item"><a class="page-link" href="${pageUrl}">${i}</a></li>
                </c:forEach>
        </ul>
    </c:if>

</section>
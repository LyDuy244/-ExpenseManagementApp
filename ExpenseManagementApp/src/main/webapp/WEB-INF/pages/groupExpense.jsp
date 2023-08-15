<%-- 
    Document   : groupExpense
    Created on : Aug 9, 2023, 3:50:21 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>Quản lý nhóm thu chi</h1>

<section class="container">
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Id</th>
                <th>Tên nhóm</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${group}" var="group">
                <tr>
                    <td>${group.id}</td>                    
                    <td>${group.name}</td>
                    <td>
                        <a href="#" class="btn btn-success">Xem chi tiết</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</section>

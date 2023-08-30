<%-- 
    Document   : transactions
    Created on : Aug 8, 2023, 2:16:53 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="nav__transaction">


    <a class="btn btn-info" href="<c:url value="/transactions/add"/>">Thêm thu chi</a>

    <div class="dropdown">
        <div class="dropdown__select">
            <span class="dropdown__text">Số lượng thu chi</span>
            <i class="fa-solid fa-angle-down dropdown__caret"></i>
        </div>
        <ul class="dropdown__list">
            <li class="dropdown__item">
                <a class="dropdown__link" href="${pageTransaction}">Tất cả</a>
            </li>
            <li class="dropdown__item">
                <a class="dropdown__link"  href="<c:url value="/transactions?ps=10" />">10</a>
            </li>
            <li class="dropdown__item">
                <a class="dropdown__link"  href="<c:url value="/transactions?ps=20" />">20</a>
            </li>
            <li class="dropdown__item">
                <a class="dropdown__link"  href="<c:url value="/transactions?ps=30" />">30</a>

            </li>
            <li class="dropdown__item">
                <a class="dropdown__link"  href="<c:url value="/transactions?ps=50" />">50</a>
            </li>
            <li class="dropdown__item">
                <a class="dropdown__link"  href="<c:url value="/transactions?ps=100" />">100</a>
            </li>
        </ul>
    </div>
</div>

<div class="mb-3">
    <c:url value="/transactions" var="action"/>
    <form class="d-flex" action="${action}">

        <select class="form-select me-2" name="typeId">
            <c:forEach items="${type}" var="t">
                <option value="${t.id}">${t.name}</option>
            </c:forEach>
        </select>

        <input class="form-control me-2" type="date" name="fromDate">
        <input class="form-control me-2" type="date" name="toDate">

        <input class="form-control me-2" type="text" name="kw" placeholder="Nhập từ khóa...">
        <button class="btn btn-primary" type="submit">Tìm</button>
    </form>
</div>



<section class="container">
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Id</th>
                <th>Mục đích</th>
                <th>Chi tiết</th>                    
                <th>Tổng tiền</th>
                <th>Loại thu chi</th>
                <th>Ngày khởi tạo</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${transactions}" var="transaction">
                <tr>
                    <td>${transaction.id}</td>                    
                    <td>${transaction.purpose}</td>
                    <td>${transaction.description}</td>
                    <td>${transaction.amount}</td>
                    <td>${transaction.typeId.name}</td>
                    <td>${transaction.createdDate}</td>
                    <td style="display: flex; justify-content: space-around;">
                        <a href="<c:url value="/transactions/${transaction.id}" />" class="btn btn-success" >Cập nhật</a>
                        <button class ="btn btn-danger">Xóa</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>


    <c:if test="${counter > 1}">
        <ul class="pagination mt-1">
            <c:url value="/transactions" var="pageTransaction"/>
            <li class="page-item"><a class="page-link" href="${pageTransaction}">Tất cả</a></li>
                <c:forEach begin="1" end="${counter}" var="i">
                    <c:url value="/transactions" var="pageUrl">
                        <c:param name="page" value="${i}"></c:param>
                    </c:url>
                <li class="page-item"><a class="page-link" href="${pageUrl}">${i}</a></li>
                </c:forEach>
        </ul>
    </c:if>

</section>

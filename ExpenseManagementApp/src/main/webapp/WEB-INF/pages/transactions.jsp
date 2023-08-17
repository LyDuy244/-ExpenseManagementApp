<%-- 
    Document   : transactions
    Created on : Aug 8, 2023, 2:16:53 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/transactions" var="pagination">
    <c:param name="ps" value="10"></c:param>
</c:url>
<a class="btn btn-success"   href="${pagination}">10</a>
<c:url value="/transactions" var="pagination">
    <c:param name="ps" value="20"></c:param>
</c:url>
<a class="btn btn-success"   href="${pagination}">20</a>
<c:url value="/transactions" var="pagination">
    <c:param name="ps" value="30"></c:param>
</c:url>
<a class="btn btn-success"  href="${pagination}">30</a>

<a class="btn btn-info" href="<c:url value="/transactions/add"/>">Thêm thu chi</a>

<section class="container">
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Id</th>
                <th>Mục đích</th>
                <th>Chi tiết</th>                    
                <th>Tổng tiền</th>
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
                    <td>${transaction.createdDate}</td>
                    <td style="display: flex; justify-content: space-around;">
                        <a href="<c:url value="/transactions/${transaction.id}" />" class="btn btn-success" >Cập nhật</a>
                        <button class ="btn btn-danger">Xóa</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <h1>${user}</h1>

    <h1>Đây là: ${pagesize}</h1>



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

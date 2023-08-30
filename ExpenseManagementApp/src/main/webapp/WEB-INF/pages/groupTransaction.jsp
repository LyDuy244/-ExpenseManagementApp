<%-- 
    Document   : groupTransaction
    Created on : Aug 20, 2023, 2:45:08 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<sec:authentication property="principal" var="loggedInUser" />
<section class="mt-4">
    <button onclick="showTransactionAccept()" class="btn btn-info btn_transaction-accept btn_transaction success">Khoản thu chi đã xác nhận</button>

    <c:if test="${loggedInUser.id == gr.ownerId.id}">
        <button onclick="showTransactionNoAccept()" class="btn btn-info btn_transaction-noAccept btn_transaction ">Khoản thu chi chưa xác nhận</button>
        <div class="text-danger mt-1">(Lưu ý: Nếu nút button màu đỏ có nghĩa là kế hoạch nhóm đã kết thúc không thể cập nhật thu chi mới)</div>
    </c:if>

    <h1>${counterNoAccept}</h1>


    <div class="transaction__accept">
        <table class="mt-4 table table-striped  ">
            <thead>
                <tr>
                    <th>Mục đích</th>
                    <th>Chi tiết</th>                    
                    <th>Tổng tiền</th>
                    <th>Loại thu chi</th>
                    <th>Người khởi tạo</th>
                    <th>Ngày khởi tạo</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${trGroupAccept}" var="trGroupAccept">
                    <tr>
                        <td>${trGroupAccept.purpose}</td>
                        <td>${trGroupAccept.description}</td>
                        <td>${trGroupAccept.amount}</td>
                        <td>${trGroupAccept.typeId.name}</td>
                        <td>${trGroupAccept.groupMemberId.userId.username}</td>
                        <td>${trGroupAccept.createdDate}</td>
                    </tr>
                </c:forEach>
            </tbody>

        </table>
        <c:if test="${counterAccept > 1}">
            <ul class="pagination mt-1">
                <c:url value="/group/details/${gr.id}/group-transaction" var="pageTransactionGroupExpense"/>
                <li class="page-item"><a class="page-link" href="${pageTransactionGroupExpense}">Tất cả</a></li>
                    <c:forEach begin="1" end="${counterAccept}" var="i">
                        <c:url value="/group/details/${gr.id}/group-transaction" var="pageUrl">
                            <c:param name="pageAccept" value="${i}"></c:param>
                        </c:url>
                    <li class="page-item"><a class="page-link" href="${pageUrl}">${i}</a></li>
                    </c:forEach>
            </ul>
        </c:if>
    </div>




    <c:if test="${loggedInUser.id == gr.ownerId.id}">
        <div class="transaction__noAccept hide">
            <table class="mt-4 table table-striped ">
                <thead>
                    <tr>
                        <th>Mục đích</th>
                        <th>Chi tiết</th>                    
                        <th>Tổng tiền</th>
                        <th>Loại thu chi</th>
                        <th>Người khởi tạo</th>
                        <th>Ngày khởi tạo</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${trGroupNoAccept}" var="trGroup">
                        <c:url value="/group/details/${gr.id}/group-transaction" var="action"/>
                        <form:form method="post" action="${action}" modelAttribute="groupTransaction">         
                            <form:hidden path="id" value="${trGroup.id}"/>  
                            <tr>
                                <td>${trGroup.purpose}</td>
                                <td>${trGroup.description}</td>
                                <td>${trGroup.amount}</td>
                                <td>${trGroup.typeId.name}</td>
                                <td>${trGroup.groupMemberId.userId.username}</td>
                                <td>${trGroup.createdDate}</td>
                                <td style="display: flex; justify-content: space-around;">
                                    <c:choose>
                                        <c:when test="${currentDate > gr.endDate}">
                                            <button class="btn btn-danger" disabled>Khóa</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn btn-success" >Cập nhật</button>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </form:form>

                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${counterNoAccept > 1}">
                <ul class="pagination mt-1">
                    <c:url value="/group/details/${gr.id}/group-transaction" var="pageTransactionGroupExpense"/>
                    <li class="page-item"><a class="page-link" href="${pageTransactionGroupExpense}">Tất cả</a></li>
                        <c:forEach begin="1" end="${counterNoAccept}" var="i">
                            <c:url value="/group/details/${gr.id}/group-transaction" var="pageUrl">
                                <c:param name="pageNoAccept" value="${i}"></c:param>
                            </c:url>
                        <li class="page-item"><a class="page-link" href="${pageUrl}">${i}</a></li>
                        </c:forEach>
                </ul>
            </c:if>
        </div>
    </c:if>
</section>

<script>
     const trNoAccept = document.querySelector(".btn_transaction-noAccept");
    const trAccept = document.querySelector(".btn_transaction-accept");
    const tableNoAccept = document.querySelector(".transaction__noAccept");
    const tableAccept = document.querySelector(".transaction__accept");
    const btnList = document.querySelectorAll(".btn_transaction");
    
    // Lấy trạng thái đã lưu trong LocalStorage (nếu có)
    const selectedButton = localStorage.getItem('selectedButton');
    
    function showTransactionNoAccept() {
        tableNoAccept.classList.remove("hide");
        tableAccept.classList.add("hide");
        btnList.forEach(item => item.classList.remove("success"));
        trNoAccept.classList.add("success");
        
        // Lưu trạng thái nút đã chọn vào LocalStorage
        localStorage.setItem('selectedButton', 'noAccept');
    }
    
    function showTransactionAccept() {
        tableNoAccept.classList.add("hide");
        tableAccept.classList.remove("hide");
        btnList.forEach(item => item.classList.remove("success"));
        trAccept.classList.add("success");
        
        // Lưu trạng thái nút đã chọn vào LocalStorage
        localStorage.setItem('selectedButton', 'accept');
    }
    
    // Khôi phục trạng thái nút sau khi tải lại trang
    if (selectedButton === 'noAccept') {
        showTransactionNoAccept();
    } else {
        showTransactionAccept();
    }
</script>
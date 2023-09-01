<%-- 
    Document   : groupDetails
    Created on : Aug 16, 2023, 5:54:45 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<sec:authentication property="principal" var="loggedInUser" />

<!--<h1>${gr.id}</h1>-->

<c:if test="${errorMsg != null}">
    <div class="alert alert-danger">
        ${errorMsg}
    </div>      
</c:if>
<div class="group-details mt-3">
    <div class="card card-4">
        <div class="card-body">
            <h1>${gr.name}</h1>
            <div class="group-item"> 
                <div class="group-item__title">Mục tiêu: </div>
                <span>${gr.title}</span>
            </div>

            <c:if test="${gr.description.trim() != ''}">
                <div class="group-item">
                    <div class="group-item__title">Mô tả chi tiết: </div> 
                    <span>${gr.description}</span>
                </div>
            </c:if>

            <div class="group-item">
                <div class="group-item__title">Trưởng nhóm: </div> 
                <span>${gr.ownerId.firstName} ${gr.ownerId.lastName}</span>
            </div>
            <div class="group-item">
                <div class="group-item__title">Ngày bắt đầu: </div> 
                <span>${gr.startDate}</span>
            </div>
            <div class="group-item">
                <div class="group-item__title"> Ngày kết thúc: </div> 
                <span>${gr.endDate}</span>
            </div>
            <!-- Button to Open the Modal -->

            <c:if test="${gr.ownerId.id == loggedInUser.id}">

                <c:choose>
                    <c:when test="${groupMembers.size() >= 4}">
                        <div class="mb-3">
                            <button type="button" class="btn btn-danger" disabled data-bs-toggle="modal" data-bs-target="#myModal">Thêm thành viên mới</button>
                            <p class="text-danger">Số lượng thành viên đã tối đa (4 người) không thể thêm thành viên mới</p>
                        </div>

                    </c:when>
                    <c:otherwise>
                        <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#myModal">Thêm thành viên mới</button>
                    </c:otherwise>
                </c:choose>
            </c:if>


            <c:choose>
                <c:when test="${currentDate > gr.endDate}">
                    <div class="mb-3">
                        <a href="<c:url value="/group/details/${gr.id}/add-transaction"/>"class="btn btn-danger disabled">Thêm khoản thu chi</a>
                        <p class="text-danger">Kế hoách nhóm đã kết thúc (không thể thêm khoản thu chi mới)</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value="/group/details/${gr.id}/add-transaction" />" class="btn btn-success mb-3">Thêm khoản thu chi</a>
                </c:otherwise>
            </c:choose>
            <a href="<c:url value="/group/details/${gr.id}/group-transaction"/>" class="btn btn-success  mb-3">Xem các khoản thu chi của nhóm</a>
        </div>
    </div>
</div>

<div class=" group-details mt-3">
    <div class="card card-4">
        <div class="card-body">
            <!--<h1>${groupMembers.size()}</h1>-->
            <h4 class="text-center mb-5">Danh sách các thành viên:</h4>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Thành viên nhóm</th>     
                        <th>Username</th>                    
                        <th>Email</th> 
                            <c:if test="${currentDate > gr.endDate}">
                            <th>Tổng tiền đã thu</th>
                            <th>Tổng tiền đã chi</th>
                            <th>Số tiền được trả lại</th>
                            <th>Số tiền phải đóng thêm</th>
                            </c:if>

                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${groupMembers}" var="member">
                        <tr>
                            <td>${member[0].firstName} ${member[0].lastName}</td>
                            <td>${member[0].username}</td>
                            <td>${member[0].email}</td>
                            <c:if test="${currentDate > gr.endDate}">
                                <td class="text-success">${member[1]}</td>       
                                <td class="text-danger">${member[2]}</td>  
                                <c:choose>
                                    <c:when test="${member[1] - avgChi > 0}">
                                        <td class="text-success">${member[1] - avgChi}</td>       
                                        <td class="text-danger">0</td>       
                                    </c:when> 
                                    <c:otherwise>
                                        <td class="text-success">0</td> 
                                        <td class="text-danger">${avgChi - member[1]}</td>       
                                    </c:otherwise>
                                </c:choose>
                            </c:if>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
        </div>
    </div>
</div>


<c:if test="${groupMembers.size() < 4}">
    <!-- The Modal -->
    <div class="modal modal-xl" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">Danh sách các tài khoản</h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>


                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    <div class="wrapper wrapper--w960 ">
                        <div class="">
                            <input class="form-control me-2" type="text" name="username"  id="usernameInput" placeholder="Nhập từ khóa...">
                            <div class="card-list">
                                <c:forEach items="${users}" var="user">
                                    <c:url value="/group/details/${gr.id}" var="action"/>
                                    <form:form method="post" action="${action}" modelAttribute="memberGroup">
                                        <form:hidden path="groupId" value="${gr.id}"/>  
                                        <form:hidden path="userId" value="${user.id}"/>
                                        <form:errors path="userId" element="div" cssClass="text-danger"/>

                                        <div class="user_card">
                                            <img class="user_card_img" src="${user.avartar}" alt="alt"/>
                                            <p class="user_card_name">${user.firstName} ${user.lastName}</h3>
                                            <p class="user_card_email">${user.email}</p>
                                            <button class="btn btn-success" type="subumit">Thêm thành viên</button>
                                        </div>
                                    </form:form>
                                </c:forEach>



                            </div>
                        </div>
                    </div>
                </div>

                <!-- Modal footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
                </div>

            </div>
        </div>
    </div>  
</c:if>



<script>
    var userCards = document.getElementsByClassName("user_card");
    var input = document.getElementById("usernameInput");


    input.addEventListener("input", function () {
        var inputText = document.getElementById("usernameInput").value.toLowerCase();

        if (inputText.trim() !== "") {
            for (var i = 0; i < userCards.length; i++) {
                var userName = userCards[i].getElementsByClassName("user_card_name")[0].textContent.toLowerCase();
                if (!userName.includes(inputText)) {
                    userCards[i].style.display = "none";
                } else {
                    userCards[i].style.display = "flex";
                }
            }
        } else {
            for (var i = 0; i < userCards.length; i++) {
                userCards[i].style.display = "flex";
            }
        }


    })

</script>
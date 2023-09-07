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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>   


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
                <div class="group-item__title">
                    <spring:message code="groupExpense.add.purpose"/>
                </div>
                <span>${gr.title}</span>
            </div>

            <c:if test="${gr.description.trim() != ''}">
                <div class="group-item">
                    <div class="group-item__title">                    
                        <spring:message code="groupExpense.add.desc"/>
                    </div> 
                    <span>${gr.description}</span>
                </div>
            </c:if>

            <div class="group-item">
                <div class="group-item__title">
                    <spring:message code="groupExpense.detail.leader"/>
                </div> 
                <span>${gr.ownerId.firstName} ${gr.ownerId.lastName}</span>
            </div>
            <div class="group-item">
                <div class="group-item__title">
                    <spring:message code="groupExpense.add.startDate"/>
                </div> 
                <span>${gr.startDate}</span>
            </div>
            <div class="group-item">
                <div class="group-item__title"> 
                    <spring:message code="groupExpense.add.endDate"/>
                </div> 
                <span>${gr.endDate}</span>
            </div>
            <!-- Button to Open the Modal -->

            <c:if test="${gr.ownerId.id == loggedInUser.id}">
                <c:choose>
                    <c:when test="${currentDate > gr.endDate}">
                        <div class="mb-3">
                            <button type="button" class="btn btn-danger" disabled data-bs-toggle="modal" data-bs-target="#myModal">
                                <spring:message code="groupExpense.detail.btn.add.user"/>
                            </button>
                            <p class="text-danger">
                                <spring:message code="groupExpense.detail.add.user.error.date"/>
                            </p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${groupMembers.size() >= 4}">
                                <div class="mb-3">
                                    <button type="button" class="btn btn-danger" disabled data-bs-toggle="modal" data-bs-target="#myModal">
                                        <spring:message code="groupExpense.detail.btn.add.user"/>
                                    </button>
                                    <p class="text-danger">
                                        <spring:message code="groupExpense.detail.add.user.error"/>
                                    </p>
                                </div>

                            </c:when>
                            <c:otherwise>
                                <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#myModal">
                                    <spring:message code="groupExpense.detail.btn.add.user"/>
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>

            </c:if>


            <c:choose>
                <c:when test="${currentDate > gr.endDate}">
                    <div class="mb-3">
                        <a href="<c:url value="/group/details/${gr.id}/add-transaction"/>"class="btn btn-danger disabled">
                            <spring:message code="groupExpense.detail.btn.add.expense"/>
                        </a>
                        <p class="text-danger">
                            <spring:message code="groupExpense.detail.add.expense.error"/>
                        </p>
                    </div>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value="/group/details/${gr.id}/add-transaction" />" class="btn btn-success mb-3">
                        <spring:message code="groupExpense.detail.btn.add.expense"/>
                    </a>
                </c:otherwise>
            </c:choose>
            <a href="<c:url value="/group/details/${gr.id}/group-transaction"/>" class="btn btn-success  mb-3">
                <spring:message code="groupExpense.detail.btn.expense"/>
            </a>
        </div>
    </div>
</div>

<div class=" group-details mt-3">
    <div class="card card-4">
        <div class="card-body">
            <!--<h1>${groupMembers.size()}</h1>-->
            <h4 class="text-center mb-5">
                <spring:message code="groupExpense.detail.group.list"/>
            </h4>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>
                            <spring:message code="groupExpense.detail.group.member"/>
                        </th>     
                        <th>Username</th>                    
                        <th>Email</th> 
                            <c:if test="${currentDate > gr.endDate}">
                            <th>
                                <spring:message code="groupExpense.detail.total.income"/>
                            </th>
                            <th>
                                <spring:message code="groupExpense.detail.total.expense"/>
                            </th>
                            <th>
                                <spring:message code="groupExpense.detail.amount.refune"/>
                            </th>
                            <th>
                                <spring:message code="groupExpense.detail.amount.extra"/>
                            </th>
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
                                <td class="text-success formattedAmount">${member[1]}</td>       
                                <td class="text-danger formattedAmount">${member[2]}</td>  
                                <c:choose>
                                    <c:when test="${member[1] - avgChi > 0}">
                                        <td class="text-success formattedAmount">${member[1] - avgChi}</td>       
                                        <td class="text-danger formattedAmount">0</td>       
                                    </c:when> 
                                    <c:otherwise>
                                        <td class="text-success formattedAmount">0</td> 
                                        <td class="text-danger formattedAmount">${avgChi - member[1]}</td>       
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


<c:if test="${groupMembers.size() < 4 && currentDate < gr.endDate}">
    <!-- The Modal -->
    <div class="modal modal-xl" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">
                        <spring:message code="groupExpense.detail.modal.user.list"/>
                    </h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    <div class="wrapper wrapper--w960 ">
                        <div class="">
                            <input class="form-control me-2" type="text" name="username"  id="usernameInput" placeholder="<spring:message code="base.input.keyword"/>">
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
                                            <button class="btn btn-success" type="subumit">
                                                <spring:message code="groupExpense.detail.modal.btn"/>
                                            </button>
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

    if (input !== null) {
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


        });
    }

    // Tạo một đối tượng Intl.NumberFormat để định dạng tiền tệ
    var formatter = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    });

    var elements = document.getElementsByClassName('formattedAmount');
    for (var i = 0; i < elements.length; i++) {
        var text = elements[i].textContent;
        var formattedAmount = formatter.format(text);
        elements[i].textContent = formattedAmount;
    }
</script>
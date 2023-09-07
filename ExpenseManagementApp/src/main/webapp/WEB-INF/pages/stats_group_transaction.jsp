<%-- 
    Document   : stats_group_transaction
    Created on : Sep 2, 2023, 2:19:32 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>   

<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<sec:authentication property="principal" var="loggedInUser" />
<h1 class="text-center text-success">
    <spring:message code="stats.title.group"/>

    
</h1>

<div class="row mt-5">
    <div class="col-md-5 col-sm-12" style="margin-right: 50px">
        <table id="statsTable" class="table text-center">
            <thead>
            <th>
                <spring:message code="stats.month"/>
                
            </th>
            <th>
                <spring:message code="stats.total.income"/>
                
            </th>
            <th>
                <spring:message code="stats.total.expense"/>
                
            </th>
            </thead>
            <c:forEach items="${stats}" var="s">
                <tr>
                    <td>${s[0]}</td>
                    <td class="formattedAmount">${s[1]}</td>
                    <td class="formattedAmount">${s[2]}</td>
                </tr>
            </c:forEach>

        </table>
    </div>
    <div class="col-md-6 col-sm-12">
        <form class="d-flex" >
            <select class="form-select me-2" name="fromDate">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
                <option value="11">11</option>
                <option value="12">12</option>
            </select>
            <select class="form-select me-2" name="toDate">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
                <option value="11">11</option>
                <option value="12" selected>12</option>

            </select>
            <select class="form-select me-2" name="year">
                <c:choose>
                    <c:when test="${years.size() > 0}">
                        <c:forEach items="${years}" var="y">
                            <option value="${y}">${y}</option>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <option value="${currentYear}">${currentYear}</option>
                    </c:otherwise>
                </c:choose>


            </select>


            <input type="submit" value="<spring:message code="stats.btn.filter"/>" class="btn btn-info">

        </form>

        <canvas id="revenueStats"></canvas> 
    </div>

</div>

<script>

    let data = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], labels = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
    let data2 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    let statsArray = [
    <c:forEach items="${stats}" var="s" varStatus="status">
    {
    month: ${s[0]},
            thu: ${s[1]},
            chi: ${s[2]}
    }<c:if test="${!status.last}">,</c:if>
    </c:forEach>
    ];
    // Lặp qua danh sách stats để cập nhật dữ liệu
    for (let i = 0; i < statsArray.length; i++) {
        let month = statsArray[i].month;
        let thu = statsArray[i].thu;
        let chi = statsArray[i].chi;
        data[month - 1] = thu;
        data2[month - 1] = chi;
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

    window.onload = function () {
        drawRevenueStatsWithPrice(labels, data, data2);
    };

</script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

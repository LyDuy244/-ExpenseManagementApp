<%-- 
    Document   : stats_transaction
    Created on : Aug 31, 2023, 12:43:47 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>   

<sec:authentication property="principal" var="loggedInUser" />
<div class="form-wrapper"  style="max-width: none; width: 1005px; margin: 0 auto">
    <h1 class="text-center text-success"> <spring:message code="stats.title.personal"/></h1>

    <div class="row mt-5">
        <div class="col-md-12 col-sm-12" style="margin-right: 50px">
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
        <div class="col-md-12 col-sm-12 mt-5">
            <div class="row">
                <form class="d-flex col mx-2 col-md-10 col-sm-10" >
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

                    <input type="button" id="create_pdf" value="<spring:message code="stats.btn.pdf"/>"  class="btn btn-info mx-2" >

                </form>
                    <div class="col col-md-8 col-sm-8" style="margin: 30px 0" >
                        <canvas id="revenueStats"></canvas>

                    </div>
            </div>

        </div>

    </div>


</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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

<script src="https://code.jquery.com/jquery-1.12.4.min.js"
integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ=" crossorigin="anonymous"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.5/jspdf.min.js"></script>
<script>
    (function () {
        var
                form = $('.form-wrapper'),
                cache_width = form.width(),
                a4 = [595.28, 841.89]; // for a4 size paper width and height  

        $('#create_pdf').on('click', function () {
            $('body').scrollTop(0);
            createPDF();
        });
        //create pdf  
        function createPDF() {
            getCanvas().then(function (canvas) {
                var
                        img = canvas.toDataURL("image/png"),
                        doc = new jsPDF({
                            unit: 'px',
                            format: 'a4'
                        });
                doc.addImage(img, 'JPEG', 20, 20);
                doc.save('stats-transaction-personal.pdf');
                form.width(cache_width);
            });
        }

        // create canvas object  
        function getCanvas() {
            form.width((a4[0] * 1.33333) - 80).css('max-width', 'none');
            return html2canvas(form, {
                imageTimeout: 2000,
                removeContainer: true
            });
        }

    }());
</script>  
<script>
    /* 
     * jQuery helper plugin for examples and tests 
     */
    (function ($) {
        $.fn.html2canvas = function (options) {
            var date = new Date(),
                    $message = null,
                    timeoutTimer = false,
                    timer = date.getTime();
            html2canvas.logging = options && options.logging;
            html2canvas.Preload(this[0], $.extend({
                complete: function (images) {
                    var queue = html2canvas.Parse(this[0], images, options),
                            $canvas = $(html2canvas.Renderer(queue, options)),
                            finishTime = new Date();

                    $canvas.css({position: 'absolute', left: 0, top: 0}).appendTo(document.body);
                    $canvas.siblings().toggle();

                    $(window).click(function () {
                        if (!$canvas.is(':visible')) {
                            $canvas.toggle().siblings().toggle();
                            throwMessage("Canvas Render visible");
                        } else {
                            $canvas.siblings().toggle();
                            $canvas.toggle();
                            throwMessage("Canvas Render hidden");
                        }
                    });
                    throwMessage('Screenshot created in ' + ((finishTime.getTime() - timer) / 1000) + " seconds<br />", 4000);
                }
            }, options));

            function throwMessage(msg, duration) {
                window.clearTimeout(timeoutTimer);
                timeoutTimer = window.setTimeout(function () {
                    $message.fadeOut(function () {
                        $message.remove();
                    });
                }, duration || 2000);
                if ($message)
                    $message.remove();
                $message = $('<div ></div>').html(msg).css({
                    margin: 0,
                    padding: 10,
                    background: "#000",
                    opacity: 0.7,
                    position: "fixed",
                    top: 10,
                    right: 10,
                    fontFamily: 'Tahoma',
                    color: '#fff',
                    fontSize: 12,
                    borderRadius: 12,
                    width: 'auto',
                    height: 'auto',
                    textAlign: 'center',
                    textDecoration: 'none'
                }).hide().fadeIn().appendTo('body');
            }
        };
    })(jQuery);

</script>  
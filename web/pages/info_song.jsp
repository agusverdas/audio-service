<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 02.01.19
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html lang="${sessionScope.locale.language}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="title.info.song" bundle="${rb}"/></title>
    <link href="../css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <style>
        input[type='number'] {
            -moz-appearance: textfield;
        }

        input::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button {
            -webkit-appearance: none;
        }
    </style>
</head>
<body style="overflow-y: scroll;">
<%@include file="header.jsp" %>
<body style="overflow-y: scroll;">
<br>
<div class="container-fluid">
    <div class="row">
        <div class="card-deck mx-auto" style="width: 40%;">
            <div class="card mx-auto">
                <div style="padding: 10px;" class="card-block mx-auto">
                    <h4 class="card-title">
                        <c:forEach var="author" items="${ song.authorList }">
                            <c:out value="${author.name} "/>
                        </c:forEach>
                    </h4>
                    <p class="card-text">${song.title}</p>
                    <audio controls controlsList="nodownload">
                        <source src="${song.path}" type="audio/ogg">
                        <source src="${song.path}" type="audio/mp3">
                    </audio>
                    <br>
                    <a href="${pageContext.request.contextPath}/Controller?command=get-buy-song&entityId=${song.songId}"
                       class="btn btn-primary">
                        <fmt:message key="button.buy.song" bundle="${rb}"/> ${song.cost}$
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>
<script src="../js/script.js"></script>
</body>
</html>

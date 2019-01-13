<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 05.01.19
  Time: 17:02
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
                    <p class="card-text">${album.albumTitle}</p>
                    <table id="example" class="table table-sm" style="width:100%">
                        <thead>
                        <tr>
                            <th style="text-align: center" scope="col"><fmt:message key="thread.title" bundle="${rb}"/></th>
                            <th style="text-align: center" scope="col"><fmt:message key="thread.authors" bundle="${rb}"/></th>
                            <th style="text-align: center" scope="col"><fmt:message key="thread.song" bundle="${rb}"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="song" items="${ album.songs }">
                            <tr>
                                <th style="vertical-align : middle;" scope="row">${song.title}</th>
                                <th style="vertical-align : middle;" scope="row">
                                    <c:forEach var="author" items="${ song.authorList }">
                                        <c:out value="${author.name}"/>
                                        <br/>
                                    </c:forEach>
                                </th>
                                <th style="vertical-align : middle;">
                                    <audio controls controlsList="nodownload">
                                        <source src="${song.path}" type="audio/ogg">
                                        <source src="${song.path}" type="audio/mp3">
                                    </audio>
                                </th>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <br>
                    <a href="${pageContext.request.contextPath}/Controller?command=get-buy-album&entityId=${album.albumId}"
                       class="btn btn-primary">
                        <fmt:message key="button.buy.song" bundle="${rb}"/> ${album.cost}$
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>
<script src="../js/audio.js"></script>
</body>
</html>

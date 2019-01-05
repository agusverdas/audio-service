<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 04.12.18
  Time: 12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html lang="${sessionScope.locale.language}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="title.main" bundle="${rb}"/></title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/styles.css">
    <link rel="stylesheet" href="../css/datatables.min.css">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/datatables.min.js"></script>
</head>
<body style="overflow-y: scroll;">
<%@include file="header.jsp" %>
<br>
<section id="tabs">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <nav>
                    <div class="nav nav-tabs nav-fill" id="nav-tab" role="tablist">
                        <a class="nav-item nav-link active" id="nav-songs-tab" data-toggle="tab" href="#nav-songs"
                           role="tab" aria-controls="nav-songs" aria-selected="true">
                            <fmt:message key="tab.songs" bundle="${rb}"/></a>
                        <a class="nav-item nav-link" id="nav-albums-tab" data-toggle="tab" href="#nav-albums"
                           role="tab" aria-controls="nav-albums" aria-selected="false">
                            <fmt:message key="tab.albums" bundle="${rb}"/></a>
                    </div>
                </nav>
                <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="nav-songs" role="tabpanel"
                         aria-labelledby="nav-songs-tab">
                        <table id="example" class="table table-sm" style="width:100%">
                            <thead>
                            <tr>
                                <th style="text-align: center" scope="col"><fmt:message key="thread.title" bundle="${rb}"/></th>
                                <th style="text-align: center" scope="col"><fmt:message key="thread.authors" bundle="${rb}"/></th>
                                <th style="text-align: center" scope="col"><fmt:message key="thread.song" bundle="${rb}"/></th>
                                <th style="text-align: center" scope="col"><fmt:message key="thread.info" bundle="${rb}"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="elem" items="${ songs }">
                                <tr>
                                    <th style="vertical-align : middle;" scope="row">${elem.title}</th>
                                    <th style="vertical-align : middle;" scope="row">
                                        <c:forEach var="author" items="${ elem.authorList }">
                                            <c:out value="${author.name}"/>
                                            <br/>
                                        </c:forEach>
                                    </th>
                                    <th style="vertical-align : middle;">
                                        <audio controls controlsList="nodownload">
                                            <source src="${elem.path}" type="audio/ogg">
                                            <source src="${elem.path}" type="audio/mp3">
                                        </audio>
                                    </th>
                                    <th style="vertical-align : middle;" scope="row">
                                        <a class="btn btn-primary btn-block btn-large"
                                           href="${pageContext.request.contextPath}/Controller?command=get-info-song&entityId=${elem.songId}">
                                            <fmt:message key="button.info" bundle="${rb}"/></a>
                                    </th>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="tab-pane fade" id="nav-albums" role="tabpanel" aria-labelledby="nav-albums-tab">
                        <table class="table table-sm">
                            <thead>
                            <tr>
                                <th style="text-align: center" scope="col"><fmt:message key="thread.title" bundle="${rb}"/></th>
                                <th style="text-align: center" scope="col"><fmt:message key="thread.authors" bundle="${rb}"/></th>
                                <th style="text-align: center" scope="col"><fmt:message key="thread.photo" bundle="${rb}"/></th>
                                <th style="text-align: center" scope="col"><fmt:message key="thread.info" bundle="${rb}"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="elem" items="${ albums }">
                                <tr>
                                    <th style="vertical-align : middle;" scope="row">${elem.albumTitle}</th>
                                    <th style="vertical-align : middle;" scope="row">
                                        <c:out value="${elem.author.name}"/>
                                    </th>
                                    <th style="vertical-align : middle;">
                                        <img src="${elem.photo}" class="img-fluid">
                                    </th>
                                    <th style="vertical-align : middle;" scope="row">
                                        <a class="btn btn-primary btn-block btn-large"
                                           href="${pageContext.request.contextPath}/Controller?command=get-info-album&entityId=${elem.albumId}">
                                            <fmt:message key="button.info" bundle="${rb}"/></a>
                                    </th>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<br>
<%@include file="footer.jsp" %>
<script src="../js/script.js"></script>
</body>
</html>

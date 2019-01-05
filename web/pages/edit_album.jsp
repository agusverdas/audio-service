<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 01.01.19
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html lang="${sessionScope.locale.language}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="title.edit.album" bundle="${rb}"/></title>
    <link href="../css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body style="overflow-y: scroll;">
<%@include file="header.jsp" %>
<section id="tabs">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <nav>
                    <div class="nav nav-tabs nav-fill" id="nav-tab" role="tablist">
                        <a class="nav-item nav-link active" id="nav-songs-tab" data-toggle="tab" href="#nav-songs"
                           role="tab" aria-controls="nav-songs" aria-selected="true">
                            <fmt:message key="tab.songs" bundle="${rb}"/></a>
                    </div>
                </nav>
                <form action="${pageContext.request.contextPath}/Controller" method="POST">
                    <input type="hidden" name="command" value="post-edit-album">
                    <input type="hidden" name="id" value="${entityId}">
                    <div class="tab-content" id="nav-tabContent">
                        <div class="tab-pane fade show active" id="nav-songs" role="tabpanel"
                             aria-labelledby="nav-songs-tab">
                            <table id="example" class="table table-sm" style="width:100%">
                                <thead>
                                <tr>
                                    <th style="text-align: center" scope="col"><fmt:message key="thread.title"
                                                                                            bundle="${rb}"/></th>
                                    <th style="text-align: center" scope="col"><fmt:message key="thread.authors"
                                                                                            bundle="${rb}"/></th>
                                    <th style="text-align: center" scope="col"><fmt:message key="thread.song"
                                                                                            bundle="${rb}"/></th>
                                    <th style="text-align: center" scope="col">CHECKBOX</th>
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
                                        <th>
                                            <input type="checkbox" name="checkedRows" value="${elem.songId}">
                                        </th>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <input type="submit" value="OK">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<br>
<%@include file="footer.jsp" %>
</body>
</html>

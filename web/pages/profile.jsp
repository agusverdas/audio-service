<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 11.12.18
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html lang="${sessionScope.locale.language}">
<head>
    <meta charset="utf-8">
    <title><fmt:message key="title.profile" bundle="${rb}"/></title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
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
                        <a class="nav-item nav-link active" id="nav-profile-tab" data-toggle="tab" href="#nav-profile"
                           role="tab" aria-controls="nav-add" aria-selected="true">
                            <fmt:message key="tab.profile" bundle="${rb}"/></a>
                        <a class="nav-item nav-link" id="nav-songs-tab" data-toggle="tab" href="#nav-songs"
                           role="tab" aria-controls="nav-adda" aria-selected="false">
                            <fmt:message key="tab.your.songs" bundle="${rb}"/></a>
                    </div>
                </nav>
                <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="nav-profile" role="tabpanel"
                         aria-labelledby="nav-profile-tab">
                        <div style="float: right" class="col-md-4 img">
                            <img style="max-height: 100%; max-width: 100%" class="img-rounded" src="${user.photo}"/>
                        </div>
                        <div class="col-md-6 details">
                            <blockquote>
                                <h5>${user.name}</h5>
                            </blockquote>
                            <p>
                                <fmt:message key="label.email" bundle="${rb}"/>: ${user.email}<br/>
                                <fmt:message key="label.role" bundle="${rb}"/>: ${user.role}<br/>
                                <fmt:message key="label.money" bundle="${rb}"/>: ${user.money}<br/>
                                <fmt:message key="label.bonus" bundle="${rb}"/>: ${user.bonus}<br/><br/>
                                <a href="${pageContext.request.contextPath}/Controller?command=get-edit"
                                   class="btn btn-primary btn-block btn-large"><fmt:message key="button.edit.profile"
                                                                                            bundle="${rb}"/></a><br>
                                <a href="${pageContext.request.contextPath}/Controller?command=get-add-money"
                                   class="btn btn-primary btn-block btn-large"><fmt:message key="button.add.money" bundle="${rb}"/></a>
                            </p>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="nav-songs" role="tabpanel" aria-labelledby="nav-songs-tab">
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
                                            <c:out value="${author.name} "/>
                                        </c:forEach>
                                    </th>
                                    <th style="vertical-align : middle;">
                                        <audio controls controlsList="nodownload">
                                            <source src="${elem.path}" type="audio/ogg">
                                            <source src="${elem.path}" type="audio/mp3">
                                        </audio>
                                    </th>
                                    <th style="vertical-align : middle;" scope="row">

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
<%@include file="footer.jsp" %>
</body>
</html>

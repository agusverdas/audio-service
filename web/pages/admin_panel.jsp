<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 12.12.18
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="label.title.admin" bundle="${rb}"/></title>
    <link rel="stylesheet" href="../css/bootstrap.min.css" id="bootstrap-css">
    <link rel="stylesheet" href="../css/styles.css">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="header.jsp" %>
<br>
<section id="tabs">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <nav>
                    <div class="nav nav-tabs nav-fill" id="nav-tab" role="tablist">
                        <a class="nav-item nav-link active" id="nav-add-tab" data-toggle="tab" href="#nav-add"
                           role="tab" aria-controls="nav-add" aria-selected="true">
                            <fmt:message key="label.button.addsong" bundle="${rb}"/></a>
                        <a class="nav-item nav-link" id="nav-adda-tab" data-toggle="tab" href="#nav-adda"
                           role="tab" aria-controls="nav-adda" aria-selected="false">
                            <fmt:message key="label.button.addalbum" bundle="${rb}"/></a>
                        <a class="nav-item nav-link" id="nav-users-tab" data-toggle="tab" href="#nav-users"
                           role="tab" aria-controls="nav-users" aria-selected="false">
                            <fmt:message key="label.button.allusers" bundle="${rb}"/></a>
                        <a class="nav-item nav-link" id="nav-editsongs-tab" data-toggle="tab" href="#nav-editsongs"
                           role="tab" aria-controls="nav-editsongs" aria-selected="false">
                            <fmt:message key="label.title.editsongs" bundle="${rb}"/></a>
                        <a class="nav-item nav-link" id="nav-editalbums-tab" data-toggle="tab" href="#nav-editalbums"
                           role="tab" aria-controls="nav-editalbums" aria-selected="false">
                            <fmt:message key="label.title.editalbums" bundle="${rb}"/></a>
                    </div>
                </nav>
                <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="nav-add" role="tabpanel"
                         aria-labelledby="nav-add-tab">
                        <form style="text-align: center;" method="post"
                              action="${pageContext.request.contextPath}/Controller" enctype="multipart/form-data">
                            <input type="hidden" name="command" value="post-add-song">
                            <input type="text" name="title" placeholder=
                            <fmt:message key="label.placeholder.title" bundle="${rb}"/> required="required"
                                   maxlength="50"><br>
                            <input type="text" name="author" placeholder=
                            <fmt:message key="label.placeholder.author" bundle="${rb}"/> required="required"
                                   maxlength="255"><br>
                            <input type="text" name="cost" placeholder=
                            <fmt:message key="label.placeholder.cost" bundle="${rb}"/> required="required"
                                   maxlength="8"><br>
                            <input type="file" name="song" required="required" accept="audio/mpeg3"><br>
                            <button type="submit" class="btn btn-primary btn-block btn-large">
                                <fmt:message key="label.navtab.addsong" bundle="${rb}"/></button>
                            <br>
                            <span style="color:red">${errorMessage}</span>
                        </form>
                    </div>
                    <div class="tab-pane fade" id="nav-adda" role="tabpanel" aria-labelledby="nav-adda-tab">
                        <form style="text-align: center;" method="post"
                              action="${pageContext.request.contextPath}/Controller" enctype="multipart/form-data">
                            <input type="hidden" name="command" value="post-add-album">
                            <input type="text" name="title" placeholder=
                            <fmt:message key="label.placeholder.title" bundle="${rb}"/> required="required"
                                   maxlength="50"><br>
                            <input type="text" name="author" placeholder=
                            <fmt:message key="label.placeholder.author" bundle="${rb}"/> required="required"
                                   maxlength="50"><br>
                            <input type="file" name="photo" required="required" accept="image/jpeg, image/png"><br>
                            <button type="submit" class="btn btn-primary btn-block btn-large">
                                <fmt:message key="label.navtab.addalbum" bundle="${rb}"/></button>
                            <br>
                            <span style="color:red">${errorMessage}</span>
                        </form>
                    </div>
                    <div class="tab-pane fade" id="nav-users" role="tabpanel" aria-labelledby="nav-users-tab">
                        <table class="table table-sm">
                            <thead>
                            <tr>
                                <th scope="col"><fmt:message key="label.placeholder.email" bundle="${rb}"/></th>
                                <th scope="col"><fmt:message key="label.placeholder.name" bundle="${rb}"/></th>
                                <th scope="col"><fmt:message key="label.placeholder.role" bundle="${rb}"/></th>
                                <th scope="col"><fmt:message key="label.placeholder.money" bundle="${rb}"/></th>
                                <th scope="col"><fmt:message key="label.placeholder.bonus" bundle="${rb}"/></th>
                                <th scope="col"><fmt:message key="label.placeholder.editbonus" bundle="${rb}"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="elem" items="${ users }">
                                <tr>
                                    <th style="vertical-align : middle;" scope="row">${elem.email}</th>
                                    <th style="vertical-align : middle;" scope="row">${elem.name}</th>
                                    <th style="vertical-align : middle;" scope="row">${elem.role}</th>
                                    <th style="vertical-align : middle;" scope="row">${elem.money}</th>
                                    <th style="vertical-align : middle;" scope="row">${elem.bonus}</th>
                                    <th style="vertical-align : middle;" scope="row">
                                        <a class="btn btn-primary btn-block btn-large"
                                           href="${pageContext.request.contextPath}/Controller?command=get-edit-bonus&entityId=${elem.userId}">
                                            <fmt:message key="label.placeholder.editbonus" bundle="${rb}"/></a>
                                    </th>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="tab-pane fade" id="nav-editsongs" role="tabpanel" aria-labelledby="nav-editsongs-tab">
                        <table class="table table-sm">
                            <thead>
                            <tr>
                                <th scope="col"><fmt:message key="label.placeholder.title" bundle="${rb}"/></th>
                                <th scope="col"><fmt:message key="label.placeholder.author" bundle="${rb}"/></th>
                                <th scope="col"><fmt:message key="label.placeholder.song" bundle="${rb}"/></th>
                                <th scope="col"><fmt:message key="label.placeholder.cost" bundle="${rb}"/></th>
                                <th scope="col"><fmt:message key="label.title.editsongs" bundle="${rb}"/></th>
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
                                    <th style="vertical-align : middle;" scope="row">${elem.cost}</th>
                                    <th style="vertical-align : middle;" scope="row">
                                        <a class="btn btn-primary btn-block btn-large"
                                           href="${pageContext.request.contextPath}/Controller?command=get-edit-song&entityId=${elem.songId}">
                                            <fmt:message key="label.title.editsongs" bundle="${rb}"/></a>
                                    </th>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="tab-pane fade" id="nav-editalbums" role="tabpanel" aria-labelledby="nav-editalbums-tab">
                        <table class="table table-sm">
                            <thead>
                            <tr>
                                <th scope="col"><fmt:message key="label.placeholder.title" bundle="${rb}"/></th>
                                <th scope="col"><fmt:message key="label.placeholder.author" bundle="${rb}"/></th>
                                <th scope="col"><fmt:message key="label.title.photo" bundle="${rb}"/></th>
                                <th scope="col"><fmt:message key="label.title.editalbums" bundle="${rb}"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="elem" items="${ albums }">
                                <tr>
                                    <th style="vertical-align : middle;" scope="row">${elem.albumTitle}</th>
                                    <th style="vertical-align : middle;" scope="row">
                                        HERE WILL BE AUTHORS
                                    </th>
                                    <th style="vertical-align : middle;">
                                        <img src="${elem.photo}" class="img-fluid">
                                    </th>
                                    <th style="vertical-align : middle;" scope="row">
                                        <a class="btn btn-primary btn-block btn-large"
                                           href="${pageContext.request.contextPath}/Controller?command=get-edit-album&entityId=${elem.albumId}">
                                            <fmt:message key="label.title.editalbums" bundle="${rb}"/></a>
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
</body>
</html>

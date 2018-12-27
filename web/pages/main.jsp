<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 04.12.18
  Time: 12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html>
<head>
    <title>Main page</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
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
                            <a class="nav-item nav-link active" id="nav-songs-tab" data-toggle="tab" href="#nav-songs" role="tab" aria-controls="nav-songs" aria-selected="true">Songs</a>
                            <a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">Profile</a>
                            <a class="nav-item nav-link" id="nav-contact-tab" data-toggle="tab" href="#nav-contact" role="tab" aria-controls="nav-contact" aria-selected="false">Contact</a>
                            <a class="nav-item nav-link" id="nav-about-tab" data-toggle="tab" href="#nav-about" role="tab" aria-controls="nav-about" aria-selected="false">About</a>
                        </div>
                    </nav>
                    <div class="tab-content" id="nav-tabContent">
                        <div class="tab-pane fade show active" id="nav-songs" role="tabpanel" aria-labelledby="nav-songs-tab">
                            <table class="table table-sm">
                                <thead>
                                <tr>
                                    <th scope="col">Title</th>
                                    <th scope="col">Author</th>
                                    <th scope="col">Song</th>
                                    <th scope="col">Cost</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="elem" items="${ songs }" varStatus="status">
                                    <tr>
                                        <th style="vertical-align : middle;" scope="row">${elem.title}</th>
                                        <th style="vertical-align : middle;" scope="row">${elem.title}</th>
                                        <th style="vertical-align : middle;">
                                            <audio controls>
                                                <source src=${elem.path} type="audio/mpeg">
                                            </audio>
                                        </th>
                                        <th style="vertical-align : middle;" scope="row">${elem.cost}</th>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
                            Et et consectetur ipsum labore excepteur est proident excepteur ad velit occaecat qui minim occaecat veniam. Fugiat veniam incididunt anim aliqua enim pariatur veniam sunt est aute sit dolor anim. Velit non irure adipisicing aliqua ullamco irure incididunt irure non esse consectetur nostrud minim non minim occaecat. Amet duis do nisi duis veniam non est eiusmod tempor incididunt tempor dolor ipsum in qui sit. Exercitation mollit sit culpa nisi culpa non adipisicing reprehenderit do dolore. Duis reprehenderit occaecat anim ullamco ad duis occaecat ex.
                        </div>
                        <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">
                            Et et consectetur ipsum labore excepteur est proident excepteur ad velit occaecat qui minim occaecat veniam. Fugiat veniam incididunt anim aliqua enim pariatur veniam sunt est aute sit dolor anim. Velit non irure adipisicing aliqua ullamco irure incididunt irure non esse consectetur nostrud minim non minim occaecat. Amet duis do nisi duis veniam non est eiusmod tempor incididunt tempor dolor ipsum in qui sit. Exercitation mollit sit culpa nisi culpa non adipisicing reprehenderit do dolore. Duis reprehenderit occaecat anim ullamco ad duis occaecat ex.
                        </div>
                        <div class="tab-pane fade" id="nav-about" role="tabpanel" aria-labelledby="nav-about-tab">
                            Et et consectetur ipsum labore excepteur est proident excepteur ad velit occaecat qui minim occaecat veniam. Fugiat veniam incididunt anim aliqua enim pariatur veniam sunt est aute sit dolor anim. Velit non irure adipisicing aliqua ullamco irure incididunt irure non esse consectetur nostrud minim non minim occaecat. Amet duis do nisi duis veniam non est eiusmod tempor incididunt tempor dolor ipsum in qui sit. Exercitation mollit sit culpa nisi culpa non adipisicing reprehenderit do dolore. Duis reprehenderit occaecat anim ullamco ad duis occaecat ex.
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </section>
    <br>
    <%--<div class="container">
    <table class="table table-sm">
        <thead>
        <tr>
            <th scope="col">Title</th>
            <th scope="col">Author</th>
            <th scope="col">Song</th>
            <th scope="col">Cost</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="elem" items="${ songs }" varStatus="status">
            <tr>
                <th style="vertical-align : middle;" scope="row">${elem.title}</th>
                <th style="vertical-align : middle;" scope="row">${elem.title}</th>
                <th style="vertical-align : middle;">
                    <audio controls>
                        <source src=${elem.path} type="audio/mpeg">
                    </audio>
                </th>
                <th style="vertical-align : middle;" scope="row">${elem.cost}</th>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>--%>
    <%@include file="footer.jsp" %>
</body>
</html>

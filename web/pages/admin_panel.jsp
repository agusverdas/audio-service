<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 12.12.18
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
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
                           role="tab" aria-controls="nav-add" aria-selected="true">Add Song</a>
                        <a class="nav-item nav-link" id="nav-adda-tab" data-toggle="tab" href="#nav-adda"
                           role="tab" aria-controls="nav-adda" aria-selected="false">Add album</a>
                        <a class="nav-item nav-link" id="nav-contact-tab" data-toggle="tab" href="#nav-contact"
                           role="tab" aria-controls="nav-contact" aria-selected="false">See users</a>
                        <a class="nav-item nav-link" id="nav-about-tab" data-toggle="tab" href="#nav-about" role="tab"
                           aria-controls="nav-about" aria-selected="false">About</a>
                    </div>
                </nav>
                <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="nav-add" role="tabpanel"
                         aria-labelledby="nav-add-tab">
                        <form style="text-align: center;" method="post"
                              action="${pageContext.request.contextPath}/Controller" enctype="multipart/form-data">
                            <input type="hidden" name="command" value="post-add-song">
                            <label> Song title: <br>
                                <input type="text" name="title" required="required" maxlength="50">
                            </label>
                            <br>
                            <label> Author name: <br>
                                <input type="text" name="author" required="required" maxlength="50">
                            </label>
                            <br>
                            <label> Song cost: <br>
                                <input type="text" name="cost" required="required" maxlength="6">
                            </label>
                            <br>
                            <label> Song: <br>
                                <input style="width:200px" type="file" name="song" accept="audio/mpeg3">
                            </label>
                            <br>
                            <button type="submit" class="btn btn-primary btn-large">Add song</button>
                            <span style="color:red">${errorMessage}</span>
                        </form>
                    </div>
                    <div class="tab-pane fade" id="nav-adda" role="tabpanel" aria-labelledby="nav-adda-tab">
                        <form style="text-align: center;" method="post"
                              action="${pageContext.request.contextPath}/Controller" enctype="multipart/form-data">
                            <input type="hidden" name="command" value="post-add-album">
                            <label> Album title: <br>
                                <input type="text" name="title" required="required" maxlength="50">
                            </label>
                            <br>
                            <label> Author name: <br>
                                <input type="text" name="author" required="required" maxlength="50">
                            </label>
                            <br>
                            <label> Album photo: <br>
                                <input type="file" name="photo" required="required" maxlength="6">
                            </label>
                            <br>
                            <br>
                            <button type="submit" class="btn btn-primary btn-large">Add album</button>
                            <span style="color:red">${errorMessage}</span>
                        </form>
                    </div>
                    <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">
                        Et et consectetur ipsum labore excepteur est proident excepteur ad velit occaecat qui minim
                        occaecat veniam. Fugiat veniam incididunt anim aliqua enim pariatur veniam sunt est aute sit
                        dolor anim. Velit non irure adipisicing aliqua ullamco irure incididunt irure non esse
                        consectetur nostrud minim non minim occaecat. Amet duis do nisi duis veniam non est eiusmod
                        tempor incididunt tempor dolor ipsum in qui sit. Exercitation mollit sit culpa nisi culpa non
                        adipisicing reprehenderit do dolore. Duis reprehenderit occaecat anim ullamco ad duis occaecat
                        ex.
                    </div>
                    <div class="tab-pane fade" id="nav-about" role="tabpanel" aria-labelledby="nav-about-tab">
                        Et et consectetur ipsum labore excepteur est proident excepteur ad velit occaecat qui minim
                        occaecat veniam. Fugiat veniam incididunt anim aliqua enim pariatur veniam sunt est aute sit
                        dolor anim. Velit non irure adipisicing aliqua ullamco irure incididunt irure non esse
                        consectetur nostrud minim non minim occaecat. Amet duis do nisi duis veniam non est eiusmod
                        tempor incididunt tempor dolor ipsum in qui sit. Exercitation mollit sit culpa nisi culpa non
                        adipisicing reprehenderit do dolore. Duis reprehenderit occaecat anim ullamco ad duis occaecat
                        ex.
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

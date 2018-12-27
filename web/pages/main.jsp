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
    <div class="container">
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
    <%@include file="footer.jsp" %>
</body>
</html>

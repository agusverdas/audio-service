<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 11.12.18
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
    Name:    ${sessionScope.user.name}<br/>
    E-mail:  ${sessionScope.user.email}<br/>
    Photo:   ${sessionScope.user.photo}<br/>
    Role:    ${sessionScope.user.role}<br/>
    Bonus:   ${sessionScope.user.bonus}<br/>
    <a href="Controller?command=logout">Logout</a><br/>
    <a href="edit_profile.jsp">Edit profie</a>
</body>
</html>--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html>
<head>
    <title>Main page</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="header.jsp" %>
<br>
<div class="container">
    <div class="row">
        <div class="col-md-3 img">
            <img style="max-height: 100%; max-width: 100%" class="img-rounded" src=${user.photo}  />
        </div>
        <div class="col-md-3 details">
            <blockquote>
                <h5>${user.name}</h5>
            </blockquote>
            <p>
                ${user.email} <br>
                    <a href="edit_profile.jsp" class="btn btn-primary btn-block btn-large">Edit profile</a>
            </p>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>

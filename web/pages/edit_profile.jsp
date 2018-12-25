<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 11.12.18
  Time: 13:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit profile</title>
    <link href="../css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/jquery-3.3.1.min.js"></script>
</head>
<body>
    <%@include file="header.jsp" %>
    <div class="login">
        <h1>Edit profile</h1>
        <form method="post" action="/Controller" enctype="multipart/form-data">
            <input type="hidden" name="command" value="edit-profile">
            <input type="text" value="${user.name}" name="nick" required="required" maxlength="10">
            <input type="email" value="${user.email}" name="e-mail" required="required">
            <input type="file" name="photo">
            <button type="submit" class="btn btn-primary btn-block btn-large">OK</button>
            <span style="color:red">${errorMessage}</span>
        </form>
        <br>
    </div>
    <%@include file="footer.jsp" %>
</body>
</html>

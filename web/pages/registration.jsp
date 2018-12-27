<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 07.12.18
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<html>
<head>
    <title>Registration</title>
    <link href="../css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<div class="login">
    <h1>Registration</h1>
    <form method="post" action="/Controller">
        <input type="hidden" name="command" value="registration">
        <input type="email" name="e-mail" placeholder="E-mail" required="required" maxlength="32"/>
        <input type="text" name="nick" placeholder="Nickname" required="required" maxlength="10"/>
        <input type="password" name="password" placeholder="Password" required="required" minlength="6" maxlength="10"/>
        <button type="submit" class="btn btn-primary btn-block btn-large">OK</button>
        <span style="color:red">${errorMessage}</span>
    </form>
</div>
</body>
</html>

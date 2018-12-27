<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 04.12.18
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <link href="../css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>

<div class="login">
    <h1>Login</h1>
    <form method="post" action="Controller">
        <input type="hidden" name="command" value="login">
        <input type="email" name="e-mail" placeholder="E-mail" required="required" maxlength="32"/>
        <input type="password" name="password" placeholder="Password" required="required" minlength="6" maxlength="10"/>
        <button type="submit" class="btn btn-primary btn-block btn-large">Let me in.</button>
        <a class="btn btn-primary btn-block btn-large" href="${pageContext.request.contextPath}/pages/registration.jsp">Registration </a>
        <span style="color:red">${errorMessage}</span>
    </form>
    <br>
</div>

</body>
</html>

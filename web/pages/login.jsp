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
</head>
<body>
    <!-- todo: add localization -->
    <form method="POST" action="Controller">
        <fieldset>
            <legend>Вход в систему</legend>
            <input type="hidden" name="command" value="login">
            <p>
            <label> E-mail:
                <input type="email" name="e-mail" maxlength="32">
            </label>
            </p>
            <p>
            <label> Password:
                <input type="password" name="password" minlength="6" maxlength="10">
            </label>
            </p>
            <input type="submit" value="Submit">
            <a href="./pages/registration.jsp">Registration </a>
            <br/>
            <span style="color:red">${errorMessage}</span>
        </fieldset>
    </form>
</body>
</html>

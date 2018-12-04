<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 04.12.18
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form method="POST" action="Controller">
        <fieldset>
            <legend>Вход в систему</legend>
            <input type="hidden" name="command" value="login">
            <p>
            <label> E-mail:
                <input type="email" name="e-mail">
            </label>
            </p>
            <p>
            <label> Password:
                <input type="text" name="password">
            </label>
            </p>
            <input type="submit" value="Submit">

            ${errorLoginPassMessage}
        </fieldset>
    </form>
</body>
</html>

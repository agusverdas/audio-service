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
</head>
<body>
    <form method="POST" action="/Controller">
        <fieldset>
            <legend>Registration</legend>
            <input type="hidden" name="command" value="registration">
            <p>
            <label>E-mail:
                <input type="email" name="e-mail" maxlength="32">
            </label>
            </p>
            <p>
            <label>Name:
                <input type="text" name="nick" maxlength="10">
            </label>
            </p>
            <p>
            <label>Password:
                <input type="password" name="password" minlength="6" maxlength="8">
            </label>
            </p>
            <input type="submit" value="OK"><br>
            <span style="color:red">${errorRegistrationMsg}</span>
        </fieldset>
    </form>
</body>
</html>

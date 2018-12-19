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
</head>
<body>
    <!-- todo: add input for photo -->
    <form action="/Controller" method="POST" enctype="multipart/form-data">
        <fieldset>
            <legend>Profile info edit</legend>
            <input type="hidden" name="command" value="edit-profile">
            <p><label>
                    Name:
                    <input type="text" value="${user.name}" name="nick">
            </label></p>
            <p><label>
                    E-mail:
                    <input type="email" value="${user.email}" name="e-mail">
            </label></p>
            <p><label>
                    Photo:
                    <input type="file" name="photo">
            </label></p>
            <input type="submit" value="OK">
            <span style="color:red">${errorEditMsg}</span>
        </fieldset>
    </form>
</body>
</html>

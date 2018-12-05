<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 04.12.18
  Time: 12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
    <h3>Welcome</h3>
    <hr/>
        ${sessionScope.name}, hello!
    <hr/>
    <a href="Controller?command=logout">Logout</a>
</body>
</html>

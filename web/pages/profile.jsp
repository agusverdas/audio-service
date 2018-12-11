<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 11.12.18
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
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
    <a href="Controller?command=logout">Logout</a>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 12.12.18
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin panel</title>
</head>
<body>
<!-- todo: change for admin -->
<table>
    <c:forEach var="elem" items="${ userList }">

    </c:forEach>
    <thead>
    <tr>
        <th>Id</th>
        <th>E-mail</th>
        <th>User name</th>
        <th>Role</th>
        <th>Bonus</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>John Lennon</td>
        <td>Rhythm Guitar</td>
    </tr>
    <tr>
        <td>Paul McCartney</td>
        <td>Bass</td>
    </tr>
    <tr>
        <td>George Harrison</td>
        <td>Lead Guitar</td>
    </tr>
    <tr>
        <td>Ringo Starr</td>
        <td>Drums</td>
    </tr>
    </tbody>
</table>
</body>
</html>

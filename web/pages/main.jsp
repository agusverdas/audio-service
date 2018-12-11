<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 04.12.18
  Time: 12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
    <h3>Welcome</h3>
    <hr/>
        ${sessionScope.user.name}, hello!
    <hr/>
    <c:if test="${sessionScope.role =='ADMIN'}">
        <c:out value="You are admin" />
    </c:if>
    <!--todo: ask Можно ли так вызывать logout?-->
    <!--todo: ask Если нужно, то как замазать URL?-->
    <a href="./pages/profile.jsp">Go to profile</a>
    <a href="Controller?command=logout">Logout</a>
</body>
</html>

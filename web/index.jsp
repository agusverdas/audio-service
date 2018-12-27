<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 04.12.18
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--todo: ask Делать проверку из сессии это хорошая идея?-->
<html>
  <head>
    <title>Start page</title>
  </head>
  <body>
    <c:if test="${sessionScope.user != null}">
      <jsp:forward page="pages/main.jsp"/>
    </c:if>
    <jsp:forward page="pages/login.jsp"/>
  </body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 04.12.18
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Start page</title>
  </head>
  <body>
    <c:if test="${sessionScope.user != null}">
      <jsp:forward page="/Controller?command=get-main"/>
    </c:if>
    <jsp:forward page="pages/login.jsp"/>
  </body>
</html>

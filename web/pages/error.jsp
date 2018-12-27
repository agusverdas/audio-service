<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 04.12.18
  Time: 12:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error jsp</title>
</head>
<body>
Request from ${pageContext.errorData.requestURI} is failed
<br/>
Exception: ${pageContext.exception}
<br/>
Message from exception: ${pageContext.exception.message}
<br/>
<c:forEach items="${pageContext.exception.stackTrace}" var="element">
    <c:out value="${element}"/>
</c:forEach>
<a href="../index.jsp">Go Back</a>
</body>
</html>

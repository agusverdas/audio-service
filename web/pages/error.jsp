<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 04.12.18
  Time: 12:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
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
    <a href="../index.jsp">Go Back</a>
</body>
</html>

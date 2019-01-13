<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 04.12.18
  Time: 12:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html lang="${sessionScope.locale.language}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="title.error" bundle="${rb}"/></title>
    <link href="../css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body style="overflow-y: scroll;">
<br/>
<h2 style="text-align: center; color: white;">Error page</h2>
<div class="jumbotron vertical-center">
<div class="col-lg-12 container">
    <fmt:message key="text.exception.path" bundle="${rb}"/> ${pageContext.errorData.requestURI}
    <br/>
    <fmt:message key="text.exception.type" bundle="${rb}"/> ${pageContext.exception}
    <br/>
    <fmt:message key="text.exception.message" bundle="${rb}"/> ${pageContext.exception.message}
    <br/>
    <c:forEach items="${pageContext.exception.stackTrace}" var="element">
        <c:out value="${element}"/><br/>
    </c:forEach>
    <a class="btn btn-primary btn-block btn-large" href="../index.jsp"><fmt:message key="navbar.main"
                                                                                    bundle="${rb}"/></a>
</div>
</div>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 04.12.18
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html lang="${sessionScope.locale.language}">
<head>
    <title><fmt:message key="title.login" bundle="${rb}"/></title>
    <link href="../css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body style="overflow-y: scroll;">
<div class="login">
    <h1><fmt:message key="header.login" bundle="${rb}"/></h1>
    <form method="post" action="${pageContext.request.contextPath}/Controller">
        <input type="hidden" name="command" value="post-login">
        <input type="email" name="e-mail" placeholder=
        <fmt:message key="placeholder.email" bundle="${rb}"/> required="required" maxlength="32"/>
        <input type="password" name="password" placeholder=
        <fmt:message key="placeholder.password" bundle="${rb}"/> required="required" minlength="6" maxlength="10"/>
        <button type="submit" class="btn btn-primary btn-block btn-large"><fmt:message key="button.login"
                                                                                       bundle="${rb}"/></button>
        <a class="btn btn-primary btn-block btn-large"
           href="${pageContext.request.contextPath}/Controller?command=get-registration"><fmt:message
                key="button.registration" bundle="${rb}"/></a>
        <c:if test="${ not empty errorMessage }">
            <span style="color:red">
                <fmt:message key="${ errorMessage }" bundle="${rb}"/>
            </span>
        </c:if>
    </form>
    <br>
</div>
</body>
</html>
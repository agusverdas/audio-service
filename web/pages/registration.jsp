<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 07.12.18
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf-8">
    <title><fmt:message key="label.title.registration" bundle="${rb}"/></title>
    <link href="../css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<div class="login">
    <h1><fmt:message key="label.button.registration" bundle="${rb}"/></h1>
    <form method="post" action="${pageContext.request.contextPath}/Controller">
        <input type="hidden" name="command" value="post-registration">
        <input type="email" name="e-mail" placeholder="<fmt:message key="label.registration.email" bundle="${rb}"/>"
               required="required" maxlength="32"/>
        <input type="text" name="nick" placeholder="<fmt:message key="label.registration.name" bundle="${rb}"/>"
               required="required" minlength="6" maxlength="16"/>
        <input type="password" name="password"
               placeholder="<fmt:message key="label.registration.password" bundle="${rb}"/>" required="required"
               minlength="6" maxlength="10"/>
        <button type="submit" class="btn btn-primary btn-block btn-large">OK</button>
        <span style="color:red">${errorMessage}</span>
    </form>
</div>
</body>
</html>

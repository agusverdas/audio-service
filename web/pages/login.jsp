<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 04.12.18
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <title><fmt:message key="label.title.login" bundle="${rb}"/></title>
    <link href="../css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>

<div class="login">
    <h1><fmt:message key="label.button.login" bundle="${rb}"/></h1>
    <form method="post" action="Controller">
        <input type="hidden" name="command" value="post-login">
        <input type="email" name="e-mail" placeholder=
        <fmt:message key="label.placeholder.email" bundle="${rb}"/> required="required" maxlength="32"/>
        <input type="password" name="password" placeholder=
        <fmt:message key="label.placeholder.password" bundle="${rb}"/> required="required" minlength="6"
               maxlength="10"/>
        <button type="submit" class="btn btn-primary btn-block btn-large"><fmt:message key="label.button.login"
                                                                                       bundle="${rb}"/></button>
        <a class="btn btn-primary btn-block btn-large"
           href="${pageContext.request.contextPath}/Controller?command=get-registration"><fmt:message
                key="label.button.registration" bundle="${rb}"/></a>
        <span style="color:red">${errorMessage}</span>
    </form>
    <br>
</div>

</body>
</html>
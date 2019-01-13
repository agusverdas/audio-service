<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 11.12.18
  Time: 13:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html lang="${sessionScope.locale.language}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="title.edit.profile" bundle="${rb}"/></title>
    <link href="../css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body style="overflow-y: scroll;">
<%@include file="header.jsp" %>
<div class="login">
    <h1><fmt:message key="header.edit.profile" bundle="${rb}"/></h1>
    <form method="post" action="${pageContext.request.contextPath}/Controller" enctype="multipart/form-data">
        <input type="hidden" name="command" value="post-edit-profile">
        <input type="text" value="${user.name}" name="nick" required="required" minlength="6" maxlength="16">
        <input type="email" value="${user.email}" name="e-mail" required="required" maxlength="32">
        <input type="file" name="photo" accept="image/jpeg, image/png">
        <button type="submit" class="btn btn-primary btn-block btn-large">OK</button>
        <c:if test="${ not empty errorMessage }">
            <span style="color:red">
                <fmt:message key="${ errorMessage }" bundle="${rb}"/>
            </span>
        </c:if>
    </form>
    <br>
</div>
<%@include file="footer.jsp" %>
</body>
</html>

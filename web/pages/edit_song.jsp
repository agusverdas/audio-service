<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 01.01.19
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="label.title.edit.songs" bundle="${rb}"/></title>
    <link href="../css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <style>
        input[type='number'] {
            -moz-appearance:textfield;
        }

        input::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button {
            -webkit-appearance: none;
        }
    </style>
</head>
<body style="overflow-y: scroll;">
<%@include file="header.jsp" %>
<div class="login">
    <h1><fmt:message key="label.title.edit.songs" bundle="${rb}"/></h1>
    <form method="post" action="${pageContext.request.contextPath}/Controller" enctype="multipart/form-data">
        <input type="hidden" name="command" value="post-edit-song">
        <input type="hidden" name="id" value="${requestScope.entityId}">
        <!--todo: add validation, max numbers in decomal -->
        <input type="number" min="0" step=".01" name="cost" placeholder=
                "<fmt:message key="label.placeholder.cost" bundle="${rb}"/>" maxlength="8"><br>
        <input type="file" name="song" accept="audio/mpeg3"><br>
        <button type="submit" class="btn btn-primary btn-block btn-large"><fmt:message key="label.title.update"
                                                                             bundle="${rb}"/></button>
        <br>
        <span style="color:red">${errorMessage}</span>
    </form>
    <br>
</div>
<%@include file="footer.jsp" %>
</body>
</html>

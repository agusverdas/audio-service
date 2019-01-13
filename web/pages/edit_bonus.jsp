<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 01.01.19
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html lang="${sessionScope.locale.language}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="title.edit.bonus" bundle="${rb}"/></title>
    <link href="../css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <style>
        input[type='number'] {
            -moz-appearance: textfield;
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
    <h1><fmt:message key="header.edit.bonus" bundle="${rb}"/></h1>
    <form method="post" action="${pageContext.request.contextPath}/Controller">
        <input type="hidden" name="command" value="post-edit-bonus">
        <input type="hidden" name="id" value="${entityId}">
        <input type="number" min="0" step=".01" placeholder=
        <fmt:message key="placeholder.new.bonus" bundle="${rb}"/> name="bonus" required="required" maxlength="6">
        <button type="submit" class="btn btn-primary btn-block btn-large"><fmt:message key="button.ok"
                                                                                       bundle="${rb}"/></button>
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
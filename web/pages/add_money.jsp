<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 01.01.19
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html lang="${sessionScope.locale.language}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="title.add.money" bundle="${rb}"/></title>
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
    <h1><fmt:message key="header.add.money" bundle="${rb}"/></h1>
    <form method="post" action="${pageContext.request.contextPath}/Controller">
        <input type="hidden" name="command" value="post-add-money">
        <input type="number" name="money" min="0" step=".01"
               placeholder="<fmt:message key="placeholder.money" bundle="${rb}"/>"
               required="required" maxlength="10">
        <button type="submit" class="btn btn-primary btn-block btn-large"><fmt:message key="button.ok"
                                                                                       bundle="${rb}"/></button>
        <span style="color:red">${errorMessage}</span>
    </form>
    <br>
</div>
<%@include file="footer.jsp" %>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 02.01.19
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html lang="${sessionScope.locale.language}">
<head>
    <title><fmt:message key="title.song.payment" bundle="${rb}"/></title>
    <link href="../css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body style="overflow-y: scroll;">
<%@include file="header.jsp" %>
<div class="login">
    <h1>Song cost ${song.cost}$</h1>
    <form method="post" action="${pageContext.request.contextPath}/Controller">
        <input type="hidden" name="command" value="post-buy-song"/>
        <input type="hidden" name="id" value="${song.songId}">
        <input style="width: auto;" type="radio" name="payment" value="pocket" checked="checked"/>
        <span style="color: white; font-size: 1.25em;"> Pocket (${user.money}$)</span><br>
        <input style="width: auto;" type="radio" name="payment" value="bonus"/>
        <span style="color: white; font-size: 1.25em;"> Bonus (${user.bonus}$)</span><br>
        <button type="submit" class="btn btn-primary btn-block btn-large">Buy</button>
        <span style="color:red">${errorMessage}</span>
    </form>
    <br>
</div>
<%@include file="footer.jsp" %>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 11.12.18
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent" var="rb"/>
<html>
<head>
    <meta charset="utf-8">
    <title><fmt:message key="label.title.profile" bundle="${rb}"/></title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link href="../css/styles.css" rel="stylesheet">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body style="overflow-y: scroll;">
<%@include file="header.jsp" %>
<br>
<div style="border: 2px solid #000;
border-radius: 15px;
-moz-border-radius: 15px;" class="container">
    <div class="row">
        <div class="col-md-5 img">
            <img style="max-height: 100%; max-width: 100%" class="img-rounded" src="${user.photo}"/>
        </div>
        <div class="col-md-5 details">
            <blockquote>
                <h5>${user.name}</h5>
            </blockquote>
            <p>
                <fmt:message key="label.placeholder.email" bundle="${rb}"/>: ${user.email}<br/>
                <fmt:message key="label.placeholder.role" bundle="${rb}"/>: ${user.role}<br/>
                <fmt:message key="label.placeholder.money" bundle="${rb}"/>: ${user.money}<br/>
                <fmt:message key="label.placeholder.bonus" bundle="${rb}"/>: ${user.bonus}<br/><br/>
                <a href="${pageContext.request.contextPath}/Controller?command=get-edit"
                   class="btn btn-primary btn-block btn-large"><fmt:message key="label.title.edit.profile"
                                                                            bundle="${rb}"/></a><br>
                <a href="${pageContext.request.contextPath}/Controller?command=get-add-money"
                   class="btn btn-primary btn-block btn-large"><fmt:message key="label.title.add.money"
                                                                            bundle="${rb}"/></a>
            </p>
        </div>
    </div>
</div>
<footer class="fixed-bottom page-footer font-small">
    <div class="footer-copyright text-center py-3">&copy; 2018 Copyright: alexandergusakovsky@gmail.com
    </div>
</footer>
</body>
</html>

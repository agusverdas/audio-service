<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: verdas
  Date: 25.12.18
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/Controller?command=get-main">
                <fmt:message key="label.navbar.home" bundle="${rb}"/></a>
            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/Controller?command=get-profile">
                <fmt:message key="label.navbar.profile" bundle="${rb}"/></a>
            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/Controller?command=get-logout">
                <fmt:message key="label.navbar.logout" bundle="${rb}"/></a>
            <ctg:admin-button/>
        </div>
    </div>
</nav>

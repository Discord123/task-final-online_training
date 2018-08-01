<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty sessionScope.locale}">
    <c:set var="locale" value="en_US" scope="session"  />
</c:if>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb" />

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="../css/bootstrap.css">
    <link rel="icon" href="../fonts/favicon.ico">
    <title>Login into courses page</title>
</head>
<body>
<jsp:include page="/jsp/parts/header.jsp"/>

<div class="container" role="main">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <form class="form-signin"  name="loginForm" method="POST" action="/controller">

                <input type="hidden" name="command" value="login" />

                <h2 class="form-signin-heading" align="center"><fmt:message key="label.login.header" bundle="${rb}"/></h2>
                <br>
                <label for="inputEmail" class="sr-only"><fmt:message key="label.registration.email" bundle="${rb}"/></label>
                <input type="email" name="user_email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
                <br>
                <label for="inputPassword" class="sr-only"><fmt:message key="label.registration.password" bundle="${rb}"/></label>
                <input type="password" name="user_password" id="inputPassword" class="form-control" placeholder="Password" required>
                <br>
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="label.login.login-button" bundle="${rb}" /></button>
                <br>

            </form>
            <a href="/controller?command=getPage&expectedPage=recovery"><fmt:message key="label.login.forgot-password" bundle="${rb}"/></a>
        </div>
    </div>
</div>
<div class="col-md-4"></div>
<jsp:include page="/jsp/parts/footer.jsp"/>
</body>
</html>

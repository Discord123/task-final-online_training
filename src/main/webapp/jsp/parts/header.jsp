<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="../../css/bootstrap.css">
    <link rel="icon" href="../../../fonts/favicon.ico">
</head>

<body>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-togle="collapse" data-target=#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="index.jsp"><fmt:message key="label.navbar.admin.main"  bundle="${rb}"/></a></li>
                <li><a href="/controller?command=getPage&expectedPage=registration"><fmt:message key="label.login.registration" bundle="${rb}"/></a></li>
                <li><a href="/controller?command=getPage&expectedPage=login"><fmt:message key="label.login.login-button" bundle="${rb}"/></a></li>
            </ul>
            <ul class="nav navbar-nav active pull-right">
                <li>
                    <form action="/controller">
                        <input type="hidden" name="command" value="locale">
                        <button type="submit" class="btn btn-default navbar-btn" name="locale" value="RU" >RU</button>
                    </form>
                </li>
                <li>
                    <form action="/controller">
                        <input type="hidden" name="command" value="locale">
                        <button type="submit" class="btn btn-default navbar-btn" name="locale" value="EN" >EN</button>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
<%--message processor--%>
<div class="container">
    <div class="col-md-2"></div>
    <div class="col-md-8">
        <c:if test="${not empty sessionScope.actionSuccessful}">
            <div class="alert alert-success" role="alert" align="center">
                <fmt:message key="${sessionScope.actionSuccessful}" bundle="${rb}"/>
            </div>
            <c:remove var="actionSuccessful" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.actionFail}">
            <div class="alert alert-danger" role="alert" align="center">
                <fmt:message key="${sessionScope.actionFail}" bundle="${rb}"/>
            </div>
            <c:remove var="actionFail" scope="session"/>
        </c:if>
    </div>
    <div class="col-md-2"></div>
</div>
</body>
</html>

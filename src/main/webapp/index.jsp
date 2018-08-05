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
    <title>Free language courses</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="../../css/bootstrap.css">
    <link rel="icon" href="../../../fonts/favicon.ico">
</head>

<body>

<jsp:include page="/jsp/parts/header.jsp"/>

<div class="intro">
    <div class="layout-positioner">
        <h1 class="promo"><fmt:message key="index.text" bundle="${rb}"/></h1>
    </div>
</div>
</body>
</html>

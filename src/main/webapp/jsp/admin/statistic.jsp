<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Admin page</title>
</head>
<body>
<jsp:include page="../../jsp/admin/parts/header.jsp"/>
<H1 align="center"><fmt:message key="label.statistic.title" bundle="${rb}"/></H1>

<div class="container theme-showcase" role="main">
    <h3><fmt:message key="label.statistic.users-count" bundle="${rb}"/>${sessionScope.usersCount}</h3>
    <h3><fmt:message key="label.statistic.teachers-count" bundle="${rb}"/>${sessionScope.teachersCount}</h3>
    <h3><fmt:message key="label.statistic.tasks-count" bundle="${rb}"/>${sessionScope.tasksCount}</h3>
    <h3><fmt:message key="label.statistic.courses-count" bundle="${rb}"/>${sessionScope.coursesCount}</h3>
    <h3><fmt:message key="label.statistic.subjects-count" bundle="${rb}"/>${sessionScope.subjectsCount}</h3>
    <br/>
</div>

<jsp:include page="../../jsp/admin/parts/footer.jsp"/>
</body>
</html>

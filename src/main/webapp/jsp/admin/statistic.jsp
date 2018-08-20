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
    <h3><fmt:message key="label.statistic.users-count" bundle="${rb}"/>${sessionScope.getStatistic.usersCount}</h3>
    <h3><fmt:message key="label.statistic.teachers-count" bundle="${rb}"/>${sessionScope.getStatistic.teachersCount}</h3>
    <h3><fmt:message key="label.statistic.tasks-count" bundle="${rb}"/>${sessionScope.getStatistic.tasksCount}</h3>
    <h3><fmt:message key="label.statistic.courses-count" bundle="${rb}"/>${sessionScope.getStatistic.coursesCount}</h3>
    <h3><fmt:message key="label.statistic.subjects-count" bundle="${rb}"/>${sessionScope.getStatistic.subjectsCount}</h3>
    <h3><fmt:message key="label.statistic.count-people-study-english" bundle="${rb}"/>${sessionScope.getStatistic.englishLanguageCount}</h3>
    <h3><fmt:message key="label.statistic.count-people-study-german" bundle="${rb}"/>${sessionScope.getStatistic.germanLanguageCount}</h3>
    <h3><fmt:message key="label.statistic.count-people-study-french" bundle="${rb}"/>${sessionScope.getStatistic.frenchLanguageCount}</h3>
    <h3><fmt:message key="label.statistic.count-people-study-italian" bundle="${rb}"/>${sessionScope.getStatistic.italianLanguageCount}</h3>
    <h3><fmt:message key="label.statistic.count-people-study-spanish" bundle="${rb}"/>${sessionScope.getStatistic.spanishLanguageCount}</h3>
    <br/>
</div>

<jsp:include page="../../jsp/admin/parts/footer.jsp"/>
</body>
</html>

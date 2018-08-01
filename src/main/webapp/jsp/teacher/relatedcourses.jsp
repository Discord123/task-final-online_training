<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb" />

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Related courses</title>
</head>
<body>
<jsp:include page="../../jsp/teacher/parts/header.jsp"/>

<H1 align="center"><fmt:message key="label.relatedcourses.h1" bundle="${rb}"/></H1>

<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-12">
            <br/>
            <c:choose>
                <c:when test="${sessionScope.relatedCourses.size() eq 0}">
                    <%--Message block--%>
                    <div class="alert alert-info " role="alert" align="center">
                        <fmt:message key="message.teacher.no-related-courses" bundle="${rb}"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th><fmt:message key="label.availablecourses.title" bundle="${rb}"/></th>
                            <th><fmt:message key="label.availablecourses.subject-language" bundle="${rb}"/></th>
                            <th><fmt:message key="label.availablecourses.subject-level" bundle="${rb}"/></th>
                            <th><fmt:message key="label.availablecourses.status" bundle="${rb}"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${sessionScope.relatedCourses}" var="courses">
                            <tr>
                                <td>${courses.title}</td>
                                <td>${courses.subject.language}</td>
                                <td>${courses.subject.level}</td>
                                <td>${courses.status}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<jsp:include page="../../jsp/teacher/parts/footer.jsp"/>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb" />

<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="label.student.title" bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="../../jsp/student/parts/header.jsp"/>

<H1 align="center"><fmt:message key="label.student.joined-courses" bundle="${rb}"/></H1>

<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-12">
            <br/>
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th><fmt:message key="label.availablecourses.title" bundle="${rb}"/></th>
                    <th><fmt:message key="label.availablecourses.subject-language" bundle="${rb}"/></th>
                    <th><fmt:message key="label.availablecourses.subject-level" bundle="${rb}"/></th>
                    <th><fmt:message key="label.availablecourses.status" bundle="${rb}"/></th>
                    <th><fmt:message key="label.allcourses.teacher-fullname" bundle="${rb}"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.takenCourses}" var="courses">
                    <tr>
                        <td>${courses.title}</td>
                        <td>${courses.subject.language}</td>
                        <td>${courses.subject.level}</td>
                        <td>${courses.status}</td>
                        <td>${courses.teacher.lastName} ${courses.teacher.firstName}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="../../jsp/student/parts/footer.jsp"/>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb" />

<!DOCTYPE html>
<html lang="en">
<head>
    <title>All teachers</title>
</head>
<body>
<jsp:include page="../../jsp/admin/parts/header.jsp"/>
<c:if test="${empty sessionScope.allTeachers}">
    <jsp:forward page="/controller?command=showallteachers"></jsp:forward>
</c:if>

<H1 align="center"><fmt:message key="label.allteachers.title" bundle="${rb}"/></H1>

<div class="container theme-showcase" role="main">
    <div class="col-md-4"></div>
    <div class="col-md-4"></div>
    <div class="col-md-4"></div>
    <div class="row">
        <div class="col-md-12">
            <br/>
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th><fmt:message key="label.allteachers.teacheremail" bundle="${rb}"/></th>
                    <th><fmt:message key="label.allteachers.teachername" bundle="${rb}"/></th>
                    <th><fmt:message key="label.allteachers.teacher-lastname" bundle="${rb}"/></th>
                    <th width="80"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sessionScope.allTeachers}" var="teachers">
                    <tr>
                        <td>${teachers.email}</td>
                        <td>${teachers.firstName}</td>
                        <td>${teachers.lastName}</td>
                        <td>
                            <form action="/controller">
                                <input type="hidden" name="command" value="deleteuser">
                                <button type="submit" class="btn btn-danger btn-sm" name="userid" value="${teachers.id}" ><fmt:message key="label.allteachers.delete-teacher-btn" bundle="${rb}"/></button>
                            </form>
                        </td>
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

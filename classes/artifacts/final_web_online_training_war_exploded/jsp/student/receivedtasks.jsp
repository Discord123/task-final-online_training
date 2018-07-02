<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb" />

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Available courses</title>
</head>
<body>
<jsp:include page="../../jsp/student/parts/header.jsp"/>

<H1 align="center"><fmt:message key="label.receivedtasks.title" bundle="${rb}"/></H1>

<div class="container theme-showcase" role="main">
    <div class="col-md-4"></div>
    <div class="col-md-4">
    </div>
    <div class="col-md-4"></div>
    <div class="row">
        <div class="col-md-12">

            <br/>
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th><fmt:message key="label.receivedtasks.taskname" bundle="${rb}"/></th>
                    <th><fmt:message key="label.receivedtasks.taskdescription" bundle="${rb}"/></th>
                    <th><fmt:message key="label.receivedtasks.taskmark" bundle="${rb}"/></th>
                    <th><fmt:message key="label.receivedtasks.taskreview" bundle="${rb}"/></th>
                    <th width="80"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.receivedTasks}" var="tasks">
                    <tr>
                        <td>${tasks.name}</td>
                        <td>${tasks.description}</td>
                        <td>
                            <c:choose>
                                <c:when test="${tasks.review.mark eq 0}">
                                    <fmt:message key="label.receivedtasks.tasknotreviewed" bundle="${rb}"/>
                                </c:when>
                                <c:otherwise>
                                    ${tasks.review.mark}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${tasks.review.review}</td>
                        <td>
                            <c:choose>
                                <c:when test="${tasks.review.mark eq 0}">
                                    <form method="POST" action="/controller">
                                        <input type="hidden"  name="command" value="getpage" />
                                        <input type="hidden"  name="expectedPage" value="sendanswer" />
                                        <input type="hidden"  name="taskid" value="${tasks.id}" />
                                        <button type="submit" class="btn btn-success"><fmt:message key="label.receivedtasks.send-answer" bundle="${rb}"/></button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="label.receivedtasks.tasksended" bundle="${rb}"/>
                                </c:otherwise>
                            </c:choose>
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

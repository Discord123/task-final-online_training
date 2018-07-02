<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Task reviews</title>
</head>
<body>
<jsp:include page="../../jsp/teacher/parts/header.jsp"/>

<H1 align="center"><fmt:message key="label.taskreviews.h1" bundle="${rb}"/></H1>


<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-12">
            <br/>
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th><fmt:message key="label.taskreviews.student-name" bundle="${rb}"/></th>
                    <th><fmt:message key="label.taskreviews.is-task-complete" bundle="${rb}"/></th>
                    <th><fmt:message key="label.taskreviews.mark" bundle="${rb}"/></th>
                    <th><fmt:message key="label.taskreviews.review" bundle="${rb}"/></th>
                    <th width="80"></th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sessionScope.reviewsAndUsers}" var="reviews">
                    <tr>
                        <td>${reviews.lastName} ${reviews.firstName}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty reviews.answer}">
                                    <fmt:message key="label.taskreviews.answer-sended" bundle="${rb}"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="label.taskreviews.answer-empty" bundle="${rb}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${reviews.mark eq 0}">
                                    <fmt:message key="label.receivedtasks.tasknotreviewed" bundle="${rb}"/>
                                </c:when>
                                <c:otherwise>
                                    ${reviews.mark}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty reviews.review}">
                                    <fmt:message key="label.taskreviews.review-done" bundle="${rb}"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="label.taskreviews.review-empty" bundle="${rb}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <form method="POST" action="/controller">
                                <input type="hidden" name="command" value="getPage" />
                                <input type="hidden"  name="expectedPage" value="reviewtask" />
                                <input type="hidden"  name="user_id" value="${reviews.userId}" />
                                <input type="hidden"  name="task_id" value="${reviews.taskId}" />
                                <button type="submit" class="btn btn-info"><fmt:message key="label.taskreviews.review-btn" bundle="${rb}"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="../../jsp/teacher/parts/footer.jsp"/>
</body>
</html>
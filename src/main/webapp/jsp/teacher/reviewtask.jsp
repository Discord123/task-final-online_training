<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Related tasks</title>
</head>
<body>
<jsp:include page="../../jsp/teacher/parts/header.jsp"/>

<H1 align="center"><fmt:message key="label.reviewtask.h1" bundle="${rb}"/></H1>
<div class="col-md-2"></div>
<div class="col-md-8">
<dl class="dl-horizontal">
    <dt><fmt:message key="label.reviewtask.task" bundle="${rb}"/></dt>
    <dd>
        <c:forEach items="${sessionScope.relatedTasks}" var="tasks">
            <c:if test="${tasks.id eq param.task_id}">
                ${tasks.description}
            </c:if>
        </c:forEach>
    </dd>
    <dt><fmt:message key="label.reviewtask.answer" bundle="${rb}"/></dt>
    <dd>
        <c:forEach items="${sessionScope.reviewsAndUsers}" var="reviews">
            <c:if test="${reviews.userId eq param.user_id and reviews.taskId eq param.task_id}">
                ${reviews.answer}
            </c:if>
        </c:forEach>
    </dd>
</dl>
    <hr>
</div>
<div class="col-md-2"></div>


<div class="container" role="main">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <H2 align="center"><fmt:message key="label.reviewtask.h2" bundle="${rb}"/></H2>

        <form name="editCourseForm" method="POST" action="/controller">
            <input type="hidden" name="command" value="sendreview"/>
            <input type="hidden" name="task_id" value="${param.task_id}"/>
            <input type="hidden" name="user_id" value="${param.user_id}"/>

            <div class="control-group">
                <!-- Mark -->
                <label for="task_mark"><fmt:message
                        key="label.reviewtask.mark" bundle="${rb}"/></label>
                <div class="controls">
                    <select name="task_mark" id="task_mark" required class="form-control">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                    </select>
                </div>
            </div>
            <br>
            <div class="control-group">
                <!-- Review -->
                <div class="form-group">
                    <label for="task_review"><fmt:message
                            key="label.reviewtask.review" bundle="${rb}"/></label>
                    <textarea class="form-control" id="task_review" name="task_review" rows="10"></textarea>
                </div>
            </div>

            <button type="submit" class="btn btn-primary"><fmt:message key="label.reviewtask.send-review-btn"
                                                                      bundle="${rb}"/></button>
        </form>
    </div>
    <div class="col-md-4"></div>
</div>

<jsp:include page="../../jsp/teacher/parts/footer.jsp"/>
</body>
</html>
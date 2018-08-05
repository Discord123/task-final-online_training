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

<H1 align="center"><fmt:message key="label.relatedtasks.h1" bundle="${rb}"/></H1>

<%--Message block--%>
<c:if test="${sessionScope.relatedCoursesSize eq 0}">
    <div class="alert alert-info " role="alert" align="center">
        You have no related courses
    </div>
</c:if>
<hr>
<div class="container" role="main">
    <div class="col-md-4"></div>
    <div class="col-md-4">

        <form name="editCourseForm" method="GET" action="/controller">
            <input type="hidden" name="command" value="takecourserelatedtasks"/>
            <div class="control-group">
                <!-- Related courses -->
                <label class="control-label" for="course_id"><fmt:message key="label.relatedtasks.select_course"
                                                                                  bundle="${rb}"/><span
                        class="required"></span></label>
                <div class="controls">
                    <select name="course_id" id="course_id" required class="form-control">
                        <c:forEach items="${sessionScope.relatedCourses}" var="courses">
                            <option value="${courses.id}">${courses.title}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <br>
            <button type="submit" class="btn btn-primary" ><fmt:message key="label.relatedtasks.select-btn" bundle="${rb}"/></button>
        </form>
    </div>
    <div class="col-md-4"></div>
    <br>
</div>

<c:if test="${not empty sessionScope.relatedTasks}">
    <hr>
    <div class="container theme-showcase" role="main">
        <div class="row">
            <div class="col-md-12">
                <br/>
                <table class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th><fmt:message key="label.relatedtasks.task-id" bundle="${rb}"/></th>
                        <th><fmt:message key="label.relatedtasks.task-title" bundle="${rb}"/></th>
                        <th><fmt:message key="label.relatedtasks.task-description" bundle="${rb}"/></th>
                        <th width="80"></th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sessionScope.relatedTasks}" var="tasks">
                        <tr>
                            <td>${tasks.id}</td>
                            <td>${tasks.name}</td>
                            <td>${tasks.description}</td>
                            <td>
                                <form method="GET" action="/controller">
                                    <input type="hidden" name="command" value="takereviewsbytaskid"/>
                                    <input type="hidden" name="taskid" value="${tasks.id}"/>
                                    <button type="submit" class="btn btn-info"><fmt:message
                                            key="label.relatedtasks.task-info-btn" bundle="${rb}"/></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</c:if>
<jsp:include page="../../jsp/teacher/parts/footer.jsp"/>
</body>
</html>
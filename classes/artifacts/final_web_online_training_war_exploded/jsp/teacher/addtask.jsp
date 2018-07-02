<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Add task</title>
</head>
<body>
<jsp:include page="../../jsp/teacher/parts/header.jsp"/>

<H1 align="center"><fmt:message key="label.addtask.h1" bundle="${rb}"/></H1>
<div class="container" role="main">
    <div class="col-md-4"></div>
    <div class="col-md-4">

        <form name="addNewTaskForm" method="POST" action="/controller">
            <input type="hidden" name="command" value="addtask"/>
            <input type="hidden" name="addCourse" value="go"/>


            <div class="control-group">
                <!-- Course -->
                <label class="control-label" for="course_id"><fmt:message key="label.addtask.select-course"
                                                                           bundle="${rb}"/><span
                        class="required">*</span></label>
                <div class="controls">
                    <select name="course_id" id="course_id" required class="form-control">
                        <c:forEach items="${sessionScope.relatedCourses}" var="courses">
                            <option value="${courses.id}">${courses.title}</option>
                        </c:forEach>
                    </select>
                    <p class="help-block"><fmt:message key="label.addtask.select-course-help" bundle="${rb}"/></p>
                </div>
            </div>

            <div class="control-group">
                <!-- Title -->
                <label class="control-label" for="task_name"><fmt:message
                        key="label.addtask.task-title" bundle="${rb}"/><span class="required">*</span></label>
                <div class="controls">
                    <input type="text" class="form-control" id="task_name" name="task_name"
                           required="" pattern="[А-Яа-я\w\s.,?!-+#%_()]{5,45}"/>
                    <p class="help-block"><fmt:message key="label.addtask.task-title-help" bundle="${rb}"/></p>
                </div>
            </div>

            <div class="control-group">
                <!-- Description -->
                <label class="control-label" for="task_description"><fmt:message
                        key="label.addtask.task-description" bundle="${rb}"/><span class="required">*</span></label>
                <div class="controls">
                    <textarea type="text" rows="8" class="form-control" id="task_description" name="task_description"
                              required="" pattern="[А-Яа-я\w\s.,?!-+#%_()]{5,450}"></textarea>
                    <p class="help-block"><fmt:message key="label.addtask.task-description-help" bundle="${rb}"/></p>
                </div>
            </div>

            <br>
            <p><span class="required">*</span> - <fmt:message key="label.form.required-fields" bundle="${rb}"/></p>
            <button type="submit" class="btn btn-success"><fmt:message key="label.addtask.task-add-btn"
                                                                      bundle="${rb}"/></button>
        </form>
    </div>
    <div class="col-md-4"></div>
</div>
<jsp:include page="../../jsp/admin/parts/footer.jsp"/>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Add new course</title>
</head>
<body>
<jsp:include page="../../jsp/admin/parts/header.jsp"/>

<H1 align="center"><fmt:message key="label.addcourse.title" bundle="${rb}"/></H1>
<div class="container" role="main">
    <div class="col-md-4"></div>
    <div class="col-md-4">

        <form name="addNewCourseForm" method="POST" action="/controller">
            <input type="hidden" name="command" value="addcourse"/>
            <input type="hidden" name="addCourse" value="go"/>
            <div class="control-group">
                <!-- Title -->
                <label class="control-label" for="course_title"><fmt:message
                        key="label.editcourse.course-title" bundle="${rb}"/><span class="required">*</span></label>
                <div class="controls">
                    <input type="text" class="form-control" id="course_title" name="course_title"
                           required="" pattern="[А-Яа-я\w\s.,?!-+#%_()]{5,75}"/>
                    <p class="help-block"><fmt:message key="label.editcourse.course-title-help" bundle="${rb}"/></p>
                </div>
            </div>

            <div class="control-group">
                <!-- Subject -->
                <label class="control-label" for="subject_id"><fmt:message key="label.editcourse.subject"
                                                                           bundle="${rb}"/><span
                        class="required">*</span></label>
                <div class="controls">
                    <select name="subject_id" id="subject_id" required class="form-control">
                        <c:forEach items="${sessionScope.allSubjects}" var="subjects">
                            <option value="${subjects.id}">${subjects.language} - ${subjects.level}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <!-- Related teacher -->
                <label class="control-label"
                       for="course_teacher_id"><fmt:message key="label.editcourse.teacher"
                                                            bundle="${rb}"/></label>
                <div class="controls">
                    <select name="course_teacher_id" id="course_teacher_id" required class="form-control">
                        <option value="0"><fmt:message key="label.addcourse.course-without-teacher" bundle="${rb}"/></option>
                        <c:forEach items="${sessionScope.allTeachers}" var="teachers">
                            <option value="${teachers.id}">${teachers.lastName} ${teachers.firstName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <!-- Course status -->
                <label class="control-label" for="course_status"><fmt:message key="label.editcourse.status"
                                                                              bundle="${rb}"/><span
                        class="required">*</span></label>
                <div class="controls">
                    <select name="course_status" id="course_status" required class="form-control">
                        <option value="GATHERING"><fmt:message key="label.addcourse.course-status-gathering" bundle="${rb}"/></option>
                        <option value="RUNNING"><fmt:message key="label.addcourse.course-status-running" bundle="${rb}"/></option>
                        <option value="ENDED"><fmt:message key="label.addcourse.course-status-ended" bundle="${rb}"/></option>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <!-- Course Availability -->
                <label class="control-label" for="course_isAvailable"><fmt:message
                        key="label.editcourse.availability" bundle="${rb}"/><span class="required">*</span></label>
                <div class="controls">
                    <select name="course_isAvailable" id="course_isAvailable" required class="form-control">
                        <option value="1"><fmt:message key="label.addcourse.course-available" bundle="${rb}"/></option>
                        <option value="0"><fmt:message key="label.addcourse.course-inavailable" bundle="${rb}"/></option>
                    </select>
                </div>
            </div>
            <br>
            <p><span class="required">*</span> - <fmt:message key="label.form.required-fields" bundle="${rb}"/></p>
            <button type="submit" class="btn btn-danger"><fmt:message key="label.editcourse.add-btn"
                                                                      bundle="${rb}"/></button>
        </form>
    </div>
    <div class="col-md-4"></div>
</div>
<jsp:include page="../../jsp/admin/parts/footer.jsp"/>
</body>
</html>
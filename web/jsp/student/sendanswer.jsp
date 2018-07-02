<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb" />


<!DOCTYPE html>
<html lang="en">
<head>
    <title>Send answer</title>
</head>
<body>
<jsp:include page="../../jsp/student/parts/header.jsp"/>
<H1 align="center"><fmt:message key="label.sendanswer.title" bundle="${rb}"/></H1>

<div class="container" role="main">
    <div class="col-md-3"></div>
    <div class="col-md-6">
        <form method="post" action="/controller">
            <input type="hidden" name="command" value="sendAnswer">
            <input type="hidden" name="taskid" value="${param.taskid}">

            <div class="control-group">
                <!-- Answer -->
                <label class="control-label" for="answer"><fmt:message
                        key="label.sendanswer.answer" bundle="${rb}"/><span class="required">*</span></label>
                <div class="controls">
                    <textarea type="text" rows="10" class="form-control" id="answer" name="answer"
                              required="" pattern="[А-Яа-я\w\s.,?!-+#%_()]{1,4500}"></textarea>
                    <p class="help-block"><fmt:message key="label.sendanswer.helper" bundle="${rb}"/></p>
                </div>
            </div>

            <button type="submit" class="btn btn-success"><fmt:message key="label.sendanswer.send" bundle="${rb}"/></button>
        </form>
    </div>
    <div class="col-md-3"></div>
<jsp:include page="../../jsp/student/parts/footer.jsp"/>
</body>
</html>
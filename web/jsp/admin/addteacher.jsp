<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb"/>

<!DOCTYPE html>
<html lang="EN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="../fonts/favicon.ico">
    <title>Registration</title>
</head>
<body>
<jsp:include page="../../jsp/admin/parts/header.jsp"/>
<div class="container" role="main">
    <div class="col-md-4"></div>
    <div class="col-md-4">

        <form name="teacherRegisterForm" method="POST" action="/controller">
            <input type="hidden" name="command" value="addteacher"/>
            <fieldset>

                <div class="control-group">
                    <!-- E-mail -->
                    <label class="control-label" for="email"><fmt:message key="label.registration.email" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <input type="email" id="email" name="user_email" required="" class="form-control" placeholder="email@example.com" />
                        <p class="help-block"><fmt:message key="label.registration.email.help" bundle="${rb}"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Password -->
                    <label class="control-label" for="password"><fmt:message key="label.registration.password" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <input type="password" id="password" name="user_password" required="" class="form-control"  pattern="^[a-zA-Z0-9-_\.]{4,20}$"/>
                        <p class="help-block"><fmt:message key="label.registration.password.help" bundle="${rb}"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Repeat password -->
                    <label class="control-label" for="check_password"><fmt:message key="label.registration.repeatpassword" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <input type="password" id="check_password" name="check_password" required="" class="form-control" pattern="^[a-zA-Z0-9-_\.]{4,20}$"/>
                        <p class="help-block"><fmt:message key="label.registration.repeatpassword.help" bundle="${rb}"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- First Name -->
                    <label class="control-label" for="first_name"><fmt:message key="label.registration.name" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <input type="text" id="first_name" name="first_name" required="" class="form-control" placeholder="Иван" pattern="^[А-ЯA-Z][a-яa-z]{2,30}"/>
                        <p class="help-block"><fmt:message key="label.registration.name.help" bundle="${rb}"/></p>
                    </div>
                </div>

                <div class="control-group">
                    <!-- Last Name -->
                    <label class="control-label" for="last_name"><fmt:message key="label.registration.last-name" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <input type="text" id="last_name" name="last_name" required="" class="form-control" placeholder="Иванов" pattern="^[А-ЯA-Z][a-яa-z]{2,30}"/>
                        <p class="help-block"><fmt:message key="label.registration.last-name.help" bundle="${rb}"/></p>
                    </div>
                </div>
                <p><span class="required">*</span> - <fmt:message key="label.form.required-fields" bundle="${rb}"/></p>
                <button type="submit" class="btn btn-success" ><fmt:message key="label.login.registration" bundle="${rb}"/></button>
            </fieldset>
        </form>
    </div>
    <div class="col-md-4"></div>
    <jsp:include page="../../jsp/admin/parts/footer.jsp"/>
</body>
</html>

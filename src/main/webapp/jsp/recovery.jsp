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
    <title>Password recovery</title>
</head>
<body>
<jsp:include page="/jsp/parts/header.jsp"/>
<h1 align="center"><fmt:message key="label.recovery.h1" bundle="${rb}"/></h1>
<div class="container" role="main">
    <div class="col-md-4"></div>
    <div class="col-md-4">

        <form name="registerForm" method="POST" action="/controller">
            <input type="hidden" name="command" value="recoverpassword"/>
            <fieldset>

                <div class="control-group">
                    <!-- E-mail -->
                    <label class="control-label" for="email"><fmt:message key="label.registration.email" bundle="${rb}"/><span class="required">*</span></label>
                    <div class="controls">
                        <input type="email" id="email" name="email" required="" class="form-control" placeholder="email@example.com" />
                        <p class="help-block"><fmt:message key="label.registration.email.help" bundle="${rb}"/></p>
                    </div>
                </div>

                <p><span class="required">*</span> - <fmt:message key="label.form.required-fields" bundle="${rb}"/></p>
                <button type="submit" class="btn btn-primary" ><fmt:message key="label.recovery.recovery-btn" bundle="${rb}"/></button>
            </fieldset>
        </form>
    </div>
    <div class="col-md-4"></div>
    <jsp:include page="/jsp/parts/footer.jsp"/>
</body>
</html>
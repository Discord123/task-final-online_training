<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Error page</title>
</head>
<body>
<h1>Exception code: ${pageContext.errorData.statusCode}</h1>
Requset from ${pageContext.errorData.requestURI} is failed
<br/>
Servlet name: ${pageContext.errorData.servletName}
<br/>
Exception: ${pageContext.exception}
<br/>
Message from exception: ${pageContext.exception.message}
<br/>
<br>
<hr>
<a href="/index.jsp"><fmt:message key="label.errorpage.login"  bundle="${rb}"/></a>
</body>
</html>

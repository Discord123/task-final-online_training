<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags"%>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb" />

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Student page</title>
</head>
<body>
<jsp:include page="../../jsp/student/parts/header.jsp"/>
<ctg:welcome role="${sessionScope.user.role}" locale="${sessionScope.locale}" fullName="${sessionScope.user.firstName} ${sessionScope.user.lastName}" />
<jsp:include page="../../jsp/student/parts/footer.jsp"/>
</body>
</html>
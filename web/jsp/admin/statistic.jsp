<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Admin page</title>
</head>
<body>
<jsp:include page="../../jsp/admin/parts/header.jsp"/>
<H1 align="center"><fmt:message key="label.statistic.title" bundle="${rb}"/></H1>

<div class="container theme-showcase" role="main">
    <h3><fmt:message key="label.statistic.users-count" bundle="${rb}"/>${sessionScope.get("getStatistic").usersCount}</h3>
    <h3><fmt:message key="label.statistic.teachers-count" bundle="${rb}"/>${sessionScope.get("getStatistic").teachersCount}</h3>
    <h3><fmt:message key="label.statistic.tasks-count" bundle="${rb}"/>${sessionScope.get("getStatistic").tasksCount}</h3>
    <h3><fmt:message key="label.statistic.courses-count" bundle="${rb}"/>${sessionScope.get("getStatistic").coursesCount}</h3>
    <br/>
    <form method="POST" action="/controller">
        <input type="hidden"  name="command" value="savestatistic" />
        <button type="submit" class="btn btn-warning">
            <fmt:message key="label.statistic.save-btn" bundle="${rb}"/>
        </button>
    </form>
</div>

<%--<!-- Кнопка пуска модальное окно -->--%>
<%--<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">--%>
    <%--Launch demo modal--%>
<%--</button>--%>

<%--<!-- Модальное окно -->--%>
<%--<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">--%>
    <%--<div class="modal-dialog" role="document">--%>
        <%--<div class="modal-content">--%>
            <%--<div class="modal-header">--%>
                <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close">--%>
                    <%--<span aria-hidden="true">&times;</span>--%>
                <%--</button>--%>
                <%--<h4 class="modal-title" id="myModalLabel">Modal title</h4>--%>
            <%--</div>--%>
            <%--<div class="modal-body">--%>
                <%--...--%>
            <%--</div>--%>
            <%--<div class="modal-footer">--%>
                <%--<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>--%>
                <%--<button type="button" class="btn btn-primary">Save changes</button>--%>
                <%--<form method="POST" action="/controller">--%>
                    <%--<input type="hidden"  name="command" value="savestatistic" />--%>
                    <%--<button type="button" class="btn btn-primary">--%>
                        <%--<fmt:message key="label.statistic.save-btn" bundle="${rb}"/>--%>
                    <%--</button>--%>
                <%--</form>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>

<jsp:include page="../../jsp/admin/parts/footer.jsp"/>
</body>
</html>

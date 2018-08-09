<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="localedata" var="rb" />

<!DOCTYPE html>
<html lang="en">
<head>
    <title>All teachers</title>
</head>
<body>
<jsp:include page="../../jsp/admin/parts/header.jsp"/>
<c:if test="${empty sessionScope.allTeachers}">
    <jsp:forward page="/controller?command=takeallteachers"></jsp:forward>
</c:if>

<H1 align="center"><fmt:message key="label.allteachers.title" bundle="${rb}"/></H1>

<div class="container theme-showcase" role="main">
    <div class="col-md-4"></div>
    <div class="col-md-4"></div>
    <div class="col-md-4"></div>
    <div class="row">
        <div class="col-md-12">
            <br/>
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th><fmt:message key="label.allteachers.teacheremail" bundle="${rb}"/></th>
                    <th><fmt:message key="label.allteachers.teachername" bundle="${rb}"/></th>
                    <th><fmt:message key="label.allteachers.teacher-lastname" bundle="${rb}"/></th>
                    <th width="80"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sessionScope.allTeachers}" var="teachers">
                    <tr>
                        <td>${teachers.email}</td>
                        <td>${teachers.firstName}</td>
                        <td>${teachers.lastName}</td>
                        <td>
                            <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal">
                                <fmt:message key="label.allteachers.delete-teacher-btn" bundle="${rb}"/>
                            </button>
                            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                            <h4 class="modal-title" id="myModalLabel"><fmt:message key="label.allteachers.delete-teacher-operation" bundle="${rb}"/></h4>
                                        </div>
                                        <div class="modal-body">
                                            <fmt:message key="label.allteachers.delete-teacher-question" bundle="${rb}"/>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal"> <fmt:message key="label.allteachers.delete-teacher-close" bundle="${rb}"/></button>
                                            <form action="/controller">
                                                <input type="hidden" name="command" value="deleteuser">
                                                <button type="submit" class="btn btn-primary" name="userid" value="${teachers.id}" ><fmt:message key="label.allteachers.delete-teacher-accept" bundle="${rb}"/></button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="../../jsp/student/parts/footer.jsp"/>
</body>
</html>




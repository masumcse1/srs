<%--
  Created by IntelliJ IDEA.
  User: valenciastarr
  Date: 9/23/23
  Time: 9:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
    <jsp:include page="./bs.jsp"/>
</head>
<body>
    <div class="container-fluid m-0 p-0">
        <div class="row">
            <h1 class="col text-center mx-auto p-2 bg-primary">Welcome to the Student Registration Site!</h1>
        </div>
        <div class="row m-2">
            <p class="text-center mx-auto m-2">Hello ${student.firstName}</p>
        </div>
        <div class="row d-flex justify-content-center m-2">
            <div class="col-3 text-center border rounded-left">
                <a class="btn btn-primary m-2" href="/srs/register">register for course</a>
            </div>

            <div class="col-3 text-center border">
                <a class="btn btn-primary m-2" href="/srs/status">check course status</a>
            </div>

            <div class="col-3 text-center border rounded-right">
                <a class="btn btn-primary m-2" id="student-course-registrations" href="/srs/student-course-registrations">student course registrations</a>
            </div>

            <div class="col-3 text-center border rounded-right">
                <a class="btn btn-primary m-2" id="logout" onclick="window.location.replace('${pageContext.request.contextPath}/logout')">logout</a>
            </div>

        </div>
    </div>


</body>
</html>

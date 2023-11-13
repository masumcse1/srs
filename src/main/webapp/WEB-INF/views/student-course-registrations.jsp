<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <jsp:include page="./bs.jsp"/>
    </head>
    <body>
        <div class="container-fluid p-0 m-0">

            <div class="row m-2">
                <h1 class="col text-center mx-auto p-2 bg-primary">Welcome to the Student Registration Site!</h1>
            </div>

            <div class="row m-2">
                <p class="text-center mx-auto m-2">Hello ${student.firstName}</p>
            </div>

            <div class="col-3 text-center mx-auto">
                <a class="btn btn-primary m-2" id="logout" onclick="window.location.replace('${pageContext.request.contextPath}/logout')">logout</a>
            </div>

            <div class="row">
                <h2 class="col text-center mx-auto p-2">
                    Course Registrations By Last Name
                </h2>
            </div>

            <form name="student-course-registrations" class="mx-auto col-sm-4 m-2" action="${pageContext.request.contextPath}/student-course-registrations" method="post">
                <c:if test="${failure}">
                    <p class="alert alert-danger mx-auto text-center">
                        Something went wrong
                    </p>
                </c:if>
                <div class="${CSSClassText}">${text}</div>
                <div class="form-group m-2">
                    <label for="studentLastName">
                        Student last name:
                    </label>
                    <input type="text" id="studentLastName" name="studentLastName" class="form-control" />
                </div>
                <div class="form-group col-3 mt-2">
                    <input type="submit" class="form-control btn btn-primary m-2 text-nowrap" style="width: 16rem;" value="get course registrations"/>
                    <input type="reset" class="form-control btn btn-primary m-2" value="reset" />
                </div>
            </form>
            <div class="row">
                <div class="col-4 mx-auto">
                    <a class="btn btn-primary text-center m-2" href="${pageContext.request.contextPath}/">back</a>
                </div>
            </div>
        </div>
    </body>
</html>

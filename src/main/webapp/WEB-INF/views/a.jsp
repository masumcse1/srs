<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration Form A</title>
        <jsp:include page="./bs.jsp"/>
    </head>
    <body>
        <div class="container-fluid">
            <a class="btn btn-primary mt-2" href="${pageContext.request.contextPath}/" style="cursor:pointer">back</a>
            <div class="row">
                <div class="col-md-6">
                    <h1>Registration Form A</h1>
                    <c:if test="${not empty student.errorsFormA}">
                        <div class="alert alert-danger">
                            <h5>Fix the following errors</h5>
                            <c:forEach items="${student.errorsFormA}" var="v">
                                <div>
                                    ${v.value}
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>


                    <form method="post" action="${pageContext.request.contextPath}/a">
                        <div class="mb-3">
                            <label for="id" class="form-label">User Id</label>
                            <input type="text" class="form-control" id="id" name="id" value="${student.id}">
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password" value="${student.password}">
                        </div>
                        <div class="mb-3">
                            <label for="repeatPassword" class="form-label">Repeat Password</label>
                            <input type="password" class="form-control" id="repeatPassword" name="repeatPassword" value="${student.repeatPassword}">
                        </div>
                        <div class="mb-3">
                            <label for="firstName" class="form-label">First Name</label>
                            <input type="text" class="form-control" id="firstName" name="firstName" value="${student.firstName}">
                        </div>
                        <div class="mb-3">
                            <label for="lastName" class="form-label">Last Name</label>
                            <input type="text" class="form-control" id="lastName" name="lastName" value="${student.lastName}">
                        </div>
                        <div class="mb-3">
                            <label for="ssn" class="form-label">Social Security Number</label>
                            <input type="password" class="form-control" id="ssn" name="ssn" value="${student.ssn}">
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="text" class="form-control" id="email" name="email" value="${student.email}">
                        </div>
                        <label for="level" class="form-label">Level:</label>
                        <div class="mb-3">
                            <select  class="form-control" name="level" id="level">
                                <c:choose>
                                    <c:when test="${student.level eq undergraduate}">
                                        <option value="undergraduate" selected>undergraduate</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="undergraduate">undergraduate</option>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${student.level eq graduate}">
                                        <option value="graduate" selected>graduate</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="graduate">graduate</option>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Continue</button>
                        <a href="${pageContext.request.contextPath}/reset" class="btn btn-danger">Reset</a>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>

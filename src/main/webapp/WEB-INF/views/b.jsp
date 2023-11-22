<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration Form B</title>
        <jsp:include page="./bs.jsp"/>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-6">
                    <h1>Registration Form B</h1>
                    <c:if test="${not empty student.errorsFormB}">
                        <div class="alert alert-danger">
                            <h5>Fix the following errors</h5>
                            <c:forEach items="${student.errorsFormB}" var="v">
                                <div>
                                    ${v.value}
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>
                    <c:if test="${complete}">
                        <div class="alert alert-info">
                            You Have Completed Registration
                        </div>
                    </c:if>
                    <c:if test="${failure}">
                        <div class="alert alert-danger">
                            Something went wrong
                        </div>
                    </c:if>
                      <c:if test="${registermaximumlimitover}">
                        <div class="alert alert-danger">
                            students registering during a given timeframe maximum limit over
                        </div>
                    </c:if>
                    <c:if test="${duplicate}">
                        <div class="alert alert-danger">
                            Account already exists in our system.  Please login.  If you forgot your userid or password please contact the administration department.
                        </div>
                    </c:if>
                    <form method="post" action="${pageContext.request.contextPath}/b">
                        <div class="mb-3">
                            <label for="address" class="form-label">Address</label>
                            <input type="text" class="form-control" id="address" name="address" value="${student.address}">
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-8">
                                <label for="city" class="form-label">City</label>
                                <input type="text" class="form-control" id="city" name="city" value="${student.city}">
                            </div>
                            <div class="col-md-4">
                                <label for="state" class="form-label">State</label>
                                <input type="state" class="form-control" id="state" name="state" value="${student.state}">
                            </div>

                        </div>
                        <div class="mb-3">
                            <label for="zipCode" class="form-label">Zip Code/Postal Code</label>
                            <input type="zipCode" class="form-control" id="zipCode" name="zipCode" value="${student.zipCode}">
                        </div>
                        <button type="submit" class="btn btn-primary">Register</button>
                        <a href="${pageContext.request.contextPath}/reset" class="btn btn-danger">Reset</a>
                    </form>
                    <div>
                        <a class="btn btn-primary m-2" href="${pageContext.request.contextPath}/login">Login</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: valenciastarr
  Date: 10/7/23
  Time: 11:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Status</title>
    <jsp:include page="./bs.jsp"/>
</head>
<body>
  <div class="container-fluid">

    <div class="row">
      <h1 class="col text-center mx-auto p-2 bg-primary">
        Welcome to the Student Registration Site!
      </h1>
    </div>

    <div class="row m-2">
      <p class="text-center mx-auto m-2">Hello ${student.firstName}</p>
    </div>

    <div class="col-3 text-center mx-auto">
      <a class="btn btn-primary m-2" id="logout" onclick="window.location.replace('${pageContext.request.contextPath}/logout')">logout</a>
    </div>

    <div class="row">
      <h2 class="col text-center mx-auto p-2">
        Registration Status Report
      </h2>
    </div>

    <form class="mx-auto col-4" method="post" action="/srs/status">
      <c:if test="${failure}">
        <p class="error">Something went wrong</p>
      </c:if>
      <c:if test="${messageCSSClass}==error"></c:if>
      <div class="${messageCSSClass}">${message}</div>
      <fieldset>
        <legend>Check any specific course(s) and click on submit:</legend>
          <c:forEach items="${courses}" var="course">
            <div class="form-check">
              <input class="form-check-input" type="checkbox" name="${course.courseTitle}" value="${course.courseTitle}">
              <label class="form-check-label" for="${course.courseTitle}">${course.courseTitle}</label>
            </div>
          </c:forEach>
      </fieldset>
      <div>
        <input class="btn btn-primary" type="submit" value="submit">
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

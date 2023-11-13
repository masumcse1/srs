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
  <div class="row">
    <h1 class="col text-center mx-auto p-2 bg-primary">Welcome to the Student Registration Site!</h1>
  </div>
  <div class="row">
    <h2 class="mx-auto text-center m-2">
      If you already have an account please login
    </h2>
    <form name="login" class="mx-auto col-sm-4 m-2" action="${pageContext.request.contextPath}/login" method="post">
      <c:if test="${failure}">
        <p class="alert alert-danger mx-auto text-center">
          Something went wrong
        </p>
      </c:if>
      <c:if test="${invalidCredentials}">
        <p class="alert alert-danger mx-auto text-center">
          Userid/password combination not on file.  Please try to login again or try to register. You have ${remainingLoginAttempts} login attempts remaining.
        </p>
      </c:if>
      <c:if test="${noCredentials}">
        <p class="alert alert-danger mx-auto text-center">
          Please enter userid and password.
        </p>
      </c:if>
      <div class="form-group m-2">
        <label for="userId">
          User Id:
        </label>
        <input type="text" id="userId" name="userId" class="form-control" />
      </div>
      <div class="form-group m-2">
        <label for="password">
          Password:
        </label>
        <input type="password" id="password" name="password" class="form-control" />
      </div>
      <div class="form-group col-3 mt-2">
        <input type="submit" class="form-control btn btn-primary m-2" value="login"/>
        <input type="reset" class="form-control btn btn-primary m-2" value="reset" />
      </div>
    </form>

  </div>

  <div class="row">
    <h2 class="m-2 mx-auto text-center">
      For new users, please register first <a href="./a">register</a>
    </h2>
  </div>

</div>
</body>
</html>

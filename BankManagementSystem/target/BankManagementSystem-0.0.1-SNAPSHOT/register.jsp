<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>Register</h2>
  <form action="register" method="post">
        Full Name: <input type="text" name="fullname" required> <br>
        UserName: <input type="text" name="username" required> <br>
        Password: <input type="password" name="password"required> <br>
        <input type="submit" value="Register">
  </form>
  <c:if test="${param.success != null}">
        <p style="color:green">${param.success}</p>
    </c:if>
    <c:if test="${param.error != null}">
        <p style="color:red">${param.error}</p>
    </c:if>
    
     <p>Already have an account? <a href="login.jsp">Login</a></p>
</body>
</html>
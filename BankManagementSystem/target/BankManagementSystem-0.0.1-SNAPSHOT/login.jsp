<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>Login</h2>
<form action="login" method="post">
   Username: <input type="text" name="username" required> <br>
   Password: <input type="password" name="password" required> <br>
   <input type="submit" value="login">
   
</form>

<c:if test="${param.error != null}">
<p style="color:red">${param.error}</p>
</c:if>

<p>Dont havean account? <a href="register.jsp">Register</a></p>

</body>
</html>
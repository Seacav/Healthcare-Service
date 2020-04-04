<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>      
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>Login</h2>
	<form:form action="${pageContext.request.contextPath}/authTheUser" method="POST">
		<c:if test="${param.logout != null }">
			<i>You have been logged out.</i>
		</c:if>
		<c:if test="${param.error != null }">
			<i>You entered invalid username/password</i>
		</c:if>
		<p>User name: <input type="text" name="username"/></p>
		<p>Password: <input type="password" name="password"/></p>
		<input type="submit" value="Login"/>
	</form:form>
</body>
</html>
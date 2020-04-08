<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css">
<link rel="stylesheet" href="<c:url value="/resources/static/css/loginPageStyle.css" />">
</head>
<body>
<div class="wrapper">
	<h2>Login</h2>
	<form:form action="${pageContext.request.contextPath}/authTheUser" method="POST">
		<c:if test="${param.logout != null }">
			<i class="warning">You have been logged out.</i>
		</c:if>
		<c:if test="${param.error != null }">
			<i class="warning">You entered invalid username/password</i>
		</c:if>
		<div class="input-container">
			<i class="fas fa-user"></i><input type="text" name="username"/>
		</div>
		<div class="input-container">
			<i class="fas fa-key"></i><input type="password" name="password"/>
		</div>
		<div class="input-container">
			<input type="submit" value="Sign In"/>
		</div>
	</form:form>
</div>
</body>
</html>
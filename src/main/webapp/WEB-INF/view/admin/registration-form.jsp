<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<c:url value="/resources/static/css/tiles/navbarStyle.css" />">
<link rel="stylesheet" href="<c:url value="/resources/static/css/admin/registrationFormStyle.css" />">
<link rel="stylesheet" href="<c:url value="/resources/static/css/tiles/footerStyle.css" />">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css">
<title>Administrator panel</title>
</head>
<body>
<div class="navbar">
		<a href="${pageContext.request.contextPath}/admin/"><i class="fas fa-home"></i></a>
		<form:form action="${pageContext.request.contextPath}/logout" method="POST">
			<button type="submit" class="logout"><i class="fas fa-door-open"></i></button>
		</form:form>
</div>
<div class="wrapper">
	<div class="title">
		<h2>Register new user</h2>
	</div>
	<form:form id="regForm" action="register" modelAttribute="user" class="registration-form" method="POST">
		<div class="input-container">
			<span>Username: </span><form:input path="username"/>
		</div>
		<div class="input-container">
			<span>First Name: </span><form:input path="firstName"/>
		</div>
		<div class="input-container">
			<span>Last Name: </span><form:input path="lastName"/>
		</div>
		<div class="input-container">
			<span>Role: </span><form:select path="role">
			   <form:option value="DOCTOR" label="Doctor" />
			   <form:option value="NURSE" label="Nurse" />
			</form:select>
		</div>
		<div class="input-container">
			<span>Password: </span><form:input type="password" path="password"/>
		</div>
		<input type="submit" id="subBtn" value="Submit"/>
	</form:form>
	<script src="<c:url value="/resources/static/js/admin/registrationFormScript.js"/>"></script>
</div>
<div class="footer">
	<a>Some info here</a>
</div>
</body>
</html>
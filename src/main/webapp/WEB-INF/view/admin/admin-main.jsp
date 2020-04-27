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
<link rel="stylesheet" href="<c:url value="/resources/static/css/admin/adminMainStyle.css" />">
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
		<h2>Administrator panel</h2>
	</div>
	<a class="menu-option" href="<c:url value="registrationForm" />">Register new user</a>
	<a class="menu-option" href="<c:url value="listUsers" />">List all users</a>
    <a class="menu-option" href="<c:url value="addTreatment" />">Add new treatment</a>
</div>
<div class="footer">
	<a>Some info here</a>
</div>
</body>
</html>
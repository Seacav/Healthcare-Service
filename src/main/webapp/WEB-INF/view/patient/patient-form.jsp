<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet" href="<c:url value="/resources/static/css/tiles/navbarStyle.css" />">
<link rel="stylesheet" href="<c:url value="/resources/static/css/tiles/footerStyle.css" />">
<link rel="stylesheet" href="<c:url value="/resources/static/css/patientFormStyle.css" />">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css">
</head>
<body>
<div class="navbar">
		<a href="${pageContext.request.contextPath}/doctor/"><i class="fas fa-home"></i></a>
		<form:form action="${pageContext.request.contextPath}/logout" method="POST">
			<button type="submit" class="logout"><i class="fas fa-door-open"></i></button>
		</form:form>
</div>
<div class="wrapper">
	<h2>Patient form</h2>
	<form:form action="process-form" modelAttribute="patient" method="POST" id="patient-form">
		<div class="input-container">
			<span>First name: </span><form:input path="firstName"/>
		</div>
		<div class="input-container">
			<span>Last name: </span><form:input path="lastName"/>
		</div>
		<div class="input-container">
			<span>Insurance number: </span><form:input path="insNumber" value="${insNumber}"/>
		</div>
		<div class="input-container">
			<span>Diagnosis: </span><form:input path="diagnosis"/>
		</div>
		<input type="submit" id="subBtn" value="Submit"/>
	</form:form>
	<script src="<c:url value="/resources/static/js/patientFormScript.js"/>"></script>
</div>
<div class="footer">
	<a>Some info here</a>
</div>
</body>
</html>
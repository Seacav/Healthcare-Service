<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>Patient form</h2>
<form:form action="process-form" modelAttribute="patient" method="POST">
	<form:hidden path="id"/>
	First name: <form:input path="firstName"/><br><br>
	Last name: <form:input path="lastName"/><br><br>
	Insurance number: <form:input path="insNumber" value="${insNumber}"/><br><br>
	Diagnosis: <form:input path="diagnosis"/><br><br>
	<input type="submit" value="Submit"/>
</form:form>
</body>
</html>